import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
public class DrawingCanvas extends JComponent{
	
	
	//private data fields
	private int width;
	private int height;
	private int namePosx;
	private int namePosy;
	private int a;
	private int b;
	private int posx;
	private int posy;
	private String name;
	
	//Create arrayList to store objects to draw
	ArrayList<Ellipse2D.Double> circless = new ArrayList<Ellipse2D.Double>();
	ArrayList<Line2D.Double> liness = new ArrayList<Line2D.Double>();
	ArrayList<String> namess = new ArrayList<String>();
	ArrayList<Integer> xPos = new ArrayList<Integer>();
	ArrayList<Integer> yPos = new ArrayList<Integer>();
	
	
	
	//constructor
	public DrawingCanvas(int w, int h) {
		width = w;
		height = h;
	}
	
	//Override method
	@Override protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//Draw all of the circles
		if(circless.size() != 0) {
			for(Ellipse2D.Double circles: circless) {
					if(circless.size() != 0) {
						g2d.draw(circles);
				}
			
			}
		}
		
		//Draw all of the lines
		if(liness.size() != 0) {
			for(Line2D.Double lines: liness) {
				if(liness.size() != 0) {
					g2d.draw(lines);
				}
			}	
		}
		

		
		//Draw all of the names
		if((namess.size() != 0) && (xPos.size() != 0) && (yPos.size() != 0)) {
			for(int i = 0; i < namess.size(); i ++) {
				
				String nameb = namess.get(i);
				int xcoord = xPos.get(i);
				int ycoord = yPos.get(i);
				g2d.drawString(nameb, xcoord, ycoord);
			}
					
		}else {
			if(xPos.size() == 0) {
				System.out.println("xPos array empty");
			}
					
			if(yPos.size() == 0) {
				System.out.println("yPos array empty");
			}
		}
		}//paint Component
	
	//OVERLOAD paint component class in JComponent
	//Helper Method for drawing a circle
	protected void drawCircle( Graphics g, int x, int y, String name) {
		
		//Hardcoded circle size
		int circleHeight = 60;
		int circleWidth = 60;
		
		Graphics2D g2d = (Graphics2D) g;
		
		//Create new Circle and color it black
		Ellipse2D.Double c = new Ellipse2D.Double(x,y, circleHeight, circleWidth);
		g2d.setColor(Color.black);
		g2d.fill((Shape) c);
		
		//Create overlying Circle
		Ellipse2D c2 = new Ellipse2D.Double(x+5,y+5, circleHeight -10, circleWidth -10);
		g2d.setColor(Color.white);
		g2d.fill((Shape) c2);
		
		//Create text within circle
		Font textFont = new Font("Times New Roman", Font.BOLD, 13);
		g2d.setColor(Color.black);
		g2d.setFont(textFont);
		g2d.drawString(name, x +25 , y +30);
	}
	
	//draw line method
	protected void drawLine(Graphics g, int x, int y, int a, int b) {
		Graphics2D g2d = (Graphics2D) g;
		Line2D.Double l = new Line2D.Double(x, y, a , b);
		g2d.setColor(Color.black);
		g2d.draw(l);
		
	}
	
	
	//set the numbers for position and names
	public void setNums(int x, int y, int a, int b, String name) {
		this.a = a;
		this.b = b;
		this.name = name;
	}
	
	//adds a circle to a list of objects that needs to be drawn
	public void addCircle(int x, int y, int circleHeight, int circleWidth) {
		Ellipse2D.Double c = new Ellipse2D.Double(x,y, circleHeight, circleWidth);
		circless.add(c);
	}
	
	//adds a line to a list of objects that need to be drawn
	public void addLine(int x, int y, int a, int b) {
		Line2D.Double l = new Line2D.Double(x,y,a,b);
		liness.add(l);
	}
	
	//Adds a string name to the arraylist to be drawn
	public void addName(String name, int x, int y) {
		namess.add(name);
		this.namePosx = x;
		xPos.add(x);
		this.namePosy = y;
		yPos.add(y);
	}
	
	//sets the position for the names
	public void setNamePos(int x, int y) {
		this.namePosx = x;
		xPos.add(x);
		this.namePosy = y;
		yPos.add(y);
	}
	
	
	
}
