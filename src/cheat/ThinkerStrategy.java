package cheat;

import cards.Card;
import cards.Card.*;
import cards.Hand;
import java.util.*;

/**
 * Thinker Strategy.
 * A strategy for playing the card game cheat. Cheats if there's no honest card
 * available or sometimes randomly cheats even if there's no need to. If
 * the strategy cheats it's more likely to play a a high ranked card if it can.
 * If the strategy doesn't cheat it plays the most possible honest cards it can,
 * but sometimes it does keep some cards back. The strategy calls keeps a note
 * of cards it has discarded and calls cheat when certain the other player is 
 * cheating and also tries to make an informed decision to call cheat based on
 * a probability and how likely the bid is.
 * @author Phillip
 */
public class ThinkerStrategy implements Strategy{
    
    private Hand personalDiscardedCards = new Hand();
    
    public Hand getPersonalDiscardedCards(){
        return personalDiscardedCards;
    }
    
    
    
    @Override
    public boolean cheat(Bid b, Hand h){
        Random rn = new Random();
        //create a random number between 0 and 4
        int number = rn.nextInt(5);
        System.out.println("CHEAT NUMBER: " + number);
        //get the rank of the current bid
        Card.Rank rank = b.r;
        Hand hand = b.h;
        //if the random number equals 0 cheat (1 in 5 chance of cheating)
        if(number == 0){
            int i=0;
            for(Card c:h){
                if(c.getRank() == rank || c.getRank()== rank.getNext())
                    i++;
            }
            return i != h.size();
        }
        else{
            //loop through all the cards in the hand
            for(Card c: h){
                //if the hand contains a card with the same rank or the next 
                //rank of the bid rank return false as no need to cheat
                if(c.getRank() == rank || c.getRank() == rank.getNext())
                    return false;
            }
        }
        //return true as there's a need to cheat
        return true;
    }
    
    @Override
    public Cheat cheat(Hand h, Bid b){
        throw new UnsupportedOperationException();
    }
    
    /**
     * viableCheat.
     * Checks that a card is a cheating card and if it is adds it to the bid
     * hand and returns true
     * @param bidCard
     * @param previousBidRank
     * @param bidHand
     * @return true if a cheating card is set
     */
    private boolean viableCheat(Card bidCard, Card.Rank previousBidRank, 
            Hand bidHand){
        if(bidCard.getRank()!=previousBidRank && 
            bidCard.getRank()!=previousBidRank.getNext()){
                bidHand.add(bidCard);
                 System.out.println("cheat set");
                 return true;
        }
        return false;
    }
    
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        //get the rank of current bid
        Card.Rank previousBidRank = b.r;
        //initialise the bid rank for this bid
        Card.Rank bidRank = null;
        //initialise the hand for this bid
        Hand bidHand = new Hand();
        Card bidCard;
        Random rn = new Random();
        int number = rn.nextInt(5);
        System.out.println("NUMBER: " + number);
        boolean cheatCardSet = false;
        final int LOWEST_RANK_VALUE = Rank.SEVEN.getValue();
        System.out.println("CHEAT: " + cheat);
        boolean availableCard = false;
        if(cheat){
            //go through all the cards in the players hand
                for(Card c: h){
                    //check if the hand contains a card lower than the lowest
                    //rank value and it's not an honest card
                    if(c.getRank().getValue()<LOWEST_RANK_VALUE){
                        if(c.getRank() != previousBidRank && 
                                c.getRank() != previousBidRank.getNext())
                            availableCard = true;
                    }
                }
                //if number is 0 then set the cheating card to have a value less
                //than the LOWEST_RANK_VALUE
                if(number == 0 && availableCard){
                    while(!cheatCardSet){
                        int randomCard = rn.nextInt(h.size());
                        bidCard = h.getCard(randomCard);
                        //if the bid card is a cheating card add it to 
                        //the bid hand
                        if(bidCard.getRank().getValue() < LOWEST_RANK_VALUE){
                            if(bidCard.getRank() != previousBidRank && 
                                  bidCard.getRank()!=previousBidRank.getNext()){
                                bidHand.add(bidCard);
                                cheatCardSet = true;
                            }
                        } 
                    }  
                }
            
            //else number is above 0 or there is no card available below 7 to
            //cheat with set cheat card to have a value 7 or greater
            else{
                availableCard = false;
                //if number is greater than 0 set the cheating card to have a 
                //value greater than 7
                //go through all the cards in the players hand
                for(Card c: h){
                    //check if the hand contains a card above the lowest
                    //rank value and it's not a non cheating card
                    if(c.getRank().getValue()>=LOWEST_RANK_VALUE){
                        if(c.getRank() != previousBidRank && 
                                c.getRank() != previousBidRank.getNext())
                            availableCard = true;
                    }
                }
                if(availableCard){
                    while(!cheatCardSet){
                        int randomCard = rn.nextInt(h.size());
                        bidCard = h.getCard(randomCard);
                        //check the bid card value is above 7
                        if(bidCard.getRank().getValue() >= LOWEST_RANK_VALUE){
                            //if the bid card is a cheating card add it to 
                            //the bid hand and set cheatCardSet to true
                            cheatCardSet = viableCheat(bidCard, previousBidRank,
                                    bidHand);
                        }
                    }
                    
                }
                else{
                    while(!cheatCardSet){
                        int randomCard = rn.nextInt(h.size());
                        bidCard = h.getCard(randomCard);
                        //if the card is a cheating card add it to the bid
                        //hand and set cheatCardSet to true
                        cheatCardSet = viableCheat(bidCard, previousBidRank,
                                                            bidHand);
                    } 
                }  
            }  
            int whichRank = rn.nextInt(2);
            //if whichRank is 0 or this is the first card to be played
            //set the rank to cheat with to be the current bid rank
            if(whichRank == 0){
                bidRank = previousBidRank;
            }
            //else set the bid rank to be the rank after the current bid 
            //rank
            else{
                bidRank = previousBidRank.getNext();
            }
        }
        else{
            //get the number of cards in the players hand that macth the
            //previous bid rank and the rank afer it
            int thisRankCount = h.countRank(previousBidRank);
            int nextRankCount = h.countRank(previousBidRank.getNext());
             //if number is less than 2 play a random amount of cards
            if(number < 2){
                //randomly set how many cards to play between 1 and how many
                //of the card the player has in their hand
                int cardsToPlay = rn.nextInt(thisRankCount)+1;
                System.out.println("CARDS TO PLAY: " + cardsToPlay);
                int i=0;
                //play the most possible cards
                if(thisRankCount>=nextRankCount){
                    //set the bid rank to the current rank
                    bidRank = previousBidRank;
                    //go through all the cards in the players hand    
                    for(int j=0; j<h.size(); j++){  
                        bidCard = h.getCard(j);
                        //if the rank equals the rank of the current bid and
                        //not all the random amount of cards to be played have
                        //been played add the card to the bid hand
                        if(bidCard.getRank()==previousBidRank && i<cardsToPlay){
                            bidHand.add(bidCard);
                            i++;
                        }
                    }
                }
                else{
                    bidRank = previousBidRank.getNext();
                    //go through all the cards in the players hand
                    for(int j=0; j<h.size(); j++){
                        bidCard = h.getCard(j);
                        //if the rank equals the rank after the current bid and
                        //not all the random amount of cards to be played have
                        //been played add the card to the bid hand
                        if(bidCard.getRank() == previousBidRank.getNext() && 
                                            i<cardsToPlay){
                            bidHand.add(bidCard);
                            i++;
                        }
                    }
                }
            }
            else{
                //play the most amount of cards
                if(thisRankCount>=nextRankCount){
                    //set this bid rank to the rank of the current bid
                    bidRank = previousBidRank;
                    //add all the cards with the bid rank in the players hand
                    //to the bid hand
                    bidHand.add(h.getAllCardsByRank(bidRank));
                }
                else{
                    //set the bid rank to rank after the current bid rank
                    bidRank = previousBidRank.getNext();
                    //add all the cards with the bid rank in the players hand
                    //to the bid hand
                    bidHand.add(h.getAllCardsByRank(bidRank));
                    
                }
            }
        }
        //remove all the cards in the bid hand from the players hand
        h.remove(bidHand);
        //add the bid hand to the players personal discards hand
        personalDiscardedCards.add(bidHand);
        Bid thisBid = new Bid(bidHand, bidRank);
        return thisBid;
    }
    
    @Override
    public Bid chooseBid(Bid b, Hand h, Cheat cheat){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean callCheat(Hand h, Bid b, double p){
        int sizeOfBid = b.h.size();
        Rank bidRank = b.r;
        Random rn = new Random();
        //generate a random number between 0 and 100
        int number = rn.nextInt(101);
        //multiply the probaility by 100
        p*=100;
        //how many cards in the players hand match the bid rank
        int hr = h.countRank(bidRank);
        //how many cards in the players discard hand that match the bid rank
        int dr = personalDiscardedCards.countRank(bidRank);
        //return true as the bid is impossible
        if((hr + dr + sizeOfBid) > 4 || (hr + dr ) == 4){
            return true;
        }
        //return false as the player has no information about the current bid
        else if((hr + dr) == 0){
            return false;        
         }
        //else depending on how much the player knows increase the probabilty of
        //calling cheat
        else if((hr + dr) == 1){
            return (p > number);        
        }
        else if((hr + dr) == 2){
            p*=2.5;
            return (p > number);       
        }
        else{
            p*=5;
            return (p > number);        
        }
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b){
        throw new UnsupportedOperationException();
    }
    
}
