package cheat;
import cards.*;
import java.util.Random;

public class Bid {
		Hand h;
		Card.Rank r;
		public Bid(){
                    
                    Random rn = new Random();
                    //create a random number between 0 and 13 that's used to 
                    //assign an initial bid rank after cheat has been called
                    int randomRank = rn.nextInt(13);
                    h=new Hand();
                    r=Card.Rank.values()[randomRank]; 
		}
		public Bid(Hand hand,Card.Rank bidRank){
			h=hand;
			r=bidRank;
		}
		public void setHand(Hand hand){ h=hand;}
		public void setRank(Card.Rank rank){ r=rank;}
		
		public Hand getHand(){ return h;}
		public int getCount(){ return h.size();}
		public Card.Rank getRank(){ return r;}
                @Override
		public String toString(){
			return h.size()+" x "+r;
		}
		
}	

