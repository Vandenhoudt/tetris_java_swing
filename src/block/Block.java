package block;

import java.awt.Point;

public enum Block {
	SWIGGELY{
		public void makePoints() {
			points[0] = new Point(0,0);
			points[1] = new Point(1,0);
			points[2] = new Point(1,1);
			points[3] = new Point(2,1);
		}
	},REVERSE_SWIGGELY{
		public void makePoints() {
			points[0] = new Point(0,1);
			points[1] = new Point(1,1);
			points[2] = new Point(1,0);
			points[3] = new Point(2,0);
		}
	},L_BLOCK{
		public void makePoints() {
			points[0] = new Point(0,0);
			points[1] = new Point(0,1);
			points[2] = new Point(0,2);
			points[3] = new Point(1,2);
		}
	},REVERSE_L_BLOCK{
		public void makePoints() {
			points[0] = new Point(1,0);
			points[1] = new Point(1,1);
			points[2] = new Point(1,2);
			points[3] = new Point(0,2);
		}
	},T_BLOCK{
		public void makePoints() {
			points[0] = new Point(0,1);
			points[1] = new Point(1,1);
			points[2] = new Point(1,0);
			points[3] = new Point(2,1);
		}
	},SQUARE{
		public void makePoints() {
			points[0] = new Point(0,0);
			points[1] = new Point(1,0);
			points[2] = new Point(1,1);
			points[3] = new Point(0,1);
		}
	},LINE{
		public void makePoints() {
			points[0] = new Point(0,0);
			points[1] = new Point(1,0);
			points[2] = new Point(2,0);
			points[3] = new Point(3,0);
		}
	};
	
	public Point[] points = new Point[4];
	
	public Point[] getPoints(){
		makePoints();
		return this.points;
	}
	
	public void makePoints() {
		
	}
}
