
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
			name = "Regular Sword";
			break;
		case "rusty_sword.png":
			setGoldCost(50);
			name = "Rusty Sword";
			break;
		case "saw_sword.png":
			setGoldCost(100);
			name = "Saw Sword";
			break;
		case "spiked_baton.png":
			setGoldCost(250);
			name = "Spiked Baton";
			break;
		case "dueling_sword.png":
			setGoldCost(250);
			name = "Dueling Sword";
			break;
		case "axe.png":
			setGoldCost(200);
			name = "Battle Axe";
			break;
		case "golden_sword.png":
			setGoldCost(300);
			name = "Golden Sword";
			break;
		case "hammer.png":
			setGoldCost(250);
			name = "Hammer";
			break;
		case "mace.png":
			setGoldCost(300);
			name = "Mace";
			break;
		case "machete.png":
			setGoldCost(300);
			name = "Machete";
			break;
		case "katana.png":
			setGoldCost(350);
			name = "Katana";
			break;
		case "big_hammer.png":
			setGoldCost(400);
			name = "Big Hammer";
			break;
		case "green_staff.png":
			setGoldCost(400);
			name = "Green Staff";
			break;
		case "diamond_cleaver.png":
			setGoldCost(400);
			name = "Diamond Cleaver";
			break;
		case "lavish_gold_sword.png":
			setGoldCost(800);
			name = "Lavish Gold Sword";
			break;
		case "diamond_great_sword.png":
			setGoldCost(700);
			name = "Diamond Great Sword";
			break;
		case "platinum_sword.png":
			setGoldCost(750);
			name = "Platinum Sword";
			break;
		case "red_gem_sword.png":
			setGoldCost(1000);
			name = "Red Gem Sword";
			break;
		case "red_magic_staff.png":
			setGoldCost(800);
			name = "Red Magic Staff";
			break;

		}
		atkDmg = this.getGoldCost()/50;
		int gold = this.getGoldCost();
		while (gold>300) {
			atkDmg++;
			gold-=300;
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
