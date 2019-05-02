import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation {

	File runRight = new File("src/img/animations/playerRun.png");
	private BufferedImage[] running;
	private BufferedImage startWith;
	private double imgH, imgW;

	public Animation() {
		running = new BufferedImage[8];
		getAllImg();
	}

	private void getAllImg() {
		
				try {
					startWith = ImageIO.read(runRight);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //all the images in the folder
				imgH = startWith.getHeight()/1; //get image height/width
				imgW = startWith.getWidth()/8;
				for (int i=0;i<running.length;i++) {
					running[i]=startWith.getSubimage((int)(i*imgW), 0, (int)imgW, (int)imgH);
				}

			
	}


	public BufferedImage getFirstImage() {
		return running[0];
	}

	public BufferedImage update(int check, int ticks) {
		if (check != 0) {
			ticks = ticks / 12;
			ticks = ticks % 8;
			return(running[ticks]);

		}
		return getFirstImage();
	}


}
