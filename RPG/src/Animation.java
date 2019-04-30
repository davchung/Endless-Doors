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
	private int rows = 1, cols = 8;

	public Animation() {
		imgW = 50;
		imgH = 50;
		getAllImg();
	}

	private void getAllImg() {
		aObjs = new ArrayList<BufferedImage[]>();
		for (final File f : dir.listFiles()) {
			try {
				startWith = ImageIO.read(f);
				addTo();
			} catch (final IOException e) {
			}
		}

	}

	private void addTo() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				addOn[(i * cols) + j] = startWith.getSubimage((int)(j * imgW), (int)(i * imgH), (int)imgW, (int)imgH);
			}
		}
		aObjs.add(addOn);
	}

	public void update(int check) {
		
	}
}
