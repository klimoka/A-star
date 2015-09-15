package cz.cvut.atg.zui.astar.klimoka1;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.util.*;

/**
 * Created by Kata on 12.3.14.
 */
public class Planner implements PlannerInterface {

    private List<GraphEdge> path = new ArrayList<GraphEdge>();
    private double maxSpeed;
    private OpenList ol = new OpenList();

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {
        return AStar(graph, origin, destination);
    }

    @Override
    public AbstractOpenList getOpenList() {
        return ol;
    }

    private List<GraphEdge> AStar (RoadGraph graph, GraphNode origin, GraphNode goal){
        Set <Long> cl = new HashSet<Long>();
        Node node = new Node(origin, 0, countH(origin,goal), null);
        ol.add(node);
        maxSpeed = findTheHighestSpeed(graph);

        while (!ol.isEmpty()){
            node = ol.getNodeWithLowestF();
            if (node.getNode().equals(goal)){
                Collections.reverse(reconstructPath(node, graph));
                return path;
            } else{
                ol.removeNode(node);
                cl.add(node.getId());

                List<GraphEdge> edges= graph.getNodeOutcomingEdges(node.getNode().getId());
                List<GraphNode> neighbours = new ArrayList<GraphNode> ();
                List<GraphNode> orig = new ArrayList<GraphNode> ();

                if (edges != null){
                    for (GraphEdge edge : edges){
                        neighbours.add(graph.getNodeByNodeId(edge.getToNodeId()));
                        orig.add(graph.getNodeByNodeId(edge.getFromNodeId()));
                    }
                    for(int i = 0; i<neighbours.size(); i++ ){
                        long idTested = neighbours.get(i).getId();
                        double new_g = node.getg() + countG(node.getNode(), neighbours.get(i), graph);
                        if (!cl.contains(idTested)){
                            if (!ol.containsNode(neighbours.get(i))){
                                Node newNode = new Node(neighbours.get(i), new_g, countH(neighbours.get(i),goal), node);
                                ol.add(newNode);
                                if(idTested == goal.getId()){
                                }
                            }
                            else{
                                Node testedNode = ol.getNodeById(idTested);
                                if (new_g < testedNode.getg()){
                                    testedNode.setg(new_g);
                                    testedNode.setAncestor(node);
                                }

                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /*
        Create list of edges by returning from goal state by ancestors. The order of edges is inverted.
     */
    private List<GraphEdge> reconstructPath (Node node, RoadGraph graph){
        if (node.getAncestor() != null){
            path.add(graph.getEdge(node.getAncestor().getId(), node.getId()));
            reconstructPath(node.getAncestor(), graph);
        }
        return path;
    }

    private double findTheHighestSpeed(RoadGraph graph){
        Collection<GraphEdge> edges = graph.getAllEdges();
        double max = 0;
        for(GraphEdge edge : edges){
            double speed = edge.getAllowedMaxSpeedInKmph();
            if(max < speed){
                max = speed;
            }
        }
        return max;
    }

    private double countH (GraphNode actual, GraphNode goal){
        double distance = Utils.distanceInKM(actual, goal);
        return distance/this.maxSpeed;
    } 
    private double countG (GraphNode actualNode, GraphNode nextNode, RoadGraph graph){
        GraphEdge edge = graph.getEdge(actualNode.getId(), nextNode.getId());
        double distance = edge.getLengthInMetres()/1000;
        double speed = edge.getAllowedMaxSpeedInKmph();
        return distance/speed;
    }
}
