import java.awt.*;

public abstract class GameObject {

	private static double locX;
	private static double locY;
	private final double WIDTH;
	private final double HEIGHT;

	public GameObject(int x, int y, double w, double h) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}

	public void draw(Graphics g) {
		g.fillRoundRect((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT, 100, 100);
	}
	
	public void moveX(int howmuch) {
		locX += howmuch;
	}
	
	public void moveY(int howmuch) {
		locY += howmuch;
	}
}
