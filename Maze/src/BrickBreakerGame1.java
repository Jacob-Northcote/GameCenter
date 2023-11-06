import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreakerGame1 extends JPanel implements KeyListener, ActionListener {
    private int paddleX = 250;
    private int ballX = 300;
    private int ballY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private int[][] bricks;
    private Timer timer;
    private int score = 0;
    private boolean gameOver = false;

    public BrickBreakerGame1() {
        bricks = new int[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks[i][j] = 1;
            }
        }

        timer = new Timer(5, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

      
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (bricks[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * 60 + 20, i * 30 + 50, 50, 20);
                }
            }
        }

    
        g.setColor(Color.RED);
        g.fillRect(paddleX, 550, 100, 8);

        
        g.setColor(Color.GREEN);
        g.fillOval(ballX, ballY, 20, 20);

       
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 20, 30);

        if (gameOver) {
           
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 250, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to replay", 280, 350);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            timer.start();
            ballX += ballXDir;
            ballY += ballYDir;

            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    if (bricks[i][j] == 1) {
                        Rectangle brickRect = new Rectangle(j * 60 + 20, i * 30 + 50, 50, 20);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            bricks[i][j] = 0;
                            ballYDir = -ballYDir;
                            score += 10;
                        }
                    }
                }
            }

            
            Rectangle paddleRect = new Rectangle(paddleX, 550, 100, 8);
            Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);

            if (ballRect.intersects(paddleRect)) {
                ballYDir = -ballYDir;
            }

           
            if (ballX <= 0 || ballX >= 780) {
                ballXDir = -ballXDir;
            }
            if (ballY <= 0) {
                ballYDir = -ballYDir;
            }

          
            if (ballY >= 570) {
                gameOver = true;
            }
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < 700) {
                paddleX += 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
                paddleX -= 20;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_R) {
               
                ballX = 300;
                ballY = 350;
                ballXDir = -1;
                ballYDir = -2;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 10; j++) {
                        bricks[i][j] = 1;
                    }
                }
                score = 0;
                gameOver = false;
            }
        }
    }

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreakerGame1 game = new BrickBreakerGame1();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }
}