package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class SnakeGame extends JFrame {
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 700;

    private static final int TILE_WIDTH = 20;
    private static final int TILE_HEIGHT = 20;

    private int score = 0;

    private long fp = 50;
    private int direction = KeyEvent.VK_RIGHT; // default direction

    private Point snake;
    private Painter snakeFace;
    private Point food;
    private static final int[] possibleLocations = new int[(SCREEN_WIDTH/TILE_WIDTH)+1];
    ArrayList<Point> snakeTail = new ArrayList<>();

    public SnakeGame(){
        // set dimentions
        setTitle("~ SNAKE GAME ~");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);

        // centering game screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenDim.width/2 - SCREEN_WIDTH/2, screenDim.height/2 - SCREEN_HEIGHT/2);

        // config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Controls controls = new Controls();
        this.addKeyListener(controls);

        startGame();

        snakeFace = new Painter();
        this.getContentPane().add(snakeFace);

        Frame frame = new Frame();
        Thread trd = new Thread(frame);
        trd.start();

        // visible
        setVisible(true);
    }

    public void startGame(){
        for (int i = 0; i < SCREEN_WIDTH/TILE_WIDTH - 2; i++) {
            possibleLocations[i] = 20*i;
        }
        System.out.println(Arrays.toString(possibleLocations));
        food = new Point(possibleLocations[(int)(Math.random()*(SCREEN_WIDTH/TILE_WIDTH + 1))], possibleLocations[(int)(Math.random()*(SCREEN_WIDTH/TILE_WIDTH + 1))]);
        snake = new Point(SCREEN_WIDTH/2 + TILE_WIDTH/2 ,SCREEN_HEIGHT/2 + TILE_HEIGHT/2);
        snakeTail.add(snake);
    }

    public void putFood(){

        snakeTail.add(0,new Point(snake.x, snake.y));
        snakeTail.remove(snakeTail.size()-1);

        if (food.x == snake.x && food.y == snake.y) {
            food.x = possibleLocations[(int) (Math.random() * (SCREEN_WIDTH / TILE_WIDTH + 1))];
            food.y = possibleLocations[(int) (Math.random() * (SCREEN_WIDTH / TILE_WIDTH + 1))];
            score += 1;
            snakeTail.add(0,new Point(snake.x, snake.y));
        }
    }

    public void refresh(){
        putFood();
        snakeFace.repaint();
    }


    public class Painter extends JPanel {
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            // draw each point od the snake tail
            g.setColor(new Color(23,255,49));
            for (Point point : snakeTail) {
                g.fillRect(point.x, point.y, TILE_WIDTH, TILE_HEIGHT);
            }

            g.setColor(new Color(128,0,0));
            g.fillRect(food.x, food.y, TILE_WIDTH,TILE_HEIGHT);

            // draw grid
            for (int i = 0; i < SCREEN_WIDTH; i = i + TILE_WIDTH) {
                for (int j = 0; j < SCREEN_HEIGHT; j = j + TILE_HEIGHT) {
                    g.setColor(new Color(25,18,21));
                    g.drawRect(0, 0, i, j);
                }
            }
        }
    }

    public class Controls extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }else if (e.getKeyCode() == KeyEvent.VK_UP){
                if(direction != KeyEvent.VK_DOWN){
                    direction = KeyEvent.VK_UP;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if(direction != KeyEvent.VK_UP){
                    direction = KeyEvent.VK_DOWN;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                if(direction != KeyEvent.VK_RIGHT){
                    direction = KeyEvent.VK_LEFT;
                }
            }else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                if(direction != KeyEvent.VK_LEFT){
                    direction = KeyEvent.VK_RIGHT;
                }
            }
        }
    }

    public class Frame extends Thread{
        long last = 0;
        public void run(){
            while(true){
                if((System.currentTimeMillis() - last) > fp){
                    if (direction == KeyEvent.VK_UP){
                        snake.y = snake.y - TILE_HEIGHT;
                        if (snake.y < 0 ){
                            snake.y = SCREEN_HEIGHT - 2 * TILE_HEIGHT;
                        }
                    }else if(direction == KeyEvent.VK_DOWN){
                        snake.y = snake.y + TILE_HEIGHT;
                        if (snake.y > SCREEN_HEIGHT - 2 * TILE_HEIGHT){
                            snake.y = 0;
                        }
                    }else if(direction == KeyEvent.VK_LEFT){
                        snake.x = snake.x - TILE_HEIGHT;
                        if (snake.x < 0 ){
                            snake.x = SCREEN_WIDTH - 2 * TILE_WIDTH;
                        }
                    }else if(direction == KeyEvent.VK_RIGHT){
                        snake.x = snake.x + TILE_WIDTH;
                        if (snake.x > SCREEN_WIDTH - 2 * TILE_WIDTH){
                            snake.x = 0;
                        }
                    }else{
                        break;
                    }
                    refresh();
                    last = System.currentTimeMillis();
                }
            }
        }

    }


}
