function updateCurrentUserDetails(result) {
    localStorage.setItem("currentUser", result.userId);
    localStorage.setItem("currentUserHandle", result.handle);
    localStorage.setItem("currentUserMediaId", result.mediaId);
    localStorage.setItem("currentUserFollowingCount", result.followingCount);
    localStorage.setItem("currentUserFollowerCount", result.followerCount);
    localStorage.setItem("currentUserTweetCount", result.tweetCount);
    localStorage.setItem("isFollowed", result.isFollowing);
}

function updateLoggedInUserDetails(jsonObject) {
    localStorage.setItem("loggedInUser", jsonObject.userId);
    localStorage.setItem("loggedInUserHandle", jsonObject.handle);
    localStorage.setItem("loggedInMediaId", jsonObject.mediaId);
    localStorage.setItem("loggedInUserFollowingCount", jsonObject.followingCount);
    localStorage.setItem("loggedInUserFollowerCount", jsonObject.followerCount);
    localStorage.setItem("loggedInUserTweetCount", jsonObject.tweetCount);
}

function setCurrentEqualLoggedIn() {
    localStorage.setItem("currentUser", localStorage.getItem("loggedInUser"));
    localStorage.setItem("currentUserHandle", localStorage.getItem("loggedInUserHandle"));
    localStorage.setItem("currentUserMediaId", localStorage.getItem("loggedInUserMediaId"));
    localStorage.setItem("currentUserFollowerCount", localStorage.getItem("loggedInUserFollowerCount"));
    localStorage.setItem("currentUserTweetCount", localStorage.getItem("loggedInUserTweetCount"));
    localStorage.setItem("currentUserFollowingCount", localStorage.getItem("loggedInUserFollowingCount"));
}

function clearCurrentUserFromLocalStorage() {
    localStorage.removeItem("currentUser");
    localStorage.removeItem("currentUserHandle");
    localStorage.removeItem("mediaId");
    localStorage.removeItem("currentUserFollowerCount");
    localStorage.removeItem("currentUserTweetCount");
    localStorage.removeItem("currentUserFollowingCount");
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

function clearLocalStorage() {
    localStorage.clear();
}

function destroyTimeStamps() {
    localStorage.removeItem("startTime");
    localStorage.removeItem("latestTime");
    localStorage.removeItem("moreOlderTweets");
}
