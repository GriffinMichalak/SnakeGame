import javax.swing.*;

import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

  static final int SCREEN_WIDTH = 600;
  static final int SCREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;
  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
  static final int DELAY = 75;
  final int x[] = new int[GAME_UNITS];
  final int y[] = new int[GAME_UNITS];
  int bodyParts = 6;
  int applesEaten = 0;
  int appleX;
  int appleY;
  char direction = 'R';
  boolean isRunning = false;
  Timer timer;
  Random random;

  GamePanel() {
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
  }


  public void startGame() {
    newApple();
    isRunning = true;
    timer = new Timer(DELAY, this);
    timer.start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponents(g);
    draw(g);
  }

  public void draw(Graphics g) {
    if(isRunning) {
      g.setColor(Color.red);
      g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

      for(int i = 0; i < bodyParts; i++) {
        if(i == 0) { //the head of the snake
          g.setColor(Color.green);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        else { //the body of the snake
          g.setColor(new Color(45, 180, 0));
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }

      //Displays Current Score
      g.setColor(Color.red);
      g.setFont(new Font("Calibri", Font.BOLD, 30));
      FontMetrics fontMetrics = getFontMetrics(g.getFont());
      g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
    }
    else {
      gameOver(g);
    }
  }

  /**
   * Generates a new apple at a random location on the screen.
   */
  public void newApple() {
    appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
    appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
  }

  public void move() {
    for(int i = bodyParts; i > 0; i--) {
      x[i] = x[i - 1];
      y[i] = y[i - 1];
    }

    switch (direction) {
      case 'U':
        y[0] = y[0] - UNIT_SIZE;
        break;
      case 'D':
        y[0] = y[0] + UNIT_SIZE;
        break;
      case 'L':
        x[0] = x[0] - UNIT_SIZE;
        break;
      case 'R':
        x[0] = x[0] + UNIT_SIZE;
    }
  }

  public void checkApple() {
    if(x[0] == appleX && y[0] == appleY) {
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }

  public void checkCollision() {
    //checks if the Snake's head collides with its body.
    for(int i = bodyParts; i > 0; i--) {
      if((x[0] == x[i]) && y[0] == y[i]) {
        isRunning = false;
      }
    }

    //checks if the Snake's head checks with the left border
    if(x[0] < 0) {
      isRunning = false;
    }

    //checks if the Snake's head checks with the right border
    if(x[0] > SCREEN_WIDTH) {
      isRunning = false;
    }

    //checks if the Snake's head checks with the top border
    if(y[0] < 0) {
      isRunning = false;
    }

    //checks if the Snake's head checks with the bottom border
    if(y[0] > SCREEN_HEIGHT) {
      isRunning = false;
    }

    if(!isRunning) {
      timer.stop();
    }


  }

  /**
   * Displays "Game Over" on the screen after the game ends.
   * @param g
   */
  public void gameOver(Graphics g) {
    //Displays Score Text
    g.setColor(Color.red);
    g.setFont(new Font("Calibri", Font.BOLD, 30));
    FontMetrics scoreFontMetrics = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - scoreFontMetrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

    //Displays Game Over Text
    g.setColor(Color.red);
    g.setFont(new Font("Calibri", Font.BOLD, 75));
    FontMetrics gameOverFontMetrics = getFontMetrics(g.getFont());
    g.drawString("Game Over", (SCREEN_WIDTH - gameOverFontMetrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
  }

  public class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      switch(e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          if(direction != 'R') {
            direction = 'L';
          }
          break;

        case KeyEvent.VK_RIGHT:
          if(direction != 'L') {
            direction = 'R';
          }
          break;

        case KeyEvent.VK_UP:
          if(direction != 'D') {
            direction = 'U';
          }
          break;

        case KeyEvent.VK_DOWN:
          if(direction != 'U') {
            direction = 'D';
          }
          break;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if(isRunning) {
      move();
      checkApple();
      checkCollision();
    }

    repaint();
  }

}
