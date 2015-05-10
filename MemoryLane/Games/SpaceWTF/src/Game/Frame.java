package Game;

import javax.swing.*;

public class Frame
{
	public static void main(String[] args)
	{

		new Background();
		new Ship();
		new Spawner();
		
		JFrame frame = new JFrame("SpaceWTF");
		
		frame.add(new Control());
		frame.setVisible(true);
		frame.setSize(606,628);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}