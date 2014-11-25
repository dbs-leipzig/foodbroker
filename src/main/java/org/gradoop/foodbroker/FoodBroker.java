package org.gradoop.foodbroker;

import org.apache.commons.cli.*;
import org.gradoop.foodbroker.formatter.Formatter;
import org.gradoop.foodbroker.formatter.JSONFormatter;
import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.simulator.BusinessProcess;
import org.gradoop.foodbroker.simulator.BusinessProcessRunner;
import org.gradoop.foodbroker.simulator.FoodBrokerage;
import org.gradoop.foodbroker.stores.Store;

import java.util.ArrayList;
import java.util.List;

public class FoodBroker {
    public static void main(String[] args) throws ParseException {
        // create the command line parser
        CommandLineParser parser = new BasicParser();

        // create the Options
        Options options = new Options();
        options.addOption("s", "scale", true, "Set Scale Factor");
        options.addOption("r", "store", true, "Choose Store");
        options.addOption("f", "format", true, "Choose Output format");

        CommandLine line = parser.parse(options, args);

        int scaleFactor = 0;
        //Store store = null;
        //Formatter formatter = null;
        Boolean run = true;

        if(line.hasOption("scale")){
             scaleFactor = Integer.parseInt(line.getOptionValue("scale"));

            if (scaleFactor > 0 && scaleFactor < 10000){

            }
            else{
                System.out.println("scale factor out of range 1..10000");
                run = false;
            }
        }
        else{
            System.out.println("no scale factor specified");
            run = false;
        }

        if(line.hasOption("format")){
            String formatClass = line.getOptionValue("format");

            if(formatClass.equals("json")){
                //formatter = new JSONFormatter();

            }
            else{
                System.out.println("Unknown formatter : " + formatClass);
                run = false;
            }
        }
        else {
            System.out.println("no formatter specified");
            run = false;
        }

        if(line.hasOption("store")){
            String storeClass = line.getOptionValue("store");

            if(storeClass.equals("console")){
                //store = new ConsoleStore(formatter);
            }
            else if (storeClass.equals("file")){
                //store = new org.gradoop.foodbroker.stores.FileStore(formatter);
            }
            else{
                System.out.println("Unknown store : " + storeClass);
                run = false;
            }
        }
        else {
            System.out.println("no store specified");
            run = false;
        }

        if(run){
            Formatter formatter = new JSONFormatter();
            Store store = new org.gradoop.foodbroker.stores.FileStore(formatter);
            MasterDataGenerator generator = new MasterDataGenerator(scaleFactor,store);

            store.open();
            generator.generate();
            store.close();

            int availableProcessors = Runtime.getRuntime().availableProcessors();
            List<Thread> threadList = new ArrayList<>();

            for(int processor = 1; processor <= availableProcessors; processor++){
                formatter = new JSONFormatter();
                store = new org.gradoop.foodbroker.stores.FileStore(formatter,processor);
                BusinessProcess businessProcess = new FoodBrokerage(generator,store);
                BusinessProcessRunner runner = new BusinessProcessRunner(businessProcess,scaleFactor,availableProcessors );
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
        }
    }
}
