import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOver extends GameGUI {
	
	public GameOver() {
		super(true, "GameOverBckgrnd.jpg");
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		super.draw(g);;

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Chelsea", 0, 102));
		g.drawString("Game Over!", StartGame.SCREEN_WIDTH * 1 / 4, StartGame.SCREEN_HEIGHT * 4 / 10);
		g.setFont(new Font("Chelsea", 0, 60));
		g.drawString("Press N to return to start.", StartGame.SCREEN_WIDTH * 1 / 5, StartGame.SCREEN_HEIGHT * 6 / 10);
	}
}
