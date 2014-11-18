package org.gradoop.foodbroker;

import org.apache.commons.cli.*;
import org.gradoop.foodbroker.generators.MasterDataGenerator;

public class FoodBroker {
    public static void main(String[] args) throws ParseException {
        // create the command line parser
        CommandLineParser parser = new BasicParser();

        // create the Options
        Options options = new Options();
        options.addOption("s", "scale", true, "Set Scale Factor");

        CommandLine line = parser.parse(options, args);

        int scaleFactor = Integer.parseInt(line.getOptionValue("scale"));

        MasterDataGenerator generator = new MasterDataGenerator(scaleFactor);
        generator.generate();

    }
}
