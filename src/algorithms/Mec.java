package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import supportGUI.Circle;

/**
Class that contains the functions
Mec stands for Minimum englobing circle
 */
public class Mec {
	
	/**
	 * Function that takes into parameter 3 points and returns their Circumcenter.
	 * (taken from Prof's TME1)
	 */
	  public static Point calculateCircumcenter(Point p, Point q, Point r) {
		  
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
          return new Point((int)cX,(int)cY);
		}
	  
	/**
	 * Function that takes an array of points R and returns the circle englobing those points depending on the size.
	 */
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
			  
			  Point circum = calculateCircumcenter(a,b,c);
			  double dist = circum.distance(a);
			  
			  return new Circle(circum, (int)dist);
		  }
		  return new Circle(new Point(0,0), 0);
	  }
	  
	  
	/**
	 * Welzl Algorithm 
	 * Takes two inputs , Array of points (points) and an empty Array of points (R)
	 * It works by calling itself recursively and it stops when P is empty or R equals 3.
	 */
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
	  
	  
		/**
		 * Naive englobing Circle algorithm.
		 * It takse an ArrayList of points as parameter.
		 * (Taken also from Prof's TME1)
		 */
	  public Circle calculCercleMin_Naive(ArrayList<Point> inputPoints) {
	      ArrayList<Point> points = (ArrayList<Point>) inputPoints.clone();
	      if (points.size()<1) return null;
	      double cX,cY,cRadius,cRadiusSquared;
	      for (Point p: points){
	          for (Point q: points){
	              cX = .5*(p.x+q.x);
	              cY = .5*(p.y+q.y);
	              cRadiusSquared = 0.25*((p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y));
	              boolean allHit = true;
	              for (Point s: points)
	                  if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
	                      allHit = false;
	                      break;
	                  }
	              if (allHit) return new Circle(new Point((int)cX,(int)cY),(int)Math.sqrt(cRadiusSquared));
	          }
	      }
	      double resX=0;
	      double resY=0;
	      double resRadiusSquared=Double.MAX_VALUE;
	      for (int i=0;i<points.size();i++){
	          for (int j=i+1;j<points.size();j++){
	              for (int k=j+1;k<points.size();k++){
	                  Point p=points.get(i);
	                  Point q=points.get(j);
	                  Point r=points.get(k);
	                  //si les trois sont colineaires on passe
	                  if ((q.x-p.x)*(r.y-p.y)-(q.y-p.y)*(r.x-p.x)==0) continue;
	                  //si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les echange
	                  if ((p.y==q.y)||(p.y==r.y)) {
	                      if (p.y==q.y){
	                          p=points.get(k); //ici on est certain que p n'est sur la meme ligne de ni q ni r
	                          r=points.get(i); //parce que les trois points sont non-colineaires
	                      } else {
	                          p=points.get(j); //ici on est certain que p n'est sur la meme ligne de ni q ni r
	                          q=points.get(i); //parce que les trois points sont non-colineaires
	                      }
	                  }
	                  //on cherche les coordonnees du cercle circonscrit du triangle pqr
	                  //soit m=(p+q)/2 et n=(p+r)/2
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
	                  cRadiusSquared=(p.x-cX)*(p.x-cX)+(p.y-cY)*(p.y-cY);
	                  if (cRadiusSquared>=resRadiusSquared) continue;
	                  boolean allHit = true;
	                  for (Point s: points)
	                      if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
	                          allHit = false;
	                          break;
	                      }
	                  if (allHit) {resX=cX;resY=cY;resRadiusSquared=cRadiusSquared;}
	              }
	          }
	      }
	      return new Circle(new Point((int)resX,(int)resY),(int)Math.sqrt(resRadiusSquared));
	  }  
}
