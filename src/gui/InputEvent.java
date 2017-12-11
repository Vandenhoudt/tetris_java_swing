package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import block.Field;

public class InputEvent implements KeyListener{
	
	private final Field field;
	private static boolean pressedUp;
	private static boolean pressedDown;
	private static boolean pressedRight;
	private static boolean pressedLeft;
	
	public InputEvent (Field field) {
		this.field = field;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()){
			case KeyEvent.VK_DOWN:
				if (!pressedDown) {
					pressedDown = true;
					this.field.moveDown();
				}
				break;
			case KeyEvent.VK_LEFT:
				if (!pressedLeft) {
					pressedLeft = true;
					this.field.moveLeft();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!pressedRight) {
					pressedRight = true;
					this.field.moveRight();
				}
				break;
			case KeyEvent.VK_UP:
				if (!pressedUp) {
					pressedUp = true;
					this.field.rotateLeft();
				}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			pressedDown = false;
			break;
		case KeyEvent.VK_LEFT:
			pressedLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			pressedRight = false;
			break;
		case KeyEvent.VK_UP:
			pressedUp = false;
		break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	

}
