import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartGame {
	private JPanel startPanel;
	public static int SCREEN_WIDTH = 1050;
	public static int SCREEN_HEIGHT = 750;
	public static Clip clip;
	public static int ticks;
	private static final int REFRESH_RATE = 10;
	public static JFrame startFrame;
	Timer timer;

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
		Knight k = new Knight(163,275);
		k.expandPlayer(4);
		Archer a = new Archer(1050-163-200,275);
		a.expandPlayer(4);
		startFrame = new JFrame("Welcome to RPG!");
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);
		startPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(250, 250, 250)); // white color
				g.setColor(new Color(0, 0, 0)); // black color
				k.drawTutorial(g);
				a.drawTutorial(g);
				g.drawLine(525, 0, 525, 750);
			}
		};
		startPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		center(startFrame);
		startFrame.add(startPanel);
		startFrame.setBackground(Color.LIGHT_GRAY);
		// determines where the frame is placed
		startFrame.pack();
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer = new Timer(REFRESH_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startPanel.repaint();
				ticks++;
			}

		});
		timer.start();
		startPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getX()<SCREEN_WIDTH/2) {
					RPGGame.selectClass(0);
				}else {
					RPGGame.selectClass(1);
				}
				(new RPGGame()).beginGame();
			}
		});
		
	}
	public void closeFrame() {
		startFrame.dispose();
	}

	public static void center(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screenSize.getWidth() - SCREEN_WIDTH) / 2);
	    int y = (int) ((screenSize.getHeight() - SCREEN_HEIGHT) / 2)-50;
	    frame.setLocation(x, y);
	}
}
