package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame {
    private final int GAME_DIM;
    private final int TILE_DIM;
    private final Snake snake;
    private final Food food;
    private final long fp;


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


    public static class Painter extends JPanel {
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

    public static class Controls extends KeyAdapter {
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

    public class Frame extends Thread {
        private final SnakeGame sg;
        long last = 0;

        public Frame(SnakeGame sg) {
            this.sg = sg;
        }

        public void run() {
            while (this.sg.snake.isLiving) {
                if ((System.currentTimeMillis() - last) > fp) {
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
                    refresh();
                    last = System.currentTimeMillis();
                }
            }
        }

    }
}
