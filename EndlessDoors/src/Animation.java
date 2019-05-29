
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] anim;

	// constructor #1 for Animation
	public Animation(String name, int frames) {
		anim = new BufferedImage[frames];
		getImg("img/sprites/" + name);
	}

	private void getImg(String path) {
		for (int i = 0; i < anim.length; i++) {

			try {
				anim[i] = ImageIO.read(this.getClass().getResource(path + "_anim_f" + i + ".png"));
			} catch (Exception e) {
				System.out.println("More frames indicated than in file for " + path);
				System.out.println("Tried to grab: "+path + "_anim_f" + i + ".png");
			}
		}

	}
	
	public BufferedImage startScreen() {
		int ticks = StartGame.ticks / 10;
		ticks = ticks % anim.length;
		//System.out.println("this is working");
		return (anim[ticks]);
		
	}

	public BufferedImage getImage() {
		int ticks = RPGGame.ticks / 10;
		ticks = ticks % anim.length;
		return (anim[ticks]);
	}

	public BufferedImage getFirst() {
		return anim[0];
	}
	
	public BufferedImage getSecond() {
		return anim[1];
	}
	
	public BufferedImage getThird() {
		return anim[2];
	}

}
