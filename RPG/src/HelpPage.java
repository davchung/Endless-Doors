import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpPage extends GameGUI {


	public HelpPage() {
		super(true, "HelpPageBckgrnd.jpg");
	}


	public void draw(Graphics g) {
		int LEFT_MARGIN = EndlessDoorsRunner.SCREEN_WIDTH * 1 / 6;
		int yVal = EndlessDoorsRunner.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(0, 130, 255)); // color: blue
		super.draw(g);
		
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Chelsea", 0, TITLE_FONT));
		g.drawString("HELP PAGE", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Chelsea", 0, BODY_FONT));
		yVal += 80;
		g.drawString("W A S D to move the character.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("J to do main attack.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("K to use special power.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("O to deploy the super secret weapon.", LEFT_MARGIN, yVal);
		yVal += 60;
		
		g.drawString("I to access the Inventory, or the Trading Post if near Trader.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("? to view the Help Page.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("P to pause/play the game.", LEFT_MARGIN, yVal);

		g.setFont(new Font("Chelsea", 0, END_FONT));
		yVal += 80;
		g.drawString("Press ? to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Read the README.md file for more information.", LEFT_MARGIN, yVal);
	}
}