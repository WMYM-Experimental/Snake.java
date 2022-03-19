package com.snake;

import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {
    final int GAME_DIM;
    final int TILE_DIM;
    final Snake snake;
    final Food food;
    final long fp;


    private final Painter gamePainter;

    public SnakeGame(Snake snake, Food food, int GAME_DIM, int TILE_DIM, long fp) {
        this.snake = snake;
        this.food = food;
        this.GAME_DIM = GAME_DIM;
        this.TILE_DIM = TILE_DIM;
        this.fp = fp;

        // set dimentions
        setTitle(String.format("SNAKE GAME => SCORE: %s", this.snake.score));
        setSize(GAME_DIM, GAME_DIM);
        setResizable(false);

        // centering game screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenDim.width / 2 - GAME_DIM / 2, screenDim.height / 2 - GAME_DIM / 2);

        // config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Controls controls = new Controls(this);
        this.addKeyListener(controls);

        startGame();

        gamePainter = new Painter(this);
        this.getContentPane().add(gamePainter);

        Frame frame = new Frame(this);
        Thread trd = new Thread(frame);
        trd.start();

        // visible
        setVisible(true);
    }

    public void startGame() {
        this.food.location = new Point(this.food.possibleLocations[(int) (Math.random() * (GAME_DIM / TILE_DIM + 1))], this.food.possibleLocations[(int) (Math.random() * (GAME_DIM / TILE_DIM + 1))]);
        this.snake.location = new Point(GAME_DIM / 2 + TILE_DIM / 2, GAME_DIM / 2 + TILE_DIM / 2);
        this.snake.snakeTail.add(this.snake.location);
    }

    public void putFood() {
        this.snake.snakeTail.add(0, new Point(this.snake.location));
        this.snake.snakeTail.remove(this.snake.snakeTail.size() - 1);

        if (this.food.location.x == this.snake.location.x && this.food.location.y == this.snake.location.y) {
            this.food.location = new Point(this.food.possibleLocations[(int) (Math.random() * (this.GAME_DIM / this.TILE_DIM + 1))], this.food.possibleLocations[(int) (Math.random() * (this.GAME_DIM / this.TILE_DIM + 1))]);

            this.snake.score += 1;
            this.setTitle(String.format("SNAKE GAME => SCORE : %s", this.snake.score));

            this.snake.snakeTail.add(0, new Point(snake.location.x, snake.location.y));
        }
    }

    public void refresh() {
        putFood();
        this.snake.isAlive();
        if (!this.snake.isLiving) {
            JOptionPane.showMessageDialog(null,
                    String.format("YOUR SCORE IS: %s", this.snake.score),
                    "GAME OVER!",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(this.snake.score);
        }
        gamePainter.repaint();
    }
}
