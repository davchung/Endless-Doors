import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Center {
	private static JPanel panel;
	private static Timer timer;
	private static int ticks = 0;
	private static Ball b;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = 500, HEIGHT = 500;
	private static final int REFRESH_RATE = 1;
	private static int mX, mY;
	private static boolean clicked = false;

	public Center() {

	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("BALL");
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Toolkit.getDefaultToolkit().sync();
				super.paintComponent(g);
				drawGame(g);
			}
		};

		b = new Ball(HEIGHT / 2, WIDTH / 2, 50, 50);

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setLocation(WIDTH / 10, HEIGHT / 10);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.requestFocusInWindow();
		timer = new Timer(REFRESH_RATE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateGame();
				panel.repaint();
			}
		});
		timer.start();

		panel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mX = e.getX();
				mY = e.getY();
				b.setInitials((b.getX() - mX)/7, (b.getY() - mY)/7);
				clicked = true;
			}
		});

	}

	private static void drawGame(Graphics g) {
		b.draw(g);
		

	}

	protected static void updateGame() {
		ticks++;

		int hurts = 3 / REFRESH_RATE;
		if (ticks % hurts == 0) {
			checkWalls();
			if(clicked)
				b.current();
		}
	}

	public int getScreenHeight() {
		return HEIGHT;
	}

	public int getScreenWidth() {
		return WIDTH;
	}

	private static void checkWalls() {
		if(b.getX() >= WIDTH -b.getLength() || b.getX() <= 0) {
			b.flipBallVX();
		}
		if(b.getY() >= HEIGHT -b.getWidth()|| b.getY() <= 0) {
			b.flipBallVY();
		}
	}
}