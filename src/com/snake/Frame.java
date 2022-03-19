package com.snake;

import java.awt.event.KeyEvent;

public class Frame extends Thread {
    private final SnakeGame sg;
    long last = 0;

    public Frame(SnakeGame sg) {
        this.sg = sg;
    }

    public void run() {
        while (this.sg.snake.isLiving) {
            if ((System.currentTimeMillis() - last) > this.sg.fp) {
                if (this.sg.snake.direction == KeyEvent.VK_UP) {
                    this.sg.snake.location.y = this.sg.snake.location.y - this.sg.TILE_DIM;
                    if (this.sg.snake.location.y < 0) {
                        this.sg.snake.location.y = this.sg.GAME_DIM - 3 * this.sg.TILE_DIM;
                    }
                } else if (this.sg.snake.direction == KeyEvent.VK_DOWN) {
                    this.sg.snake.location.y = this.sg.snake.location.y + this.sg.TILE_DIM;
                    if (this.sg.snake.location.y > this.sg.GAME_DIM - 3 * this.sg.TILE_DIM) {
                        this.sg.snake.location.y = 0;
                    }
                } else if (this.sg.snake.direction == KeyEvent.VK_LEFT) {
                    this.sg.snake.location.x = this.sg.snake.location.x - this.sg.TILE_DIM;
                    if (this.sg.snake.location.x < 0) {
                        this.sg.snake.location.x = this.sg.GAME_DIM - 2 * this.sg.TILE_DIM;
                    }
                } else if (this.sg.snake.direction == KeyEvent.VK_RIGHT) {
                    this.sg.snake.location.x = this.sg.snake.location.x + this.sg.TILE_DIM;
                    if (this.sg.snake.location.x > this.sg.GAME_DIM - 2 * this.sg.TILE_DIM) {
                        this.sg.snake.location.x = 0;
                    }
                } else {
                    break;
                }
                this.sg.refresh();
                last = System.currentTimeMillis();
            }
        }
    }
}