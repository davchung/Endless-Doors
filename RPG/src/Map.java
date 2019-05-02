import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class Map {

	RPGRunner r;
	private BufferedImage image;
	static final File dir = new File("src/img/randimg");// goes to the file directory randimg
	private ArrayList<GameObject> eObjs;// gets every image in the folder randimg
	private double numEnv, numChest, imgW, imgH;// amount is how many times each img in randImg gets draw

	public Map(int env, int chest) {
		numEnv = env;
		numChest = chest;
		imgW = 50;
		imgH = 50;
		getAllImg();
	}

	private void getAllImg() {
		eObjs = new ArrayList<GameObject>();
		for (final File f : dir.listFiles()) {// every file in randimg folder
			try {
				randGen(ImageIO.read(f));// takes in every image and adds it to list
			} catch (final IOException e) {
			}
		}
		randGenChests();
	}

	private void randGenChests() {
		for (int c = 0; c < numChest; c++) {
			eObjs.add(new Chest((Math.random() * (RPGRunner.SCREEN_WIDTH - imgW)), (Math.random() * (RPGRunner.SCREEN_HEIGHT - imgH)), imgW,
					imgH, 100));
		}

	}

	public void draw(Graphics g) {
		for (GameObject e : eObjs) {
			e.draw(g);
		}
	}

	public ArrayList<GameObject> getObjs() {
		return eObjs;
	}

	private void randGen(BufferedImage i) {
		for (int c = 0; c < numEnv; c++) {
			eObjs.add(new Environment((Math.random() * (RPGRunner.SCREEN_WIDTH - imgW)), (Math.random() * (RPGRunner.SCREEN_HEIGHT - imgH)), imgW,
					imgH, i));
		}
	}

}
