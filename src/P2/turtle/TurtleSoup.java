/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.color(PenColor.CYAN);
        for(int i = 0; i < 4; i++ ) {
        	turtle.forward(sideLength);
        	turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return ((double)180-(double)360/sides);
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	double exangle = 180.0 - angle;
        return ((int)(360.0/exangle + 0.5));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	turtle.color(PenColor.BLACK);
        for(int i = 0; i < sides; i++) {
        	turtle.forward(sideLength);
        	turtle.turn((double)180 - calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX, int targetY) {
    	double angle = Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;//返回一个-180~180的值
    	double bearing;
    	if(angle < 90.0) {
    		bearing = 90.0 - angle - currentBearing;
    		if (bearing < 0)
    			bearing += 360.0;
    	}
    	else {
    		bearing = 360.0 - (angle - 90.0) - currentBearing;
    		if (bearing < 0)
    			bearing += 360.0;
    		if (bearing == 360.0)
    			bearing = 0.0;
    	}
    	return bearing;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	int oriX,oriY;
    	int dstX,dstY;
    	double currentBearing = 0.0;
    	List<Double> Bearings = new ArrayList<>();
    	oriX = xCoords.get(0);
    	oriY = yCoords.get(0);//初始化坐标
    	int listlength = xCoords.size();
    	for(int i = 1; i < listlength; i++) {
    		dstX = xCoords.get(i);
    		dstY = yCoords.get(i);
    		Bearings.add(calculateBearingToPoint(currentBearing, oriX, oriY, dstX, dstY));
    		currentBearing = Bearings.get(i-1);
    		oriX = dstX;
    		oriY = dstY;
    	}
        return Bearings;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Point ori = new Point(Double.MAX_VALUE , Double.MAX_VALUE);
    	Set<Point> convexHullPoints = new HashSet<Point>();
    	Set<Point> Points = new HashSet<Point>();
    	for (Point i : points) {
    		Points.add(i);
    		if(i.x() < ori.x())
    			ori = i;
    		else if(i.x() == ori.x() && i.y() < ori.y())
    			ori = i;
    		else 
    			continue;
    	}
    	if (points.size() <= 3) {
    		convexHullPoints = points;
        	return convexHullPoints;
    	}
    	convexHullPoints.add(ori);
    	Point from = ori;
    	Point to = null;
    	List<Point> restPoints = new ArrayList<Point>(Points);
    	List<Double> Bearings = new ArrayList<Double>();
    	int size = 0,cur = 0;
    	double maxdist = 0,distj = 0,disti = 0;
    	
    	while(to != ori) {
    		Point temp;
    		for(int i = 0; i < restPoints.size(); i++) {
    			temp = restPoints.get(i);
    			Bearings.add(calculateBearingToPoint(0.0, (int)from.x(), (int)from.y(), (int)temp.x(), (int)temp.y()));
    		}
    		size = Bearings.size();
    		cur = 0;
    		maxdist = Math.pow((restPoints.get(cur).x()-from.x()),2) + Math.pow((restPoints.get(cur).y()-from.y()),2);
    		for(int i = 1; i < size; i++) {
    			if(Bearings.get(i).equals(Bearings.get(cur))) {
    				disti = Math.pow((restPoints.get(i).x()-from.x()),2) + Math.pow((restPoints.get(i).y()-from.y()),2);
    				if(disti > maxdist) {
    					cur = i;
    					maxdist = disti;
    				}
    			}
    		}
    		for(int i = 1; i < size; i++) {
    			if(Bearings.get(i) < Bearings.get(cur)) {
    				cur = i;
    				disti = Math.pow((restPoints.get(i).x()-from.x()),2) + Math.pow((restPoints.get(i).y()-from.y()),2);
    				for(int j = i+1; j < size; j++) {
    					distj = Math.pow((restPoints.get(j).x()-from.x()),2) + Math.pow((restPoints.get(j).y()-from.y()),2);
    					if(Bearings.get(j).equals(Bearings.get(i))){
    						System.out.println(maxdist+" "+distj);
    						if (distj > maxdist) {
    							cur = j;
    							maxdist = distj;
    						}
    					}
    				}
    			}
    		}
    		to = restPoints.get(cur);
    		convexHullPoints.add(restPoints.get(cur));
    		
    		from = to;
    		Bearings.clear();
    		size = 0;
    		restPoints.remove(cur);
    	}
        return convexHullPoints;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	turtle.color(PenColor.RED);
    	turtle.turn(90);
    	for(int i = 0; i < 3; i++) {
    		turtle.forward(120);
        	turtle.turn(120);
    	}
    	
    	turtle.forward(40);
    	turtle.turn(120);
    	turtle.forward(80);
    	turtle.turn(240);
    	
    	for(int i = 0; i < 2; i++) {
    		turtle.forward(120);
        	turtle.turn(240);
    	}
    	turtle.forward(40);
    	turtle.turn(60);
    	turtle.forward(40);
    	turtle.turn(150);
    	turtle.turn(330);
    	for(int i = 0; i < 2; i++)
    	drawRegularPolygon(turtle, 145, 3);//六芒星
    	
    	
    	
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);
        drawRegularPolygon(turtle, 7, 40);
        drawPersonalArt(turtle);
        //draw the window
        
        Point p1 = new Point(1, 1);
		Point p2 = new Point(10, 10);
		Point p3 = new Point(1, 10);
		Point p4 = new Point(2, 2);
		Point p5 = new Point(1, 2);
		Point p7 = new Point(1, 4);
		Point p6 = new Point(3, 2);
		Set<Point> points = new HashSet<Point>();
		Set<Point> convexHull = new HashSet<Point>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		points.add(p6);
		points.add(p7);
		convexHull = convexHull(points);
		System.out.println("convexHullPoints are:");
		for(Point i : convexHull) {
			System.out.println( "(" + i.x() + "," + i.y() + ") ");
		}
		//测试凸包算法
        turtle.draw();
    }

}
