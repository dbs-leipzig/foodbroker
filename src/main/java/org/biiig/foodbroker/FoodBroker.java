package org.biiig.foodbroker;

import org.apache.commons.cli.*;
import org.apache.commons.lang.time.StopWatch;
import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.formatter.FormatterFactory;
import org.biiig.foodbroker.formatter.JSONFormatterFactory;
import org.biiig.foodbroker.formatter.SQLFormatterFactory;
import org.biiig.foodbroker.generator.MasterDataGenerator;
import org.biiig.foodbroker.model.AbstractDataObject;
import org.biiig.foodbroker.model.AbstractRelationship;
import org.biiig.foodbroker.simulation.BusinessProcess;
import org.biiig.foodbroker.simulation.BusinessProcessRunner;
import org.biiig.foodbroker.simulation.FoodBrokerage;
import org.biiig.foodbroker.stores.*;

import java.util.ArrayList;
import java.util.List;

public class FoodBroker {

    /*
    is set to false in the case of any invalid option
     */
    private static Boolean allOptionsProvidedAndValid = true;
    /*
    represents the relative size of generated datasets
     */
    private static int scaleFactor = 0;
    /*
    factory for formatters of the chosen format
     */
    private static FormatterFactory formatterFactory = null;
    /*
    factory for stores of the chosen type
     */
    private static StoreFactory storeFactory = null;
    /*
    combines the stores from multiple threads
     */
    private static StoreCombiner storeCombiner = null;

    private static String directory = null;

    private static boolean combine = false;

    /**
     * main method
     * @param args
     */
    public static void main(String[] args){

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if (!parseOptions(args)) {
            return;
        }

        if(allOptionsProvidedAndValid){

            // generate master data
            Formatter masterDataFormatter = formatterFactory.newInstance(directory);
            Store masterDataStore = storeFactory.newInstance(masterDataFormatter);
            storeCombiner.add(masterDataStore);

            MasterDataGenerator masterDataGenerator = new MasterDataGenerator(scaleFactor,masterDataStore);
            masterDataGenerator.generate();

            stopWatch.stop();
            long masterDataObjectCount = AbstractDataObject.getInstanceCount();
            long masterDateGenerationTime = stopWatch.getTime();

            System.out.println(String.format(
              "%s master data objects created within %s milliseconds",
              masterDataObjectCount, masterDateGenerationTime));

            stopWatch.reset();
            stopWatch.start();

            // prepare parallel simulation

            int availableProcessors = Runtime.getRuntime().availableProcessors();
            List<Thread> threadList = new ArrayList<>();

            for(int processor = 1; processor <= availableProcessors; processor++){
                // simulate business process
                Formatter transactionalDataFormatter = formatterFactory.newInstance(directory);
                Store transactionalDataStore = storeFactory.newInstance(transactionalDataFormatter, processor);
                storeCombiner.add(transactionalDataStore);

                BusinessProcess businessProcess = new FoodBrokerage(masterDataGenerator,transactionalDataStore);
                BusinessProcessRunner runner = new BusinessProcessRunner(businessProcess,scaleFactor,availableProcessors );

                // manage threads
                Thread thread = new Thread(runner);
                threadList.add(thread);
                thread.start();
            }

            for(Thread thread : threadList){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            stopWatch.stop();

            long transactionalDataObjectCount = AbstractDataObject.getInstanceCount() - masterDataObjectCount;
            long relationshipCount = AbstractRelationship.getInstanceCount();
            long simulationTime = stopWatch.getTime();

            System.out.println(String.format(
                    "%s transactional data objects and %s relationships created within %s milliseconds",
                    transactionalDataObjectCount,
                    relationshipCount,
                    simulationTime
            ));

            stopWatch.reset();
            stopWatch.start();

            // merge files
            if(combine){
                storeCombiner.combine();
                stopWatch.stop();
                System.out.println(String.format(
                  "file combination took %s milliseconds", stopWatch.getTime
                    ()));

            }

        }
    }

    private static boolean parseOptions(String[] args) {
        // create and parse options
        Options options = new Options();
        options.addOption("s", "scale", true, "Set Scale Factor [1..10000]");
        options.addOption("o", "output", true, "Choose Output [console, file]");
        options.addOption("f", "format", true, "Choose Output format [json, sql]");
        options.addOption("c", "combine", false, "Combine output files");
        options.addOption("d", "directory", true, "Output directory when using file output (default is user home)");

        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(FoodBroker.class.getName(), options, true);
            return false;
        }

        // parse options
        CommandLineParser parser = new BasicParser();
        CommandLine commandLine = null;
        try {
          commandLine = parser.parse(options, args);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        if (commandLine == null) {
          return false;
        }
        // validate options
        validateScaleFactor(commandLine);
        validateFormat(commandLine);
        validateStore(commandLine);
        validateDirectory(commandLine);
        validateCombine(commandLine);

        return true;
    }

    private static void validateCombine(CommandLine commandLine) {
        if(commandLine.hasOption("combine") && storeFactory instanceof FileStoreFactory && formatterFactory != null){
            storeCombiner = new FileStoreCombiner(formatterFactory.newInstance(directory));
            combine = true;
        }
        else {
            storeCombiner = new DummyStoreCombiner();
        }
    }

    private static void validateStore(CommandLine commandLine) {
        if(commandLine.hasOption("output")){
            String storeClass = commandLine.getOptionValue("output");

            if(storeClass.equals("console")){
                storeFactory = new ConsoleStoreFactory();
            }
            else if (storeClass.equals("file")){
                storeFactory = new FileStoreFactory();
            }
            else{
                System.out.println("Unknown output : " + storeClass);
                allOptionsProvidedAndValid = false;
            }
        }
        else {
            System.out.println("no store specified");
            allOptionsProvidedAndValid = false;
        }
    }

    private static void validateDirectory(CommandLine commandLine) {
        if (commandLine.hasOption("directory")) {
            directory = commandLine.getOptionValue("directory");
            if (directory.endsWith(System.getProperty("file.separator"))) {
                directory = directory.substring(0, directory.length() - 1);
            }
        } else {
            directory = System.getProperty("user.home");
        }
    }

    private static void validateFormat(CommandLine commandLine) {
        if(commandLine.hasOption("format")){
            String format = commandLine.getOptionValue("format");

            if(format.equals("json")){
                formatterFactory = new JSONFormatterFactory();
            }
            else if (format.equals("sql")){
                formatterFactory = new SQLFormatterFactory();
            }
            else{
                System.out.println("Unknown formatter : " + format);
                allOptionsProvidedAndValid = false;
            }
        }
        else {
            System.out.println("no formatter specified");
            allOptionsProvidedAndValid = false;
        }
    }

    private static void validateScaleFactor(CommandLine commandLine) {
        if(commandLine.hasOption("scale")){
            scaleFactor = Integer.parseInt(commandLine.getOptionValue("scale"));

            if (scaleFactor < 1 || scaleFactor > 10000){
                System.out.println("scale factor out of range 1..10000");
                allOptionsProvidedAndValid = false;
            }
        }
        else{
            System.out.println("no scale factor specified");
            allOptionsProvidedAndValid = false;
        }
    }
}
