package blackjack;
import java.util.Scanner;

public class BlackjackMain {

	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		boolean gameOver = false;
		System.out.println("Welcome to Blackjack! Dealer stands on soft 17 or higher. Enter any key to deal.");
		keyboard.next();
		BlackjackHand player = new BlackjackHand(false);
		BlackjackHand dealer = new BlackjackHand(true);
		do
		{
			gameOver = BlackjackRound(keyboard, player, dealer);
		} while(gameOver == false);
		keyboard.close();
	}

	public static boolean BlackjackRound(Scanner keyboard, BlackjackHand player, BlackjackHand dealer)
	{
		player.hit();
		player.hit();
		dealer.hit();
		dealer.hit();
		boolean hitMe = true;
		String answer;
		System.out.println("Deal!");
		dealer.printHand();
		player.printHand();
		/*
		 * TODO Integrate the following while loop's functionality into the
		 * BlackjackHand class and replace this part with a method call.
		 */
		while((hitMe == true && player.getTotal() < 21) || dealer.getTotal() < 17)
		{
			if(hitMe == true && player.getTotal() < 21)
			{
				do
				{
					System.out.print("Hit? (Y/N)");
					answer = keyboard.next();
					if(answer.equals("y") || answer.equals("Y"))
						player.hit();
					else if(answer.equals("n") || answer.equals("N"))
						hitMe = false;
					else
						System.out.println("Invalid input.");
				} while(!(answer.equals("y")) && !(answer.equals("Y")) && !(answer.equals("n")) && !(answer.equals("N")));
			}
			if(dealer.getTotal() < 17 && player.getTotal() < 21)
				dealer.hit();					//Dealer hits if below 17 and player hasn't busted
			dealer.printHand();
			player.printHand();
		}
		resolveWinConditions(player, dealer);

		do
		{
			System.out.print("Play Again? (Y/N)");
			answer = keyboard.next();
			if(answer.equals("y") || answer.equals("Y"))
			{
				return false;
			}
			else if(answer.equals("n") || answer.equals("N"))
			{
				return true;
			}
			else
				System.out.println("Invalid input.");
		} while(!(answer.equals("y")) || !(answer.equals("Y")) || !(answer.equals("n")) || !(answer.equals("N")));

		return true;			//Failsafe, this return statement should not trigger if the "Play Again?" loop above is functioning properly.
	}
	/*
	 * TODO Integrate the following method into the BlackjackHand class and replace
	 * calls to this method from the main with calls to the object method
	 */
	public static void resolveWinConditions(BlackjackHand p, BlackjackHand d)
	{
		d.endRound();
		d.printHand();
		p.printHand();
		if(p.getTotal() > 21)
			System.out.print("Bust! ");
		if(d.getTotal() > 21)
			System.out.print("Dealer Bust! ");
		if(p.compareTo(d) == 1)
			System.out.println("You win!");
		else if(p.compareTo(d) == -1)
			System.out.println("Dealer wins.");
		else
			System.out.println("Push.");
		p.reset();
		d.reset();
	}
}
