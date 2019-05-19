
public class Weapon extends Item {

	private double atkSpeed;
	private double dmg;
	private String name;

	public Weapon(String pathName, int gold) {
		super(pathName, gold);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "axe.png":
			atkSpeed = 2;
			dmg = 15;
			name = "Battle Axe";
			break;
		case "big_hammer.png":
			atkSpeed = 2;
			dmg = 30;
			name = "Big Hammer";
			break;
		case "diamond_cleaver.png":
			atkSpeed = 1.5;
			dmg = 25;
			name = "Diamond Cleaver";
			break;
		case "diamond_great_sword.png":
			atkSpeed = 1;
			dmg = 20;
			name = "Diamond Great Sword";
			break;
		case "dueling_sword.png":
			atkSpeed = .25;
			dmg = 10;
			name = "Dueling Sword";
			break;
		case "golden_sword.png":
			atkSpeed = 1;
			dmg = 30;
			name = "Golden Sword";
			break;
		case "green_staff.png":
			atkSpeed = 2;
			dmg = 20;
			name = "Green Staff";
			break;
		case "hammer.png":
			atkSpeed = 2.5;
			dmg = 20;
			name = "Hammer";
			break;
		case "katana.png":
			atkSpeed = .75;
			dmg = 15;
			name = "Katana";
			break;
		case "lavish_gold_sword.png":
			atkSpeed = 2;
			dmg = 50;
			name = "Lavish Gold Sword";
			break;
		case "mace.png":
			atkSpeed = 1.25;
			dmg = 20;
			name = "Mace";
			break;
		case "machete.png":
			atkSpeed = 1;
			dmg = 20;
			name = "Machete";
			break;
		case "platinum_sword.png":
			atkSpeed = 1;
			dmg = 25;
			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":
			atkSpeed = 1.25;
			dmg = 30;
			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":
			atkSpeed = 3;
			dmg = 60;
			name = "Red Magic Staff";
			break;
		case "regular_sword.png":
			atkSpeed = 1;
			dmg = 10;
			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			atkSpeed = 1;
			dmg = 5;
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			atkSpeed = 1;
			dmg = 15;
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			atkSpeed = 2;
			dmg = 30;
			name = "Spiked Baton";
			break;

		}
	}
	@Override
	public String toString() {
		return name;
	}
}
