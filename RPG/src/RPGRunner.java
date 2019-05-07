import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
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
	private ArrayList <Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<String> keys = new ArrayList<String>();
	private Map m = new Map(10, 5);
	private Animation a = new Animation();
	private Attack attack;
	// what direction the player was last facing
	private int lastR, lastD;
	private int facing;

	public Player getPlayer() {
		return player;
	}

	private Enemy e;
	public Enemy getEnemy() {
		return e;
	}

	public void beginGame() {
		JFrame mainFrame = new JFrame("Role-Playing Game");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50, 50, 50, 50);
		e = new Enemy(500, 500, 75, 75);
		objects.addAll(m.getObjs());
		objects.add(e);
		objects.add(player);
		mainPanel = new JPanel() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				mainPanel.setBackground(new Color(152, 251, 152));
				for (GameObject go : objects) {
					go.draw(g);
				}
				for (GameObject go : objects) {
					if (go instanceof Enemy) {
						go.draw(g);
					}
				}

				if (attack != null && !attack.expire()) {
					attack.draw(g);
				}
				//player.draw(g);
				player.draw(g,facing);
				g.drawString("Enemy health: " + e.getHealth(), 500, 25);
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
		if (attack!=null&&attack.expire()){
			attack = null;
		}
		for (GameObject e : objects) {
			if (player.equals(e)||attack!=null&&attack.equals(e))
				continue;
			if (player.collides(e) && !e.throughable) {
				double dx = player.getCX()-e.getCX();
				double dy = player.getCY()-e.getCY();
				double m = Math.sqrt(dx*dx+dy*dy);
				dx = speed*dx/m;
				dy = speed*dy/m;
				player.moveX(dx);
				player.moveY(dy);
			}
			//tests if any enemy collides with the attack
			if(e instanceof Enemy) {
				if (((Enemy) e).getHealth()<=0)
					toRemove.add(e);
					//objects.remove(e);
				if(attack!=null&&attack.collides(e)) {
					((Enemy)e).hit();
				}
			}
			if(e instanceof Wall) {
				if (((Wall) e).getHealth()<=0)
					toRemove.add(e);
				if(attack!=null&&attack.collides(e)) {
					((Wall)e).hit();
				}
			}

		}
		objects.removeAll(toRemove);

	}

	private void enemyMovement() {
		double x = 0, y = 0;
		if (e.getLocX() - player.getLocX() > 0) {
			x = -e.getSpeed();
		} else {
			x = e.getSpeed();
		}
		if (e.getLocY() - player.getLocY() > 0) {
			y = -e.getSpeed();
		} else {
			y = e.getSpeed();
		}
		if (e.getLocX() - player.getLocX() == 0 && e.getLocY() - player.getLocY() == 0) {
			System.out.println("Enemy collided with Player.");
		}
		e.moveTowardPlayer(x, y);
		for (GameObject i: objects) {
			if (e.collides(i) && (i instanceof Wall)) {
				e.moveTowardPlayer(-x, -y);
			}
		}
	}

private void controls() {
	int down = 0, right = 0;
	if (attack==null||attack.expire()) {
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
		if (right!=0) {
			facing = right;
		}
		if (keys.contains("j")) {
			if (player.attack(ticks)) {
				attack = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, lastR, lastD, ticks);
			}
		}
	}
	player.setBufferedImage(a.update(Math.abs(down) + Math.abs(right)));

}

@Override
public void keyPressed(KeyEvent e) {
	if (!keys.contains("" + e.getKeyChar())) {
		keys.add("" + e.getKeyChar());
	}

	if(keys.contains("p")) {
		if(timer.isRunning()) {
			timer.stop();
		} else {
			timer.start();
		}
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
