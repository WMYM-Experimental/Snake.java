package com.snake;

import java.awt.*;

public class Food {
    public Point location;
    public final int[] possibleLocations = new int[35+1];

    public Food(){
        for (int i = 0; i < 35 - 2; i++) {
            possibleLocations[i] = 20*i;
        }
        this.location = new Point();
    }
}
