import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Item {

	private String pathName;
	private int goldCost;
	public static final String PATH_PREFIX = "img/sprites/items";
	
	public Item(String pN, int gold) {
		pathName = pN;
		goldCost = gold;
	}
	
	public int getGoldCost() {
		return goldCost;
	}
	
	public BufferedImage getImage() {
		String path;
		BufferedImage img = null;
		path = PATH_PREFIX + pathName;
		try {
			img = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
