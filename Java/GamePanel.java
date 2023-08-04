import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The GamePanel class represents the main game panel where the Snake game is played.
 */
public class GamePanel extends JPanel implements ActionListener {

  // Constants for screen dimensions and unit size
  static final int SCREEN_WIDTH = 600;
  static final int SCREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;
  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
  static final int DELAY = 75;

  // Arrays to store the positions of the snake's body parts
  final int x[] = new int[GAME_UNITS];
  final int y[] = new int[GAME_UNITS];

  // Variables to keep track of game state
  int bodyParts = 6;
  int applesEaten = 0;
  int appleX;
  int appleY;
  Direction direction = Direction.RIGHT;
  boolean isRunning = false;
  Timer timer;
  Random random;

  /**
   * Constructs a new GamePanel object.
   * Initializes the game panel, sets up the key listener, and starts the game.
   */
  GamePanel() {
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
  }

  /**
   * Starts the game by setting up the initial state, generating the first apple, and starting the game timer.
   */
  public void startGame() {
    newApple();
    isRunning = true;
    timer = new Timer(DELAY, this);
    timer.start();
  }

  /**
   * Draws the game components on the panel.
   *
   * @param g The Graphics object to draw on.
   */
  public void paintComponent(Graphics g) {
    super.paintComponents(g);
    draw(g);
  }

  /**
   * Draws the snake, apples, and score on the game panel.
   *
   * @param g The Graphics object to draw on.
   */
  public void draw(Graphics g) {
    if (isRunning) {
      g.setColor(Color.red);
      g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

      for (int i = 0; i < bodyParts; i++) {
        if (i == 0) { //the head of the snake
          g.setColor(Color.green);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        } else { //the body of the snake
          g.setColor(new Color(45, 180, 0));
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }

      // Displays Current Score
      g.setColor(Color.red);
      g.setFont(new Font("Calibri", Font.BOLD, 30));
      FontMetrics fontMetrics = getFontMetrics(g.getFont());
      g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    } else {
      gameOver(g);
    }
  }

  /**
   * Generates a new apple at a random location on the screen.
   */
  public void newApple() {
    appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
  }

  /**
   * Updates the position of the snake based on its current direction.
   */
  public void move() {
    for (int i = bodyParts; i > 0; i--) {
      x[i] = x[i - 1];
      y[i] = y[i - 1];
    }

    switch (direction) {
      case UP:
        y[0] = y[0] - UNIT_SIZE;
        break;
      case DOWN:
        y[0] = y[0] + UNIT_SIZE;
        break;
      case LEFT:
        x[0] = x[0] - UNIT_SIZE;
        break;
      case RIGHT:
        x[0] = x[0] + UNIT_SIZE;
    }
  }

  /**
   * Checks if the snake's head collides with an apple and updates the score accordingly.
   */
  public void checkApple() {
    if (x[0] == appleX && y[0] == appleY) {
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }

  /**
   * Checks for collisions with the snake's body or the boundaries of the game board and ends the game if a collision is detected.
   */
  public void checkCollision() {
    // checks if the Snake's head collides with its body.
    for (int i = bodyParts; i > 0; i--) {
      if ((x[0] == x[i]) && y[0] == y[i]) {
        isRunning = false;
      }
    }

    // checks if the Snake's head checks with the left border
    if (x[0] < 0) {
      isRunning = false;
    }

    // checks if the Snake's head checks with the right border
    if (x[0] >= SCREEN_WIDTH) {
      isRunning = false;
    }

    // checks if the Snake's head checks with the top border
    if (y[0] < 0) {
      isRunning = false;
    }

    // checks if the Snake's head checks with the bottom border
    if (y[0] >= SCREEN_HEIGHT) {
      isRunning = false;
    }

    if (!isRunning) {
      timer.stop();
    }
  }

  /**
   * Displays the "Game Over" message and the final score when the game ends.
   *
   * @param g The Graphics object to draw on.
   */
  public void gameOver(Graphics g) {
    // Displays Score Text
    g.setColor(Color.red);
    g.setFont(new Font("Calibri", Font.BOLD, 30));
    FontMetrics scoreFontMetrics = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - scoreFontMetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

    // Displays Game Over Text
    g.setColor(Color.red);
    g.setFont(new Font("Calibri", Font.BOLD, 75));
    FontMetrics gameOverFontMetrics = getFontMetrics(g.getFont());
    g.drawString("Game Over", (SCREEN_WIDTH - gameOverFontMetrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
  }

  /**
   * Inner class to handle user keyboard input for controlling the snake's direction.
   */
  public class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          if (direction != Direction.RIGHT) {
            direction = Direction.LEFT;
          }
          break;

        case KeyEvent.VK_RIGHT:
          if (direction != Direction.LEFT) {
            direction = Direction.RIGHT;
          }
          break;

        case KeyEvent.VK_UP:
          if (direction != Direction.DOWN) {
            direction = Direction.UP;
          }
          break;

        case KeyEvent.VK_DOWN:
          if (direction != Direction.UP) {
            direction = Direction.DOWN;
          }
          break;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (isRunning) {
      move();
      checkApple();
      checkCollision();
    }
    repaint();
  }
}
