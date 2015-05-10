package game;

public class Player {
	private double x, y, r, xv, yv, hor, ver, tv;
	private double a = 0.04;
	private double da = 0.98;
	
	public Player(int x, int y, int r)
	{
		this.x = x;
		this.y = y;
		this.r = r/2;
		
		xv = 0;
		yv = 0;
		
		hor = 0;
		ver = 0;
		
		tv = 40;
	}
	
	public void updateMovement()
	{		
		xv *= da; // Mult by de accel
		yv *= da;

			// position vel - if > term vel, set to tv. Else add the move direction + dir acc
		xv = xv > tv ? tv : xv + hor*a;
		yv = yv > tv ? tv : yv + ver*a;
		
			// Add vel to x or y
		x += xv;
		y += yv;
		
			// Keep within bounding box (600x570)
		x = x + r > 600 ? 600 - r : x;
		x = x - r < 0 ? 0 : x;
			// ditto
		y = y + r > 600 ? 600 - r : y;
		y = y - r < 0 ? 0 : y;
		
		// System.out.println(x + " " + y + " " + xv + " " + yv);
	}
	
	public void updateControls(boolean up, boolean down, boolean left, boolean right)
	{
		if(up && !down) { ver = -1; }
		else if (!up && down) { ver = 1; }
		else { ver = 0; }
		
		if(left && !right) { hor = -1; }
		else if (!left && right) { hor = 1; }
		else { hor = 0; }
	}
	
	public int getX()
	{
		return (int)(x);
	}
	public int getY()
	{
		return (int)(y);
	}
}
