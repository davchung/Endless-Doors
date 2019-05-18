import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameObject {

	// these are the variables that all GameObjects have
	protected int health;
	protected double locX, locY;
	protected int WIDTH, HEIGHT;
	protected Rectangle current;
	public final static String PATH_PREFIX = "img/";
	protected BufferedImage image;
	protected int hittable = 0;
	public boolean throughable;
	protected boolean invincible;

	// constructor #1 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean inv, int startingHealth, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = inv;
		health = startingHealth;
		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
	// constructor #2 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean inv, int startingHealth, BufferedImage b) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = inv;
		health = startingHealth;
		image = b;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
	
	public void uponRemoval() {
		int decide = (int) (Math.random()*100);
		if(this instanceof Chest) {
			RPGGame.getObjects().add(new Coin(this.getLocX(),this.getLocY(),decide*3));
		}
		if(this instanceof Crate) {
			if(decide >= 75)
				RPGGame.getObjects().add(new Coin(this.getLocX(),this.getLocY(),decide/10));
		}
		if(this instanceof Coin) {
			RPGGame.getInventory().addGold(health);
		}
		if(this instanceof ExplosiveBarrel) {
			//dmg player here
		}
	}

	// getters, setters, and "incrementers" are here
	public boolean getInvincibility() {
		return invincible;
	}
	public void setInvincibility(boolean value) {
		invincible = value;
	}
	public int getHealth() {
		return this.health;
	}
	public void incrementHealth(int amount) {
		this.health += amount;
	}

	protected Rectangle getRect() {
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
	public BufferedImage getImg() {
		return image; //so i can see image from other classes
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
			g.drawImage(image, (int) locX, (int) locY,  WIDTH, HEIGHT, null);
		}
	}
	public void draw(Graphics g,BufferedImage i) {
		if (image != null) {
			g.drawImage(i, (int) locX, (int) locY,  WIDTH, HEIGHT, null);
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
		if (getRect().intersects(other.getRect())) {
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
	
	
	// returns a random int between lower bound (lb) and upper bound (ub), inclusive
	public static int randInt(int lb, int ub) {
		return lb + (int)(Math.random() * ((ub - lb) + 1));
	}

}
