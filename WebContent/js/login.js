$(document).ready(function(){
    var previousEmailValue = "";
    var invalidHandle = false;
    var invalidEmail = false;
    $(SIGN_UP_EMAIL).focusout(function() {
        $(EMAIL_EXISTS_ERROR).hide();
        $(INVALID_EMAIL_ENTERED).hide();
        var emailEntered = $(SIGN_UP_EMAIL).val();
        var validEmail = new RegExp("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$");
        
        console.log("checking email");
        if (validEmail.test(emailEntered) == false) {
        	$(INVALID_EMAIL_ENTERED).show();
        	console.log("email invalid");
        	invalidEmail = true;
        	return;
        }
        
        
        if (previousEmailValue != emailEntered) {
            $(EMAIL_EXISTS_ERROR).hide();
            $(INVALID_EMAIL_ENTERED).hide();
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
                    if (result != "true") {
                        $(EMAIL_EXISTS_ERROR).hide();
                        $(INVALID_EMAIL_ENTERED).hide();
                        console.log("new Email");
                        invalidEmail = false;
                    } else {
                        $(EMAIL_EXISTS_ERROR).show();
                        console.log("Email exists");
                        invalidEmail = true;
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
            invalidHandle = true;
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
                    if (result != "true") {
                        $(HANDLE_TAKEN_ERROR).hide();
                        $(INVALID_USERNAME_ENTERED).hide();
                        invalidHandle = false;
                        console.log("New Handle!");
                    } else {
                        $(HANDLE_TAKEN_ERROR).show(); 
                        invalidHandle = true;
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
                window.location = PROFILE_CUM_HOME_PAGE;
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
        if (name == "" || password == "" || handle == "" || email == "") {
        	alert("Please fill out all the fields");
        	return;
        }
        $(SIGN_UP_EMAIL).focusout();
        $(SIGN_UP_HANDLE).focusout();
        if (invalidHandle == true || invalidEmail == true)
        	return;
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
                if ($(IMAGE_ELEMENT_MODAL).val()) {
                    var file = $(IMAGE_ELEMENT_MODAL);
                    var filename = $.trim(file.val());
                	if (!(isJpg(filename) || (isPng(filename) || (isJpeg(filename))))) {
                		alert("only Jpg, Jpeg and Png formats");
                		return;
                	}                	
                    uploadImageForUser(IMAGE_INFORMATION_URL_USER, IMAGE_FORM_MODAL, result.userId, LOGIN_AND_REGISTRATION_PAGE);
                    alert("successful registration");
                } else {
                    window.location.replace(LOGIN_AND_REGISTRATION_PAGE);
                }
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
    
    function isJpg(filename) {
    	return filename.match(/jpg$/i);
    }

    function isPng(filename) {
    	return filename.match(/png$/i);
    }
    
    function isJpeg(filename) {
    	return filename.match(/jpeg$/i);
    }
});
