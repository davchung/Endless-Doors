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
		g.setColor(new Color(0, 130, 255));
		super.draw(g);
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Times New Roman", 0, 30));
		g.drawString("HELP PAGE", (int) (locX + WIDTH * 1 / 2), (int)locY); //hardcoded this a bit, will fix
		g.drawString("W A S D to move the character.", 100, (int)(locY + 25));
		g.drawString("J to attack.", 100, (int)(locY + 25*2));
		g.drawString("k to build walls.", 100, (int)(locY + 25*3));
		g.drawString("K to place bombs. (to be implemented later on)", 100, (int)(locY + 25*4));
		g.drawString("I to check inventory.", 100, (int)(locY + 25*5));
		g.drawString("? to enter/exit the Help Page.", 100, (int)(locY + 25*7));
		g.drawString("P to pause/play the game.", 100, (int)(locY + 25*8));

		g.setFont(new Font("Comic Sans MS", 0, 12));
		g.drawString("Thanks for playing our game!", 100, 500);
	}
}