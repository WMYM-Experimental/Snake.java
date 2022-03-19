package com.snake;

import javax.swing.*;
import java.awt.*;

public class Painter extends JPanel {
    SnakeGame sg;

    public Painter(SnakeGame sg) {
        this.sg = sg;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw each point od the snake tail
        for (Point point : this.sg.snake.snakeTail) {
            g.setColor(new Color(23, 255, 49));
            if (point == this.sg.snake.snakeTail.get(0)) {
                g.setColor(new Color(2, 118, 19));
            }
            g.fillRect(point.x, point.y, this.sg.TILE_DIM, this.sg.TILE_DIM);
        }

        g.setColor(new Color(128, 0, 0));
        g.fillRect(this.sg.food.location.x, this.sg.food.location.y, this.sg.TILE_DIM, this.sg.TILE_DIM);

        // draw grid
        for (int i = 0; i < this.sg.GAME_DIM; i = i + this.sg.TILE_DIM) {
            for (int j = 0; j < this.sg.GAME_DIM; j = j + this.sg.TILE_DIM) {
                g.setColor(new Color(25, 18, 21));
                g.drawRect(0, 0, i, j);
            }
        }
    }
}