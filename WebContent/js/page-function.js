function profilePage() {
    $(HASHTAG_HEADING_PANEL).hide();
    $(LEFT_PANEL_PROFILE_DETAILS).show();
    $(LEFT_PANEL_HASHTAGS).hide();
    $(LEFT_PANEL_FOLLOW_BUTTON).hide();
    console.log()
    if (getCurrentUser() != getLoggedInUser()) {
        $(LEFT_PANEL_FOLLOW_BUTTON).show();
        if (localStorage.getItem("followState") == "unfollow") {
            console.log("here in followState unfollw " + LEFT_PANEL_FOLLOW_BUTTON);
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Unfollow");
        } else {
            $(LEFT_PANEL_FOLLOW_BUTTON).val("Follow");
        }
    }
    $(MIDDLE_PANELS_FOR_USERS).hide();
    $(MIDDLE_PANEL_NEW_TWEET).show();
    $(MIDDLE_PANEL_TWEET_PARENT).show();
}

function homePage() {
    profilePage();
    $(LEFT_PANEL_FOLLOW_BUTTON).hide();
}

function followersPage() {
    $(HASHTAG_HEADING_PANEL).hide();
    $(LEFT_PANEL_PROFILE_DETAILS).show();
    $(LEFT_PANEL_HASHTAGS).hide();
    $(MIDDLE_PANELS_FOR_USERS).show();
    $(MIDDLE_PANEL_NEW_TWEET).hide();
    $(MIDDLE_PANEL_TWEET_PARENT).hide();    
}

function followingPage() {
    $(MIDDLE_PANELS_FOR_USERS).html("");
    followersPage();
}

function hashtagPage() {
    $(HASHTAG_HEADING_PANEL).show();
    $(LEFT_PANEL_PROFILE_DETAILS).hide();
    $(LEFT_PANEL_HASHTAGS).show();
    $(MIDDLE_PANELS_FOR_USERS).hide();
    $(MIDDLE_PANEL_NEW_TWEET).hide();
    $(LEFT_PANEL_FOLLOW_BUTTON).hide();
    $(MIDDLE_PANEL_TWEET_PARENT).show();        
}