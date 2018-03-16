import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import org.apache.commons.codec.binary.Base64;

public class V1_0{

    private String league, season, feed, format, param, finalURL;
    private final String baseURL = "https://api.mysportsfeeds.com/v1.0/pull";
    private final String[] possibleFeeds = {
            "cumulative_player_stats", "full_game_schedule,", "daily_game_schedule", "daily_player_stats",
            "game_boxscore", "scoreboard", "game_play-by-play", "game_starting_lineup", "player_game_logs",
            "team_game_logs", "roster_players", "active_players", "overall_team_standings", "conference_team_standings",
            "division_team_standings", "playoff_team_standings", "player_injuries", "current_season",
            "latest_updates", "daily_dfs"
    };


    public V1_0(String league, String season, String feed, String format, String param){
        this.league = league;
        this.season = season;
        this.feed = feed;
        this.format = format;
        this.param = param;
        finalURL = "";
    }

    public boolean checkFeed(String feed){
        for(String i : possibleFeeds){
            if(i.toLowerCase().equals(feed)){
                return true;
            }
        }

        return false;
    }

    public void createURL() {
        if(checkFeed(feed)){
            finalURL = baseURL + "/" + league + "/" + season + "/" + feed + "." + format + "?" + param;
            System.out.println(finalURL);
        }
    }


    public void getFile(String username, String password){
        try {
            createURL();
            String webPage = finalURL;

            URL url = new URL(webPage);
            String authString = username + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            connection.setRequestProperty("Accept-Encoding", "gzip");

            InputStream content = (InputStream)connection.getInputStream();
            GZIPInputStream is = new GZIPInputStream(content);

            byte[] buffer = new byte[4096];
            int i = - 1;

            String fileName = feed + "-" +  league + "-" + season + "." + format;
            File file = new File(fileName);

            OutputStream output = new FileOutputStream(file);
            while ( (i = is.read(buffer)) != -1) {
                output.write(buffer, 0, i);
            }
            output.close();
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
