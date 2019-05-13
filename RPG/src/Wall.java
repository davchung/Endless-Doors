
public class Wall extends Environment {

	private int health = 100;
	public int getHealth() { return health; }
	private int hittable = 0;

	public Wall(double x, double y, double w, double h) {
		super(x, y, w, h, false, "/sprites/wall_mid.png"); // uses Environment's constructor #1
	}

	public void hit() {
		if (RPGGame.ticks > hittable) {
			health -= 10;
			hittable = RPGGame.ticks + 26;
			return;
		}

	}

}
