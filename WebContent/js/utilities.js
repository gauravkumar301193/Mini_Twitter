function addInfoToLeftPanelProfile() {
    $(LEFT_PANEL_FOLLOWER_COUNT).html(localStorage.getItem("currentUserFollowerCount"));
    if (localStorage.getItem("currentUserFollowingCount") == null) 
        console.log("here");
    $(LEFT_PANEL_FOLLOWING_COUNT).html(localStorage.getItem("currentUserFollowingCount"));
    $(LEFT_PANEL_TWEET_COUNT).html(localStorage.getItem("currentUserTweetCount"));
    
    $(LEFT_PANEL_USER_HANDLE).html("@" + localStorage.getItem("currentUserHandle"));
    
    if (localStorage.getItem("currentUserMediaId")!= null) {
        $(LEFT_PANEL_PROFILE_PHOTO).attr("src", IMAGE_RETRIEVE_URL + "?mediaId=" + localStorage.getItem("currentUser"));
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
        }
    });
}