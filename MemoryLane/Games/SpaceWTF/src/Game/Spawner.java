package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Spawner implements ActionListener
{
	Timer timer;
	static boolean space;
	static ArrayList<Lazer> lazerList;
	static ArrayList<Ammo> ammoList;
		double ammoChance = 0.05;
	static ArrayList<Health> healthList;
		double healthChance = 0.01;
	static ArrayList<Enemy> enemyList;
		double enemyChance = 0.1;
		double enemyHealth = 1;
	static ArrayList<EnemyLazer> enemyLazerList;
		static double enemyLazerChance = 0.007;
	static double ammo = 1;
	static double health = 1;
	
	public Spawner()
	{
		lazerList = new ArrayList<Lazer>();
		ammoList = new ArrayList<Ammo>();
		healthList = new ArrayList<Health>();
		enemyList = new ArrayList<Enemy>();
		enemyLazerList = new ArrayList<EnemyLazer>();
		
		timer = new Timer(100, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(Math.random() < ammoChance)
		{
			newAmmo();
		}
		if(Math.random() < healthChance)
		{
			newHealth();
		}
		if(Math.random() < enemyChance)
		{
			newEnemy();
			enemyHealth = enemyHealth + 0.02;
		}
		
		if(Control.space)
		{
			newLazer();
		}
	}
	
	public void newLazer() {
		if(ammo > 0)
		{
			int tempX = Ship.getX() + (Ship.shipImg.getWidth(null)/2) - 1;
			Lazer newLazer = new Lazer(tempX - 10, Ship.getY()-5);
			Lazer newLazer2 = new Lazer(tempX + 9, Ship.getY()-5);
			lazerList.add(newLazer);
			lazerList.add(newLazer2);
			ammo = ammo - 0.03;
		}
	}
	
	public void newAmmo() {
		int tempX = (int)(Math.random()*(576) + 10);
		Ammo newAmmo = new Ammo(tempX, -18);
		ammoList.add(newAmmo);
	}
	
	public void newHealth() {
		int tempX = (int)(Math.random()*(576) + 10);
		Health newHealth = new Health(tempX, -14);
		healthList.add(newHealth);
	}

	public void newEnemy() {
		int tempX = (int)(Math.random()*(515) + 10);
		Enemy newEnemy = new Enemy(tempX, -71,enemyHealth);
		enemyList.add(newEnemy);
	}
	public static void newEnemyLazer(int startX, int startY) {
		EnemyLazer newEnemyLazer = new EnemyLazer(startX, startY);
		enemyLazerList.add(newEnemyLazer);
	}

	public static ArrayList<Ammo> getAmmoList()
	{
		return ammoList;
	}
	public static ArrayList<Lazer> getLazerList()
	{
		return lazerList;
	}
	public static ArrayList<Health> getHealthList()
	{
		return healthList;
	}
}
