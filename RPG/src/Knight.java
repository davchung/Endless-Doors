
public class Knight extends Player {

	public Knight(double x, double y, int w, int h) {
		super(x, y, w, h);
	}
	
	public Attack getAttack() {
		Attack sword = new Attack((int)this.getCX(), (int)this.getCY(), this.WIDTH, this.HEIGHT, this.WIDTH, this.HEIGHT,RPGGame.lastR,RPGGame.lastD,20,this.getDamage(), "sprites/items/lavish_gold_sword.png");
		
		return sword;
	}
	public Attack getSpecial() {
		Attack shield = new Shield((int)this.getLocX(), (int)this.getLocY(), this.WIDTH, this.HEIGHT,50);
		return shield;
	}
	
	@Override
	public void hit(int damage) {
		if (RPGGame.ticks > hittable&&!invincible&&RPGGame.getSpecial().size()==0) {
			health -= damage;
			hittable = RPGGame.ticks + 26;
		}
	}

}
