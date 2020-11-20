import algorithms.CoreDecomposition;
import model.Graph;
import model.Result;
import util.*;
import util.SetOpt.Option;

import java.io.IOException;

public class Main {
    @Option(abbr = 'p', usage = "Print the result.")
    public static boolean printResult = true;

    @Option(abbr = 'd', usage = "print the progress")
    public static int debug = 1;

    @Option(abbr = 's', usage = "sperate delimiter,0:tab,1:space,2:comma")
    public static String delim = "\t";


    public static void main(String[] args) throws IOException {
        //read parameters
        Main main = new Main();
        args = SetOpt.setOpt(main, args);

        //read graph
        String datasetName = args[0];
        Graph graph = FileIOUtils.loadGraph(datasetName, delim);

        CoreDecomposition coreDecomposition = new CoreDecomposition(graph);
        Result result = coreDecomposition.run();
        result.setDatasetName(datasetName);


    }

}
