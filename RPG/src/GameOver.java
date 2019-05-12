import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver extends GameGui {
	
	public GameOver(int x, int y, double w, double h) {
		super(x, y, w, h, true, "GameOverBckgrnd.jpg");
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 0, 0, 255));
		g.fillRect(0, 0, 1200, 800);

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times New Roman", 0, 100));
		g.drawString("Game Over!", 350, 350);
		g.setFont(new Font("Times New Roman", 0, 40));
		g.drawString("Press B to return to start.", 400, 500);
	}
}
