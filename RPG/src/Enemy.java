import java.awt.Graphics;

public class Enemy extends GameObject {
	
	private static Animation run = new Animation("big_demon_idle", 4);
	private static Animation idle = new Animation("big_demon_idle", 4);
	private double eSpeed = 1.5; // enemy speed

	// constructor #1 for Enemy
	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, false, run.getFirst()); // uses GameObject's constructor #2
	}

	// getters and setters are here
	public double getSpeed() {
		return this.eSpeed;
	}

	@Override
	public void draw(Graphics g) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(run.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
					(int) super.HEIGHT + 20, null);
			return;
		}
		g.drawImage(idle.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);
	}

	// these methods are for movement
	@Override
	public void moveX(double howMuch) {
		if (RPGGame.ticks > getHittable())
			super.moveX(howMuch);
	}
	@Override
	public void moveY(double howMuch) {
		if (RPGGame.ticks > getHittable())
			super.moveY(howMuch);
	}
}
