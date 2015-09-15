/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testovaci;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kata
 */
public class OpenList {
    List <Node> ol = new ArrayList<Node>();

    public OpenList(){};

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

    public Node getNodeWithLowestf(){
        Node node = ol.get(0);
        double minimum = node.getf();
        for(int i = 1; i<ol.size(); i++){
            Node newn = ol.get(i);
            if (newn.getf() < minimum){
                minimum = newn.getf();
                node = newn;
            }
        }
        return node;
    }

    public void removeNode(Node node){
        ol.remove(node);
    }

    public Node getNodeById (long id){
        for(int i = 0; i<ol.size(); i++){
            Node newn = ol.get(i);
            if(newn.getId() == id){
                return newn;
            }
        }
        return null;
    }

    public boolean containsNode (long n){
        for(int i = 0; i < ol.size(); i++){
            if(ol.get(i).getNode() == n){
                return true;
            }
        }
        return false;
    }
}
