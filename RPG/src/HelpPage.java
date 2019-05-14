import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpPage extends GameGui {


	public HelpPage(int x, int y, double w, double h) {
		super(x, y, w, h, true, "helppagebkgrnd.jpg");
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}


	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int yVal = StartGame.SCREEN_HEIGHT * 2 / 10;
		g.setColor(new Color(0, 130, 255));
		super.draw(g);
		
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times New Roman", 0, 75));
		g.drawString("HELP PAGE", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Arial", 0, 25));
		yVal += 80;
		g.drawString("W A S D to move the character.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("J to attack.", LEFT_MARGIN, yVal);
		yVal += 60;
		g.drawString("K to place bombs. (to be implemented later on)", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("I to enter/exit the inventory page.", LEFT_MARGIN, yVal);
		yVal += 60;
		g.drawString("? to enter/exit the help page.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("P to pause/play the game.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Plus more features later on??? Stay tuned!", LEFT_MARGIN, yVal);

		g.setFont(new Font("Comic Sans MS", 0, 12));
		yVal += 80;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
	}
}