package util;

import model.Edge;
import model.Graph;
import model.Result;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FileIOUtils {

    private static final Logger LOGGER = Logger.getLogger(FileIOUtils.class);

    /**
     * load an input graph in memory
     *
     * @param datasetName dataset name
     * @param delim       seperate sybolm
     * @return a graph
     * @throws IOException io
     */
    public static Graph loadGraph(String datasetName, String delim) throws IOException {
        long startTime = System.nanoTime();
        //Operate System
        String pathSeparator = "\\";
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {
            pathSeparator = "/";
        }
        String path = "datasets" + pathSeparator + datasetName;

        //read edges
        final BufferedReader br = new BufferedReader(new FileReader(path));

        HashMap<Integer, ArrayList<Integer>> temAdjMap = new HashMap<>();

        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith("#") || line.startsWith("%") || line.startsWith("//")) { //comment
                continue;
            }

            String[] tokens = line.split(delim);
            Integer firstNode = Integer.parseInt(tokens[0]);
            Integer secondNode = Integer.parseInt(tokens[1]);

            ArrayList<Integer> firstNodeNeighbors = temAdjMap.get(firstNode) == null ? new ArrayList<>() : temAdjMap.get(firstNode);
            firstNodeNeighbors.add(secondNode);
            temAdjMap.put(firstNode, firstNodeNeighbors);

            ArrayList<Integer> secondNodeNeighbors = temAdjMap.get(secondNode) == null ? new ArrayList<>() : temAdjMap.get(secondNode);
            secondNodeNeighbors.add(firstNode);
            temAdjMap.put(secondNode, secondNodeNeighbors);
        }

        //remove duplicate edges and construct graph
        ArrayList<Integer> nodeList = new ArrayList<>();
        ArrayList<Edge> edgeList = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> adjMap = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<Integer>> map : temAdjMap.entrySet()) {
            Integer node = map.getKey();
            HashSet<Integer> nodeNeighborsSet = new HashSet<>(map.getValue());

            nodeList.add(node);
            for (Integer nei : nodeNeighborsSet) {
                if (nei > node) {
                    edgeList.add(new Edge(node, nei));
                }
            }
            adjMap.put(node, new ArrayList<>(nodeNeighborsSet));
        }

        long endTime = System.nanoTime();
        LOGGER.info("TakenTime:" + (double) (endTime - startTime) / 1.0E9D);

        return new Graph(nodeList, edgeList, adjMap);
    }

    /**
     * write the core number of nodes
     *
     * @param result coreMap
     * @throws IOException IO
     */
    public static void writeCoreNumber(Result result, int printResult) throws IOException {
        long startTime = System.nanoTime();

        HashMap<Integer, Integer> output = result.getCoreMap();
        double takenTime = result.getTakenTime();
        String algorithmName = result.getAlgorithmName();
        String datasetName = result.getDatasetName();
        String type = result.getType();

        //Operate System
        String pathSeparator = "\\";
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {
            pathSeparator = "/";
        }
        String fileName = "corenumber" + pathSeparator + algorithmName + "_" + datasetName + "_" + type;

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        bw.write("# takenTime:" + takenTime + "ms");
        bw.newLine();

        //whether print the core nubmer
        if (printResult == 1) {
            for (Integer key : output.keySet()) {
                bw.write(key.toString() + " " + output.get(key));
                bw.newLine();
            }
        }
        bw.close();

        long endTime = System.nanoTime();
        LOGGER.info((double) (endTime - startTime) / 1.0E9D);
    }

    /**
     * read a core number file
     *
     * @param coreFile filename of core number
     * @return coreVMap
     * @throws IOException
     */
    public static HashMap<Integer, Integer> loadCoreFile(String coreFile) throws IOException {
        long startTime = System.nanoTime();
        //Operate System
        String pathSeparator = "\\";
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {
            pathSeparator = "/";
        }
        String path = "corenumber" + pathSeparator + coreFile;

        HashMap<Integer, Integer> coreVMap = new HashMap<>();

        //read edges
        final BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith("#")) {
                continue;
            }

            String[] tokens = line.split(" ");
            Integer node = Integer.parseInt(tokens[0]);
            int coreness = Integer.parseInt(tokens[1]);
            coreVMap.put(node, coreness);
        }

        long endTime = System.nanoTime();
        LOGGER.info((double) (endTime - startTime) / 1.0E9D);

        return coreVMap;
    }

    /**
     * write the nodeToEdgesMap to file
     *
     * @param nodeToEdgesMap data structure
     * @param datasetName    dataset name
     * @throws IOException io
     */
    public static void writeNodeToEdgesMap(HashMap<Integer, ArrayList<Integer>> nodeToEdgesMap, String datasetName) throws IOException {
        long startTime = System.nanoTime();

        //Operate System
        String pathSeparator = "\\";
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {
            pathSeparator = "/";
        }
        String fileName = "datasets/nodeToEdgesMap/" + datasetName + ".txt";

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        //write
        for (Map.Entry<Integer, ArrayList<Integer>> entry : nodeToEdgesMap.entrySet()) {

            bw.write(entry.getKey().toString());
            bw.newLine();

            bw.write(entry.getValue().toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
        }
        bw.close();

        long endTime = System.nanoTime();
        LOGGER.info((double) (endTime - startTime) / 1.0E9D);
    }

    /**
     * read the file of nodeToEdgesMap
     *
     * @param datasetName dataset name
     * @return nodeToEdgesMap
     * @throws IOException io
     */
    public static HashMap<Integer, ArrayList<Integer>> loadNodeToEdgesMap(String datasetName) throws IOException {
        long startTime = System.nanoTime();

        String path = "datasets/nodeToEdgesMap/" + datasetName;

        HashMap<Integer, ArrayList<Integer>> nodeToEdgesMap = new HashMap<>();

        //read
        final BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            final String nodeIdLine = br.readLine();
            if (nodeIdLine == null) {
                break;
            }

            //node
            Integer node = Integer.parseInt(nodeIdLine);

            //read edges
            String edgesIdLine = br.readLine();
            String[] tokens = edgesIdLine.split(",");

            ArrayList<Integer> edgesIdList = new ArrayList<>();
            for (String token : tokens) {
                int nodeInEdge = Integer.parseInt(token);
                edgesIdList.add(nodeInEdge);
            }
            nodeToEdgesMap.put(node, edgesIdList);
        }

        long endTime = System.nanoTime();
        LOGGER.info((double) (endTime - startTime) / 1.0E9D);

        return nodeToEdgesMap;
    }


    public static void writeCSV(StringBuffer data, String dataName) throws IOException {
        long startTime = System.nanoTime();

        //Operate System
        String pathSeparator = "\\";
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {
            pathSeparator = "/";
        }
        String fileName = "datasets/result/" + dataName + ".csv";

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        //write
        bw.write(data.toString().replace("[", "").replace("]", "").replace(" ", ""));
        bw.newLine();

        bw.close();

        long endTime = System.nanoTime();
        LOGGER.info((double) (endTime - startTime) / 1.0E9D);
    }

    public static void writeFile(ArrayList<ArrayList<Integer>> edgeList, String datasetPath, String fileName) throws IOException {
        long startTime = System.nanoTime();

        String path = datasetPath + "\\" + fileName + "-hyperedges" + ".txt";

        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        for (ArrayList<Integer> edge : edgeList) {

            String line = edge.toString().replace("[", "").replace("]", "").replace(",", "");
            bw.write(line);
            bw.newLine();
        }
        bw.close();

        long endTime = System.nanoTime();
        LOGGER.info(fileName + " WRITE DONE!: " + (double) (endTime - startTime) / 1.0E9D);
    }
}
