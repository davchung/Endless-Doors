
public class ExplosiveBarrel extends GameObject {

	private int health = 100;

	public int getHealth() {
		return health;
	}

	private int hittable = 0;

	public ExplosiveBarrel(double x, double y, double w, double h) {
		super(x, y, w, h, false, false, 25, "env/eb.png");

	}

	public void hit() {
		if (RPGGame.ticks > hittable) {
			health -= 10;
			hittable = RPGGame.ticks + 26;
			return;
		}

	}
}
