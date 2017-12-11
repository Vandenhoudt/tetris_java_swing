package block;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import javax.swing.*;

public class Field {
	private Block block;
	private Point[] coordinates;
	private Color color;
	private boolean moving = false;
	private JButton[][] buttons;
	private int numberOfTetris = 1000;
	
	public Field (JButton[][] field) {
		this.buttons = field;
	}
	
	public void addBlock (Block block) {
		if (!moving) {
			this.moving = true;
			Random generator = new Random(System.currentTimeMillis());
			this.block = block;
                        this.setCoordinates(this.block.getPoints(), 2);
                        for (Point i : this.coordinates) {
                                i.setLocation(i.getX() + 4, i.getY());
                        }
                        this.color = new Color(generator.nextInt(200),generator.nextInt(200),generator.nextInt(200));
                        for (int i = 0; i < this.coordinates.length; i++) {
                               this.buttons[(int)this.coordinates[i].getX()][(int)this.coordinates[i].getY()].setBackground(this.color);
                        }
		}
	}
        
        public boolean checkStartPlace() {
            Color defualtColor = new JButton().getBackground();
            
            for (int i = 3; i < 7; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (!(this.buttons[i][ii].getBackground().equals(defualtColor))) {
                        return false;
                    }
                }
            }
            
            return true;
        }
	
	public Point[] getCoordinates() {
		return this.coordinates.clone();
	}
	
	public boolean isMoving() {
		return this.moving;
	}
	
	public int getScore () {
		return this.numberOfTetris;
	}
        
        public JButton[][] getButtons() {
            return this.buttons;
        }
	
	public synchronized void rotateLeft(){
	    Point[] rotatedCoordinates = new Point[4];
	    Point origin = this.coordinates[1];
	    if (this.moving) {
		    try {
		    for(int i = 0; i < rotatedCoordinates.length; i++){
		    	
		        // Translates current coordinate to be relative to (0,0)
		        Point translationCoordinate = new Point(coordinates[i].x - origin.x, coordinates[i].y - origin.y);
		        
		        // Java coordinates start at 0 and increase as a point moves down, so
		        // multiply by -1 to reverse
		        translationCoordinate.y *= -1;
		        
		        // Clone coordinates, so I can use translation coordinates
		        // in upcoming calculation
		        rotatedCoordinates[i] = (Point)translationCoordinate.clone();
		        
		        // May need to round results after rotation
		        rotatedCoordinates[i].x = (int)Math.round(translationCoordinate.x * Math.cos(Math.PI/2) + translationCoordinate.y * Math.sin(Math.PI/2)); 
		        rotatedCoordinates[i].y = (int)Math.round(translationCoordinate.x * Math.sin(Math.PI/2) - translationCoordinate.y * Math.cos(Math.PI/2));
		        
		        // Multiply y-coordinate by -1 again
		        rotatedCoordinates[i].y *= 1;
		        
		        // Translate to get new coordinates relative to
		        // original origin
		        rotatedCoordinates[i].x += origin.x;
		        rotatedCoordinates[i].y += origin.y;
		        
		        // Erase the old coordinates by making them black
//		        this.buttons[coordinates[i].x][ coordinates[i].y].setBackground(null);
		    }
		    // Set new coordinates to be drawn on screen
		    setCoordinates(rotatedCoordinates, 0);
		    } catch (NullPointerException a) {
		    }
	    }
	}
	
	public synchronized void moveDown() {
		Point[] points = new Point[4];
		if (this.moving) {
			for(int i = 0; i < points.length; i++){
				points[i] = new Point((int)this.coordinates[i].getX(),(int)this.coordinates[i].getY() + 1);
			}
			setCoordinates(points, 1);
		}
	}
	
	
	
	public synchronized void moveLeft() {
		Point[] points = new Point[4];
		if (this.moving) {
			for(int i = 0; i < points.length; i++){
				points[i] = new Point((int)this.coordinates[i].getX() - 1,(int)this.coordinates[i].getY());
			}
			 
			setCoordinates(points, 0);
		}
	}
	
	public synchronized void moveRight() {
		Point[] points = new Point[4];
		if (this.moving) {
			for(int i = 0; i < points.length; i++){
				points[i] = new Point((int)this.coordinates[i].getX() + 1,(int)this.coordinates[i].getY());
			}
			 
			setCoordinates(points, 0);
		}
	}
	
	protected void move (Point[] points) {
		for (Point i : points) {
			this.buttons[(int)i.getX()][(int)i.getY()].setBackground(this.color);
		}
	}
	
	protected void setCoordinates(Point[] points,int code) {
            
		if (code == 2) {
			if (!checkPosition(points,true)) {
				JFrame frame = new JFrame();
				frame.setVisible(true);
				frame.setTitle("DED");
				frame.add(new JLabel("you lose"));
				frame.setSize(100, 100);
			}
		} else if (checkPosition(points,false)) {
			this.coordinates = points;
			move(points);
		} else if (code == 1) {
			this.moving = false;
			
			for (int i = 0; i < this.buttons[0].length; i++) {
				if (checkRow(i)) {
                                    if (this.numberOfTetris != 2850) {
                                        this.numberOfTetris++;
                                    }
                                    
                                    Color[] colors = {Color.BLACK,null};
                                    for (int ii = 0; ii < colors.length; ii++) {
                                        for (int iii = 0; iii < this.buttons.length; iii++) {
                                            this.buttons[iii][i].setBackground(colors[ii]);
                                            try {
                                                Thread.sleep(200 / (this.getScore() / 500 + 1) );
                                            } catch(InterruptedException ex) {
                                                Thread.currentThread().interrupt();
                                            }
                                        }
                                    }

                                    for (int j = i; j > 0; j--) {
                                        for (int jj = 0; jj < this.buttons.length; jj++) {
                                            this.buttons[jj][j].setBackground(this.buttons[jj][j - 1].getBackground());
                                        }
                                    }
				}
			}
		}
	}
	
	protected boolean checkRow (int row) {
		Color defualtColor = new JButton().getBackground();
		
		for (int i = 0; i < this.buttons.length; i++) {
			if (this.buttons[i][row].getBackground().equals(defualtColor)) {
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean checkPosition(Point[] points, boolean code) {
		boolean collideSelf;
		int x;
		int y;
		for(int i = 0; i < points.length; i++){
			collideSelf = false;
			try {
				x = (int)points[i].getX();
				y = (int)points[i].getY();
				if (this.buttons[x][y].getBackground().getRGB() != this.buttons[0][0].getBackground().getRGB()) { 
					for (Point ii : this.coordinates) {
						if (ii.equals(points[i]) && !code) {
							collideSelf = true;
						}
					}
					
					if (!collideSelf) {
						return false;
					}
				}
			} catch (ArrayIndexOutOfBoundsException a) {
				return false;
			}
		}
		
		if (!code) {
			for(int i = 0; i < this.coordinates.length; i++){
				this.buttons[coordinates[i].x][coordinates[i].y].setBackground(null);
			}
		} else {
			this.coordinates = points;
		}
		
		return true;
	}
}
