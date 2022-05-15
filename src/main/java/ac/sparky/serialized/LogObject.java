package ac.sparky.serialized;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public final class LogObject {
    private final String checkName;
    private final String checkType;
    private final int violations;
    private final String debug;
}
