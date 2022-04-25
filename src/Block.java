
public class Block {
	
	private String type;
	// collumn,row
	private int[] position = new int[2];
	private String img;
	private boolean movable;
	private String property;
		
	Block(int[] position, String type, String property) {
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

	public String getImg() {
		return img;
	}
	
	// setting images automaticly by type and property
	public void setImg() {
		Character ch = Character.toLowerCase(type.charAt(0));
		type = type.replace(type.charAt(0), ch);
		Character ch2 = Character.toUpperCase(property.charAt(0));
		property = property.replaceFirst(Character.toString(property.charAt(0)), Character.toString(ch2));

		if ( !((type+property).equals("emptyFree"))){
			img = "images/" + type + property +".png";
		}	
	}

	public boolean isMovable() {
		return movable;
	}

	public String getProperty() {
		return property;
	}

	public int getColumn(){
		return position[0];
	}

	public int getRow(){
		return position[1];
	}

	
	
	
	
}
