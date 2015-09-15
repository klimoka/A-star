package cz.cvut.atg.zui.astar.klimoka1;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.util.*;

/**
 * Created by Kata on 12.3.14.
 */
public class OpenList extends AbstractOpenList {

    List <Node> ol = new ArrayList<Node>();

    public OpenList(){};

    @Override
    protected boolean addItem(Object item) {
        if(ol.contains(item)){
            return true;
        }
        ol.add((Node)item);
        return false;
    }

    public boolean isEmpty(){
        if (ol.size() == 0){
            return true;
        }
        return false;
    }

    public Node getNodeWithLowestF(){
        Node node = ol.get(0);
        double minimum = node.getf();
        for(int i = 1; i<ol.size(); i++){
            Node newN = ol.get(i);
            if (newN.getf() < minimum){
                minimum = newN.getf();
                node = newN;
            }
        }
        return node;
    }

    public void removeNode(Node node){
        ol.remove(node);
    }

    public Node getNodeById (long id){
        for(int i = 0; i<ol.size(); i++){
            Node newN = ol.get(i);
            if(newN.getId() == id){
                return newN;
            }
        }
        return null;
    }

    public boolean containsNode (GraphNode n){
        for(int i = 0; i < ol.size(); i++){
            if(ol.get(i).getNode().equals(n)){
                return true;
            }
        }
        return false;
    }
}
