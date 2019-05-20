import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Inventory extends GameGUI {

	private int numWalls = 0;
	private static int gold = 0;
	private static ArrayList<Item> inventory = new ArrayList<Item>();

	public Inventory() {
		super(true, "InventoryBckgrnd.jpg");
	}
	
	public static ArrayList<Item> getItems(){
		return inventory;
	}
	public void addWalls(int num) {
		numWalls += num;
	}

	public void removeWalls(int num) {
		numWalls -= num;
	}
	
	public static int getGold() {
		return Inventory.gold;
	}

	public void addGold(int gold) {
		Inventory.gold += gold;
	}

	public void subtractGold(int gold) {
		Inventory.gold -= gold;
	}

	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 4;
		g.setColor(new Color(145, 130, 10)); // color: brown
		super.draw(g);

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Herculanum", 0, TITLE_FONT));
		g.drawString("INVENTORY", LEFT_MARGIN, yVal);

		g.setFont(new Font("Papyrus", 0, BODY_FONT));
		yVal += 80;
		g.drawString("Amount of gold: " + gold, LEFT_MARGIN, yVal);
		yVal += 40;
		g.drawString("Items in inventory: ", LEFT_MARGIN, yVal);
		if (inventory.size() == 0) {
			yVal += 40;
			g.drawString("*No items in inventory.*", LEFT_MARGIN+END_FONT, yVal);
		}
		for (int i = 0; i < inventory.size(); i++) {
			yVal += 40;
			g.drawString("[" + (i+1) + "] " + inventory.get(i), LEFT_MARGIN+END_FONT, yVal);
			g.drawImage(inventory.get(i).getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		}

		g.setFont(new Font("Herculanum", 0, END_FONT));
		yVal += 80;
		g.drawString("Press I to return to game.", LEFT_MARGIN, yVal);
		yVal += 25;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);

		RPGGame.pause();
	}
}
