
public class Attack extends GameObject{
	private int r, d, expire;
	public Attack (int x, int y, int right, int down, int ticks, String s) {
		super(x ,y, 50, 50, true, s); // uses GameObject's constructor #1
		r=right;
		d=down;
		expire = ticks+20;
		super.moveX(r*50-25);
		super.moveY(d*50-25);
	}

	public boolean expire(){
		if (rpgGame.ticks>expire) {
			return true;
		}
		return false;
	}
}

