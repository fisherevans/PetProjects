package diceRoller;

public class Die
{
		// Define Variables
	private int number;
	private boolean hold;
	
	public Die()
	{
			// Initiate variables
		number = 0;
		hold = false;
	}
	
	public void rollDie()
	{
		number = (int)(Math.random()*6 + 1); // Random Float 1.0-6.9 to int
	}
	public void invHold()
	{
		hold = !hold; 
	}
	public void setBlank()
	{
		number = 0;
	}
	
	public boolean getHold()
	{
		return hold; // Return if held
	}
	public int getNumber()
	{
		return number; // Return current number.
	}
}
