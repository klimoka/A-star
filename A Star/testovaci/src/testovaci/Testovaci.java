/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testovaci;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Kata
 */
public class Testovaci {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long goal = 5432;
        OpenList ol = new OpenList();
        Set <Node> cl = new HashSet<Node>();
        Node node = new Node(123, 0, 0, null);
        ol.addItem(node);
        
        while (!ol.isEmpty()){
            node = ol.getNodeWithLowestf();
            if (node.getNode() == goal){
                System.out.println("konec");//reconstructPath(node.getAncestor(), graph);
            } else{
                ol.removeNode(node);
                cl.add(node);

            //    List<GraphEdge> edges= graph.getNodeOutcomingEdges(node.getNode().getId());
            //    List<GraphNode> neighbours = new ArrayList<GraphNode>();
                List<long> neighbours = {234, 345};
                for(int i = 0; i<neighbours.size(); i++ ){
                    long idTested = neighbours.get(i).getId();
                    Node testedNode = ol.getNodeById(idTested);
                    double new_g = node.getg() + graph.getEdge(node.getNode().getId(), idTested).getLengthInMetres();
                    if (!ol.containsNode(neighbours.get(i))){
                        Node newNode = new Node(neighbours.get(i), new_g, 0, node);
                        ol.add(newNode);
                    }
                    else{
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
