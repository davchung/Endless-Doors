import java.awt.Graphics;

public class Ball {

	private static double x;//coords
	private static double y;
	private static double iX;//velocity
	private static double iY;
	private static int length;
	private static int width;

	public Ball(int iX, int iY, int l, int w) {
		x = iX;
		y = iY;
		length = l;
		width = w;
	}

	public void draw(Graphics g) {// 0 450
		g.fillRoundRect((int) x, (int) y, length, width, 100, 100);
	}

	public void changeX(int change) {
		x = change;
	}

	public void changeY(int change) {
		y = change;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void flipBallVY() {
		iY = -iY;
	}	

	public void flipBallVX() {
		iX = -iX;
	}

	public void changeXByIncrement(int increment) {
		x += increment;
	}

	public void changeYByIncrement(int increment) {
		y += increment;
	}
	
	public void setBallVY(int y) {
		iY = y;
	}

	public void setInitials(double iX, double iY) {
		this.iX = iX;
		this.iY = iY;
	}
	
	public int getX() {
		return (int) x;
	}
	public int getY() {
		return (int) y;
		
	}
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getBallVY() {
		return (int) iY;
	}

	public void current() {
		iY += 1;
		x += iX;
		y += iY;
	}


	
	
	
	
	
}
