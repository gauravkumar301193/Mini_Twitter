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
    localStorage.setItem("currentUser", getLoggedInUser());
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
    if (getLoggedInUser() == null) {
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

function getLoggedInUser() {
//	if (localStorage.getItem("loggedInUser") == null) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
//	}
	return localStorage.getItem("loggedInUser");
}

function getLoggedInUserHandle() {
//	if (localStorage.getItem("loggedInUserHandle") == nul) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("loggedInUserHandle");
}

function getLoggedInMediaId() {
//	if (localStorage.getItem("loggedInMediaId") == null) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("loggedInMediaId");
}

function getLoggedInUserFollowingCount() {
//	if (localStorage.getItem("loggedInUserFollowingCount") == null) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("loggedInUserFollowingCount");
}

function getLoggedInUserFollowerCount() {
//	if (localStorage.getItem("loggedInUserFollowerCount") == null) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("loggedInUserFollowerCount");
}

function getLoggedInUserTweetCount() {
//	if (localStorage.getItem("loggedInUserTweetCount") == null) {
//		clearLocalStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("loggedInUserTweetCount");
}


function getCurrentUser() {
//	if (localStorage.getItem("currentUser") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUser");
}

function getCurrentUserHandle() {
//	if (localStorage.getItem("currentUserHandle") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUserHandle");
}

function getCurrentUserMediaId() {
//	if (localStorage.getItem("currentUserMediaId") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUserMediaId");
}

function getCurrentUserFollowingCount() {
//	if (localStorage.getItem("currentUserFollowingCount") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUserFollowingCount");
}

function getCurrentUserFollowerCount() {
//	if (localStorage.getItem("currentUserFollowerCount") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUserFollowerCount");
}

function getCurrentUserTweetCount() {
//	if (localStorage.getItem("currentUserTweetCount") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("currentUserTweetCount");
}

function checkIfCurrentUserIsFollowed() {
//	if (localStorage.getItem("isFollowed") == null) {
//		clearLocasStorage();
//		window.location.replace(LOGIN_AND_REGISTRATION_PAGE);		
//	}
	return localStorage.getItem("isFollowed");
}