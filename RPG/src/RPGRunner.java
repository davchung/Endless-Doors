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
	private JPanel startPanel;
	private BufferedImage startImg;
	private JPanel mainPanel;
	private Timer timer;
	private static final int REFRESH_RATE = 5;
	private int ticks = 0;
	private double speed = 1.7;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int) (screenSize.getWidth() * 3 / 4),
			SCREEN_HEIGHT = (int) (screenSize.getHeight() * 3 / 4);

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<String> keys = new ArrayList<String>();
	private Map m = new Map(10, 5);
	private Animation a = new Animation();
	private Attack attack;
	// what direction the player was last facing
	private int lastR, lastD;

	public Player getPlayer() {
		return player;
	}
	private Enemy e;

	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame startFrame = new JFrame("Welcome to RPG!");
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			startImg = ImageIO.read(this.getClass().getResource("img/startImg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		startPanel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(startImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
				g.setColor(new Color(250, 250, 250));
				g.fillRect(55, (SCREEN_HEIGHT/2)-75, 955, 100);
				g.setColor(new Color(0, 0, 0));
				g.setFont(new Font("Times New Roman", 0, 75));
				g.drawString("Click anywhere to begin game.", 65, SCREEN_HEIGHT/2);
			}
		};

		// frame doesn't get minimized
		startPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		startFrame.add(startPanel);
		// frame gets placed a little way from top and left side
		startFrame.setLocation(SCREEN_WIDTH / 10, SCREEN_HEIGHT / 10);
		startFrame.pack();
		startFrame.setVisible(true);

		startPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				startFrame.setVisible(false);
				beginGame();

			}
		});
	}

	private void beginGame() {
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
				if (attack != null && !attack.expire(ticks)) {
					attack.draw(g);
				}
				player.draw(g);
			}
		};

		// frame doesn't get minimized
		mainPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		mainFrame.setLocation(SCREEN_WIDTH / 10, SCREEN_HEIGHT / 10);
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
				ticks++;
			}

		});
		timer.start();
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
	}

	private void controls() {
		int down = 0, right = 0;
		if (attack==null||attack.expire(ticks+40)) {
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
			if (!(down == 0 && right == 0)) {
				lastR = right;
				lastD = down;
			}
			if (keys.contains("j")) {
				if (player.attack(ticks)) {
					attack = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, lastR, lastD, ticks);
				}
			}
		}
		player.setBufferedImage(a.update(Math.abs(down) + Math.abs(right), ticks));
		for (GameObject e : objects) {
			if (player.equals(e)||attack!=null&&attack.equals(e))
				continue;
			if (player.collides(e)) {
				System.out.println("Collided with " + e);
			}
			if(e instanceof Enemy) {
				if (((Enemy) e).getHealth()<=0)
					objects.remove(e);
			}
			if(attack!=null&&attack.collides(e)) {
				e.hit();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains("" + e.getKeyChar())) {
			keys.add("" + e.getKeyChar());
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
