import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;

public class EndlessDoorsGame implements KeyListener {

	// these are all variables that allow the game to run
	private JFrame mainFrame = new JFrame("Endless Doors");
	private JPanel mainPanel;
	private static Timer timer;
	private static final int REFRESH_RATE = 10;
	public static int ticks = 0;
	private static int levelTicks;

	// these are all variables that are involved with playing the game
	private static Player player;
	public static int lastR, lastD=-1; // last direction the player was facing
	private int facing = 1;
	private Map map;
	private int lev;
	private int lastLev;
	private Portal portal;
	public static Trader trader = new Trader();
	public static Floor floor = new Floor();
	private static AudioClip test = new AudioClip("boss");
	private FileWriter fW;

	// these are all variables related to GUIs
	private static Inventory i;
	private HelpPage hP = new HelpPage();
	private GameOver gO = new GameOver();
	private TradingPost tP = new TradingPost();

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<StringGraphic> displayStrings = new ArrayList<StringGraphic>();  //list of strings to draw
	private Font tutFont = new Font("Chelsea",0,25);
	private Color tutColor = new Color (255,255,255);
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private static ArrayList<GameObject> damagedObjects = new ArrayList<GameObject>();
	private static ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
	private static ArrayList<Attack> special = new ArrayList<Attack>();
	private static ArrayList<Attack> envirAttacks = new ArrayList<Attack>();
	private static ArrayList<Attack> primary = new ArrayList<Attack>();

	// these variables are all "switches" (imagine an on/off switch for a light
	// bulb)
	private boolean helpShown = false;
	private boolean gameOver = false;
	private boolean invenShown = false; // inventory shown
	private boolean levelDone = false;
	private boolean tradeOpen = false;

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// these are getters for variables
	public static Player getPlayer() {
		return EndlessDoorsGame.player;
	}

	public static ArrayList<Attack> getPrimary() {
		return primary;
	}

	public static Floor getFloor() {
		return floor;
	}

	public static ArrayList<Attack> getEnemyAttacks() {
		return enemyAttacks;
	}

	public static ArrayList<Attack> envirAttacks() {
		return enemyAttacks;
	}

	public static void setEnemyAttack(Attack atk) {
		enemyAttacks.add(atk);
	}

	public static ArrayList<GameObject> getObjects() {
		return EndlessDoorsGame.objects;
	}

	public static ArrayList<GameObject> getDamagedObjects() {
		return EndlessDoorsGame.damagedObjects;
	}

	public static ArrayList<Enemy> getEnemies() {
		return EndlessDoorsGame.enemies;
	}

	public static ArrayList<Attack> getSpecial() {
		return EndlessDoorsGame.special;
	}

	public static Inventory getInventory() {
		return i;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	public void beginGame() {
		try {
			fW = new FileWriter("record.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		i = new Inventory();
		map = new Map();
		objects.addAll(map.getEObjs());
		objects.add(player);
		test.loop();
		setEnemies(Map.getLevel());
		EndlessDoorsRunner.startFrame.dispose();
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
				for (Attack a : special) {
					if (!a.expire()) {
						a.draw(g);
					}
				}
				for (Attack e : enemyAttacks) {
					if (!e.expire()) {
						e.draw(g);// draws all enemy attacks
					}
				}
				player.draw(g, facing); // draws the player
				for (Attack p : primary) {
					p.draw(g);
				}
				for (Attack e : envirAttacks) {
					e.draw(g);
				}
				g.setColor(new Color(0, 0, 0));
				//drawHitboxes(g); // draws all hitboxes. Dev-only.

				for (GameObject go : objects) {// draws the health bars
					if (go instanceof MoveableObject && go.health < go.maxHealth) {
						g.setColor(new Color(0, 255, 0));
						g.fillRect((int) go.getLocX(), go.hPBarYLoc(), (int) (go.WIDTH * go.getHealthPercent()), 5);
						g.setColor(new Color(255, 0, 0));
						g.fillRect((int) (go.getLocX() + (go.WIDTH * go.getHealthPercent())), go.hPBarYLoc(),
								(int) (go.WIDTH * (1 - go.getHealthPercent())), 5);
						g.setColor(new Color(0,0,0));
						g.drawRect((int) go.getLocX(), go.hPBarYLoc(), (int) (go.WIDTH ), 5);
					}
				}

				g.setColor(new Color(255, 0, 0));
				for (GameObject go : enemies) {
					go.draw(g);//TESTING
					if (go instanceof MoveableObject && ((MoveableObject) go).getLoss() != 0) {
						g.drawString("" + -(int) ((MoveableObject) go).getLoss(), (int) go.getCX() - 10,
								(int) go.getCY());
					}
				}

				for (GameObject go : damagedObjects) {
					if (!(go instanceof Enemy) && !go.getInvincibility() && go.getHealth() > 0) {
						g.drawString("" + (int) go.getHealth(), (int) go.getCX() - 8, (int) go.getCY());
					}
				}

				// this is where level number is drawn
				g.setColor(Color.WHITE);
				g.setFont(new Font("Chelsea", 0, 15));
				lev = Map.getLevel() % 7;
				if (lev > 5)
					lev = 5;
				lev = (Map.getLevel() / 7) * 5 + lev;
				if (lev > 0)
					lastLev = lev;
				if (lev == 0) {
					g.drawString("Level: tutorial", EndlessDoorsRunner.SCREEN_WIDTH - 110, 18);
				}
				else {
					g.drawString("Level: " + lastLev, EndlessDoorsRunner.SCREEN_WIDTH - 75, 18);
				}

				// this is where the player's health bar is drawn
				g.setColor(new Color(255, 0, 0)); // color: red
				g.fillRect(20, 25, EndlessDoorsRunner.SCREEN_WIDTH / 4, 16);
				g.setColor(new Color(0, 255, 0)); // color: green
				g.fillRect(20, 25,
						(int) (((EndlessDoorsRunner.SCREEN_WIDTH / 4) * (int) player.getHealth()) / player.getMaxHealth()), 16);
				g.setColor(new Color(255, 255, 255)); // color: white
				g.drawString("Player health: " + (int)player.getHealth(), 22, 38);
				/*if(pHit) {
					g.setColor(new Color(255,0,0));
					g.fillRect(0,0,1050,100);
					g.fillRect(0,0,100,750);
					g.fillRect(950,0,100,750);
					g.fillRect(0,650,1050,100);
					timer.setDelay(5);
					pHit=false;
				}
				 */

				// this is where the special power cooldown status is drawn
				g.setColor(new Color(0, 0, 255));
				g.fillRect(EndlessDoorsRunner.SCREEN_WIDTH * 4 / 5 + 24, 25, 181, 16);
				g.setColor(new Color(255, 255, 255)); // color: white
				if (player.canSpecial()) {
					g.drawString("Special power: available", EndlessDoorsRunner.SCREEN_WIDTH * 4 / 5 + 33, 38);
				}
				else {
					g.drawString("Special power: unavailable", EndlessDoorsRunner.SCREEN_WIDTH * 4 / 5 + 25, 38);
				}

				if (helpShown == true) {
					hP.draw(g);
				}
				if (helpShown == false) {
					g.setColor(new Color(255, 255, 255));
					g.drawString("Type ? for help.", 22, 18);
				}
				if (levelDone == true) {
					findEmptyPlace("portal");
					if (player.collides(portal) && keys.contains("j")) {
						// don't touchy touchy
						enemies.clear();
						objects.clear();
						primary.clear();
						special.clear();
						enemyAttacks.clear();
						damagedObjects.clear();
						levelTicks = ticks;
						floor.reset();
						map.addObjs();
						objects.addAll(map.getEObjs());
						findEmptyPlace("player");
						setEnemies(Map.getLevel());
						levelDone = false;
						objects.remove(portal);
						tP.refreshItems();
					}
				}
				if (gameOver == true) {
					gO.draw(g, lastLev);
					enemies.clear();
					pause();
					EndlessDoorsRunner.timer.stop();
					map.setRoomCount(0);


					/*if (JOptionPane.showConfirmDialog(null, "Would you like to be entered into the record?", "Record Entry", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						try {
							String entry = JOptionPane.showInputDialog("Enter your name.") + "   " + lev + System.getProperty("line.separator");
							fW.append(entry);
							fW.close();
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}*/
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
		mainPanel.setPreferredSize(new Dimension(EndlessDoorsRunner.SCREEN_WIDTH, EndlessDoorsRunner.SCREEN_HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		EndlessDoorsRunner.center(mainFrame);
		mainFrame.pack();
		mainFrame.addKeyListener(this);
		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				if (ticks - levelTicks > 50 || ticks - levelTicks == 0)
					movement();
				collision();
				update(); // updates movement
				attractCoins();
				/**
				 * int <= System.getCurrentTimeMillis(); 3000
				 */
				ticks++;
			}

		});
		timer.start();
		player.addCooldown(10);
		player.addSpecialCooldown(10);
	}

	public void setEnemies(int level) {
		if (level == 0) {
			player.setInvincibility(true);
			makeTutorial();
			levelDone = true;
			return;
		}
		player.setInvincibility(false);
		ArrayList<Enemy> list = new ArrayList<Enemy>();
		int amountE = (int) (Math.random() * 2) + 1 + level / 7;// amount of enemies in the floor
		int difficulty = Map.getLevel() / 7 + 1;
		if (!((level % 7 == 0) || (level % 7 == 6))) {
			for (int c = 0; c < amountE; c++) {
				int theme = (int) (Math.random() * 5); // gets a random type of enemy
				switch (theme) {
				case 0:
					list.add(new Skeleton(0, 0, difficulty));
					break;
				case 1:
					list.add(new Goblin(0, 0, difficulty));
					break;
				case 2:
					list.add(new Wogol(0, 0, difficulty));
					break;
				case 3:
					list.add(new Zombie(0, 0, difficulty));
					break;
				case 4:
					list.add(new Swampy(0, 0, difficulty));
				}
			}
		}
		if (level % 7 == 0) {
			list.add(new Demon(0, 0, difficulty));
		}
		for (Enemy e : list) {
			checkSpawns(e, difficulty);
		}
	}

	private void makeTutorial() {
		ArrayList<Enemy> list = new ArrayList<Enemy>();
		list.add(new Skeleton(75, 310, 1));
		list.add(new Goblin(75, 475, 1));
		list.add(new Wogol(75, 610, 1));
		list.add(new Zombie(925, 325, 1));
		list.add(new Swampy(925, 475, 1));
		list.add(new Demon(900, 600, 1));
		player.setLoc(500, 600);
		portal = new Portal(500, 50);
		objects.add(portal);
		for (Enemy e : list) {
			e.addCooldown(Integer.MAX_VALUE);
			objects.add(e);
			enemies.add(e);
		}
	}

	protected void attractCoins() {
		for (GameObject m : objects) {
			if (m instanceof Coin) {
				double x = player.getCX() - m.getCX();
				double y = player.getCY() - m.getCY();
				double mag = Math.sqrt(x * x + y * y);
				x /= mag;
				y /= mag;
				double speed = 100 / mag;
				if (mag < 300) {
					m.moveX(x * speed);
					m.moveY(y * speed);
				}
			}
		}
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
		for (Attack e : envirAttacks) {
			e.update();
		}
		if (enemies.isEmpty()) {
			if (levelDone == false) {
				for (GameObject obj : objects) {
					if (obj.health > 1) {
						if (obj instanceof Crate) {
							obj.health = 1;
						}
						if (obj instanceof Barrel) {
							obj.health = 1;
						}
					}
				}
			}
			levelDone = true;
		}
		objects.removeAll(enemies);
		objects.addAll(enemies);
		objects.removeAll(enemyAttacks);
		objects.addAll(enemyAttacks);
		objects.removeAll(special);
		objects.addAll(special);
		objects.removeAll(primary);
		objects.addAll(primary);
	}

	public static void selectClass(int which) {
		switch (which) {
		case 0:
			player = new Knight(EndlessDoorsRunner.SCREEN_HEIGHT / 4, EndlessDoorsRunner.SCREEN_WIDTH / 4);
			break;
		case 1:
			player = new Archer(EndlessDoorsRunner.SCREEN_HEIGHT / 4, EndlessDoorsRunner.SCREEN_WIDTH / 4);
			break;
		}
	}

	protected void drawHitboxes(Graphics g) {
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
		for (Attack p : envirAttacks) {
			g.drawRect((int) (p.getLocX() + p.WIDTH / 10), (int) (p.getLocY() + p.HEIGHT / 10),
					(int) (p.WIDTH * 8 / 10), (int) (p.HEIGHT * 8 / 10));
		}

	}

	private void checkSpawns(Enemy e2, int level) {
		int x = GameObject.randInt(300, EndlessDoorsRunner.SCREEN_WIDTH - 50 - e2.WIDTH) / 50;
		int y = GameObject.randInt(300, EndlessDoorsRunner.SCREEN_HEIGHT - 50 - e2.WIDTH) / 50;
		if (e2 instanceof Demon) {
			e2 = null;
			e2 = new Demon(x * 50, y * 50, level);
		}
		if (e2 instanceof Goblin) {
			e2 = null;
			e2 = new Goblin(x * 50, y * 50, level);
		}
		if (e2 instanceof Wogol) {
			e2 = null;
			e2 = new Wogol(x * 50, y * 50, level);
		}
		if (e2 instanceof Skeleton) {
			e2 = null;
			e2 = new Skeleton(x * 50, y * 50, level);
		}
		if (e2 instanceof Zombie) {
			e2 = null;
			e2 = new Zombie(x * 50, y * 50, level);
		}
		if (e2 instanceof Swampy) {
			e2 = null;
			e2 = new Swampy(x * 50, y * 50, level);
		}
		double dx= player.getCX()-e2.getCX();
		double dy = player.getCY()-e2.getCY();
		double mag = Math.sqrt(dx*dx+dy*dy);
		if (mag<150) {
			checkSpawns(e2, level);
			return;
		}
		for (GameObject w : objects) {
			if (!e2.equals(w) && !w.throughable && e2.collides(w)) {
				checkSpawns(e2, level);
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
		if (Map.getLevel()==0) {
			toRemove.addAll(enemyAttacks);
		}
		for (Attack p : primary) {
			if (p.expire())
				toRemove.add(p);
		}
		for (Attack a : envirAttacks) {
			if (a.expire()) {
				toRemove.add(a);
			}
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
			if (ticks-levelTicks<20)
				toRemove.add(e);
		}
		for (GameObject objs : objects) {// collision for gameobjects
			while (player.collides(objs) && !objs.throughable && !(player.equals(objs))) {
				player.moveAway(objs);
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
				if (objs.getHealth() <= 0) {
					toRemove.add(objs);
				}
				for (Attack p : primary) {
					if (p.collides(objs) && !(objs instanceof Player)) {
						if (player instanceof Archer) {
							objs.hit(p.getDamage() * 2, p.getgameID());
						}
						if (player instanceof Knight) {
							objs.hit(p.getDamage(), p.getgameID());
						}
						damagedObjects.add(objs);
					}
				}
				for (Attack a : enemyAttacks) {
					if (a.collides(objs)) {
						objs.hit(a.getDamage(), a.getgameID());
						damagedObjects.add(objs);
					}
				}
			}

			if (player.collides(objs) && objs instanceof Chest)
				toRemove.add(objs);
			if (player.collides(objs) && (objs instanceof Coin || objs instanceof Potion)) {
				toRemove.add(objs);
			}
			if (!objs.throughable) {
				for (Attack p : primary) {
					if (!(objs instanceof Player) && player instanceof Archer && p.collides(objs))
						toRemove.add(p);
				}
				for (Attack s : special) {
					if (!(objs instanceof Player) && player instanceof Archer && s.collides(objs)) {
						((Grapple) s).pull();
						((Grapple) s).retract(objs);
					}
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
				//pHit = true;
			}
		}
		for (GameObject g : toRemove) {
			g.uponRemoval();
			// if (g instanceof Barrel)
			// envirAttacks.add(((Barrel) g).explode());
		}
		player.checkBounds();
		objects.removeAll(toRemove);
		enemies.removeAll(toRemove);
		primary.removeAll(toRemove);
		enemyAttacks.removeAll(toRemove);
		envirAttacks.removeAll(toRemove);
		special.removeAll(toRemove);
	}

	public boolean wallCollision(GameObject object) {
		for (GameObject obs : objects) {
			if (!obs.throughable && object.collides(obs) && !(obs.equals(object)))
				return true;
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// this allows the player to be controlled by W A S D
	private void controls() {
		int down = 0, right = 0;
		if (player.canMove()) {
			if (keys.contains("w")) {
				player.moveY(-player.getSpeed());
				down -= 1;
				int checks = 0;
				while (wallCollision(player) && checks < 6) {
					player.moveY(player.getSpeed() / 5);
					checks++;
				}
			}
			if (keys.contains("a")) {
				player.moveX(-player.getSpeed());
				right -= 1;
				int checks = 0;
				while (wallCollision(player) && checks < 6) {
					player.moveX(player.getSpeed() / 5);
					checks++;
				}
			}
			if (keys.contains("s")) {
				player.moveY(player.getSpeed());
				down += 1;
				int checks = 0;
				while (wallCollision(player) && checks < 6) {
					player.moveY(-player.getSpeed() / 5);
					checks++;
				}
			}
			if (keys.contains("d")) {
				player.moveX(player.getSpeed());
				right += 1;
				int checks = 0;
				while (wallCollision(player) && checks < 6) {
					player.moveX(-player.getSpeed() / 5);
					checks++;
				}
			}
			if (keys.contains("o")) {
				for (Enemy e : enemies) {
					e.hit(Integer.MAX_VALUE, Integer.MAX_VALUE);
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
				if (player instanceof Archer)
					player.addSpecialCooldown(200);
			}
		}
	}

	public static void pause() {
		if (timer.isRunning()) {
			test.stop();
			timer.stop();
		} else {
			test.loop();
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
		if (keys.contains("?") || keys.contains("/")) {
			helpShown = !helpShown;
			mainPanel.repaint();
			/*
			 * if (helpShown == true) { timer.stop(); }
			 */
			pause();
		}

		// check inventory
		if (keys.contains("i") && !(player.collides(trader))) {
			invenShown = !invenShown;
			mainPanel.repaint();
			pause();
		}

		// game over
		if (gameOver == true && (keys.contains("n"))) {
			keys.clear();
			test.stop();
			objects.clear();
			enemies.clear();
			primary.clear();
			special.clear();
			walls.clear();
			enemyAttacks.clear();
			damagedObjects.clear();
			envirAttacks.clear();
			floor.reset();
			new EndlessDoorsRunner().init();
			mainFrame.dispose();
		}

		// trading post
		if (keys.contains("i") && player.collides(trader)) {
			tradeOpen = !tradeOpen;
			player.setDamage(5+ EndlessDoorsGame.getInventory().getTotalDmg());
			mainPanel.repaint();
			pause();
		}

		tradingPostBuying();
	}

	private void tradingPostBuying() {
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
		mainPanel.repaint();
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
		boolean portalCheck = false;
		for (GameObject g : objects) {
			if (g instanceof Portal) {
				portalCheck = true;
			}
		}

		if (!portalCheck && levelDone == true) {
			while (true) {
				int r = (int) (Math.random() * EndlessDoorsRunner.SCREEN_WIDTH) / 50;
				int c = (int) (Math.random() * EndlessDoorsRunner.SCREEN_HEIGHT) / 50;
				r *= 50;
				c *= 50;
				GameObject g = new Wall(r, c, 50, 50);
				boolean here = true;
				for (GameObject g1 : EndlessDoorsGame.getObjects()) {
					if (g.collides(g1)) {
						here = false;
					}
				}
				if (here) {
					if (s.equals("portal")) {
						portal = new Portal(r, c);
						objects.add(portal);
					}
					if (s.equals("player"))
						player.setPlayerLoc(r, c);
					return;
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }
}
