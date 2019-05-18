
public class Shield extends Attack{

	public Shield(int x, int y, int width, int height, int duration) {
		super(x-20, y-20, width+40, height+40, duration, 0, "sprites/shield.png");
	}
	@Override
	public void update() {
		this.locX=RPGGame.getPlayer().locX-20;
		this.locY=RPGGame.getPlayer().locY-20;
	}
	
	public void change(GameObject e) {
		if (e instanceof Attack) {
			((Attack) e).reflect(this.getCX(),this.getCY());
			RPGGame.getPrimary().add((Attack) e);
			RPGGame.getEnemyAttacks().remove(e);
		}
	}

}
