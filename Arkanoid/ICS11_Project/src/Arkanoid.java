import java.util.Scanner;
import java.io.*;

import hsa_ufa.Console;
import java.awt.*; 

import java.math.*; //math library

import java.util.Random;

public class Arkanoid {

	static Console c = new Console(650, 500); //make sure to change it at home (650, 500) is the original
	static Scanner in = new Scanner(System.in);
	static Random rng = new Random(); //balls moving random from 1-2 steps away from the original

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		int x_plate = 275; //650 wide/2 then -50 for the plate wide 100
		int y_plate = 480; //border height - 20
		int w_plate = 100;
		int h_plate = 10;

		int top_border_x = 0;
		int top_border_y = 0;
		int top_border_length = c.getDrawWidth(); //top border length/width
		int top_border_height = 20; //top border height

		int border_width = 20; //left and right border width
		int border_height = c.getDrawHeight(); //left and right border height
		int left_border_x = 0;
		int left_border_y = 0;
		int right_border_x = c.getDrawWidth() - border_width;
		int right_border_y = 0;

		double x_ball = 650/2-5;
		double y_ball = 500-20-10;
		int w_ball = 10;
		int h_ball = 10;
		double xball_moving = 0; //x_direction of the moving ball
		double yball_moving = 0; //y_direction of the moving ball
		double ball_speed = 0; //speed of the ball
		boolean ball_active = false;

		int arrow_length = 50;
		int angle = 45;
		double xmoving = 1;
		double ymoving = Math.round(Math.sqrt(1-50^2)); //y^2 + x^2 = r^2
		int arrow_x1 = c.getDrawWidth() / 2; //same as x1					  			//x coordinate bottom of arrow
		int arrow_y1 = c.getDrawHeight() - 20 - 5; //center of the ball 	  			//y coordinate bottom of arrow
		int arrow_x2; //= arrow_x1 + (int) (Math.cos(angle * Math.PI/180) * arrow_length); //x coordinate top of arrow
		int arrow_y2; //= arrow_y1 - (int) (Math.sin(angle * Math.PI/180) * arrow_length); //y coordinate top of arrow

		int rec_x = 275; //top x of the rectangle in the middle of the screen
		int rec_y = 100; //top y of the rectangle in the middle of the screen
		int rec_w = 100; //width of the rectangle in the middle of the screen
		int rec_h = 50; //height of the rectangle in the middle of the screen

		/*
		 * Coordinates of the top rectangle border
		 * 
		 */
		int top_rec_x;
		int top_rec_y;
		int top_rec_w;
		int top_rec_h = 1;


		/*
		 * Coordinates of the bottom rectangle border
		 * 
		 */
		int bottom_rec_x;
		int bottom_rec_y;
		int bottom_rec_w;
		int bottom_rec_h = 1;


		/*
		 * Coordinates of the left rectangle border
		 * 
		 */
		int left_rec_x;
		int left_rec_y;
		int left_rec_w = 1;
		int left_rec_h;


		/*
		 * Coordinates of the right rectangle border
		 * 
		 */
		int right_rec_x;
		int right_rec_y;
		int right_rec_w = 1;
		int right_rec_h;

		int[] brick_x; //array of drawing rectangle
		int[] brick_y;
		int brick_w = 90;
		int brick_h = 30;
		boolean[] brick_visible; //if true then draw if false then not draw and breaks

		int num = 30; //number of bricks
		int points = 0; //point when touch the brick

		double sleep = 1; //sleep

		num = num();

		brick_x = new int[num];
		brick_y = new int[num];
		brick_visible = new boolean[num]; //brick_visible = true?

		for (int i = 0; i < num; i++) {

			if (brick_visible[i] = true) {
				brick_x[i] = i % 5 * brick_w + 100; //take the remainder for the x_coordinates
				brick_y[i] = i / 5 * brick_h + 100; //1/5 = 0 (top y is 0) (brick[i]/5) ex. 8/5 = 1
			}
		}

		System.out.print("The Console is " + c.getDrawWidth() + " pixels wide ");
		System.out.println("and " + c.getDrawHeight() + " pixels high.");

		while (true) {

			arrow_x2 = arrow_x1 + (int) (Math.cos(angle * Math.PI/180) * arrow_length); //x coordinate top of arrow
			arrow_y2 = arrow_y1 - (int) (Math.sin(angle * Math.PI/180) * arrow_length); //y coordinate top of arrow

			synchronized (c) { //flicker solve

				c.clear();

				Image plate = Toolkit.getDefaultToolkit().getImage(c.getClass().getClassLoader().getResource("plate.png")); //plate input
				c.drawImage(plate, x_plate, y_plate, w_plate, h_plate); //draw image of plate


				for (int i = 0; i < num; i++) {
					if (brick_visible[i] == true)
						c.drawRect(brick_x[i], brick_y[i], brick_w, brick_h);
				}

				Image border = Toolkit.getDefaultToolkit().getImage(c.getClass().getClassLoader().getResource("border.png")); //border input
				c.drawImage(border, 0, 0, 650, 500); //draw image of cover border

				c.fillOval((int)x_ball, (int)y_ball, w_ball, h_ball); //shape of the ball
				Image ball = Toolkit.getDefaultToolkit().getImage(c.getClass().getClassLoader().getResource("ball.png")); //input ball image
				c.drawImage(ball,(int)x_ball, (int)y_ball, w_ball, h_ball ); //draw image of plate

				c.drawLine(arrow_x2, arrow_y2, arrow_x1, arrow_y1);

			} //end of flicker


			Thread.sleep((int) sleep); //delay

			if (c.isKeyDown(Console.VK_RIGHT)) { //moving right
				x_plate++;
				arrow_x1++;
			}

			if (c.isKeyDown(Console.VK_LEFT)) { //moving left
				x_plate--;
				arrow_x1--;
			}


			if (c.getKeyChar() == 'a') { //arrow moving
				angle++;					
			}

			if (c.getKeyChar() == 'd') { //arrow moving
				angle--;
			}

			//find direction of the arrow and replicate it to the ball
			ball_speed = 1;


			if (c.isKeyDown(' ')) {
				ball_active = true;

				xball_moving = (Math.cos(angle * Math.PI/180) * ball_speed);
				yball_moving = (Math.sin(angle * Math.PI/180) * ball_speed);

				/*
				 * this is where to input the moving of the balls
				 * when pressing "Space"
				 * 
				 */

			}

			if (ball_active) {
				x_ball += xball_moving;
				y_ball += -yball_moving;
				arrow_x2 = 0;
				arrow_y2 = 0;
				arrow_x1 = 0;
				arrow_y1 = 0;
			}

			else {
				x_ball = x_plate + 45;
			}


			if (x_ball + w_ball >= right_border_x) { //right border
				xball_moving = -xball_moving;
			}

			if (x_ball <= left_border_x + border_width) { //left border
				xball_moving = -xball_moving;
			}

			if (y_ball <= top_border_y + top_border_height) { //top border
				yball_moving = -yball_moving;
			}


			for (int i = 0; i < num; i++) {				

				right_rec_x = brick_x[i] + brick_w - 1;
				right_rec_y = brick_y[i];
				right_rec_h = brick_h;

				left_rec_x = brick_x[i];
				left_rec_y = brick_y[i];
				left_rec_h = brick_h;

				top_rec_x = brick_x[i];
				top_rec_y = brick_y[i];
				top_rec_w = brick_w;

				bottom_rec_x = brick_x[i];
				bottom_rec_y = brick_y[i] + brick_h;
				bottom_rec_w = brick_w;

				if (x_ball >= right_rec_x && x_ball <= right_rec_x + right_rec_w && y_ball >= right_rec_y && y_ball <= right_rec_y + right_rec_h ) { //right rectangle border collision detection
					xball_moving = -xball_moving;
					brick_visible[i] = false;
					points++;

				}

				if (x_ball + w_ball >= left_rec_x && x_ball + w_ball <= left_rec_x + left_rec_w && y_ball >= left_rec_y && y_ball <= left_rec_y + left_rec_h) { 	//left rectangle border collision detection
					xball_moving = -xball_moving;				
					brick_visible[i] = false;
					points++;

				}

				if (y_ball + h_ball >= top_rec_y && y_ball + h_ball <= top_rec_y + top_rec_h && x_ball >= top_rec_x && x_ball <= top_rec_x + top_rec_w) { 	//top rectangle border collision detection
					yball_moving = -yball_moving;				
					brick_visible[i] = false;
					points++;

				}

				if (y_ball >= bottom_rec_y && y_ball <= bottom_rec_y + bottom_rec_h && x_ball >= bottom_rec_x && x_ball <= bottom_rec_x + bottom_rec_w) {
					yball_moving = -yball_moving;				
					brick_visible[i] = false;
					points++;
				}
				if (brick_visible[i] == false) {

					brick_x[i] = -500;
					brick_y[i] = -500;
				}


			}


			if (y_ball + h_ball >= y_plate && x_ball >= x_plate && x_ball <= x_plate + w_plate ) { //touching the plate when the game is started
				yball_moving = -yball_moving;
			}


			if (c.isKeyDown(Console.VK_BACK_SPACE) || y_ball > c.getDrawHeight())
				break;
		}
		c.clear();
		c.println("You have " + points + " points");
	}


	static int num(){

		int num;
		while (true) {
			// type in the menu
			c.println("How many brick do you want to play?");
			num = c.readInt();

			if (num < Integer.MAX_VALUE && num > Integer.MIN_VALUE) {
				break;
			}

			else {
				c.println("Invalid number! Retry!");
				c.clear();
				continue;
			}
		}
		return num;
	}

}
