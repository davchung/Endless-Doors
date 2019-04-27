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
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class RPGRunner implements KeyListener {

	private JPanel panel;
	private Timer timer;
	private static final int REFRESH_RATE = 5;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)(screenSize.getWidth()*3/4), HEIGHT = (int)(screenSize.getHeight()*3/4);
	Player player;
	Enemy e;
	private ArrayList<String> keys = new ArrayList<String>();

	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame frame = new JFrame("Role Playing Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player(50,50,50,50);
		e = new Enemy(100,100,100,100);
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
		frame.setLocation(WIDTH/10, HEIGHT/10);
		// background
		panel.setBackground(new Color(250, 250, 250));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				//clickedAt(me);
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
				direction();
			}

			
		});
		timer.start();
	}

	private void direction() {
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
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(!keys.contains(""+e.getKeyChar()))
			keys.add(""+e.getKeyChar());
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(keys.contains(""+e.getKeyChar()))
			keys.remove(""+e.getKeyChar());
	
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	

}
