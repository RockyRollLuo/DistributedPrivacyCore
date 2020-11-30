package algorithms;

import model.Graph;
import model.Result;
import org.apache.log4j.Logger;
import util.GetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DPCoreDecompIncre {
    private static Logger LOGGER = Logger.getLogger(DPCoreDecompIncre.class);

    private final Graph graph;

    public DPCoreDecompIncre(Graph graph) {
        this.graph = graph;
    }

    public Result run() {
        long startTime = System.nanoTime();

        HashMap<Integer, ArrayList<Integer>> adjMap = graph.getAdjMap();
        HashMap<Integer, Integer> degMap = graph.getDegMap();

        degMap = (HashMap<Integer, Integer>) GetUtils.getSortMapByValue(degMap, 1);
        Set<Integer> nodeListByDegereeAcending = degMap.keySet();

        HashSet<Integer> accomplishNodes = new HashSet<>();
        //estCoreMap
        HashMap<Integer, Integer> estCoreMap = new HashMap<>();
        for (Integer node : nodeListByDegereeAcending) {
            estCoreMap.put(node, 1);
        }

        int roundIndex = 0;
        int changedNum = 0;

        /*
         * rounds
         */
        do {
            roundIndex++;
            changedNum = 0;

            /*
             * 1.the first subRound in this round
             */
            HashSet<Integer> unChangeNodes = new HashSet<>();
            for (Integer u : nodeListByDegereeAcending) {
                int estCoreOfU = estCoreMap.get(u);

                //count support
                int support = 0;
                ArrayList<Integer> neighborsOfU = adjMap.get(u);
                for (Integer neighborOfU : neighborsOfU) {
                    /*
                     * estCore is no less than the roundIndex  &&
                     * degree greater thant roundIndex, current round only degree greater than roundIndex
                     * then send 1 to neighbor
                     */
                    if (estCoreMap.get(neighborOfU) >= roundIndex && degMap.get(neighborOfU) > roundIndex) {
                        support++;
                    }
                }

                //update estCore
                if (support > roundIndex) {
                    estCoreMap.put(u, estCoreOfU + 1);
                    changedNum++;  //exist update
                } else if (support == roundIndex) { //nodes that support smaller than roundIndex have been determined
                    unChangeNodes.add(u);
                    accomplishNodes.add(u);
                }
            }

            /*
             * 2.the subsequent subRound in this round
             * recomputed support
             */
            while (unChangeNodes.size() != 0) {
                HashSet<Integer> newUnChangeNodes = new HashSet<>();

                for (Integer u : unChangeNodes) {

                    for (Integer v : adjMap.get(u)) {
                        if (accomplishNodes.contains(v)) continue;

                        int support = 0;
                        ArrayList<Integer> neighborsOfV = adjMap.get(v);
                        for (Integer w : neighborsOfV) {
                            if (estCoreMap.get(w) > roundIndex) { //estCore is no less than the round then send bit
                                support++;
                            }
                        }

                        if (support <= roundIndex) { //some node may has many neighbors not increase
                            estCoreMap.put(v, roundIndex);
                            newUnChangeNodes.add(v);
                            accomplishNodes.add(v);
                        }
                    }
                }
                unChangeNodes = newUnChangeNodes;
//                LOGGER.info("=subRound=" + roundIndex + " estCoreMap=" + estCoreMap.toString());
            }
//            LOGGER.info("==Round=" + roundIndex + " estCoreMap=" + estCoreMap.toString());
        } while (changedNum != 0);

        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.info("TakenTime:" + takenTime + "\n" + "TotalRound:" + roundIndex);

        return new Result(estCoreMap, takenTime, roundIndex, "DPCoreDecompIncre");
    }
}