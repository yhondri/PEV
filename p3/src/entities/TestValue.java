package entities;

import java.util.Map;

public class TestValue {
    private Map<String, Boolean> valuesMap;
    private boolean result;


    public TestValue(Map<String, Boolean> valuesMap, boolean result) {
        this.valuesMap = valuesMap;
        this.result = result;
    }

    public Map<String, Boolean> getValuesMap() {
        return valuesMap;
    }

    public boolean getResult() {
        return result;
    }
}
