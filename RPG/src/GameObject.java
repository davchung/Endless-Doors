import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {

	private int health = 20;
	private int hittable = 0;
	private int damage = 10;
	protected double WIDTH, HEIGHT;
	protected BufferedImage image;
	public final static String PATH_PREFIX = "img/";
	private Rectangle current;
	protected double locX, locY;
	protected double pastX, pastY; // how much they moved in their last frame
	public boolean throughable;
	private int cooldown = 0;

	public int getHealth() {
		return this.health;
	}

	public void incrementHealth(int amount) {
		this.health += amount;
	}

	public GameObject(double x, double y, double w, double h, String s, boolean through) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
		throughable = through;
	}

	public GameObject(double x, double y, double w, double h, BufferedImage i, boolean through) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = i;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
		throughable = through;
	}

	public int getHittable() {
		return hittable;
	}

	public void setHittable(int amount) {
		this.hittable = amount;
	}

	public int getDamage() {
		return this.damage;
	}

	public double getLocX() {
		return locX;
	}

	public double getLocY() {
		return locY;
	}

	public double getPastX() {
		return pastX;
	}

	public double getPastY() {
		return pastY;
	}

	public Rectangle getRect() {
		return current;
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

	public void setBufferedImage(BufferedImage b) {
		image = b;
	}

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

	public boolean attack(int ticks) {
		if (cooldown >= ticks)
			return false;
		cooldown = ticks + 40;
		return true;
	}

	public void hit(int damage) {
		if (RPGRunner.ticks > hittable) {
			health -= damage;
			hittable = RPGRunner.ticks + 26;
			return;
		}

	}

}
