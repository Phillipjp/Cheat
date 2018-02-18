package cheat;

import cards.*;

/*
 * You should implement a new class BasicStrategy that implements the Strategy interface provided.  Basic strategy should
1.	Never cheat
2.	Always play all the cards of the lowest value possible 
*/

public interface Strategy {
 
/**
 * Decides on whether to cheat or not	
 * @param b   the bid this player has to follow (i.e the 
 * bid prior to this players turn. 
 * @param h	  The players current hand	
 * @return true if the player will cheat, false if not
 */
	public boolean cheat(Bid b, Hand h);
        
/**
 * Decides on whether to cheat or not	
 * @param h   The players current hand
 * @param b   The bid this player has to follow (i.e the 
 * bid prior to this players turn.	
 * @return    cheat, true if the player will cheat, false if not and what type
 * of cheat the player is making.
 */
        public Cheat cheat(Hand h, Bid b);
/**
 * 	
 * @param b   the bid the player has to follow. 
 * @param h	  The players current hand	
 * @param cheat true if the Strategy has decided to cheat (by call to cheat()) 
 * 
 * @return a Bid with the cards to pass to the game and the Rank. This will be 
 * different to the rank of thecards if the player is cheating!
 * 
 */
	public Bid chooseBid(Bid b, Hand h, boolean cheat);
 /**
 * 	
 * @param b   The bid the player has to follow. 
 * @param h   The players current hand	
 * @param cheat true if the Strategy has decided to cheat and what type of cheat
 * (by call to cheat()) 
 * 
 * @return a Bid with the cards to pass to the game and the Rank. This will be 
 * different to the rank of the cards if the player is cheating!
 * 
 */  
        public Bid chooseBid(Bid b, Hand h, Cheat cheat);	
/**
 * @param h     the players current hand
 * @param b     the current bid
 * @return true if this player is going to call cheat on the last play
 */
	public boolean callCheat(Hand h,Bid b);
/**
 * @param h     the players current hand
 * @param b     the current bid
 * @param p     the probability if the player calling cheat
 * @return true if this player is going to call cheat on the last play
 */      
        public boolean callCheat(Hand h,Bid b, double p);

}
