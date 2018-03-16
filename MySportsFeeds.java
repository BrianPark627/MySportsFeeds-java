public class MySportsFeeds {

    private double version;
    private String username, password;
    private boolean force;

    public MySportsFeeds(double version) throws versionNotRecognizedException {
        this.version = version;
        force = true;
        checkVersion();
    }

    private double checkVersion() throws versionNotRecognizedException {
        if(version == 1.0 || version == 1.1 || version == 1.2){
            return version;
        }else
            throw new versionNotRecognizedException("The specified version is not recognized. Supported versions are: 1.0, 1.1, 1.2");
    }

    public void authenticate(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void get(String league, String season, String feed, String format, String param) {
        if (version == 1.0) {
            V1_0 version1_0 = new V1_0(league, season, feed, format, param);
            version1_0.getFile(username, password);
        } else if (version == 1.1) {
            V1_1 version1_1 = new V1_1(league, season, feed, format, param);
            version1_1.getFile(username, password);
        } else {
            V1_2 version1_2 = new V1_2(league, season, feed, format, param);
            version1_2.getFile(username, password);
        }
    }
}
