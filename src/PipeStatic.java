
public class PipeStatic extends Blocks {
	private final boolean isStatic = true;
	private boolean isVertical;
	
	
	PipeStatic(boolean isVertical, byte position) {
		this.isVertical = isVertical;
		super.setPosition(position);
	}
	
	public void setImg() {
		if(isVertical)
			super.setImg("verticalPipeStatic.png");
		else
			super.setImg("horizontalPipeStatic.png");
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
