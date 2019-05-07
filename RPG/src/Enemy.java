
public class Enemy extends GameObject {
	private int health = 20;
	public int getHealth() { return this.health; }
	private int hittable = 0;
	private double speed = 1.5;

	public double getSpeed() {
		return this.speed;
	}

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png", false);
	}

	protected void moveTowardPlayer(double x, double y) {
		moveX(x);
		moveY(y);

	}

	public void hit() {
		if (RPGRunner.ticks > hittable) {
			health -= 10;
			hittable = RPGRunner.ticks + 26;
			return;
		}

	}

	@Override
	public void moveX(double howMuch) {
		if (RPGRunner.ticks > hittable)
			super.moveX(howMuch);
	}

	@Override
	public void moveY(double howMuch) {
		if (RPGRunner.ticks > hittable)
			super.moveY(howMuch);
	}
}
