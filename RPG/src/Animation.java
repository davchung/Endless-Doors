import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation {
	static final File dir = new File("src/img/animations");
	private ArrayList<BufferedImage[]> aObjs;
	private BufferedImage[] addOn;
	private BufferedImage startWith;
	private double imgH, imgW;
	private int rows = 4, cols = 8;
	RPGRunner r;
	public Animation() {
		addOn = new BufferedImage[rows*cols];
		getAllImg();
	}

	private void getAllImg() {
		aObjs = new ArrayList<BufferedImage[]>();
		for (final File f : dir.listFiles()) {
			try {
				startWith = ImageIO.read(f); //all the images in the folder
				imgH = startWith.getHeight()/rows; //get image height/width
				imgW = startWith.getWidth()/cols;
				addTo();
			} catch (IOException e) {
			}
		}
	}


	private void addTo() {
		for (int i = 0; i < rows; i++) { //for every row and col add part of the image to addon
			for (int j = 0; j < cols; j++) {
				addOn[(i * cols) + j] = startWith.getSubimage((int) (j * imgW), (int) (i * imgH), (int) imgW,
						(int) imgH);
			}
		}
		aObjs.add(addOn); //add all the image's parts to the main list and cycle to next image
	}
	
	public BufferedImage getFirstImage() {
		return (aObjs.get(0))[0];
	}

	public void update(int check, int ticks) {
		if (check != 0) {
			ticks = ticks / 25;
			ticks = ticks % 8;
			switch (ticks) {
			case 0:
				r.getPlayer().setBufferedImage((aObjs.get(0))[0]); //after some time, make player's image to 1st stage run
				break;
			case 1:
				r.getPlayer().setBufferedImage((aObjs.get(0))[1]);//then 2nd stage run, etc
				break;
			case 2:
				r.getPlayer().setBufferedImage((aObjs.get(0))[2]);
				break;
			case 3:
				r.getPlayer().setBufferedImage((aObjs.get(0))[3]);
				break;
			case 4:
				r.getPlayer().setBufferedImage((aObjs.get(0))[4]);
				break;
			case 5:
				r.getPlayer().setBufferedImage((aObjs.get(0))[5]);
				break;
			case 6:
				r.getPlayer().setBufferedImage((aObjs.get(0))[6]);
				break;
			case 7:
				r.getPlayer().setBufferedImage((aObjs.get(0))[7]);
				break;
			}

		}
	}


}