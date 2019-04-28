import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Map {

	RPGRunner r;
	private Image image;
	static final File dir = new File("src/img/randimg");// goes to the file directory randimg
	private ArrayList<Image> randImg;// gets every image in the folder randimg
	private int amount, imgW, imgH;// amount is how many times each img in randImg gets draw

	public Map(int x) {
		amount = x;
		imgW = 50;
		imgH = 50;
		getAllImg();
	}

	private void getAllImg() {
		randImg = new ArrayList<Image>();
		Image img = null;
		for (final File f : dir.listFiles()) {// every file in randimg folder
			try {
				randImg.add(ImageIO.read(f));// adds every file to randImg
			} catch (final IOException e) {
			}
		}
	}

	public void draw(Graphics g) {
		randGen(g);
	}

	private void randGen(Graphics g) {
		for (Image i : randImg) {
			for (int c = 0; c < amount; c++) {
				g.drawImage(i, (int) (Math.random() * (r.WIDTH - imgW)), (int) (Math.random() * (r.HEIGHT - imgH)),
						imgW, imgH, null);
			}
		}

	}
}
