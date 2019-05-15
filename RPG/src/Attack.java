
public class Attack extends GameObject{
	
	private int expire;
	
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, int right, int down,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+20;
		
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);//centers drawing on player
		super.moveX(right*width);
		super.moveY(down*height);
		if(right>0) {
			
		} else {
			
		}if(down>0) {
			
		}else {
			
		}
		System.out.println(right);
		System.out.println(down);
		
		
		
	}

	public boolean expire(){
		if (RPGGame.ticks>expire) {
			return true;
		}
		return false;
	}
}

