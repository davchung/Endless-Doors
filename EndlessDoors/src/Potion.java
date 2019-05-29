
public class Potion extends GameObject {

	private String type;

	public Potion(double x, double y, String color) {
		super(x + 15 / 2, y + 15 / 2, 35, 35, true, true, 1, "sprites/items/flask_big_" + color + ".png");
		type = color;
	}

	public void activate() {
		if (type.equals("Invincibility")) { // invincibility potion
			int startTime = EndlessDoorsGame.ticks;
			EndlessDoorsGame.getPlayer().setInvincibility(true);
			if (EndlessDoorsGame.ticks == startTime + 1000) { // turns off invincibility after 10 secs
				EndlessDoorsGame.getPlayer().setInvincibility(false);
			}
		} else if (type.equals("green")) { // expansion potion
			int startTime = EndlessDoorsGame.ticks;
			EndlessDoorsGame.getPlayer().expandPlayer(3);
			if (EndlessDoorsGame.ticks == startTime + 1000) { // turns off expansion after 10 secs
				EndlessDoorsGame.getPlayer().expandPlayer(1/3);
			}
		} else if (type.equals("red")) { // attack damage potion
			// attack.addToDamage(1);
		} else if (type.equals("yellow")) { // health potion
			EndlessDoorsGame.getPlayer().incrementHealth(5 + (int)(Map.roomCount * 1.5)); // total health increase of 25
		}
	}
}
