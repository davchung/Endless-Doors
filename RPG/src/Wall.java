
public class Wall extends Environment {

	private double health;

	public Wall(double x, double y, double w, double h, double health) {
		super(x, y, w, h, "wall.png");
		this.health = health;
	}
}
