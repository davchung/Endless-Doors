
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Floor {

	static final File dir=new File("src/img/Sprites/floors"); 
	ArrayList<BufferedImage> floors = new ArrayList<BufferedImage>();

	public Floor() {
		getImages();
	}

	public void makeFloor(Graphics g) {
		for (int r = 0; r < StartGame.SCREEN_HEIGHT; r += Map.OBJ_HEIGHT) {
			for (int c = 0; c < StartGame.SCREEN_WIDTH; c += Map.OBJ_WIDTH) {
				g.drawImage(floors.get(getRandInd()), r, c, Map.OBJ_WIDTH, Map.OBJ_HEIGHT, null);
			}
		}

	}

	private void getImages() {
		System.out.println(dir.isDirectory());
		for (final File f : dir.listFiles()) {

			try {
				floors.add(ImageIO.read(f));

			} catch (final IOException e) {
			}
		}

	}

	private int getRandInd() {
		int r = (int) (Math.random() * floors.size());
		return r;
	}

}
