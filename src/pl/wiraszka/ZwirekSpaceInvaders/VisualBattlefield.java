/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

import java.util.ArrayList;

/**
 * <h2>VisualBattlefield in Zwirek Space Invaders</h2>
 * Represents structure of battlefield visable to user. 
 * Is created in Model and sent to View to propre display it to user.
 * @author Maciej Wiraszka
 *
 */
public class VisualBattlefield implements Commons 
{
	/**
	 * Table holding names of pictures to be shown on the battlefield. 
	 * Names of pictures and battlefield size according to interface Commons.
	 */
	public String[][] imageName;
	
	/**
	 * Constructor that holds whole process of creating VisualBattlefield
	 * <ul>
	 * 	<li>Create empty imageName table with battlefield size according to interface Commons</li> 
	 * 	<li>Insert player, aliens and shots names of images in proper order.</li>
	 * </ul>
	 * @param player to display
	 * @param aliens array holding aliens to display
	 * @param alienShots array holding alien shots to display
	 * @param playerShots array holding player shots to display
	 */
	public VisualBattlefield(Player player, ArrayList<Alien> aliens, ArrayList<Shot> alienShots, ArrayList<Shot> playerShots)
	{
		imageName = new String[BOARD_HEIGTH][BOARD_WIDTH];
		
		// add player
		imageName[BOARD_HEIGTH-1][player.getY()] = PLAYER_IMAGE_NAME;
		
		// add aliens
		for (Alien alien : aliens)
		{
			imageName[alien.getX()][alien.getY()] = ALIEN_IMAGE_NAME;
		}
		
		// add player shots
		for (Shot shot : playerShots)
		{
			imageName[shot.getX()][shot.getY()] = SHOT_IMAGE_NAME;
		}
		
		// add alien shots
		for (Shot shot : alienShots)
		{
			if(imageName[shot.getX()][shot.getY()] == ALIEN_IMAGE_NAME || imageName[shot.getX()][shot.getY()] == ALIEN_AND_SHOT_IMAGE_NAME)
				imageName[shot.getX()][shot.getY()] = ALIEN_AND_SHOT_IMAGE_NAME;
			else imageName[shot.getX()][shot.getY()] = SHOT_IMAGE_NAME;
		}
	}
}
