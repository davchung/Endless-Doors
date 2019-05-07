import java.awt.Graphics;

public class Player extends GameObject {	
	//directions go clockwise, 0 is north, 7 is north-west
	private int direction = 0; 
	private int cooldown = 0;
	private static Animation a = new Animation();
	int width, height;
	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, a.getFirstImage(), true);
		a = new Animation();
		width= (int)w;
		height = (int)h;
	}
	public void setDirection(int dir) {
		direction = dir;
	}

	public boolean attack(int ticks) {
		if (cooldown>=ticks)
			return false;
		cooldown = ticks+30;
		return true;

	}
	
	public void draw(Graphics g, int r, int d) {
		int dx = 0, dy = 0;
		if (r<0)
			dx= width;
		if(d<0)
			dy =height;
		
		g.drawImage(super.image, (int)super.locX+dx, (int)super.locY+dy, (int)(-1*super.WIDTH),(int)(d*super.HEIGHT),null);
	}
	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0)
			dx= width;
		
		
		g.drawImage(super.image, (int)super.locX+dx, (int)super.locY, (int)(r*super.WIDTH),(int) super.HEIGHT,null);
	}

}
