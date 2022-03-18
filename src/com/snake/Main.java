package com.snake;

public class Main {

    public static void main(String[] args) {
        final int GAME_DIM = 700;
        final int TILE_DIM = 20;
        long fp = 50;

        Snake snake = new Snake();
        Food food = new Food();
        SnakeGame s = new SnakeGame(snake, food, GAME_DIM, TILE_DIM, fp);

    }
}
