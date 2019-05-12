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
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// these are all variables that are involved with playing the game
	private static Player player;
	private double pSpeed = 3.0; // player speed
	private int lastR, lastD; // last direction the player was facing
	private int facing = 1;
	// kindly refrain from changing this enemy's name
	private Enemy eNWIMN; // this stands for "enemyNavigatingWallsIsMyNightmare"
	private Enemy smarterE; // "smarter enemy" this part is a work in progress
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

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Wall> damagedWalls = new ArrayList<Wall>();

	// these variables are all "switches" (imagine an on/off switch for a light bulb)
	private boolean wallDamaged = false;
	private boolean enemyHit = false;
	private boolean playerHit = false;
	private boolean helpPage = false;
	private boolean gameOver = false;
	private boolean iVisible = false; // inventory visible

	// these are getters for variables
	public static Player getPlayer() {
		return RPGGame.player;
	}
	public Enemy getEnemy() {
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

		player = new Player(50, 50, 50, 50);
		eNWIMN = new Enemy(GameObject.randInt(200, 500), GameObject.randInt(200, 500), 50, 50);
		objects.addAll(m.getWalls());
		objects.add(player);
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
				player.draw(g, facing);

				if (pAttack != null && !pAttack.expire()) {
					pAttack.draw(g);
				}
				if (eAttack != null && !eAttack.expire()) {
					eAttack.draw(g);
				}

				g.drawString("Player health: " + player.getHealth(), 675, 65);
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
						g.drawString("-" + player.getDamage(), (int) eNWIMN.getCX() - 5, (int) eNWIMN.getCY());
					}
					enemyHit = false;
				}
				if (playerHit == true) {
					if (player.getHealth() > 0) {
						g.drawString("-" + eNWIMN.getDamage(), (int) player.getCX() - 5, (int) player.getCY());
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
					g.setColor(new Color(255, 0, 0, 255));
					g.fillRect(0, 0, 1200, 800);

					g.setColor(new Color(255, 255, 255));
					g.setFont(new Font("Times New Roman", 0, 100));
					g.drawString("Game Over!", 350, 350);
					g.setFont(new Font("Times New Roman", 0, 40));
					g.drawString("Press B to return to start.", 400, 500);
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
		mainFrame.setLocation(StartGame.SCREEN_WIDTH / 10, StartGame.SCREEN_HEIGHT / 10);
		mainFrame.pack();
		mainFrame.addKeyListener(this);
		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				eNWIMN.autoMove();
				collision();
				ticks++;
				/*if (ticks % 100 == 0) {
					System.out.println(ticks / 100 + " second");
				}*/
			}

		});
		timer.start();
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
			//if (player.equals(e) || pAttack != null && pAttack.equals(e))
				//continue;
			if (player.collides(e) && !e.throughable) {
				double dx = player.getCX() - e.getCX();
				double dy = player.getCY() - e.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = pSpeed * dx / m;
				dy = pSpeed * dy / m;
				player.moveX(dx);
				player.moveY(dy);
			}

			// tests if any enemy collides with the pAttack
			if (e instanceof Enemy) {
				if (((Enemy) e).getHealth() <= 0)
					toRemove.add(e);
				if (pAttack != null && pAttack.collides(e)) {
					((Enemy) e).hit(player.getDamage());
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
		if (player.getHealth() <= 0) {
			toRemove.add(player);
			gameOver = true;
			pause();
		}
		if (eAttack != null && eAttack.collides(player)) {
			player.hit(eNWIMN.getDamage());
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

	// this allows the player to be controlled by W A S D
	private void controls() {
		int down = 0, right = 0;
		if (pAttack == null && builtWall == null) {
			if (keys.contains("w") || keys.contains("W")) {
				player.moveY(-pSpeed);
				down -= 1;
			}
			if (keys.contains("a") || keys.contains("A")) {
				player.moveX(-pSpeed);
				right -= 1;
			}
			if (keys.contains("s") || keys.contains("S")) {
				player.moveY(pSpeed);
				down += 1;
			}
			if (keys.contains("d") || keys.contains("D")) {
				player.moveX(pSpeed);
				right += 1;
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

			// this allows the J key to control attacking
			if (keys.contains("j") || keys.contains("J")) {
				if (player.attack(ticks)) {
					pAttack = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, lastR, lastD, ticks,
							"sprites/weapon_golden_sword.png");
				}
			}

			// this allows the lowercase k key to control building walls
			if (keys.contains("k")) {
				if (player.build(ticks) && i.numWalls > 0) {
					builtWall = new Wall(player.getLocX() - (50 * lastR), player.getLocY() - (50 * lastD), 50, 50, 100);
					i.removeWalls(1);
					objects.add(builtWall);
				}
			}
			
			// this allows the uppercase K key to control placing bombs
			if (keys.contains("K")) {
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
		if (!keys.contains("" + e.getKeyChar())) {
			keys.add("" + e.getKeyChar());
		}

		// pause button
		if (keys.contains("p") || keys.contains("P")) {
			pause();
			if (helpPage)
				helpPage = false;
			keys.removeAll(objects);
		}

		// help button
		if (keys.contains("?")) {
			helpPage = !helpPage;
			if (!helpPage)
				pause();
			keys.removeAll(objects);
		}

		// check inventory
		if (keys.contains("i") || keys.contains("I")) {
			iVisible = !iVisible;
			if (!iVisible)
				pause();
			keys.removeAll(objects);
		}

		// game over
		if (gameOver && (keys.contains("b") || keys.contains("B"))) {
			new StartGame().init();
			mainFrame.setVisible(false);
			mainFrame.setEnabled(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (keys.contains("" + e.getKeyChar())) {
			keys.remove("" + e.getKeyChar());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
