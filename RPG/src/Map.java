import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class Map {

	private ArrayList<GameObject> eObjs;
	private ArrayList<Wall> walls;
	private double numChest;
	public static final int OBJ_WIDTH = 50;
	public static final int OBJ_HEIGHT = 50;
	public ArrayList<int[][]> rooms;

	private ArrayList<int[][]> allRooms = new ArrayList<int[][]>();

	public Map(int chest) {
		walls = new ArrayList<Wall>();
		rooms = new ArrayList<int[][]>();
		eObjs = new ArrayList<GameObject>();
		numChest = chest;
		getAllRooms();
		addObjs();
	}

	private void getAllRooms() {
		int[][] room1 = new int[][] { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //0 is where walls are supposed to go
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0 },
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
	
	public void addObjs() {
		int index = (int)(Math.random() * allRooms.size());
		
		for(int r = 0; r < rooms.get(index).length; r++) {
			for(int c = 0; c < rooms.get(index)[r].length; c++) {
				switch(rooms.get(index)[r][c]) {
				case 1:
					walls.add(new Wall(c*OBJ_WIDTH, r* OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 2:
					//eObjs.add(new Crate(c*OBJ_WIDTH, r* OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 3:
					//eObjs.add(new ExplosiveBarrel(c*OBJ_WIDTH, r* OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
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
			room[row][c] = 1;
		}
	}
	
	private void placeVertically(int[][] room, int col, int start, int end) {
		for (int r = start; r < end + 1; r++) {
			room[r][col] = 1;
		}
	}

	public ArrayList<GameObject> getEObjs() {
		return this.eObjs;
	}

	public ArrayList<Wall> getWalls() {
		return this.walls;
	}

}
