
public class CurvedPipe extends Blocks {
	private final boolean isStatic = false;
	//00 = 0, 01 = 1, 10 = 10, 11 = 11
	private byte property;
	
	CurvedPipe(byte property, byte position) {
		this.property = property;
		super.setPosition(position);
	}
	
	public void setImg() {
		if(property == 0)
			super.setImg("00curvedPipe.png");
		else if(property == 1) 
			super.setImg("01curvedPipe.png");
		else if(property == 10) 
			super.setImg("10curvedPipe.png");
		else if(property == 11)
			super.setImg("11curvedPipe.png");
	}

	public byte getProperty() {
		return property;
	}

	public void setProperty(byte property) {
		this.property = property;
	}

	public boolean isStatic() {
		return isStatic;
	}
	
	
	

}
