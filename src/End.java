
public class End extends Blocks{
	private boolean isVertical;
	final private boolean isStatic = true;
	
	
	End(boolean isVerticel, byte position) {
		this.isVertical = isVerticel;
		super.setPosition(position);
	}
	
	public void setImg() {
		if(isVertical)
			super.setImg("verticalEnd.png");
		else
			super.setImg("horixontalEnd.png");
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
