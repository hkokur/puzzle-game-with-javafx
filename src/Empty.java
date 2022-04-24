
public class Empty extends Blocks {
	final private boolean isStatic = false;
	
	
	Empty(byte position) {
		super.setPosition(position);
	}
	
	public void setImg() {
		super.setImg("empty.png");
	}

	public boolean isStatic() {
		return isStatic;
	}
	

}
