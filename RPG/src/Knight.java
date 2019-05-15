
public class Knight extends Player {

	public Knight(double x, double y, int w, int h) {
		super(x, y, w, h);
	}
	
	public Attack getAttack() {
		Attack sword = new Attack((int)this.getCX(), (int)this.getCY(), this.WIDTH, this.HEIGHT, this.WIDTH, this.HEIGHT,RPGGame.lastR,RPGGame.lastD, "sprites/weapon_golden_sword.png");
		
		return sword;
	}

}
