
public class Grapple extends Attack {

	public Grapple(int x, int y, int width, int height, int pWidth, int pHeight, double right, double down,
			int velocity, int duration, double e, String s) {
		super(x, y, width, height, pWidth, pHeight, right, down, velocity, duration, e, s);
	}
	
	
	public void pull() {
		Player p = RPGGame.getPlayer();
		double diffX = this.getCX()-p.getCX();
		double diffY = this.getCY()-p.getCY();
		double mag = Math.sqrt(diffX*diffX+diffY+diffY);
		diffX /= mag;
		diffY /= mag;
		RPGGame.getPlayer().moveX(diffX*vel);
		RPGGame.getPlayer().moveY(diffY*vel);
	}

}
