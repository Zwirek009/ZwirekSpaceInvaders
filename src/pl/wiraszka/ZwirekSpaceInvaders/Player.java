/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

/**
 * <h2>Player in Zwirek Space Invaders</h2>
 * Represents a player in the game.
 * @author Maciej Wiraszka
 *
 */
public class Player implements Commons 
{
	/**
	 * Horizontal placement of the player.
	 */
	private int y;
	
	/**
	 * Number of player's lives.
	 */
	private int numOfLives;
	
	/**
	 * Defines if player can shot (is reloaded).
	 */
	private boolean canShot;
	
	/**
	 * Thread used for player's reloading.
	 */
	Thread reload;
	
	/**
	 * Player constructor.
	 * Sets starting configuration of a player according to interface Commons.
	 */
	public Player()
	{
		y = PLAYER_START_AREA;
		numOfLives = PLAYER_NUM_OF_LIVES;
		setCanShot(true);
	}
	
	/**
	 * Changes horizontal position of a player to one left (if possible).
	 */
	public void moveLeft()
	{
		if(y > 0) --y;
	}
	
	/**
	 * Changes horizontal position of a player to one right (if possible).
	 */
	public void moveRight()
	{
		if(y < BOARD_WIDTH - 1) ++y;
	}
	
	/**
	 * @return the y
	 */
	public int getY() 
	{
		return y;
	}
	
	/**
	 * @param y the value to set
	 */
	public void setY(int y) 
	{
		this.y = y;
	}

	/**
	 * @return the numOfLives
	 */
	public int getNumOfLives() 
	{
		return numOfLives;
	}
	
	/**
	 * Moment of player's shot. Starts new thread to count reload time and allow shooting again after the time set in interface Commons.
	 */
	public void shot()
	{
		setCanShot(false);
		Runnable reloadPlayer = new ReloadPlayer();
		reload = new Thread(reloadPlayer);
		reload.start();
	}
	
	/**
	 * @return the canShot
	 */
	public boolean isCanShot() 
	{
		return canShot;
	}

	/**
	 * @param canShot the canShot to set
	 */
	private void setCanShot(boolean canShot) 
	{
		this.canShot = canShot;
	}
	
	/**
	 * After player is hit, decrease numOfLives and check if he still lives.
	 * @return true if player is out of lives and destroyed, false if player still have nonnegative number of lives left.
	 */
	public boolean isDestroyed()
	{
		--numOfLives;
		y = PLAYER_START_AREA;
		
		if (numOfLives < 0)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * Inner class that holds thread action scheme.
	 * <ul>
	 * 	<li>Sleep for time SHOT_RELOAD specified in interface Commons.</li> 
	 * 	<li>Allows user to shot.</li>
	 * </ul>
	 * @author Maciej Wiraszka
	 *
	 */
	private class ReloadPlayer implements Runnable
	{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
                Thread.sleep(SHOT_RELOAD);
            } catch (InterruptedException e) {
                System.out.println("interrupted sleep");
            }
			setCanShot(true);
		}	
	}
}
