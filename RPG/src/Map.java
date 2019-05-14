import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class Map {

	private BufferedImage image;
	static final File imgDir = new File("src/img/randimg");// goes to the file directory randimg
	private ArrayList<GameObject> eObjs;// gets every image in the folder randimg
	private ArrayList<Wall> walls;
	private double numEnv, numChest, imgW, imgH;// amount is how many times each img in randImg gets draw
	public static final int WALL_WIDTH = 50;
	public static final int WALL_HEIGHT = 50;
	public ArrayList<int[][]> rooms;

	private ArrayList<int[][]> allRooms = new ArrayList<int[][]>();

	public Map(int env, int chest) {
		walls = new ArrayList<Wall>();
		rooms = new ArrayList<int[][]>();
		eObjs = new ArrayList<GameObject>();
		numEnv = env;
		numChest = chest;
		imgW = StartGame.SCREEN_WIDTH;
		imgH = StartGame.SCREEN_HEIGHT;
		getAllRooms();
		addWalls();
	}

	private void getAllRooms() {
		int[][] room1 = new int[][] { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //0 is where walls are supposed to go
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				};

		rooms.add(room1);
		for (int[][] r : rooms) {
			outlineWalls(r);  //walls on the outside will be added later on
		}
		
		for(int r = 0; r < room1.length; r++) {
			for(int c = 0; c < room1[0].length; c++) {
				System.out.print(room1[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	public void addWalls() {
		int index = (int)(Math.random() * allRooms.size());
		
		for(int r = 0; r < rooms.get(index).length; r++) {
			for(int c = 0; c < rooms.get(index)[r].length; c++) {
				if(rooms.get(index)[r][c] == 1) {
					walls.add(new Wall(c*WALL_WIDTH, r* WALL_HEIGHT, WALL_WIDTH, WALL_HEIGHT));
				}
			}
		}
	}

	private void outlineWalls(int[][] room) {
		for (int r = 0; r < room.length; r++) {
			room[r][0] = 1;
			room[r][20] = 1;
		}
		for (int c = 1; c < room[0].length; c++) {
			room[0][c] = 1;
			room[14][c] = 1;
		}
	}
	
	private void placeHorizontally(int[][]room, int row, int start, int end) {
		for (int c = start; c < end + 1; c++) {
			room[row][c] += 1;
		}
	}
	
	private void placeVertically(int[][] room, int col, int start, int end) {
		for (int r = start; r < end + 1; r++) {
			room[r][col] += 1;
		}
	}

	public ArrayList<GameObject> getEObjs() {
		return this.eObjs;
	}

	public ArrayList<Wall> getWalls() {
		return this.walls;
	}

}
