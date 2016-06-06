/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

/**
 * <h2> Main class of Zwirek Space Invaders </h2>
 * Class with main() method for game initialization. 
 * Game implemented using MVC pattern.
 * @author Maciej Wiraszka
 * 
 */
public class Main 
{

	/**
	 * Initiates MVC objects.
	 * @param args never used
	 */
	public static void main(String[] args) 
	{
		View gameView = new View();
		Model gameModel = new Model(gameView);
		new Controller(gameView, gameModel);
	}

}
