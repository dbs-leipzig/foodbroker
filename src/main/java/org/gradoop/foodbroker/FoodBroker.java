package org.gradoop.foodbroker;

import org.apache.commons.cli.*;
import org.gradoop.foodbroker.formatter.JSONFormatter;
import org.gradoop.foodbroker.generator.MasterDataGenerator;
import org.gradoop.foodbroker.manager.DateManager;
import org.gradoop.foodbroker.simulator.BusinessProcess;
import org.gradoop.foodbroker.simulator.FoodBrokerage;
import org.gradoop.foodbroker.configuration.ProcessConfiguration;
import org.gradoop.foodbroker.stores.ConsoleStore;
import org.gradoop.foodbroker.stores.Store;

public class FoodBroker {
    public static void main(String[] args) throws ParseException {
        // create the command line parser
        CommandLineParser parser = new BasicParser();

        // create the Options
        Options options = new Options();
        options.addOption("s", "scale", true, "Set Scale Factor");

        CommandLine line = parser.parse(options, args);

        int scaleFactor = Integer.parseInt(line.getOptionValue("scale"));

        Store store = new ConsoleStore(new JSONFormatter());

        MasterDataGenerator generator = new MasterDataGenerator(scaleFactor,store);
        generator.generate();
        generator.shuffle();

        ProcessConfiguration config = new ProcessConfiguration(FoodBrokerage.class.getSimpleName(),scaleFactor);
        DateManager dateManager = new DateManager(config.getStartDate(),config.getEndDate(),config.getCases());

        BusinessProcess process = new FoodBrokerage(generator,store);

        for(int kase = 1; kase <= config.getCases(); kase++){
            process.start(dateManager.getStartDate(kase));
        }

    }
}
