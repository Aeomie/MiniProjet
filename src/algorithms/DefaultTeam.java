package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une paire de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(0);
    Point q=points.get(1);

    /*******************
     * PARTIE A ECRIRE *
     *******************/

    return new Line(p,q);
  }

	/**
	 * Function that's called to draw the Circle in the GUI
	 * you can alternate by commenting the Welzl or Naive.
	 */
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    ArrayList<Point> points_copy = (ArrayList<Point>) points.clone();
    
    Mec mec = new Mec();
    Circle res = mec.Welzl(points_copy, new ArrayList<>());
    //Circle res = mec.calculCercleMin_Naive(points);
    System.out.println(" res circle : Center : " + res.getCenter().x + " , " + res.getCenter().y + " , radius : " + res.getRadius());
    return res;
  }
  

}
