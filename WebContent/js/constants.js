// URLs
//var IPAddress = "http://localhost:8080/Mini_twitter/";
var IPAddress = "";
var AUTHENTICATION_URL = IPAddress + "AuthenticateUser";
var EMAIL_VERIFICATION_URL = IPAddress + "CheckEmailExists";
var HANDLE_VERIFICATION_URL = IPAddress + "CheckHandleExists";
var USER_REGISTER_URL = IPAddress + "RegisterUser";
var DELETE_TWEET_URL = "";
var RETWEET_URL = IPAddress + "RetweetGivenUserIdAndTweetId";
var USER_PROFILE_SETTINGS_URL = "";
var FIVE_MOST_USED_HASHTAGS_URl = IPAddress + "FetchFiveMostUsedHashtags";
var TWEETS_FOR_USER_HOME_URL = IPAddress + "FetchTweetsForUserHomeGivenId";
var TWEETS_FOR_USER_PROFILE = IPAddress + "FetchTweetsForUserProfileGivenUserIdOrHandle";
var FETCH_NOTIFICATIONS_URL = IPAddress + "FetchNotificationsGivenUserId";
var UNLIKE_TWEET_URL = "";
var FETCH_SINGLE_TWEET_URL = IPAddress + "GetTweetGivenId";
var USER_DETAILS_GIVEN_ID = IPAddress + "FetchUserDetailsGivenUserId";
var FETCH_ALL_FOLLOWERS_GIVEN_USERID = IPAddress + "FetchFollowersGivenUserId";
var FETCH_ALL_FOLLOWING_GIVEN_USERID = IPAddress + "FetchFollowingGivenUserId";
var FOLLOW_A_USER_URL = IPAddress + "FollowUser";
var UNFOLLOW_A_USER_URL = IPAddress + "UnfollowUser";
var HASHTAG_URL = "";
var LIKE_TWEET_URL = IPAddress + "LikeTweetGivenTweetIdAndUserId";
var UNLIKE_TWEET_URL = IPAddress + "UnlikeATweet";
var DELETE_TWEET_URL = IPAddress + "DeleteTweetGivenId";
var TWEETS_BY_A_SPECIFIC_USER = IPAddress + "FetchTweetsAndRetweetsGivenUserId";
var LOGOUT_URL = IPAddress + "EndUserSession";
var IMAGE_UPLOAD_TWEET_URL = IPAddress + "UploadImageForTweet";
var POST_A_NEW_TWEET_URL = IPAddress + "PostNewTweet";
var MAKE_CONNECTION_BETWEEN_USER_URL = "";
var TWEETS_FOR_A_HASHTAG = IPAddress + "TweetsWithHashtag";

// Profile
var HASHTAG_HEADING_PANEL = "#hashtag-heading";
var LEFT_PANEL_TWEET_COUNT = "#left-panel-tweet-count";
var LEFT_PANEL_TWEET_HEADING = "#left-panel-tweet-heading";
var LEFT_PANEL_FOLLOWER_HEADING = "#left-panel-follower-heading";
var LEFT_PANEL_FOLLOWING_HEADING = "#left-panel-following-heading";
var LEFT_PANEL_FOLLOWER_COUNT = "#left-panel-follower-count";
var LEFT_PANEL_FOLLOWING_COUNT = "#left-panel-following-count"
var LEFT_PANEL_PROFILE_PHOTO = "#profilePhoto";
var LEFT_PANEL_USER_HANDLE = "#displayUserHandle";
var NEW_TWEET_LETTER_COUNT = "#tweet-characters-left";
var NO_USER_MESSAGE = "#noUserMessage";
var NOTIFICATION_BUTTON = "#notificationNavigation";
var NOTIFICATION_MODAL = "#notificationModal";
// Pages
var LOGIN_AND_REGISTRATION_PAGE = "loginAndRegister.html";
var PROFILE_CUM_HOME_PAGE = "userhome.html";

var NOTIFICATIONS_MODAL_PARENT = "#notificationsParent";

// Images
var ALTERNATE_TWEET_IMAGE = "images/twitter.png";
var ALTERNATE_PROFILE_IMAGE = "images/user.png";
var IMAGE_UPLOAD_FOR_TWEET = "imageInputForTweet";

// // Element IDs
// Sign in
var SIGN_IN_EMAIL = "#signInEmail";
var SIGN_IN_PASSWORD = "#signInPassword";

// Sign up
var SIGN_UP_EMAIL = "#signUpEmail";
var SIGN_UP_PASSWORD = "#signUpPassword";
var SIGN_UP_HANDLE = "#signUpHandle";
var SIGN_UP_NAME = "#userName";

// Errors
var EMAIL_EXISTS_ERROR = "#emailExistsError";
var HANDLE_TAKEN_ERROR = "#handleTakenError";
var INVALID_CREDENTIALS_ENTERED = "#invalidCredentials";
var INVALID_USERNAME_ENTERED = "#usernameInvalidError";
var LEFT_PANEL_FOLLOW_BUTTON = "#follow-button-left-panel";
var MODAL_SEARCHES = "#searchBody";
// Buttons
var SIGN_UP_BUTTON = "#signUpButton";
var SIGN_IN_BUTTON = "#signIn";

var LOADER = "#loader";
// Notification Bar
var HOME_BUTTON = "#homeButton";
var PROFILE_BUTTON = "#profileButton";
var OPTIONS_BUTTON = "#optionsButton";
var UPDATE_PROFILE_BUTTON = "#updateProfileButton";
var LOGOUT_BUTTON = "#logout";

// values
var NEW_TWEET_FETCH_INTERVAL = 30000;
var NEW_TWEET_TEXT = "#newTweetText";
// followers
var FOLLOWER_PARENT = "#followerParent";

var HASHTAG_HEADING_PANEL_TEXT = "#hashtag";
var LEFT_PANEL_PROFILE_DETAILS = "#left-panel-profile";
var LEFT_PANEL_HASHTAGS = "#left-panel-hashtags";
var HASHTAG_TITLE = "#hashtag";
var LEFT_PANEL_HASHTAG_PREFIX = "#left-panel-hashtag-";
var MIDDLE_PANEL_NEW_TWEET = "#middle-panel-new-tweet";
var INPUT_TEXT_FOR_NEW_TWEET = "#newTweetText";
var UPLOAD_IMAGE_FOR_NEW_TWEET = "#imageInputForTweet";
var POST_A_NEW_TWEET_BUTTON = "#postTweet";
var MIDDLE_PANEL_TWEET_PARENT = "#middle-panel-all-tweets";

var MIDDLE_PANELS_FOR_USERS = "#middle-panel-users";
var PARENT_FOR_USERS_ELEMENT = "#usersParent";


var IMAGE_UPLOAD_URL = "UploadImageForUser";
var IMAGE_RETRIEVE_URL = "FetchImageGivenTweetId";
var IMAGE_ELEMENT_MODAL = "#imageUserUpload";
var IMAGE_FORM_MODAL = "imageInputForLogin";
var IMAGE_FORM_TWEET = "imageInputForTweet";
var IMAGE_ELEMENT_TWEET = "#x";
var IMAGE_INFORMATION_URL_USER = "AccknowledgmentForUserMedia";
var IMAGE_INFORMATION_URL_TWEET = "AccknowledgmentForTweetMedia";
var FETCH_IMAGE_GIVEN_USER_ID = "FetchImageGivenUserId";