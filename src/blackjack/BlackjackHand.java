package blackjack;

import java.util.ArrayList;
import java.util.Random;

public class BlackjackHand implements Comparable<BlackjackHand>
{
	private ArrayList<Byte> handCards = new ArrayList<Byte>();
	private static ArrayList<Byte> cardsUsed = new ArrayList<Byte>();
	private boolean isHandDealer;						//Used to hide the dealer's face-down cards when printing hand
	private boolean roundOver = false;
	private byte currentCard;
	private byte cardTotal;
	private byte softAceTracker = 0;					//Tracks number of soft aces in the hand to convert them to hard aces when over 21
	private Random rand = new Random();

	public BlackjackHand(boolean dealer)
	{
		isHandDealer = dealer;
	}

	public int getTotal()
	{
		return cardTotal;
	}

	public void hit()
	{
		boolean hasCardBeenUsed = false;
		do
		{
			currentCard = (byte)(rand.nextDouble() * 52);
			for(int i = 0; i < cardsUsed.size(); i++)			//Checks if card generated is equal to one already used.
			{
				if(currentCard == cardsUsed.get(i))
					hasCardBeenUsed = true;
			}
		} while(hasCardBeenUsed == true);
		cardsUsed.add(currentCard);
		currentCard = (byte)((currentCard / 4) + 1);
		handCards.add(currentCard);
		switch (currentCard)
		{
		case 1:
			if(cardTotal <= 10)
			{
				cardTotal += 11;
				softAceTracker++;
			} else
				cardTotal += 1;
			break;
		case 11:
			cardTotal += 10;
			break;
		case 12:
			cardTotal += 10;
			break;
		case 13:
			cardTotal += 10;
			break;
		default:
			cardTotal += currentCard;
			break;
		}
		if(cardTotal > 21)
		{
			if(softAceTracker > 0)
			{
				cardTotal -= 10;
				softAceTracker--;
			}
		}
	}
	/**
	 *
	 */
	public void printHand()
	{
		if(isHandDealer == true && roundOver == false)
		{
			System.out.printf("\nDealer: X ");
			for(int i = 1; i < handCards.size(); i++)
				switch(handCards.get(i))
				{
				case 1:
					System.out.print("A ");
					break;
				case 11:
					System.out.print("J ");
					break;
				case 12:
					System.out.print("Q ");
					break;
				case 13:
					System.out.print("K ");
					break;
				default:
					System.out.print(handCards.get(i) + " ");
					break;
				}
		}
		else
		{
			if(roundOver == true && isHandDealer == true)
				System.out.printf("\nDealer: ");
			else
				System.out.print("You:    ");
			for(int i = 0; i < handCards.size(); i++)
				switch(handCards.get(i))
				{
				case 1:
					System.out.print("A ");
					break;
				case 11:
					System.out.print("J ");
					break;
				case 12:
					System.out.print("Q ");
					break;
				case 13:
					System.out.print("K ");
					break;
				default:
					System.out.print(handCards.get(i) + " ");
					break;
				}
			System.out.printf("Total: %d", cardTotal);
			if(softAceTracker > 0)
				System.out.print(" (Soft)");
		}
			System.out.println();
	}

	@Override
	public int compareTo(BlackjackHand other)
	{
		if(this.getTotal() > 21 && other.getTotal() <= 21)
			return -1;
		if(this.getTotal() <= 21 && other.getTotal() > 21)
			return 1;
		if(this.getTotal() > 21 && other.getTotal() > 21)
			return 0;
		if(this.getTotal() > other.getTotal())
			return 1;
		if(this.getTotal() < other.getTotal())
			return -1;
		else
			return 0;
	}

	/**
	 * Changes the setting of this hand as the dealer's hand after object creation.
	 * Currently unused, but I might make use of it in a future version.
	 * @param deal the true/false value for the status of this hand as the dealer's hand
	 */
	public void changeDealerStatus(boolean deal)
	{
		isHandDealer = deal;
	}

	/**
	 * Marks the round as over. Allows the printHand() function to reveal the dealer's face-down
	 * cards at the end of the round.
	 */
	public void endRound()
	{
		roundOver = true;
	}

	/**
	 * Resets all variables of the object, except isHandDealer, to their default states. Used at the end of a
	 * round to reset everything for the next.
	 */
	public void reset()
	{
		handCards.clear();
		cardsUsed.clear();
		cardTotal = 0;
		currentCard = 0;
		softAceTracker = 0;
		roundOver = false;
	}
}
