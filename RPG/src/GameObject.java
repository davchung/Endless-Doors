import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {

	// these are the variables that all GameObjects have
	private int health = 30;
	private int damage = 10;

	private Rectangle current;
	public final static String PATH_PREFIX = "img/";
	protected BufferedImage image;

	protected double locX, locY;
	protected double pastX, pastY; // how much they moved in their last frame
	protected double WIDTH, HEIGHT;
	private double right, down;

	private int cooldown = 0;
	private int hittable = 0;
	public boolean throughable;

	// constructor #1 for GameObject
	public GameObject(double x, double y, double w, double h, boolean through, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
	// constructor #2 for GameObject
	public GameObject(double x, double y, double w, double h, boolean through, BufferedImage i) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		image = i;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}

	// getters, setters, and "incrementers" are here
	public int getHealth() {
		return this.health;
	}
	public void incrementHealth(int amount) {
		this.health += amount;
	}
	public int getDamage() {
		return this.damage;
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
	public double getPastX() {
		return this.pastX;
	}
	public double getPastY() {
		return this.pastY;
	}
	
	public double getRight() {
		return this.right;
	}
	public double getDown() {
		return this.down;
	}
	public void setRight(double r) {
		this.right = r;
	}
	public void setDown(double d) {
		this.down = d;
	}

	public int getCooldown() {
		return this.cooldown;
	}
	public void setCooldown(int cd) {
		this.cooldown = cd;
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

	// these methods have to do with movement
	public void moveX(double howMuch) {
		locX += howMuch;
		current.x = (int) locX;
		pastX = howMuch;
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
		if (RPGGame.ticks > hittable) {
			health -= damage;
			hittable = RPGGame.ticks + 26;
			cooldown +=26;
			return;
		}
	}

	public boolean attack(int ticks) {
		if (cooldown >= RPGGame.ticks)
			return false;
		cooldown = RPGGame.ticks + ticks;
		return true;
	}
	
	// returns a random int between lower bound (lb) and upper bound (ub), inclusive
	public static int randInt(int lb, int ub) {
		return lb + (int)(Math.random() * ((ub - lb) + 1));
	}

}
