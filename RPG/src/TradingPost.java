import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TradingPost extends GameGUI {
	
	// instance variables for Trading Post
	private Item slot1;
	private Item slot2;
	private Item slot3;
	private Item slot4;
	private Item slot5;
	private String[] weapons = new String[] {"axe.png", "big_hammer.png", "diamond_cleaver.png", "diamond_great_sword.png", "dueling_sword.png", "golden_sword.png", "green_staff.png", "hammer.png", "katana.png", "lavish_gold_sword.png", "mace.png", "machete.png", "platinum_sword.png", "red_gem_sword.png", "red_magic_staff.png", "regular_sword.png", "rusty_sword.png", "saw_sword.png", "spiked_baton.png"};
	private String[] potions = new String[] {"flask_big_blue.png", "flask_big_green.png", "flask_big_red.png", "flask_big_yellow.png"};

	// constructor #1 for Trading Post
	public TradingPost() {
		super(true, "TradingPostBckgrnd.jpg"); // uses constructor #2 from GameGUI
		slot1 = new Weapon(weapons[GameObject.randInt(0, 18)], 100);
		slot2 = new Weapon(weapons[GameObject.randInt(0, 18)], 200);
		slot3 = new Weapon(weapons[GameObject.randInt(0, 18)], 300);
		slot4 = new Weapon(weapons[GameObject.randInt(0, 3)], 400);
		slot5 = new Weapon(weapons[GameObject.randInt(0, 3)], 500);
	}
	
	// these are getters and setters
	public Item getSlot1() {
		return this.slot1;
	}
	public Item getSlot2() {
		return this.slot2;
	}
	public Item getSlot3() {
		return this.slot3;
	}
	public Item getSlot4() {
		return this.slot4;
	}
	public Item getSlot5() {
		return this.slot5;
	}

	public void draw(Graphics g) {
		int LEFT_MARGIN = StartGame.SCREEN_WIDTH * 2 / 10;
		int LEFT_INDENT = 25;
		int yVal = StartGame.SCREEN_HEIGHT * 1 / 6;
		g.setColor(new Color(35, 140, 35)); // color: green
		super.draw(g);

		g.setColor(new Color(255, 255, 255));
		g.setFont(new Font("Herculanum", 0, TITLE_FONT-25));
		g.drawString("TRADING POST", LEFT_MARGIN, yVal);
		yVal += 50;

		g.setFont(new Font("Papyrus", 0, BODY_FONT-5));
		g.drawString("Welcome to my shop, traveler.", LEFT_MARGIN, yVal);
		yVal += 35;

		g.drawString("Press the corresponding number to purchase an item.", LEFT_MARGIN, yVal);
		yVal += 55;

		
		g.drawString("[1] " + slot1, LEFT_MARGIN, yVal);
		g.drawImage(slot1.getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Cost: " + slot1.getGoldCost(), LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		
		g.setFont(new Font("Papyrus", 0, BODY_FONT-5));
		g.drawString("[2] " + slot2, LEFT_MARGIN, yVal);
		g.drawImage(slot2.getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Cost: " + slot2.getGoldCost(), LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		
		g.setFont(new Font("Papyrus", 0, BODY_FONT-5));
		g.drawString("[3] " + slot3, LEFT_MARGIN, yVal);
		g.drawImage(slot3.getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Cost: " + slot3.getGoldCost(), LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		
		g.setFont(new Font("Papyrus", 0, BODY_FONT-5));
		g.drawString("[4] " + slot4, LEFT_MARGIN, yVal);
		g.drawImage(slot4.getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Cost: " + slot4.getGoldCost(), LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		
		g.setFont(new Font("Papyrus", 0, BODY_FONT-5));
		g.drawString("[5] " + slot5, LEFT_MARGIN, yVal);
		g.drawImage(slot5.getImg(), LEFT_MARGIN * 2, yVal-END_FONT, 50, 50, null);
		yVal += 25;
		g.setFont(new Font("Comic Sans MS", 0, END_FONT));
		g.drawString("Cost: " + slot5.getGoldCost(), LEFT_MARGIN + LEFT_INDENT, yVal);
		yVal += 40;

		g.setFont(new Font("Herculanum", 0, END_FONT));
		g.drawString("Press K to return to game.", LEFT_MARGIN, yVal);
		yVal += 20;
		g.drawString("Thanks for playing our game!", LEFT_MARGIN, yVal);
	}
}
