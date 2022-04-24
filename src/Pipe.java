
public class Pipe extends Blocks {
	private final boolean isStatic = false;
	private boolean isVertical;
	
	Pipe(boolean isVertical, byte position) {
		this.isVertical = isVertical;
		super.setPosition(position);
	}
	
	public void setImg() {
		if(isVertical) {
			super.setImg("verticalPipe.png");
		}
		else
			super.setImg("horizontalPipe.png");
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}

	public boolean isStatic() {
		return isStatic;
	}
	

}
