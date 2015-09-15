package cz.cvut.atg.zui.astar.klimoka1;

import eu.superhub.wp5.planner.planningstructure.GraphNode;

/**
 * Created by Kata on 13.3.14.
 */
public class Node {
    private GraphNode gn;
    private double g;
    private double h;
    private Node ancestor;


    public Node(GraphNode gn, double g, double h, Node ancestor){
        this.gn = gn;
        this.g = g;
        this.h = h;
        this.ancestor = ancestor;
    }

    public double getf(){
        return this.g+this.h;
    }

    public double getg(){
        return this.g;
    }

    public GraphNode getNode(){
        return this.gn;
    }

    public Node getAncestor(){
        return this.ancestor;
    }

    public long getId(){
        return gn.getId();
    }

    public void setg (double new_g){
        this.g = new_g;
    }

    public void setAncestor (Node anc){

        ancestor = anc;
    }



}
