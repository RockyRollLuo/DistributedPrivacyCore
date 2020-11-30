package algorithms;

import model.Graph;
import model.Result;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoreDecomposition {
    private static Logger LOGGER = Logger.getLogger(CoreDecomposition.class);

    private final Graph graph;

    public CoreDecomposition(Graph graph) {
        this.graph = graph;
    }

    public Result run() {
        long startTime = System.nanoTime();

        HashMap<Integer, ArrayList<Integer>> adjMap = graph.getAdjMap();
        HashMap<Integer, Integer> degMap = graph.getDegMap();
        ArrayList<Integer> tempNodeList = new ArrayList<>(graph.getNodeList());

        //coreMap
        HashMap<Integer, Integer> coreMap = new HashMap<>();

        for (int k = 1; ; k++) {
            if (tempNodeList.isEmpty()) {
                break;
            }

            ArrayList<Integer> delQueue = new ArrayList<>();
            for (Map.Entry<Integer, Integer> degMapEntry : degMap.entrySet()) {
                Integer u = degMapEntry.getKey();
                int degreeU = degMapEntry.getValue();
                if (degreeU <= k) {
                    delQueue.add(u);
                }
            }

            while (!delQueue.isEmpty()) {
                ArrayList<Integer> newDelQueue = new ArrayList<>();

                for (Integer node : delQueue) {
                    if (!tempNodeList.contains(node)) continue;

                    ArrayList<Integer> nodeNeigbors = adjMap.get(node);
                    for (Integer nodeNei : nodeNeigbors) {
                        if (!tempNodeList.contains(nodeNei)) continue;

                        int degNodeNei = degMap.get(nodeNei) - 1;
                        degMap.put(nodeNei, degNodeNei);

                        if (degNodeNei == k) {
                            newDelQueue.add(nodeNei);
                        }
                    }
                    coreMap.put(node, k);
                    tempNodeList.remove(node);
                }
                delQueue = newDelQueue;
            }
        }

        long endTime = System.nanoTime();
        double takenTime = (endTime - startTime) / 1.0E9D;
        LOGGER.info("TakenTime:" + takenTime);

        return new Result(coreMap, takenTime, "CoreDecomposition");
    }

}
