import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Weapon extends Item {

	private double atkSpeed;
	private double dmg;
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();

	public Weapon(String pathName, int gold) {
		super(pathName, gold);
		getInfo(pathName);
	}

	private void getInfo(String pathName) {
		switch (pathName) {
		case "axe.png":
			atkSpeed = 2;
			dmg = 15;
			break;
		case "big_hammer.png":
			atkSpeed = 2;
			dmg = 30;
			break;
		case "diamond_cleaver.png":
			atkSpeed = 1.5;
			dmg = 25;
			break;
		case "diamond_great_sword.png":
			atkSpeed = 1;
			dmg = 20;
			break;
		case "dueling_sword.png":
			atkSpeed = .25;
			dmg = 10;
			break;
		case "golden_sword.png":
			atkSpeed = 1;
			dmg = 30;
			break;
		case "green_staff.png":
			atkSpeed = 2;
			dmg = 20;
			break;
		case "hammer.png":
			atkSpeed = 2.5;
			dmg = 20;
			break;
		case "katana.png":
			atkSpeed = .75;
			dmg = 15;
			break;
		case "lavish_gold_sword.png":
			atkSpeed = 2;
			dmg = 50;
			break;
		case "mace.png":
			atkSpeed = 1.25;
			dmg = 20;
			break;
		case "machete.png":
			atkSpeed = 1;
			dmg = 20;
			break;
		case "platinum_sword.png":
			atkSpeed = 1;
			dmg = 25;
			break;
		case "red_gem_sword.png":
			atkSpeed = 1.25;
			dmg = 30;
			break;
		case "red_magic_staff.png":
			atkSpeed = 3;
			dmg = 60;
			break;
		case "regular_sword.png":
			atkSpeed = 1;
			dmg = 10;
			break;
		case "rusty_sword.png":
			atkSpeed = 1;
			dmg = 5;
			break;
		case "saw_sword.png":
			atkSpeed = 1;
			dmg = 15;
			break;
		case "spiked_baton.png":
			atkSpeed = 2;
			dmg = 30;
			break;

		
		}
	}
}
