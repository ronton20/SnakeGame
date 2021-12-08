import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final int WINDOW_SIZE = 600;
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
		frame.setResizable(false);
		
		MyPanel p = new MyPanel();
		frame.add(p);
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();

			    if (key == KeyEvent.VK_LEFT && p.getDirection() != p.RIGHT)
			        p.setDirection(MyPanel.LEFT);

			    else if (key == KeyEvent.VK_RIGHT && p.getDirection() != p.LEFT)
			        p.setDirection(KeyEvent.VK_RIGHT);

			    else if (key == KeyEvent.VK_UP && p.getDirection() != p.DOWN)
			        p.setDirection(KeyEvent.VK_UP);

			    else if (key == KeyEvent.VK_DOWN && p.getDirection() != p.UP)
			        p.setDirection(KeyEvent.VK_DOWN);

			}
		});
		
		frame.setVisible(true);
	}

}
