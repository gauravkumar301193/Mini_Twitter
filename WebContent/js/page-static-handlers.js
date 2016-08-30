$(document).ready(function() {
    if (localStorage.getItem("pageFunction") == null) {
        clearLocalStorage();
        window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
    }
    var pageRole = localStorage.getItem("pageFunction");
    var url = "";
    var userId = "";
    if (pageRole == "home") {
        destroyTimeStamps();
        if (checkIfLoggedInUserSet() == false) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        setCurrentEqualLoggedIn();
        url = TWEETS_FOR_USER_HOME_URL;
        homePage();
        addInfoToLeftPanelProfile();
        userId = localStorage.getItem("currentUser");
    } else if (pageRole == "profile") {
        destroyTimeStamps();
        if (!checkIfCurrentUserSet() && !checkIfLoggedInUserSet()) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        url = TWEETS_FOR_USER_PROFILE;
        profilePage();
        addInfoToLeftPanelProfile();
        userId = localStorage.getItem("currentUser");
        if (localStorage.getItem("isFollowed") == true) {
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Unfollow");
        } else if (localStorage.getItem("isFollowed") == false) {
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Follow");
        }
    } else if (pageRole == "hashtag") {
        destroyTimeStamps();
        if (localStorage.getItem("hashtag") == null) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        userId = localStorage.getItem("hashtag");
        hashtagPage();
        url = TWEETS_FOR_A_HASHTAG;
        addInfoToLeftPanelHashtag();
        $(HASHTAG_HEADING_PANEL_TEXT).html("#" + userId);
    } else if (pageRole == "tweets") {
        destroyTimeStamps();
        if (!checkIfLoggedInUserSet() && !checkIfCurrentUserSet()) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        userId = localStorage.getItem("currentUser");
        url = TWEETS_BY_A_SPECIFIC_USER;
        profilePage();
        addInfoToLeftPanelProfile();
    } else if (pageRole == "singleTweet") {
        destroyTimeStamps();
        if (checkIfLoggedInUserSet() == false) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        setCurrentEqualLoggedIn();
        url = "";
        homePage();
        addInfoToLeftPanelProfile();
        fetchTweetGivenTweetId(localStorage.getItem("singleTweet"), localStorage.getItem("loggedInUser"));
    }
    localStorage.setItem("sent", true);
    fetchOlderTweetsFromServer(url, userId, localStorage.getItem("loggedInUser"));
    
    $(LEFT_PANEL_PROFILE_DETAILS).click(function(e) {
        var id = e.target.id;
        var photo = LEFT_PANEL_PROFILE_PHOTO;
        var handle = LEFT_PANEL_USER_HANDLE;
        var tweets = LEFT_PANEL_TWEET_HEADING;
        var followers = LEFT_PANEL_FOLLOWER_HEADING;
        var following = LEFT_PANEL_FOLLOWING_HEADING;
        console.log(following.substr(1, following.length));
        if (id == tweets.substr(1, tweets.length)) {
            localStorage.setItem("pageFunction", "tweets");
            destroyTimeStamps();
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        } else if (id == followers.substr(1, followers.length)) {
            followersPage();
            fetchAllFollowers(localStorage.getItem("currentUser"), localStorage.getItem("loggedInUser"));
        } else if (id == following.substr(1, following.length)) {
            followersPage();
            fetchAllFollowing(localStorage.getItem("currentUser"), localStorage.getItem("loggedInUser"));
        } else if (id == photo.substr(1, photo.length)) {
            $("#imageModal").show();
//            $("#imageModal").css("display", "block");
            $("#modal-image").attr("src", IMAGE_RETRIEVE_URL + "?mediaId=" + localStorage.getItem("currentUser") + "&type=user");
        } else if (id == handle.substr(1, handle.length)) {
            localStorage.setItem("pageFunction", "profile");
            setCurrentEqualLoggedIn();
            destroyTimeStamps();
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        }
    });
    
    $(LEFT_PANEL_HASHTAGS).click(function(e) {
        var x = $("#" + e.target.id).html();
        console.log(x);
        localStorage.setItem("pageFunction", "hashtag");
        localStorage.setItem("hashtag", x.substr(1, x.length));
        destroyTimeStamps();
        window.location.replace(PROFILE_CUM_HOME_PAGE);
    });
    
    $(HOME_BUTTON).click(function() {
        setCurrentEqualLoggedIn();
        localStorage.setItem("pageFunction", "home");
        destroyTimeStamps();
        window.location.replace(PROFILE_CUM_HOME_PAGE);
    });
    
    $(PROFILE_BUTTON).click(function() {
        setCurrentEqualLoggedIn();
        localStorage.setItem("pageFunction", "profile");
        destroyTimeStamps();
        window.location.replace(PROFILE_CUM_HOME_PAGE);
    });
    
    $(LOGOUT_BUTTON).click(function() {
        $.ajax({
            url : LOGOUT_URL,
            type : "POST",
            data: {
                userId : localStorage.getItem("loggedInUser")
            },
            crossOrigin: true,
            xhrFields: { 
                withCredentials: false 
            },
            success : function(result) {
                console.log("logged out successfully");
                clearLocalStorage();
                window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
            },
            error : function(e) {
                console.log("error while logging out: " + e);
            }
        });
    });
    
    $(window).scroll(function(){
        var doc = $(document).height() - $(window).height();
        console.log("herer 1 ");
        var current = $(document).scrollTop();
        console.log("height " + doc + "  " + current + "  " + Math.round(doc * 0.7));
        if (current == Math.round(doc * 0.7) && localStorage.getItem("sent") == null) {
            console.log("here");
            localStorage.setItem("sent", true);
            fetchOlderTweetsFromServer(url, userId, localStorage.getItem("loggedInUser"));
        }
    });
    
    $(UPDATE_PROFILE_BUTTON).click(function() {
        // open modal to update the information;
    })
    
    $("#search-nav").click(function(){
        console.log("here");
        $("#searchModal").css("display", "block");
    });
    
    $("#searchText").keyup(function(){
        fetchAndUpdateMatchingResults($("#searchText").val(), localStorage.getItem("loggedInUser"));
    });
    
    $(LEFT_PANEL_FOLLOW_BUTTON).click(function(){
        var val = $(LEFT_PANEL_FOLLOW_BUTTON).val();
        var x = $(LEFT_PANEL_FOLLOWER_COUNT).html();
        var y = localStorage.getItem("loggedInUserFollowingCount");
        if (val == "Follow") {
            followAUser(localStorage.getItem("currentUser"), localStorage.getItem("loggedInUser"));
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Unfollow");
            x++;
            y++;
        } else {
            unfollowAUser(localStorage.getItem("currentUser"), localStorage.getItem("loggedInUser"));
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Follow");
            x--;
            y--;
        }
        $(LEFT_PANEL_FOLLOWER_COUNT).html(x);
        localStorage.setItem("loggedInUserFollowingCount", y);
        localStorage.setItem("currentUserFollowerCount", x);
    });
    
    $(MODAL_SEARCHES).click(function(e){
        var x = $("#" + e.target.id).text();
        x = x.substr(1, x.length);
        fetchAndShowProfileOfCurrentUser(x, "", localStorage.getItem("loggedInUser"));
    });
    
    $(NOTIFICATION_BUTTON).click(function(){
        console.log("here button pressed");
        $("#notificationModal").css("display", "block");
        fetchAndDisplayNotifications(localStorage.getItem("loggedInUser"));
    });
    
    $(NOTIFICATION_MODAL).click(function(e) {
        var elementId = e.target.id.split("-");
        console.log("here " + e.target.id);
        if (elementId[0] == "profile") {
            fetchAndShowProfileOfCurrentUser("", elementId[1], localStorage.getItem("loggedInUser"));
        } else if (elementId[0] == "tweet" || elementId[1] == "tweet") {
            console.log("in tweet");
            localStorage.setItem("singleTweet", elementId[1]);
            destroyTimeStamps();
            localStorage.setItem("pageFunction", "singleTweet");
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        }
    });

});
