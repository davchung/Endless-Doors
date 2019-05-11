
public class Attack extends GameObject{
	int locX, locY, r, d, expire;
	public Attack (int x, int y, int right, int down, int ticks, String s, int w, int h) {
		super (x ,y, 50, 50, s, true);
		locX=x;
		locY=y;
		r=right;
		d=down;
		expire=ticks+20;
		super.moveX(r*50-25);
		super.moveY(d*50-25);
		WIDTH = w;
		HEIGHT = h;
	}

	public boolean expire(){
		if (RPGRunner.ticks>expire) {
			return true;
		}
		return false;
	}
}

