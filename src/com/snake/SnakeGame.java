package com.snake;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame {

    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 700;

    private static final int TILE_WIDTH = 20;
    private static final int TILE_HEIGHT = 20;

    Point snake;
    Painter snakeFace;
    long fp = 100;

    int direction = KeyEvent.VK_RIGHT; // default direction

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

        snake = new Point(SCREEN_WIDTH/2 + TILE_WIDTH/2 ,SCREEN_HEIGHT/2 + TILE_HEIGHT/2);
        snakeFace = new Painter();
        this.getContentPane().add(snakeFace);

        Frame frame = new Frame();
        Thread trd = new Thread(frame);
        trd.start();
        // visible
        setVisible(true);
    }

    public void refresh(){
        snakeFace.repaint();
    }

    public class Painter extends JPanel {
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(95,119,9));
            g.fillRect(snake.x, snake.y, TILE_WIDTH,TILE_HEIGHT);
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
