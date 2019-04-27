import java.awt.*;

public class Enemy extends GameObject {

	private static double locX, locY;
	private final double WIDTH, HEIGHT;
	private Image image;

	public Enemy(double x, double y, double w, double h, String s) {
		super(x, y, w, h, s);
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		image = super.getImage(s);
	}

	public void draw(Graphics g) {
		g.drawRect((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
}
