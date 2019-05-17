import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartGame {
	private JPanel startPanel;
	private BufferedImage startImg;
	public static int SCREEN_WIDTH = 1050;
	public static int SCREEN_HEIGHT = 750;
	public static Clip clip;

	public static void main(String[] args) {
		new StartGame().init();
	}

	public void init() {
		/**
		try { 
			AudioInputStream audioIn;
			audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("sounds/bgm.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			if (!clip.isRunning()) {
				clip.loop(clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		**/
		

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
				int fontSize = 70;
				super.paintComponent(g);
				g.drawImage(startImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
				g.setColor(new Color(250, 250, 250)); // white color
				g.fillRect(0, (SCREEN_HEIGHT / 2) - fontSize, SCREEN_WIDTH, fontSize * 3 / 2);
				g.setColor(new Color(0, 0, 0)); // black color
				g.setFont(new Font("Times New Roman", 0, fontSize));
				g.drawString("Click anywhere to begin game.", SCREEN_WIDTH / 12, SCREEN_HEIGHT / 2);
			}
		};
		// frame doesn't get minimized
		startPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		center(startFrame);
		startFrame.add(startPanel);
		// determines where the frame is placed
		startFrame.pack();
		startFrame.setVisible(true);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				startFrame.setVisible(false);
				startFrame.setEnabled(false);
				(new RPGGame()).beginGame();
			}
		});
	}

	public static void center(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screenSize.getWidth() - SCREEN_WIDTH) / 2);
	    int y = (int) ((screenSize.getHeight() - SCREEN_HEIGHT) / 2)-50;
	    frame.setLocation(x, y);
	}
}
