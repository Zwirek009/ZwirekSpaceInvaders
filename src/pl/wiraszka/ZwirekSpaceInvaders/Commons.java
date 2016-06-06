/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

/**
 * <h2>Commons in Zwirek Space Invaders</h2>
 * Interface that holds all constants for whole program.
 * These constants defines many different aspects of program way of working and their change can affect how program works.
 * Is implemented by most of program classes.
 * @author Maciej Wiraszka
 *
 */
public interface Commons 
{
	/**
	 * Battlefield width (number of positions that aliens, players etc can occupy).
	 */
	public static final int BOARD_WIDTH = 11;
	
	/**
	 * Battlefield height (number of positions that aliens, players etc can occupy).
	 */
	public static final int BOARD_HEIGTH = 10;
	
	/**
	 * Size of width and height in single squared field on the battlefield (in pixels).
	 */
	public static final int BOARD_FIELD_SIZE = 40;
	
	/**
	 * Name of file with player image to be displayed in View.
	 */
	public static final String PLAYER_IMAGE_NAME = "graphic/player.png";
	
	/**
	 * Name of file with alien image to be displayed in View.
	 */
	public static final String ALIEN_IMAGE_NAME = "graphic/alien.png";
	
	/**
	 * Name of file with shot image to be displayed in View.
	 */
	public static final String SHOT_IMAGE_NAME = "graphic/shot.png";
	
	/**
	 * Name of file with combined alien and shot image do be displayed in View. 
	 */
	public static final String ALIEN_AND_SHOT_IMAGE_NAME = "graphic/alienAndShot.png";
	
	/**
	 * Name of file with start communicate image do be displayed in View.
	 */
	public static final String START_IMAGE_NAME = "graphic/welcomeScreen.png";
	
	/**
	 * Name of file with aliens-winning end image do be displayed in View.
	 */
	public static final String END_NEG_IMAGE_NAME = "graphic/endNegative.png";
	
	/**
	 * Name of file with player-winning end image do be displayed in View.
	 */
	public static final String END_WIN_IMAGE_NAME = "graphic/endWin.png";
	
	/**
	 * Path to folder with score images do be displayed in View.
	 */
	public static final String SCORE_IMAGE_PATH = "graphic/score/";
	
	/**
	 *Path to folder with health level image do be displayed in View.
	 */
	public static final String HEALTH_IMAGE_PATH = "graphic/health/";
	
	/**
	 * Index of horizontal start area of player.
	 */
	public static final int PLAYER_START_AREA = 5;
	
	/**
	 * Starting number of player lives.
	 */
	public static final int PLAYER_NUM_OF_LIVES = 3;
	
	/**
	 * Period of time between screen reprinting (in milliseconds).
	 */
	public static final int REFRESH_DELAY = 100;
	
	/**
	 * Defines number of screen refreshes between aliens moves.
	 */
	public static final int ALIENS_MOVE_FREQ = 8;
	
	/**
	 * Defines number of screen refreshes between shot moves.
	 */
	public static final int SHOT_MOVE_FREQ = 2;
	
	/**
	 * Players reload time after shot (in milliseconds).
	 */
	public static final int SHOT_RELOAD = 1000;
	
	/**
	 *  Minimum alien reload time after shot (in milliseconds). 
	 */
	public static final int MIN_ALIEN_RELOAD = 5000;
	
	/**
	 * Maximum alien reload time after shot (in milliseconds). 
	 */
	public static final int MAX_ALIEN_RELOAD = 20000;
	
	/**
	 * Game freeze time after player is hit (in milliseconds).
	 */
	public static final int PAUSE_AFTER_HIT = 500;
}
