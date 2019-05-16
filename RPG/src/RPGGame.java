import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JOptionPane;

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
	private Trader trader;
	private Map m = new Map(5);
	private Floor floor = new Floor();

	private Attack pAttack; // player attack
	private static Attack eAttack; // enemy attack
	private Wall builtWall;

	// these are all variables related to GUIs
	private Inventory i = new Inventory();
	private ChestLoot cL = new ChestLoot();
	private HelpPage hP = new HelpPage();
	private GameOver gO = new GameOver();
	private NextLevel nL = new NextLevel();
	private TradingPost tP = new TradingPost();

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<GameObject> damagedObjects = new ArrayList<GameObject>();
	private	static ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
	private static ArrayList<Item> inventory = new ArrayList<Item>();

	// these variables are all "switches" (imagine an on/off switch for a light
	// bulb)
	private boolean objDamaged = false;
	private boolean helpShown = false;
	private boolean gameOver = false;
	private boolean invenShown = false; // inventory shown
	private boolean levelDone = false;
	private boolean tradeOpen = false;

	// these are getters for variables
	public static Player getPlayer() {
		return RPGGame.player;
	}

	public Demon getDemon() {
		return this.demon;
	}

	public static void setEnemyAttack(Attack atk) {
		RPGGame.eAttack = atk;
		enemyAttacks.add(atk);
	}

	public static ArrayList<GameObject> getObjects() {
		return RPGGame.objects;
	}

	public static ArrayList<Enemy> getEnemies() {
		return RPGGame.enemies;
	}
	public static ArrayList<Item> getInventory() {
		return RPGGame.inventory;
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
		trader = new Trader();
		objects.add(trader);

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
				g.drawRect(250, 200, 50, 50);

				if (pAttack != null && !pAttack.expire()) {
					pAttack.draw(g);
				}
				for (Attack e:enemyAttacks) {
					e.draw(g);
				}

				g.drawString("Knight health: " + player.getHealth(), StartGame.SCREEN_WIDTH * 5 / 6, 65);
				g.drawString("Demon health: " + demon.getHealth(), StartGame.SCREEN_WIDTH * 5 / 6, 85);

				g.setColor(new Color(255, 0, 0));
				for (GameObject go:objects) {
					if (go instanceof MoveableObject&&((MoveableObject) go).getLoss()!=0) {
						g.drawString(""+-((MoveableObject) go).getLoss(), (int)go.getCX()-5, (int)go.getCY());
					}
				}
				if (objDamaged == true) {
					for (GameObject go : damagedObjects) {
						if (go.getHealth() < 100 && go.getHealth() > 0 && !go.getInvincibility()) {
							g.drawString("" + go.getHealth(), (int) go.getCX() - 8, (int) go.getCY());
						}
					}
				}

				if (helpShown == true) {
					hP.draw(g);
				}
				if (helpShown == false) {
					g.setColor(new Color(255, 255, 255));
					g.drawString("Press ? for help.", 20, 25);
				}

				if (levelDone == true) {
					nL.draw(g);
				}
				if (gameOver == true) {
					gO.draw(g);
				}

				if (invenShown == true) {
					i.draw(g);
				}
				if (tradeOpen == true) {
					tP.draw(g);
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
				if (ticks>50||ticks==0)
					movement();
				collision();
				update(); //updates last health
				ticks++;
			}

		});
		timer.start();
	}

	protected void update() {
		for (Object m: objects) {
			if (m instanceof MoveableObject)
				((MoveableObject) m).update();
		}
		for (Attack e:enemyAttacks) {
			e.update();
		}

	}

	private void checkSpawns() {
		int x = GameObject.randInt(300, StartGame.SCREEN_WIDTH-150)/50;
		int y = GameObject.randInt(300, StartGame.SCREEN_HEIGHT-150)/50;
		demon = new Demon(x*50,y*50 , 100, 100, 1);
		for (GameObject w : objects) {
			if (demon.collides(w)&&!demon.equals(w)&&!w.throughable) {
				checkSpawns();
				return;
			}
		}
	}

	protected void movement() {
		for (Object enemy : enemies) {
			((Enemy) enemy).autoMove();
		}

	}

	protected void collision() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		if (pAttack != null && pAttack.expire()) {
			pAttack = null;
		}
		for (Attack e:enemyAttacks) {
			if (e.expire()) {
				enemyAttacks.remove(e);
			}
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
					levelDone = true;
				}
				if (pAttack != null && pAttack.collides(e)) {
					((Enemy) e).hit(player.getDamage());
				}
			}
			if(e instanceof Crate || e instanceof Chest || e instanceof Wall || e instanceof ExplosiveBarrel) {
				if (e.getHealth() <= 0)
					toRemove.add(e);
				if ((pAttack != null && pAttack.collides(e)) ) {
					e.hit();
					damagedObjects.add(e);
					objDamaged = true;
				}
				for (Attack a:enemyAttacks) {
					if (a.collides(e)) {
						e.hit();
						damagedObjects.add(e);
						objDamaged = true;
					}
				}
			}
		}

		if (player.getHealth() <= 0) {
			toRemove.add(player);
			gameOver = true;
			pause();
		}
		for (Attack e:enemyAttacks) {
			if (e.collides(player)) {
				player.hit(demon.getDamage());
			}
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

			// this allows the j key to control attacking (main commands)
			if (keys.contains("j")) {
				if (player.canMove(60)) {
					pAttack = player.getAttack();
				}
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

		// for developers only!
		if (keys.contains("o")) {
			objects.removeAll(getEnemies());
			player.setInvincibility(true);
		}

		// pause button
		if (keys.contains("p")) {
			pause();
			if (helpShown)
				helpShown = false;
		}

		// help button
		if (keys.contains("?")) {
			helpShown = !helpShown;
			mainPanel.repaint();
			pause();
		}

		// check inventory
		if (keys.contains("i")) {
			invenShown = !invenShown;
			mainPanel.repaint();
			pause();
		}

		// game over
		if (gameOver == true && (keys.contains("n"))) {
			objects.clear();
			enemies.clear();
			this.enemyAttacks.clear();
			new RPGGame().beginGame();
			mainFrame.dispose();
		}

		// next level
		if (levelDone == true && (keys.contains("n"))) {
			gameLevel++;
			System.out.println("Game Level: " + gameLevel);
		}

		// trading post
		if (keys.contains("k") && player.collides(trader)) {
			tradeOpen = !tradeOpen;
			mainPanel.repaint();
			pause();
		}

		// trading post - buy option 1
		if (keys.contains("1") && tradeOpen == true) {
			JOptionPane.showConfirmDialog(null, "Are you sure you want to purchase [1] ?");
			inventory.add(new Weapon("axe.png",20));
			JOptionPane.showMessageDialog(null, "[1] has been added to your Inventory.");
		}
		// trading post - buy option 2
		if (keys.contains("2") && tradeOpen == true) {
			JOptionPane.showConfirmDialog(null, "Are you sure you want to purchase [2] ?");
			JOptionPane.showMessageDialog(null, "This feature does not work yet.");
		}
		// trading post - buy option 3
		if (keys.contains("3") && tradeOpen == true) {
			JOptionPane.showConfirmDialog(null, "Are you sure you want to purchase [3] ?");
			JOptionPane.showMessageDialog(null, "This feature does not work yet.");
		}
		// trading post - buy option 4
		if (keys.contains("4") && tradeOpen == true) {
			JOptionPane.showConfirmDialog(null, "Are you sure you want to purchase [4] ?");
			JOptionPane.showMessageDialog(null, "This feature does not work yet.");
		}
		// trading post - buy option 5
		if (keys.contains("5") && tradeOpen == true) {
			JOptionPane.showConfirmDialog(null, "Are you sure you want to purchase [5] ?");
			JOptionPane.showMessageDialog(null, "This feature does not work yet.");
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
