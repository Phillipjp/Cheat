package cheat;
import cards.*;
import cheat.StrategyFactory.Strat;


public interface Player {
// enum Action{PASS,PLAY}
/**add to the players hand
 * @param c: Card to add
 */	
	void addCard(Card c);
/**
 * 	add all the cards in h to the players hand
 * @param h: hand to add
 */
	void addHand(Hand h);
/**
 * @return number of cards left in the players hand
 */
	int cardsLeft();
/**
 * @param g: the player should contain a reference to the game it is playing in
 */
	void setGame(CardGame g);
/**	
 * Sets a strategy for a player using the chooseStrategy method in 
 * StrategyFactory
 * @param s: An Enum Strat that refers to a strategy type
 * @throws cheat.IllegalStrategyChoice if the strategy choice doesn't exist and 
 * sets the strategy to one applicable for the player
 */
	void setStrategy(Strat s)throws IllegalStrategyChoice;


/**
 * Constructs a bid when asked to by the game. 	
 * @param b: the last bid accepted by the game. .
 * @return the players bid
 */
	Bid playHand(Bid b);
/**
 * 
 * @param b: the last players bid
 *
 * @return true if calling the last player a cheat.
 */
	boolean callCheat(Bid b);
        
/**
*prints out the players hand in descending order
*/
void printHand();
        
/**
*
* @return the type of strategy the player is using
*/
public Strategy getStrategy();
    
/**
*
* @return the current hand size of the player
*/
public int getHandSize();
	
}
