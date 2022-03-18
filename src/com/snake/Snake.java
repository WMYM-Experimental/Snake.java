package com.snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {
    public Point location;
    public int direction = KeyEvent.VK_RIGHT; // default direction
    ArrayList<Point> snakeTail = new ArrayList<>();
    public int score = 0;

    public Snake(){
        this.location =  new Point();
    }

}
