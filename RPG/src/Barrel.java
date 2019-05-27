import java.awt.Graphics;

public class Barrel extends GameObject {

	public Barrel(double x, double y, int w, int h) {
		super(x, y, w, h, false, false, 10, "sprites/barrel.png");
	}
	
	@Override
	public void draw(Graphics g) {
		if (image != null) {
			g.drawImage(image, (int) locX, (int) locY, WIDTH, HEIGHT, null);
		}

	}

}
