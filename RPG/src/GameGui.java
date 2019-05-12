import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameGui {
	
	protected double locX, locY;
	protected double WIDTH, HEIGHT;
	private boolean pauseGame; // whether it pauses the game or not
	BufferedImage b;
	
	public GameGui(int x, int y, double w, double h, boolean pg, BufferedImage i) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		pauseGame = pg;
	}
	
	public GameGui(int x, int y, double w, double h, boolean pg, String s) {
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
		pauseGame = pg;
	}
	
	public void draw(Graphics g) {
		g.fillRect((int)locX, (int)locY, (int)WIDTH, (int)HEIGHT);
	}
	
	
}
