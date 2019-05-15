import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ChestLoot extends GameGui{
	public ChestLoot() {
		super(false,"ChestLootBckgrnd.jpg");
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		g.drawString("CHEST", (int)(locX + WIDTH*2), (int)(locY + HEIGHT*2));
	}
}
