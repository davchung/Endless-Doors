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
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 0 is empty space
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0 }, // 1 is walls
			{ 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0 }, // 2 is crates
			{ 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0 }, // 3 is barrels
			{ 0, 0, 2, 0, 0, 2, 0, 3, 0, 3, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0 },
			{ 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0 },
			{ 0, 2, 2, 0, 0, 2, 3, 0, 0, 3, 2, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0 },
			{ 2, 2, 2, 2, 0, 2, 3, 0, 3, 3, 2, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0 },
			{ 0, 3, 0, 0, 0, 2, 0, 3, 0, 0, 2, 0, 2, 2, 0, 2, 2, 2, 2, 0, 0 },
			{ 0, 3, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0 },
			{ 0, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0 },
			{ 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 3, 2, 2, 2, 2, 2, 2, 2, 3, 3, 0, 0, 0, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 2, 2, 3, 3, 0, 0, 0, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		};
		rooms.add(room1);
		for (int[][] r : rooms) {
			outlineWalls(r);  //walls on the outside will be added later on
		}
		outlineChests(room1);
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
		int count = 0;
		while (numChest > 0) {
			System.out.println("flag1 "+numChest);
			System.out.println(""+count);
			count++;
			int randR = GameObject.randInt(1, 13);
			int randC = GameObject.randInt(1, 19);
			//if (room[randR][randC] == 0) {
				System.out.println("flag2 "+numChest);
				room[randR][randC] = 4;
				numChest--;
			//}
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
