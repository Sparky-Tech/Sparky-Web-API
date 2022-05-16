package ac.sparky;

import ac.sparky.exception.InvalidRequestException;
import ac.sparky.serialized.SparkyPlayerLogsObject;
import ac.sparky.wrapper.handler.WebHandler;
import ac.sparky.wrapper.util.Tuple;

public class SparkyAPI {
    private final static WebHandler webHandle = new WebHandler();

    public static String getFormattedUUIDFromName(String license, String username) throws InvalidRequestException {
        return webHandle.getUUIDFromName(license, username);
    }

    public static boolean isInOverwatch(String license, String uuid) throws InvalidRequestException {
        return webHandle.isInOverwatch(license, uuid);
    }

    public static int getLogAmount(String license, String uuid) throws InvalidRequestException {
        return webHandle.getLogAmount(license, uuid);
    }

    public static boolean clearLogs(String license, String uuid) throws InvalidRequestException {
        return webHandle.deleteLogs(license, uuid);
    }

    public static SparkyPlayerLogsObject getLogs(String license, String uuid) throws InvalidRequestException {
        Tuple<Boolean, SparkyPlayerLogsObject> tuple = webHandle.getLogs(license, uuid);

        if (tuple == null || !tuple.one) {
            throw new InvalidRequestException("Invalid API Usage.");
        }

        return tuple.two;
    }
}
