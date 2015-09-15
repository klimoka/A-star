package cz.cvut.atg.zui.astar;

import eu.superhub.wp5.graphcommon.graph.Graph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Evaluation of players' planners.
 * Author: Ondrej Vanek (e-mail: ondrej.vanek@agents.fel.cvut.cz)
 * Date: 3/11/13
 * Time: 11:33 AM.
 */
public class AStarEvaluator {

    public static final int TRIALS = 100;
    Random random = new Random(1);

    public static final String DATA_FILE = "./data/ukhigh_filtered.dat";
    public static final String OUT_DIR = "./results";

    public static void main(String[] args) throws IOException {
        //if(args.length==0) throw new IllegalArgumentException("Number of arguments is 0. Please input player name.");
        AStarEvaluator executor = new AStarEvaluator();
        String name="klimoka1";

        if(args.length>0) {
            //name = args[0];
            if(name.endsWith("/")){
                name = name.substring(0,name.length()-1);
            }
        }


        try {
            executor.run(DATA_FILE, name);
        } catch (Exception e) {
            e.printStackTrace();
            BufferedWriter bwerr = new BufferedWriter(new FileWriter("errors.log",true));
            bwerr.append(name+"\n");
            bwerr.close();
        }
    }

    private void run(String mapFileName, String playerName) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        // load the serialized graph
        Graph<GraphNode, GraphEdge> newroadGraph = deserialize(mapFileName);
        RoadGraph roadGraph = new RoadGraph(newroadGraph);

        System.out.println("Graph loaded.");

        List<GraphNode> list = new ArrayList<GraphNode>(roadGraph.getAllNodes());
        Collections.shuffle(list,random);

        String playerDirName = OUT_DIR + "/" + playerName;
        File f = new File(playerDirName);
        f.mkdir();
        BufferedWriter bw = new BufferedWriter(new FileWriter(playerDirName+"/results.csv"));
        // writeHeader(bw);
        for(int i=1;i< 2*TRIALS;i+=2){
        //for(int i=41*2+1;i< 41*2+2;i+=2){
            System.out.println(playerName+": "+i/2+"/"+TRIALS);
            GraphNode origin = list.get(i);
            GraphNode destination = list.get(i+1);

            PlannerInterface astar = getPlayer(playerName);

            long time = System.currentTimeMillis();
            try {
                List<GraphEdge> plan = astar.plan(roadGraph,origin, destination);
                System.out.println(plan);
                long delta = (System.currentTimeMillis()-time);
                int openListSize = astar.getOpenList().getCounter().getCount();
                boolean correctPlan = checkPlan(plan, origin, destination);
                writeRecord(i/2,bw,plan,openListSize,delta,correctPlan);
            } catch (IOException e) {
                e.printStackTrace();
            } catch( Exception e ){
                e.printStackTrace();
                System.err.println("Player error");
                BufferedWriter bwerr = new BufferedWriter(new FileWriter(OUT_DIR+"/errors.log",true));
                bwerr.append(playerName+"\n");
                bwerr.append(e.getMessage()+"\n");
                bwerr.close();

                break;
            }
            astar.getOpenList().getCounter().reset();
        }
        bw.close();
    }

    private void writeHeader(BufferedWriter bw) throws IOException {
        bw.write("nr,correct,length,time,open,time");
    }

    private boolean checkPlan(List<GraphEdge> plan, GraphNode origin, GraphNode destination) {
        if(plan==null) return true;
        if(plan.get(0).getFromNodeId()!=origin.getId()){

                return false;
                    }
        if(plan.get(plan.size()-1).getToNodeId()!=destination.getId()){
                return false;
        }

        for(int i=0;i<plan.size()-1;i++){
            GraphEdge e1 = plan.get(i);
            GraphEdge e2 = plan.get(i+1);
            if(e1.getToNodeId()!=e2.getFromNodeId()){
                System.err.println("Plan's edges are not consistent");
                return false;

            }
        }
        return true;
    }

    /**
     *
     * @param i
     * @param bw
     * @param plan
     * @param openListSize
     * @param delta
     */
    private void writeRecord(int i, BufferedWriter bw, List<GraphEdge> plan, int openListSize, long delta, boolean correct) throws IOException {
        String foundSolution = "TRUE";
        if(!correct){
            foundSolution="FALSE";
        }
        bw.write(i+", "+foundSolution+", "+getPlanLength(plan)+", "+getPlanTime(plan)+", "+openListSize+","+delta+"\n");
    }

    /**
     * Prints properties of the plan.
     * @param plan plan to be analysed.
     */
    private double getPlanLength(List<GraphEdge> plan) {
        if(plan!=null){

            double planLength = 0;

            for(GraphEdge edge: plan){
                planLength+=edge.getLengthInMetres()/1000;
            }
            return planLength;
        } else{
            return -1;
        }
    }

    /**
     * Prints properties of the plan.
     * @param plan plan to be analysed.
     */
    private double getPlanTime(List<GraphEdge> plan) {
        if(plan!=null){
            double planTime = 0;
            for(GraphEdge edge: plan){
                planTime +=edge.getLengthInMetres()/1000.0/edge.getAllowedMaxSpeedInKmph();
            }
            return planTime;
        } else{
            return -1;
        }
    }

    private PlannerInterface getPlayer(String playerName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        if(playerName.equals("")){
//            return new Planner();
//        }
        String player = "cz.cvut.atg.zui.astar."+playerName+".Planner";
        Class clazz = Class.forName(player);
        return (PlannerInterface) clazz.newInstance();
    }

    /**
     * Deserializes a graph from a data file.
     * @param filename    file with the graph.
     * @return deserialized graph.
     */
    private Graph<GraphNode, GraphEdge> deserialize(String filename) {
        try{
            //use buffering
            InputStream file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream( file );
            ObjectInput input = new ObjectInputStream ( buffer );
            try{
                //deserialize the graph
                Graph<GraphNode, GraphEdge> roadGraph = (Graph<GraphNode, GraphEdge>) input.readObject();
                return roadGraph;
            }
            finally{
                input.close();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //This shouldn't happen! Luckily, one does not simply reach this line.
        System.exit(1);
        return null;
    }

}
