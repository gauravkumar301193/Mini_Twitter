function fetchTweetsBetweenTimeStamps(url, data, isNew) {
    if (url == "")
        return;
    console.log("appending");
    if (isNew != true) {
	    $(MIDDLE_PANEL_TWEET_PARENT).append("<div class=\"loader\" id=\"loader\"></div>");
	    localStorage.setItem("sent", "true");
    }
    $.ajax({
        url : url,
        data : data,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log(result);
            var tweets = result.Tweets;
            console.log(tweets);
            var totalTweetsFetched = tweets.length;
            if (totalTweetsFetched > 0) {
                var tweetsHTMLArray = parseJSONOfAllTweets(tweets, isNew);
                if (isNew == true) {
                    for (var i = tweetsHTMLArray.length - 1; i >= 0; i--) {                    	
                        $(MIDDLE_PANEL_TWEET_PARENT).prepend(tweetsHTMLArray[i]);
                    }

                    localStorage.setItem("latestTime", tweets[0].timestamp);
                } else {
                    for (var i = 0; i < tweetsHTMLArray.length; i++) {
                        $(MIDDLE_PANEL_TWEET_PARENT).append(tweetsHTMLArray[i]);       
                    }
                    if (localStorage.getItem("latestTime") == null) {
                    	localStorage.setItem("latestTime", tweets[0].timestamp);
                    }
                    localStorage.setItem("startTime", tweets[tweets.length - 1].timestamp);
                }
                REQUEST_FOR_TWEETS_SENT = false;
                for (var i = 0; i < tweets.length; i++) {
                	console.log("Changing button states");
                    changeButtonStatesForTweet(tweets[i], getLoggedInUser());
                }
            } else if (isNew == false) {
                localStorage.setItem("moreOlderTweets", false);
            }
            REQUEST_FOR_TWEETS_SENT = false;
            $(LOADER).remove();
        },
        error : function(e) {
        	redirectToLoginIfError(e);
            REQUEST_FOR_TWEETS_SENT = false;
        }
    });
}

function fetchNewTweetsFromServer(url, userId, loggedInUser) {
    var startTime = 0;
    if (localStorage.getItem("latestTime") != null) {
        startTime = localStorage.getItem("latestTime");
        startTime++;
    }
    var latestTime = Date.now();
    var data = {};
    data["userId"] = userId;
    data["startTime"] = startTime;
//    data["latestTime"] = latestTime;
    data["loggedInUser"] = loggedInUser;
    fetchTweetsBetweenTimeStamps(url, data, true);
}

function fetchOlderTweetsFromServer(url, userId, loggedInUser) {
    var latestTime;
    if (localStorage.getItem("startTime") != null) {
        latestTime = localStorage.getItem("startTime");
    } 
    var startTime = 0;
    console.log(latestTime + "  " + startTime);
    var startTime = 0;
    var data = {};
    data["userId"] = userId;
    data["startTime"] = startTime;
    data["latestTime"] = latestTime;
    data["loggedInUser"] = loggedInUser;
    fetchTweetsBetweenTimeStamps(url, data, false);
}

function fetchOlderTweetsForHashtag(hashtag, loggedInUser) {
    var latestTime = Date.now();
    if (localStorage.getItem("startTime") == null) {
        latestTime = localStorage.getItem("startTime");
    }
    var startTime = 0;
    var data = {};
    data["hashtag"] = hashtag;
    data["startTime"] = startTime;
    data["latestTime"] = latestTime;
    data["loggedInUser"] = loggedInUser;
    fetchTweetsBetweenTimeStamps(TWEETS_FOR_A_HASHTAG, data, false);    
}

function fetchNewTweetsForHashtag(hashtag, loggedInUser) {
    var startTime = 0;
    if (localStorage.getItem("latestTime") != null) {
        startTime = localStorage.getItem("latestTime");
    }
    var latestTime = Date.now();
    var data = {};
    data["hashtag"] = hashtag;
    data["startTime"] = startTime;
    data["latestTime"] = latestTime;
    data["loggedInUser"] = loggedInUser;
    fetchTweetsBetweenTimeStamps(TWEETS_FOR_A_HASHTAG, data, true);
}

function fetchTweetsByASingleUser(userId) {
    fetchOlderTweetsFromServer(TWEETS_BY_A_SPECIFIC_USER, userId, getLoggedInUser());
}

function fetchOlderTweetsForUserHome(userId) {
    fetchOlderTweetsFromServer(TWEETS_FOR_USER_HOME_URL, userId, getLoggedInUser());
}

function fetchNewerTweetsForUserHome(userId) {
    fetchNewTweetsFromServer(TWEETS_FOR_USER_HOME_URL, userId, getLoggedInUser());
}

function fetchOlderTweetsForUserProfile(userId) {
    fetchOlderTweetsFromServer(TWEETS_FOR_USER_PROFILE, userId, getLoggedInUser());
}

function fetchNewerTweetsForUserProfile(userId) {
    fetchNewerTweetsFromServer(TWEETS_FOR_USER_PROFILE, userId, getLoggedInUser());
}

function fetchTweetGivenTweetId(tweetId, loggedInUser) {
    $.ajax({
        url : FETCH_SINGLE_TWEET_URL,
        data : {
            tweetId : tweetId,
            loggedInUser : loggedInUser
        }, 
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            var tweets = result.Tweets;
            console.log(tweets);
            if (tweets.length > 0) {
                var tweetText = parseTweetText(tweets[0]);
                var tweetHTML = designTweet(tweets[0], tweetText);
                $(MIDDLE_PANEL_TWEET_PARENT).prepend(tweetHTML);
            }
        },
        error : function(e) {
        	redirectToLoginIfError(e);
            console.log("error occurred: " + e);
        }
    });
}