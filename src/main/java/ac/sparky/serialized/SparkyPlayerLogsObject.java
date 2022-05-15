package ac.sparky.serialized;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public final class SparkyPlayerLogsObject {
    private final String uuid;
    private final String licenseKey;
    private final List<LogObject> logs;
}
