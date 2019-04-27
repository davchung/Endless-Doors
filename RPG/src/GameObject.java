import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameObject {

	private static double locX, locY;
	private final double WIDTH, HEIGHT;
	private Image image;
	public final static String PATH_PREFIX = "img/";

	public GameObject(double x, double y, double w, double h, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = getImage(s);
	}

	protected Image getImage(String fn) {
		Image img = null;
		fn = PATH_PREFIX + fn;
		try {

			img = ImageIO.read(this.getClass().getResource(fn));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public void draw(Graphics g) {
		if(image != null) {
			g.drawImage(image, (int)locX, (int)locY, null);
		}
	}

	public void moveX(int howMuch) {
		locX += howMuch;
	}

	public void moveY(int howMuch) {
		locY += howMuch;
	}
}
