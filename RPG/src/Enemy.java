
public class Enemy extends GameObject {
	private double speed = 1.5;
	public double getSpeed() {
		return this.speed;
	}

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png", false);
		incrementHealth(10);
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
