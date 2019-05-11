import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Animation {

	File runRight = new File("src/img/animations/playerRun.png");
	String path = "img/sprites/";
	private BufferedImage[] running;
	private BufferedImage startWith;
	private BufferedImage[] anim;
	private double imgH, imgW;

	public Animation() {
		running = new BufferedImage[8];
		getAllImg();
	}

	public Animation(String name, int frames) {
		path += name;
		anim = new BufferedImage[frames];
	}

	private void getAllImg() {

		try {
			startWith = ImageIO.read(runRight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // all the images in the folder
		imgH = startWith.getHeight() / 1; // get image height/width
		imgW = startWith.getWidth() / 8;
		for (int i = 0; i < running.length; i++) {
			running[i] = startWith.getSubimage((int) (i * imgW), 0, (int) imgW, (int) imgH);
		}

	}

	private void getImg() {
		for (int i = 0; i < anim.length; i++) {

			try {

			} catch (Exception e) {
				System.out.println("More frames indicated than in file for " + path);
			}
		}
	}

	public BufferedImage getFirstImage() {
		return running[0];
	}

	public BufferedImage update(int check) {
		if (check != 0) {
			int ticks = RPGRunner.ticks / 5; // smaller = faster
			ticks = ticks % 8;
			return (running[ticks]);

		}
		return getFirstImage();
	}

}
