
public class Potion extends GameObject {

	private String type;

	public Potion(double x, double y, String color) {
		super(x + 15 / 2, y + 15 / 2, 35, 35, true, true, 1, "sprites/items/flask_big_" + color + ".png");
		type = color;
	}

	public void activate() {
		if (type.equals("Invincibility")) { // invincibility potion
			int startTime = RPGGame.ticks;
			RPGGame.getPlayer().setInvincibility(true);
			if (RPGGame.ticks == startTime + 1000) { // turns of invincibility after 10 secs
				RPGGame.getPlayer().setInvincibility(true);
			}
		} else if (type.equals("green")) {
			// to do
		} else if (type.equals("red")) {
			// to do
		} else if (type.equals("yellow")) { // health potion
			RPGGame.getPlayer().incrementHealth(9 + (int)(Map.roomCount * 1.5)); // total health increase of 25
			System.out.println("here");
		}
	}
}
