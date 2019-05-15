
public class Attack extends GameObject{
	private int vel = 0;
	private double r;
	double d;
	private int expire;
	//this is the constructor for a melee attack;
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, int right, int down, int duration,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+duration;
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);
		super.moveX(right*width);//moves to where the player faces
		super.moveY(down*height);
		vel = 0;
	}
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, double x2, double y2,int velocity, int duration,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+duration;
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);
		r=x2;
		d=y2;
		vel =velocity;
		super.moveX(x2*width/2);//moves to where the player faces
		super.moveY(y2*height/2);
		
	}

	public void update() {
		super.moveX(r*vel);
		super.moveY(d*vel);
	}
	public boolean expire(){
		if (RPGGame.ticks>expire) {
			return true;
		}
		return false;
	}
}

