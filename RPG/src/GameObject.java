import java.awt.*;

public class GameObject {

	private static double locX, locY;
	private final double WIDTH, HEIGHT;
	private Image image;
	public final static String PATH_PREFIX = "img/";

	public GameObject(double x, double y, double w, double h, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}

	public void draw(Graphics g) {
		if(image != null) {
			g.drawImage(image, (int)locX, (int)locY, null);
		}

	}

	public void moveX(int howmuch) {
		locX += howmuch;
	}

	public void moveY(int howmuch) {
		locY += howmuch;
	}
}
