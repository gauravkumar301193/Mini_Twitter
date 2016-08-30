function fetchTweetsBetweenTimeStamps(url, data, isNew) {
    if (url == "")
        return;
    console.log("appending");
    $(MIDDLE_PANEL_TWEET_PARENT).append("<div class=\"loader\" id=\"loader\"></div>");
    localStorage.setItem("sent", "true");
    $.ajax({
        url : url,
        data : data,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        async : false,
        success : function(result) {
            console.log(result);
            var tweets = result.Tweets;
            console.log(tweets);
            localStorage.removeItem("sent");
            var totalTweetsFetched = tweets.length;
            if (totalTweetsFetched > 0) {
                var tweetsHTMLArray = parseJSONOfAllTweets(tweets);
                if (isNew == true) {
                    for (var i = tweetsHTMLArray.length - 1; i >= 0; i--) {
                        $(MIDDLE_PANEL_TWEET_PARENT).prepend(tweetsHTMLArray[i]);
                    }
//                    for (var i = 0; i < tweets.length; i++) {
//                        var tweetProfilePhotoId = "#profileImage-" + tweets[i].authorId;
//                        var tweetPostImage = "#tweetImage-" + tweets[i].mediaId;
//                        $(tweetProfilePhotoId).attr("src", FETCH_IMAGE_GIVEN_USER_ID + "?mediaId=" + tweets[i].authorId);
//                        $(tweetProfilePhotoId).show();
//                        if (tweets[i].mediaId != 0) {
//                        	console.log("Fetching from URL = " + IMAGE_RETRIEVE_URL + "?mediaId=" + tweets[i].mediaId);
//	                        $(tweetPostImage).attr("src", IMAGE_RETRIEVE_URL + "?mediaId=" + tweets[i].mediaId);
//	                        $(tweetPostImage).show();
//                        }
//                    }
                    localStorage.setItem("latestTime", tweets[0].timestamp);
                } else {
                    for (var i = 0; i < tweetsHTMLArray.length; i++) {
                        $(MIDDLE_PANEL_TWEET_PARENT).append(tweetsHTMLArray[i]);       
                    }
//                    for (var i = 0; i < tweets.length; i++) {
//                    	console.log("here11");
//                        var tweetProfilePhotoId = "#profileImage-" + tweets[i].authorId;
//                        var tweetPostImage = "#tweetImage-" + tweets[i].mediaId;
//                        $(tweetProfilePhotoId).attr("src", FETCH_IMAGE_GIVEN_USER_ID + "?mediaId=" + tweets[i].authorId);
//                        $(tweetProfilePhotoId).show();
//                    	console.log("here22");
//                        if (tweets[i].mediaId != 0) {
//                        	console.log("here33");
//                        	console.log("Fetching from URL = " + IMAGE_RETRIEVE_URL + "?mediaId=" + tweets[i].mediaId);
//	                        $(tweetPostImage).attr("src", IMAGE_RETRIEVE_URL + "?mediaId=" + tweets[i].mediaId);
//	                        $(tweetPostImage).show();
//                        }
//                    }
                    localStorage.setItem("latestTime", tweets[0].timestamp);
                }
                for (var i = 0; i < tweets.length; i++) {
                    changeButtonStatesForTweet(tweets[i], localStorage.getItem("loggedInUser"));
                }
            } else if (isNew == false) {
                localStorage.setItem("moreOlderTweets", false);
            }
            localStorage.removeItem("sent");
            $(LOADER).remove();
        }
    });
}

function fetchNewTweetsFromServer(url, userId, loggedInUser) {
    var startTime = 0;
    if (localStorage.getItem("latestTime") != null) {
        startTime = localStorage.getItem("latestTime");
    }
    var latestTime = Date.now();
    var data = {};
    data["userId"] = userId;
    data["startTime"] = startTime;
    data["latestTime"] = latestTime;
    data["loggedInUser"] = loggedInUser;
    fetchTweetsBetweenTimeStamps(url, data, true);
}

function fetchOlderTweetsFromServer(url, userId, loggedInUser) {
    var latestTime;
    if (localStorage.getItem("startTime") != null) {
        latestTime = localStorage.getItem("startTime");
    } 
    if (localStorage.getItem("moreOlderTweets") == true) {
        return;
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
    fetchOlderTweetsFromServer(TWEETS_BY_A_SPECIFIC_USER, userId, localStorage.getItem("loggedInUser"));
}

function fetchOlderTweetsForUserHome(userId) {
    fetchOlderTweetsFromServer(TWEETS_FOR_USER_HOME_URL, userId, localStorage.getItem("loggedInUser"));
}

function fetchNewerTweetsForUserHome(userId) {
    fetchNewTweetsFromServer(TWEETS_FOR_USER_HOME_URL, userId, localStorage.getItem("loggedInUser"));
}

function fetchOlderTweetsForUserProfile(userId) {
    fetchOlderTweetsFromServer(TWEETS_FOR_USER_PROFILE, userId, localStorage.getItem("loggedInUser"));
}

function fetchNewerTweetsForUserProfile(userId) {
    fetchNewerTweetsFromServer(TWEETS_FOR_USER_PROFILE, userId, localStorage.getItem("loggedInUser"));
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
            console.log("error occurred: " + e);
        }
    });
}