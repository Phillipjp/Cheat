/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheat;

/**
 * StrategyFactory.
 * A class that contains a single method that chooses a strategy type to return.
 *
 * @author phillipperks
 */
public class StrategyFactory {
    
    public enum Strat{BASIC, HUMAN, THINKER, MY};
    
    /**
     * A method that returns a strategy for a player to use
     * @param s A Strat that refers to a strategy to return
     * @return A strategy type
     * @throws IllegalStrategyChoice if the parameter does not correspond to a
     * strategy
     */
    public static Strategy chooseStrategy(Strat s) throws IllegalStrategyChoice{
        if(s==Strat.BASIC){
            return new BasicStrategy();
        }
        else if(s==Strat.HUMAN){
            return new Human();
        }
        else if(s==Strat.THINKER){
            return new ThinkerStrategy();
        }
        else if(s==Strat.MY){
            return new MyStrategy();
        }
        else{
            throw new IllegalStrategyChoice();
        }
            
    }
            
    
}
