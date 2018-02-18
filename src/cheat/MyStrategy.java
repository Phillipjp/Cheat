package cheat;

import cards.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author phillipperks
 */
public class MyStrategy implements Strategy{
    
    private Hand personalDiscardedCards = new Hand();
    private int numberOfDiscardedRank [] = new int [13];
    private int bidPlayersHandSize = 0;
    
    /**
     *
     * @return a hand of the cards the player has discarded
     */
    public Hand getPersonalDiscardedCards(){
        return personalDiscardedCards;
    }
    
    /**
     *
     * @return an array of the number of cards of a rank all players have 
     * supposedly discarded
     */
    public int[] getDiscardedRankCount(){
        return numberOfDiscardedRank;
    }
    
    /**
     *sets the count for the number of cards of a rank all players have 
     * supposedly discarded to zero
     */
    public void resetNumberOfDiscardedRank(){
        for(int i=0; i<numberOfDiscardedRank.length; i++){
            numberOfDiscardedRank[i]=0;
        }
    }
    
    /**
     *sets bidPlayersHandSize to equal the size of the bidding players current 
     * hand size
     * @param i the size of the bidding players hand size
     */
    public void setbidPlayersHandSize(int i){
        bidPlayersHandSize = i;
    }
    
    @Override
    public boolean cheat(Bid b, Hand h){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Cheat cheat(Hand h, Bid b){
        try {
            //the type of cheat the player will make
            Cheat.Type type;
            //boolean if the player will cheat or not
            boolean cheat;
            //the prevoious bid rank
            Card.Rank previousBidRank = b.r;
            Random rn = new Random();
            //create a random number between 0 and 4
            int number = rn.nextInt(4);
            //number of cards with the same rank as the previous bid rank
            int numberOfRank = h.countRank(previousBidRank);
            //number of cards with the same rank as the rank after the
            //previous bid rank
            int numberOfNextRank = h.countRank(previousBidRank.getNext());
            //if the player doesnt have any honest cards make a standard cheat
            if(numberOfRank == 0 && numberOfNextRank == 0){
                cheat = true;
                type = Cheat.Type.STANDARD;
            }
            //else 2/5 chance of performing a cheat if possible
            else if(number < 2){
                //boolean to check it is possible to perform a MIMIC cheat of 
                //the current bid rank
                boolean thisRankMimic = ((h.size()-numberOfRank)
                        -numberOfNextRank)>=numberOfRank;
                //boolean to check it is possible to perform a MIMIC cheat of 
                //the bid rank after the current one
                boolean nextRankMimic = ((h.size()-numberOfNextRank)
                        -numberOfRank)>=numberOfNextRank;
                //If possible to perfrom a MIMC cheat
                if((numberOfRank > 1 && thisRankMimic) ||
                        (numberOfNextRank > 1 && nextRankMimic)){
                    cheat = true;
                    type = Cheat.Type.MIMIC;
                }
                //else if possible to perform a CHEAT_AND_HONEST cheat
                else if((numberOfRank == 1 || numberOfNextRank == 1)
                        && h.size() > 1){
                    cheat = true;
                    type = Cheat.Type.CHEAT_AND_HONEST;
                }
                //else perform a STANDARD cheat if possible
                else{
                    if(h.size() == (numberOfRank+numberOfNextRank)){
                        cheat = false;
                        type = Cheat.Type.NO_CHEAT;
                    }
                    else{
                        cheat = true;
                        type = Cheat.Type.STANDARD;
                    }
                }
            }
            //else dont cheat at all
            else{
                cheat = false;
                type = Cheat.Type.NO_CHEAT;
            }
            Cheat toCheat = new Cheat(cheat, type);
            return toCheat;
        } catch (IllegalCheatCombinationException ex) {
            Logger.getLogger(MyStrategy.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        //return null as the cheat combination was impossible
        return null;
        
    }
    
    @Override
    public Bid chooseBid(Bid b ,Hand h, boolean cheat){
        throw new UnsupportedOperationException();
    }
    
   
    @Override
    public Bid chooseBid(Bid b, Hand h, Cheat cheat){
        Hand bidHand = new Hand();
        Card.Rank bidRank = null;
        Card bidCard;
        //get the rank of the prevoius bid
        Card.Rank previousBidRank = b.r;
        //get the hand of the prevoius bid
        Hand previousBidHand = b.h;
        
        Random rn = new Random();
        //number of cards matching the prevoius bid rank in the players hand
        int numberOfRank = h.countRank(previousBidRank);
        //number of cards matching the rank after the previous bid rank in
        //the players hand
        int numberOfNextRank = h.countRank(previousBidRank.getNext());
        
        boolean bidRankSet = false;
        
        System.out.println(cheat);
        if(cheat.getCheat()){
            boolean set = false;
            //play a single cheating card
            if(cheat.getType()== Cheat.Type.STANDARD){
                System.out.println(cheat.getType());
                while(!set){
                    int randomCard = rn.nextInt(h.size());
                    //set bidCard to be a random card from the hand
                    bidCard = h.getCard(randomCard);
                    //check the bidCard isn't honet
                    if(bidCard.getRank() != previousBidRank && 
                            bidCard.getRank() != previousBidRank.getNext()){
                        //add the bidCard to the bidHand
                        bidHand.add(bidCard);
                        set = true;
                    }
                }
                //randomly choose what rank to bid
                int whichRank = rn.nextInt(2);
                //if which rank equals 1 or the size of the previous bid rank is
                //greater than 2 set the bid rnk to equal the rank after the 
                //prevoius bid rank
                if(whichRank==1||numberOfDiscardedRank[previousBidRank.ordinal()]>2){
                    bidRank = previousBidRank.getNext();
                }
                //else set the nbid rank to the previous bid rank
                else{
                    bidRank = previousBidRank;
                }
            }
            //play an honest and a cheating card
            else if(cheat.getType()==Cheat.Type.CHEAT_AND_HONEST){
                System.out.println(cheat.getType());
                if(numberOfRank == 1){
                    //get an honest card
                    bidHand = h.getAllCardsByRank(previousBidRank);
                    //set the bid rank to equal the rank of the honest card
                    bidRank = previousBidRank;
                    bidRankSet = true;
                }
                else{
                    //get an honest card
                    bidHand = h.getAllCardsByRank(previousBidRank.getNext());
                    //set the bid rank to equal the rank of the honest card
                    bidRank = previousBidRank.getNext();
                    bidRankSet = true;
                }
                while(!set){
                    int randomCard = rn.nextInt(h.size());
                    //set bidCard to be a random card from the hand
                    bidCard = h.getCard(randomCard);
                    //check the bidCard isn't honet
                    if(bidCard.getRank() != bidRank){
                        //add the bidCard to the bidHand
                        bidHand.add(bidCard);
                        set = true;
                    }
                }
                
            }
           //play the same number of cheating cards as honest card for that rank
            else{
                System.out.println(cheat.getType());
                boolean thisRankMimic=((h.size()-numberOfRank)-numberOfNextRank)
                        >=numberOfRank;
                //if it's possible to mimic the previous bid rank
                if(thisRankMimic && numberOfRank > 1){
                    int i=0;
                    //set bid rank to equal the rank of the cards being mimic'd
                    bidRank = previousBidRank;
                    bidRankSet = true;
                    while(i < numberOfRank){
                        int randomCard = rn.nextInt(h.size());
                        //set bidCard to be a random card from the hand
                        bidCard = h.getCard(randomCard);
                        //check the bidCard isn't honet
                        if(bidCard.getRank() != previousBidRank && 
                                bidCard.getRank() != previousBidRank.getNext()){
                            //add the bidCard to the bidHand
                            bidHand.add(bidCard);
                            //remove the bidCard from the players hand
                            h.remove(bidCard);
                            i++;
                        }
                    
                    }
                }
                //else mimic the rank after the previous bid rank
                else{
                    int i=0;
                    //set bid rank to equal the rank of the cards being mimic'd
                    bidRank = previousBidRank.getNext();
                    bidRankSet = true;
                    while(i < numberOfNextRank){
                        int randomCard = rn.nextInt(h.size());
                        //set bidCard to be a random card from the hand
                        bidCard = h.getCard(randomCard);
                        //check the bidCard isn't honet
                        if(bidCard.getRank() != previousBidRank && 
                                bidCard.getRank() != previousBidRank.getNext()){
                            //add the bidCard to the bidHand
                            bidHand.add(bidCard);
                            //remove the bidCard from the players hand
                            h.remove(bidCard);
                            i++;
                        }
                    
                    }
                    
                }
            }
        }
        //else don't cheat
        else{
            System.out.println(cheat.getType());
            //play the most cards possible
            if(numberOfRank>=numberOfNextRank){
                //set the bid rank to equal the previous bid rank
                bidRank = previousBidRank;
                //add all the cards with the bid rank in the players hand
                //to the bid hand
                bidHand.add(h.getAllCardsByRank(bidRank));

            }
            else{
                //set the bid rank to equal the rank after the previous bid rank
                bidRank = previousBidRank.getNext();
                //add all the cards with the bid rank in the players hand
                //to the bid hand
                bidHand.add(h.getAllCardsByRank(bidRank));

            }  
        }
        //remove all the cards in the bid hand form the players hand
        h.remove(bidHand);
        //add all the cards in the bid hand to the players personal discarded 
        //cards hand
        personalDiscardedCards.add(bidHand);
        //create a new bid
        Bid thisBid = new Bid(bidHand, bidRank);
        return thisBid;
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b, double p){
        boolean cheat = true;
        Card.Rank bidRank = b.r;
        int sizeOfBid = b.h.size();
        Random rn = new Random();
        int number = rn.nextInt(100);
        //increase the probibilty of of calling cheat according to how many of 
        //the current bid rank has already been played supposedly 
        p+=(numberOfDiscardedRank[bidRank.ordinal()]/20);
        //multiplay the probibility by 100
        p*=100;
        System.out.println("NUMBER: " + number);
        //how many of the current bid rank are in the players hand
        int hr = h.countRank(bidRank);
        //how many of the current bid rank the player has played
        int dr = personalDiscardedCards.countRank(bidRank);
        //if the player is playing their last cards or it's known the bid is
        //impossible call cheat
        if(bidPlayersHandSize == 0 || (hr + dr + sizeOfBid) > 4 || 
                (hr + dr) == 4){
            return cheat;
        }
        else if(numberOfDiscardedRank[bidRank.ordinal()] > 4 || (hr+dr)==3){
            p*=5;
            return(p>number);
        }
        else if((hr + dr) == 2){
            p*=2;
            return(p>number);
        }
        else if((hr + dr) == 1){
            return(p>number);
        }
        else if(numberOfDiscardedRank[bidRank.ordinal()] == 1){
            p*=0.5;
            return(p>number);
        }
        else{
            return !cheat;
        }
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b){
         throw new UnsupportedOperationException();
    }
}
