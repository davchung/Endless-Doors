import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameGUI{

	protected double locX = EndlessDoorsRunner.SCREEN_WIDTH * 1 / 10;
	protected double locY = EndlessDoorsRunner.SCREEN_HEIGHT * 1 / 10;
	protected double WIDTH = EndlessDoorsRunner.SCREEN_WIDTH * 8 / 10;
	protected double HEIGHT = EndlessDoorsRunner.SCREEN_HEIGHT * 8 / 10;
	
	public final static String PATH_PREFIX = "img/gui/";
	protected BufferedImage img;
	
	public static final int TITLE_FONT = 75;
	public static final int BODY_FONT = 25;
	public static final int END_FONT = 18;

	// constructor #1 for GameGUI
	public GameGUI(boolean pg, BufferedImage i) {
	}

	// constructor #2 for GameGUI
	public GameGUI(boolean pg, String s) {
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
