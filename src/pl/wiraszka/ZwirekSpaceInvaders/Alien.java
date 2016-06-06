/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

import java.util.Random;

/**
 * <h2>Alien in Zwirek Space Invaders</h2>
 * Represents an alien in the game.
 * @author Maciej Wiraszka
 *
 */
public class Alien implements Commons
{
	/**
	 * Vertical placement of alien.
	 */
	private int x;
	
	/**
	 * Horizontal placement of alien.
	 */
	private int y;
	
	/**
	 * Defines if alien is reloaded.
	 */
	private boolean canShot;
	
	/**
	 * Generator of random numbers (for setting reload time).
	 */
	Random generator;
	
	/**
	 * Converted (from interface Commons) maximum reload time.
	 */
	int maxReload;
	
	/**
	 * Thread used for alien's reloading.
	 */
	Thread reload;
	
	/**
	 * Constructor that holds alien's create process.
	 * <ul>
	 * 	<li>Set starting field indexes.</li> 
	 * 	<li>Initiate other class fields.</li> 
	 * 	<li>Create random numbers generator.</li> 
	 * </ul>
	 * @param x alien vertical position on battlefield
	 * @param y alien horizontal position on battlefield
	 */
	public Alien(int x, int y)
	{
		this.setX(x);
		this.setY(y);
		
		canShot = false;
		generator = new Random();
		maxReload = MAX_ALIEN_RELOAD - MIN_ALIEN_RELOAD;
	}
	
	/**
	 * Try to move alien one position to the left.
	 * @return true if move has been done, false if it couldn't be done
	 */
	public boolean moveAlienLeft()
	{
		if(y > 0)
		{
			--y;
			return true;
		}
		else return false;
	}
	
	/**
	 * Try to move alien one position to the right.
	 * @return true if move has been done, false if it couldn't be done
	 */
	public boolean moveAlienRight()
	{
		if(y < BOARD_WIDTH - 1)
		{
			++y;
			return true;
		}
		else return false;
	}
	
	/**
	 * @return the x
	 */
	public int getX() 
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) 
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() 
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) 
	{
		this.y = y;
	}
	
	/**
	 * @param b the canShot to set
	 */
	private void setCanShot(boolean b) 
	{
		canShot = b;		
	}
	
	/**
	 * @return the canShot
	 */
	public boolean isCanShot() 
	{
		return canShot;		
	}
	
	/**
	 * Moment of alien's shot. Starts new thread to count reload time.
	 * Allow shooting again after the randomized time which min and max values are set in interface Commons.
	 */
	public void reloadAlien()
	{
		setCanShot(false);
		
		Runnable reloadAlien = new ReloadAlien();
		reload = new Thread(reloadAlien);
		reload.start();
	}
	
	/**
	 * Inner class that holds thread action scheme.
	 * <ul>
	 * 	<li>Sleep for randomized time with min and max specified in interface Commons.</li> 
	 * 	<li>Allows alien to shot.</li>
	 * </ul>
	 * @author Maciej Wiraszka
	 *
	 */
	private class ReloadAlien implements Runnable
	{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() 
		{
			try {
                Thread.sleep(generator.nextInt(maxReload) + MIN_ALIEN_RELOAD);
            } catch (InterruptedException e) {
                System.out.println("interrupted sleep");
            }
			setCanShot(true);
		}
		
	}
}
