import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private Player player;
	private JPanel mainPanel;
	private Timer timer;
	private static final int REFRESH_RATE = 10;
	public static int ticks = 0;
	private double speed = 3.5;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<String> keys = new ArrayList<String>();
	private Map m = new Map(10, 5);
	private Attack playerAttack;
	private Attack enemyAttack;
	// what direction the player was last facing
	private int lastR, lastD;
	private int facing=1;
	private boolean wallDamaged = false;
	private ArrayList<Wall> damagedWalls = new ArrayList<Wall>();
	private boolean enemyHit = false;
	private boolean playerHit = false;
	private boolean helpPage = false;

	public Player getPlayer() {
		return player;
	}

	private Enemy test;

	public Enemy getEnemy() {
		return test;
	}

	public void beginGame() {
		JFrame mainFrame = new JFrame("Role-Playing Game");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50, 50, 50, 50);
		test = new Enemy(500, 500, 50, 50);
		objects.addAll(m.getWalls());
		objects.add(test);
		enemies.add(test);
		objects.add(player);
		mainPanel = new JPanel() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

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

				if (playerAttack != null && !playerAttack.expire()) {
					playerAttack.draw(g);
				}
				if (enemyAttack != null && !enemyAttack.expire()) {
					enemyAttack.draw(g);
				}

				g.drawString("Player health: " + player.getHealth(), 675, 65);
				g.drawString("Enemy health: " + test.getHealth(), 675, 85);

				g.setColor(new Color(255, 0, 0));
				if (wallDamaged == true) {
					for (Wall dw: damagedWalls) {
						if (dw.getHealth() < 100 && dw.getHealth() > 0) {
							g.drawString(""+dw.getHealth(), (int)dw.getCX()-8, (int)dw.getCY());
						}
					}
				}
				if (enemyHit == true) {
					if (test.getHealth() > 0) {
						g.drawString("-"+player.getDamage(), (int)test.getCX()-5, (int)test.getCY());
					}
					enemyHit = false;
				}
				if (playerHit == true) {
					if (test.getHealth() > 0) {
						g.drawString("-"+test.getDamage(), (int)player.getCX()-5, (int)player.getCY());
					}
					playerHit = false;
				}

				if (helpPage == true) {
					g.setColor(new Color(0, 0, 0));
					g.setFont(new Font("Times New Roman", 0, 20));
					g.drawString("Help Page", 100, 100);
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
		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.repaint();
				controls();
				enemyMovement();
				collision();
				ticks++;
			}

		});
		timer.start();
	}

	protected void collision() {
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		if (playerAttack != null && playerAttack.expire()) {
			playerAttack = null;
		}
		if (enemyAttack != null && enemyAttack.expire()) {
			enemyAttack = null;
		}

		for (GameObject e : objects) {
			if (player.equals(e) || playerAttack != null && playerAttack.equals(e))
				continue;
			if (player.collides(e) && !e.throughable) {
				double dx = player.getCX() - e.getCX();
				double dy = player.getCY() - e.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = speed * dx / m;
				dy = speed * dy / m;
				player.moveX(dx);
				player.moveY(dy);
			}

			// tests if any enemy collides with the playerAttack
			if (e instanceof Enemy) {
				if (((Enemy) e).getHealth() <= 0)
					toRemove.add(e);
				if (playerAttack != null && playerAttack.collides(e)) {
					((Enemy) e).hit(player.getDamage());
					enemyHit = true;
				}
			}
			if (e instanceof Wall) {
				if (((Wall) e).getHealth() <= 0)
					toRemove.add(e);
				// both playerAttack and enemyAttack can damage walls
				if ((playerAttack != null && playerAttack.collides(e)) || (enemyAttack != null && enemyAttack.collides(e))) {
					((Wall)e).hit();
					damagedWalls.add((Wall)e);
					wallDamaged = true;
				}
			}

			// tests if the player collides with the enemyAttack
			if (e instanceof Player) {
				if (((Player) e).getHealth() <= 0)
					toRemove.add(e);
				if (enemyAttack != null && enemyAttack.collides(e)) {
					((Player) e).hit(test.getDamage());
					playerHit = true;
				}
			}

		}
		objects.removeAll(toRemove);
		enemies.removeAll(toRemove);
		walls.removeAll(toRemove);

	}

	private void enemyMovement() {

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
					enemyAttack = new Attack((int) e.getLocX() + 25, (int) e.getLocY() + 25, (int)x, (int)y, ticks, "ax.png", 50, 50);
				}
			}
			e.moveX(x);
			e.moveY(y);
			e.setR(x);
			e.setD(y);

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

	private void controls() {
		int down = 0, right = 0;
		if (playerAttack == null) {
			if (keys.contains("w") || keys.contains("W")) {
				player.moveY(-speed);
				down -= 1;
			}
			if (keys.contains("a") || keys.contains("A")) {
				player.moveX(-speed);
				right -= 1;
			}
			if (keys.contains("s") || keys.contains("S")) {
				player.moveY(speed);
				down += 1;
			}
			if (keys.contains("d") || keys.contains("D")) {
				player.moveX(speed);
				right += 1;
			}
			if (down != 0 || right != 0) {
				lastR = right;
				lastD = down;
				
			}
			if (right != 0) {
				facing = right;
			}
			if (keys.contains("j") || keys.contains("J")) {
				if (player.attack(ticks)) {
					playerAttack = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, lastR, lastD, ticks, "sprites/weapon_golden_sword.png", 50, 50);
				}
			}
			player.setR(right);
			player.setD(down);
		}
		//player.setBufferedImage(a.update(Math.abs(down) + Math.abs(right)));

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
		}

		// help button
		if (keys.contains("?")) {
			helpPage ^= true;
			pause();
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
