import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.InputMismatchException;
import javax.swing.*;
import java.awt.*;
public class Project1_employee {

	public static void main(String[] args) throws IOException {
		
				//setupTime
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_yyyy_hh_mm_ss");
				LocalDateTime time = LocalDateTime.now();
				
				//create file to write to
				String timeStream = formatter.format(time);
				String file1 = "payfile_" + timeStream + ".txt";
				String file2 = "surefile_" + timeStream + ".txt";
				File payFile = new File(file1);
				File sureFile = new File(file2);
				
				if(payFile.createNewFile()) {
					System.out.println("FIle successfully created");
				}else {
					System.out.println("File could not be created");
				}
				
				if(sureFile.createNewFile()) {
					System.out.println("FIle 2 successfully created");
				}else {
					System.out.println("File 2 could not be created");
				}
				
				
				//setup Jframe and add drawing Canvas Class
				int w = 640;
				int h = 480;
				JFrame f = new JFrame();
				DrawingCanvas binaryDraw = new DrawingCanvas(w,h);
				f.setSize(w,h);
				f.setTitle("Binary Tree Representation");
				f.add(binaryDraw);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(false);
				
				//Create a new filewriter and binary tree
				FileWriter write = new FileWriter(payFile);
				FileWriter sure = new FileWriter(sureFile);
				Tree BinaryTree = new Tree(f, binaryDraw);
				
				//Store the inputs
				double percent;
				double tips;
				String name;
				double totalTips = 0;
				double total60 = 0;
				double totalTakeHome = 0;
				double totalWorkerTakeHome = 0;
				
				//write the time to the file
				formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy_hh:mm:ss");
				write.write(formatter.format(time));
				sure.write(formatter.format(time));
				
				do {
				
				//Ask user for input
				Scanner userInput = new Scanner(System.in);
				
				System.out.println("Enter the name of the worker");
				name = userInput.nextLine();
				
				System.out.println("Enter the 60% value");
				percent = getValidDouble();
				
				if(percent == -1 || name.equals("done")) {
					userInput = new Scanner(System.in);
					System.out.println("Terminal, continue exit? Y or N ");
					String userAns = userInput.nextLine();
					
					//Evaluate
					
					if(userAns.equals("N") || userAns.equals("n")) {
						f.setVisible(true);
						
						//now ask if user wants to exit and continue to write to file
						do{
						System.out.println("Exit now? Y or N");
						userAns= userInput.nextLine();
						}
						while((!userAns.equals("Y")) && (!userAns.equals("y")));
						
						System.out.println("Writing to files now");
						break;
							
						
						
						
					}else {
						System.out.println("Continuing to exit program, writing to files");
						break;
					}
					
				}else {
					
				System.out.println("Enter the tips value");
				tips = getValidDouble();
				
				
				//add to binary tree
				Worker newWorker = new Worker(name, tips, percent);
				boolean addSucc = BinaryTree.addNode(newWorker, binaryDraw);
				
				//do the neccessary calculations
				double cash = percent *.4;
				double check = (percent * .6) + tips;
				double total = cash + check;
				
				//perform etc calulations
				double highestTip = BinaryTree.highestTip(BinaryTree).getTips();
				Worker workerHighestTip = BinaryTree.highestTip(BinaryTree);
				
				
				//display the calculations
				System.out.println("Total: " + total);
				System.out.println("Cash: " + cash);
				System.out.println("Check : " + check);
				
				
				
				//write to the file
				String cashVal = String.valueOf(cash);
				String checkVal = String.valueOf(check);
				
				write.write("\n\n" + name);
				write.write("\nCash: " + cashVal);
				write.write("\nCheck: " + checkVal);
				
				sure.write("\n\n" + name);
				sure.write("\n60Val: " + percent); 
				sure.write("\nTips: " + tips);
				
				total60 += percent;
				totalTips += tips;
				totalWorkerTakeHome += tips + percent;
				totalTakeHome += .4 * (1.6666666666 * percent);
				
				}
				
				
				
				}while(percent != -1);
				
				
				
				
				
				//write all relevant metrics to file
				write.write("\n\nTotal Tips = " + totalTips);
				write.write("\nTotal worker 60 percent = " + total60);
				write.write("\nTotal owner take home = " + totalTakeHome);
				write.write(("\nTotal worker take home = " + totalWorkerTakeHome));
				write.write("\nWorker with least total: " + BinaryTree.findLowest().getName() + " , " + BinaryTree.findLowest().getTotal());
				write.write("\nWorker with highest total: " + BinaryTree.findHighest().getName() + " , " + BinaryTree.findHighest().getTotal());
				write.write("\nWorker with the highest tips: " + BinaryTree.highestTip(BinaryTree).getName() + " , " + BinaryTree.highestTip(BinaryTree).getTips());
				write.write("\nWorker with the lowest tips: " + BinaryTree.lowestTip(BinaryTree).getName() + " , " + BinaryTree.lowestTip(BinaryTree).getTips());
				write.close();
				sure.close();
				
				
				
			}
			
			//method to validate and make user input safe for programs use
			private static double getValidDouble() {
				
				double userValue = 0;
				int returnVal = 0;
				Scanner userInput = new Scanner(System.in);
				
				while(returnVal == 0){
					try {
						userValue = userInput.nextDouble();
						while(userValue < 0 && userValue != -1) {
							System.out.println("Input less than zero, try again");
							userValue = userInput.nextDouble();
							returnVal = 0;
						}
						returnVal = 1;
					}catch (InputMismatchException e){
						System.out.println("Input is not a valid double, try again");
						userInput.next();
						returnVal = 0;
					}
				}
				return userValue;
				
				}

		}

		//class that represents a worker
		class Worker{
			private String name;
			private double tips;
			private double percent;
			private double total;
			
			public Worker(String name, double tips, double percent) {
				this.name = name;
				this.tips = tips;
				this.percent = percent;
				this.total = tips + percent;
			}
			
			public double getTotal() {
				return this.total;
			}
			
			public double getTips() {
				return this.tips;
			}
			
			public double getPercent()
			{
				return this.getPercent();
			}
			
			public String getName() {
				return this.name;
			}
			
		}

		class Tree{
			
			//private data fields
			private treeNode root;
			private JFrame f;
			private DrawingCanvas binaryDraw;
			
			//Constructor 
			public Tree(JFrame f, DrawingCanvas binaryDraw) {
				this.root = null;
				this.f = f;
				this.binaryDraw = binaryDraw;
				
				
			}
			
			
			
			//public methods
			public boolean addNode(Worker worker, DrawingCanvas binaryDraw) {
				//set all required variables
				boolean returnVal = false;
				double rightVal = 0;
				double leftVal = 0;
				int x = 320;
				int y = 240;
				int circleSize = 60;
				int xVal = x;
				int yVal = y;
				int parentXVal = x + 30;
				int parentYVal = y + 60;
				int childXVal = parentXVal;
				int childYVal = parentYVal;
				
				if(this.root == null) {
					
					//create new root node with appropriate information
					root = new treeNode(worker);
					root.xPos = parentXVal;
					root.yBottom = parentYVal;
					
					//draw the circle and add the name for the root
					binaryDraw.addCircle(x, y, circleSize, circleSize);
					binaryDraw.addName(worker.getName(), x+20 , y+20);
					
					//add the total value of the worker to the circle drawing
					//convert total value to string
					String totalVal = String.format("%.2f" ,worker.getTotal());
					binaryDraw.addName(totalVal, x+20, y+30);

					returnVal = true;
				}else {
					treeNode current = root;
					treeNode parent = null;
					while(current != null) {
						leftVal = 0;
						rightVal = 0;
						if(worker.getTotal() < current.worker.getTotal()) {
							xVal -= 60;
							yVal += 90;
							leftVal = 1;
							parent = current;
							current = parent.left;
						}else if(worker.getTotal() > current.worker.getTotal()) {
							xVal += 60;
							yVal +=90;
							rightVal = 1;
							parent = current;
							current = parent.right;
						}
					}// while loop
					if(leftVal == 1) {
						
						//create new node storing the worker
						parent.left = new treeNode(worker);
						
						//set the worker's coordinates for the shapes to be drawn 
						parent.left.xPos = parent.xPos - 60;
						parent.left.yBottom = parent.yBottom + 90;
						parent.left.yTop = parent.left.yBottom - 60 ;
						
						//Draw the shapes and lines
						binaryDraw.addCircle(xVal, yVal, circleSize, circleSize);
						binaryDraw.addName(worker.getName(), xVal +20 , yVal +20);
						
						//add the total value of the worker to the circle drawing
						//convert total value to string
						String totalVal = String.format( "%.2f" ,worker.getTotal());
						binaryDraw.addName(totalVal, xVal+20, yVal+30);

						//draw lines connecting the circles
						binaryDraw.addLine(parent.xPos, parent.yBottom, parent.left.xPos, parent.left.yTop);
						
						//write the difference between the total of the nodes halfway between the lines
						double difference = parent.left.worker.getTotal() - parent.worker.getTotal();
						String differenceVal = "-" + String.format("%.2f", difference);
						
						int midX = ((parent.left.xPos + parent.xPos) / 2) - 60;
						
						//evaluate if node is root or not to determine yVal
						int midY = 0;
						if(parent.equals(root)) {
							midY = ((parent.left.yTop + parent.yTop) / 2) + 150;
						}else {
							midY = ((parent.left.yTop + parent.yTop) / 2) + 35;
						}
						binaryDraw.addName(differenceVal, midX, midY);
						
						returnVal = true;
						
					}else if(rightVal == 1) {
						
						//Create new node storing the worker
						parent.right = new treeNode(worker);
						
						//Set the worker's coordinates for the shapes to be drawn
						parent.right.xPos = parent.xPos + 60;
						parent.right.yBottom = parent.yBottom + 90;
						parent.right.yTop = parent.right.yBottom - 60;
						
						//Draw the shapes and lines
						binaryDraw.addCircle(xVal, yVal, circleSize, circleSize);
						binaryDraw.addName(worker.getName(), xVal +20, yVal +20);
						
						//add the total value of the worker to the circle drawing
						//convert total value to string
						String totalVal = String.format("%.2f" ,worker.getTotal());
						binaryDraw.addName(totalVal, xVal+20, yVal+30);
						
						//draw the lines connecting the circles
						binaryDraw.addLine(parent.xPos, parent.yBottom, parent.right.xPos , parent.right.yTop);
						
						//write the difference between the total of the nodes halfway between the lines
						double difference = parent.right.worker.getTotal() - parent.worker.getTotal();
						String differenceVal = "+" + String.format("%.2f", difference);
						int midX = ((parent.right.xPos + parent.xPos) / 2);
						
						//evaluate if parent node or not to determine y val
						int midY = 0;
						if(parent.equals(root)) {
							midY = ((parent.right.yTop + parent.yTop) / 2 ) + 150;
						}else {
							midY = ((parent.right.yTop + parent.yTop) / 2 ) + 25;
						}
						
						binaryDraw.addName(differenceVal, midX, midY);
						
						returnVal = true;
						
					}else{
						returnVal = false;
					}
					
					
				}//else
				
				return returnVal;
				
			}
			
			//find methods
			//traverse to the lowest, leftmost part of the tree
			public Worker findLowest() {
				treeNode current = root;
				while(current.left != null) {
					current = current.left;
				}
				return current.worker;
			}
			
			//traverse to the lowest, rightmost part of the tree
			public Worker findHighest() {
				treeNode current = root;
				while(current.right!= null) {
					current = current.right;
				}
				return current.worker;
			}
			
			//level order traversal, find what worker has the highest tips
			public Worker highestTip(Tree tree) {
				Queue<treeNode> treeQueue = new LinkedList<>();
				treeNode rootNode = tree.root;
				treeNode currentNode;
				double tipVal = 0;
				Worker worker = rootNode.worker;
				
				currentNode = rootNode;
				treeQueue.add(rootNode);
				while(treeQueue.isEmpty() == false) {
					currentNode = treeQueue.poll();
					
					if(currentNode.worker.getTips() > tipVal) {
						tipVal = currentNode.worker.getTips();
						worker = currentNode.worker;
					}
					
					if(currentNode.left != null) {
						treeQueue.add(currentNode.left);
					}
				
					if(currentNode.right != null) {
						treeQueue.add(currentNode.right);
					}
				}
					return worker;
				
			}
			
			//level order traversal, find what worker has the lowest tips
			public Worker lowestTip(Tree tree) {
				Queue<treeNode> treeQueue = new LinkedList<>();
				treeNode rootNode = tree.root;
				treeNode currentNode;
				double tipVal = rootNode.worker.getTips();
				Worker worker = rootNode.worker;
				
				currentNode = rootNode;
				treeQueue.add(rootNode);
				while(treeQueue.isEmpty() == false) {
					currentNode = treeQueue.poll();
					
					if(currentNode.worker.getTips() < tipVal) {
						tipVal = currentNode.worker.getTips();
						worker = currentNode.worker;
					}
					
					if(currentNode.left != null) {
						treeQueue.add(currentNode.left);
					}
				
					if(currentNode.right != null) {
						treeQueue.add(currentNode.right);
					}
				}
					return worker;
				
			}
			
				private static class treeNode{
				private Worker worker;
				private treeNode left;
				private treeNode right;
				private int xPos;
				private int yBottom;
				private int yTop;
				
				public treeNode(Worker worker) {
					this.worker = worker;
					this.left = null;
					this.right = null;
				}
			}
		
	}


