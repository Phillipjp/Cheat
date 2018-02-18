package cheat;

import java.util.*;
import cards.*;
import cheat.StrategyFactory.Strat;

/**
 * Contains the methods to play a game of cheat.
 * I have change some rules from the original implementation. After cheat is
 * called the bid rank is no longer set back to TWO it is a randomly chosen 
 * rank. For the first turn the rank the player plays does not have to match the
 * current bid rank, it can be one above as well. The reason for these changes 
 * are to stop the game from entering an infinite loop of calling cheat for the
 * more advanced strategies. I have also changed how the order for calling cheat
 * is decided. It now asks the player directly after the current player then the
 * next player and so on until all the players have been asked apart from the
 * current player. This was to make the game more fair and not give any player
 * an advantage because of what position they're at.
 * @author phillipperks
 */
public class BasicCheat implements CardGame{
    private Player[] players;
    private int nosPlayers;
    public static final int MINPLAYERS=5;
    private int currentPlayer;
    private Hand discards;
    private Bid currentBid;
    

    public BasicCheat() throws IllegalStrategyChoice{
        this(MINPLAYERS);
    }
    public BasicCheat(int n) throws IllegalStrategyChoice{
        nosPlayers=n;
        players=new Player[nosPlayers];
        players[0]=(new BasicPlayer(new Human(),this));
//        players[0]=(new BasicPlayer(null,this));
//        players[0].setStrategy(Strat.HUMAN);
//        players[1]=(new BasicPlayer(null,this));
//        players[1].setStrategy(Strat.MY);
//        players[2]=(new BasicPlayer(null,this));
//        players[2].setStrategy(Strat.THINKER);
//        players[3]=(new BasicPlayer(null,this));
//        players[3].setStrategy(Strat.BASIC);
//        players[4]=(new BasicPlayer(null,this));
//        players[4].setStrategy(Strat.THINKER);
        for(int i=1;i<nosPlayers;i++)
                players[i]=(new BasicPlayer(new BasicStrategy(),this));
        currentBid=new Bid();
        currentBid.setRank(Card.Rank.TWO);
        currentPlayer=0;
    }

    @Override
    public boolean playTurn(){
//        lastBid=currentBid;
        //Ask player for a play,
        System.out.println("current bid = "+currentBid);
        currentBid=players[currentPlayer].playHand(currentBid);
        //if the players' strategy is MyStrategy increase the discarded rank
        //count and set the size for the current players hand for calling cheat 
        for(int j=0; j<nosPlayers; j++){
            if(players[j].getStrategy() instanceof MyStrategy){
                MyStrategy mp = (MyStrategy)players[j].getStrategy();
                if((players[j].equals(players[currentPlayer]) && 
                        !isCheat(currentBid)) || 
                        !players[j].equals(players[currentPlayer])){
                    mp.getDiscardedRankCount()[currentBid.getRank().ordinal()]+= 
                            currentBid.h.size();
                }
                mp.setbidPlayersHandSize(players[currentPlayer].getHandSize());
            }
        }
        
        System.out.println("Player bid = "+currentBid);
        
         //Add hand played to discard pile
        discards.add(currentBid.getHand());
        //Offer all other players the chance to call cheat
        boolean cheat=false;
        boolean allPlayers = false;
        //ask all the players if they want to call cheat starting from the
        //player after the current player
        for(int i=currentPlayer+1;!allPlayers && !cheat;i++){
            //if i is bigger than the number of players set it to equal 0
            if(i==nosPlayers){
                i=0;
            }
            System.out.println("PLayer checking for call cheat: " + (i+1));
            //if i isnt't the current player check to call cheat
            if(i!=currentPlayer){
                cheat=players[i].callCheat(currentBid);
                if(cheat){
                    //clear discarded hand for players using the ThinkerStrategy
                    // and MyStrategy 
                    for(int j=0; j<nosPlayers; j++){
                        if(players[j].getStrategy() instanceof ThinkerStrategy){
                            ThinkerStrategy tp = (ThinkerStrategy)players[j].getStrategy();
                            tp.getPersonalDiscardedCards().clearHand();
                        }
                        if(players[j].getStrategy() instanceof MyStrategy){
                            MyStrategy mp = (MyStrategy)players[j].getStrategy();
                            mp.getPersonalDiscardedCards().clearHand();
                            mp.resetNumberOfDiscardedRank();
                        }
                    }
                    System.out.println("Player called cheat by Player "+(i+1));
                    if(isCheat(currentBid)){	
//CHEAT CALLED CORRECTLY
//Give the discard pile of cards to currentPlayer who then has to play again                      
                        players[currentPlayer].addHand(discards);
                        System.out.println("Player cheats!");
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1)/*+players[currentPlayer]*/);

                    }
                    else{
//CHEAT CALLED INCORRECTLY
//Give cards to caller i who is new currentPlayer
                        System.out.println("Player Honest");
                        currentPlayer=i;
                        players[currentPlayer].addHand(discards);
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1)/*+players[currentPlayer]*/);
                    }
//If cheat is called, current bid reset to an empty bid with rank two whatever 
//the outcome
                    currentBid=new Bid();
//Discards now reset to empty	
                    discards=new Hand();
                }
            }
            //else i is the current player so all players have checked if they
            //want to call cheat so exit the for loop
            else{
                allPlayers = true;
            }
        }
        if(!cheat){
//Go to the next player       
            System.out.println("No Cheat Called");

            currentPlayer=(currentPlayer+1)%nosPlayers;
        }
        
        for(int j=0; j<nosPlayers; j++){
            if(players[j].getStrategy() instanceof MyStrategy){
                MyStrategy mp = (MyStrategy)players[j].getStrategy();
                mp.setbidPlayersHandSize(0);
            }
        }
        return true;
    }
    
    @Override
    public int winner(){
            for(int i=0;i<nosPlayers;i++){
                    if(players[i].cardsLeft()==0)
                            return i;
            }
            return -1;

    }
    @Override
    public void initialise(){
            //Create Deck of cards
            Deck d=new Deck();
            d.shuffle();
            //Deal cards to players
            //System.out.println(d.size());
            Iterator<Card> it=d.iterator();
            int count=0;
            while(it.hasNext()){
                Card c = d.deal();
                players[count%nosPlayers].addCard(c);
                //it.remove();
                //System.out.println(c);
                count++;
            }
            //System.out.println(count);
            //Initialise Discards
            discards=new Hand();
            //Chose first player
            currentPlayer=0;
            currentBid=new Bid();
            currentBid.setRank(Card.Rank.TWO);
    }
    public void playGame(){
            initialise(); 
            int c=0;
            Scanner in = new Scanner(System.in);
            boolean finished=false;
            while(!finished){
                    //Play a hand
                    System.out.println(" Cheat turn for player "+(currentPlayer+1));
                    players[currentPlayer].printHand();
                    playTurn();
                    System.out.println(" Current discards =\n"+discards);
                    c++;
                    System.out.println(" Turn "+c+ " Complete. Press any key to continue or enter Q to quit>");
                    String str=in.nextLine();
                    if(str.equals("Q")||str.equals("q")||str.equals("quit"))
                            finished=true;
                    int w=winner();
                    if(w>=0){
                            System.out.println("The Winner is Player "+(w+1));
                            finished=true;
                    }

            }
    }
    public static boolean isCheat(Bid b){
            for(Card c:b.getHand()){
                    if(c.getRank()!=b.r)
                            return true;
            }
            return false;
    }
    public static void main(String[] args) throws IllegalStrategyChoice{
            BasicCheat cheat=new BasicCheat(5);
            cheat.playGame();
    }
}

