package models;
/**
 * @author gaurav.kum
 */

public class Media {
	private int media_id;
	private String media_path;
	private MediaType media_type;
	private static int mediaIdGenerator = 0;
	
	public Media() {
		
	}
	
	public int getMediaId() {
		return media_id;
	}
	
	public void setMediaId(int media_id) {
		this.media_id = media_id;
	}
	
	public String getMediaPath() {
		return media_path;
	}
	
	public void setMediaPath(String media_path) {
		this.media_path = media_path;
	}

	public MediaType getMediaType() {
		return media_type;
	}
	
	public void setMediaType(MediaType media_type) {
		this.media_type = media_type;
	}

	public int generateMediaId() {
		mediaIdGenerator++;
		return mediaIdGenerator;
	}
	
	
}
