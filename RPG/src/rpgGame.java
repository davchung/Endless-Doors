import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class rpgGame implements KeyListener {

	// these are all variables that allow the game to run
	private JPanel mainPanel;
	private Timer timer;
	private static final int REFRESH_RATE = 10;
	public static int ticks = 0;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// these are all variables that are involved with playing the game
	private Player player;
	private double pSpeed = 3.0; // player speed
	private int lastR, lastD; // last direction the player was facing
	private int facing = 1;
	// kindly refrain from changing this enemy's name
	private Enemy eNWIMN; // this stands for enemyNavigatingWallsIsMyNightmare
	private Map m = new Map(10, 5);
	private Attack pAttack; // player attack
	private Attack eAttack; // enemy attack
	private Wall builtWall;
	private int numWalls = 0;

	// these variables are all ArrayLists of other variables
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Wall> damagedWalls = new ArrayList<Wall>();

	// these variables are all "switches" (imagine an on/off switch for a light bulb)
	private boolean wallDamaged = false;
	private boolean enemyHit = false;
	private boolean playerHit = false;
	private boolean helpPage = false;
	private boolean gameOver = false;

	// these are getters for variables
	public Player getPlayer() {
		return this.player;
	}
	public Enemy getEnemy() {
		return this.eNWIMN;
	}

	public void beginGame() {

		player = new Player(50, 50, 50, 50);
		eNWIMN = new Enemy(300, 300, 50, 50);
		objects.addAll(m.getWalls());
		objects.add(player);
		objects.add(eNWIMN);
		enemies.add(eNWIMN);

		JFrame mainFrame = new JFrame("Role-Playing Game");
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
				mainPanel.setBackground(new Color(150, 250, 150));

				for (GameObject go : objects) {
					go.draw(g);
				}
				for (GameObject go : objects) {
					if (go instanceof Enemy) {
						go.draw(g);
					}
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
					for (Wall dw: damagedWalls) {
						if (dw.getHealth() < 100 && dw.getHealth() > 0) {
							g.drawString(""+dw.getHealth(), (int)dw.getCX()-8, (int)dw.getCY());
						}
					}
				}
				if (enemyHit == true) {
					if (eNWIMN.getHealth() > 0) {
						g.drawString("-"+player.getDamage(), (int)eNWIMN.getCX()-5, (int)eNWIMN.getCY());
					}
					enemyHit = false;
				}
				if (playerHit == true) {
					if (eNWIMN.getHealth() > 0) {
						g.drawString("-"+eNWIMN.getDamage(), (int)player.getCX()-5, (int)player.getCY());
					}
					playerHit = false;
				}

				if (helpPage == true) {
					g.setColor(new Color(0, 130, 255));
					g.fillRect(100, 200, 600, 325);

					g.setColor(new Color(255, 255, 255));
					g.setFont(new Font("Times New Roman", 0, 40));
					g.drawString("Help Page", 150, 250);

					g.setFont(new Font("Arial", 0, 20));
					g.drawString("W A S D to move the character.", 150, 300);
					g.drawString("J to attack.", 150, 325);
					g.drawString("K to build.", 150, 350);
					g.drawString("I to check inventory. (will be implemented later on)", 150, 375);
					g.drawString("? to enter/exit the Help Page.", 150, 425);
					g.drawString("P to pause/play the game.", 150, 450);
					
					g.setFont(new Font("Comic Sans MS", 0, 12));
					g.drawString("Thanks for playing our game!", 150, 500);

					pause();
				}
				if (helpPage == false) {
					g.setColor(new Color(255, 255, 255));
					g.drawString("Press ? for help.", 20, 25);
				}
				if (gameOver) {
					g.setColor(new Color(255,0,0,255));
					g.fillRect(0, 0, 1000, 925);

					g.setColor(new Color(255, 255, 255));
					g.setFont(new Font("Times New Roman", 0, 100));
					g.drawString("Game Over!", 350, 350);
					g.setFont(new Font("Times New Roman", 0, 40));
					g.drawString("Press Space to return to start", 270, 600);
				}
			}
		};

		// frame doesn't get minimized
		mainPanel.setPreferredSize(new Dimension(StartGame.SCREEN_WIDTH, StartGame.SCREEN_HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		mainFrame.setLocation(StartGame.SCREEN_WIDTH / 10, StartGame.SCREEN_HEIGHT / 10);
		mainFrame.pack();
		mainFrame.addKeyListener(this);
		// this timer controls the actions in the game and then repaints after each update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				enemyMovement();
				collision();
				ticks++;
				if (ticks%100==0) {
					System.out.println(ticks/100 + " second");
				}
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
			if (player.equals(e) || pAttack != null && pAttack.equals(e))
				continue;
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
					((Wall)e).hit();
					damagedWalls.add((Wall)e);
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
		for (GameObject go: toRemove) {
			if (go instanceof Wall) {
				numWalls++;
			}
		}
		walls.removeAll(toRemove);

	}

	private void enemyMovement() {
		// makes the enemy follow the player
		for(Enemy e: enemies) {
			objects.remove(e);
			double x = 0, y = 0;
			x = (player.getCX() - e.getCX());
			y = (player.getCY() - e.getCY());
			double mag = Math.sqrt(x * x + y * y);
			x = (e).getSpeed() * x / mag;
			y = (e).getSpeed() * y / mag;
			if ((e).collides(player)) {
				if (e.attack(ticks)) {
					eAttack = new Attack((int) e.getLocX() + 25, (int) e.getLocY() + 25, (int)x, (int)y, ticks, "flame.png");
				}
			}
			e.moveX(x);
			e.moveY(y);
			e.setRight(x);
			e.setDown(y);

			for (GameObject i : objects) {
				if (e.collides(i) && (i instanceof Wall)) {
					double dx = e.getCX() - i.getCX();
					double dy = e.getCY() - i.getCY();
					double m = Math.sqrt(dx * dx + dy * dy);
					dx = ((Enemy)e).getSpeed() * dx / m;
					dy = ((Enemy)e).getSpeed() * dy / m;
					e.moveX(dx);
					e.moveY(dy);
				}

			}
			objects.add(e);

		}
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
					pAttack = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, lastR, lastD, ticks, "sprites/weapon_golden_sword.png");
				}
			}

			// this allows the K key to control building
			if (keys.contains("k") || keys.contains("K")) {
				if (player.build(ticks) && numWalls > 0) {
					builtWall = new Wall(player.getLocX()-(50*lastR), player.getLocY()-(50*lastD), 50, 50, 100);
					numWalls--;
					objects.add(builtWall);
				}
			}
		}
	}

	private void pause() {
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
		if (gameOver && keys.contains(" "))
			new StartGame().init();
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
