package LazerGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaserBash extends JPanel implements ActionListener {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int PLAYER_WIDTH = 20;
    private static final int PLAYER_HEIGHT = 40;
    private static final int LASER_WIDTH = 15; // Made lasers slightly smaller
    private static final int LASER_LENGTH = 50; // Made lasers slightly smaller
    private static final int LASER_SPEED = 4;
    private static final int TIMER_DELAY = 10;
    private static final int PLAYER_SPEED = 5;
    private static final int COIN_SIZE = 15;
    private static final int COIN_SPEED = 1; // Made coins fall slower
    private static final int SCORE_PANEL_HEIGHT = 30;
    private static final int LASER_SPAWN_CHANCE = 8; // Reduced laser spawn rate
    private static final int COIN_SPAWN_INTERVAL = 5000;
    private static final int MAX_COINS = 10; // Increased the number of falling coins
    private static final int STAR_COUNT = 100; // Number of stars in the background
    private static final int ASTEROID_COUNT = 50; // Number of asteroids in the background

    private Timer timer, coinTimer;
    private List<Rectangle> lasers;
    private List<Rectangle> coins;
    private List<Point> stars; // List to hold the stars
    private List<Point> asteroids; // List to hold the asteroids
    private Rectangle player;
    private JButton startButton, restartButton;
    private boolean gameStarted = false;
    private Random random = new Random();
    private boolean[] keysPressed = new boolean[256];
    private int score = 0;
    private JLabel scoreLabel;

    public LaserBash() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed[e.getKeyCode()] = false;
            }
        });

        // Initialize stars
        stars = new ArrayList<>();
        for (int i = 0; i < STAR_COUNT; i++) {
            int x = random.nextInt(WINDOW_WIDTH);
            int y = random.nextInt(WINDOW_HEIGHT);
            stars.add(new Point(x, y));
        }

        // Initialize asteroids
        asteroids = new ArrayList<>();
        for (int i = 0; i < ASTEROID_COUNT; i++) {
            int x = random.nextInt(WINDOW_WIDTH);
            int y = random.nextInt(WINDOW_HEIGHT);
            asteroids.add(new Point(x, y));
        }

        player = new Rectangle(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 40, 150, 80);
        startButton.addActionListener(e -> startGame());
        this.setLayout(null);
        this.add(startButton);

        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBounds(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 + 50, 150, 80);
        restartButton.addActionListener(e -> restartGame());
        this.add(restartButton);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 5, 200, SCORE_PANEL_HEIGHT);
        this.add(scoreLabel);

        timer = new Timer(TIMER_DELAY, this);
        coinTimer = new Timer(COIN_SPAWN_INTERVAL, e -> spawnCoins());
    }

    private void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            lasers = new ArrayList<>();
            coins = new ArrayList<>();
            score = 0;
            updateScore();
            startButton.setVisible(false);
            restartButton.setVisible(false);
            requestFocus();
            timer.start();
            coinTimer.start();
        }
    }

    private void restartGame() {
        gameStarted = false;
        startGame();
    }

    private void spawnCoins() {
        if (gameStarted) {
            for (int i = 0; i < MAX_COINS; i++) {
                int x = random.nextInt(WINDOW_WIDTH - COIN_SIZE);
                int y = random.nextInt(WINDOW_HEIGHT - COIN_SIZE);
                Rectangle newCoin = new Rectangle(x, y, COIN_SIZE, COIN_SIZE);
                coins.add(newCoin);
            }
        }
    }

    private void movePlayer() {
        if (gameStarted) {
            if (keysPressed[KeyEvent.VK_W]) {
                player.y = Math.max(player.y - PLAYER_SPEED, 0);
            }
            if (keysPressed[KeyEvent.VK_S]) {
                player.y = Math.min(player.y + PLAYER_SPEED, WINDOW_HEIGHT - PLAYER_HEIGHT);
            }
            if (keysPressed[KeyEvent.VK_A]) {
                player.x = Math.max(player.x - PLAYER_SPEED, 0);
            }
            if (keysPressed[KeyEvent.VK_D]) {
                player.x = Math.min(player.x + PLAYER_SPEED, WINDOW_WIDTH - PLAYER_WIDTH);
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
            g.setColor(new Color(0, 128, 128));
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Welcome to LaserBash!", WINDOW_WIDTH / 2 - 200, WINDOW_HEIGHT / 2 - 100);
            startButton.setBackground(new Color(0, 200, 100));
        } else {
            // Draw stars
            g.setColor(Color.WHITE);
            for (Point star : stars) {
                g.fillRect(star.x, star.y, 2, 2);
            }

            // Draw asteroids
            g.setColor(Color.GRAY);
            for (Point asteroid : asteroids) {
                g.fillOval(asteroid.x, asteroid.y, 5, 5);
            }

            // Draw player as a rocket with multiple components
            int[] xPoints = {player.x, player.x, player.x + player.width};
            int[] yPoints = {player.y, player.y + player.height, player.y + player.height / 2};
            g.setColor(Color.RED);
            g.fillPolygon(xPoints, yPoints, 3); // Triangle head
            g.setColor(Color.GREEN);
            g.fillRect(player.x, player.y + player.height / 4, player.width, player.height / 4);
            g.setColor(Color.YELLOW);
            g.fillRect(player.x, player.y + player.height / 2, player.width, player.height / 4);

            // Draw lasers with more detail
            g.setColor(Color.RED);
            for (Rectangle laser : lasers) {
                g.fillRect(laser.x, laser.y, LASER_LENGTH, LASER_WIDTH);
                // Add some detail to the laser
                g.setColor(Color.ORANGE);
                g.fillRect(laser.x + 5, laser.y + 5, LASER_LENGTH - 10, LASER_WIDTH - 10);
                g.setColor(Color.RED);
            }

            g.setColor(Color.YELLOW);
            for (Rectangle coin : coins) {
                g.fillOval(coin.x, coin.y, coin.width, coin.height);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePlayer();
        if (gameStarted) {
            if (random.nextInt(100) < LASER_SPAWN_CHANCE) {
                int x = WINDOW_WIDTH;
                int y = random.nextInt(WINDOW_HEIGHT - LASER_WIDTH);
                Rectangle newLaser = new Rectangle(x, y, LASER_LENGTH, LASER_WIDTH);

                boolean touching = false;
                for (Rectangle laser : lasers) {
                    if (newLaser.intersects(laser)) {
                        touching = true;
                        break;
                    }
                }

                if (!touching) {
                    lasers.add(newLaser);
                }
            }

            List<Rectangle> lasersToRemove = new ArrayList<>();
            for (Rectangle laser : lasers) {
                laser.x -= LASER_SPEED;
                if (laser.x + LASER_LENGTH < 0) {
                    lasersToRemove.add(laser);
                }
                if (laser.intersects(player)) {
                    gameOver();
                    break;
                }
            }
            lasers.removeAll(lasersToRemove);

            List<Rectangle> coinsToRemove = new ArrayList<>();
            for (Rectangle coin : coins) {
                coin.y += COIN_SPEED;
                if (coin.y > WINDOW_HEIGHT) {
                    coinsToRemove.add(coin);
                }
                if (coin.intersects(player)) {
                    score++;
                    updateScore();
                    coinsToRemove.add(coin);
                }
            }
            coins.removeAll(coinsToRemove);
        }
        repaint();
    }

    private void gameOver() {
        timer.stop();
        coinTimer.stop();
        gameStarted = false;
        startButton.setVisible(true);
        restartButton.setVisible(true);
        JOptionPane.showMessageDialog(this, "Game Over! Your score was: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("LaserBash");
            LaserBash game = new LaserBash();
            frame.add(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}