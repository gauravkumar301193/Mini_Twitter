function parseMentionNotification(tweetId, handle) {
    var element = "<a style=\"margin-top:3px;\" id=\"tweet-" + tweetId + "\">@" + handle + " mentioned you in a tweet</a><hr>"; 
    return element;
}

function parseFollowersNotifications(userId, handle) {
    var element = "<a style=\"margin-top:3px;\" id=\"profile-" + userId + "\">@" + handle + " followed you</a><hr>";
    return element;
}

function parseRetweetsNotification(userId, tweetId, handle) {
    var element = "<a style=\"margin-top:3px;\" id=\"tweet-" + tweetId + "\">@" + handle + " retweeted your tweet.</a><hr>"; 
    return element;
}

function fetchAndDisplayNotifications(userId) {
    $.ajax({
        url : FETCH_NOTIFICATIONS_URL,
        type: "GET",
        data: {
            userId : userId
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success: function(result) {
            var followers = result.followers;
            var mentions = result.mentions;
            var retweets = result.retweets;
            for (var i = 0; i < followers.length; i++) {
                var element = parseFollowersNotifications(followers[i].user_id, followers[i].handle);
                $(NOTIFICATIONS_MODAL_PARENT).append(element);
            }
            for (var i = 0; i < mentions.length; i++) {
                var element = parseMentionNotification(mentions[i].tweet_id, mentions[i].handle);
                $(NOTIFICATIONS_MODAL_PARENT).append(element);
            }
            for (var i = 0; i < retweets.length; i++) {
                var element = parseRetweetsNotification(retweets[i].user_id, retweets[i].tweet_id, retweets[i].handle);
                $(NOTIFICATIONS_MODAL_PARENT).append(element);
            }
        }, 
        error: function(e) {
        	redirectToLoginIfError(e);
        }
    });
}

