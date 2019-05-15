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
		g.setColor(new Color(255, 190, 0));
		super.draw(g);
		
		g.setColor(new Color(0, 0, 255));
		g.setFont(new Font("Times New Roman", 0, 75));
		g.drawString("CONGRATS!", LEFT_MARGIN, yVal);
		
		g.setColor(new Color(0, 255, 0));
		g.setFont(new Font("Futura", 0, 25));
		yVal += 80;
		g.drawString("You completed this level.", LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Press B to continue to next level. (will be implemented later on)", LEFT_MARGIN, yVal);
		
		g.setColor(new Color(175, 0, 255));
		g.setFont(new Font("Comic Sans MS", 0, 12));
		yVal += 80;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
		
		RPGGame.pause();
	}
}
