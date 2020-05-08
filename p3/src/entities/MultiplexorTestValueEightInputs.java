package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiplexorTestValueEightInputs implements MultiplexorTestValue {
    private List<TestValue> testValues;

    public MultiplexorTestValueEightInputs() {
        testValues = new ArrayList<>();
        TestValue testValue;

        //region D0
        Map<String, Boolean> testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D0

        //region D1

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D1

        //region D2

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D2

        //region D3

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D3

        //region D4

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", true);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D4

        //region D5

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", true);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D5

        //region D6

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", true);
        testValuesMap.put("D7", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D6

        //region D7

        //000
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //001
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //010
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //011
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //100
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //101
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //110
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        //111
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("A2", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValuesMap.put("D4", false);
        testValuesMap.put("D5", false);
        testValuesMap.put("D6", false);
        testValuesMap.put("D7", true);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);
        //endregion D7
    }

    public List<TestValue> getTestValues() {
        return testValues;
    }
}
