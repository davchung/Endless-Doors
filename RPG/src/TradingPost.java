import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TradingPost extends GameGUI {

	public TradingPost() {
		super(true, "TradingPostBckgrnd.jpg");
	}

	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(35, 140, 35));
		super.draw(g);
		
		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Herculanum", 0, 75));
		g.drawString("TRADING POST", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Papyrus", 0, 25));
		yVal += 80;
		g.drawString("Welcome to my trading post, traveler.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Press the corresponding number to purchase an item.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("(to be implemented later on)", LEFT_MARGIN, yVal);
		yVal += 60;
		g.drawString("[1] Battle Axe", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("[2] Sticks and Stones", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("[3] Poison-tipped Spear", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("[4] ICBM", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("[5] Excalibur", LEFT_MARGIN, yVal);

		g.setFont(new Font("Comic Sans MS", 0, 12));
		yVal += 80;
		g.drawString("Press K to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
	}
}
