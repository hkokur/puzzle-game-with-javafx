
public  class Blocks {
	
	private String type;
	private byte position;
	private String img;
	private boolean movable;
	private String property;
	
	Blocks(byte position, String type, String property) {
		this.position = position;
		this.type = type;
		this.property = property;
		settingMovable();
	}
	
	public void settingMovable() {
		if(type.equals("Empty") || type.equals("Pipe")) {
			movable = true;
		}
	}

	public String getType() {
		return type;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition(byte position) {
		this.position = position;
	}

	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}

	public boolean isMovable() {
		return movable;
	}

	public String getProperty() {
		return property;
	}

	
	
	
	
}
