package diceRoller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;


public class Roller
{
	static String name;
	
	public static void main(String[] args)
	{
			// Declare starting variables
		name = JOptionPane.showInputDialog("Please enter you name:"); 
			// Create the Window
		DiceWindow diceWindow = new DiceWindow();
		ScoreWindow scoreWindow = new ScoreWindow();
	}

	public static class DiceWindow extends JFrame
	{
		private ArrayList<Die> dice = new ArrayList<Die>();
		
			// Create the Image Icons
		private Icon dieBlankIcon = new ImageIcon(getClass().getResource("images/blank.jpg"));
		private Icon dieOneIcon = new ImageIcon(getClass().getResource("images/1.jpg"));
		private Icon dieTwoIcon = new ImageIcon(getClass().getResource("images/2.jpg"));
		private Icon dieThreeIcon = new ImageIcon(getClass().getResource("images/3.jpg"));
		private Icon dieFourIcon = new ImageIcon(getClass().getResource("images/4.jpg"));
		private Icon dieFiveIcon = new ImageIcon(getClass().getResource("images/5.jpg"));
		private Icon dieSixIcon = new ImageIcon(getClass().getResource("images/6.jpg"));
		private Icon dieHoldIcon = new ImageIcon(getClass().getResource("images/hold.jpg"));
		private Icon dieHoldNotIcon = new ImageIcon(getClass().getResource("images/hold_not.jpg"));
		private Icon dieRollIcon = new ImageIcon(getClass().getResource("images/roll.jpg"));
		private Icon dieRollNotIcon = new ImageIcon(getClass().getResource("images/roll_not.jpg"));
		
			// Create the buttons
		private JButton dieOne;
		private JButton dieOneHold;
		private JButton dieTwo;
		private JButton dieTwoHold;
		private JButton dieThree;
		private JButton dieThreeHold;
		private JButton dieFour;
		private JButton dieFourHold;
		private JButton dieFive;
		private JButton dieFiveHold;
		private JButton rollDice;
		private JLabel rollNumber;
		private int rollNumberInt;
		
		public DiceWindow()
		{
			super(name + "'s Dice Roller");
			setVisible(true);
			setLocationRelativeTo(null); // Center on screen
			Insets insets = getInsets();
			setSize(insets.left + insets.right + 312,insets.top + insets.bottom + 67);
			setLayout(null);
			getContentPane().setBackground(new Color(255,255,255));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			EventHandler handle = new EventHandler();
			
				// Set Images, bounds and listener for the buttons then add the button
			dieOne = new JButton(dieOneIcon);
			dieOne.addActionListener(handle);
			dieOne.setBounds(10,10,34,34);
			dieOne.setBorderPainted(false);
			dieOne.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieOne);
			dieOneHold = new JButton(dieHoldNotIcon);
			dieOneHold.addActionListener(handle);
			dieOneHold.setBounds(11,46,34,11);
			dieOneHold.setBorderPainted(false);
			dieOneHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieOneHold);
			
			dieTwo = new JButton(dieOneIcon);
			dieTwo.addActionListener(handle);
			dieTwo.setBounds(56,10,36,36);
			dieTwo.setBorderPainted(false);
			dieTwo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieTwo);
			dieTwoHold = new JButton(dieHoldNotIcon);
			dieTwoHold.addActionListener(handle);
			dieTwoHold.setBounds(57,46,34,11);
			dieTwoHold.setBorderPainted(false);
			dieTwoHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieTwoHold);
			
			dieThree = new JButton(dieOneIcon);
			dieThree.addActionListener(handle);
			dieThree.setBounds(102,10,36,36);
			dieThree.setBorderPainted(false);
			dieThree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieThree);
			dieThreeHold = new JButton(dieHoldNotIcon);
			dieThreeHold.addActionListener(handle);
			dieThreeHold.setBounds(103,46,34,11);
			dieThreeHold.setBorderPainted(false);
			dieThreeHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieThreeHold);
			
			dieFour = new JButton(dieOneIcon);
			dieFour.addActionListener(handle);
			dieFour.setBounds(148,10,36,36);
			dieFour.setBorderPainted(false);
			dieFour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieFour);
			dieFourHold = new JButton(dieHoldNotIcon);
			dieFourHold.addActionListener(handle);
			dieFourHold.setBounds(149,46,34,11);
			dieFourHold.setBorderPainted(false);
			dieFourHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieFourHold);
			
			dieFive = new JButton(dieOneIcon);
			dieFive.addActionListener(handle);
			dieFive.setBounds(194,10,36,36);
			dieFive.setBorderPainted(false);
			dieFive.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieFive);
			dieFiveHold = new JButton(dieHoldNotIcon);
			dieFiveHold.addActionListener(handle);
			dieFiveHold.setBounds(195,46,34,11);
			dieFiveHold.setBorderPainted(false);
			dieFiveHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(dieFiveHold);
				
				// Add Roll Button + number
			rollDice = new JButton(dieRollIcon);
			rollDice.addActionListener(handle);
			rollDice.setBounds(240,10, 62,34);
			rollDice.setBorderPainted(false);
			add(rollDice);
			
			rollNumberInt = 1;
			rollNumber = new JLabel("Roll " + rollNumberInt);
			rollNumber.setBounds(240,46, 62,11);
			rollNumber.setHorizontalAlignment(SwingConstants.CENTER);
			add(rollNumber);
			
				// Create the Dice
			dice.add(new Die());
			dice.add(new Die());
			dice.add(new Die());
			dice.add(new Die());
			dice.add(new Die());
			
			updateButtons();
		}
		
		public void updateButtons()
		{
				// DICE
			dieOne.setIcon(intToIcon(dice.get(0).getNumber())); // Update the dice img
			dieOneHold.setIcon(boolToIcon(dice.get(0).getHold())); // and then the hold image
			dieTwo.setIcon(intToIcon(dice.get(1).getNumber()));
			dieTwoHold.setIcon(boolToIcon(dice.get(1).getHold()));
			dieThree.setIcon(intToIcon(dice.get(2).getNumber()));
			dieThreeHold.setIcon(boolToIcon(dice.get(2).getHold()));
			dieFour.setIcon(intToIcon(dice.get(3).getNumber()));
			dieFourHold.setIcon(boolToIcon(dice.get(3).getHold()));
			dieFive.setIcon(intToIcon(dice.get(4).getNumber()));
			dieFiveHold.setIcon(boolToIcon(dice.get(4).getHold()));
		}
			
			// Dice Roller
		public void rollDice()
		{
				// Update Roll number.
			rollNumberInt++;
			if(rollNumberInt <= 3)
			{
				rollNumber.setText("Roll " + rollNumberInt);
				if(rollNumberInt == 3)
				{
					rollDice.setIcon(dieRollNotIcon);
				}
				else
				{
					rollDice.setIcon(dieRollIcon);
				}
			}
			else
			{
				for(int i = 0 ; i < 5 ; i++) // Loop through each die
				{
					if(dice.get(i).getHold()) // If held
					{
						dice.get(i).invHold(); // Un hold
					}
				}
				rollNumberInt = 1;
				rollDice.setIcon(dieRollIcon);
				rollNumber.setText("Roll " + rollNumberInt);
			}
			
				// Loop through each die and roll anything not held
			for(int i = 0 ; i < 5 ; i++) // Loop through each die
			{
				if(!dice.get(i).getHold())
				{
					dice.get(i).rollDie();
				}
			}
		}
		
			// Return an Icon given and integer
		public Icon intToIcon(int i)
		{
			switch(i)
			{
			case 0:
				return dieBlankIcon;
			case 1:
				return dieOneIcon;
			case 2:
				return dieTwoIcon; 
			case 3:
				return dieThreeIcon; 
			case 4:
				return dieFourIcon; 
			case 5:
				return dieFiveIcon;
			case 6:
				return dieSixIcon;
			default:
				return dieOneIcon;
			}
		}
		
			// Icon from boolean.
		public Icon boolToIcon(boolean bool)
		{
			if(bool)
				return dieHoldIcon;
			else
				return dieHoldNotIcon;
		}
		
			// Button listener
		private class EventHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(event.getSource() == rollDice) // Rolling
				{
					rollDice();
					updateButtons();
				}
				else if(event.getSource() == dieOne || event.getSource() == dieOneHold) // Holding die #1
				{
					dice.get(0).invHold(); // Inverts the hold bool
					updateButtons(); // Redraws the die with the hold update
				}
				else if(event.getSource() == dieTwo || event.getSource() == dieTwoHold) // Holding die #2
				{
					dice.get(1).invHold();
					updateButtons();
				}
				else if(event.getSource() == dieThree || event.getSource() == dieThreeHold) // Holding die #3
				{
					dice.get(2).invHold();
					updateButtons();
				}
				else if(event.getSource() == dieFour || event.getSource() == dieFourHold) // Holding die #4
				{
					dice.get(3).invHold();
					updateButtons();
				}
				else if(event.getSource() == dieFive || event.getSource() == dieFiveHold) // Holding die #5
				{
					dice.get(4).invHold();
					updateButtons();
				}
			}
		}
	}

	public static class ScoreWindow extends JFrame
	{
		
		public ScoreWindow()
		{
			
		}
	}
}
