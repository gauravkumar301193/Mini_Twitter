package models;

/**
 * @author gaurav.kum
 */

public enum MediaType {
	IMAGE("image")
	, VIDEO("video")
	, GIF("gif");
	
	private String type;
	
	private MediaType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
	
	public String getValue() {
        return type;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

