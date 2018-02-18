package cheat;

import cards.*;
import cheat.StrategyFactory.Strat;

public class BasicPlayer implements Player{
    
    public Strategy strategy;
    public Hand playerHand;
    public CardGame cardGame;
    
    public BasicPlayer(Strategy strategy, CardGame cardGame){
        playerHand = new Hand();
        this.strategy = strategy;
        this.cardGame = cardGame;
    }
    
    @Override
    public void addCard(Card c){
        playerHand.add(c);
    }
    
    @Override
    public void addHand(Hand h){
        playerHand.add(h);
    }
    
    @Override
    public int cardsLeft(){
        return playerHand.size();
    }
    
    @Override
    public void setGame(CardGame g){
        cardGame = g;
    }
    
    @Override
    public void setStrategy(Strat s) throws IllegalStrategyChoice{
        try{
        strategy = StrategyFactory.chooseStrategy(s);
        }
        catch(IllegalStrategyChoice e){System.out.println(e + 
                "\nInvalid strategy for BasicPlayer set. Setting the strategy "
                        + "to BasicStrategy");
            strategy = StrategyFactory.chooseStrategy(Strat.BASIC);};
    }
    
    @Override
    public Bid playHand(Bid b){
        if(strategy instanceof MyStrategy){
            Cheat cheat = strategy.cheat(playerHand, b);
            Bid playerBid = strategy.chooseBid(b, playerHand, cheat);
            return playerBid;
        }
        else{
            boolean cheat = strategy.cheat(b, playerHand);
            Bid playerBid = strategy.chooseBid(b, playerHand, cheat);
            return playerBid;
        }
    }
    
    @Override
    public boolean callCheat(Bid b){
        if(strategy instanceof BasicStrategy || strategy instanceof Human){
        return strategy.callCheat(playerHand, b);
        }
        else{
            return strategy.callCheat(playerHand, b, 0.1);
        }
    }
    
    @Override
    public void printHand(){
        System.out.print("_________________"
                + "________________________\n");
        playerHand.sortDescending();
        System.out.println(playerHand);
        System.out.print("_________________"
                + "________________________\n");
    }
    
    @Override
    public Strategy getStrategy(){
        return strategy; 
    }
    
    @Override
    public int getHandSize(){
        return playerHand.size();
    }
}
