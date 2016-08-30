function uploadImage(urlForInfo, elementId, id) {
    $.ajax({
        url: "UploadImage",
        type: "POST",
        data: new FormData(document.getElementById(IMAGE_FORM_TWEET)), 
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success : function(result) {
            console.log("image uploaded successfully ");
            console.log(result);
            imageInformationUpload(urlForInfo, result, id);
        },
        error : function (e) {
            console.log("couldn't upload image: " + e);
        }
    });
} 

function imageInformationUpload(url, mediaId, id) {
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
            console.log("image has been uploaded for " + id);
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