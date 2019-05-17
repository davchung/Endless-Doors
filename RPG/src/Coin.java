
public class Coin extends GameObject {

	public Coin(double x, double y, int gold) {
		super(x, y, 50, 50, true, false, gold, "Sprites/coin_anim_f0.png");
	}
	
	@Override
	public void hit(int damage) {
		if (RPGGame.ticks > hittable&&!invincible) {
			System.out.println("here!");
			health -= damage;
			RPGGame.getInventory().addGold(damage);
			hittable = RPGGame.ticks + 26;
		}
	}


}
