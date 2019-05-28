import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class StringGraphic {
	private String text;
	private int xLoc;
	private int yLoc;
	private Font textFont;
	private Color col;
	public StringGraphic(String s, int x,int y, Color c, Font f) {
		this.text = s;
		this.xLoc = x;
		this.yLoc = y;
		this.col = c;
	}
	public void drawString(Graphics g) {
		g.setColor(col);
		g.setFont(textFont);
		g.drawString(this.text,xLoc,yLoc);
	}
}
