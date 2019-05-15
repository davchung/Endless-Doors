import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Trader extends GameGui{
	
	public Trader() {
		super(true,"trader.png");
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		g.drawString("ITEM SHOP", (int)(locX + WIDTH*1/2), (int)locY);
	}
}
