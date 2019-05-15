import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class NextLevel extends GameGUI {

	public NextLevel() {
		super(true, "NextLevelBckgrnd.jpg");
	}
	
	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 3 / 20;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(80, 0, 150)); // color: purple
		super.draw(g);
		
		g.setColor(new Color(250, 250, 250));
		g.setFont(new Font("Optima", 0, TITLE_FONT));
		g.drawString("CONGRATS!", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Arial", 0, BODY_FONT));
		yVal += 80;
		g.drawString("You completed this level.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Press N to continue to next level. (will be implemented later on)", LEFT_MARGIN, yVal);
		
		g.setFont(new Font("Optima", 0, END_FONT));
		yVal += 80;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
		
		RPGGame.pause();
	}
}
