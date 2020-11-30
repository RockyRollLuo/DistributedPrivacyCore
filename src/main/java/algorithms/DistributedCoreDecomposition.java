package algorithms;

import model.Graph;
import model.Result;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DistributedCoreDecomposition {
    private static Logger LOGGER = Logger.getLogger(DistributedCoreDecomposition.class);

    private final Graph graph;

    public DistributedCoreDecomposition(Graph graph) {
        this.graph = graph;
    }

    public Result run() {
        long startTime = System.nanoTime();

        HashMap<Integer, ArrayList<Integer>> adjMap = graph.getAdjMap();
        HashMap<Integer, Integer> degMap = graph.getDegMap();

        //estCoreMap
        HashMap<Integer, Integer> estCoreMap = new HashMap<>(degMap);

        int totalRound = 0;
        int changedNum = 0;

        while (true) {
            totalRound++;
//            LOGGER.info("==Round:" + totalRound);

            changedNum = 0;
            for (Map.Entry<Integer, Integer> estCoreMapEntry : estCoreMap.entrySet()) {
                Integer u = estCoreMapEntry.getKey();
                int estCoreOfU = estCoreMapEntry.getValue();

                //get neighbors' estCore
                ArrayList<Integer> estCoreList = new ArrayList<>();
                ArrayList<Integer> neighborsOfU = adjMap.get(u);
                for (Integer neighborOfU : neighborsOfU) {
                    estCoreList.add(estCoreMap.get(neighborOfU));
                }

                //update estCore
//                Collections.sort(estCoreList);
//                Collections.reverse(estCoreList);
//                int newEstCoreOfU = estCoreOfU; //degree
//                for (int i = 0; i < estCoreList.size(); i++) {
//                    if (i > estCoreList.get(i)) {
//                        newEstCoreOfU = i;
//                        break;
//                    }
//                }
                int newEstCoreOfU = computedIndex(estCoreList, estCoreOfU);
                estCoreMap.put(u, newEstCoreOfU);

                //exist update
                if (newEstCoreOfU != estCoreOfU) {
                    changedNum++;
                }
            }

            if (changedNum == 0) {
                break;
            }

        }

        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.info("TakenTime:" + takenTime+"\n"+"TotalRound:"+totalRound);

        return new Result(estCoreMap, takenTime, totalRound, "DistributedCoreDecomposition");
    }

    private static int computedIndex(ArrayList<Integer> neighborsEstCore, int k) {
        int[] count = new int[k + 1];
        for (int i = 0; i < k + 1; i++) {
            count[i] = 0;
        }

        for (int j : neighborsEstCore) {
            int index = Math.min(k, j);
            count[index] = count[index] + 1;

        }

        //the neighbors num greater than core vlaue
        for (int i = k; i > 1; i--) {
            count[i - 1] = count[i - 1] + count[i];
        }

        //the maximum core number(k) has at least k neighbors
        int ret = k;
        while (ret > 1 & count[ret] < ret) {
            ret = ret - 1;
        }
        return ret;
    }
}