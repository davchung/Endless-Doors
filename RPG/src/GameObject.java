import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {

	// these are the variables that all GameObjects have
	private int health;
	protected double locX, locY;
	protected int WIDTH, HEIGHT;
	private Rectangle current;
	
	public final static String PATH_PREFIX = "img/";
	protected BufferedImage image;
	private int hittable = 0;
	public boolean throughable;
	private boolean invincible;

	// constructor #1 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean i, int startingHealth, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = i;
		health = startingHealth;
		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
	// constructor #2 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean i, int startingHealth, BufferedImage b) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = i;
		health = startingHealth;
		image = b;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}

	// getters, setters, and "incrementers" are here
	
	public boolean invincibility() {
		return invincible;
	}
	public int getHealth() {
		return this.health;
	}
	public void incrementHealth(int amount) {
		this.health += amount;
	}

	public Rectangle getRect() {
		return this.current;
	}
	public void setBufferedImage(BufferedImage b) {
		this.image = b;
	}

	public double getLocX() {
		return this.locX;
	}
	public double getLocY() {
		return this.locY;
	}
	
	public int getHittable() {
		return this.hittable;
	}
	public void setHittable(int h) {
		this.hittable = h;
	}

	// these methods have to do with images and drawing
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
	public void draw(Graphics g,BufferedImage i) {
		if (image != null) {
			g.drawImage(i, (int) locX, (int) locY, (int) WIDTH, (int) HEIGHT, null);
		}
	}

	// these methods have to do with movement
	public void moveX(double howMuch) {
		locX += howMuch;
		current.x = (int) locX;
	}
	public void moveY(double howMuch) {
		locY += howMuch;
		current.y = (int) locY;
	}

	public double getCX() {
		return this.locX + .5 * this.WIDTH;
	}
	public double getCY() {
		return this.locY + .5 * this.HEIGHT;
	}

	public boolean collides(GameObject other) {
		if (current.intersects(other.getRect())) {
			return true;
		}
		return false;
	}
	public void hit(int damage) {
		if (RPGGame.ticks > hittable&&!invincible) {
			health -= damage;
			hittable = RPGGame.ticks + 26;
		}
	}
	public void hit() {
		if (RPGGame.ticks > hittable && !invincible) {
			health -= 5;
			hittable = RPGGame.ticks + 26;
		}
	}
	
	// returns a random int between lower bound (lb) and upper bound (ub), inclusive
	public static int randInt(int lb, int ub) {
		int rand;
		rand=lb + (int)(Math.random() * ((ub - lb) + 1));
		lb=rand/50;
		return lb*50;
	}

}
