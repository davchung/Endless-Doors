

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Floor {

	int[][] floor;
	ArrayList<BufferedImage> floors = new ArrayList<BufferedImage>();

	public Floor() {
		floor = new int[EndlessDoorsRunner.SCREEN_HEIGHT / Map.OBJ_HEIGHT][EndlessDoorsRunner.SCREEN_WIDTH / Map.OBJ_WIDTH];
		makeFloor();
	}

	public void drawFloor(Graphics g) {
		for (int r = 0; r < floor.length; r++) {
			for (int c = 0; c < floor[r].length; c++) {
				g.drawImage(floors.get(floor[r][c]), c * Map.OBJ_WIDTH, r * Map.OBJ_HEIGHT, Map.OBJ_WIDTH,
						Map.OBJ_HEIGHT, null);
			}
		}

	}

	private void makeFloor() {
		getImages();
		for (int r = 0; r < floor.length; r++) {
			for (int c = 0; c < floor[r].length; c++) {
				int randInt = (int)(Math.random()*30);
				if (randInt>7) {
					randInt = 0;
				}
				floor[r][c] = randInt; // randind
			}
		}

	}
	

	private void getImages() {
		String fn ="img/sprites/floors/floor_";
		for (int i = 1;i<10;i++) {
			BufferedImage img = null;
			try {
				img = GameObject.tint(ImageIO.read(this.getClass().getResource(fn + i+ ".png")),Color.gray);
			} catch (IOException e) {

			}
			floors.add(img);
		}
	}

	public void setChestFloor(int xInd, int yInd) {
		floor[yInd / 50][xInd / 50] = floors.size() - 1;
	}

	public void reset() {
		makeFloor();

	}
}
