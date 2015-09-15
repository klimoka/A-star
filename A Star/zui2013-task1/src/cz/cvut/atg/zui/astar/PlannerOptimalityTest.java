package cz.cvut.atg.zui.astar;

import eu.superhub.wp5.graphcommon.graph.Graph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Jakub Kulhan <jakub.kulhan@gmail.com>
 */
@RunWith(Parameterized.class)
public class PlannerOptimalityTest {

    private static RoadGraph graph = null;

    private int planId;

    private long originNodeId;

    private long destinationNodeId;

    private double travelledDistance;

    private double travelledTime;

    private int expandedNodesCount;

    private PlannerInterface planner;

    public PlannerOptimalityTest(
            int planId,
            long originNodeId,
            long destinationNodeId,
            double travelledDistance,
            double travelledTime,
            int expandedNodesCount
    ) {
        this.planId = planId;
        this.originNodeId = originNodeId;
        this.destinationNodeId = destinationNodeId;
        this.travelledDistance = travelledDistance;
        this.travelledTime = travelledTime;
        this.expandedNodesCount = expandedNodesCount;
    }

    private static RoadGraph getGraph() {
        if (graph == null) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("./data/ukhigh_filtered.dat")));
                graph = new RoadGraph((Graph<GraphNode, GraphEdge>) in.readObject());

            } catch (IOException  e) {
                e.printStackTrace();
                System.exit(1);

            } catch (ClassNotFoundException e){
                e.printStackTrace();
                System.exit(1);
            } finally{
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        }

        return graph;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { 0, 830503005L, 1272039973L, -1.0, -1.0, 8350 },
                { 1, 1510516031L, 367105234L, 505.28325903083544, 5.634859916738966, 56613 },
                { 2, 21291551L, 256606542L, 192.94266751177557, 2.3248922807328434, 11765 },
                { 3, 351122923L, 7535730L, 111.22588714856649, 1.4641527704438069, 4944 },
                { 4, 784025074L, 1049868519L, 267.9852512355414, 3.0123267608817748, 27282 },
                { 5, 310158916L, 26818013L, -1.0, -1.0, 8346 },
                { 6, 1076870201L, 100131519L, 124.92305866275532, 1.461813778200056, 1062 },
                { 7, 286417186L, 387265870L, -1.0, -1.0, 100252 },
                { 8, 445586L, 36242702L, 717.2136288651033, 8.427976120500633, 59017 },
                { 9, 338440529L, 9593581L, 87.46201585512289, 1.5968255734242158, 11101 },
                { 10, 525168333L, 28091454L, 361.80545301860013, 4.78349675767654, 22901 },
                { 11, 17406884L, 363879L, 358.31059110318836, 4.639805947998393, 25993 },
                { 12, 30631836L, 29795575L, 342.6523416349778, 3.973093018544946, 34348 },
                { 13, 324537719L, 768562929L, 44.079759296828975, 0.8436822800216391, 2775 },
                { 14, 310651040L, 253910114L, 189.61810293425526, 1.91402078225799, 14357 },
                { 15, 928132202L, 48751770L, 152.8142318234716, 2.480404889533194, 16333 },
                { 16, 521574571L, 25258671L, -1.0, -1.0, 8343 },
                { 17, 1398416950L, 434471319L, -1.0, -1.0, 100261 },
                { 18, 9695990L, 243775764L, 186.9624867483411, 2.487900576503735, 14686 },
                { 19, 26955471L, 424857L, 552.2012823792319, 7.060555471202515, 60899 },
                { 20, 243741241L, 9709789L, 193.06593214621364, 3.0344299058406516, 27644 },
                { 21, 456190832L, 3690909L, 350.23229289539574, 3.880170767957607, 44733 },
                { 22, 10310553L, 18161338L, -1.0, -1.0, 100244 },
                { 23, 290650L, 738840810L, -1.0, -1.0, 100277 },
                { 24, 344711775L, 21697555L, 82.65642805818553, 1.1876536911848692, 2690 },
                { 25, 1060488958L, 20938406L, 387.4021882002194, 4.242261086969107, 40955 },
                { 26, 289258980L, 399343667L, 625.6051996424271, 7.289708474730303, 43267 },
                { 27, 258639256L, 1320934643L, 450.966496864562, 5.778209192279105, 14883 },
                { 28, 1246083738L, 28832273L, 245.09239589521388, 3.5490739100235063, 23047 },
                { 29, 1248244781L, 253615326L, 49.20284640106114, 0.7390874267940682, 426 },
                { 30, 29786641L, 20915242L, 274.2268686536462, 4.240567935464495, 5906 },
                { 31, 1498792185L, 1299605695L, 71.74264519499228, 0.8502140464560927, 4603 },
                { 32, 27462973L, 569506L, 106.37800863079215, 1.146286615583469, 3620 },
                { 33, 746713679L, 248780953L, 85.65723123948253, 0.8113917192285394, 2620 },
                { 34, 248002729L, 39006653L, -1.0, -1.0, 100301 },
                { 35, 303541307L, 49262935L, 335.47714637370336, 3.578727660073075, 33479 },
                { 36, 14354414L, 821884269L, 205.6650513656645, 2.379025996088068, 12719 },
                { 37, 2122993L, 25310809L, 713.3510523424129, 8.143991743070696, 75774 },
                { 38, 25760191L, 1481372128L, 68.09661063226123, 0.9449551625971633, 4463 },
                { 39, 9920444L, 254957909L, 204.29609592094818, 2.711286496410064, 20393 },
                { 40, 31193581L, 282226268L, 708.7483977642191, 8.222837331880736, 62156 },
                { 41, 359263362L, 1056672076L, 316.1915749612663, 3.858936245698194, 33594 },
                { 42, 950151614L, 81718501L, 343.1551257971224, 3.878458099264671, 39389 },
                { 43, 837466297L, 294391L, 205.7924650257218, 2.6330723214986045, 22352 },
                { 44, 1270957231L, 13800648L, 310.14751836031377, 3.4756004548372412, 25766 },
                { 45, 18040107L, 1470753803L, 237.84447995171678, 2.7828620710509018, 30466 },
                { 46, 21715027L, 21419350L, 284.02035696131605, 3.4760600369909462, 18619 },
                { 47, 1141958966L, 1431410631L, 79.48929321886541, 1.0748039643886689, 7209 },
                { 48, 32097711L, 255429504L, 324.0489080897771, 4.457440166927573, 23377 },
                { 49, 26660959L, 1529270514L, 274.74908457090885, 2.5279685483712138, 12410 },
                { 50, 27145770L, 355487L, 202.31810876376284, 2.401993161463687, 14384 },
                { 51, 1014186L, 279266092L, 206.12726750471586, 2.2712828984577627, 15329 },
                { 52, 45350713L, 248098549L, 883.8703024480882, 10.847082845474334, 66568 },
                { 53, 458731354L, 636063526L, 302.7383287663944, 3.291857115378281, 37475 },
                { 54, 25608420L, 264258340L, 103.82887513516077, 1.6236984239185814, 7296 },
                { 55, 248631320L, 240762577L, 323.29904792509257, 4.3562259465458855, 22679 },
                { 56, 109887L, 1469977579L, 209.0650188860661, 2.1872949497189853, 18526 },
                { 57, 1002563923L, 607011L, 251.09891707912533, 2.5421525508616383, 16938 },
                { 58, 26449422L, 308233869L, 82.99259499703312, 0.8618340541333548, 556 },
                { 59, 25609422L, 512460L, 170.3728438465664, 2.0571619747797314, 17928 },
                { 60, 1221958496L, 27229321L, 289.8783415681635, 2.8975791915958, 11713 },
                { 61, 249188607L, 534403974L, 192.3140834461226, 3.1935568072866367, 6269 },
                { 62, 30484858L, 9832491L, -1.0, -1.0, 8352 },
                { 63, 330984519L, 12088070L, 193.6896817214694, 1.8828425789049847, 7980 },
                { 64, 697425527L, 317462953L, 246.16937945673277, 2.6050841166819096, 8801 },
                { 65, 1035458596L, 59778000L, 645.7660654255736, 7.894576107248197, 82691 },
                { 66, 616726453L, 818375053L, -1.0, -1.0, 100290 },
                { 67, 7651955L, 696697193L, 417.6812458864955, 5.30670961453919, 27791 },
                { 68, 686557035L, 124290973L, 496.05386908841274, 6.1772438292726735, 51301 },
                { 69, 21548409L, 236452L, 387.97499999502395, 5.249451798923959, 10679 },
                { 70, 36139347L, 727708576L, 204.1777873719532, 3.577472410415849, 13761 },
                { 71, 720832L, 362905839L, -1.0, -1.0, 100243 },
                { 72, 419886946L, 206233977L, 400.17726806226244, 5.172379611683622, 62965 },
                { 73, 823834L, 408253L, 625.0166071383607, 7.372357402461062, 38609 },
                { 74, 16781221L, 400834L, 200.11993979675833, 2.2937931976955537, 17334 },
                { 75, 60282502L, 673845574L, 125.50823845120647, 1.7858855112619731, 10767 },
                { 76, 31082001L, 539418851L, 899.1089672996698, 11.05274486868848, 97183 },
                { 77, 25915779L, 292187L, 22.0946492690427, 0.44523128593847117, 2874 },
                { 78, 35738701L, 1139750417L, 600.819916932926, 7.576681213226208, 19928 },
                { 79, 585392L, 20940342L, 309.36970941624224, 2.907586100464345, 17645 },
                { 80, 12774759L, 243900531L, 513.7701974765076, 5.029736087510673, 41698 },
                { 81, 392366513L, 1392201009L, 77.63260521048761, 1.400414588359563, 1346 },
                { 82, 1056672099L, 1016685820L, 436.89887024459796, 4.938667188130196, 52052 },
                { 83, 680365L, 32922303L, 729.432301908261, 8.410126663709041, 69447 },
                { 84, 361691289L, 1541607994L, 122.46995834374712, 1.2926727358014771, 7677 },
                { 85, 673594L, 247083294L, 680.1594829646939, 7.728719014166675, 85273 },
                { 86, 203998L, 71151677L, 183.44761213783576, 1.9822471410487466, 12030 },
                { 87, 30174387L, 22952187L, 395.45867508116413, 5.127655422482828, 52501 },
                { 88, 389466304L, 347402897L, -1.0, -1.0, 100237 },
                { 89, 477085263L, 735149148L, 371.48331982807144, 4.331665472069332, 54307 },
                { 90, 290762687L, 32037872L, 247.05282742462697, 2.299425740838131, 18524 },
                { 91, 926864L, 2388079L, -1.0, -1.0, 100270 },
                { 92, 1331472262L, 668008L, 241.836866633311, 3.4327570730905745, 8256 },
                { 93, 1141002340L, 264573649L, 274.1179754730551, 2.8295152714280136, 26919 },
                { 94, 1169109302L, 36605633L, 92.5670472996518, 1.8149340185621399, 1772 },
                { 95, 27476914L, 13986279L, 205.24947437462, 2.191830093520621, 15623 },
                { 96, 509881803L, 244133753L, -1.0, -1.0, 8315 },
                { 97, 26735400L, 26624881L, 112.11208531312445, 1.204870047209728, 2734 },
                { 98, 336124562L, 27667562L, 193.18679169354053, 2.0099557840006588, 7164 },
                { 99, 21494361L, 570833L, 655.4884649787972, 7.751517430270179, 87422 },
        });
    }

    @Before
    public void setUp() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        planner = (PlannerInterface) Class.forName("cz.cvut.atg.zui.astar.klimoka1.Planner").newInstance();
    }

    @Test
    public void testOptimality() {
        RoadGraph graph = getGraph();

        List<GraphEdge> plan = planner.plan(
                graph,
                graph.getNodeByNodeId(originNodeId),
                graph.getNodeByNodeId(destinationNodeId)
        );

        double planTravelledDistance;
        double planTravelledTime;

        if (plan == null) {
            planTravelledDistance = -1.0;
            planTravelledTime = -1.0;

        } else {
            long lastNodeId = originNodeId;
            Assert.assertEquals("path must start from origin node", originNodeId, plan.get(0).getFromNodeId());

            planTravelledDistance = 0.0;
            planTravelledTime = 0.0;

            for (GraphEdge e : plan) {
                planTravelledDistance += e.getLengthInMetres() / 1000.0;
                planTravelledTime += e.getLengthInMetres() / 1000.0 / e.getAllowedMaxSpeedInKmph();
                Assert.assertEquals("edges must be consecutive", lastNodeId, e.getFromNodeId());
                lastNodeId = e.getToNodeId();
            }

            Assert.assertEquals("path must end in destination node", destinationNodeId, lastNodeId);
        }

        int planExpandedNodesCount = planner.getOpenList().getCounter().getCount();
        planner.getOpenList().getCounter().reset();

        Assert.assertTrue(
                "distance (" + planTravelledDistance + " <= " + travelledDistance + ")",
                planTravelledDistance <= travelledDistance
        );

        Assert.assertTrue(
                "time (" + planTravelledTime + " <= " + travelledTime + ")",
                planTravelledTime <= travelledTime
        );

        Assert.assertTrue(
                "expanded nodes (" + planExpandedNodesCount + " <= " + expandedNodesCount + ")",
                planExpandedNodesCount <= expandedNodesCount
        );
    }

}