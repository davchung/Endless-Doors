import java.util.*;

public class Map {

	private ArrayList<GameObject> eObjs;
	private ArrayList<Wall> walls;
	private double numChest;
	public static final int OBJ_WIDTH = 50;
	public static final int OBJ_HEIGHT = 50;
	public ArrayList<int[][]> rooms;

	private ArrayList<int[][]> allRooms = new ArrayList<int[][]>();

	public Map(int numChests) {
		walls = new ArrayList<Wall>();
		rooms = new ArrayList<int[][]>();
		eObjs = new ArrayList<GameObject>();
		numChest = numChests;
		getAllRooms();
		addObjs();
	}

	// these are getters
	public ArrayList<GameObject> getEObjs() {
		return this.eObjs;
	}
	public ArrayList<Wall> getWalls() {
		return this.walls;
	}

	private void getAllRooms() {
		int[][] room1 = new int[][] { // 21 columns and 15 rows
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0 is empty space
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 1 }, // 1 is walls
			{ 1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 }, // 2 is crates
			{ 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 3, 0, 0, 1 }, // 3 is barrels
			{ 1, 0, 2, 0, 0, 2, 0, 3, 0, 3, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1 },
			{ 1, 2, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 3, 0, 0, 1 },
			{ 1, 2, 2, 0, 0, 2, 3, 0, 0, 3, 2, 0, 2, 2, 0, 2, 2, 0, 0, 0, 1 },
			{ 1, 2, 2, 2, 0, 2, 3, 0, 3, 3, 2, 0, 2, 2, 0, 2, 2, 0, 0, 0, 1 },
			{ 1, 3, 0, 0, 0, 2, 0, 3, 0, 0, 2, 0, 2, 2, 0, 2, 2, 2, 2, 0, 1 },
			{ 1, 3, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 },
			{ 1, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1 },
			{ 1, 0, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 3, 2, 2, 2, 2, 2, 2, 2, 3, 3, 0, 0, 0, 2, 0, 0, 1 },
			{ 1, 0, 0, 0, 3, 3, 0, 0, 0, 0, 2, 2, 3, 3, 0, 0, 0, 2, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		};
		rooms.add(room1);
		outlineChests(room1);
		
		// please keep this here - it is to debug the 2D array
		/*for (int r = 0; r < room1.length; r++) {
			for (int c = 0; c < room1[r].length; c++) {
				System.out.print(room1[r][c] + " ");
			}
			System.out.println();
		}*/
	}

	private void outlineChests(int[][] room) {
		while (numChest > 0) {
			room[GameObject.randInt(4, 13)][GameObject.randInt(5, 19)] = 4;
			numChest--;
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
					eObjs.add(new Crate(c*OBJ_WIDTH, r* OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 3:
					eObjs.add(new ExplosiveBarrel(c*OBJ_WIDTH, r* OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 4:
					eObjs.add(new Chest(c*OBJ_WIDTH, r* OBJ_HEIGHT, GameObject.randInt(10, 50)));
				}
			}
		}
	}
}
