
public class Weapon extends Item {

	private double atkSpeed;
	private double dmg;
	private String name;

	public Weapon(String pathName, int cost) {
		super(pathName, cost);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "axe.png":
			dmg = 1.5;
			name = "Battle Axe";
			break;
		case "big_hammer.png":
			dmg = 3;
			name = "Big Hammer";
			break;
		case "diamond_cleaver.png":
			dmg = 2.5;
			name = "Diamond Cleaver";
			break;
		case "diamond_great_sword.png":
			dmg = 2;
			name = "Diamond Great Sword";
			break;
		case "dueling_sword.png":
			dmg = 1;
			name = "Dueling Sword";
			break;
		case "golden_sword.png":
			dmg = 3;
			name = "Golden Sword";
			break;
		case "green_staff.png":
			dmg = 2;
			name = "Green Staff";
			break;
		case "hammer.png":
			dmg = 2;
			name = "Hammer";
			break;
		case "katana.png":
			dmg = 1.5;
			name = "Katana";
			break;
		case "lavish_gold_sword.png":
			dmg = 5;
			name = "Lavish Gold Sword";
			break;
		case "mace.png":
			dmg = 2;
			name = "Mace";
			break;
		case "machete.png":
			dmg = 2;
			name = "Machete";
			break;
		case "platinum_sword.png":
			dmg = 2.5;
			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":
			dmg = 3;
			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":
			dmg = 6;
			name = "Red Magic Staff";
			break;
		case "regular_sword.png":
			dmg = 1;
			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			dmg = .5;
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			dmg= 1.5;
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			dmg = 3;
			name = "Spiked Baton";
			break;

		}
	}
	@Override
	public String toString() {
		return name;
	}
}
