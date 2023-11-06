import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryCardGame1 extends JFrame {
    private List<Card> cards;
    private Card firstCard;
    private Card secondCard;
    private Timer timer;
    private int elapsedTimeInSeconds;
    private int points;
    private JButton resetButton;

    public MemoryCardGame1() {
        setTitle("Memory Card Game");
        setLayout(new BorderLayout());

        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(resetButton, BorderLayout.NORTH);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(4, 4));
        add(cardPanel, BorderLayout.CENTER);

        cards = new ArrayList<>();
        List<Integer> symbols = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            symbols.add(i);
            symbols.add(i);
        }
        Collections.shuffle(symbols);

        for (int symbol : symbols) {
            Card card = new Card(symbol);
            card.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    revealCard(card);
                }
            });
            cards.add(card);
            cardPanel.add(card);
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTimeInSeconds++;
            }
        });
        timer.start();

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void revealCard(Card card) {
        if (firstCard == null && !card.isRevealed()) {
            firstCard = card;
            firstCard.showSymbol();
        } else if (secondCard == null && card != firstCard && !card.isRevealed()) {
            secondCard = card;
            secondCard.showSymbol();

            Timer checkMatchTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkMatch();
                }
            });
            checkMatchTimer.setRepeats(false);
            checkMatchTimer.start();
        }
    }

    private void checkMatch() {
        if (firstCard.getSymbol() == secondCard.getSymbol()) {
            firstCard.setEnabled(false);
            secondCard.setEnabled(false);
        } else {
            firstCard.hideSymbol();
            secondCard.hideSymbol();
        }

        firstCard = null;
        secondCard = null;

        if (allCardsMatched()) {
            timer.stop();
            calculatePoints();
            JOptionPane.showMessageDialog(this, "Congratulations! You completed the game in " +
                    elapsedTimeInSeconds + " seconds.\nPoints: " + points);
        }
    }

    private boolean allCardsMatched() {
        for (Card card : cards) {
            if (card.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void calculatePoints() {
        if (elapsedTimeInSeconds < 15) {
            points = 10;
        } else if (elapsedTimeInSeconds < 17) {
            points = 9;
        } else if (elapsedTimeInSeconds < 20) {
            points = 8;
        } else if (elapsedTimeInSeconds < 23) {
            points = 7;
        } else {
            points = 6;
        }
    }

    private void resetGame() {
        for (Card card : cards) {
            card.setEnabled(true);
            card.hideSymbol();
        }
        elapsedTimeInSeconds = 0;
        points = 0;
        timer.restart();
    }

    private class Card extends JButton {
        private int symbol;
        private boolean revealed;

        public Card(int symbol) {
            this.symbol = symbol;
            this.revealed = false;
            setText("?");
        }

        public int getSymbol() {
            return symbol;
        }

        public void showSymbol() {
            setText(String.valueOf(symbol));
            revealed = true;
        }

        public void hideSymbol() {
            setText("?");
            revealed = false;
        }

        public boolean isRevealed() {
            return revealed;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MemoryCardGame1();
        });
    }
}