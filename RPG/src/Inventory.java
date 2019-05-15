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
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Herculanum", 0, 75));
		g.drawString("INVENTORY", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Papyrus", 0, 25));
		yVal += 80;
		g.drawString("Gold: ___ (to be implemented later on)", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Other items will be added soon!", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Comic Sans MS", 0, 12));
		yVal += 80;
		g.drawString("Press I to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
		
		RPGGame.pause();
	}
}
