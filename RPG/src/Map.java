import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Map {

	RPGRunner r;
	private Image image;
	static final File dir = new File("src/img/randimg");// goes to the file directory randimg
	private ArrayList<GameObject> eObjs;// gets every image in the folder randimg
	private double amount, imgW, imgH;// amount is how many times each img in randImg gets draw

	public Map(int x) {
		amount = x;
		imgW = 50;
		imgH = 50;
		getAllImg();
	}

	private void getAllImg() {
		eObjs = new ArrayList<GameObject>();
		Image img = null;
		for (final File f : dir.listFiles()) {// every file in randimg folder
			try {
				randGen(ImageIO.read(f));//takes in every image and adds it to list
			} catch (final IOException e) {
			}
		}
	}

	public void draw(Graphics g) {
		for(GameObject e: eObjs) {
			e.draw(g);
		}
	}
	
	public ArrayList<GameObject> getObjs(){
		return eObjs;
	}

	private void randGen(Image i) {
		for (int c = 0; c < amount; c++) {
			eObjs.add(new Environment((Math.random() * (r.WIDTH - imgW)), (Math.random() * (r.HEIGHT - imgH)), imgW,
					imgH, i));
		}
	}


}
