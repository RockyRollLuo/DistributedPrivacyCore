package algorithms;

import model.Graph;
import model.Result;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DPCoreDecompDecr {
    private static Logger LOGGER = Logger.getLogger(DPCoreDecompDecr.class);

    private final Graph graph;

    public DPCoreDecompDecr(Graph graph) {
        this.graph = graph;
    }

    public Result run() {
        long startTime = System.nanoTime();

        HashMap<Integer, ArrayList<Integer>> adjMap = graph.getAdjMap();
        HashMap<Integer, Integer> degMap = graph.getDegMap();

        int N = adjMap.size();

        //estCoreMap
        HashMap<Integer, Integer> estCoreMap = new HashMap<>(degMap);

        HashSet<Integer> accomplishNodes = new HashSet<>();

        int roundIndex = 0;
        int changedNum = 0;

        do {
            roundIndex++;
            changedNum = 0;

            /*
             * 1.the first subRound in this round
             */
            HashSet<Integer> determinedNodes = new HashSet<>();

            for (Map.Entry<Integer, Integer> estCoreMapEntry : estCoreMap.entrySet()) {
                Integer u = estCoreMapEntry.getKey();
                int estCoreOfU = estCoreMapEntry.getValue();

                //count support
                int support = 0;
                ArrayList<Integer> neighborsOfU = adjMap.get(u);
                for (Integer v : neighborsOfU) {
                    if (estCoreMap.get(v) >= roundIndex && degMap.get(v) >= roundIndex) { //estCore is no less than the round then send bit
                        support++;
                    }
                }

                //update estCore
                if (support < estCoreOfU) {
                    estCoreOfU = support;
                    estCoreMap.put(u, estCoreOfU);

                    changedNum++;  //exist update
                }

                //each round the estCore equal roundIndex, than determined
                if (estCoreOfU == roundIndex) {
                    estCoreMap.put(u, roundIndex);
                    determinedNodes.add(u); //local
                    accomplishNodes.add(u); //global
                }
            }

            /*
             * 2.the subsequent subRound in this round
             * recomputed support
             */
            while (determinedNodes.size() != 0) {
                HashSet<Integer> newDeterminedNodes = new HashSet<>();

                for (Integer u : determinedNodes) {

                    for (Integer v : adjMap.get(u)) {
                        if (accomplishNodes.contains(v)) continue;

                        int support = 0;
                        ArrayList<Integer> neighborsOfV = adjMap.get(v);
                        for (Integer w : neighborsOfV) {
                            if (estCoreMap.get(w) > roundIndex) { //estCore is no less than the round then send bit
                                support++;
                            }
                        }

                        if (support == roundIndex) { //some node may has many neighbors not increase
                            estCoreMap.put(v, roundIndex);
                            newDeterminedNodes.add(v); //
                            accomplishNodes.add(v);
                        }
                    }
                }
                determinedNodes = newDeterminedNodes;
                LOGGER.info("=subRound=" + roundIndex + " estCoreMap=" + estCoreMap.toString());
            }

            LOGGER.info("==Round=" + roundIndex + " estCoreMap=" + estCoreMap.toString());
        } while (accomplishNodes.size() != N);

        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.info("TakenTime:" + takenTime);

        return new Result(estCoreMap, takenTime, roundIndex, "DistributedPrivacyCoreDecomposition");
    }
}