function convertTextToURL(text) {
    console.log("converting " + text);
    return aTag = "<a href=\"" + text + "\">" 
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
             "<img class=\"col-xs-2 photo-thumbnail\" src='" + IMAGE_RETRIEVE_URL + "?mediaId=" + jsonObject.authorId + "' id=\"profileImage-" + jsonObject.authorId + "\"" + "style=\"\" onerror=\"brokenProfileImage(this)\">" + 
            "<div class=\"col-xs-9 tweet-handle-time\" name=\"handleTweetTime\">" +
                "<div class=\"row\" name=\"handleTime\">" + 
                    "<div class=\"col-xs-10\"> " +
                        "<a id=\"profile-" + jsonObject.authorHandle + "-" + jsonObject.authorId + "\">@" + jsonObject.authorHandle + "</a>" + 
                        "<span class=\"tweet-time\" name=\"tweetTime\">" + getTimeDifference(jsonObject.timestamp) + "</span>" +
                    "<hr></div>";
        console.log("for delete button" + localStorage.getItem("loggedInUser") + "   " + jsonObject.authorId);
        if (localStorage.getItem("loggedInUser") == jsonObject.authorId) {
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
             "onerror=\"brokenTweetImage(this)\" src=\"" + IMAGE_RETRIEVE_URL + "?mediaId=" + jsonObject.mediaId +
        "\">"; 
        }
        tweet += "</div>" +
        "<div class=\"row tweet-footer\" name=\"tweetFooter\">" +
            "<div class=\"col-sm-6\" name=\"allButtonsForTweet\">" +
                "<div class=\"row\">" + 
                    "<input type=\"button\" class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"like-" + jsonObject.tweetId +"-" + "\" value=\"Like\">" +
                    "<input type=\"button\" class=\"col-sm-4 btn-primary btn-md tweet-footer-button\" id=\"Retweet-" + jsonObject.tweetId + "-" + localStorage.getItem("loggedInUser") 
 + "\" value=\"Retweet\">" +
                "</div>" +
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
    var likeButton = "#like-" + jsonObject.tweetId +"-";
    var retweetButton = "#retweet-" + jsonObject.tweetId + "-";
    
    if (jsonObject.isLikedByLoggedInUser) {
        $(likeButton).val("Unlike");
    }
    if (jsonObject.retweetUserId == loggedInUser) {
        $(retweetButton).prop('disabled', true);
    }
}

function parseJSONOfAllTweets(jsonObject) {
    var tweetsArray = [];
    for (var i = 0; i < jsonObject.length; i++) {
        var singleTweet = jsonObject[i];
        var tweetText = parseTweetText(singleTweet);
        var tweetHTML = designTweet(singleTweet, tweetText);
        tweetsArray.push(tweetHTML);
    }
    return tweetsArray;
}

function getTimeDifference(timestamp) {
    var diff = Date.now() - timestamp;
    if (Math.round(diff/1000) < 60) {
        return Math.round(diff/1000) + " seconds ago";
    } else if (Math.round(diff/1000/60) < 60) {
        return Math.round(diff/1000/60) + " minutes ago";
    } else if (Math.round(diff/1000/60/60) < 24) {
        return Math.round(diff/1000/60/60) + " hours ago";
    } else if (Math.round(diff/1000/60/60) < 366){
        return Math.round(diff/1000/60/60/24) + " days ago";
    } else {
        return Math.round(diff/1000/60/60/24/365) + " years ago"
    }
}

function increaseTweetCount() {
    if (localStorage.getItem("currentUser") == localStorage.getItem("loggedInUser")) {
        var count = $(LEFT_PANEL_TWEET_COUNT).html();
        count++;
        $(LEFT_PANEL_TWEET_COUNT).html(count);                
    }
    var x = localStorage.getItem("loggedInUserTweetCount");
    x++;
    localStorage.setItem("loggedInUserTweetCount", x);
}

function decreaseTweetCount() {
    if (localStorage.getItem("currentUser") == localStorage.getItem("loggedInUser")) {
        var count = $(LEFT_PANEL_TWEET_COUNT).html();
        count--;
        $(LEFT_PANEL_TWEET_COUNT).html(count);                
    }
    var x = localStorage.getItem("loggedInUserTweetCount");
    x--;
    localStorage.setItem("loggedInUserTweetCount", x);
}
