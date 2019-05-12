import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Inventory extends GameGui {

	public int numWalls = 0;

	public Inventory(int x, int y, double w, double h) {
		super(x, y, w, h, true, "inventorybkgrnd.jpg");
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}

	public void addWalls(int num) {
		numWalls += num;
	}

	public void removeWalls(int num) {
		numWalls -= num;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times New Roman", 0, 40));
		g.drawString("INVENTORY", (int) (locX + WIDTH * 1 / 2), 0);
		g.drawString("Walls: " + numWalls, 100, (int) (locY + HEIGHT * 1 / 4));
		g.drawString("Gold: ___ (to be implemented later on)", 100, (int) (locY + HEIGHT * 1 / 4 +25));
		RPGGame.pause();
	}
}
