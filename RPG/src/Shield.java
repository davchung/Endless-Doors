
public class Shield extends Attack{

	public Shield(int x, int y, int width, int height, int duration) {
		super(x-20, y-20, width+40, height+40, duration, 0, "sprites/shield.png");
	}
	@Override
	public void update() {
		this.locX=RPGGame.getPlayer().locX-20;
		this.locY=RPGGame.getPlayer().locY-20;
		//System.out.println("updated");
	}

}
