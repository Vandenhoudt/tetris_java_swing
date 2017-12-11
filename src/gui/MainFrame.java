package gui;

import java.util.Random;
import javax.swing.*;

import block.Block;
import block.Field;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MainFrame extends JFrame {
	private JButton[][] buttons = new JButton[10][22];
        /**this is the default button size*/
	public static final int BUTTON_SIZE = 30;
	private Block[] blocks = Block.values();
	private Field field;
	
        @SuppressWarnings("SleepWhileInLoop")
	public MainFrame () {
		super("tetris");
		setSize(100,100);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initButtons();
		setSize(500,900);
		this.field = new Field(this.buttons);
                InputEvent input = new InputEvent(this.field);
		setVisible(true);
                
                playSound();
		
		while (this.field.checkStartPlace()) {
                    this.field.addBlock(this.blocks[new Random(System.currentTimeMillis()).nextInt(blocks.length)]);
			while (this.field.isMoving()) {
                            this.addKeyListener(input);
				try {
				    Thread.sleep(1000 - (this.field.getScore() / 3));
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
                                this.removeKeyListener(input);
				this.field.moveDown();
			}
		}
                
                JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("DED");
		frame.add(new JLabel("you lose"));
		frame.setSize(100, 100);
	}

	private void initButtons() {
		for (int i = 0; i < this.buttons.length; i++) {
			for (int ii = 0; ii < this.buttons[i].length; ii++) {
				this.buttons[i][ii] = new JButton();
				this.buttons[i][ii].setSize(BUTTON_SIZE,BUTTON_SIZE);
				this.buttons[i][ii].setEnabled(false);
				this.buttons[i][ii].setBackground(null);
				this.buttons[i][ii].setLocation(i * BUTTON_SIZE + 100, ii * BUTTON_SIZE + 100);
				add(this.buttons[i][ii]);
			}
		}		
	}
        
        @SuppressWarnings("Convert2Lambda")
        private void playSound() {
            new Thread(new Runnable() {
                @Override
                public void run () {
                    //** add this into your application code as appropriate
                    // Open an input stream  to the audio file.
                    InputStream in;
                    while (true) {
                        try {
                            in = new FileInputStream(".//src//tetris.wav");
                            AudioStream as = new AudioStream(in);  
                            AudioPlayer.player.start(as);   
                                try {
                                    Thread.sleep(82000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            AudioPlayer.player.stop(as); 
                        }catch (FileNotFoundException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        }
}
