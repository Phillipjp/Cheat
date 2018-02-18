package cards;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @author Phillip
 */
public class Card implements Comparable<Card>, Serializable{

    
    private static final long serialVersionUID = 100L;
    
    private Suit suit;
    private Rank rank;
    
    //Values for the suit of a card
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES};
    
    public enum Rank{
        
        //Values for the rank of a card
        TWO(2), THREE(3), FOUR(4), FIVE(5), 
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), 
        TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
        
        public int value;
        
        //Constructor for a card rank
        Rank (int value){
            this.value = value;
        }
        
        //Gets the value of a card
        public int getValue(){
            return value;
        }
        
        //Gets the next enum value in the list
        public Rank getNext(){
            int nextValue = ordinal()+1;
            if(nextValue > 12 ){
                nextValue = 0;
            }
            return Rank.values() [nextValue];  
        }  
    }
    
    //Constructor with Rank and Suit passed as parameters
    Card (Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    public Rank getRank(){
        return rank;
    }
    
    public Suit getSuit(){
        return suit;
    }
    
    @Override
    public String toString(){
        StringBuilder card = new StringBuilder();
        card.append(rank).append(" of ").append(suit);
        return card.toString();
        //return rank + " of " + suit;
    }
    
    @Override
    public int compareTo(Card c){
        int rankComp = this.getRank().compareTo(c.getRank());
        int suitComp = this.getSuit().compareTo(c.getSuit());
        
        if(rankComp < 0)
            return -1;
        else if(rankComp == 0){
            if(suitComp < 0)
                return 1;
            else
                return -1;
        }
        return 1;              
    }
    
    public static int difference (Card a, Card b){
        int cardA = a.rank.ordinal();
        int cardB = b.rank.ordinal();

        if(cardA > cardB )
            return cardA-cardB;
        else
            return cardB-cardA;
    }
    
    public static int differenceValue(Card a, Card b){
        int cardA = a.rank.getValue();
        int cardB = b.rank.getValue();
        if(cardA >= cardB )
            return cardA-cardB;
        else
            return cardB-cardA;            
        }
        
    public static class CompareDescending implements Comparator<Card>{
        @Override
        public int compare(Card a, Card b){
              
            int rankComp = a.getRank().compareTo(b.getRank());
            
                return b.rank.ordinal()-a.rank.ordinal();

               
        }
    }
        
    public static class CompareSuit implements Comparator<Card>{
        @Override
        public int compare(Card a, Card b){
                
            if(a.getSuit().equals(b.getSuit())){
                return a.rank.ordinal()-b.rank.ordinal();
            }
            else {
                return a.suit.ordinal() - b.suit.ordinal();   
            }
                           
        }  
    }
        
        public static void main(String[] args) {
        Card card1 = new Card(Rank.QUEEN, Suit.CLUBS);
        Card card2 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card card3 = new Card(Rank.TEN, Suit.SPADES);
        Card card4 = new Card(Rank.EIGHT, Suit.CLUBS);
        Card card5 = new Card(Rank.TWO, Suit.CLUBS);
        Card card6 = new Card(Rank.ACE, Suit.CLUBS);
        Card card7 = new Card(Rank.SIX, Suit.CLUBS);
        Card card8 = new Card(Rank.QUEEN, Suit.SPADES);
        Card card9 = new Card(Rank.QUEEN, Suit.HEARTS);
        
        Card card40 = new Card(Rank.SIX, Suit.HEARTS);
        Card card20 = new Card(Rank.TEN, Suit.SPADES);
        Card card30 = new Card(Rank.TWO, Suit.CLUBS);
        Card card10 = new Card(Rank.TEN, Suit.DIAMONDS);
        Card card50 = new Card(Rank.TEN, Suit.CLUBS);
        Card card60 = new Card(Rank.TEN, Suit.HEARTS);
        System.out.print("Card #1 is:\t\t" + card1 + "\n");
        System.out.print("Card #2 is:\t\t" + card2 + "\n");
        System.out.print("Card #3 is:\t\t" + card3 + "\n");
        System.out.print("Card #4 is:\t\t" + card4 + "\n\n\n");

        System.out.print("Method\t\t\tOutput\n");
        System.out.println("_____________________"
                + "____________________\n");
        System.out.print("card1.getRank()");
        System.out.print("\t\t" + card1.getRank() + "\n");
        System.out.print("card1.getNext()");
        System.out.print("\t\t" + card1.getRank().
                getNext() + "\n");
        System.out.print("card1.getSuit()");
        System.out.print("\t\t" + card1.getSuit() + "\n");
        System.out.print("card1.getValue()");
        System.out.print("\t" + card1.getRank().
                getValue() + "\n");
        
        System.out.print("card6.getValue().ordinal()");
        System.out.print("\t" + card6.getRank().ordinal() + "\n");
        
        System.out.print("card5.getValue().ordinal()");
        System.out.print("\t" + card5.getRank().ordinal() + "\n");
        
        System.out.println("_____________________"
                + "____________________\n");
        System.out.print("difference(card1,card3)");
        System.out.print("\t\t" + difference(card1, card3) + "\n");
        
        System.out.println("_____________________"
                + "____________________\n");
        System.out.print("differenceValue(card1,card3)");
        System.out.print("\t" + differenceValue(card1, card3) + "\n");
        
        System.out.println("_____________________"
                + "____________________\n");
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card10);
        cards.add(card20);
        cards.add(card30);
        cards.add(card40);
        cards.add(card50);
        cards.add(card60);
//        cards.add(card5);
//        cards.add(card6);
//        cards.add(card7);
//        cards.add(card8);
//        cards.add(card9);
       
        Comparator<Card> dsc = new CompareDescending();
        Collections.sort(cards, dsc);
        System.out.print("CompareDescending\t");
        for (int i = 0; i < cards.size() - 1; i++) {
            System.out.print(cards.get(i) + "\n");
            System.out.print("\t\t\t");
        }
        System.out.print(cards.get(cards.size() - 1) + "\n\n");

        System.out.print("_________________"
                + "________________________\n");
        Comparator<Card> suit = new CompareSuit();
        
        Collections.sort(cards, suit);
        System.out.print("CompareSuit\t\t");
        for (int i = 0; i < cards.size() - 1; i++) {
            System.out.print(cards.get(i) + "\n");
            System.out.print("\t\t\t");
        }
        System.out.print(cards.get(cards.size() - 1) + "\n");
        System.out.print("_________________"
                + "________________________\n");
        
        Card c = new Card(Rank.FOUR, Suit.CLUBS);
        Card cc = new Card(Rank.QUEEN, Suit.HEARTS);
        
            System.out.println(c.compareTo(cc));
            
            System.out.println(suit.compare(c, cc));
            System.out.println(dsc.compare(c, cc));

    }
    //************************************************/
    }
    
