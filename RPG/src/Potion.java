
public class Potion extends GameObject {

	private String type;

	public Potion(double x, double y, int potionType) {
		super(x, y, 50, 50, true, true, 1, "Sprites/flask_big_blue.png");
		if (potionType == 0) {
			type = "health";
		}
		else if (potionType == 1) {
			type = "invincibility";
			image = getImage("Sprites/flask_big_green.png");
		}
	}
	
	protected void activatePotions() {
		if (type.equals("health") && this.collides(RPGGame.getPlayer())) { // health potion
			RPGGame.getPlayer().incrementHealth(20);
		}
		else if (type.equals("invincibility") && this.collides(RPGGame.getPlayer())) { // invincibility potion
			int startTime = RPGGame.ticks;
			RPGGame.getPlayer().setInvincibility(true);
			if (RPGGame.ticks == startTime + 1000) { // turns of invincibility after 10 secs
				RPGGame.getPlayer().setInvincibility(true);
			}
		}
	}

}
