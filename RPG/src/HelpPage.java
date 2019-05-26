import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpPage extends GameGUI {


	public HelpPage() {
		super(true, "HelpPageBckgrnd.jpg");
	}


	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 1 / 6;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(0, 130, 255)); // color: blue
		super.draw(g);
		
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Chelsea", 0, TITLE_FONT));
		g.drawString("HELP PAGE", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Arial", 0, BODY_FONT));
		yVal += 80;
		g.drawString("W A S D to move the character.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("J to attack and destroy blocks.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("K to activate shield and access the Trading Post (if near the Trader).", LEFT_MARGIN, yVal);
		yVal += 60;
		g.drawString("I to access the Inventory page.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("? to access the Help Page.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("P to pause/play the game.", LEFT_MARGIN, yVal);

		g.setFont(new Font("Optima", 0, END_FONT));
		yVal += 80;
		g.drawString("Press ? to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
	}
}