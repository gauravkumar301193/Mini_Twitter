function postANewTweet(tweetText, userId, isMedia) {
    $(NEW_TWEET_TEXT).val("");
    $(NEW_TWEET_LETTER_COUNT).text("140 Character left");
    $.ajax({
        url : POST_A_NEW_TWEET_URL,
        type : "POST",
        data : {
            tweetText : tweetText,
            userId : userId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("tweet posted successfully");
            
            if ($(IMAGE_ELEMENT_TWEET).val()) {
                console.log("Uploading Image");
                uploadImageForTweet(IMAGE_INFORMATION_URL_TWEET, IMAGE_FORM_TWEET, result);
            } else {
                fetchTweetGivenTweetId(result, getLoggedInUser());
            }
        },
        error : function(e) {
            console.log("Couldn't post your tweet: " + e);
        }
    });
}

function postRetweet(tweetId, userId, authorId, handle) {
    $.ajax({
        url : RETWEET_URL,
        type : "POST",
        data : {
            tweetId : tweetId,
            loggedInUser : userId,
            loggedInUserHandle : handle,
            authorId : authorId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("tweet posted successfully");
            $("#retweet-" + tweetId + "-" + userId).prop("disabled", true);
        },
        error : function(e) {
            console.log("error occurred while posting tweet: " + e);
        }
    });
}

function likeTweet(tweetId, userId) {
    $.ajax({
        url : LIKE_TWEET_URL,
        type : "POST",
        data : {
            tweetId : tweetId,
            userId : userId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("tweet liked successfully");
            $("#like-" + tweetId + "-").val("Unlike");
        },
        error : function(e) {
            console.log("couldn't like tweet");
        }
    });
}

function unlikeTweet(tweetId, userId) {
    $.ajax({
        url : UNLIKE_TWEET_URL,
        type : "POST",
        data : {
            tweetId : tweetId,
            userId : userId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("tweet unliked successfully");
            $("#like-" + tweetId + "-").val("Like");
        },
        error : function(e) {
            console.log("couldn't unlike tweet");
        }
    });
}

function deleteTweet(tweetId) {
    $.ajax({
        url : DELETE_TWEET_URL,
        type : "POST",
        data : {
            tweetId : tweetId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("tweet deleted successfully");
        },
        error : function(e) {
            console.log("tweet delete unsuccessful: " + e);
        }
    });
}

$(document).ready(function() {
    $(MIDDLE_PANEL_TWEET_PARENT).click(function(e){
        var x = e.target.id + "";
        console.log(e.target.id);
        var elementId = x.split("-");
        console.log("here " + elementId[0]);
        if (elementId[0] == "profile") {
            fetchAndShowProfileOfCurrentUser(elementId[1], elementId[2], getLoggedInUser());
        } else if (elementId[0] == "profileImage") {
            $("#imageModal").show();
            $("#modal-image").attr("src", FETCH_IMAGE_GIVEN_USER_ID + "?userId=" + elementId[1]);
        } else if (elementId[0] == "delete") {
            deleteTweet(elementId[1]);
            decreaseTweetCount();
            $("#" + elementId[1]).html("");
        } else if (elementId[0] == "tweetImage") {
            console.log("tweetImage Clicked");
//            $(IMAGE_MODAL).show();
            $(IMAGE_IN_MODAL).attr("src", IMAGE_RETRIEVE_URL + "?mediaId=" + elementId[1]);
        } else if (elementId[0] == "like") {
            console.log($("#" + e.target.id).val());
            if ($("#" + e.target.id).val() == "Like") {
                $("#like-" + elementId[1] + "-").val("Unlike");               
                likeTweet(elementId[1], getLoggedInUser());
            } else {
                $("#like-" + elementId[1] + "-").val("Like");
                unlikeTweet(elementId[1], getLoggedInUser());
            }
        } else if (elementId[0] == "Retweet" && $("#" + e.target.id).val() == "Retweet") {
            postRetweet(elementId[1], elementId[2], getLoggedInUser(), getLoggedInUserHandle());
            increaseTweetCount();
            $("#" + e.target.id).val("Retweeted");
        } else if (elementId[0] == "hashtag") {
            localStorage.setItem("hashtag", elementId[1]);
            localStorage.setItem("pageFunction", "hashtag");
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        }
    });
    
    $(MIDDLE_PANEL_NEW_TWEET).click(function(e){
        if (("#" + e.target.id) == POST_A_NEW_TWEET_BUTTON) {
        	if ($(NEW_TWEET_TEXT).val().trim() == "") {
        		alert("Please enter some text first");
        		return;
        	}
            if (getLoggedInUser() != null && $(NEW_TWEET_TEXT).val().trim() != "") {
                if ($(IMAGE_ELEMENT_TWEET).val()) {
                    var file = $(IMAGE_ELEMENT_TWEET);
                    var filename = $.trim(file.val());
                	if (!(isJpg(filename) || (isPng(filename)))) {
                		alert("only Jpg and Png formats");
                		return;
                	}
                }
                increaseTweetCount();
                postANewTweet($(NEW_TWEET_TEXT).val().trim(), getLoggedInUser(), false);
            }
        }
    });
    count = 0;
    $(NEW_TWEET_LETTER_COUNT).html("140 Characters left");
    $(NEW_TWEET_TEXT).keypress(function(key) {
        count += 1;
        count = $(NEW_TWEET_TEXT).val().length;
        if (count > 140)
        	count = 140;
        $(NEW_TWEET_LETTER_COUNT).html((140 - count) + " Characters left");
    });
    
    $(NEW_TWEET_TEXT).keydown(function(e) {
        if (e.which == 8 || e.which == 46) {
            count--;
            if (count < 0)
                count = 0;
            if (count > 140)
                count = 140;
        }
        count = $(NEW_TWEET_TEXT).val().length;
        if (count > 140)
        	count = 140;
        $(NEW_TWEET_LETTER_COUNT).html((140 - count) + " Characters left");
    });
    
    $(NEW_TWEET_TEXT).keyup(function(e) {
        var text = $(NEW_TWEET_TEXT).val();
        if (text.length > 140) {
            text = text.substring(0, 140);
            count = 140;
        }
        $(NEW_TWEET_TEXT).val(text);
        count = $(NEW_TWEET_TEXT).val().length;
        if (count > 140)
        	count = 140;
        $(NEW_TWEET_LETTER_COUNT).html((140 - text.length) + " Characters left");
    });
    

});