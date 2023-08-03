# Snake Game

## Introduction

This is a simple Snake game implemented in Java using Swing. The player controls a snake on a grid-based board, and the goal is to eat apples that randomly appear on the board, making the snake grow longer. The game ends if the snake collides with itself or the board's boundaries.

## How to Play

- Use the arrow keys (Up, Down, Left, Right) to control the direction of the snake.
- The snake will move continuously in the direction specified by the player.
- The snake will grow in length whenever it eats an apple.
- The game ends if the snake collides with itself or the board's boundaries.

## Instructions

1. **Prerequisites**: Make sure you have Java installed on your system to run this game.

2. **Running the Game**: Execute the `GamePanel` class, which contains the main logic and GUI for the game. Upon running, a window will open displaying the game.

3. **Gameplay**: Use the arrow keys to control the direction of the snake. The snake will move continuously in the direction specified by the player.

4. **Scoring**: Each time the snake eats an apple, the player's score increases by one. The score will be displayed on the top of the game window.

5. **Game Over**: The game ends when the snake collides with itself or hits the board's boundaries. When the game is over, a "Game Over" message will be displayed along with the final score.

6. **Restart**: After the game ends, you can restart the game by running it again.

## Code Overview

- `GamePanel`: This class is the main game panel and extends `JPanel`. It handles the game logic, drawing the game components, and managing user input.

- `startGame()`: Initializes the game state, sets up the game timer, and starts the game.

- `paintComponent(Graphics g)`: Overrides the `paintComponent` method to draw the game components on the panel.

- `draw(Graphics g)`: Draws the snake, apples, and score on the game panel.

- `newApple()`: Generates a new apple at a random location on the screen.

- `move()`: Updates the position of the snake based on its current direction.

- `checkApple()`: Checks if the snake's head collides with an apple, and if so, increments the score and generates a new apple.

- `checkCollision()`: Checks for collisions with the snake's body or the boundaries of the game board and ends the game if a collision is detected.

- `gameOver(Graphics g)`: Displays the "Game Over" message and the final score when the game ends.

- `MyKeyAdapter`: A nested class that extends `KeyAdapter` and handles user keyboard input for controlling the snake's direction.

- `actionPerformed(ActionEvent e)`: Overrides the `actionPerformed` method to perform game logic on each timer tick.

## Customize the Game

You can customize various aspects of the game by modifying the following variables:

- `SCREEN_WIDTH`: Width of the game window (in pixels).
- `SCREEN_HEIGHT`: Height of the game window (in pixels).
- `UNIT_SIZE`: Size of each unit in the game grid (in pixels).
- `DELAY`: Delay (in milliseconds) between each timer tick, which controls the speed of the game.

Feel free to explore and make changes to the game code to enhance the gameplay or add additional features. Enjoy playing or experimenting with the Snake game!
