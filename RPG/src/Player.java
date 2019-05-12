import java.awt.Graphics;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	
	
	private static Animation knight = new Animation("knight_f_idle",4);
	private int width, height;
	private int right,down;

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, knight.getFirst(), true);
		width = (int)w;
		height = (int)h;
	}

	public void setR(int r) {
		right = r;
	}
	public void setD(int d) {
		down = d;
	}
	public void draw(Graphics g, int r, int d) {
		int dx = 0, dy = 0;
		if (r<0)
			dx = width;
		if(d<0)
			dy = height;

		g.drawImage(super.image, (int)super.locX+dx, (int)super.locY+dy, (int)(-1*super.WIDTH), (int)(d*super.HEIGHT), null);
	}
	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0)
			dx = width;
		 

		g.drawImage(knight.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
	}
	
	@Override
	public void draw(Graphics g) {
		
	}

}
