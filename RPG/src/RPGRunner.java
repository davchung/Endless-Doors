import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private Player player = new Player(25.0, 25.0, 75.0, 100.0, "player.png");
	private Enemy enemy = new Enemy(200.0, 200.0, 80.0, 105.0, "enemy.png");

	private JPanel panel;
	private Timer timer;
	private static final int REFRESH_RATE = 100;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)(screenSize.getWidth()*3/4), HEIGHT = (int)(screenSize.getHeight()*3/4);

	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame frame = new JFrame("Role Playing Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				player.draw(g);
				enemy.draw(g);
			}
		};

		// frame doesn't get minimized
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// frame gets placed a little way from top and left side
		frame.setLocation(WIDTH/10, HEIGHT/10);
		// background color is white
		panel.setBackground(new Color(250, 250, 250));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				//to-do

			}
		});

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);

		// this timer controls the actions in the game and then repaints after each update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
			}
		});
		timer.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if('w'==e.getKeyChar())
			System.out.println("w");
	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

}
