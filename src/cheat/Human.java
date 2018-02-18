package cheat;

import cards.*;
import java.util.*;

/**
 *
 * @author Phillip
 */
public class Human implements Strategy{
    
    @Override
    public boolean cheat(Bid b, Hand h){
        boolean cheat = false;
        boolean answer = false;
        Scanner scan = new Scanner(System.in);
        String choice;
        System.out.println("The current bid rank is: " + b.r);
        h.sortAscending(h);
        System.out.println("Your hand is:" + h);
        System.out.println("Do you want to cheat?\n"
                + "Click y for yes or n for no");
        while(!answer){
            try{
                //the user input
                choice = scan.nextLine();
                //make the input to lower case for validation
                choice = choice.toLowerCase();
                //if user wants to cheat set cheat to true
                if(choice.equals("y") || choice.equals("yes")){
                    answer = true;
                    cheat = true;
                }
                //if user doesn't want to cheat set cheat to false
                else if(choice.equals("n") || choice.equals("no")){
                    //loop through all the cards in the hand
                    for(Card c: h){
                    //if the hand contains a card with the same rank or the next 
                    //rank of the bid rank return false as no need to cheat
                        if(c.getRank() == b.r || c.getRank() == b.r.getNext()){
                            answer = true;
                            cheat =  false;
                        }
                    }
                    //there are no honest cards in the players hand so they
                    //have to cheat
                    if(!answer){
                        System.out.println("You have to cheat! You don't have a"
                                + "honnest card.");
                            answer = true;
                            cheat =  true;
                    }
                }
                //if the answer is invalid ask the user to resubmit their answer  
                else{
                    System.out.println("INVALID ANSWER: Please try again");
                }
            }
            catch(Exception e){}
        }
        return cheat;
    }
    
    @Override
    public Cheat cheat(Hand h, Bid b){
        throw new UnsupportedOperationException();
    }
    
    /**
     * Checks the input from the user is within range (between 0 and the size of
     * the players hand), if it's out of range asks the user to keep on trying 
     * until the input is in range.
     * @param intInput the users input
     * @param h the players hand
     * @return intInput that is now in range
     */
    private int checkInputInRange(int intInput, Hand h){
        Scanner scan = new Scanner(System.in);
        String input;
        while(intInput >= h.size() || intInput<0){
            System.out.println("Sorry that number is invalid, "
                + "try again");
            input = scan.next();
            intInput = Integer.parseInt(input);
        }
        return intInput;
    }
    
    /**
     * prints out the cards in the players hand with a number next to it that's
     * used to select that card
     * @param h the players hand
     */
    private void printCardOptions(Hand h){
        System.out.println("Your Hand is:");
        System.out.print("Card \t\t\t Number \n");
        System.out.println("_________________"
             + "________________________\n");
        //int count = 0;
        for (int count = 0; count<h.size();count++){           
            System.out.print(h.getCard(count) + "    \t" + count + "\n");
             //count++;
        }
        System.out.println("Pick a card to play by typing the "
        + "number next to it");
    }
  
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        boolean keepCheating = true;
        boolean answer;
        boolean keepPlaying;
        boolean bidRankSet = false;
        Scanner scan = new Scanner(System.in);
        String input;
        int intInput;
        String stringInput;
        Card.Rank rank = b.r;
        Card bidCard;
        Card.Rank bidRank = null;
        Hand bidHand = new Hand();
        if(cheat){
            do{
                try{
                    printCardOptions(h);
                    //get users input
                    input = scan.next();
                    intInput = Integer.parseInt(input);
                    //validate the users input
                    intInput = checkInputInRange(intInput, h);
                    bidCard = h.getCard(intInput);
                    //add the selected card to the bid hand
                    bidHand.add(bidCard);
                    //remove the slected card from the players hand
                    h.remove(bidCard);
                    System.out.println("Do you want to play another card to "
                            + "cheat with? y or n");
                    stringInput = scan.next();
                    answer = false;
                    while(!answer){
                        stringInput = stringInput.toLowerCase();
                        //add at leat one more card to the bid hand
                        if(stringInput.equals("y") || 
                                stringInput.equals("yes")){
                            //if there are card in the hand to play
                            if(h.size() > 0){
                                keepCheating = true;
                                answer = true;
                            }
                            else{
                                System.out.println("You have no cards left to"
                                        + "play");
                                keepCheating = false;
                                answer = true;
                            }
                        }
                        //don't play another card
                        else if(stringInput.equals("n") || 
                                stringInput.equals("no")){
                            //check the player has actually cheated if not get
                            //them to play another card
                            if(bidHand.containsSingleRank(rank) || 
                                    bidHand.containsSingleRank(rank.getNext())){
                                System.out.println("You have only played honest"
                                        + " cards and said you wanted to cheat,"
                                        + "are you sure you don't want to play another card? y or n");
                                boolean check = false;
                                
                                while(!check){
                                    stringInput = scan.next();
                                    stringInput = stringInput.toLowerCase();
                                    
                                    if(stringInput.equals("y") || 
                                stringInput.equals("yes")){
                                        keepCheating = false;
                                        answer = true;
                                        check = true;
                                        bidRankSet = true;
                                        bidRank = bidCard.getRank();
                                        System.out.println("Bid rank set to: " + bidRank);
                                        
                                    }
                                    else if(stringInput.equals("n") || 
                                stringInput.equals("no")){
                                        keepCheating = true;
                                answer = true;
                                check = true;
                                    }
                                    else{
                            System.out.println("Invalid answer, please try "
                                    + "again.");
                            stringInput = scan.next();
                                    }
                                    
                                }
                                
                            }
                            else{
                                keepCheating = false;
                                answer = true;
                            }
                        }
                        //the input was incorrect so ask the user to try again
                        else{
                            System.out.println("Invalid answer, please try "
                                    + "again.");
                            stringInput = scan.next();
                            keepCheating = true;
                        }
                    }
                   
                }
                catch(NumberFormatException e){System.out.println(e + 
                        "\nThat's an invalid answer, please try again");}
            }while(keepCheating);
            if(!bidRankSet){
            //else let the user choose the bid rank
            System.out.println("Please choose a bid rank to lie with by"
                            + "typing the number next to it:");
            System.out.println(b.r + ":\t" + 0);
            System.out.println(b.r.getNext() + ":\t" + 1);
            answer = false;
            while(!answer){
                try{
                    input = scan.next();
                    intInput = Integer.parseInt(input);
                    //bid rank equal the preivious bid rnak
                    if(intInput == 0){
                        bidRank = rank;
                        answer = true;
                    }
                    //bid rank equal the rank after the previous bid rank
                    else if(intInput == 1){
                        bidRank = rank.getNext();
                        answer = true;
                    }
                    //incorrect answer so get the user to try again
                    else{
                        System.out.println("Invalid answer, please pick 0 "
                                + "or 1");
                        
                           
                    }
                }catch(NumberFormatException e){System.out.println(e + 
                        "\nThat's an invalid answer, please try again");}
            }
            }
            
        }
        else{
            answer = false;
            do{
                try{
                    printCardOptions(h);
                    //get users input
                    input = scan.next();
                    intInput = Integer.parseInt(input);
                    //validate the users input
                    intInput = checkInputInRange(intInput, h);
                    bidCard = h.getCard(intInput);
                    bidRank = bidCard.getRank();
                    //check the bid card is honest
                    if(bidRank == rank || bidRank == rank.getNext()){
                        answer = true;
                        //add bid card to the bid hand
                        bidHand.add(bidCard);
                        //remove the bid card from the players hand
                        h.remove(bidCard);  
                    }
                    //bid card isn't honest so ask the user to try again
                    else{
                        System.out.println("The rank must much the rank of the "
                                + "current bid or the the next rank after it");
                        answer = false;
                    }
                }catch(NumberFormatException e){System.out.println(e + 
                        " That's an invalid answer, please try again");}
            }while(!answer);
            answer = false;
            do{
                try{
                    //ask the user if they want to play another card
                    System.out.println("Do you want to play another card? y or "
                            + "n");
                    stringInput = scan.next();
                    stringInput = stringInput.toLowerCase();
                    //if they want to play another card
                    if(stringInput.equals("y") || stringInput.equals("yes")){
                        keepPlaying = false;
                        //go through all the cards and make sure they have 
                        //another honest card
                        for(Card c:h){
                            if(c.getRank() == bidRank){
                               keepPlaying = true;
                               answer = false;
                            } 
                        }
                        if(keepPlaying){
                            printCardOptions(h);
                            //get the users input
                            input = scan.next();
                            intInput = Integer.parseInt(input);
                            //validate the users input
                            intInput = checkInputInRange(intInput, h);
                            bidCard = h.getCard(intInput);
                            //check the bid card is honest
                            if(bidCard.getRank() == bidRank){
                                //add the bid card to the bid hand
                                bidHand.add(bidCard);
                                //remove the bid card from the players hand
                                h.remove(bidCard);
                            }
                            else{
                                System.out.println("You can't play that card"
                                        + " as it is not the same rank as"
                                        + "your previous chosen card.");
                            }
                        }
                        //no honest cards left to play to stop playing cards
                        else{
                            System.out.println("No honnest cards left to play");
                            answer = true;
                        }
                    }
                    //dont't play any more cards
                    else if(stringInput.equals("n")||stringInput.equals("no")){
                        answer = true;
                    }
                    //the users' input was invalid so ask them to try again
                    else{
                        System.out.println("Answer invalid, please try again.");
                        answer = false;
                    }
                    
                }catch(NumberFormatException e){System.out.println(e + 
                        " That's an invalid answer, please try again");}
            }while(!answer);
            
        }
        Bid thisBid = new Bid(bidHand, bidRank);
        return thisBid;
        
    }
       
    @Override
    public Bid chooseBid(Bid b, Hand h, Cheat cheat){
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean callCheat(Hand h,Bid b){
      boolean callCheat = false;
        boolean answer = false;
        Scanner scan = new Scanner(System.in);
        String choice;
        //inform the player of the current bid rank and size and remind them of
        //their own hand
        System.out.println("The current bid rank is: " + b.r);
        System.out.println("The number of cards played on the previous go: "
                + b.h.size());
        System.out.println("Your current hand is: " + h);
        //ask the player if they want to call cheat
        System.out.println("Do you want to call cheat?\n"
                + "Click y for yes or n for no");
        
        choice = scan.nextLine();
        choice = choice.toLowerCase();
        while(!answer){
            choice = choice.toLowerCase();
            //if the users answer is y call cheat
            if(choice.equals("y") || choice.equals("yes")){
                answer = true;
                callCheat = true;
            }
            //if the users answer is n don't call cheat
            else if(choice.equals("n") || choice.equals("no")){
                answer = true;
                callCheat = false;
            }
            //the users' answer was invalid so ask them to try again
            else{
                System.out.println("INVALID ANSWER: Please try again");
                choice = scan.nextLine();
            }
        }
        return callCheat;
    }
    
    @Override
    public boolean callCheat(Hand h,Bid b, double p){
        throw new UnsupportedOperationException();
    }
    
}
