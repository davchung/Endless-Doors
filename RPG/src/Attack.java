
public class Attack extends GameObject{
	
	private int expire;
	
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, int right, int down,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+20;
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);
		super.moveX(right*width);//moves to where the player faces
		super.moveY(down*height);
	}

	public boolean expire(){
		if (RPGGame.ticks>expire) {
			return true;
		}
		return false;
	}
}

