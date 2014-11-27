package org.gradoop.foodbroker;

import org.apache.commons.cli.*;
import org.gradoop.foodbroker.formatter.Formatter;
import org.gradoop.foodbroker.formatter.FormatterFactory;
import org.gradoop.foodbroker.formatter.JSONFormatter;
import org.gradoop.foodbroker.formatter.JSONFormatterFactory;
import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.simulation.BusinessProcess;
import org.gradoop.foodbroker.simulation.BusinessProcessRunner;
import org.gradoop.foodbroker.simulation.FoodBrokerage;
import org.gradoop.foodbroker.stores.*;

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

    /**
     * main method
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {

        // create and parse options
        Options options = new Options();
        options.addOption("s", "scale", true, "Set Scale Factor");
        options.addOption("r", "store", true, "Choose Store");
        options.addOption("f", "format", true, "Choose Output format");

        // parse options
        CommandLineParser parser = new BasicParser();
        CommandLine commandLine = parser.parse(options, args);

        // validate options
        validateScaleFactor(commandLine);
        validateFormat(commandLine);
        validateStore(commandLine);

        if(allOptionsProvidedAndValid){

            List<Store> storeList = new ArrayList<>();

            // generate master data
            Formatter masterDataFormatter = formatterFactory.newInstance();
            Store masterDataStore = storeFactory.newInstance(masterDataFormatter);
            storeList.add(masterDataStore);

            MasterDataGenerator masterDataGenerator = new MasterDataGenerator(scaleFactor,masterDataStore);
            masterDataGenerator.generate();

            // prepare parallel simulation

            int availableProcessors = Runtime.getRuntime().availableProcessors();
            List<Thread> threadList = new ArrayList<>();

            for(int processor = 1; processor <= availableProcessors; processor++){
                // simulate business process
                Formatter transactionalDataFormatter = new JSONFormatter();
                Store transactionalDataStore = new org.gradoop.foodbroker.stores.FileStore(transactionalDataFormatter,processor);
                storeList.add(transactionalDataStore);

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

            // merge files
            if (storeFactory instanceof FileStoreFactory){
                /*
                TODO
                 */
            }
        }
    }

    private static void validateStore(CommandLine commandLine) {
        if(commandLine.hasOption("store")){
            String storeClass = commandLine.getOptionValue("store");

            if(storeClass.equals("console")){
                storeFactory = new ConsoleStoreFactory();
            }
            else if (storeClass.equals("file")){
                storeFactory = new FileStoreFactory();
            }
            else{
                System.out.println("Unknown store : " + storeClass);
                allOptionsProvidedAndValid = false;
            }
        }
        else {
            System.out.println("no store specified");
            allOptionsProvidedAndValid = false;
        }
    }

    private static void validateFormat(CommandLine commandLine) {
        if(commandLine.hasOption("format")){
            String format = commandLine.getOptionValue("format");

            if(format.equals("json")){
                formatterFactory = new JSONFormatterFactory();
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
