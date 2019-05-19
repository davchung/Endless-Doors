
public class Potion extends Item {

	private String type;

	public Potion(String pathName, int cost) {
		super(pathName, cost);
		getInfo(pathName);
		activate();
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "flask_big_blue.png":
			type = "Invincibility";
			break;
		case "flask_big_green.png":
			type = "Poison";
			break;
		case "flask_big_red.png":
			type = "Damage";
			break;
		case "flask_big_yellow.png":
			type = "Healing";
		}
	}

	private void activate() {
		if (type.equals("Invincibility")) { // invincibility potion
			int startTime = RPGGame.ticks;
			RPGGame.getPlayer().setInvincibility(true);
			if (RPGGame.ticks == startTime + 1000) { // turns of invincibility after 10 secs
				RPGGame.getPlayer().setInvincibility(true);
			}
		}
		else if (type.equals("Poison")) {
			// to do
		}
		else if (type.equals("Damage")) {
			// to do
		}
		else if (type.equals("Healing")) { // health potion
			for (int i = 0; i < 5; i++) {
				RPGGame.getPlayer().incrementHealth(5); // total health increase of 25
			}
		}
	}
	
	@Override
	public String toString() {
		return type + " Potion";
	}
}
