
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private JPanel panel;
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
	private Player player;
	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame frame = new JFrame("RPG");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50, 50, 50, 50);
		Enemy e = new Enemy(100, 100, 100, 100);
		objects.add(player);
		objects.add(e);
		panel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				for (GameObject go:objects) {
					go.draw(g);
				}
			}
		};

		// frame doesn't get minimized
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// frame gets placed a little way from top and left side
		frame.setLocation(WIDTH / 10, HEIGHT / 10);
		// background
		panel.setBackground(new Color(250, 250, 250));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				// clickedAt(me);
				// to-do

			}
		});

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);

		// this timer controls the actions in the game and then repaints after each
		// update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
				controls();
				ticks++;
			}

		});
		timer.start();
	}

	private void controls() {
		if (keys.contains("w")) {
			player.moveY(-speed);
		}
		if (keys.contains("a")) {
			player.moveX(-speed);
		}
		if (keys.contains("s")) {
			player.moveY(speed);
		}
		if (keys.contains("d")) {
			player.moveX(speed);
		}

		if (keys.contains("j")) {
			player.attack(keys, ticks);
		}
		for (GameObject e:objects) {
			if (player.equals(e))
				continue;
			if (player.collides(e)) {
				System.out.println("Collided");
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains("" + e.getKeyChar()))
			keys.add("" + e.getKeyChar());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (keys.contains("" + e.getKeyChar()))
			keys.remove("" + e.getKeyChar());

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
