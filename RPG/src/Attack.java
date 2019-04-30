
public class Attack extends GameObject{
	int locX, locY, r, d, tick;
	public Attack (int x, int y, int right, int down,int ticks) {
		super (x,y, 20, 20, "sword.png");
		locX=x;
		locY=y;
		r=right;
		d=down;
		tick=ticks;
	}
}
