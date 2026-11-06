package com.example.ui.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScenarioContext {
    private final Map<String, Object> values = new HashMap<>();

    public void set(String key, Object value) {
        values.put(Objects.requireNonNull(key, "Scenario context key must not be null"),
                Objects.requireNonNull(value, "Scenario context value must not be null"));
    }

    public <T> T get(String key, Class<T> valueType) {
        Objects.requireNonNull(key, "Scenario context key must not be null");
        Objects.requireNonNull(valueType, "Scenario context value type must not be null");
        Object value = values.get(key);
        if (value == null) {
            throw new IllegalStateException("No scenario value has been stored for key: " + key);
        }
        if (!valueType.isInstance(value)) {
            throw new IllegalStateException("Scenario value for key '" + key + "' is not a "
                    + valueType.getSimpleName());
        }
        return valueType.cast(value);
    }
}
