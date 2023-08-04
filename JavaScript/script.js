const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const boxSize = 20;
const canvasSize = 400;
const maxSpeed = 200; // Adjust this for the speed of the snake

let snake = [{ x: 200, y: 200 }];
let food = { x: 0, y: 0 };
let direction = 'right';
let changingDirection = false;
let score = 0;

function generateFood() {
  food.x = boxSize * Math.floor(Math.random() * (canvasSize / boxSize));
  food.y = boxSize * Math.floor(Math.random() * (canvasSize / boxSize));

  for (const segment of snake) {
    if (segment.x === food.x && segment.y === food.y) {
      return generateFood();
    }
  }
}

function draw() {
  ctx.clearRect(0, 0, canvasSize, canvasSize);
  
  for (const segment of snake) {
    ctx.fillStyle = '#4CAF50';
    ctx.fillRect(segment.x, segment.y, boxSize, boxSize);
  }

  ctx.fillStyle = '#FF0000';
  ctx.fillRect(food.x, food.y, boxSize, boxSize);

  ctx.fillStyle = '#333';
  ctx.font = '20px Arial';
  ctx.fillText(`Score: ${score}`, 20, 30);
}

function moveSnake() {
  const head = { ...snake[0] };

  switch (direction) {
    case 'up':
      head.y -= boxSize;
      break;
    case 'down':
      head.y += boxSize;
      break;
    case 'left':
      head.x -= boxSize;
      break;
    case 'right':
      head.x += boxSize;
      break;
  }

  snake.unshift(head);

  if (head.x === food.x && head.y === food.y) {
    score += 10;
    generateFood();
  } else {
    snake.pop();
  }
}

function checkCollision() {
  const head = snake[0];

  if (
    head.x < 0 ||
    head.x >= canvasSize ||
    head.y < 0 ||
    head.y >= canvasSize ||
    snake.some((segment, index) => index !== 0 && segment.x === head.x && segment.y === head.y)
  ) {
    clearInterval(gameLoop);
    alert('Game Over! Your score: ' + score);
  }
}

function changeDirection(event) {
  if (!changingDirection) {
    changingDirection = true;

    const key = event.keyCode;
    const keyPressed = event.key;

    if (key === 37 && direction !== 'right') {
      direction = 'left';
    } else if (key === 38 && direction !== 'down') {
      direction = 'up';
    } else if (key === 39 && direction !== 'left') {
      direction = 'right';
    } else if (key === 40 && direction !== 'up') {
      direction = 'down';
    } else if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(keyPressed)) {
      event.preventDefault();
    }
  }
}

document.addEventListener('keydown', changeDirection);

generateFood();
let gameLoop = setInterval(() => {
  changingDirection = false;
  moveSnake();
  checkCollision();
  draw();
}, maxSpeed);
