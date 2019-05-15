import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameGUI{

	protected double locX = StartGame.SCREEN_WIDTH * 1 / 10;
	protected double locY = StartGame.SCREEN_HEIGHT * 1 / 10;
	protected double WIDTH = StartGame.SCREEN_WIDTH * 8 / 10;
	protected double HEIGHT = StartGame.SCREEN_HEIGHT * 8 / 10;
	
	private boolean pauseGame; // whether it pauses the game or not
	public final static String PATH_PREFIX = "img/gui/";
	protected BufferedImage img;

	public GameGUI(boolean pg, BufferedImage i) {
		pauseGame = pg;
	}

	public GameGUI(boolean pg, String s) {
		pauseGame = pg;
	}

	public void draw(Graphics g) {
		g.fillRect((int)locX, (int)locY, (int)WIDTH, (int)HEIGHT);
	}
	
	protected BufferedImage getImage(String fn) {
		BufferedImage img = null;
		fn = PATH_PREFIX + fn;
		try {
			img = ImageIO.read(this.getClass().getResource(fn));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}