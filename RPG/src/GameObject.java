import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class GameObject {

	// these are the variables that all GameObjects have
	protected double health;
	protected double maxHealth;

	protected double locX, locY;
	protected int WIDTH, HEIGHT;
	protected Rectangle current;
	public final static String PATH_PREFIX = "img/";
	protected BufferedImage image;
	protected int hittable = 0;
	public boolean throughable;
	protected boolean invincible;
	private static int IDAssigner;
	private int gameID;
	ArrayList<Integer> wasHit = new ArrayList<Integer>();

	// constructor #1 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean inv, int startingHealth, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = inv;
		health = startingHealth;
		maxHealth = startingHealth;

		image = getImage(s);
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
		gameID = IDAssigner;
		IDAssigner++;
	}

	// constructor #2 for GameObject
	public GameObject(double x, double y, int w, int h, boolean through, boolean inv, int startingHealth,
			BufferedImage b) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		throughable = through;
		invincible = inv;
		health = startingHealth;
		maxHealth = startingHealth;

		image = b;
		current = new Rectangle((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
		gameID = IDAssigner;
		IDAssigner++;
	}

	public void setLoc(int x, int y) {
		locX = x;
		locY = y;
		current.setLocation(x, y);
	}

	public int getgameID() {
		return gameID;
	}

	public void uponRemoval() {
		int decide = (int) (Math.random() * 100);
		if (this instanceof Chest) {
			if(decide <= 20)
				RPGGame.getObjects().add(new Potion(this.getLocX(),this.getLocY(),"yellow"));
			else {
				RPGGame.getObjects().add(new Coin(this.getLocX(), this.getLocY(), decide / 4 + Map.roomCount * 4));
			}
			RPGGame.getFloor().setChestFloor((int) (this.getLocX()), (int) (this.getLocY()));
		}
		if (this instanceof Enemy) {
			
			if(decide >= 75) {
				RPGGame.getObjects().add(new Coin(this.getLocX(), this.getLocY(), (int)(decide / 2 + Map.roomCount * 2.75)));
			}
			else if(decide >= 50)
				RPGGame.getObjects().add(new Coin(this.getLocX(), this.getLocY(), (int)(decide / 2.5 + Map.roomCount * 2.75)));
			else if (decide >= 25)
				RPGGame.getObjects().add(new Coin(this.getLocX(), this.getLocY(), decide / 3 + Map.roomCount * 2));
			else {
				RPGGame.getObjects().add(new Coin(this.getLocX(), this.getLocY(), decide / 4 + (int)(Map.roomCount * 1.5)));
			}
		}
		if (this instanceof Coin) {
			RPGGame.getInventory().addGold(health);
		}
		
		if(this instanceof Potion) {
			((Potion)this).activate();
		}
	}

	// getters, setters, and "incrementers" are here
	public boolean getInvincibility() {
		return invincible;
	}

	public void setInvincibility(boolean value) {
		invincible = value;
	}

	public double getHealth() {
		return this.health;
	}
	public double getMaxHealth() {
		return this.maxHealth;
	}
	
	public void increaseMaxHealth(double d) {
		this.health+=d;
		this.maxHealth+=d;
	}

	public double getHealthPercent() {
		return ((double) this.health/this.maxHealth);
	}

	public void incrementHealth(int amount) {
		if ((this.health+=amount)>this.maxHealth)
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
		return image; // so i can see image from other classes
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
			g.drawImage(image, (int) locX, (int) locY, WIDTH, HEIGHT, null);
		}
	}

	public void draw(Graphics g, BufferedImage i) {
		if (image != null) {
			g.drawImage(i, (int) locX, (int) locY, WIDTH, HEIGHT, null);
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

	public int hPBarYLoc() {
		return (int) (this.getLocY() - 0.4*this.HEIGHT);
	}

	public boolean collides(GameObject other) {
		if (getRect().intersects(other.getRect())) {
			return true;
		}
		return false;
	}

	public void hit(double d, int iD) {
		if (RPGGame.ticks > hittable && !invincible && (!wasHit.contains(iD))) {
			health -= d;
			hittable = RPGGame.ticks + 10;
			wasHit.add(iD);
			if (!(this instanceof Enemy)) {
				RPGGame.getDamagedObjects().add(this);
				}
		}
	}

	// returns a random int between lower bound (lb) and upper bound (ub), inclusive
	public static int randInt(int lb, int ub) {
		return lb + (int) (Math.random() * ((ub - lb) + 1));
	}

	public void hit(double d) {
		if (RPGGame.ticks > hittable && !invincible) {
			health -= d;
			hittable = RPGGame.ticks + 26;
			if (!(this instanceof Enemy)) {
				RPGGame.getDamagedObjects().add(this);
				}
		} 

	}
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}

	public static BufferedImage tint(BufferedImage image, Color color) {
		BufferedImage copy = copyImage(image);
	    for (int x = 0; x < copy.getWidth(); x++) {
	        for (int y = 0; y < copy.getHeight(); y++) {
	            Color pixelColor = new Color(copy.getRGB(x, y), true);
	            int r = (pixelColor.getRed() + color.getRed()) / 2;
	            int g = (pixelColor.getGreen() + color.getGreen()) / 2;
	            int b = (pixelColor.getBlue() + color.getBlue()) / 2;
	            int a = pixelColor.getAlpha();
	            int rgba = (a << 24) | (r << 16) | (g << 8) | b;
	            copy.setRGB(x, y, rgba);
	        }
	    }
	    return copy;
	}

}
