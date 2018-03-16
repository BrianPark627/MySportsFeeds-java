import org.apache.commons.codec.binary.Base64;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class V1_2 extends V1_1{

    private final String baseURL = "https://api.mysportsfeeds.com/v1.2/pull";
    private String league, season, feed, format, param, finalURL;

    public V1_2(String league, String season, String feed, String format, String param) {
        super(league, season, feed, format, param);
        this.league = league;
        this.season = season;
        this.feed = feed;
        this.format = format;
        this.param = param;
        finalURL = "";
    }

    public void createURL(){
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
            System.out.println("auth string: " + authString);
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            // System.out.println("Base64 encoded auth string: " + authStringEnc);

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
