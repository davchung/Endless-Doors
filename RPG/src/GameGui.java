import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameGui {

	protected double locX = StartGame.SCREEN_WIDTH * 1 / 10;
	protected double locY = StartGame.SCREEN_HEIGHT * 1 / 10;
	protected double WIDTH = StartGame.SCREEN_WIDTH * 8 / 10;
	protected double HEIGHT = StartGame.SCREEN_HEIGHT * 8 / 10;
	private boolean pauseGame; // whether it pauses the game or not
	BufferedImage b;

	public GameGui(boolean pg, BufferedImage i) {
		pauseGame = pg;
	}

	public GameGui(boolean pg, String s) {
		pauseGame = pg;
	}

	public void draw(Graphics g) {
		g.fillRect((int)locX, (int)locY, (int)WIDTH, (int)HEIGHT);
	}


}
