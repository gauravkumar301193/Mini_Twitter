package models;
/**
 * @author gaurav.kum
 */

public class Media {
	private int mediaId;
	private String mediaPath;
	private MediaType mediaType;
	private static int mediaIdGenerator = 0;
	
	public int getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(int media_id) {
		this.mediaId = media_id;
	}
	
	public String getMediaPath() {
		return mediaPath;
	}
	
	public void setMediaPath(String media_path) {
		this.mediaPath = media_path;
	}

	public MediaType getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(MediaType media_type) {
		this.mediaType = media_type;
	}

	public int generateMediaId() {
		mediaIdGenerator++;
		return mediaIdGenerator;
	}
	
	
}
