function convertTextToURL(text) {
    console.log("converting " + text);
    return aTag = "<a href=\"" + text + "\" target=\"blank\">" 
        + text + "</a>";
}

function designTweet(jsonObject, tweetText) {
        var tweet = "<div id=\"" + jsonObject.tweetId + "\">";
            
            if (jsonObject.isARetweet == "true") {
                tweet +=    "<div class=\"row\">" + 
                                "<div class=\"col-xs-12\">" + 
                                    "<a id=\"profile-" + jsonObject.retweetUserHandle + "-" + jsonObject.retweetUserId + "\">@" + jsonObject.retweetUserHandle + "</a>" + 
                                    "<span> Retweeted</span>" +
                                "</div>" +
                            "</div>"
            }

            tweet += "<div class=\"row tweet-header\" name=\"tweet-header\">" +    
             "<img class=\"col-xs-2 photo-thumbnail\" src='" + FETCH_IMAGE_GIVEN_USER_ID + "?userId=" + jsonObject.authorId + "' id=\"profileImage-" + jsonObject.authorId + "\"" + 
                " onerror=\"brokenProfileImage(this)\" data-toggle=\"modal\" data-target=\"#imageModal\">" + 
            "<div class=\"col-xs-9 tweet-handle-time\" name=\"handleTweetTime\">" +
                "<div class=\"row\" name=\"handleTime\">" + 
                    "<div class=\"col-xs-10\"> " +
                        "<a id=\"profile-" + jsonObject.authorHandle + "-" + jsonObject.authorId + "\">@" + jsonObject.authorHandle + "</a>" + 
                        "<span class=\"tweet-time\" name=\"tweetTime\">" + getTimeDifference(jsonObject.timestamp) + "</span>" +
                    "<hr></div>";
        console.log("for delete button" + getLoggedInUser() + "   " + jsonObject.authorId);
        if (getLoggedInUser() == jsonObject.authorId) {
                    tweet += "<div class=\"col-xs-1\">" +
                            "<button type=\"button\" class=\"close\" id=\"delete-" + jsonObject.tweetId + "\">&times;</button>" +
                        "</div>";
        }
               tweet += "</div>" +
                "<div class=\"row tweet-text\" name=\"tweetText\">" + tweetText +
                "</div>" +
            "</div>" +
        "</div>" +
        "<div class=\"row\" name=\"tweetImageDivision\">" +
            "<div class=\"col-xs-2\" name=\"emptyDivForImageAlignment\"></div>";
        if (jsonObject.mediaId != 0) {
            tweet += "<img class=\"col-xs-7 tweet-image\" id=\"tweetImage-" + jsonObject.mediaId +
             "\" onerror=\"brokenTweetImage(this)\" src=\"" + IMAGE_RETRIEVE_URL + "?mediaId=" + jsonObject.mediaId +
        "\" data-toggle=\"modal\" data-target=\"#imageModal\">"; 
        }
        tweet += "</div>" +
        "<div class=\"row tweet-footer\" name=\"tweetFooter\">" +
            "<div class=\"col-sm-6\" name=\"allButtonsForTweet\">" +
                "<div class=\"row\">" + 
                    "<input type=\"button\" class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"like-" + jsonObject.tweetId +"-" + "\" value=\"Like\">";
                    if(jsonObject.authorId != getLoggedInUser()) { 
                    	tweet += "<input type=\"button\" style=\"width:100px;\" class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"Retweet-" + jsonObject.tweetId + "-" + jsonObject.authorId 
                    	+ "\" value=\"Retweet\">";
                    }
                    
               tweet += "</div>" +
            "</div>" +
        "</div>" +
        "<div class=\"row\" name=\"line-break-after-tweet\">" +
            "<span class=\"col-xs-1\"></span>" +
            "<hr class=\"col-xs-9\">" + 
        "</div></div>";
    return tweet;
}

function convertTextToMentions(text) {
    return "<a id=\"profile-" + text.substr(1, text.length) + "-\">" + text + "</a>";
}

function convertTextToHashtags(text) {
    return "<a id=\"hashtag-" + text.substr(1, text.length) + "\">" + text + "</a>";
}

function parseTweetText(singleTweet) {
    console.log("Here in parsing");
    console.log(singleTweet);
    var mentions = singleTweet.mentions;
    var urls = singleTweet.urls;
    var hashtags = singleTweet.hashtags;
    var allWords = singleTweet.allWords;
    var noSpaces = singleTweet.noSpaces;
    
    if (mentions != null) {
        for (var i = 0; i < mentions.length; i++) {
            console.log(mentions[i].index + "  " + i);
            console.log
            console.log(allWords[mentions[i].index].word);
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

function changeButtonStatesForTweet(jsonObject, loggedInUser) {
	console.log("In changing button State");
    var likeButton = "#like-" + jsonObject.tweetId +"-";
    var retweetButton = "#Retweet-" + jsonObject.tweetId + "-" + getLoggedInUser();
    if (jsonObject.isLikedByLoggedInUser) {
        $(likeButton).val("Unlike");
    }
    console.log(jsonObject.retweetUserId + "  " + loggedInUser);
    if (jsonObject.retweetUserId == loggedInUser || jsonObject.userId == getCurrentUser()) {
    	console.log("they are equal");
        $(retweetButton).val("Retweeted");
    }
}

function parseJSONOfAllTweets(jsonObject, isNew) {
    var tweetsArray = [];
    for (var i = 0; i < jsonObject.length; i++) {
        var singleTweet = jsonObject[i];
        if (checkIfTweetNeeded(isNew, singleTweet) == false)
        	continue;
        var tweetText = parseTweetText(singleTweet);
        var tweetHTML = designTweet(singleTweet, tweetText);
        tweetsArray.push(tweetHTML);
    }
    return tweetsArray;
}

function checkIfTweetNeeded(isNew, singleTweet) {
    if (localStorage.getItem("pageFunction") == null) {
    	clearLocalStorage();
		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
	}
    var pageFunction = localStorage.getItem("pageFunction"); 
    if (isNew == true && getLoggedInUser() == getCurrentUser()) {
    	if ( (pageFunction == "home" || pageFunction == "profile")
    		&& singleTweet.authorId == getCurrentUser() && singleTweet.isARetweet == "false") {
    		return false;
    	} else if (pageFunction == "tweets") 
    		return false;
    }
    return true;
} 

function getTimeDifference(timestamp) {
    var diff = Date.now() - timestamp;
    var seconds = Math.floor(diff/1000);
    var minutes = Math.floor(diff/1000/60);
    var hours = Math.floor(diff/1000/60/60);
    var days = Math.floor(diff/1000/60/60 / 24);
    var months = Math.floor(diff/1000/60/60 / 24/ 30);
    var years = Math.floor(diff/1000/60/60 / 24/ 30/ 12);

    if (seconds < 60) {
    	if (seconds < 0)
    		return "0 seconds ago";
        return seconds + trimTime(seconds, " seconds") +  " ago";
    } else if (minutes < 60) {
        return minutes + trimTime(minutes, " minutes") + " ago";
    } else if (hours < 24) {
        return hours + trimTime(hours, " hours") + " ago";
    } else if (days < 30){
        return days + trimTime(days, " days") + " ago";
    } else if (months < 12) {
    	return months + trimTime(months, " months") + " ago";
    } else {
        return years + trimTime(years, " years") + " ago";
    }
}

function trimTime(time, stringTime) {
	if (time == 1)
		return stringTime.substr(0, stringTime.length-1);
	return stringTime;
}

function increaseTweetCount() {
    if (getCurrentUser() == getLoggedInUser()) {
        var count = $(LEFT_PANEL_TWEET_COUNT).html();
        count++;
        $(LEFT_PANEL_TWEET_COUNT).html(count);
        var x = getCurrentUserTweetCount();
        x++;
        localStorage.setItem("currentUserTweetCount", x);
    }
    var y = getLoggedInUserTweetCount();
    y++;
    localStorage.setItem("loggedInUserTweetCount", y);
}

function decreaseTweetCount() {
    if (getCurrentUser() == getLoggedInUser()) {
        var count = $(LEFT_PANEL_TWEET_COUNT).html();
        count--;
        $(LEFT_PANEL_TWEET_COUNT).html(count);                
        var y = getCurrentUserTweetCount();
        y--;
        localStorage.setItem("currentUserTweetCount", y);
    }
    var x = getLoggedInUserTweetCount();
    x--;
    localStorage.setItem("loggedInUserTweetCount", x);
}
