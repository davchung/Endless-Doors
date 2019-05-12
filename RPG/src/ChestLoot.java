import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ChestLoot extends GameGui{
	public ChestLoot(int x, int y,double w, double h) {
		super(x,y,w,h,false,"chestbkgrnd.jpg");
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		g.drawString("CHEST", (int)(locX + WIDTH*2), (int)(locY + HEIGHT*2));
	}
}
