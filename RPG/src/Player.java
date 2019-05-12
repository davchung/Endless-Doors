import java.awt.Graphics;

public class Player extends GameObject {
	
	private static Animation run = new Animation("knight_f_run",4);
	private static Animation idle = new Animation("knight_f_idle",4);
	private int width, height;
	private int right,down;

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, idle.getFirst(), true);
		width = (int)w;
		height = (int)h;
	}

	public void setR(int r) {
		right = r;
	}
	public void setD(int d) {
		down = d;
	}

	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0) 
			dx = width;
		if (down != 0 || right != 0) {
			g.drawImage(run.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
			return;
		}
		
		g.drawImage(idle.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
		
	}
	
	@Override
	public void draw(Graphics g) {
		//putting this here to prevent graphics from drawing player when running through all GameObjects
	}

	public boolean build(int ticks) {
		// TODO Auto-generated method stub
		return false;
	}
}
