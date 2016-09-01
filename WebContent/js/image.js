function uploadImageForTweet(urlForInfo, elementId, id) {
    $.ajax({
        url: IMAGE_UPLOAD_URL,
        type: "POST",
        data: new FormData(document.getElementById(elementId)), 
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("image uploaded successfully ");
            console.log(result);
            imageInformationUploadPostTweet(urlForInfo, result, id);
        },
        error : function (e) {
            console.log("couldn't upload image: " + e);
        }
    });
} 

function imageInformationUploadPostTweet(url, mediaId, id) {
    $.ajax({
        url: url,
        type: "POST",
        data: {
            mediaId: mediaId,
            id: id
        },
        crossDomain: true,
        xhrFields: { 
            withCredentials: false 
        },
        success: function() {
            console.log("success for information upload..!!!");
            fetchTweetGivenTweetId(id, getLoggedInUser());
        },
        error: function(e) {
            console.log("couldn't upload image information: " + e);
        }
    });
}


function uploadImageForUser(urlForInfo, elementId, id, pageToBeLoaded) {
    $.ajax({
        url: IMAGE_UPLOAD_URL,
        type: "POST",
        data: new FormData(document.getElementById(elementId)), 
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            console.log("image uploaded successfully ");
            console.log(result);
            imageInformationUploadNewUser(urlForInfo, result, id, pageToBeLoaded);
        },
        error : function (e) {
            console.log("couldn't upload image: " + e);
        }
    });
} 


function imageInformationUploadNewUser(url, mediaId, id, pageToBeLoaded) {
    $.ajax({
        url: url,
        type: "POST",
        data: {
            mediaId: mediaId,
            id: id
        },
        crossDomain: true,
        xhrFields: { 
            withCredentials: false 
        },
        success: function() {
            console.log("success for information upload..!!!");
            window.location.replace(pageToBeLoaded);
        },
        error: function(e) {
            console.log("couldn't upload image information: " + e);
        }
    });
}

function brokenProfileImage(image) {
    image.src = ALTERNATE_PROFILE_IMAGE;
    image.onerror = "";
}

function brokenTweetImage(image) {
    image.src = ALTERNATE_TWEET_IMAGE;
    image.onerror = "";
}

function getAndLoadImage(divisionId, mediaId, altImageAddr) {
    var imageURL = IMAGE_RETRIEVE_URL + "?image=" + mediaId;
    $("#" + divisionId).attr("src",imageURL);
	$("#" + divisionId).show();
}