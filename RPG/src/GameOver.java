import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver extends GameGUI {
	
	private int score;
	
	public GameOver() {
		super(true, "GameOverBckgrnd.jpg");
	}

	public void draw(Graphics g, int s) {
		score = s;
		g.setColor(new Color(255, 0, 0));
		super.draw(g);;

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Chelsea", 0, 102));
		g.drawString("Game Over!", StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 4 / 10 - 20);
		
		g.setFont(new Font("Chelsea", 0, 60));
		g.drawString("Your score: " + score, StartGame.SCREEN_WIDTH * 2 / 6, StartGame.SCREEN_HEIGHT * 6 / 10 - 10);
		g.drawString("Type N to return to start.", StartGame.SCREEN_WIDTH * 1 / 5, StartGame.SCREEN_HEIGHT * 7 / 10);
	}
}
