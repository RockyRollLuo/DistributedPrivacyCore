import algorithms.CoreDecomposition;
import algorithms.DPCoreDecompDecr;
import algorithms.DPCoreDecompIncre;
import algorithms.DistributedCoreDecomposition;
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

    @Option(abbr = 's', usage = "sperate delimiter")
    public static String delim = "\t";


    public static void main(String[] args) throws IOException {
        //read parameters
        Main main = new Main();
        args = SetOpt.setOpt(main, args);

        //read graph
        String datasetName = args[0];
        Graph graph = FileIOUtils.loadGraph(datasetName, delim);


        CoreDecomposition coreDecomposition = new CoreDecomposition(graph);
        Result result1 = coreDecomposition.run();
        result1.setDatasetName(datasetName);
        System.out.println(result1.getCoreMap().toString());


        DistributedCoreDecomposition distributedCoreDecomposition = new DistributedCoreDecomposition(graph);
        Result result2 = distributedCoreDecomposition.run();
        result2.setDatasetName(datasetName);
        System.out.println(result2.getCoreMap().toString());


        DPCoreDecompDecr dpCoreDecompDecr = new DPCoreDecompDecr(graph);
        Result result3 = dpCoreDecompDecr.run();
        result3.setDatasetName(datasetName);
        System.out.println(result3.getCoreMap().toString());


//        DPCoreDecompIncre dpCoreDecompIncre = new DPCoreDecompIncre(graph);
//        Result result4 = dpCoreDecompIncre.run();
//        result4.setDatasetName(datasetName);
//        System.out.println(result4.getCoreMap().toString());



    }

}
