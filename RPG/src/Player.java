import java.awt.Graphics;

public class Player extends GameObject {

	private static Animation run = new Animation("knight_f_run",4);
	private static Animation idle = new Animation("knight_f_idle",4);
	private int right, down;

	// constructor #1 for Player
	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, true, idle.getFirst()); // uses GameObject's constructor #2
		incrementHealth(20);
	}

	// setters are over here
	public void setR(int r) {
		this.right = r;
	}
	public void setD(int d) {
		this.down = d;
	}

	// these are methods related to drawing
	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0) 
			dx = (int)WIDTH;
		if (down != 0 || right != 0) {
			g.drawImage(run.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
			return;
		}

		g.drawImage(idle.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);

	}
	@Override
	public void draw(Graphics g) {
		//putting this here to prevent graphics from drawing player when running through all GameObjects
	}

	// this will allow Player to "build" a wall
	public boolean build(int ticks) {
		// TODO Auto-generated method stub
		return false;
	}
}
