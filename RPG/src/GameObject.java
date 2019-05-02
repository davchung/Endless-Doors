import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {

	private double locX, locY;
	public double getLocX() { return locX; }
	public double getLocY() { return locY; }
	private double WIDTH, HEIGHT;
	private BufferedImage image;
	public final static String PATH_PREFIX = "img/";
	private Rectangle current;
	public Rectangle getRect() { return current; }

	public GameObject(double x, double y, double w, double h, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}

	public GameObject(double x, double y, double w, double h, BufferedImage i) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = i;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}

	protected BufferedImage getImage(String fn) {
		BufferedImage img = null;
		fn = PATH_PREFIX + fn;
		try {

			img = ImageIO.read(this.getClass().getResource(fn));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void draw(Graphics g) {
		if (image != null) {
			g.drawImage(image, (int) locX, (int) locY, (int) WIDTH, (int) HEIGHT, null);
		}
	}

	public void moveX(double howMuch) {
		locX += howMuch;
		current.x = (int) locX;
	}

	public void moveY(double howMuch) {
		locY += howMuch;
		current.y = (int) locY;
	}

	public boolean collides(GameObject other) {
		if (current.intersects(other.getRect())) {
			return true;
		}
		return false;
	}

}
