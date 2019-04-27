
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private JPanel panel;
	private Timer timer;
	//currently 200 times per second, i think? 1000 ticks/5 
	private static final int REFRESH_RATE = 5;
	int ticks = 0;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) (screenSize.getWidth() * 3 / 4),
			HEIGHT = (int) (screenSize.getHeight() * 3 / 4);
	Player player;
	Enemy e;
	private ArrayList<String> keys = new ArrayList<String>();

	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame frame = new JFrame("Role Playing Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50, 50, 50, 50);
		e = new Enemy(100, 100, 100, 100);
		panel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				e.draw(g);
				player.draw(g);
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
			player.moveY(-1.7);
		}
		if (keys.contains("a")) {
			player.moveX(-1.7);
		}
		if (keys.contains("s")) {
			player.moveY(1.7);
		}
		if (keys.contains("d")) {
			player.moveX(1.7);
		}


		if (keys.contains("j")) {
			player.attack(keys,ticks);
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
