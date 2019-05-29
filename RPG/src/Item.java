import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Item {

	private int goldCost;
	private BufferedImage img;
	public static final String PATH_PREFIX = "img/sprites/items/";
	
	// constructor
	public Item(String pN) {
		img = getImage(pN);
	}
	
	// these are getters
	public int getGoldCost() {
		return goldCost;
	}
	
	public void setGoldCost(int gold) {
		goldCost = gold;
	}
	public BufferedImage getImg() {
		return img;
	}
	
	public BufferedImage getImage(String fn) {
		String path;
		BufferedImage img = null;
		path = PATH_PREFIX + fn;
		try {
			img = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
