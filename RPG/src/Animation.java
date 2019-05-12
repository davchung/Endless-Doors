import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Animation {

	File runRight = new File("src/img/animations/playerRun.png");
	String path = "img/sprites/";
	private BufferedImage[] anim;

	// constructor #1 for Animation
	public Animation(String name, int frames) {
		path += name;
		anim = new BufferedImage[frames];
		getImg();
	}

	private void getImg() {
		for (int i = 0; i < anim.length; i++) {

			try {
				anim[i] = ImageIO.read(this.getClass().getResource(path + "_anim_f" + i + ".png"));
			} catch (Exception e) {
				System.out.println("More frames indicated than in file for " + path);
			}
		}

	}

	public BufferedImage getImage() {
		int ticks = rpgGame.ticks / 10;
		ticks = ticks % anim.length;
		return (anim[ticks]);
	}

	public BufferedImage getFirst() {
		return anim[0];
	}

}
