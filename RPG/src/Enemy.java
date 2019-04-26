import java.awt.*;

public class Enemy extends GameObject {

	private static double locX;
	private static double locY;
	private final double WIDTH;
	private final double HEIGHT;

	public Enemy(int x, int y, double w, double h) {
		super(x, y, w, h);
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}

	public void draw(Graphics g) {
		g.drawRect((int) locX, (int) locY, (int) WIDTH, (int) HEIGHT);
	}
}
