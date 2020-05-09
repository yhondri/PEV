package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiplexorTestValueEightInputs implements MultiplexorTestValue {
    private List<TestValue> testValues;
    public MultiplexorTestValueEightInputs(List<List<Boolean>> data) {
        testValues = new ArrayList<>();
        for (List<Boolean> row : data) {
            Map<String, Boolean> testValuesMap = new HashMap<>();
            testValuesMap.put("A0", row.get(0));
            testValuesMap.put("A1", row.get(1));
            testValuesMap.put("A2", row.get(2));
            testValuesMap.put("D0", row.get(3));
            testValuesMap.put("D1", row.get(4));
            testValuesMap.put("D2", row.get(5));
            testValuesMap.put("D3", row.get(6));
            testValuesMap.put("D4", row.get(7));
            testValuesMap.put("D5", row.get(8));
            testValuesMap.put("D6", row.get(9));
            testValuesMap.put("D7", row.get(10));
            TestValue testValue = new TestValue(testValuesMap, row.get(11));
            testValues.add(testValue);
        }
    }

    public List<TestValue> getTestValues() {
        return testValues;
    }
}
