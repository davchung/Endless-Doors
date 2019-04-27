import java.awt.*;

public class Enemy extends GameObject {

	// I don't know if the stuff that I commented out are needed, because that code
	// is already in the superclass GameObject

	/*
	 * private static double locX, locY; private final double WIDTH, HEIGHT; private
	 * Image image;
	 */

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png");
		/*
		 * locX = x; locY = y; WIDTH = w; HEIGHT = h; image = super.getImage(s);
		 */
	}

}
