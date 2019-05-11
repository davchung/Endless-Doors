
public class Wall extends Environment {

	private int health = 20;
	public int getHealth() { return this.health; }
	private int hittable = 0;

	public Wall(double x, double y, double w, double h, int health) {
		super(x, y, w, h, "wall.png", false);
		this.health = health;
	}

	public void hit() {
		if (RPGRunner.ticks > hittable) {
			health -= 10;
			hittable = RPGRunner.ticks + 26;
			return;
		}

	}

}
