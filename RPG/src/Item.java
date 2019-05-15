
import java.io.File;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Item {

	private String pathName;
	private String name;
	private int goldCost;
	private final String PATH_PREFIX = "img/sprites/items";
	
	public Item(String pN, int gold) {
		pathName = pN;
		goldCost = gold;
		name = "";
		name();
	}
	
	public void name() {
		String[] delim = pathName.split("_");
		for(String s: delim) {
			s.toUpperCase();
			name += s;
			name += " ";
		}
		
	}
	public String getName() {
		return name;
	}
	public int getGoldCost() {
		return goldCost;
	}
	
	public String getPathName() {
		return pathName;
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
