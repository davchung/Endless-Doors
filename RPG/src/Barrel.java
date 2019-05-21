
public class Barrel extends GameObject {


	public Barrel(double x, double y, int w, int h) {
		super(x, y, w, h, false, false, 25, "Sprites/barrel.png");
	}

	public void explode() {
		image = getImage("Sprites/hole.png");
		throughable = true;
	}

}
