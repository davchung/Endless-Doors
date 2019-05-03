
public class Enemy extends GameObject {
	int health = 20;
	int hittable = 0;
	private double speed = 0.5;

	public double getSpeed() {
		return this.speed;
	}

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png");
	}

	protected void moveTowardPlayer(double x, double y) {
		moveX(x);
		moveY(y);

	}

	public void hit() {
		if (RPGRunner.ticks > hittable) {
			health -= 10;
			hittable = RPGRunner.ticks + 81;
			return;
		}
		
	}

	public int getHealth() {
		return health;
	}
}
