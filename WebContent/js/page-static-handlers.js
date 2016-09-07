$(document).ready(function() {
    if (localStorage.getItem("pageFunction") == null) {
    	localStorage.setItem("pageFunction", "home");
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
        $(LEFT_PANELS_CONTAINER).addClass("hidden-xs");
        setCurrentEqualLoggedIn();
        $(NEW_TWEET_TEXT).val("");
        url = TWEETS_FOR_USER_HOME_URL;
        homePage();
        addInfoToLeftPanelProfile();
        userId = getCurrentUser();
    } else if (pageRole == "profile") {
        destroyTimeStamps();
        if (!checkIfCurrentUserSet() && !checkIfLoggedInUserSet()) {
            clearLocalStorage();
            window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
        }
        url = TWEETS_FOR_USER_PROFILE;
        profilePage();
        addInfoToLeftPanelProfile();
        userId = getCurrentUser();
        if (getLoggedInUser() == getCurrentUser()) {
        	$(NEW_TWEET_TEXT).val("");
        } else {
        	$(NEW_TWEET_TEXT).val("@" + getCurrentUserHandle() + " ");
        }
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
        userId = getCurrentUser();
        if (getLoggedInUser() == getCurrentUser()) {
        	$(NEW_TWEET_TEXT).val("");
        } else {
        	$(NEW_TWEET_TEXT).val("@" + getCurrentUserHandle() + " ");
        }
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
        fetchTweetGivenTweetId(localStorage.getItem("singleTweet"), getLoggedInUser());
    }
    localStorage.setItem("sent", true);
    fetchOlderTweetsFromServer(url, userId, getLoggedInUser());
    
    var fetchNewTweetsInterval = null;
    setTimeout(function() {
    	fetchNewTweetsInterval = setInterval(function() {
    		if (REQUEST_FOR_TWEETS_SENT == false) {
    			REQUEST_FOR_TWEETS_SENT = true;
    			fetchNewTweetsFromServer(url, userId, getLoggedInUser());
    		}
    	}, 30000);
    }, 30000);
    
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
            clearInterval(fetchNewTweetsInterval);
            fetchAllFollowers(getCurrentUser(), getLoggedInUser());
        } else if (id == following.substr(1, following.length)) {
            followersPage();
            clearInterval(fetchNewTweetsInterval);
            fetchAllFollowing(getCurrentUser(), getLoggedInUser());
        } else if (id == photo.substr(1, photo.length)) {
            $(IMAGE_IN_MODAL).attr("src", FETCH_IMAGE_GIVEN_USER_ID + "?userId=" + getCurrentUser());
        } else if (id == handle.substr(1, handle.length)) {
            localStorage.setItem("pageFunction", "profile");
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
                userId : getLoggedInUser()
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
            	redirectToLoginIfError(e);
                console.log("error while logging out: " + e);
            }
        });
    });
    
    $(window).scroll(function(){
        var doc = $(document).height() - $(window).height();
        var current = $(document).scrollTop();
        console.log("height " + doc + "  " + current + "  " + Math.round(doc * 0.7));
        if (catchUser(doc, current) == true) {
            console.log("Requesting For more tweets");
            REQUEST_FOR_TWEETS_SENT = true;
            fetchOlderTweetsFromServer(url, userId, getLoggedInUser());
        }
    });
    
    $(UPDATE_PROFILE_BUTTON).click(function() {
        $(UPDATE_PROFILE_MODAL).show();
        console.log("here");
    })
    
    $(UPDATE_PROFILE_SUBMIT).click(function() {
        var emailId = $(UPDATE_PROFILE_EMAIL_ID).val();
        var password ="";
        if ($(UPDATE_PROFILE_PASSWORD).val() != "") {
            password = MD5($(UPDATE_PROFILE_PASSWORD).val());
        }
        var username = $(UPDATE_PROFILE_USERNAME).val();
        if ($(IMAGE_ELEMENT_MODAL).val()) {
            var file = $(IMAGE_ELEMENT_MODAL);
            var filename = $.trim(file.val());
        	if (!(isJpg(filename) || !(isPng(filename) || !(isJpeg(filename))))) {
        		alert("only Jpg, Jpeg and Png formats");
        		return;
        	}
        }
        console.log("pass = " + password + " user=" + username + " email=" + emailId);
        updateProfileInformation(emailId, password, username, getLoggedInUser());
    });
    
    $("#search-nav").click(function(){
        console.log("here");
        $("#searchModal").css("display", "block");
    });
    
    $("#searchText").keyup(function(){
        fetchAndUpdateMatchingResults($("#searchText").val(), getLoggedInUser());
    });
    
    $(LEFT_PANEL_FOLLOW_BUTTON).click(function(){
        var val = $(LEFT_PANEL_FOLLOW_BUTTON).val();
        var x = $(LEFT_PANEL_FOLLOWER_COUNT).html();
        var y = getLoggedInUserFollowingCount();
        if (val == "Follow") {
            followAUser(getCurrentUser(), getLoggedInUser());
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Unfollow");
            x++;
            y++;
        } else {
            unfollowAUser(getCurrentUser(), getLoggedInUser());
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
        fetchAndShowProfileOfCurrentUser(x, "", getLoggedInUser());
    });
    
    $(NOTIFICATION_BUTTON).click(function(){
        console.log("here button pressed");
//        $("#notificationModal").css("display", "block");
        fetchAndDisplayNotifications(getLoggedInUser());
    });
    
    $(NOTIFICATION_MODAL).click(function(e) {
        var elementId = e.target.id.split("-");
        console.log("here " + e.target.id);
        if (elementId[0] == "profile") {
            fetchAndShowProfileOfCurrentUser("", elementId[1], getLoggedInUser());
        } else if (elementId[0] == "tweet" || elementId[1] == "tweet") {
            console.log("in tweet");
            localStorage.setItem("singleTweet", elementId[1]);
            destroyTimeStamps();
            localStorage.setItem("pageFunction", "singleTweet");
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        }
    });
    
    var previousEmailValue = "";
    $(UPDATE_PROFILE_EMAIL_ID).focusout(function() {
        console.log("here");
        var emailEntered = $(UPDATE_PROFILE_EMAIL_ID).val();
        
        if (previousEmailValue != emailEntered) {
            previousEmailValue = emailEntered;
            console.log(emailEntered);
            $.ajax({
                url : EMAIL_VERIFICATION_URL,
                type : "GET",
                data : {
                  emailId : emailEntered  
                },
                crossOrigin: true,
                xhrFields: { 
                    withCredentials: false 
                },
                success : function(result) {
                    if (result != "true") {
                        $(EMAIL_EXISTS_ERROR).hide();
                        console.log("new Email");
                    } else {
                        $(EMAIL_EXISTS_ERROR).show();
                        console.log("false");
                    }
                },
                error : function(e) {
                	redirectToLoginIfError(e);
                    $(EMAIL_EXISTS_ERROR).show();
                }
            });
        }
    });

});
