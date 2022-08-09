package ac.sparky;

import ac.sparky.exception.InvalidRequestException;
import ac.sparky.serialized.SparkyPlayerLogsObject;
import ac.sparky.util.EncryptionUtil;
import ac.sparky.wrapper.handler.WebHandler;
import ac.sparky.wrapper.util.Tuple;
import lombok.Getter;

public class SparkyAPI {
    private final static WebHandler webHandle = new WebHandler();
    public final static Static staticValues = new Static();

    static {
        staticValues.generate();
    }

    public static String getFormattedUUIDFromName(String username) throws InvalidRequestException {
        return webHandle.getUUIDFromName(staticValues.getLicenseKey(), username);
    }

    public static boolean isInOverwatch(String uuid) throws InvalidRequestException {
        return webHandle.isInOverwatch(staticValues.getLicenseKey(), uuid);
    }

    public static int getLogAmount(String uuid) throws InvalidRequestException {
        return webHandle.getLogAmount(staticValues.getLicenseKey(), uuid);
    }

    public static boolean clearLogs(String uuid) throws InvalidRequestException {
        return webHandle.deleteLogs(staticValues.getLicenseKey(), uuid);
    }

    public static SparkyPlayerLogsObject getLogs(String uuid) throws InvalidRequestException {
        Tuple<Boolean, SparkyPlayerLogsObject> tuple = webHandle.getLogs(staticValues.getLicenseKey(), uuid);

        if (tuple == null || !tuple.one) {
            throw new InvalidRequestException("Invalid API Usage.");
        }

        return tuple.two;
    }

    @Getter
    public static class Static {
        private final String licenseKey = "ENTER_LICENSE_KEY";
        private final String apiKey = "ENTER_API_KEY";
        private String apiEndPoint;

        public void generate() {
            this.apiEndPoint = EncryptionUtil.decrypt(this.apiKey, this.licenseKey) + "/api";
        }
    }
}
