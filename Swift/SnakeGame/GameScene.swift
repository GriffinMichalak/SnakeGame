//
//  GameScene.swift
//  SnakeGame
//
//  Created by Griffin Michalak on 8/3/23.
//

import SpriteKit
import GameplayKit

class GameScene: SKScene {
    // Constants for the game
    let snakeSpeed: TimeInterval = 0.15
    let gridSize: CGFloat = 20.0
    
    // Snake properties
    var snake: [CGPoint] = []
    var snakeNode: SKShapeNode!
    var snakeDirection: CGPoint = .zero
    
    // Food properties
    var food: CGPoint = .zero
    var foodNode: SKShapeNode!
    
    override func didMove(to view: SKView) {
        setupGame()
    }
    
    func setupGame() {
        // Initialize snake at the center of the screen
        let initialPosition = CGPoint(x: size.width / 2, y: size.height / 2)
        snake.append(initialPosition)
        
        // Create snake node
        snakeNode = SKShapeNode(rectOf: CGSize(width: gridSize, height: gridSize))
        snakeNode.fillColor = .green
        snakeNode.position = initialPosition
        addChild(snakeNode)
        
        // Schedule update method to move the snake
        scheduleSnakeMovement()
        
        // Spawn initial food
        spawnFood()
    }
    
    func scheduleSnakeMovement() {
        let moveAction = SKAction.run {
            self.moveSnake()
        }
        let waitAction = SKAction.wait(forDuration: snakeSpeed)
        let sequence = SKAction.sequence([moveAction, waitAction])
        run(SKAction.repeatForever(sequence))
    }
    
    func moveSnake() {
        let head = snake[0]
        let newHead = CGPoint(x: head.x + snakeDirection.x * gridSize, y: head.y + snakeDirection.y * gridSize)
        
        if snake.contains(newHead) {
            // Snake collided with itself - game over
            gameOver()
            return
        }
        
        snake.insert(newHead, at: 0)
        
        if newHead == food {
            // Snake ate the food
            snakeNode.position = newHead
            spawnFood()
        } else {
            // Remove the tail segment
            snakeNode.position = snake.removeLast()
        }
    }
    
    func spawnFood() {
        food = CGPoint(x: CGFloat.random(in: gridSize...(size.width - gridSize)), y: CGFloat.random(in: gridSize...(size.height - gridSize)))
        
        if !snake.contains(food) {
            foodNode?.removeFromParent()
            foodNode = SKShapeNode(rectOf: CGSize(width: gridSize, height: gridSize))
            foodNode.fillColor = .red
            foodNode.position = food
            addChild(foodNode)
        } else {
            // If the food spawns on the snake, try again
            spawnFood()
        }
    }
    
    func gameOver() {
        // Implement game over logic here
        print("Game Over")
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            let touchLocation = touch.location(in: self)
            let head = snake[0]
            let diffX = touchLocation.x - head.x
            let diffY = touchLocation.y - head.y
            
            // Determine the new direction for the snake
            if abs(diffX) > abs(diffY) {
                snakeDirection = CGPoint(x: diffX > 0 ? 1 : -1, y: 0)
            } else {
                snakeDirection = CGPoint(x: 0, y: diffY > 0 ? -1 : 1)
            }
        }
    }

}
