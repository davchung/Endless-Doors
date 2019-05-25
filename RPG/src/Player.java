import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Player extends MoveableObject {

	private static int playerHealth = 50;
	// constructor #1 for Player
	public Player(double x, double y, int w, int h, BufferedImage img) {
		super(x, y, w, h, playerHealth, img); // uses GameObject's constructor #2
		setRect();
		this.speed=2.5;
	}
	private void setRect() {
		Rectangle r = super.getRect();
		Rectangle k = new Rectangle((int) (r.getX() + r.getWidth() / 50), (int) (r.getY() + r.getHeight() / 50),
				(int) (r.getWidth() * 48 / 50), (int) (r.getHeight() * 48 / 50));
		this.current =k;
	}
	
	public void setPlayerLoc(int x, int y) {
		super.setLoc(x, y);
		//setRect();
	}
	
	protected Rectangle getRect() {
		Rectangle r = super.getRect();
		Rectangle k = new Rectangle((int) (r.getX() + r.getWidth() / 50), (int) (r.getY() + r.getHeight() / 50),
				(int) (r.getWidth() * 48 / 50), (int) (r.getHeight() * 48 / 50));
		return k;
	}
	
	// these are methods related to drawing

	@Override
	public void draw(Graphics g) {
		//putting this here to prevent graphics from drawing Player when running through all GameObjects
	}
	public Attack getSpecial() {
		Attack shield = new Shield((int)this.getLocX(), (int)this.getLocY(), this.WIDTH, this.HEIGHT,50);
		return shield;
	}

	// this will allow Player to "build" a wall
	public boolean build(int ticks) {
		if (getCooldown() >= ticks)
			return false;
		setCooldown(ticks + 40);
		return true;
	}
	public abstract void draw(Graphics g, int r);
	public abstract Attack getAttack();
	public abstract boolean canMove();
	protected abstract boolean canSpecial();
	protected abstract void addSpecialCooldown(int i);
	public void checkBounds() {
		while (this.getLocX()<40) {
			this.moveX(10);
		}
		while (this.getLocY()<40) {
			this.moveY(10);
		}
		while (this.getLocX()>StartGame.SCREEN_WIDTH-90) {
			this.moveX(-10);
		}
		while (this.getLocY()>StartGame.SCREEN_HEIGHT-90) {
			this.moveY(-10);
		}
	}
	

}
