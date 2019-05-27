
public class Weapon extends Item {

	private String name;
	private int atkDmg;

	public Weapon(String pathName) {
		super(pathName);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "regular_sword.png":
			setGoldCost(100);
			atkDmg = 2;
			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			setGoldCost(50);
			atkDmg = 1;
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			setGoldCost(100);
			atkDmg = 3;
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			setGoldCost(250);
			atkDmg = 5;
			name = "Spiked Baton";
			break;
		case "dueling_sword.png":
			setGoldCost(250);
			atkDmg = 4;
			name = "Dueling Sword";
			break;
		// here
		case "axe.png":
			setGoldCost(200);
			atkDmg = 3;
			name = "Battle Axe";
			break;
		case "golden_sword.png":
			setGoldCost(300);
			atkDmg = 5;
			name = "Golden Sword";
			break;
		case "hammer.png":
			setGoldCost(250);
			atkDmg = 5;
			name = "Hammer";
			break;
		case "mace.png":
			setGoldCost(300);
			atkDmg = 5;
			name = "Mace";
			break;
		case "machete.png":
			setGoldCost(300);
			atkDmg = 5;
			name = "Machete";
			break;
		// here
		case "katana.png":
			setGoldCost(350);
			atkDmg = 6;
			name = "Katana";
			break;
		case "big_hammer.png":
			setGoldCost(400);
			atkDmg = 7;
			name = "Big Hammer";
			break;
		case "green_staff.png":
			setGoldCost(400);
			atkDmg = 7;
			name = "Green Staff";
			break;
		case "diamond_cleaver.png":
			setGoldCost(400);
			atkDmg = 7;
			name = "Diamond Cleaver";
			break;
		// here
		case "lavish_gold_sword.png":
			setGoldCost(800);
			atkDmg = 10;
			name = "Lavish Gold Sword";
			break;
		case "diamond_great_sword.png":
			setGoldCost(700);
			atkDmg = 10;
			name = "Diamond Great Sword";
			break;
		case "platinum_sword.png":
			setGoldCost(750);
			atkDmg = 8;
			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":
			atkDmg = 11;
			setGoldCost(1000);
			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":
			setGoldCost(800);
			atkDmg = 8;
			name = "Red Magic Staff";
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
