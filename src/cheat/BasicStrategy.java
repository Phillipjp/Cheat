package cheat;


import cards.Card.*;
import cards.*;
import java.util.*;

/**
 * Basic Strategy.
 * A strategy for playing the card game cheat. Only cheats if it's not possible
 * to play an honest card and if it does cheat plays a single card picked at
 * random. If there's no need to cheat plays all the cards matching the bid rank
 * in the hand. If no cards match the bid rank plays all the cards with the 
 * rank that matches the rank after the current bid rank. Only calls cheat if 
 * certain the other player is cheating.
 * @author Phillip
 */
public class BasicStrategy implements Strategy{
    
    @Override
    public boolean cheat(Bid b, Hand h){
        //get the rank of the current bid
        Rank rank = b.r;
        //loop through all the cards in the hand
        for(Card c: h){
            //if the hand contains a card with the same rank or the rank after
            //the bid rank return false as no need to cheat
            if(c.getRank() == rank || c.getRank() == rank.getNext())
                return false;
        }
        //return true as there's a need to cheat
        return true;
    }
    
    @Override
    public Cheat cheat(Hand h, Bid b){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        Random rn = new Random();
        //get the rank of current bid
        Rank rank = b.r;
        //initialise the bid rank for this bid
        Rank bidRank;
        //initialise the hand for this bid
        Hand bidHand = new Hand();
        //initialise the card to add to the bid
        Card bidCard;
        
        //if there's a need to cheat
        if(cheat){
            int randomCard = rn.nextInt(h.size());
            int whichRank = rn.nextInt(2);
            //set the card to cheat with as any random card from the hand
            bidCard = h.getCard(randomCard);
            //randomly choose the rank of the cheat card
            if(whichRank == 0){
                bidRank = rank;
            }
            else{
                bidRank = rank.getNext();
            }
            //add the card to the bidHand
            bidHand.add(bidCard);
        }
        //if no need to cheat
        else{
              //set this bid rank to the rank of the current bid
              bidRank = rank;
              bidHand.add(h.getAllCardsByRank(bidRank));
              //if no cards have been added to the bid hand
              if(bidHand.size()==0){
                //set this bid rank to the rank of the current bid
                bidRank = rank.getNext();
                bidHand.add(h.getAllCardsByRank(bidRank));
              }
              
            }
        //remove the cards in the bidHand from the players hand
        h.remove(bidHand);
        Bid thisBid = new Bid(bidHand, bidRank);
        
        return thisBid;
    }
    
    @Override
    public Bid chooseBid(Bid b, Hand h, Cheat cheat){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b){
        int sizeOfBid = b.h.size();
        Rank bidRank = b.r;
        //the number of cards in the players hand that rank matches the bid rank
        int matched = h.countRank(bidRank);
        //return true if the bid is impossible
        return (matched + sizeOfBid) > 4; 
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b, double p){
        throw new UnsupportedOperationException();
    }
}
