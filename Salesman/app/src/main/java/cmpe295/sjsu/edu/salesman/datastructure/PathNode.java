package cmpe295.sjsu.edu.salesman.datastructure;


import java.util.ArrayList;
import java.util.List;

import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/21/15.
 */
public class PathNode {


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    private Point point;

    public List<PathNode> getNeighbors() {
        return neighbors;
    }

    private List<PathNode> neighbors;

    public PathNode(Point point) {
        this.point = point;
        neighbors = new ArrayList<PathNode>();
    }

    public void addNeighbors(PathNode node){
        neighbors.add(node);
    }

}

