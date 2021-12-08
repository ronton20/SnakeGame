import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;

public class MyPanel extends JPanel 
{
	public static final int UP = KeyEvent.VK_UP;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	private int direction;
	
	private Apple apple;
	private boolean appleEaten;
	private Color color;
	
	private ArrayList<Coordinates> coords;
	private Coordinates head;
	private int snakeSize;
	private int counter;
	
	private JLabel headPic;
	private JLabel booger;
	
	private final int BLOCK_SIZE = 15;
	private final int STARTING_SIZE = 4;
	
	private final int DELAY = 100;
	private Timer t ;
	private int rows;
	private int cols;
	
	private JLabel lblCounter;
	private JLabel lblEnd;
	
	private final String SNAKE_HEAD_PATH = "SnakeHead.png";
	private final String APPLE_PATH = "booger.png";
	private final int APPLE_SIZE = BLOCK_SIZE;
	
	public MyPanel() {
		color = Color.GREEN;
		head = new Coordinates(5 * BLOCK_SIZE, 5 * BLOCK_SIZE);
		coords = new ArrayList<>();
		coords.add(head);
		direction = RIGHT;
		snakeSize = STARTING_SIZE;
		int randX = (int)(Math.random() * this.cols);
		int randY = (int)(Math.random() * this.rows);
		this.apple = new Apple(randX * BLOCK_SIZE, randY * BLOCK_SIZE);
		
		createApple();
		appleEaten = true;
		
		counter = 0;
		lblCounter = new JLabel("Your Score: " + counter);

		for(int i = 1; i < snakeSize; i++)
			coords.add(new Coordinates(head.getX() - i * BLOCK_SIZE, head.getY()));
		
		t = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				move();
				repaint();
				for(Coordinates coord: coords) {
					if(!coord.equals(head)) {
						if(head.getX() == coord.getX() && head.getY() == coord.getY()) {
							color = Color.RED;
							t.stop();
							gameOver();
						}
					}
				}
			}
		});
		
		
		t.start();
	}
	
	public void setDirection(int dir) {
		this.direction = dir;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	private void createApple() {
		int randX = (int)(Math.random() * this.cols);
		int randY = (int)(Math.random() * this.rows);
		this.apple = new Apple(randX * BLOCK_SIZE, randY * BLOCK_SIZE);
		
		appleEaten = false;
	}
	
	private void move() {
		switch(direction) {
		case UP: 
			if(head.getY() > 1)
				head = new Coordinates(head.getX(), head.getY() - BLOCK_SIZE);
			else
				head = new Coordinates(head.getX(), this.rows * BLOCK_SIZE - BLOCK_SIZE);
			coords.add(0, head);
			break;
			
		case DOWN: 
			if(head.getY() < rows * BLOCK_SIZE - BLOCK_SIZE)
				head = new Coordinates(head.getX(), head.getY() + BLOCK_SIZE);
			else
				head = new Coordinates(head.getX(), 0);
			coords.add(0, head);
			break;
			
		case LEFT: 
			if(head.getX() > 0)
				head = new Coordinates(head.getX() - BLOCK_SIZE, head.getY());
			else
				head = new Coordinates(this.cols * BLOCK_SIZE - BLOCK_SIZE, head.getY());
			coords.add(0, head);
			break;
			
		case RIGHT: 
			if(head.getX() < cols * BLOCK_SIZE - BLOCK_SIZE)
				head = new Coordinates(head.getX() + BLOCK_SIZE, head.getY());
			else
				head = new Coordinates(0, head.getY());
			coords.add(0, head);
			break;
		}
		if(coords.size() > snakeSize)
			coords.remove(snakeSize);

	}
	
	private void drawApple(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(apple.getX(), apple.getY(), BLOCK_SIZE, BLOCK_SIZE);
	}
	
	private void gameOver() {
		int lblWidth = this.getWidth() / 4;
		int lblHeight = this.getHeight() / 4;
		lblEnd = new JLabel("GAME OVER");
		lblEnd.setBounds((this.getWidth() / 2) - (lblWidth / 2), (this.getHeight() / 2) - (lblHeight / 2), lblWidth ,lblHeight);
		lblEnd.setForeground(Color.RED);
		add(lblEnd);
		repaint();
	}
	
	private void counter() {
		int lblWidth = this.getWidth() / 5;
		int lblHeight = this.getHeight() / 20;
		lblCounter.setBounds(this.getWidth() / 2 - lblWidth / 2, 2, lblWidth, lblHeight);
		lblCounter.setForeground(Color.WHITE);
		lblCounter.setText("Your Score: " + counter);
		add(lblCounter);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		this.rows = this.getHeight() / BLOCK_SIZE;
		this.cols = this.getWidth() / BLOCK_SIZE;
		
		super.paintComponent(g);
		super.setBackground(Color.BLACK);
		for(Coordinates c: coords) {
			g.setColor(color);
			g.fillRect(c.getX(), c.getY(), BLOCK_SIZE, BLOCK_SIZE);
		}
		
		g.setColor(Color.BLACK);
		for(int row = 0; row < rows; row++)
			g.drawLine(0, row * BLOCK_SIZE, getWidth(), row * BLOCK_SIZE);
		for(int col = 0; col < cols; col++)
			g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, getHeight());
		
		drawApple(g);
		//checking if you eat an apple
		if(head.getX() == apple.getX() && head.getY() == apple.getY())	{
			snakeSize++;
			counter++;
			createApple();
		}
		
		counter();
		
	}
	
}
