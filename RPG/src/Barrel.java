import java.awt.Graphics;

public class Barrel extends GameObject {

	public Barrel(double x, double y, int w, int h) {
		super(x, y, w, h, false, false, 25, "Sprites/barrel.png");
	}

	public Attack explode() {
		return new Attack((int)(locX - 75), (int)(locY - 75), 150, 150, 30, 10, "explosion.png");
	}
	
	@Override
	public void draw(Graphics g) {
		if (image != null) {
			g.drawImage(image, (int) locX, (int) locY, WIDTH, HEIGHT, null);
		}

	}

}
