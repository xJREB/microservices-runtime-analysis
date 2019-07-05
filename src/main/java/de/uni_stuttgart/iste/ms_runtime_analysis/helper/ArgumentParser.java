package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;

/**
 * Defines helper functions for argument parsing
 */
public class ArgumentParser {

    private String[] args;
    private Options options = new Options();

    /**
     * Create argument parser and set available parameter options
     * 
     * @param args parameters to be parsed
     */
    public ArgumentParser(String[] args) {

        this.args = args;

        // Main app parameters
        options.addOption("h", "help", false, "Show help");
        options.addOption("d", "debug", false, "Enable debug output");
        options.addOption("sd", "start_date", true,
                "(Required) Start date for the analysis, yyyy/MM/dd HH:mm:ss or unix timestamp in ms");
        options.addOption("ed", "end_date", true,
                "(Optional) End date for the analysis, yyyy/MM/dd HH:mm:ss or unix timestamp in ms, defaults to now");
        options.addOption("i", "integrators", true,
                "(Required) Integrators to be used, delimited with commas");

        Option option = new Option("ip", "integrator_params", true,
                "(Optional) Parameters for the integrators, space-delimited integrator:key=value elements");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);

        options.addOption("e", "exporters", true,
                "(Required) Exporters to be used, delimited with commas");

        option = new Option("ep", "exporter_params", true,
                "(Optional) Parameters for the exporters, space-delimited exporter:key=value elements");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);

    }

    /**
     * Parses the set parameters into an configuration object
     * 
     * @return parsed configuration
     */
    public Configuration parse() {

        Configuration configuration = new Configuration();
        DefaultParser parser = new DefaultParser();

        CommandLine cmd;
        try {

            cmd = parser.parse(options, args, true);

            // Read help parameter
            if (cmd.hasOption("h")) {
                help();
            }

            // Read debug parameter
            configuration.setDebugEnabled(cmd.hasOption("d"));

            // Read start date parameter
            if (cmd.hasOption("sd")) {

                String startDate = cmd.getOptionValue("sd");
                long timestamp = 0;

                try {
                    timestamp = Long.parseLong(startDate);
                } catch (Exception e0) {

                    try {
                        timestamp = DateUtils.dateToTimestamp(startDate);
                    } catch (java.text.ParseException e1) {
                        help();
                    }

                }

                configuration.setStartDate(timestamp);

            } else {
                help();
            }

            // Read end date parameter
            if (cmd.hasOption("ed")) {

                String endDate = cmd.getOptionValue("ed");
                long timestamp = 0;

                if (endDate.equals("now")) {
                    configuration.setEndDate(System.currentTimeMillis());
                } else {

                    try {
                        timestamp = Long.parseLong(endDate);
                    } catch (Exception e0) {

                        try {
                            timestamp = DateUtils.dateToTimestamp(endDate);
                        } catch (java.text.ParseException e1) {
                            help();
                        }

                    }

                    configuration.setEndDate(timestamp);
                }

            } else {
                configuration.setEndDate(System.currentTimeMillis());
            }

            // Read integrator parameter
            if (cmd.hasOption("i")) {

                for (String currentIntegrator : cmd.getOptionValue("i").split(",")) {
                    configuration.addIntegrator(currentIntegrator);
                }

            } else {
                help();
            }

            // Read parameters for the integrators
            if (cmd.hasOption("ip")) {

                for (String currentOption : cmd.getOptionValues("ip")) {

                    String[] params = currentOption.split(":", 2);

                    if (params.length != 2) {
                        help();
                    }

                    String integrator = params[0];
                    String keyValue = params[1];

                    params = keyValue.split("=", 2);

                    if (params.length != 2) {
                        help();
                    }

                    String key = params[0];
                    String value = params[1];

                    Map<String, String> integratorParam = new HashMap<>();
                    integratorParam.put(key, value);
                    configuration.addIntegratorParam(integrator, integratorParam);
                }

            }

            // Read exporter parameter
            if (cmd.hasOption("e")) {

                for (String exporter : cmd.getOptionValue("e").split(",")) {
                    configuration.addExporter(exporter);
                }

            } else {
                help();
            }

            // Read parameters for the exporters
            if (cmd.hasOption("ep")) {

                for (String currentOption : cmd.getOptionValues("ep")) {

                    String[] params = currentOption.split(":", 2);

                    if (params.length != 2) {
                        help();
                    }

                    String exporter = params[0];
                    String keyValue = params[1];

                    params = keyValue.split("=", 2);

                    if (params.length != 2) {
                        help();
                    }

                    String key = params[0];
                    String value = params[1];

                    Map<String, String> exporterParam = new HashMap<>();
                    exporterParam.put(key, value);
                    configuration.addExporterParam(exporter, exporterParam);
                }

            }

        } catch (ParseException e) {
            help();
        }

        return configuration;
    }

    /**
     * Prints the help message and exits the application
     */
    private void help() {

        HelpFormatter help = new HelpFormatter();
        help.setOptionComparator(null);
        help.printHelp("Microservices Maintainability Analysis via Runtime Data", options);

        System.exit(0);
    }

}
