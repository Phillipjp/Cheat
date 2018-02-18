package cards;
import java.util.*;
import java.io.Serializable;
/**
 *
 * @author Phillip
 */
public class Hand implements Iterable<Card>, Serializable {
    
    private static final long serialVersionUID = 102L;
    
    private ArrayList<Card> handOfCards;
    private ArrayList<Card> iterateList;
    
    private int numberOfRank [] = new int [13];
    private int numberOfSuit [] = new int [4];
    private int valueOfHand = 0;
    
    public Hand(){
        this.handOfCards = new ArrayList<>();
        this.iterateList = new ArrayList<>();
    }
    
    public Hand(Card[] cardArray){
        this.handOfCards = new ArrayList<>();
        this.iterateList = new ArrayList<>();
        for (Card card : cardArray) {
            handOfCards.add(card);
        }
    }
    
    public Hand(Hand secondHand){
          
        this.iterateList = new ArrayList<>();
        this.handOfCards = new ArrayList<>();
        for(Card c: secondHand)
            handOfCards.add(c);
        
    }
    
    public Card getCard(int i){
        return handOfCards.get(i);
    }
    
    public Hand getAllCardsByRank(Card.Rank r){
        Hand hand = new Hand();
        for(Card c: handOfCards){
            if(c.getRank()== r){
                hand.add(c);
            }
        }
        return hand;
    }
    
    public void clearHand(){
        handOfCards.clear();
    }
    
    public int[] getNumberOfRank(){
        return numberOfRank;
    }
    
    public int[] getNumberOfSuit(){
        return numberOfSuit;
    }
    
    public int countRank(Card.Rank rank) {
        int numberOfCards = 0;
        for (Card c : handOfCards) {
            if (c.getRank()==rank) {
                numberOfCards++;
            }
        }
        return numberOfCards;
    }
    
    public int countSuit(Card.Suit suit) {
        int numberOfCards = 0;
        for (Card c : handOfCards) {
            if (c.getSuit()==suit) {
                numberOfCards++;
            }
        }
        return numberOfCards;
    }
    
    public boolean containsSingleRank(Card.Rank r){
        int i = 0;
        for(Card c: handOfCards){
            if(c.getRank().equals(r)){
                if(handOfCards.contains(c)){
                    i++;
                }
            } 
        }
        return i==handOfCards.size();
    }
    
    public int handValue(){
        int handValue = 0;
        int val;
        for (Card card : handOfCards) {
            val = card.getRank().getValue();
            handValue += val;
            }
        return handValue;
    }
    
    
    public void add(Card c)
    {
        handOfCards.add(c);
        iterateList.add(c);
        numberOfRank[c.getRank().ordinal()]++;
        numberOfSuit[c.getSuit().ordinal()]++;
        valueOfHand += c.getRank().getValue();
    }  
    
    public void add(Collection<Card> cardCollection){
        for(Card c : cardCollection){
            this.add(c);
        }
    }
    
    public void add(Hand h){
        for(Card c : h.handOfCards){
            this.add(c);
        }
    }
    
    public boolean remove(Card c){
        if(handOfCards.contains(c)){
            this.handOfCards.remove(c);
            iterateList.remove(c);
            numberOfRank[c.getRank().ordinal()]--;
            numberOfSuit[c.getSuit().ordinal()]--;
            valueOfHand -= c.getRank().getValue();
            return true;
        }
        return false;
    }
    
    public boolean remove(Hand h){
        boolean flag = false;
        for(Card c : h.handOfCards){
            if(handOfCards.contains(c)){
                this.remove(c);
                flag = true;
            }     
        }
        return flag;
    }
    
    public Card remove(int pos){
        Card removedCard = handOfCards.get(pos);
        if(handOfCards.contains(removedCard)){
            this.remove(removedCard);
            return removedCard;
        }
        return null;
    }
    
    public int size(){
        return handOfCards.size();
    }
    //Create new iterator.
    @Override
    public Iterator<Card> iterator() {
        return new NestedIterator();
    }

    //Nested iterator class.
    private class NestedIterator implements Iterator<Card> {

        int position = 0;
        @Override
        public boolean hasNext() {
            return (position < iterateList.size());
        }

        @Override
        public Card next() {
            return iterateList.get(position++);
        }   
        
        @Override
        public void remove() {
            Card topCard = iterateList.get(position);
            iterateList.remove(topCard);
            
            
        } 
    }
    
    public void sortAscending(Hand h){
        Card currentCard;
        Card nextCard;
        int sorted;
        if(!h.handOfCards.isEmpty()){
            do{
                sorted = 1;

                for(int i=0; i<h.handOfCards.size()-1;i++){
                    currentCard = h.handOfCards.get(i);
                    nextCard = h.handOfCards.get(i+1);
                    if(currentCard.compareTo(nextCard) == 1){
                        Collections.swap(h.handOfCards, i+1, i);
                        sorted = 0;
                    }
                }
            }
            while(sorted == 0);
        }
    }
    
    public void sortDescending() {
        Comparator compDsc = new Card.CompareDescending();
        Collections.sort(this.handOfCards, compDsc);
    }
    
    public boolean isFlush(){
        int cardCount = 0;
        Card firstCard = handOfCards.get(0);
        Card.Suit suit = firstCard.getSuit();
        
        for(Card c : handOfCards){
            if(suit == c.getSuit())
                cardCount++;
        }
        return cardCount == handOfCards.size();
    }
    
    public boolean isStraight(){
        int checkCount = 0;
        int ordinalA;
        int ordinalB;
        int difference;
        Hand hand = new Hand(this);
        Card cardA;
        Card cardB;
        hand.sortDescending();
        
        for(int i=0; i<hand.handOfCards.size()-1;i++){
        cardA =hand.handOfCards.get(i); 
        cardB =hand.handOfCards.get(i+1);
        ordinalA = cardA.getRank().ordinal();
        ordinalB = cardB.getRank().ordinal();
        difference = ordinalA - ordinalB;
        if(difference == 1)
            checkCount++;
        }
        if(checkCount == hand.handOfCards.size()-1)
            return true;
        else 
            return false;
    }
    
    @Override
    public String toString(){
        StringBuilder hand = new StringBuilder();
        hand.append("\n");
        for(Card c:handOfCards){
            hand.append(c).append("\n");
        }
        return hand.toString();
    
    }
    
    public static void main (String[] args){
        Hand hand1 = new Hand();
        System.out.println("Hand hand1 = new Hand()");
           
        Card card1 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card card2 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.SEVEN, Card.Suit.SPADES);
        Card card4 = new Card(Card.Rank.TEN, Card.Suit.CLUBS);
        Card card5 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card6 = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        Card card7 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        Card card8 = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        System.out.println("card1 = " + card1);
        System.out.println("card2 = " + card2);
        System.out.println("card3 = " + card3);
        System.out.println("card4 = " + card4);
        System.out.println("card5 = " + card5);
        System.out.println("card6 = " + card6);
        System.out.println("card7 = " + card7);
        System.out.println("card8 = " + card8);
        System.out.print("_________________"
                + "________________________\n");
        
        ArrayList<Card> cards1 = new ArrayList<>();
        System.out.println("ArrayList<Card> cards1 = new ArrayList<>();");
        System.out.println("cards1.add(card1) \t" + cards1.add(card1));
        System.out.println("cards1.add(card2) \t" + cards1.add(card2));
        System.out.println("cards1.add(card3) \t" + cards1.add(card3));
        System.out.println("cards1.add(card4) \t" + cards1.add(card4));
        System.out.print("_________________"
                + "________________________\n");
        
        hand1.add(cards1);
        System.out.print("hand1.add(cards1);\t" + hand1);
        System.out.print("_________________"
                + "________________________\n");
        
        Card cardArray[] = new Card[]{card5, card6, card7, card8};
        Hand hand2 = new Hand(cardArray);
        System.out.println("Card cardArray[] = new Card[]{card5, card6, card7, "
                + "card8};");
        System.out.println("Hand hand2 = new Hand(cardArray);");
        System.out.println("hand2:" + hand2);
        System.out.print("_________________"
                + "________________________\n");
        
        Hand hand3 = new Hand(hand1);
        System.out.println("Hand hand3 = new Hand(hand1);");
        System.out.println("hand3:\t" + hand3);
        System.out.print("_________________"
                + "________________________\n");
        
        System.out.print("Method\t\t\tOutput\n");
        System.out.println("_________________"
                + "________________________\n");
        
        System.out.print("hand1.numberOfRank:\t");
        System.out.println(hand1.numberOfRank[0]);
        for(int i = 1; i<13;i++){
            System.out.println("\t\t\t" + hand1.numberOfRank[i]);
        }
        System.out.print("_________________"
                + "________________________\n");
        
        System.out.print("hand1.numberOfSuit:\t");
        System.out.println(hand1.numberOfSuit[0]);
        for(int i = 1; i<4;i++){
            System.out.println("\t\t\t" + hand1.numberOfSuit[i]);
        }
        System.out.print("_________________"
                + "________________________\n");
        
        System.out.println("hand1.valueOfHand:\t" + hand1.valueOfHand);
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand1.remove(card1):\t" + hand1.remove(card1));
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand3.remove(hand1)\t" + hand3.remove(hand1));
        System.out.print("_________________"
                + "________________________\n");
        
        Hand hand4 = new Hand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(card1);
        cards2.add(card2);
        cards2.add(card3);
        cards2.add(card4);
        cards2.add(card5);
        cards2.add(card6);
        cards2.add(card7);
        cards2.add(card8);
        
        hand4.add(cards2);
        
        System.out.println("hand4:" + hand4);
        System.out.print("_________________"
                + "________________________\n");
        hand4.sortAscending(hand4);
        System.out.println("hand4.sortAscending(hand4);\t" + hand4);
        System.out.print("_________________"
                + "________________________\n");
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("Iterator in original order \n");
        Iterator it = hand4.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        
        hand4.sortDescending();
        System.out.print("_________________"
                + "________________________\n");
        hand4.sortDescending();
        System.out.println("hand4.sortDescending();\t" + hand4);
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand4.countRank(Card.Rank.ACE);\t" + 
                hand4.countRank(Card.Rank.ACE));
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand4.countSuit(Card.Rank.CLUBS);" + 
                hand4.countSuit(Card.Suit.CLUBS));
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand4.handValue();\t" + hand4.handValue() + "\n");
        System.out.print("_________________"
                + "________________________\n");
        Hand hand5 = new Hand();
        ArrayList<Card> cards3 = new ArrayList<>();
        cards3.add(card4);
        cards3.add(card5);
        cards3.add(card6);
        cards3.add(card7);
        cards3.add(card8);
        
        hand5.add(cards3);
        
        System.out.println("hand5:" + hand5);
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand5.isFlush();\t" + hand5.isFlush() + "\n");
        System.out.print("_________________"
                + "________________________\n");
        System.out.println("hand5.isStraight();\t" + hand5.isStraight() + "\n");
        
        Hand h = new Hand();
        
        Card card9 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card card10 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card11 = new Card(Card.Rank.SEVEN, Card.Suit.SPADES);
        Card card12 = new Card(Card.Rank.TEN, Card.Suit.CLUBS);
        Card card13 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        
        h.add(card9);
        System.out.println("Contains only ace: " + h.containsSingleRank(Card.Rank.ACE));
        System.out.println("Contains only two: " + h.containsSingleRank(Card.Rank.TWO));
        h.add(card10);
        System.out.println("Contains only ace: " + h.containsSingleRank(Card.Rank.ACE));
        h.add(card13);
        System.out.println("Contains only ace: " + h.containsSingleRank(Card.Rank.ACE));
        h.add(card11);
        System.out.println("Contains only ace: " + h.containsSingleRank(Card.Rank.ACE));
        
    }
    }
