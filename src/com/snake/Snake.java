package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {
    public Point location;
    public int direction = KeyEvent.VK_RIGHT; // default direction
    public boolean isLiving = true;
    ArrayList<Point> snakeTail = new ArrayList<>();
    public int score = 0;

    public Snake() {
        this.location = new Point();
    }

    public boolean isAlive() {
        for (int i = 2; i < this.snakeTail.size(); i++) {
            if (this.snakeTail.get(i).x == this.location.x && this.snakeTail.get(i).y == this.location.y) {
                isLiving = false;
                return isLiving;
            }
        }
        return isLiving;
    }

}
