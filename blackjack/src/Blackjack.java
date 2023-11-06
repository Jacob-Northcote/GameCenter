import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
}

class BlackjackGame {
    private Deck deck;
    List<Card> playerHand;
    List<Card> dealerHand;

    public BlackjackGame() {
        deck = new Deck();
        deck.shuffle();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
    }

    public int calculateHandValue(List<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            String rank = card.getRank();
            if (rank.equals("A")) {
                numAces++;
                value += 11;
            } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                value += 10;
            } else {
                value += Integer.parseInt(rank);
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    public void playerHit() {
        Card card = deck.drawCard();
        if (card != null) {
            playerHand.add(card);
        }
    }

    public void dealerHit() {
        Card card = deck.drawCard();
        if (card != null) {
            dealerHand.add(card);
        }
    }

    public String determineWinner() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue > 21) {
            return "Player BUSTS! Dealer wins.";
        } else if (dealerValue > 21) {
            return "Dealer BUSTS ALL OVER! Player wins.";
        } else if (playerValue > dealerValue) {
            return "Player wins!";
        } else if (playerValue < dealerValue) {
            return "Dealer wins.";
        } else {
            return "It's a tie!";
        }
    }
}

public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        BlackjackGame game = new BlackjackGame();

        while (true) {
            System.out.println("Player's hand: " + game.calculateHandValue(game.playerHand));
            System.out.println("Dealer's face-up card: " + game.dealerHand.get(1).getRank());

            System.out.print("Do you want to hit or stand? ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("hit")) {
                game.playerHit();
                int playerValue = game.calculateHandValue(game.playerHand);
                System.out.println("Player drew: " + game.playerHand.get(game.playerHand.size() - 1).getRank());
                if (playerValue > 21) {
                    System.out.println("Player busts! Dealer wins.");
                    break;
                }
            } else if (choice.equals("stand")) {
                while (game.calculateHandValue(game.dealerHand) < 17) {
                    game.dealerHit();
                    System.out.println("Dealer drew: " + game.dealerHand.get(game.dealerHand.size() - 1).getRank());
                }

                System.out.println("Player's hand: " + game.calculateHandValue(game.playerHand));
                System.out.println("Dealer's hand: " + game.calculateHandValue(game.dealerHand));

                System.out.println(game.determineWinner());
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'hit' or 'stand'.");
            }
        }

        scanner.close();
    }
}