import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Runner {

	private JPanel panel;

	public static void main(String[] args) {
		new Runner().init();
	}

	private void init() {
		// map the keystrokes that the panel detects to the game
		mapKeyStrokesToActions(panel);
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("up");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("LEFT"),"left");
		inMap.put(KeyStroke.getKeyStroke("A"),"left");
		map.put("left",new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("left");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("DOWN"),"down");
		inMap.put(KeyStroke.getKeyStroke("S"),"down");
		map.put("down",new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("down");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("RIGHT"),"right");
		inMap.put(KeyStroke.getKeyStroke("D"),"right");
		map.put("right",new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				hit("right");
			}
		});

		inMap.put(KeyStroke.getKeyStroke("SPACE"),"space");
		map.put("space",new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
	}

}
