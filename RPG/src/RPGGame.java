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
	private static Player player;
	private double pSpeed = 2.5; // player speed, TRY to keep this a factor of 50, but not obligated
	public static int lastR, lastD; // last direction the player was facing
	private int facing = 1;
	private Trader trader;
	private Map m;
	Portal p;
	private Floor floor = new Floor();
	// private Attack pAttack; // player attack

	// these are all variables related to GUIs
	private static Inventory i = new Inventory();
	private HelpPage hP = new HelpPage();
	private GameOver gO = new GameOver();
	private TradingPost tP = new TradingPost();

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<GameObject> damagedObjects = new ArrayList<GameObject>();
	private static ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
	private static ArrayList<Attack> special = new ArrayList<Attack>();
	private static ArrayList<Attack> primary = new ArrayList<Attack>();

	// these variables are all "switches" (imagine an on/off switch for a light
	// bulb)
	private boolean objDamaged = false;
	private boolean helpShown = false; // this makes the help page show up when
	// the user first opens the game so that they know how to play the game
	private boolean gameOver = false;
	private boolean invenShown = false; // inventory shown
	private boolean levelDone = false;
	private boolean tradeOpen = false;

	// these are getters for variables
	public static Player getPlayer() {
		return RPGGame.player;
	}

	public static ArrayList<Attack> getPrimary() {
		return primary;
	}

	public static ArrayList<Attack> getEnemyAttacks() {
		return enemyAttacks;
	}

	public static void setEnemyAttack(Attack atk) {
		enemyAttacks.add(atk);
	}

	public static ArrayList<GameObject> getObjects() {
		return RPGGame.objects;
	}

	public void setEnemies(ArrayList<Enemy> list) {
		for (Enemy e : list) {
			checkSpawns(e);
		}
	}

	public static ArrayList<Enemy> getEnemies() {
		return RPGGame.enemies;
	}

	public static ArrayList<Attack> getSpecial() {
		return RPGGame.special;
	}

	public static Inventory getInventory() {
		return i;
	}

	public void beginGame() {
		player = new Archer(StartGame.SCREEN_HEIGHT / 2, StartGame.SCREEN_WIDTH / 2);
		m = new Map();
		objects.addAll(m.getWalls());
		objects.addAll(m.getEObjs());
		objects.add(player);

		trader = new Trader();
		objects.add(trader);

		ArrayList<Enemy> list = new ArrayList<Enemy>();
		Demon d = null;
		Demon a = null;
		list.add(d);
		list.add(a);
		setEnemies(list);

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
				floor.drawFloor(g); // draws a floor...kinda

				for (GameObject go : objects) {
					go.draw(g); // draws all objects
				}
				player.draw(g, facing); // draws the player
				for (Attack a : special) {
					if (!a.expire()) {
						a.draw(g);
					}
				}
				for (Attack e : enemyAttacks) {
					e.draw(g);// draws all enemy attacks
				}
				for (Attack p : primary) {
					p.draw(g);
				}
				drawHitboxes(g); // draws all hitboxes. Dev-only.

				g.setColor(new Color(255, 0, 0));
				for (GameObject go : enemies) {
					if (go instanceof MoveableObject && ((MoveableObject) go).getLoss() != 0) {
						g.drawString("" + -((MoveableObject) go).getLoss(), (int) go.getCX() - 10, (int) go.getCY());
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
					g.drawString("Press ? for help.", 30, 25);
				}

				if (levelDone == true) {
					findEmptyPlace("portal");
					if(player.collides(p) && keys.contains("j")) {
						objects.removeAll(m.getEObjs());
						m.addObjs();
						objects.addAll(m.getEObjs());
						findEmptyPlace("player");
						objects.remove(p);
						damagedObjects.clear();
						enemies.clear();
					}
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
				if (ticks > 50 || ticks == 0)
					movement();
				collision();
				update(); // updates movement

				/**
				 * int <= System.getCurrentTimeMillis();
				 * 3000
				 */
				ticks++;
			}

		});
		timer.start();
	}

	protected void update() {
		for (Object m : objects) {
			if (m instanceof MoveableObject) {
				((MoveableObject) m).update();
			}
		}
		for (int i = 0; i < enemyAttacks.size(); i++) {
			enemyAttacks.get(i).update();
			if (player instanceof Knight) {
				for (Attack spec : special) {
					if (spec.collides(enemyAttacks.get(i))) {
						spec.change(enemyAttacks.get(i));
						i--;
					}
				}
			}
		}
		for (Attack e : primary) {
			e.update();
		}
		for (Attack e : special) {
			e.update();
		}
		if (enemies.isEmpty()) {
			levelDone = true;
		}
		objects.removeAll(enemies);
		objects.addAll(enemies);
		objects.removeAll(enemyAttacks);
		objects.addAll(enemyAttacks);
		objects.removeAll(special);
		objects.addAll(special);
	}

	protected void drawHitboxes(Graphics g) {
		// g.drawRect((int) player.getLocX(), (int) player.getLocY(), (int)
		// player.WIDTH, (int) player.WIDTH); // hitbox

		for (GameObject e : objects) {
			if (e instanceof MoveableObject) {
				g.drawRect((int) e.getLocX(), (int) e.getLocY(), e.WIDTH, e.HEIGHT);
			}
		}
		for (Attack r : enemyAttacks) {
			g.drawRect((int) (r.getLocX() + r.WIDTH / 10), (int) (r.getLocY() + r.HEIGHT / 10),
					(int) (r.WIDTH * 8 / 10), (int) (r.HEIGHT * 8 / 10));//
		}
		for (Attack p : primary) {
			g.drawRect((int) (p.getLocX() + p.WIDTH / 10), (int) (p.getLocY() + p.HEIGHT / 10),
					(int) (p.WIDTH * 8 / 10), (int) (p.HEIGHT * 8 / 10));
		}

	}

	private void checkSpawns(Enemy e2) {
		int x = GameObject.randInt(300, StartGame.SCREEN_WIDTH - 150) / 50;
		int y = GameObject.randInt(300, StartGame.SCREEN_HEIGHT - 150) / 50;
		e2 = new Demon(x * 50, y * 50, 1);

		for (GameObject w : objects) {
			if (!e2.equals(w) && !w.throughable && e2.collides(w)) {
				checkSpawns(e2);
				return;
			}
		}
		enemies.add(e2);
		objects.add(e2);
	}

	protected void movement() {
		for (Enemy enemy : enemies) {
			enemy.autoMove();
		}

	}

	protected void collision() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		for (Attack p : primary) {
			if (p.expire())
				toRemove.add(p);
		}
		for (Attack a : special) {
			if (a.expire()) {
				toRemove.add(a);
			}
		}
		for (Attack e : enemyAttacks) {
			if (e.expire()) {
				toRemove.add(e);
			}
		}
		for (GameObject objs : objects) {// collision for gameobjects
			while (player.collides(objs) && !objs.throughable && !(player.equals(objs))) {
				double dx = player.getCX() - objs.getCX();
				double dy = player.getCY() - objs.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = pSpeed * dx / m;
				dy = pSpeed * dy / m;
				player.moveX(dx / 5);
				player.moveY(dy / 5);
			}

			// tests if any enemy collides with the pAttack
			if (objs instanceof Enemy) {
				if (((Enemy) objs).getHealth() <= 0) {
					toRemove.add(objs);
				}
				for (Attack p : primary) {
					if (p.collides(objs)) {
						objs.hit(p.getDamage(), p.getgameID());
					}
				}
			}
			if (!objs.invincible && !(objs instanceof Enemy)) {
				if (objs.getHealth() <= 0 && !(objs instanceof Barrel)) {
					toRemove.add(objs);
				}
				if (objs instanceof Barrel && objs.getHealth() <= 0) {
					((Barrel) objs).explode();
				}
				for (Attack p : primary) {
					if (p.collides(objs) && !(objs instanceof Player)) {
						objs.hit(p.getDamage(), p.getgameID());
						damagedObjects.add(objs);
						objDamaged = true;
					}
				}
				for (Attack a : enemyAttacks) {
					if (a.collides(objs)) {
						objs.hit(a.getDamage(), a.getgameID());
						damagedObjects.add(objs);
						objDamaged = true;
					}
				}
			}

			if (player.collides(objs) && objs instanceof Coin) {
				toRemove.add(objs);
			}
			if (!objs.throughable) {
				for (Attack p:primary) {
					if (player instanceof Archer&&p.collides(objs))
						toRemove.add(p);
				}
			}
		}

		if (player.getHealth() <= 0) {
			toRemove.add(player);
			gameOver = true;
			pause();
		}
		for (Attack e : enemyAttacks) {
			if (e.collides(player)) {
				player.hit(e.getDamage(), e.getgameID());
			}
		}
		for (GameObject g : toRemove) {
			g.uponRemoval();
		}
		objects.removeAll(toRemove);
		enemies.removeAll(toRemove);
		primary.removeAll(toRemove);
		special.removeAll(toRemove);

	}

	private boolean wallCollision(GameObject object) {
		for (GameObject obs : objects) {
			if (!obs.throughable && object.collides(obs) && !(obs.equals(object)))
				return true;
		}
		return false;

	}

	// this allows the player to be controlled by W A S D
	private void controls() {
		int down = 0, right = 0;
		if (player.canMove()) {
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
		}
		// this allows the j key to control attacking (main commands)
		if (keys.contains("j")) {
			if (player.canAttack()) {
				primary.add(player.getAttack());
				if (player instanceof Knight)
					player.addCooldown(60);
				if (player instanceof Archer)
					player.addCooldown(40);
			}
		}
		if (keys.contains("k")) {
			if (player.canSpecial()) {
				special.add(player.getSpecial());
				if (player instanceof Knight)
					player.addSpecialCooldown(400);
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
			if (helpShown == true) {
				helpShown = false;
			}
		}

		// help button
		if (keys.contains("?")) {
			helpShown = !helpShown;
			mainPanel.repaint();
			/*
			 * if (helpShown == true) { timer.stop(); }
			 */
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
			enemyAttacks.clear();
			new RPGGame().beginGame();
			mainFrame.dispose();
		}


		// trading post
		if (keys.contains("k") && player.collides(trader)) {
			tradeOpen = !tradeOpen;
			mainPanel.repaint();
			pause();
		}

		// trading post - buy option 1
		if (keys.contains("1") && tradeOpen == true) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to purchase [1] ?") == JOptionPane.YES_OPTION) {
				if (Inventory.getGold() < tP.getSlot1().getGoldCost()) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold to cover the purchase.");
				} else if (Inventory.getItems().indexOf(tP.getSlot1()) > -1) {
					JOptionPane.showMessageDialog(null, "You already have this item in your Inventory.");
				} else {
					Inventory.getItems().add(tP.getSlot1());
					JOptionPane.showMessageDialog(null, "[1] has been added to your Inventory.");
					i.subtractGold(tP.getSlot1().getGoldCost());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Purchase of [1] has been aborted.");
			}
			keys.remove(keys.indexOf("1"));
		}
		// trading post - buy option 2
		else if (keys.contains("2") && tradeOpen == true) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to purchase [2] ?") == JOptionPane.YES_OPTION) {
				if (Inventory.getGold() < tP.getSlot2().getGoldCost()) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold to cover the purchase.");
				} else if (Inventory.getItems().indexOf(tP.getSlot2()) > -1) {
					JOptionPane.showMessageDialog(null, "You already have this item in your Inventory.");
				} else {
					Inventory.getItems().add(tP.getSlot2());
					JOptionPane.showMessageDialog(null, "[2] has been added to your Inventory.");
					i.subtractGold(tP.getSlot2().getGoldCost());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Purchase of [2] has been aborted.");
			}
			keys.remove(keys.indexOf("2"));
		}
		// trading post - buy option 3
		else if (keys.contains("3") && tradeOpen == true) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to purchase [3] ?") == JOptionPane.YES_OPTION) {
				if (Inventory.getGold() < tP.getSlot3().getGoldCost()) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold to cover the purchase.");
				} else if (Inventory.getItems().indexOf(tP.getSlot3()) > -1) {
					JOptionPane.showMessageDialog(null, "You already have this item in your Inventory.");
				} else {
					Inventory.getItems().add(tP.getSlot3());
					JOptionPane.showMessageDialog(null, "[3] has been added to your Inventory.");
					i.subtractGold(tP.getSlot3().getGoldCost());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Purchase of [3] has been aborted.");
			}
			keys.remove(keys.indexOf("3"));
		}
		// trading post - buy option 4
		else if (keys.contains("4") && tradeOpen == true) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to purchase [4] ?") == JOptionPane.YES_OPTION) {
				if (Inventory.getGold() < tP.getSlot4().getGoldCost()) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold to cover the purchase.");
				} else if (Inventory.getItems().indexOf(tP.getSlot4()) > -1) {
					JOptionPane.showMessageDialog(null, "You already have this item in your Inventory.");
				} else {
					Inventory.getItems().add(tP.getSlot4());
					JOptionPane.showMessageDialog(null, "[4] has been added to your Inventory.");
					i.subtractGold(tP.getSlot4().getGoldCost());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Purchase of [4] has been aborted.");
			}
			keys.remove(keys.indexOf("4"));
		}
		// trading post - buy option 5
		else if (keys.contains("5") && tradeOpen == true) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to purchase [5] ?") == JOptionPane.YES_OPTION) {
				if (Inventory.getGold() < tP.getSlot5().getGoldCost()) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold to cover the purchase.");
				} else if (Inventory.getItems().indexOf(tP.getSlot5()) > -1) {
					JOptionPane.showMessageDialog(null, "You already have this item in your Inventory.");
				} else {
					Inventory.getItems().add(tP.getSlot5());
					JOptionPane.showMessageDialog(null, "[5] has been added to your Inventory.");
					i.subtractGold(tP.getSlot5().getGoldCost());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Purchase of [5] has been aborted.");
			}
			keys.remove(keys.indexOf("5"));
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

	public void findEmptyPlace(String s) {
		boolean portal = false;
		for (GameObject g : objects) {
			if (g instanceof Portal) {
				portal = true;
			}
		}

		if (!portal) {
			while (true) {
				int r = (int) (Math.random() * StartGame.SCREEN_WIDTH);
				int c = (int) (Math.random() * StartGame.SCREEN_HEIGHT);
				while(!(r %50 == 0 && c %50 ==0)) {
					r = (int) (Math.random() * StartGame.SCREEN_WIDTH);
					c = (int) (Math.random() * StartGame.SCREEN_HEIGHT);
				}
				GameObject g = new Wall(r, c, 50, 50);
				boolean here = true;
				for (GameObject g1 : RPGGame.getObjects()) {
					if (g.collides(g1))
						here = false;
				}
				if (here) {
					if(s.equals("portal"))
						p = new Portal(r,c);
					if(s.equals("player"))
						player.setPlayerLoc(r, c);
					objects.add(p);
					return;

				}
			}

		}

	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

}
