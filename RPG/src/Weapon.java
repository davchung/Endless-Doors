
public class Weapon extends Item {

	private String name;

	public Weapon(String pathName, int cost) {
		super(pathName, cost);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "axe.png":

			name = "Battle Axe";
			break;
		case "big_hammer.png":

			name = "Big Hammer";
			break;
		case "diamond_cleaver.png":

			name = "Diamond Cleaver";
			break;
		case "diamond_great_sword.png":

			name = "Diamond Great Sword";
			break;
		case "dueling_sword.png":

			name = "Dueling Sword";
			break;
		case "golden_sword.png":

			name = "Golden Sword";
			break;
		case "green_staff.png":

			name = "Green Staff";
			break;
		case "hammer.png":

			name = "Hammer";
			break;
		case "katana.png":

			name = "Katana";
			break;
		case "lavish_gold_sword.png":

			name = "Lavish Gold Sword";
			break;
		case "mace.png":

			name = "Mace";
			break;
		case "machete.png":

			name = "Machete";
			break;
		case "platinum_sword.png":

			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":

			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":

			name = "Red Magic Staff";
			break;
		case "regular_sword.png":

			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			name = "Spiked Baton";
			break;

		}
	}

	@Override
	public String toString() {
		return name;
	}
}
