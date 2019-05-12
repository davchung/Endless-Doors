import java.awt.Graphics;

public class Enemy extends GameObject {
	private double speed = 1.5;
	private static Animation run = new Animation("big_demon_idle", 4);
	private static Animation idle = new Animation("big_demon_idle", 4);
	private double right, down;

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, run.getFirst(), false);
		incrementHealth(10);
	}

	public void setR(double r) {
		right = r;
	}

	public void setD(double d) {
		down = d;
	}

	public double getSpeed() {
		return this.speed;
	}

	@Override
	public void draw(Graphics g) {

		double r = right / Math.abs(right);
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH;
		if (down != 0 || right != 0) {
			g.drawImage(run.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
					(int) super.HEIGHT + 20, null);
			return;
		}

		g.drawImage(idle.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);

	}

	@Override
	public void moveX(double howMuch) {
		if (RPGRunner.ticks > getHittable())
			super.moveX(howMuch);
	}

	@Override
	public void moveY(double howMuch) {
		if (RPGRunner.ticks > getHittable())
			super.moveY(howMuch);
	}
}
