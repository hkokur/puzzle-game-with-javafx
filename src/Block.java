
public class Block {
	
	private String type;
	private byte position;
	private String img;
	private boolean movable;
	private String property;
		
	Block(byte position, String type, String property) {
		this.position = position;
		this.type = type;
		this.property = property;
		settingMovable();
		setImg();
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
	
	// setting images automaticly by type and property
	public void setImg() {
		Character ch = Character.toLowerCase(type.charAt(0));
		type = type.replace(type.charAt(0), ch);
		if ( !((type+property).equals("emptynone"))){
			img = "images/" + type + property +".png";
		}	
	}

	public boolean isMovable() {
		return movable;
	}

	public String getProperty() {
		return property;
	}

	
	
	
	
}
