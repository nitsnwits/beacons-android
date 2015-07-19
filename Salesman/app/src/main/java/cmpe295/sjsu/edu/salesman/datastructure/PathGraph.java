package cmpe295.sjsu.edu.salesman.datastructure;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cmpe295.sjsu.edu.salesman.algorithm.Constants;
import cmpe295.sjsu.edu.salesman.algorithm.Utility;
import cmpe295.sjsu.edu.salesman.pojo.Point;

/**
 * Created by jijhaver on 6/21/15.
 */
public class PathGraph {
    private static final String TAG = PathGraph.class.getSimpleName();

    private PathNode rootNode;
    public HashMap<Point, PathNode> poiNodeMap;

    public PathGraph(){
        poiNodeMap = new HashMap<>();
    }
    public void storePath()
    {
        // Define POI
        PathNode node1 = new PathNode(new Point(0.315, 1.0)); // Entry Node
        PathNode node2 = new PathNode(new Point(0.315, 0.88));
        PathNode node3 = new PathNode(new Point(0.685, 0.88));
        PathNode node4 = new PathNode(Constants.BEACON_2_POINT);
        PathNode node5 = new PathNode(Constants.BEACON_1_POINT);
        PathNode node6 = new PathNode(new Point(0.315, 0.05));
        PathNode node7 = new PathNode(new Point(0.685, 0.05));
        PathNode node8 = new PathNode(new Point(0.315, 0.0));
        PathNode node9 = new PathNode(new Point(0.685, 0.0));
        PathNode node10 = new PathNode(Constants.BEACON_4_POINT);
        PathNode node11 = new PathNode(Constants.BEACON_3_POINT);
        PathNode node12 = new PathNode(new Point(0.685, 1.0)); // Exit Node

        // Add Neighbors
        node1.addNeighbors(node2);
        node2.addNeighbors(node1);
        node2.addNeighbors(node3);
        node2.addNeighbors(node4);
        node3.addNeighbors(node2);
        node3.addNeighbors(node11);
        node3.addNeighbors(node12);
        node4.addNeighbors(node2);
        node4.addNeighbors(node5);
        node5.addNeighbors(node4);
        node5.addNeighbors(node6);
        node6.addNeighbors(node5);
        node6.addNeighbors(node8);
        node6.addNeighbors(node7);
        node7.addNeighbors(node6);
        node7.addNeighbors(node9);
        node7.addNeighbors(node10);
        node8.addNeighbors(node6);
        node9.addNeighbors(node7);
        node10.addNeighbors(node7);
        node10.addNeighbors(node11);
        node11.addNeighbors(node10);
        node11.addNeighbors(node3);
        node12.addNeighbors(node3);

        rootNode = node1;


        poiNodeMap.put(Constants.BEACON_1_POINT,node5);
        poiNodeMap.put(Constants.BEACON_2_POINT,node4);
        poiNodeMap.put(Constants.BEACON_3_POINT,node11);
        poiNodeMap.put(Constants.BEACON_4_POINT,node10);

    }

    public List<List<Point>> getAllPath(Point pointA, Point pointB) {
        List<List<Point>> pathList = new ArrayList<>();
        if (poiNodeMap.containsKey(pointA)){
            PathNode sourceNode =  poiNodeMap.get(pointA);
            Set<PathNode> visitedNodeSet = new HashSet<>();
            List<Point> path = new ArrayList<>();
            getAllPath(sourceNode, pointB, path, pathList, visitedNodeSet);
        }
        return  pathList;
    }

    private void getAllPath(PathNode sourceNode, Point destinationPoint, List<Point> path, List<List<Point>> pathList, Set<PathNode> visitedNodeSet) {

        if(visitedNodeSet.contains(sourceNode))
            return;

        visitedNodeSet.add(sourceNode);
        path.add(sourceNode.getPoint());
        List<PathNode> neighborList = sourceNode.getNeighbors();
        for (int i = 0; i < neighborList.size(); i++) {
            PathNode neighbor = neighborList.get(i);
            if (!visitedNodeSet.contains(neighbor)){
                Point closestPoint = PathUtility.getClosestPointOnSegment(sourceNode.getPoint(), neighbor.getPoint(), destinationPoint);
                double distanceToPath = PathUtility.distanceOfTwoPoints(closestPoint, destinationPoint);
                if( distanceToPath < Constants.CLOSEST_DISTANCE_TO_PATH ){
                    path.add(closestPoint);
                    pathList.add(path);
                    return;
                }
                List<Point> pathCopy = new ArrayList<>(path);
                Log.i(TAG, sourceNode.getPoint().toString()+" -->> "+ neighbor.getPoint().toString()+" || Path: " + Utility.ListToString(pathCopy));
                getAllPath(neighbor, destinationPoint, pathCopy, pathList, visitedNodeSet);
            }
        }
    }

}
