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
	private static Knight player;
	private double pSpeed = 2.5; // player speed, TRY to keep this a factor of 50, but not obligated
	public static int lastR, lastD; // last direction the player was facing
	private int facing = 1;
	private Demon demon;
	private Map m = new Map(5);
	Floor floor = new Floor();
	
	private Attack pAttack; // player attack
	private static Attack eAttack; // enemy attack
	private Wall builtWall;

	// these are all variables related to GUIs
	private Inventory i = new Inventory(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);
	private ItemShop iS = new ItemShop(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);
	private ChestLoot cL = new ChestLoot(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);
	private HelpPage hP = new HelpPage(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);
	private GameOver gO = new GameOver(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);
	private NextLevel nL = new NextLevel(StartGame.SCREEN_WIDTH * 1 / 10, StartGame.SCREEN_HEIGHT * 1 / 10,
			StartGame.SCREEN_WIDTH * 8 / 10, StartGame.SCREEN_HEIGHT * 8 / 10);

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<GameObject> damagedObjects = new ArrayList<GameObject>();
	private ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();

	// these variables are all "switches" (imagine an on/off switch for a light
	// bulb)
	private boolean objDamaged = false;
	private boolean enemyHit = false;
	private boolean playerHit = false;
	private boolean helpPage = false;
	private boolean gameOver = false;
	private boolean iVisible = false; // inventory visible
	private boolean enemyKilled = false;

	// these are getters for variables
	public static Player getPlayer() {
		return RPGGame.player;
	}

	public Demon getDemon() {
		return this.demon;
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
		player = new Knight(100, 100, 50, 50);
		objects.addAll(m.getWalls());
		objects.addAll(m.getEObjs());
		objects.add(player);
		checkSpawns();
		objects.add(demon);
		enemies.add(demon);
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
				//floor.drawFloor(g);
				for (GameObject go : objects) {
					go.draw(g);
				}
				player.draw(g, facing);

				if (pAttack != null && !pAttack.expire()) {
					pAttack.draw(g);
				}
				if (eAttack != null && !eAttack.expire()) {
					eAttack.draw(g);
				}

				g.drawString("Knight health: " + player.getHealth(), StartGame.SCREEN_WIDTH * 5 / 6, 65);
				g.drawString("Demon health: " + demon.getHealth(), StartGame.SCREEN_WIDTH * 5 / 6, 85);

				g.setColor(new Color(255, 0, 0));

				if (objDamaged == true) {
					for (GameObject go : damagedObjects) {
						if (go.getHealth() < 100 && go.getHealth() > 0 && !go.invincibility()) {
							g.drawString("" + go.getHealth(), (int) go.getCX() - 8, (int) go.getCY());
						}
					}
				}

				if (enemyHit == true) {
					if (demon.getHealth() > 0) {
						g.drawString("-" + player.getDamage(), (int) demon.getCX() - 5, (int) demon.getCY());
					}
					enemyHit = false;
				}
				if (playerHit == true) {
					if (player.getHealth() > 0) {
						g.drawString("-" + demon.getDamage(), (int) player.getCX() - 5, (int) player.getCY());
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

				if (enemyKilled == true) {
					nL.draw(g);
				}
				if (gameOver == true) {
					gO.draw(g);
				}

				if (iVisible == true) {
					i.draw(g);
				}
			}
		};

		mainPanel.setBackground(new Color(40, 200, 240));
		// frame doesn't get minimized
		mainPanel.setPreferredSize(new Dimension(StartGame.SCREEN_WIDTH, StartGame.SCREEN_HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		StartGame.center(mainFrame);
		mainFrame.pack();
		mainFrame.addKeyListener(this);
		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				if (ticks>10)
					movement();
				collision();
				ticks++;
			}

		});
		timer.start();
	}

	private void checkSpawns() {
		demon = new Demon(GameObject.randInt(200, 500), GameObject.randInt(200, 500), 100, 100, 1);
		for (GameObject w : objects) {
			if (demon.collides(w)&&!demon.equals(w)&&!w.throughable) {
				checkSpawns();
				return;
			}
		}
	}

	protected void movement() {
		for (Object enemy : objects) {
			if (enemy instanceof Enemy) {
				((Enemy) enemy).autoMove();
			}
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
			while (player.collides(e) && !e.throughable&&!(player.equals(e))) {
				double dx = player.getCX() - e.getCX();
				double dy = player.getCY() - e.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = pSpeed * dx / m;
				dy = pSpeed * dy / m;
				player.moveX(dx / 5);
				player.moveY(dy / 5);
			}

			// tests if any enemy collides with the pAttack
			if (e instanceof Enemy) {
				if (((Enemy) e).getHealth() <= 0) {
					toRemove.add(e);
					enemyKilled = true;
				}
				if (pAttack != null && pAttack.collides(e)) {
					((Enemy) e).hit(player.getDamage());
					enemyHit = true;
				}
			}
			if(e instanceof Crate || e instanceof Chest || e instanceof Wall || e instanceof ExplosiveBarrel) {
				if (e.getHealth() <= 0)
					toRemove.add(e);
				if ((pAttack != null && pAttack.collides(e)) || (eAttack != null && eAttack.collides(e))) {
					e.hit();
					damagedObjects.add(e);
					objDamaged = true;
				}
			}
		}

		if (player.getHealth() <= 0) {
			toRemove.add(player);
			gameOver = true;
			pause();
		}
		if (eAttack != null && eAttack.collides(player)) {
			player.hit(demon.getDamage());
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
		for (GameObject obs : objects) {
			if (!obs.throughable && object.collides(obs)&&!(obs.equals(object)))
				return true;
		}
		return false;

	}

	// this allows the player to be controlled by W A S D
	private void controls() {
		int down = 0, right = 0;
		if (pAttack == null && builtWall == null) {
			if (keys.contains("w")) {
				player.moveY(-pSpeed);
				down -= 1;
				while (wallCollision(player)) {
					player.moveY(pSpeed / 5);
				}
			}
			if (keys.contains("a")) {
				player.moveX(-pSpeed);
				right -= 1;
				while (wallCollision(player)) {
					player.moveX(pSpeed / 5);
				}
			}
			if (keys.contains("s")) {
				player.moveY(pSpeed);
				down += 1;
				while (wallCollision(player)) {
					player.moveY(-pSpeed / 5);
				}
			}
			if (keys.contains("d")) {
				player.moveX(pSpeed);
				right += 1;
				while (wallCollision(player)) {
					player.moveX(-pSpeed / 5);
				}
			}
			if (down != 0 || right != 0) {
				lastR = right;
				lastD = down;
			}
			if (right != 0) {
				facing = right;
			}
			player.setRight(right);
			player.setDown(down);

			// this allows the j key to control attacking
			if (keys.contains("j")) {
				if (player.canMove(60)) {
					pAttack = player.getAttack();
				}
			}

			// this allows the k key to control building walls
			if (keys.contains("k")) {
				if (player.build(ticks) && i.numWalls > 0) {
					builtWall = new Wall(player.getLocX() - (50 * lastR), player.getLocY() - (50 * lastD),
							Map.OBJ_WIDTH, Map.OBJ_HEIGHT);
					i.removeWalls(1);
					objects.add(builtWall);
				}
			}

			// this allows the l key to control placing bombs
			if (keys.contains("l")) {
				// to be implemented later on
			}
			if (keys.contains("o")) {
				objects.removeAll(getEnemies());
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
		if (gameOver == true && (keys.contains("b"))) {
			objects.clear();
			enemies.clear();
			new RPGGame().beginGame();
			mainFrame.setVisible(false);
			mainFrame.setEnabled(false);
		}

		if (enemyKilled == true && (keys.contains("b"))) {
			gameLevel++;
			System.out.println("Game Level: " + gameLevel);
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
