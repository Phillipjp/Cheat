package cards;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.Serializable;

/**
 *
 * @author Phillip
 */
public class Deck implements Iterable<Card>, Serializable {
    
    private static final long serialVersionUID = 101L;
    
    transient public ArrayList<Card> deckOfCards;
    
    public Deck() {
        this.deckOfCards = new ArrayList<>();
        
        for (int i = 0; i < Card.Suit.values().length; i++) { 
            for (int j = 0; j < Card.Rank.values().length; j++) { 
                Card card = new Card(Card.Rank.values()[j],
                        Card.Suit.values()[i]);
                this.deckOfCards.add(card);
            }
        }
    }
    
    public void shuffle(){
        
        int deckSize = this.deckOfCards.size();
        Random random = new Random();
        int swapCardIndex;
        Card currentCard;
        //go through all the cards in the deck
        for (int i = 0; i < deckSize; i++) {
            //get a random card between i and decksize
            swapCardIndex = i + random.nextInt(deckSize - i);
            //get the card a position i
            currentCard = deckOfCards.get(i);
           //swap the current card and the card at the position of swapCardIndex
            deckOfCards.set(i, deckOfCards.get(swapCardIndex));
            deckOfCards.set(swapCardIndex, currentCard);
        }
    }
    
    public Card deal(){
        Card topCard;
        topCard=deckOfCards.get(0);
        deckOfCards.remove(0);
        return topCard;
    }
    
    public int size() {
        return this.deckOfCards.size();
    }
    
    public final void newDeck(){
        deckOfCards.clear();
        for (int i = 0; i < Card.Suit.values().length; i++) { 
            for (int j = 0; j < Card.Rank.values().length; j++) { 
                Card card = new Card(Card.Rank.values()[j],
                        Card.Suit.values()[i]);
                deckOfCards.add(card);
            }
        }
    }
    
    @Override
    public String toString(){
        StringBuilder deck = new StringBuilder();
        for(Card c: deckOfCards){
            deck.append(c).append("\n");
        }
        return deck.toString();
    }
    
    @Override
    public Iterator<Card> iterator() {
        return new DealOrder();
    }
    
    private void writeObject(java.io.ObjectOutputStream s) throws IOException{
        s.defaultWriteObject();
        Iterator<Card> it = new OddEvenIterator();
        while(it.hasNext()){
            Card c = it.next();
            //System.out.println(c);
            s.writeObject(c);
        }
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        ArrayList<Card> cards = new ArrayList(); 
        Card c = (Card) in.readObject();
        
        while(c!=null){
            cards.add(c);
            try{
            c = (Card) in.readObject();
            }catch(IOException | ClassNotFoundException e){break;};
        }
        deckOfCards = cards;
    }
    public static Deck loadFromFile(String filename) 
            throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Deck deck = (Deck) ois.readObject();
        ois.close();
        
        return deck;
    }
    
    public static void saveToFile(String filename, Deck d){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(d);
            out.close();
        }
            catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
   
    private class OddEvenIterator implements Iterator<Card>{
        
        int evenPosition = 1;
        int oddPosition = 0;
        
        
        @Override
        public boolean hasNext(){
            return evenPosition < deckOfCards.size();
        }
        
        @Override
        public Card next(){
            Card ret;
            if(oddPosition < 52){
                //oddPosition+=2;
                ret = deckOfCards.get(oddPosition);
                oddPosition+=2;
                return ret;
            }
            else{
                    //evenPosition+=2;
                    ret = deckOfCards.get(evenPosition);
                    evenPosition+=2;
                    return ret;
            }
        
        }
        
        
        
    }
    
     private class DealOrder implements Iterator<Card> {
        int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < deckOfCards.size();
        }

        @Override
        public Card next() {
            return deckOfCards.get(pos++);
        }

        @Override
        public void remove() {
            //Card topCard = deckOfCards.get(pos);
            //deckOfCards.remove(topCard);
                deckOfCards.remove(pos);

        }
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{
 
        Deck deck = new Deck();
        
        System.out.print("\tDeck deck = new Deck()\n\n");
        System.out.print("Method\t\t\tOutput\n");
        System.out.println("_________________"
                + "________________________\n");
        System.out.print("deck.size()");
        System.out.print("\t\t" + deck.size() + "\n");
        
        System.out.print("____________________"
                + "_____________________\n\n");
        System.out.print("deck.deal()");
        System.out.print("\t\t" + deck.deal() + "\n");
        System.out.print("deck.deal()");
        System.out.print("\t\t" + deck.deal() + "\n");
        System.out.print("deck.deal()");
        System.out.print("\t\t" + deck.deal() + "\n\n");
        System.out.print("deck.size()");
        System.out.print("\t\t" + deck.size() + "\n\n");
        System.out.print("____________________"
                + "_____________________\n\n");
        System.out.print("deck.newDeck()");
        deck.newDeck();
        System.out.print("\t\t Deck reinitialised. \n");  
        
        System.out.print("deck.size()");
        System.out.print("\t\t" + deck.size() +"\n\n");
        System.out.println("____________________"
                + "_____________________");
        System.out.println("deck.deal(deck.deckOfCards)");
        System.out.print("____________________"
                + "_____________________\n\n");
        for (int i=0; i<deck.deckOfCards.size();i++) {
                      System.out.println(deck.deckOfCards.get(i));  
                }
        
        deck.shuffle();
        System.out.println("____________________"
                + "_____________________");
        System.out.println("deck.shuffle()");
        System.out.print("____________________"
                + "_____________________\n\n");
        System.out.println(deck);
//        System.out.println("deck.shuffle(deck.deckOfCards)\t" + 
//                deck.deckOfCards.get(0));
//        for (int i=1; i<deck.deckOfCards.size();i++) {
//                        System.out.println("\t\t\t\t" + 
//                                deck.deckOfCards.get(i));
//                }
        System.out.println("____________________"
                + "_____________________");
        Deck deck2  = new Deck();
        System.out.println("Iterator: (DealOrder)");
         System.out.print("____________________"
                + "_____________________\n\n");
        Iterator<Card> it = deck2.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        System.out.println("\n");
        Deck deck5 = new Deck();
        saveToFile("deck.ser", deck5);
        Deck deck6 = loadFromFile("deck.ser");
        System.out.println("____________________"
                + "_____________________");
        System.out.println("serialized deck");
         System.out.print("____________________"
                + "_____________________\n\n");
        System.out.println(deck6);
         
    }
    
    
    
    
}
