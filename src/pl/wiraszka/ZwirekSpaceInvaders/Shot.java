/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

/**
 * <h2>Shot in Zwirek Space Invaders</h2>
 * Represents a single player or alien shot in the game.
 * @author Maciej Wiraszka
 *
 */
public class Shot implements Commons
{
	/**
	 * Vertical placement of shot.
	 */
	private int x;
	
	/**
	 * Horizontal placement of shot.
	 */
	private int y;
	
	/**
	 * Defines who shoot that shot.
	 * <ul>
	 * 	<li>true - alien</li> 
	 * 	<li>false - player</li>
	 * </ul>
	 */
	private boolean alienShot;
	
	/**
	 * Counts number of screen refreshes from last shot view refresh.
	 * Max defined in interface Commons.
	 */
	private int moveCnt;
	
	/**
	 * Defines, if shot is out of board (ready to be delete).
	 */
	private boolean outOfBoard;
	
	/**
	 * Constructor that holds shot's create process.
	 * <ul>
	 * 	<li>Set starting field indexes.</li> 
	 * 	<li>Initiate other class fields.</li>
	 * </ul>
	 * @param x shot vertical position on battlefield
	 * @param y shot horizontal position on battlefield
	 * @param alienShot defines who shot that shot, true - alien, false - player
	 */
	public Shot(int x, int y, boolean alienShot)
	{
		this.setX(x);
		this.setY(y);
		this.alienShot = alienShot;
		moveCnt = 0;
		setOutOfBoard(false);
	}
	
	/**
	 * Try to move shot by one position (down if alien shot, up if player shot).
	 * Allows to shot only in right refresh frame.
	 * Sets outOfBoard field to true if shot is outside the battlefield after the move.
	 */
	public void tryMoveShot()
	{
		if (moveCnt == SHOT_MOVE_FREQ)
		{
			if(alienShot)
			{
				++x;
				if(x >= BOARD_HEIGTH)
				{
					setOutOfBoard(true);
				}
			}
			else
			{
				--x;
				if(x < 0)
				{
					setOutOfBoard(true);
				}
			}
			
			moveCnt = 0;
		}
		else ++moveCnt;
	}

	/**
	 * @return the outOfBoard
	 */
	public boolean isOutOfBoard() 
	{
		return outOfBoard;
	}

	/**
	 * @param outOfBoard the outOfBoard to set
	 */
	public void setOutOfBoard(boolean outOfBoard) 
	{
		this.outOfBoard = outOfBoard;
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
}
