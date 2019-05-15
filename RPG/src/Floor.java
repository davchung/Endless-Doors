
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Floor {


	static final File dir=new File("src/img/Sprites/floors"); 

	static ArrayList<BufferedImage> floors = new ArrayList<BufferedImage>();
	int[][] imgs;

	public Floor() {
		getImages();
		imgs = new int[StartGame.SCREEN_WIDTH/Map.OBJ_WIDTH][StartGame.SCREEN_HEIGHT/Map.OBJ_HEIGHT];
		getRandInt();
	}

	public void makeFloor(Graphics g) {
		for (int r = 0; r < StartGame.SCREEN_WIDTH; r += Map.OBJ_HEIGHT) {
			for (int c = 0; c < StartGame.SCREEN_HEIGHT; c += Map.OBJ_WIDTH) {
				g.drawImage(floors.get(imgs[r/Map.OBJ_HEIGHT][c/Map.OBJ_WIDTH]), r, c, Map.OBJ_WIDTH, Map.OBJ_HEIGHT, null);
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

	private void getRandInt() {
		for (int r = 0; r < StartGame.SCREEN_WIDTH; r += Map.OBJ_HEIGHT) {
			for (int c = 0; c < StartGame.SCREEN_HEIGHT; c += Map.OBJ_WIDTH) {
				imgs[r/Map.OBJ_WIDTH][c/Map.OBJ_HEIGHT]=(int) (Math.random()*floors.size());
			}
		}
	}

}
