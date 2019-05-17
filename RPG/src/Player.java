import java.awt.Graphics;

public abstract class Player extends MoveableObject {

	private static int playerHealth = 50;

	private static Animation run = new Animation("knight_f_run",4);
	private static Animation idle = new Animation("knight_f_idle",4);

	// constructor #1 for Player
	public Player(double x, double y, int w, int h) {
		super(x, y, w, h, playerHealth, idle.getFirst()); // uses GameObject's constructor #2
	}
	// these are methods related to drawing
	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0) 
			dx = (int)WIDTH;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(run.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
			return;
		}

		g.drawImage(idle.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);

	}
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
	public Attack getAttack() {
		// TODO Auto-generated method stub
		return null;
	}
}
