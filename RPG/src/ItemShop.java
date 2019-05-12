import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ItemShop extends GameGui{
	public ItemShop(int x, int y,double w, double h) {
		super(x,y,w,h,true,"iSbackgrnd.jpg");
		locX = x;
		locY = y;
		WIDTH = w;
		HEIGHT = h;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(145, 130, 10));
		super.draw(g);
		g.drawString("ITEM SHOP", (int)(locX + WIDTH*1/2), 0);
	}
}
