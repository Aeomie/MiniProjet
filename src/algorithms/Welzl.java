package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import supportGUI.Circle;

public class Welzl {
	
	
	  public static Point calculateCircumcenter(Point A, Point B, Point C) {
		    double ax = A.x, ay = A.y;
		    double bx = B.x, by = B.y;
		    double cx = C.x, cy = C.y;

		    double denominator = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
		    if (denominator == 0) {
		        throw new IllegalArgumentException("Points are collinear; circumcenter is undefined.");
		    }

		    double X = ((ax * ax + ay * ay) * (by - cy) +
		                (bx * bx + by * by) * (cy - ay) +
		                (cx * cx + cy * cy) * (ay - by)) / denominator;

		    double Y = ((ax * ax + ay * ay) * (cx - bx) +
		                (bx * bx + by * by) * (ax - cx) +
		                (cx * cx + cy * cy) * (bx - ax)) / denominator;
		    
	          System.out.println("2-Center X : " + X);
	          System.out.println("2-Center X : " + Y);
		    return new Point((int)X,(int)Y);
		}
	  
	  public static Point calculateCircumcenter2(Point p, Point q, Point r) {
		  
		  double cX,cY;
		  
          double mX=.5*(p.x+q.x);
          double mY=.5*(p.y+q.y);
          double nX=.5*(p.x+r.x);
          double nY=.5*(p.y+r.y);
          //soit y=alpha1*x+beta1 l'equation de la droite passant par m et perpendiculaire a la droite (pq)
          //soit y=alpha2*x+beta2 l'equation de la droite passant par n et perpendiculaire a la droite (pr)
          double alpha1=(q.x-p.x)/(double)(p.y-q.y);
          double beta1=mY-alpha1*mX;
          double alpha2=(r.x-p.x)/(double)(p.y-r.y);
          double beta2=nY-alpha2*nX;
          //le centre c du cercle est alors le point d'intersection des deux droites ci-dessus
          cX=(beta2-beta1)/(double)(alpha1-alpha2);
          cY=alpha1*cX+beta1;
          System.out.println("1-Center x : " + cX);
          System.out.println("1-Center y : " + cY);
          return new Point((int)cX,(int)cY);
		}
	  public Circle trivial(ArrayList<Point> R) {
		  if(R.size() == 1) {
			  return new Circle(R.get(0), 0);
		  }
		  if(R.size() == 2) {
			  Point p = R.get(0);
			  Point q = R.get(1);
			  
			  Point Centre = new Point((p.x+q.x)/2, (p.y+q.y)/2);
			  double dist = p.distance(q) / 2;
			  return new Circle(Centre, (int)dist);
		  }
		  if(R.size() == 3) {
			  Point a = R.get(0);
			  Point b = R.get(1);
			  Point c = R.get(2);
			  
			  //Point circum = calculateCircumcenter(a,b,c);
			  Point circum = calculateCircumcenter2(a,b,c);
			  double dist = circum.distance(a);
			  
			  return new Circle(circum, (int)dist);
		  }
		  System.out.println("returning empty");
		  return new Circle(new Point(0,0), 0);
	  }
	  
	  public Circle Welzl(ArrayList<Point> points, ArrayList<Point> R) {
		    // Base case
		    if (points.isEmpty() || R.size() == 3) {
		        return trivial(R);
		    }

		    // Select a random point and create a new list without that point
		    Random rand = new Random();
		    int rand_ind = rand.nextInt(points.size());
		    Point p = points.get(rand_ind);

		    ArrayList<Point> newPoints = new ArrayList<>(points);
		    newPoints.remove(rand_ind);

		    // Recursive call without adding p to R
		    Circle D = Welzl(newPoints, R);

		    // If p is inside the circle, return the same circle
		    if (D.getCenter().distance(p) <= D.getRadius()) {
		        return D;
		    }

		    // Otherwise, create a new list for R with p added
		    ArrayList<Point> newR = new ArrayList<>(R);
		    newR.add(p);

		    // Recursive call with the new boundary points
		    return Welzl(newPoints, newR);
		}
}
