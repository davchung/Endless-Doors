import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class RPGRunner {

	private JPanel panel;
	private Timer timer;
	private static final int REFRESH_RATE = 100;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)(screenSize.getWidth()*3/4), HEIGHT = (int)(screenSize.getHeight()*3/4);
	Player p;
	Enemy e;
	public static void main(String[] args) {
		new RPGRunner().init();
	}

	private void init() {
		JFrame frame = new JFrame("Role Playing Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new Player(50,50,50,50);
		e = new Enemy(100,100,100,100);
		panel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				p.draw(g);
			}
		};
		
		// frame doesn't get minimized
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// frame gets placed a little way from top and left side
		frame.setLocation(WIDTH/10, HEIGHT/10);
		// background
		panel.setBackground(new Color(250, 250, 250));
		// map the keystrokes that the panel detects to the game
		mapKeyStrokesToActions(panel);
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

		// this timer controls the actions in the game and then repaints after each update to data
		timer = new Timer(REFRESH_RATE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
			}
		});
		timer.start();
	}

	private void mapKeyStrokesToActions(JPanel panel) {

		// A map is an Data storage interface which defines
		// an association of a key with a value
		// to "add" to a map you use the "put" method
		// to "get" from a map you use "get(key)" and the 
		// value associated with the key is returned (or null)
		ActionMap map = panel.getActionMap();
		InputMap inMap = panel.getInputMap();

		// code below associates pressing the up arrow with the command "up"
		// essentially creating the command "up" being broadcast any time the 
		// up key is hit
		inMap.put(KeyStroke.getKeyStroke("pressed UP"), "up");
		inMap.put(KeyStroke.getKeyStroke("pressed W"), "up");
		// code below associates the "up" action with anything in the 
		// actionPerformed method.  Right now, it just prints something
		map.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hit("up");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("LEFT"),"left");
		inMap.put(KeyStroke.getKeyStroke("A"),"left");
		map.put("left",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("left");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("DOWN"),"down");
		inMap.put(KeyStroke.getKeyStroke("S"),"down");
		map.put("down",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("down");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("RIGHT"),"right");
		inMap.put(KeyStroke.getKeyStroke("D"),"right");
		map.put("right",new AbstractAction(){


			@Override
			public void actionPerformed(ActionEvent e) {
				hit("right");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("SPACE"),"space");
		map.put("space",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("space");
			}
		});
	}

	public  void hit(String s) {
		keyHit(s);
		panel.repaint();
	}

	// What do you want to do when a key is hit?
	public void keyHit(String s) {
		System.out.println("Key hit: "+s);
		p.moveX(10);
	}

}
