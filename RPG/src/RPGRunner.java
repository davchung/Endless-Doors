import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private Player player;
	private JPanel startPanel;
	private Image startImg;
	private JPanel mainPanel;
	private Timer timer;
	// currently 200 times per second, i think? 1000 ticks/5
	private static final int REFRESH_RATE = 5;
	private int ticks = 0;
	private double speed = 1.7;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) (screenSize.getWidth() * 3 / 4),
			HEIGHT = (int) (screenSize.getHeight() * 3 / 4);

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<String> keys = new ArrayList<String>();
	private Map m = new Map(10, 5);
	private Animation a = new Animation();
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

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//startPanel.setBackground(new Color(250, 250, 250));
				g.drawImage(startImg, 0, 0, 100, 100, null);
				g.drawString("Click anywhere to begin game.", 100, 50);
				System.out.println(WIDTH+" "+HEIGHT);
			}
		};

		// frame doesn't get minimized
		startPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		startFrame.add(startPanel);
		// frame gets placed a little way from top and left side
		startFrame.setLocation(WIDTH / 10, HEIGHT / 10);
		startFrame.pack();
		startFrame.setVisible(true);
		startFrame.addKeyListener(this);


		startPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				startFrame.setEnabled(false);
				beginGame();

			}
		});
	}

	private void beginGame() {
		JFrame mainFrame = new JFrame("Role-Playing Game");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50, 50, 50, 50);
		e = new Enemy(500, 500, 75, 75);
		objects.addAll(m.getObjs());
		objects.add(e);
		objects.add(player);
		mainPanel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				mainPanel.setBackground(new Color(152, 251, 152));
				for (GameObject go : objects) {
					go.draw(g);
					// draw player last so that player is on top of everything else
					player.draw(g);
				}
			}
		};

		// frame doesn't get minimized
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.add(mainPanel);
		// frame gets placed a little way from top and left side
		mainFrame.setLocation(WIDTH / 10, HEIGHT / 10);
		mainFrame.pack();
		mainFrame.setVisible(true);
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

		if (keys.contains("j")) {
			if (player.attack(ticks)) {
				Attack a = new Attack((int) player.getLocX() + 25, (int) player.getLocY() + 25, right, down, ticks);
			}
		}
		//a.update(Math.abs(down) + Math.abs(right), ticks); // IF YOU WANT TO RUN SOMETHING COMMENT THIS LINE OUT
		player.setBufferedImage(a.update(Math.abs(down) + Math.abs(right), ticks));
		for (GameObject e : objects) {
			if (player.equals(e))
				continue;
			if (player.collides(e)) {
				System.out.println("Collided with " + e);
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
