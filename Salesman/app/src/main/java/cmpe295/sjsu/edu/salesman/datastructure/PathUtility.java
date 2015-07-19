package cmpe295.sjsu.edu.salesman.datastructure;

import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 7/11/15.
 */
public class PathUtility {

    public static double distanceOfTwoPoints(Point pp1, Point pp2)
    {
        double result;
        result = Math.sqrt(Math.pow((pp2.getX()-pp1.getX()),2)+ Math.pow((pp2.getY()-pp1.getY()),2));
        return result;
    }

    public static Point getClosestPointOnSegment(Point ss, Point se, Point p)
    {
        return getClosestPointOnSegment(ss.getX(), ss.getY(), se.getX(), se.getY(), p.getX(), p.getY());
    }

    /**
     * Returns closest point on segment to point
     *
     * @param sx1
     *            segment x coord 1
     * @param sy1
     *            segment y coord 1
     * @param sx2
     *            segment x coord 2
     * @param sy2
     *            segment y coord 2
     * @param px
     *            point x coord
     * @param py
     *            point y coord
     * @return closets point on segment to point
     */
    public static Point getClosestPointOnSegment(double sx1, double sy1, double sx2, double sy2, double px, double py)
    {
        double xDelta = sx2 - sx1;
        double yDelta = sy2 - sy1;

        if ((xDelta == 0) && (yDelta == 0))
        {
            throw new IllegalArgumentException("PathUtility ERROR: Segment start equals segment end");
        }

        double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final Point closestPoint;
        if (u < 0)
        {
            closestPoint = new Point(sx1, sy1);
        }
        else if (u > 1)
        {
            closestPoint = new Point(sx2, sy2);
        }
        else
        {
            closestPoint = new Point((double) (sx1 + u * xDelta), (double) (sy1 + u * yDelta));
        }

        return closestPoint;
    }

}
