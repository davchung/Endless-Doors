
public class Wall extends Environment {

	double health = 20;
	int hittable = 0;

	public Wall(double x, double y, double w, double h, double health) {
		super(x, y, w, h, "wall.png");
		this.health = health;
	}
	
	public void hit() {
		if (RPGRunner.ticks > hittable) {
			health -= 10;
			hittable = RPGRunner.ticks + 26;
			return;
		}

	}

	public int getHealth() {
		return (int) health;
	}
}
