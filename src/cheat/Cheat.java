/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheat;

/**
 *
 * @author phillipperks
 */
public class Cheat {
    
    private boolean cheat;
    private Type type;
    
    /**
     * enums for the type of cheat a player can make.
     * STANDARD: Pick a single card at random that isn't honest.
     * CHEAT_AND_HONEST:Pick an honest card and a random card not of the same 
     * rank.
     * MIMC:Pick random cheat cards that 'mimic' honest cards in the players
     * hand.
     * NO_CHEAT:Only pick honest cards
     */
    public enum Type {
        STANDARD, CHEAT_AND_HONEST, MIMIC, NO_CHEAT};
    
    /**
     * Creates a new instance of Cheat
     * 
     * @param cheat true or false
     * @param type  they type of cheat
     * @throws IllegalCheatCombinationException
     */
    public Cheat (boolean cheat, Type type) 
            throws IllegalCheatCombinationException{
        if(cheat && type == Cheat.Type.NO_CHEAT){
            throw new IllegalCheatCombinationException("It is not possible for "
                    + "cheat equal true and cheat type to equal NO_CHEAT");
        }
        else if(!cheat && (type == Cheat.Type.STANDARD || 
                type == Cheat.Type.CHEAT_AND_HONEST || 
                type == Cheat.Type.MIMIC)){
            throw new IllegalCheatCombinationException("It is not possible for "
                    + "cheat equal false and cheat type to equal STANDARD"
                    + ", CHEAT_AND_HONEST or MIMIC");
        }
        else{
            this.cheat = cheat;
            this.type = type;
        }
    }
    
    /**
     * gets the boolean cheat value
     * @return cheat    true if the player cheats false if not
     */
    boolean getCheat(){
        return cheat;
    }
    
    /**
     * gets the type of cheat the player is making
     * @return type     the type of cheat the player is making
     */
    Type getType(){
        return type;
    }
    
    @Override
    public String toString(){
        StringBuilder printCheat = new StringBuilder();
        printCheat.append(cheat).append(", ").append(type);
        return printCheat.toString();
        //return rank + " of " + suit;
    }
    
}
