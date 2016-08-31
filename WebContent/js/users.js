function fetchAndShowProfileOfCurrentUser(handle, userId, loggedInUser) {
    $.ajax({
        url : USER_DETAILS_GIVEN_ID,
        data : {
            handle : handle,
            userId : userId,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("info fetched");
            updateCurrentUserDetails(result);
            destroyTimeStamps();
            localStorage.setItem("pageFunction", "profile");
            if (result.isFollowing == true) {
                localStorage.setItem("followState", "unfollow");
            } else {
                localStorage.setItem("followState", "follow");
            }
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        },
        error : function(e) {
            console.log("user details could not be fetched: " + e);
        }
    });
}

function fetchAndUpdateInfoOfLoggedInUser(userId, loggedInUser) {
    $.ajax({
        url : USER_DETAILS_GIVEN_ID,
        data : {
            userId : userId,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("info fetched");
            setCurrentEqualLoggedIn();
            destroyTimeStamps();
            localStorage.setItem("pageFunction", "profile");
            window.location.replace(PROFILE_CUM_HOME_PAGE);
        },
        error : function(e) {
            console.log("user details could not be fetched: " + e);
        }        
    });
}

function fetchAllUsers(url, userId, loggedInUser) {
    $.ajax({
        url : url,
        data : {
            userId : userId,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            var users = result.Users;
            console.log(users);
            if (users.length > 0) {
                displayAllUsers(users);
            } else {
                var x = $(MIDDLE_PANELS_FOR_USERS);
                x.empty();
                x.append("<span id=\"noUserMessage\">No Users to be displayed.</span>");
            }
        },
        error : function(e) {
            console.log("could not fetch all users: " + e);
        }
    });
}

function fetchUserHTML(jsonObject) {
    var tweet = "<div class=\"col-md-6 col-sm-6\" style=\"margin-top:30px\">" +
                    "<div class=\"row\">" +
                        "<img class=\"col-xs-5\" id=\"user-" + jsonObject.userId + "\"style=\"max-width:70px\" onerror=\"brokenProfileImage(this)\"" + "src=\"" + FETCH_IMAGE_GIVEN_USER_ID + "?userId=" + jsonObject.userId + "\"" +
                            ">" + 
                        "<div class=\"col-xs-7\" align=\"center\">" +
                            "<a id=\"profile-" + jsonObject.userId + "\" style=\"font-size:15px;word-break:break-word\">@" + jsonObject.handle + "</a>" + 
                        "</div>" +
                    "</div>" +
                    "<div class=\"row\" align=\"center\">" +
                        "<input type=\"button\" class=\"btn-primary btn-md tweet-footer-button\" style=\"margin-top:10px;\"name=\"followThisUser\" id=\"follow-" + jsonObject.userId + "\" value=\"Follow\">" +
                    "</div>" +
                    "<hr>" +
                "</div>";
    return tweet;
}

function displayAllUsers(jsonObject) {
    x = $(PARENT_FOR_USERS_ELEMENT);
    x.empty();
    $(NO_USER_MESSAGE).remove();
    console.log(x.text());
    while (x.text() != "");
    for (var i = 0; i < jsonObject.length; i++) {
        var userHTML = fetchUserHTML(jsonObject[i]);
        console.log("here");
        x.append($(userHTML));
        if (jsonObject[i].isFollowing == true) {
            console.log("here #follow-" + jsonObject[i].userId);
            $("#follow-" + jsonObject[i].userId).val("Unfollow");
        }
    }
}

function fetchAllFollowers(userId, loggedInUser) {
    fetchAllUsers(FETCH_ALL_FOLLOWERS_GIVEN_USERID, userId, loggedInUser);
}

function fetchAllFollowing(userId, loggedInUser) {
    fetchAllUsers(FETCH_ALL_FOLLOWING_GIVEN_USERID, userId, loggedInUser);
}

function followAUser(userId, loggedInUser) {
   $.ajax({
        url : FOLLOW_A_USER_URL,
        type : "POST",
        data : {
            userId : userId,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("user has been followed");
            $("#follow-" + userId).val("Unfollow");
        },
        error : function(e) {
            console.log("could not follow user: " + e);
        }
    });
}

function unfollowAUser(userId, loggedInUser) {
    $.ajax({
        url : UNFOLLOW_A_USER_URL,
        type : "POST",
        data : {
            userId : userId,
            loggedInUser : loggedInUser
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("user has been unfollowed");
            $("#follow-" + userId).val("Follow");
        },
        error : function(e) {
            console.log("could not unfollow user: " + e);
        }        
    })
}

$(document).ready(function() {
    $(MIDDLE_PANELS_FOR_USERS).click(function(e) {
        var elementId = e.target.id.split("-");
        console.log("clicked  " + e.target.id + "  " + elementId[0] + "  " + $("#" + e.target.id).val());        
        if (elementId[0] == "profile") {
            fetchAndShowProfileOfCurrentUser("", elementId[1], localStorage.getItem("loggedInUser"));
        } else if (elementId[0] == "follow") {
            var x = localStorage.getItem("loggedInUserFollowingCount");
            if ($("#" + e.target.id).val() == "Unfollow") {
                unfollowAUser(elementId[1], localStorage.getItem("loggedInUser"));
                x--;
                $("#" + e.target.id).val("Follow");
            } else if ($("#" + e.target.id).val() == "Follow") {
                x++;
                followAUser(elementId[1], localStorage.getItem("loggedInUser"));
                $("#" + e.target.id).val("Unfollow");
            }
            localStorage.setItem("loggedInUserFollowingCount", x);
            if (localStorage.getItem("currentUser") == localStorage.getItem("loggedInUser")) {
                $(LEFT_PANEL_FOLLOWING_COUNT).html(x);
            }
        }
    });
});