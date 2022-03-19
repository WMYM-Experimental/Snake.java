package com.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controls extends KeyAdapter {
    private final SnakeGame sg;

    public Controls(SnakeGame sg) {
        this.sg = sg;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (this.sg.snake.direction != KeyEvent.VK_DOWN) {
                this.sg.snake.direction = KeyEvent.VK_UP;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (this.sg.snake.direction != KeyEvent.VK_UP) {
                this.sg.snake.direction = KeyEvent.VK_DOWN;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.sg.snake.direction != KeyEvent.VK_RIGHT) {
                this.sg.snake.direction = KeyEvent.VK_LEFT;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.sg.snake.direction != KeyEvent.VK_LEFT) {
                this.sg.snake.direction = KeyEvent.VK_RIGHT;
            }
        }
    }
}