import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class RPGGame implements KeyListener {

	// these are all variables that allow the game to run
	private JFrame mainFrame = new JFrame("Role-Playing Game");
	private JPanel mainPanel;
	private static Timer timer;
	private static final int REFRESH_RATE = 10;
	public static int ticks = 0;

	// these are all variables that are involved with playing the game
	private int gameLevel = 1;
	private static Knight knight;
	private double pSpeed = 3.0; // player speed
	private int lastR, lastD; // last direction the player was facing
	private int facing = 1;
	// kindly refrain from changing this enemy's name
	private Demon eNWIMN; // this stands for "enemyNavigatingWallsIsMyNightmare"
	private Map m = new Map(10, 5);
	private Attack pAttack; // player attack
	private static Attack eAttack; // enemy attack
	private Wall builtWall;

	// these are all variables related to GUIs
	private Inventory i = new Inventory(StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 1 / 4,
			StartGame.SCREEN_WIDTH * 2 / 4, StartGame.SCREEN_HEIGHT * 2 / 4);
	private ItemShop iS = new ItemShop(StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 1 / 4,
			StartGame.SCREEN_WIDTH * 2 / 4, StartGame.SCREEN_HEIGHT * 2 / 4);
	private ChestLoot cL = new ChestLoot(StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 1 / 4,
			StartGame.SCREEN_WIDTH * 2 / 4, StartGame.SCREEN_HEIGHT * 2 / 4);
	private HelpPage hP = new HelpPage(StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 1 / 4,
			StartGame.SCREEN_WIDTH * 2 / 4, StartGame.SCREEN_HEIGHT * 2 / 4);
	private GameOver gO = new GameOver(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Wall> damagedWalls = new ArrayList<Wall>();

	// these variables are all "switches" (imagine an on/off switch for a light
	// bulb)
	private boolean wallDamaged = false;
	private boolean enemyHit = false;
	private boolean playerHit = false;
	private boolean helpPage = false;
	private boolean gameOver = false;
	private boolean iVisible = false; // inventory visible

	// these are getters for variables
	public static Knight getKnight() {
		return RPGGame.knight;
	}

	public Demon getDemon() {
		return this.eNWIMN;
	}

	public static void setEnemyAttack(Attack atk) {
		RPGGame.eAttack = atk;
	}

	public static ArrayList<GameObject> getObjects() {
		return RPGGame.objects;
	}

	public static ArrayList<Enemy> getEnemies() {
		return RPGGame.enemies;
	}

	public void beginGame() {
		gameLevel = 1;
		knight = new Knight(50, 50, 50, 50);
		objects.addAll(m.getWalls());
		objects.add(knight);
		checkSpawns();
		objects.add(eNWIMN);
		enemies.add(eNWIMN);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			// this is where all the drawing is done
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				for (GameObject go : objects) {
					go.draw(g);
				}
				knight.draw(g, facing);

				if (pAttack != null && !pAttack.expire()) {
					pAttack.draw(g);
				}
				if (eAttack != null && !eAttack.expire()) {
					eAttack.draw(g);
				}

				g.drawString("Knight health: " + knight.getHealth(), 675, 65);
				g.drawString("Enemy health: " + eNWIMN.getHealth(), 675, 85);

				g.setColor(new Color(255, 0, 0));
				if (wallDamaged == true) {
					for (Wall dw : damagedWalls) {
						if (dw.getHealth() < 100 && dw.getHealth() > 0) {
							g.drawString("" + dw.getHealth(), (int) dw.getCX() - 8, (int) dw.getCY());
						}
					}
				}
				if (enemyHit == true) {
					if (eNWIMN.getHealth() > 0) {
						g.drawString("-" + knight.getDamage(), (int) eNWIMN.getCX() - 5, (int) eNWIMN.getCY());
					}
					enemyHit = false;
				}
				if (playerHit == true) {
					if (knight.getHealth() > 0) {
						g.drawString("-" + eNWIMN.getDamage(), (int) knight.getCX() - 5, (int) knight.getCY());
					}
					playerHit = false;
				}

				if (helpPage == true) {
					hP.draw(g);
				}
				if (helpPage == false) {
					g.setColor(new Color(255, 255, 255));
					g.drawString("Press ? for help.", 20, 25);
				}
				if (gameOver == true) {
					gO.draw(g);
				}

				if (iVisible == true) {
					i.draw(g);
				}
			}
		};

		mainPanel.setBackground(new Color(150, 250, 150));
		// frame doesn't get minimized
		mainPanel.setPreferredSize(new Dimension(StartGame.SCREEN_WIDTH, StartGame.SCREEN_HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		mainFrame.setLocation(3 * StartGame.SCREEN_WIDTH / 10, 1 * StartGame.SCREEN_HEIGHT / 10);
		mainFrame.pack();
		mainFrame.addKeyListener(this);
		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				movement();
				collision();
				ticks++;
			}

		});
		timer.start();
	}

	private void checkSpawns() {
		eNWIMN = new Demon(GameObject.randInt(200, 500), GameObject.randInt(200, 500), 50, 50, 1);
		for (GameObject w : objects) {
			if (w instanceof Wall && eNWIMN.collides(w)) {
				System.out.println("collided");
				checkSpawns();
				return;
			}
		}
		System.out.println("no problems");
		System.out.println(eNWIMN);
	}

	protected void movement() {
		for (Object enemy : objects) {
			if (enemy instanceof Enemy)
				((Enemy) enemy).autoMove();
		}

	}

	protected void collision() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		if (pAttack != null && pAttack.expire()) {
			pAttack = null;
		}
		if (eAttack != null && eAttack.expire()) {
			eAttack = null;
		}
		if (builtWall != null) {
			builtWall = null;
		}
		for (GameObject e : objects) {

			while (knight.collides(e) && !e.throughable) {
				double dx = knight.getCX() - e.getCX();
				double dy = knight.getCY() - e.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = pSpeed * dx / m;
				dy = pSpeed * dy / m;
				knight.moveX(dx / 10);
				knight.moveY(dy / 10);
			}

			// tests if any enemy collides with the pAttack
			if (e instanceof Enemy) {
				if (((Enemy) e).getHealth() <= 0)
					toRemove.add(e);
				if (pAttack != null && pAttack.collides(e)) {
					((Enemy) e).hit(knight.getDamage());
					enemyHit = true;
				}
			}
			if (e instanceof Wall) {
				if (((Wall) e).getHealth() <= 0)
					toRemove.add(e);
				// both pAttack and eAttack can damage walls
				if ((pAttack != null && pAttack.collides(e)) || (eAttack != null && eAttack.collides(e))) {
					((Wall) e).hit();
					damagedWalls.add((Wall) e);
					wallDamaged = true;
				}
			}
		}
		if (knight.getHealth() <= 0) {
			toRemove.add(knight);
			gameOver = true;
			pause();
		}
		if (eAttack != null && eAttack.collides(knight)) {
			knight.hit(eNWIMN.getDamage());
			playerHit = true;
		}
		objects.removeAll(toRemove);
		enemies.removeAll(toRemove);
		for (GameObject go : toRemove) {
			if (go instanceof Wall) {
				i.addWalls(1);
			}
		}
		walls.removeAll(toRemove);

	}

	private boolean wallCollision(GameObject object) {
		for (GameObject wall : objects) {
			if (wall instanceof Wall && object.collides(wall))
				return true;
		}
		return false;

	}

	// this allows the player to be controlled by W A S D
	private void controls() {
		int down = 0, right = 0;
		if (pAttack == null && builtWall == null) {
			if (keys.contains("w")) {
				knight.moveY(-pSpeed);
				down -= 1;
				while (wallCollision(knight)) {
					knight.moveY(pSpeed / 20);
				}
			}
			if (keys.contains("a")) {
				knight.moveX(-pSpeed);
				right -= 1;
				while (wallCollision(knight)) {
					knight.moveX(pSpeed / 20);
				}
			}
			if (keys.contains("s")) {
				knight.moveY(pSpeed);
				down += 1;
				while (wallCollision(knight)) {
					knight.moveY(-pSpeed / 20);
				}
			}
			if (keys.contains("d")) {
				knight.moveX(pSpeed);
				right += 1;
				while (wallCollision(knight)) {
					knight.moveX(-pSpeed / 20);
				}
			}
			if (down != 0 || right != 0) {
				lastR = right;
				lastD = down;
			}
			if (right != 0) {
				facing = right;
			}
			knight.setRight(right);
			knight.setDown(down);

			// this allows the j key to control attacking
			if (keys.contains("j")) {
				if (knight.attack(40)) {
					pAttack = new Attack((int) knight.getLocX() + 25, (int) knight.getLocY() + 25, lastR, lastD, ticks,
							"sprites/weapon_golden_sword.png");
				}
			}

			// this allows the k key to control building walls
			if (keys.contains("k")) {
				if (knight.build(ticks) && i.numWalls > 0) {
					builtWall = new Wall(knight.getLocX() - (50 * lastR), knight.getLocY() - (50 * lastD), Map.WALL_WIDTH, Map.WALL_HEIGHT);
					i.removeWalls(1);
					objects.add(builtWall);
				}
			}

			// this allows the l key to control placing bombs
			if (keys.contains("l")) {
				// to be implemented later on
			}
		}
	}

	public static void pause() {
		if (timer.isRunning()) {
			timer.stop();
		} else {
			timer.start();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String lower = "" + e.getKeyChar();
		lower = lower.toLowerCase();
		if (!keys.contains(lower)) {
			keys.add(lower);
		}

		// pause button
		if (keys.contains("p")) {
			pause();
			if (helpPage)
				helpPage = false;
		}

		// help button
		if (keys.contains("?")) {
			helpPage = !helpPage;
			mainPanel.repaint();
			pause();
		}

		// check inventory
		if (keys.contains("i")) {
			iVisible = !iVisible;
			if (!iVisible)
				pause();
		}

		// game over
		if (gameOver && (keys.contains("b"))) {
			objects.clear();
			enemies.clear();
			new RPGGame().beginGame();
			mainFrame.setVisible(false);
			mainFrame.setEnabled(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String lower = "" + e.getKeyChar();
		lower = lower.toLowerCase();
		if (keys.contains(lower)) {
			keys.remove(lower);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
