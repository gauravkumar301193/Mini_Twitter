function addInfoToLeftPanelProfile() {
    $(LEFT_PANEL_FOLLOWER_COUNT).html(getCurrentUserFollowerCount());
    if (getCurrentUserFollowingCount() == null) 
        console.log("here");
    $(LEFT_PANEL_FOLLOWING_COUNT).html(getCurrentUserFollowingCount());
    $(LEFT_PANEL_TWEET_COUNT).html(getCurrentUserTweetCount());
    
    $(LEFT_PANEL_USER_HANDLE).html("@" + getCurrentUserHandle());
    
    if (getCurrentUserMediaId()!= null) {
        $(LEFT_PANEL_PROFILE_PHOTO).attr("src", FETCH_IMAGE_GIVEN_USER_ID + "?userId=" + getCurrentUser());
        $(LEFT_PANEL_PROFILE_PHOTO).show();
    }
}

function addInfoToLeftPanelHashtag() {
    for(var i = 1; i < 6; i++) {
        $(LEFT_PANEL_HASHTAG_PREFIX + i).hide();
    } 
    $.ajax({
        url : FIVE_MOST_USED_HASHTAGS_URl,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log(result);
            var hashtags = result.hashtags;
            console.log("hashtag headings received successfully:");
            console.log(hashtags);
            for (var i = 0; i < hashtags.length; i++) {
                console.log("here");
                $(LEFT_PANEL_HASHTAG_PREFIX + (i+1)).html("#" + hashtags[i].word);
                $(LEFT_PANEL_HASHTAG_PREFIX + (i + 1)).show();
            }
        },
        error : function(e) {
        	redirectToLoginIfError(e);
            console.log("error loading hashtags on left panel: " + e);
        }
    });
}

function loadHashtagPage() {
    $(HASHTAG_TITLE).html(localStorage.getItem("hashtag"));
    addInfoToLeftPanelHashtag();
}

function showHashtagPage(hashtag) {
    localStorage.setItem("hashtag", hashtag);
    window.location.replace(PROFILE_CUM_HOME_PAGE);
}

function fetchAndUpdateMatchingResults(text, loggedInUser) {
	if (text[0] == "@")
		text = text.substr(1, text.length);
    $.ajax({
        url : IPAddress + "GetBestMatchingUserGivenPattern",
        type : "GET",
        data : {
            textPattern : text,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log(result);
            var x = result.Users;
            for (var i = 1; i <= x.length; i++) {
                $("#searchResult-" + i).html("@" + x[i - 1].handle);
                $("#searchResult-" + i).show();
            }
        },        
        error: function(e) {
        	redirectToLoginIfError(e);
        }
    });
}

function updateProfileInformation(emailId, password, username, userId) {
    $.ajax({
        url : UPDATE_PROFILE_INFORMATION,
        type : "POST",
        data : {
            emailId : emailId,
            password : password,
            username : username,
            userId : userId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("Information updated in database");
            if ($(IMAGE_ELEMENT_MODAL).val()) {
                uploadImageForUser(IMAGE_INFORMATION_URL_USER, IMAGE_FORM_MODAL, getLoggedInUser(), PROFILE_CUM_HOME_PAGE);
            } else {
            	window.location.replace(PROFILE_CUM_HOME_PAGE);
            }
        },
        error: function(e) {
        	redirectToLoginIfError(e);
        }
        
    });
}

function isJpg(filename) {
	return filename.match(/jpg$/i);
}

function isPng(filename) {
	return filename.match(/png$/i);
}

function isJpeg(filename) {
	console.log("here");
	return filename.match(/jpeg$/i);
}

function catchUser(doc, current) {
	if ((Math.abs(Math.round(doc * 0.30) - current) <= 5 
			|| Math.abs(Math.round(doc * 0.50) - current) <= 5
			|| Math.abs(Math.round(doc * 0.70) - current) <= 5
			|| Math.abs(Math.round(doc * 0.85) - current) <= 5
			||  doc == current) 
			&& REQUEST_FOR_TWEETS_SENT == false
			&& localStorage.getItem("moreOlderTweets") != false) {
		console.log("in here");
		return true;
	}
	return false;
}