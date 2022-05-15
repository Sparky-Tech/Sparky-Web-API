package me.rhys.example;

import ac.sparky.SparkyAPI;
import ac.sparky.exception.InvalidRequestException;
import ac.sparky.serialized.SparkyPlayerLogsObject;

public class Main {

    /*
     * Basic examples of how you can use the Sparky API
     * args 1 = Sparky License Key
     * args 2 = UUID of the player
     */

    public static void main(String[] args) {

        if (args.length < 2) return;

        showLogs(args);
    }

    private static void isInOverwatch(String[] args) {
        // checks if the player has been added to overwatch

        try {
            System.out.println(SparkyAPI.isInOverwatch(args[0], args[1]));
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }

    private static void getLogSize(String[] args) {
        // gets the amount of logs stored for the user

        try {
            System.out.println(SparkyAPI.getLogAmount(args[0], args[1]) + " logs!");
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }

    private static void deleteLogs(String[] args) {
        // clear a players logs from the web API

        try {
            if (SparkyAPI.clearLogs(args[0], args[1])) {
                System.out.println("Logs cleared!");
            } else {
                System.out.println("Something went wrong..?");
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }

    private static void showLogs(String[] args) {

        // grabs a players logs from the web API

        try {
            SparkyPlayerLogsObject sparkyPlayerLogsObject = SparkyAPI.getLogs(args[0], args[1]);

            if (sparkyPlayerLogsObject.getLogs().size() < 1) {
                System.out.println("No logs!");
            } else {
                sparkyPlayerLogsObject.getLogs().forEach(logObject ->
                        System.out.println(logObject.getCheckName() + " " +
                                logObject.getCheckType() + " x" + logObject.getViolations() + " " + logObject.getDebug()));
            }

        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }
}
