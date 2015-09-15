/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testovaci;

/**
 *
 * @author Kata
 */
public class Node {
    private long gn;
    private double g;
    private double h;
    private Node ancestor;


    public Node(long gn, double g, double h, Node ancestor){
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

    public long getNode(){
        return this.gn;
    }

    public Node getAncestor(){
        return this.ancestor;
    }

    public long getId(){
        return gn;
    }

    public double geth(){
        return this.h;
    }

    public void setg (double new_g){
        this.g = new_g;
    }

    public void setAncestor (Node anc){
        ancestor = anc;
    }

    public void seth (double h){
        this.h = h;
    }
}
