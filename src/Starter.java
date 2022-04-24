
public class Starter extends Blocks {
	final private boolean isStatic = true;
	private boolean isVertical;
	
	
	Starter(boolean isVerticel, byte position) {
		this.isVertical = isVerticel;
		super.setPosition(position);
	}
	
	//img folder
	public void setImg() {
		if(isVertical) {
			super.setImg("verticalStarter.png");
		}
		else
			super.setImg("horizontalStarter.png");
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}
}
