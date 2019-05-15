import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TradingPost extends GameGUI {

	public TradingPost() {
		super(true, "TradingPostBckgrnd.jpg");
	}

	public void draw(Graphics g) {
		System.out.println("PURCHASING DOES NOT WORK YET!");
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int LEFT_INDENT = 25;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 5;
		g.setColor(new Color(35, 140, 35)); // color: green
		super.draw(g);

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Herculanum", 0, TITLE_FONT));
		g.drawString("TRADING POST", LEFT_MARGIN, yVal);
		yVal += 55;

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		g.drawString("Welcome to my shop, traveler.", LEFT_MARGIN, yVal);
		yVal += 30;

		g.drawString("Press the corresponding number to purchase an item.", LEFT_MARGIN, yVal);
		yVal += 55;

		g.drawString("[1] Battle Axe", LEFT_MARGIN, yVal);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("The favored weapons of Vikings around the world since 750 CE.", LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		g.drawString("[2] Sticks and Stones", LEFT_MARGIN, yVal);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("... may break my bones, but words can never hurt me.", LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		g.drawString("[3] Poison-tipped Spear", LEFT_MARGIN, yVal);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Inflicts 1 damage every second for 10 seconds.", LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		g.drawString("[4] ICBM", LEFT_MARGIN, yVal);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString(" \"Now I am become Death, the destroyer of worlds.\" ", LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		g.drawString("[5] Excalibur", LEFT_MARGIN, yVal);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Removal from its stone is not included.", LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 55;

		g.setFont(new Font("Herculanum", 0, END_FONT));
		g.drawString("Press K to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
	}
}
