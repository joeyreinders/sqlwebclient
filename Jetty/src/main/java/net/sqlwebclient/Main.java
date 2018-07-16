package net.sqlwebclient;

import net.sqlwebclient.browser.BrowserLauncher;
import net.sqlwebclient.jetty.JettyStarter;
import net.sqlwebclient.jetty.custom.JettyLifecycleListener;
import net.sqlwebclient.jetty.custom.JettyLifecycleWrapper;
import net.sqlwebclient.parser.ArgumentContext;
import net.sqlwebclient.parser.CLIParser;

public final class Main {
    public static void main(final String[] args) throws Exception {
        final ArgumentContext ctx = CLIParser.newInstance().parseArguments(args);
        final JettyStarter jetty = JettyStarter.newInstance(ctx)
                .add(JettyLifecycleWrapper.State.STARTED, new JettyLifecycleListener() {
                    @Override
                    public void onState(final JettyLifecycleWrapper.State state) {
                        try {
                            BrowserLauncher.newInstance(ctx).launch();
                        } catch (final Exception ex) {
                            System.out.println(ex.getMessage());
                            //Nothing I can do now
                        }
                    }
                })
                .start();

        final Runnable shutdownHook = new Runnable() {
            @Override
            public void run() {
                try {
                    jetty.stop();
                } catch (final Exception ex) {
                    System.out.println("Error while shutting down Jetty");
                }

                System.out.println("Over and Out");
            }
        };

        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
    }
}
