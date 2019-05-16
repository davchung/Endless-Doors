import java.util.*;

public class Map {

	private ArrayList<GameObject> eObjs;
	private ArrayList<Wall> walls;
	private int numChest;
	private int numPotion;
	private int numCoin;
	public static final int OBJ_WIDTH = 50;
	public static final int OBJ_HEIGHT = 50;
	public ArrayList<int[][]> rooms;

	private ArrayList<int[][]> allRooms = new ArrayList<int[][]>();

	public Map(int numChests, int numPotions, int numCoins) {
		walls = new ArrayList<Wall>();
		rooms = new ArrayList<int[][]>();
		eObjs = new ArrayList<GameObject>();
		numChest = numChests;
		numPotion = numPotions;
		numCoin = numCoins;
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
			{ 1, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 1 }, // 2 is crates
			{ 1, 0, 0, 0, 0, 3, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 }, // 3 is barrels
			{ 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 4 is chests
			{ 1, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 2, 2, 0, 3, 3, 0, 0, 0, 1 }, // 5 is potions
			{ 1, 2, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 2, 0, 0, 3, 0, 0, 0, 1 }, // 6 is coins
			{ 1, 2, 2, 2, 0, 2, 0, 0, 3, 3, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 },
			{ 1, 3, 3, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 2, 2, 2, 0, 1 },
			{ 1, 3, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 2, 2, 2, 2, 2, 3, 3, 0, 0, 0, 0, 0, 2, 2, 0, 1 },
			{ 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2, 2, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, };

			rooms.add(room1);

			for (int[][] r : rooms) {
				outlineWalls(r); // walls on the outside are added using this function
				outlineChests(r);
				outlinePotions(r);
				outlineCoins(r);
			}
			// please keep this here - it is to debug the 2D array
			/*
			 * for (int r = 0; r < room1.length; r++) { for (int c = 0; c < room1[r].length;
			 * c++) { System.out.print(room1[r][c] + " "); } System.out.println(); }
			 */
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

	private void outlineChests(int[][] room) {
		while (numChest > 0) {
			int randR = GameObject.randInt(4, 13);
			int randC = GameObject.randInt(5, 19);
			if (room[randR][randC] == 0) {
				room[randR][randC] = 4;
				numChest--;
			}
		}
	}

	private void outlinePotions(int[][] room) {
		while (numPotion > 0) {
			int randR = GameObject.randInt(4, 13);
			int randC = GameObject.randInt(5, 19);
			if (room[randR][randC] == 0) {
				room[randR][randC] = 5;
				numPotion--;
			}
		}
	}
	
	private void outlineCoins(int[][] room) {
		while (numCoin > 0) {
			int randR = GameObject.randInt(4, 13);
			int randC = GameObject.randInt(5, 19);
			if (room[randR][randC] == 0) {
				room[randR][randC] = 6;
				numCoin--;
			}
		}
	}

	public void addObjs() {
		int index = (int) (Math.random() * allRooms.size());
		for (int r = 0; r < rooms.get(index).length; r++) {
			for (int c = 0; c < rooms.get(index)[r].length; c++) {
				switch (rooms.get(index)[r][c]) {
				case 1:
					walls.add(new Wall(c * OBJ_WIDTH, r * OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 2:
					eObjs.add(new Crate(c * OBJ_WIDTH, r * OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 3:
					eObjs.add(new ExplosiveBarrel(c * OBJ_WIDTH, r * OBJ_HEIGHT, OBJ_WIDTH, OBJ_HEIGHT));
					break;
				case 4:
					eObjs.add(new Chest(c * OBJ_WIDTH, r * OBJ_HEIGHT, GameObject.randInt(10, 50)));
					break;
				case 5:
					eObjs.add(new Potion(c * OBJ_WIDTH, r * OBJ_HEIGHT, GameObject.randInt(0, 1)));
					break;
				case 6:
					eObjs.add(new Coin(c * OBJ_WIDTH, r * OBJ_HEIGHT));
				}
			}
		}
	}
}
