function fetchFiveMostUsedHashtags() {
    $.ajax({
        url : FIVE_MOST_USED_HASHTAGS_URl,
        data : {
            hashtag : hashtag
        },
        crossOrigin: true,
        xhrFields: { 
            withCredentials: false 
        },
        success : function(result) {
            var hashtag = result.hashtags;
            var i = 1;
            for (; i <= hashtag.length; i++) {
            	console.log(hashtag[i - 1].word);
                $(LEFT_PANEL_HASHTAG_PREFIX + i).html(hashtag[i - 1].word);
            }
            for (; i <= 5; i++) {
            	console.log("hiding " + i);
                $(LEFT_PANEL_HASHTAG_PREFIX + i).hide();
            }
        }, 
        error : function(e) {
        	redirectToLoginIfError(e);
            console.log("error occured: " + e);
        }
    });
}