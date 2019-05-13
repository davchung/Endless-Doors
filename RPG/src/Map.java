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
	public static final int WALL_WIDTH = 50;
	public static final int WALL_HEIGHT = 50;
	int numberOfRooms;
	
	private int[][] quadII = new int[16][16];

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
		
		fillQuadII();
		makeWalls(quadII);
	}
	
	private void placeHorizontally(int[][] outline, int row, int start, int end) {
		for (int c = start; c < end + 1; c++) {
			outline[row][c] = 1;
		}
	}
	
	private void placeVertically(int[][] outline, int col, int start, int end) {
		for (int r = start; r < end + 1; r++) {
			outline[r][col] = 1;
		}
	}

	private void fillQuadII() {
		placeHorizontally(quadII, 0, 0, 15);
		placeHorizontally(quadII, 5, 6, 9);
		placeHorizontally(quadII, 10, 6, 9);
		placeVertically(quadII, 0, 0, 15);
		placeVertically(quadII, 2, 3, 13);
		placeVertically(quadII, 13, 3, 13);
		placeVertically(quadII, 5, 5, 10);
		placeVertically(quadII, 10, 5, 10);
		quadII[7][5] = 0;
		quadII[8][5] = 0;
		quadII[7][10] = 0;
		quadII[8][10] = 0;
	}
	
	private void makeWalls(int[][] outline) {
		for (int r = 0; r < outline.length; r++) {
			for (int c = 0; c < outline[r].length; c++) {
				if (outline[r][c] == 1) {
					walls.add(new Wall(c * 50, r * 50, WALL_WIDTH, WALL_HEIGHT));
				}
			}
		}
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
		return this.eObjs;
	}

	public ArrayList<Wall> getWalls(){
		return this.walls;
	}

	private void randGen(BufferedImage i) {
		for (int c = 0; c < numEnv; c++) {
			eObjs.add(new Environment((Math.random() * (StartGame.SCREEN_WIDTH - imgW)),
					(Math.random() * (StartGame.SCREEN_HEIGHT - imgH)), imgW, imgH, true, i));
		}
	}

}
