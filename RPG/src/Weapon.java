
public class Weapon extends Item {

	private String name;
	private int atkDmg;

	public Weapon(String pathName, int cost) {
		super(pathName, cost);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "axe.png":
			atkDmg = 3;
			name = "Battle Axe";
			break;
		case "big_hammer.png":
			atkDmg = 7;
			name = "Big Hammer";
			break;
		case "diamond_cleaver.png":
			atkDmg = 7;
			name = "Diamond Cleaver";
			break;
		case "diamond_great_sword.png":
			atkDmg = 10;
			name = "Diamond Great Sword";
			break;
		case "dueling_sword.png":
			atkDmg = 4;
			name = "Dueling Sword";
			break;
		case "golden_sword.png":
			atkDmg = 5;
			name = "Golden Sword";
			break;
		case "green_staff.png":
			atkDmg = 7;
			name = "Green Staff";
			break;
		case "hammer.png":
			atkDmg = 5;
			name = "Hammer";
			break;
		case "katana.png":
			atkDmg = 6;
			name = "Katana";
			break;
		case "lavish_gold_sword.png":
			atkDmg = 10;
			name = "Lavish Gold Sword";
			break;
		case "mace.png":
			atkDmg = 5;
			name = "Mace";
			break;
		case "machete.png":
			atkDmg = 5;
			name = "Machete";
			break;
		case "platinum_sword.png":
			atkDmg = 8;
			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":
			atkDmg = 11;
			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":
			atkDmg = 8;
			name = "Red Magic Staff";
			break;
		case "regular_sword.png":
			atkDmg = 2;
			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			atkDmg = 1;
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			atkDmg = 3;
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			atkDmg = 5;
			name = "Spiked Baton";
			break;

		}
	}

	@Override
	public String toString() {
		return name;
	}
	
	public int getAtkDmg() {
		return atkDmg;
	}
}
