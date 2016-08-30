$(document).ready(function(){
    var previousEmailValue = "";
    $(SIGN_UP_EMAIL).focusout(function() {
        var emailEntered = $(SIGN_UP_EMAIL).val();

        if (previousEmailValue != emailEntered) {
            previousEmailValue = emailEntered;
            console.log(emailEntered);
            $.ajax({
                url : EMAIL_VERIFICATION_URL,
                type : "GET",
                data : {
                  emailId : emailEntered  
                },
                crossOrigin: true,
                xhrFields: { 
                    withCredentials: false 
                },
                success : function(result) {
                    if (result != "EXISTS") {
                        $(EMAIL_EXISTS_ERROR).hide();
                        console.log("new Email");
                    } else {
                        $(EMAIL_EXISTS_ERROR).show();
                        console.log("Email exists");
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    $(EMAIL_EXISTS_ERROR).show();
                }
            });
        }
    });
    
    var previousHandleEntered = "";
    $(SIGN_UP_HANDLE).focusout(function(){
        $(HANDLE_TAKEN_ERROR).hide();
        $(INVALID_USERNAME_ENTERED).hide();
        var handle = $(SIGN_UP_HANDLE).val();
        var validHandle = new RegExp('^[0-9]*[a-zA-Z_]+[a-zA-Z0-9_]*$');

        if (validHandle.test(handle) == false) {
            $(INVALID_USERNAME_ENTERED).show();
            return;
        }
        if (previousHandleEntered != handle) {
            previousHandleEntered = handle;
            $.ajax({
                url : HANDLE_VERIFICATION_URL,
                type : "GET",
                data : {
                    handle : handle
                },
                crossOrigin: true,
                xhrFields: { 
                    withCredentials: false 
                },
                success : function(result) {
                    if (result != "EXISTS") {
                        $(HANDLE_TAKEN_ERROR).hide();
                        console.log("New Handle!");
                    } else {
                        $(HANDLE_TAKEN_ERROR).show();                        
                        console.log("Handle Exists");
                    }
                },
                error : function(e) {
                    $(HANDLE_TAKEN_ERROR).show();
                    console.log("handle already taken");
                }
            });
        }
    });
    
    $(SIGN_IN_BUTTON).click(function(){
        $(INVALID_CREDENTIALS_ENTERED).hide();
        var email = $(SIGN_IN_EMAIL).val();
        var password = MD5($(SIGN_IN_PASSWORD).val());
        var tryResult;
        $.ajax({
            url : AUTHENTICATION_URL,
            type : "POST",
            data : {
                emailId : email,
                password : password
            },
            crossOrigin: true,
            xhrFields: { 
                withCredentials: false 
            },
            success : function(result) {
                console.log("successful authentication");
                tryResult = result;
                saveDetailsToLocalStorage(tryResult);
                $(INVALID_CREDENTIALS_ENTERED).hide();
                localStorage.setItem("pageFunction", "home");
                window.location.replace(PROFILE_CUM_HOME_PAGE);
            },
            error : function(e) {
                console.log("authentication failed");
                $(INVALID_CREDENTIALS_ENTERED).show();
            }
        });
    });
    
    $(SIGN_UP_BUTTON).click(function(){
        var email = $(SIGN_UP_EMAIL).val();
        var handle = $(SIGN_UP_HANDLE).val();
        var password = MD5($(SIGN_UP_PASSWORD).val());
        var name = $(SIGN_UP_NAME).val();
        var tryResult;
        $.ajax({
            url : USER_REGISTER_URL,
            type : "POST",
            data : {
                emailId : email,
                password : password,
                handle : handle,
                userName : name
            },
            crossOrigin: true,
            xhrFields: { 
                withCredentials: false 
            },
            success : function(result) {
                console.log("user registered");
                tryResult = result;
                saveDetailsToLocalStorage(result);
                setCurrentEqualLoggedIn();
                localStorage.setItem("pageFunction", "home");
                if ($(IMAGE_ELEMENT_MODAL).val()) {
                    uploadImage(IMAGE_INFORMATION_URL_USER, IMAGE_ELEMENT_MODAL, localStorage.getItem("loggedInUser"));
                }
                window.location.replace(PROFILE_CUM_HOME_PAGE);
            },
            error : function(e) {
                console.log("error occured: ");
            }
        });
    });

    function saveDetailsToLocalStorage(jsonObject) {
        localStorage.setItem("loggedInUser", jsonObject.userId);
        localStorage.setItem("loggedInUserHandle", jsonObject.handle);
        localStorage.setItem("loggedInUserMediaId", 0);
        localStorage.setItem("loggedInUserName", jsonObject.name);
        localStorage.setItem("loggedInUserTweetCount", jsonObject.tweetCount);
        localStorage.setItem("loggedInUserFollowerCount", jsonObject.followerCount)
        localStorage.setItem("loggedInUserFollowingCount", jsonObject.followingCount);
    }
});
