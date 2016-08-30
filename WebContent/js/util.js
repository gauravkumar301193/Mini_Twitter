function getAndLoadImage(divisionId, mediaId, altImageAddr) {
    var imageURL = IMAGE_RETRIEVE_URL + "?image=" + mediaId;
    $("#" + divisionId).attr("src",imageURL);
	$("#" + divisionId).show();
}

function brokenTweetImage(image) {
    image.src = ALTERNATE_TWEET_IMAGE;
    image.onerror = "";
}

function brokenProfileImage(image) {
    image.src = ALTERNATE_PROFILE_IMAGE;
    image.onerror = "";
}

function changeButtonState(id, name, color) {
    $("#" + id).css("background-color", color);
    if (name != "") {
        $("#" + id).attr("name", name);
    }
}

function designTweetHTML(jsonData, tweetText) {
    var tweetBody = "<div class=\"row single-tweet\" id=" + jsonData.tweetId + ">";
    if(jsonData.isRetweet == true) {
        tweetBody += "<div class=\"row retweet-header\" name=\"retweetInformation\">" + 
            "<div class=\"col-xs-12\">" + 
                "<a id=\"profile#" + jsonData.retweetUserHandle + "#" + jsonData.retweetUserId + "\">@" +
                    jsonData.retweetUserHandle +
                "</a>" + 
                "<span>retweeted</span>" + 
            "</div>" + 
        "</div>"; 
    }
       tweetBody += "<div class=\"row\" name=\"tweetHeader\">" + 
            "<img class=\"col-xs-2 photo-thumbnail\" id=\"profileImage#" + jsonData.userId + "\" " + "onerror=\"brokenProfileImage(this)\">" + 
            "<div class=\"col-xs-9 tweet-handle-time\" name=\"handleTimeTweet\">" + 
                "<div class=\"row\"  name=\"handleTime\">" +
                    "<a id=\"profile#" + jsonData.userHandle + "#" + jsonData.userId + "\">@" +
                        jsonData.userHandle +
                    "</a>" + 
                    "<span class=\"tweet-time\" name=\"tweetTime\">" + jsonData.timestamp + "</span>" +
                "</div>" + 
                "<div class=\"row tweet-text\" name=\"tweetText\">" +
                    tweetText +
                "</div>" +
            "</div>" +
        "</div>" +
        "<div class=\"row\" name=\"tweetImageDivision\">" + 
            "<div class=\"col-xs-2\" name=\"emptyDivForImageAlignment\"></div>" +
            "<img id=\"tweetImage#" + jsonData.tweetId +
            "\" onerror=\"brokenTweetImage(this)\" class=\"col-xs-7 tweet-image\"/>" +
        "</div>" +
        "<div class=\"row tweet-footer\" name=\"tweetFooter\">" +
            "<div class=\"col-sm-6\" name=\"allButtonsForTweet\">" +
                "<div class=\"row\">" +
                    "<button class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"like#" + jsonData.tweetId + "#" + jsonData.userId +
                    "\">Like</button>" +
                    "<button class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"retweet#" + jsonData.tweetId + "#" + jsonData.userId +
                    "\">Retweet</button>" +
                    "<button class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"reply#" + jsonData.tweetId + "#" + jsonData.userHandle +
                    "\">Reply</button>" +
                "</div>" +
            "</div>" +
        "</div>" +
        "<div class=\"row\">" +
            "<span class=\"col-xs-1\"></span>" +
            "<hr class=\"col-xs-9\">" +
        "</div>" +
    "</div>";
    return tweetBody;
}

function convertTextToURL(text) {
    console.log("converting " + text);
    return aTag = "<a href=\"" + text + "\">" 
        + text + "</a>";
}

function convertTextToMentions(text) {
    return "<a id=\"profile#" + text.substr(1, text.length) + "#\">" + text + "</a>";
}

function convertTextToHashtags(text) {
    return "<a id=\"hashtag#" + text.substr(1, text.length) + "\">" + text + "</a>";
}

function parseTweetText(singleTweet) {
    console.log("Here in parsing");
    console.log(singleTweet);
    var mentions = singleTweet.mentions;
    var urls = singleTweet.urls;
    var hashtags = singleTweet.hashtags;
    var allWords = singleTweet.tweetText;
    var noSpaces = singleTweet.noSpaces;
    
    if (mentions != null) {
        for (var i = 0; i < mentions.length; i++) {
            console.log(mentions[i].index + " & " + allWords[mentions[i].index].word);
            allWords[mentions[i].index].word = convertTextToMentions(allWords[mentions[i].index].word);
        }
    }
    if (hashtags != null) {
        for (var i = 0; i < hashtags.length; i++) {
            allWords[hashtags[i].index].word = convertTextToHashtags(allWords[hashtags[i].index].word);
        }
    }
    if (urls != null) {
        for (var i = 0; i < urls.length; i++) {
            allWords[urls[i].index].word = convertTextToURL(allWords[urls[i].index].word);
        }
    }
    var generatedTweetText = "";
    for (var i = 0; i < allWords.length; i++) {
        generatedTweetText += " ";
        if (noSpaces != null && i != noSpaces.filter(function(value){ return value == i;})) {
            generatedTweetText = generatedTweetText.substr(0, generatedTweetText.length);
        }
        generatedTweetText += allWords[i].word;
    }
    return generatedTweetText;
}

function fetchTweetHtml(jsonObject) {
    var text = parseTweetText(jsonObject);
    var tweetHTMLContent = designTweetHTML(jsonObject, text);
    console.log("HTML Fetched");
    return tweetHTMLContent;
}

function addToDivStart(divId, tweetHTMLContent) {
    $("#"+divId).prepend(tweetHTMLContent);    
}
    
function addToDivEnd(divId, tweetHTMLContent) {
    console.log(tweetHTMLContent);
    $(divId).append(tweetHTMLContent);
    return;
}

function checkTweetButtons(jsonObject, currentUserId) {
    if (jsonObject.liked == true) {
        changeButtonState("like#" + jsonObject.tweetId + "#" + jsonObject.userId, "unlike", lightgray);
    }
    if (jsonObject.isRetweetedByUser == true) {
        changeButtonState("retweet#" + jsonObject.tweetId + "#" + jsonObject.userId, "retweeted", lightgray);
    }
}

function fetchOlderTweetsFromServer(url, userId, divId) {
    var date = new Date();
    var latestTime = date.getTime();
    var startTime = 0;
    if (localStorage.getItem("startTime") != null) {
        latestTime = localStorage.getItem("startTime");
        console.log("now getting startTime");
    }
    var data = {};
    data["loggedInUser"] = localStorage.getItem("loggedInUser");
    data["userId"] = userId;
    data["latestTime"] = latestTime;
    console.log(data);
    if (localStorage.getItem("moreOlderTweets") == null)
        var totalTweets = fetchTweetsFromServerBetweenTimestamps(url, userId, divId, 0, latestTime, false, data);
    if (totalTweets == 0) {
        localStorage.setItem("moreOlderTweets", "false");
    }
}

function fetchNewerTweetsFromServer(url, userId, divId) {
    var startTime = 0;
    var date = new Date();
    var latestTime = date.getTime();
    if (localStorage.getItem("latestTime") != null) {
        startTime = localStorage.getItem("latestTime");
    }
    var data = {};
    data["loggedInUser"] = localStorage.getItem("loggedInUser");
    data["userId"] = userId;
    data["startTime"] = startTime;
    console.log(data);
    localStorage.getItem("loggedInUser") + "}";
    localStorage.setItem("latestTime", latestTime);
    fetchTweetsFromServerBetweenTimestamps(url, userId, divId, 0, latestTime, true, jsonObject);   
}

function fetchTweetsFromServerBetweenTimestamps(url, userId, divId, startTime, latestTime, isNew, data) {
    var totalTweetsFetched = 0;
    var date = new Date();
    console.log("sending request");
    console.log(startTime + "  " + latestTime + " " + date.getTime());
    $.ajax({
        url : url,
        type : "GET",
        data : data,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success: function(result) {
            console.log("returned");
            console.log(result);
//            var jsonObject = $.parseJSON(result);
            var x = result;
            var jsonObject = x.Tweets;
            console.log(x);
            totalTweetsFetched = jsonObject.length;
            if (totalTweetsFetched == 0)
                return;
            if (jsonObject.length >= 1) {
                if (localStorage.getItem("startTime") == null)
                    localStorage.setItem("startTime", jsonObject[jsonObject.length - 1].timestamp);
                else if (localStorage.getItem("startTime") > jsonObject[jsonObject.length - 1].timestamp) 
                    localStorage.setItem("startTime", jsonObject[jsonObject.length - 1].timestamp);             
                if (localStorage.getItem("latestTime") == null)
                    localStorage.setItem("latestTime", jsonObject[0].timestamp);
            }
            if (isNew == false) {
                console.log("Tweets fetched and appending at end");
                parseAndAppendTweetsAtEnd(jsonObject, startTime, divId);
            }
            else {
                console.log("New Tweets fetched");
                parseAndAppendTweetsAtBeginning(jsonObject, startTime, divId);
            }
            fetchMediaForTweets(result);
        },
        error: function(e) {
            console.log("returned error");
            console.log(e);
        }
    });    
    return totalTweetsFetched;
}

function parseAndAppendTweetsAtEnd(jsonObject, startTime, divId) {
    console.log("appending at end function: ");
    console.log(jsonObject);
    for (var i = 0; i < jsonObject.length; i++) {
        var singleTweet = jsonObject[i];
        console.log("parsing tweet: ");
        console.log(singleTweet);
        var tweetHTML = fetchTweetHtml(singleTweet);
        console.log(i);
        addToDivEnd(divId, tweetHTML);
    }
    for (var i = 0; i < jsonObject.length; i++) {
        var singleTweet = jsonObject[i];
        var imgId = "#profileImage#" + singleTweet.userId;
        console.log("starting Image");
        $(imgId).attr("src", IMAGE_BY_USER_ID + "?userId=" + singleTweet.userId);
        $(imgId).show();
    }
}

function parseAndAppendTweetsAtBeginning(jsonObject, startTime, divId) {
    for (i = 0; i < jsonObject.length; i++) {
        var singleTweet = jsonObject[i];
        var tweetHTML = fetchTweetHtml(jsonObject);
        addToDivStart(divId, tweetHTML);
    }
}

function fetchMediaForTweets(jsonObject) {
    for (i = 0; i < jsonObject.length; i++) {
        $(imgId).show();
        var imgId = "#tweetImage#" + jsonObject.tweetId;
        var url = IMAGE_BY_TWEET_ID + "?tweetId=" + jsonObject.tweetId;
        $(imgId).attr("src", url);
    }
    for (i = 0; i < jsonObject.length; i++) {
        $(imgId).show();
        var imgId = "#profileImage#" + jsonObject[i].tweetId;
        var url = IMAGE_BY_USER_ID + "?userId=" + jsonObject.userId;
        $(imgId).attr("src", url);
    }
}

function fetchUserDetailsGivenIdAndChangePageToProfile(userId, userHandle) {
    console.log(USER_DETAILS_GIVEN_ID + " " + userHandle + " " + userId);
    $.ajax({
        url : USER_DETAILS_GIVEN_ID,
        type: "GET",
        data: {
            userId: userId,
            handle: userHandle,
            loggedInUser: localStorage.getItem("loggedInUser")
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success: function(result) {
            updateLocalStorage(result);
            localStorage.setItem("pageFunction", "profile");
            destroyTimeStamps();
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        },
        error: function(e) {
            console.log("Cannot go to the desired page");
        }
    });
}

function updateLocalStorage(result) {
    localStorage.setItem("currentUser", result.userId);
    localStorage.setItem("currentUserHandle", result.handle);
    localStorage.setItem("currentUserMediaId", result.mediaId);
    localStorage.setItem("currentUserFollowerCount", result.followerCount);
    localStorage.setItem("currentUserTweetCount", result.tweetCount);
}

function updateCurrentUserLocalStorage(jsonObject) {
    localStorage.setItem("currentUser", jsonObject.userId);
    localStorage.setItem("currentUserHandle", jsonObject.handle);
    localStorage.setItem("currentUserMediaId", jsonObject.mediaId);
    localStorage.setItem("currentUserFollowerCount", jsonObject.followerCount);
    localStorage.setItem("currentUserTweetCount", jsonObject.tweetCount);
}

function updateLoggedInUserDetails(jsonObject) {
    localStorage.setItem("loggedInUser", jsonObject.userId);
    localStorage.setItem("loggedInUserHandle", jsonObject.handle);
    localStorage.setItem("loggedInMediaId", jsonObject.mediaId);
    localStorage.setItem("loggedInUserFollowerCount", jsonObject.followerCount);
    localStorage.setItem("loggedInUserTweetCount", jsonObject.tweetCount);
}

function setCurrentEqualLoggedIn() {
    localStorage.setItem("currentUser", localStorage.getItem("loggedInUser"));
    localStorage.setItem("currentUserHandle", localStorage.getItem("loggedInUserHandle"));
    localStorage.setItem("currentUserMediaId", localStorage.getItem("loggedInUserMediaId"));
    localStorage.setItem("currentUserFollowerCount", localStorage.getItem("loggedInUserFollowerCount"));
    localStorage.setItem("currentUserTweetCount", localStorage.getItem("loggedInUserTweetCount"));
}

function clearCurrentUserFromLocalStorage() {
    localStorage.removeItem("currentUser");
    localStorage.removeItem("currentUserHandle");
    localStorage.removeItem("mediaId");
    localStorage.removeItem("currentUserFollowerCount");
    localStorage.removeItem("currentUserTweetCount");
}

function checkIfLoggedInUserSet() {
    if (localStorage.getItem("loggedInUser") == null) {
        return false;
    }
    if (localStorage.getItem("loggedInUserHandle") == null) {
        return false;
    }
    if (localStorage.getItem("loggedInUserMediaId") == null) {
        return false;
    }
    if (localStorage.getItem("loggedInUserFollowerCount") == null) {
        return false;
    }
    if (localStorage.getItem("loggedInUserTweetCount") == null) {
        return false;
    }
    return true;
}

function checkIfCurrentUserSet() {
    if (localStorage.getItem("currentUser") == null) {
        return false;
    }
    if (localStorage.getItem("currentUserHandle") == null) {
        return false;
    }
    if (localStorage.getItem("currentUserMediaId") == null) {
        return false;
    }
    if (localStorage.getItem("currentUserFollowerCount") == null) {
        return false;
    }
    if (localStorage.getItem("currentUserTweetCount") == null) {
        return false;
    }
    return true;    
}

function closeThisSession() {
    localStorage.clear();
    window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
}

function destroyTimeStamps() {
    localStorage.removeItem("startTime");
    localStorage.removeItem("latestTime");
    localStorage.removeItem("moreOlderTweets");
}

function requestServerToLogout() {
    $.ajax({
        url: LOGOUT_URL,
        type: "POST",
        success: function(result) {
            window.location.replace = LOGIN_AND_REGISTRATION_PAGE;
        },
        error: function(e) {
            console.log("error occured while logging out: " + e);
        }
    });
}

function postNewTweet(tweetText, userId, userHandle) {
    $.ajax({
        url: POST_A_NEW_TWEET_URL,
        type: "POST",
        data: {
            tweetText: tweetText,
            userId: userId,
            userHandle: userHandle
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },        
        success: function(result) {
            var tweetId = result.tweetId;
            $(TWEET_COUNT).val(parseInt($(TWEET_COUNT).val()) + 1 );
            if ($(IMAGE_UPLOAD_WITH_TWEET).get(0).files.length != 0) {
                uploadImage(UPLOAD_IMAGE_FOR_TWEET_URL, IMAGE_UPLOAD_WITH_TWEET, tweetId);
            }
            $(NEW_TWEET_TEXT).val("");
        }
    });
}

function uploadImage(url, divId, id) {
    
}

function generateFollowerElementHTML(jsonObject) {
    var followerHTML = "<div class=\"col-md-4 col-sm-6\" style=\"margin-top:30px\">" +
                        "<div class=\"row\">" +
                            "<img class=\"col-xs-5\" onerror=\"brokenProfileImage(this)\" id=\"profileImage#" +
                            jsonObject.userId + "\" " +
                            "style=\"max-width:70px\">" +
                            "<div class=\"col-xs-7\" align=\"center\">" +
                                "<a id=\"profile#" + jsonObject.userHandle + "#" + jsonObject.userId + 
                                    "\" style=\"font-size:15px;word-break:break-word\">@" + jsonObject.userHandle + 
                                "</a>" +
                            "</div>" +
                        "</div>" +
                        "<div class=\"row\" align=\"center\">" +
                            "<button class=\"btn-primary btn-md tweet-footer-button\" style=\"margin-top:10px;\" name=\"followThisUser\" id=\"follow#" + jsonObject.userId + ">Follow</button>" +
                        "</div>" + 
                        "<hr>" + 
                    "</div>";
    return followerHTML;
}

function getAllFollowers(userId) {
    $.ajax({
        url: GET_ALL_FOLLOWERS,
        type: "GET",
        data: {
            userId: userId,
            loggedInUser: localStorage.getItem("loggedInUser")
        },
        success: function(result) {
            var allFollowers = result.allFollowers;
            for (i = 0; i < allFollowers.length; i++) {
                var followerHtml = generateFollowerElementHTML(allFollowers[i]);
                $(MIDDLE_PANEL_FOLLOWERS).append(followerHtml);
            }
        },
        error: function(e) {
            console.log("couldn't fetch followers: " + e);
        }
    });
}

function follow(follower, following, elementId) {
    $.ajax({
        url: MAKE_CONNECTION_BETWEEN_USER_URL,
        type: "POST",
        data: {
            follower: follower,
            followung: following
        },
        success: function(result) {
            $(elementId).html("Unfollow");
        },
        error: function(e) {
            console.log("couldn't create a connection between users");
        }
    });
}