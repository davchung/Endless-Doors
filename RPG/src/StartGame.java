import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartGame {
	private JPanel startPanel;
	private BufferedImage startImg;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int SCREEN_WIDTH = (int) (screenSize.getWidth() * 3 / 4);
	public static int SCREEN_HEIGHT = (int) (screenSize.getHeight() * 3 / 4);

	public static void main(String[] args) {
		new StartGame().init();
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
				SCREEN_WIDTH = 800;
				SCREEN_HEIGHT = 800;
				(new rpgGame()).beginGame();

			}
		});
	}

}
