package net.sqlwebclient.parser;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.choice.RangeArgumentChoice;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public final class CLIParser {
    final ArgumentParser argumentParser = ArgumentParsers.newArgumentParser("SQLWebClient");

    private CLIParser() {
        argumentParser
                .defaultHelp(true)
                .description("This is a new way of querying databases in the browser")
                .setDefaults(ArgumentConstants.getDefaults());
        //Port
        argumentParser.addArgument("--port", "-p")
                .choices(new RangeArgumentChoice<Integer>(0, 65535))
                .setDefault(15489)
                .dest("port")
                .help("Set the port for the application");
    }

    public ArgumentContext parseArguments(final String ... args) {
        final ArgumentContext res = ArgumentContext.newInstance();
        try {
            argumentParser.parseArgs(args, res);
        } catch (final ArgumentParserException ex) {
            argumentParser.handleError(ex);
            System.exit(1);
        }

        return res;
    }

    public static CLIParser newInstance() {
        return new CLIParser();
    }
}
