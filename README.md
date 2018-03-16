# MySportsFeeds-java
Java wrapper for MySportsFeed API

Does not contain credentials required for authentication.

You can sign up for API access at https://www.mysportsfeeds.com/index.php/register/

## Usage
Initializing MySportsFeeds object with the version as parameters
```java
MySportsFeeds mysportsfeeds = new MySportsFeeds(version#);
```

Authenticate using MySportsFeeds API credentials
```java
mysportsfeeds.authenticate("username", "password");
```
Requests can be made using ```.get()```
```java
mysportsfeeds.get("MLB", "2017-regular", "scoreboard", "json", "fordate=20170721");
```
gets the 07/21/2017 scoreboard from the MLB 2017 regular season in json format

supported formats are ```.json``` ```.csv``` ```.xml```
