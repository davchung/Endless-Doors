
public class Chest extends GameObject{

	private double amountOfGold;

	public Chest(double x, double y, double gold) {
		super(x, y, 50, 50, false, false, 1, "Sprites/chest_empty_open_anim_f0.png");
	}

	
	public void openChest() {
		image = getImage("Sprites/chest_full_open_anim_f1.png");
	}

}
