import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class Map {

	private BufferedImage image;
	static final File imgDir = new File("src/img/randimg");// goes to the file directory randimg
	static final File roomDir = new File("src/img/rooms");
	private ArrayList<GameObject> eObjs;// gets every image in the folder randimg
	private ArrayList<Wall> walls;
	private double numEnv, numChest, imgW, imgH;// amount is how many times each img in randImg gets draw
	private ArrayList<BufferedImage> rooms;
	int randomRoom;
	int wallHeight = 50;
	int wallWidth = 50;
	int numberOfRooms;

	public Map(int env, int chest) {
		walls = new ArrayList<Wall>();
		rooms = new ArrayList<BufferedImage>();
		eObjs = new ArrayList<GameObject>();
		numEnv = env;
		numChest = chest;
		imgW = 50;
		imgH = 50;
		getAllImg();
		getAllRooms();
		randomRoom = (int) (Math.random() * numberOfRooms);
		putWalls();
	}

	private void getAllRooms() {
		for (final File f : roomDir.listFiles()) {// every file in randimg folder
			numberOfRooms++;
			try {
				rooms.add(ImageIO.read(f));
			} catch (final IOException e) {
			}
		}
	}

	private void putWalls() {
		for (int x = 0; x < rooms.get(randomRoom).getWidth(); x += wallWidth) {
			for (int y = 0; y < rooms.get(randomRoom).getHeight(); y += wallHeight) {
				int c = rooms.get(randomRoom).getRGB(x, y);
				Color color = new Color(c);
				if (color.getBlue() == 0 && color.getRed() == 0 && color.getGreen() == 0) {
					walls.add(new Wall(x, y, wallWidth, wallHeight, 100));
				}
			}
		}

	}

	private void getAllImg() {
		for (final File f : imgDir.listFiles()) {// every file in randimg folder
			try {
				randGen(ImageIO.read(f));// takes in every image and adds it to list
			} catch (final IOException e) {
			}
		}
		randGenChests();
	}

	private void randGenChests() {
		for (int c = 0; c < numChest; c++) {
			eObjs.add(new Chest((Math.random() * (StartGame.SCREEN_WIDTH - imgW)),
					(Math.random() * (StartGame.SCREEN_HEIGHT - imgH)), imgW, imgH, 100));
		}

	}

	public ArrayList<GameObject> getEObjs() {
		return eObjs;
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}

	private void randGen(BufferedImage i) {
		for (int c = 0; c < numEnv; c++) {
			eObjs.add(new Environment((Math.random() * (StartGame.SCREEN_WIDTH - imgW)),
					(Math.random() * (StartGame.SCREEN_HEIGHT - imgH)), imgW, imgH, i, true));
		}
	}

}
