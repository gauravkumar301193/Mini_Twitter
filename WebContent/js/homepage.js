var currentUser;
var currentUserHandle;
var tweetURL;

$(document).ready(function() {
    destroyTimeStamps();
    if (!checkIfLoggedInUserSet()) {
        closeThisSession();
    }
    if (localStorage.getItem("pageFunction") == "home") {
        tweetURL = TWEETS_FOR_USER_HOME_URL;
        setCurrentEqualLoggedIn();
    } else if (localStorage.getItem("pageFunction") == "profile") {
        tweetURL = TWEETS_FOR_USER_PROFILE;
        if (!checkIfCurrentUserSet())
            closeThisSession();
    } else if (localStorage.getItem("pageFunction") == "tweets") {
        tweetURL = TWEETS_BY_A_SPECIFIC_USER;
        if (!checkIfCurrentUserSet())
            closeThisSession();
    } else {
        closeThisSession();
    }
    
    currentUser = localStorage.getItem("currentUser");
    currentUserHandle = localStorage.getItem("currentUserHandle");
    
    if (localStorage.getItem("CurrentUserMediaId") != 0) {
        $(PROFILE_PHOTO).attr("src", IMAGE_BY_USER_ID + "?userId=" + currentUser);
    }
    $(PROFILE_PHOTO).show();    
    
    $(FOLLOWER_COUNT).html(localStorage.getItem("currentUserFollowerCount"));
    $(TWEET_COUNT).html(localStorage.getItem("currentUserTweetCount"));
    $(USER_HANDLE_LEFT_PANEL).html(currentUserHandle);

    fetchOlderTweetsFromServer(tweetURL, currentUser, TWEET_PARENT);

//    var newTweets = setInterval(function(){
//        fetchNewerTweetsFromServer(tweetURL, currentUser, TWEET_PARENT);
//    }, NEW_TWEET_FETCH_INTERVAL);
    
    $(TWEET_PARENT).click(function(e){
        var elementId = e.target.id.split("#");
        
        if (elementId[0] == "profileImage") {
            // Add action to display media.
        } else if (elementId[0] == "profile") {
            destroyTimeStamps();
            console.log("here");
            fetchUserDetailsGivenIdAndChangePageToProfile( elementId[2], elementId[1], false);
        } else if (elementId[0] == "hashtag") {
            localStorage.setItem("hashtag", elementId[1]);
            destroyTimeStamps();
            $(HASHTAG_TITLE).show();
            $(TWEET_PARENT).html("");
            $(LEFT_PANEL).hide();
            $(HASHTAG_LEFT_PANEL).show();
            tweetURL = TWEETS_FOR_A_HASHTAG;
            $(HASHTAG_TITLE_TEXT).val("#" + elementId[1]);
            
        } else if (elementId[0] == "tweetImage") {
            // Add action to display media.
        } else if (elementId[0] == "like") {
            if (e.target.name == "like") {
                likeTweet(currentUser, elementId[1]);
                changeButtonState(e.target.id, "unlike", "lightGray");
            } else {
                unlikeTweet(currentUser, elementId[1]);
                changeButtonState(e.target.id, "like", "lightblue");
            }
        } else if (elementId[0] == "retweet") {
            retweetATweet(currentUser, elementId[1]);
            changeButtonState(e.target.id, "retweeted", "lightgray");
        } else if (elementId[0] == "reply") {
            // Open a modal to write tweet and username already witten.
        }
    });
    
    $(LEFT_PANEL).click(function(e){
        if (e.target.id == FOLLOWERS_LEFT_PANEL) {
            destroyTimeStamps();
            clearInterval(newTweets);
            $(MIDDLE_PANEL_TWEETS).hide();
            $(MIDDLE_PANEL_FOLLOWERS).show();
            getAllFollowers(currentUser);
        } else if (e.target.id == TWEETS_LEFT_PANEL) {
            localStorage.setItem("pageFunction", "tweets")
            destroyTimeStamps();
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        } else if (e.target.id == USER_HANDLE_LEFT_PANEL) {
            localStorage.setItem("pageFunction", "profile");
            destroyTimeStamps();
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        }
    });
    
    $(HOME_BUTTON).click(function() {
        destroyTimeStamps();
        clearCurrentUserFromLocalStorage();
        localStorage.setItem("pageFunction", "home");
        window.location.replace(PROFILE_CUM_HOME_PAGE);
    });
    
    $(NOTIFICATION_BUTTON).click(function(){
        // design a modal and update it.
    });
    
    $(PROFILE_BUTTON).click(function() {
        destroyTimeStamps();
        setCurrentEqualLoggedIn();
        localStorage.setItem("pageFunction", "profile");
        window.location.replace(PROFILE_CUM_HOME_PAGE);
    });
    
    $(LOGOUT_BUTTON).click(function() {
        closeThisSession();
        destroyTimeStamps();
        localStorage.removeItem("pageFunction");
        requestServerToLogout();
    });
    
    $(UPDATE_PROFILE_BUTTON).click(function() {
        destroyTimeStamps();
        clearCurrentUserFromLocalStorage();
        window.location.replace(UPDATE_USER_INFO_PAGE);
    });
    
    $(FOLLOWER_PARENT).click(function(e) {
        var elementid = e.target.id.split("#");
        if (elementid[0] == "profile") {
            $(MIDDLE_PANEL_FOLLOWERS).hide();
            fetchUserDetailsGivenIdAndChangePageToProfile( elementid[2], elementid[1]);
            
        } else if (elementid[0] == "profileImage") {
            // open in modal
        } else if (elementid[0] == "follow") {
            follow(getLoggedInUser(), elementid[1], e.target.id);
        }
    });
    
    var count = 0;
    $(NEW_TWEET_TEXT).keypress(function(key) {
        count++;
    });
    
    $(NEW_TWEET_TEXT).keydown(function(e) {
        if (e.which == 8 || e.which == 46) {
            count--;
        if (count < 0)
            count = 0;
        if (count > 140)
            count = 140;
            console.log("count:" + count);
        }
    });
    
    $(NEW_TWEET_TEXT).keyup(function(e) {
        var text = $(NEW_TWEET_TEXT).val();
        if (text.length > 140) {
            text = text.substring(0, 140);
            count = 140;
        }            
        $(NEW_TWEET_TEXT).val(text);
    });
        
    $(POST_NEW_TWEET).click(function() {
        var tweetId = 0;
        if ($(NEW_TWEET_TEXT).val() != "") {
            if (!checkIfLoggedInUserSet()) {
                destroyTimeStamps();
                closeThisSession();
                window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
            }
            var userId = getLoggedInUser();
            var userHandle = localStorage.getItem("loggedInUserHandle");
            tweetId = postNewTweet($(NEW_TWEET_TEXT).val(), userId, userHandle);
            if ($(IMAGE_UPLOAD_WITH_TWEET).get(0).files.length != 0) {
                while(tweetId == 0);
                uploadImage(UPLOAD_IMAGE_FOR_TWEET_URL, IMAGE_UPLOAD_WITH_TWEET, tweetId);
            }
        }
    });
    
    $(window).on("scroll", function() {
        if (condition) {
            if (localStorage.getItem("hashtag") == null)
                fetchNewerTweetsFromServer(tweetURL, currentUser, TWEET_PARENT);
            else 
                fetchNewerTweetsFromServer(tweetURL, localStorage.getItem("hashtag"), TWEET_PARENT);                
        }
    });
});