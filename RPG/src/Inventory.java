import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Inventory extends GameGUI {

	private static int gold;
	private static ArrayList<Item> inventory = new ArrayList<Item>();

	public Inventory() {
		super(true, "InventoryBckgrnd.jpg");
		gold = 0;
		inventory.clear();
	}

	public static ArrayList<Item> getItems(){
		return inventory;
	}

	public static int getGold() {
		return Inventory.gold;
	}

	public void addGold(double health) {
		Inventory.gold += health;
	}

	public void subtractGold(int gold) {
		Inventory.gold -= gold;
	}

	public double getTotalDmg() {
		double dmg = 0;
		for(Item i: inventory) {
			if(i instanceof Weapon) {
				dmg += ((Weapon) i).getAtkDmg();
			}
		}
		return dmg;
	}

	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(145, 130, 10)); // color: brown
		super.draw(g);

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Chelsea", 0, TITLE_FONT));
		g.drawString("INVENTORY", LEFT_MARGIN, yVal);

		g.setFont(new Font("Chelsea", 0, BODY_FONT));
		yVal += 80;
		g.drawString("Amount of gold: " + gold, LEFT_MARGIN, yVal);
		g.drawString("Player health: " + RPGGame.getPlayer().getHealth(), LEFT_MARGIN * 3, yVal);
		yVal += 40;
		g.drawString("Items in inventory: ", LEFT_MARGIN, yVal);
		if (RPGGame.getPlayer() instanceof Knight) {
			g.drawString("Attack damage: " + (int)(RPGGame.getPlayer().getDamage()), LEFT_MARGIN * 3, yVal);
		}
		else {
			g.drawString("Attack damage: " + (int)(RPGGame.getPlayer().getDamage() / 2), LEFT_MARGIN * 3, yVal);
		}
		if (inventory.size() == 0) {
			yVal += 40;
			g.drawString("*No items in inventory.*", LEFT_MARGIN+END_FONT, yVal);
		}
		else {
			for (int i = 0; i < inventory.size(); i++) {
				yVal += 40;
				g.drawString("[" + (i+1) + "] " + inventory.get(i), LEFT_MARGIN+END_FONT, yVal);
			}
		}

		g.setFont(new Font("Chelsea", 0, END_FONT));
		yVal += 80;
		g.drawString("Press I to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing Endless Doors!", LEFT_MARGIN, yVal);

		RPGGame.pause();
	}
}
