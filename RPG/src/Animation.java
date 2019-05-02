import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation {

	File run = new File("src/img/animations/playerRun.png");
	private BufferedImage[] running;
	private BufferedImage startWith;
	private double imgH, imgW;

	public Animation() {
		running = new BufferedImage[8];
		getAllImg();
	}

	private void getAllImg() {
		
				try {
					startWith = ImageIO.read(run);
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
			ticks = ticks / 25;
			ticks = ticks % 8;
			return(running[ticks]);
//			switch (ticks) {
//			case 0:
//				r.getPlayer().setBufferedImage(running[0]); //after some time, make player's image to 1st stage run
//				break;
//			case 1:
//				r.getPlayer().setBufferedImage(running[1]);//then 2nd stage run, etc
//				break;
//			case 2:
//				r.getPlayer().setBufferedImage(running[2]);
//				break;
//			case 3:
//				r.getPlayer().setBufferedImage(running[3]);
//				break;
//			case 4:
//				r.getPlayer().setBufferedImage(running[4]);
//				break;
//			case 5:
//				r.getPlayer().setBufferedImage(running[5]);
//				break;
//			case 6:
//				r.getPlayer().setBufferedImage(running[0]);
//				break;
//			case 7:
//				r.getPlayer().setBufferedImage(running[7]);
//				break;
//			}

		}
		return getFirstImage();
	}


}
