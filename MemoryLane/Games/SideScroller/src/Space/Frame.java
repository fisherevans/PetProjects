package Space;

import javax.swing.*;

public class Frame
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Space");
		
		frame.add(new Board());
		frame.setVisible(true);
		frame.setSize(606,628);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}