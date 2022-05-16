package ac.sparky.wrapper.handler;

import ac.sparky.exception.InvalidRequestException;
import ac.sparky.serialized.LogObject;
import ac.sparky.serialized.SparkyPlayerLogsObject;
import ac.sparky.wrapper.util.HTTPUtil;
import ac.sparky.wrapper.util.Tuple;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebHandler {

    private final String sparkyAPI = "https://rest.sparky.ac/api";

    public String getUUIDFromName(String licenseKey, String username) throws InvalidRequestException {
        String jsonResponse = HTTPUtil.getResponse(
                this.sparkyAPI + "?license=" + licenseKey + "&type=uuid&username=" + username);

        if (jsonResponse == null || jsonResponse.length() < 6) return null;

        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("Status") || !jsonObject.getString("Status").equalsIgnoreCase("OK")) {
            throw new InvalidRequestException("Status Error");
        }

        return jsonObject.getString("FormattedUUID");
    }


    public boolean isInOverwatch(String licenseKey, String uuid) throws InvalidRequestException {
        String jsonResponse = HTTPUtil.getResponse(
                this.sparkyAPI + "?license=" + licenseKey + "&type=overwatch&uuid=" + uuid);

        if (jsonResponse == null || jsonResponse.length() < 6) return false;

        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("Status") || !jsonObject.getString("Status").equalsIgnoreCase("OK")) {
            throw new InvalidRequestException("Status Error");
        }

        return jsonObject.getBoolean("OverwatchStatus");
    }

    public int getLogAmount(String licenseKey, String uuid) throws InvalidRequestException {
        String jsonResponse = HTTPUtil.getResponse(
                this.sparkyAPI + "?license=" + licenseKey + "&type=logamount&uuid=" + uuid);

        if (jsonResponse == null || jsonResponse.length() < 6) return 0;

        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("Status") || !jsonObject.getString("Status").equalsIgnoreCase("OK")) {
            throw new InvalidRequestException("Status Error");
        }

        return jsonObject.getInt("Amount");
    }

    public boolean deleteLogs(String licenseKey, String uuid) throws InvalidRequestException {
        String jsonResponse = HTTPUtil.getResponse(
                this.sparkyAPI + "?license=" + licenseKey + "&type=clear&uuid=" + uuid);

        if (jsonResponse == null || jsonResponse.length() < 6) return false;

        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("Status") || !jsonObject.getString("Status").equalsIgnoreCase("OK")) {
            throw new InvalidRequestException("Status Error");
        }

        return true;
    }

    public Tuple<Boolean, SparkyPlayerLogsObject> getLogs(String licenseKey, String uuid) throws InvalidRequestException {

        String jsonResponse = HTTPUtil.getResponse(
                this.sparkyAPI + "?license=" + licenseKey + "&type=logs&uuid=" + uuid);

        if (jsonResponse == null || jsonResponse.length() < 6) return null;

        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (!jsonObject.has("Status") || !jsonObject.getString("Status").equalsIgnoreCase("OK")) {
            throw new InvalidRequestException("Status Error");
        }

        List<LogObject> logObjects = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("Logs");

        jsonArray.forEach(o -> {

            if (!(o instanceof String)) return;

            String str = (String) o;

            if (!str.contains(":")) return;
            String[] split = str.split(":");

            logObjects.add(new LogObject(split[0], split[1], Integer.parseInt(split[2]), split[3]));
        });

        return new Tuple<>(true, new SparkyPlayerLogsObject(uuid, licenseKey, logObjects));
    }
}
