
public class Grapple extends Arrow {
	int originalVel;
	boolean struck;
	public Grapple(int x, int y, int width, int height, int pWidth, int pHeight, double right, double down,
			int velocity, int duration, double e, String s) {
		super(x, y, width, height, pWidth, pHeight, right, down, velocity, duration, e, s);
		originalVel = vel*3/2;
	}
	
	
	public void pull() {
		Player p = EndlessDoorsGame.getPlayer();
		double diffX = this.getCX()-p.getCX();
		double diffY = this.getCY()-p.getCY();
		double mag = Math.sqrt(diffX*diffX+diffY*diffY);
		diffX /= mag;
		diffY /= mag;
		for (int i=0;i<10;i++) {
			if (this.collides(p)) {
				EndlessDoorsGame.getPlayer().moveX(-diffX*originalVel/5);
				EndlessDoorsGame.getPlayer().moveY(-diffY*originalVel/5);
				this.expire=0;
				return;
			}else {
				EndlessDoorsGame.getPlayer().moveX(diffX*originalVel/10);
				EndlessDoorsGame.getPlayer().moveY(diffY*originalVel/10);
			}
			
		}
		struck = true;
		
	}
	
	@Override
	public void update() {
		double mag = Math.sqrt(r * r + d * d);
		r = r / mag;
		d = d / mag;
		super.moveX(r * vel);
		super.moveY(d * vel);
		if (struck) {
			pull();
		}
	}


	public void retract(GameObject objs) {
		if (objs instanceof Enemy)
			objs.hit(this.getDamage());
		while (this.collides(objs)) {
			vel=-originalVel/10;
			super.update();
		}
		vel=0;
	}

}
