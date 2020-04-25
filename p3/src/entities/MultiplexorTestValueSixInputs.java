package entities;

import entities.TestValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiplexorTestValueSixInputs implements MultiplexorTestValue {
    private List<TestValue> testValues;

    /**
     * Con A0 y A1 controlamos la Dx seleccionada.
     * Como en binario, con A0 y A1, 2 bits, podemos codificar 4 salidas.
     * A0 A1 D0 D1 D2 D3 | Salida
     *  0  0  0  X  X  X | 0
     *  0  0  1  X  X  X | 1
     *  0  1  X  0  X  X | 0
     *  0  1  X  1  X  X | 1
     *  1  0  X  X  0  X | 0
     *  1  0  X  X  1  X | 1
     *  1  1  X  X  X  0 | 0
     *  1  1  X  X  X  1 | 1
     */
    public MultiplexorTestValueSixInputs() {
        testValues = new ArrayList<>();
        TestValue testValue;

        //region D0
        Map<String, Boolean> testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", true);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D0

        //region D1
        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", true);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D1

        //region D2
        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", true);
        testValuesMap.put("D3", false);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);
        //endregion D2

        //region D3
        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", false);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", false);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValue = new TestValue(testValuesMap,false);
        testValues.add(testValue);

        testValuesMap = new HashMap<>();
        testValuesMap.put("A0", true);
        testValuesMap.put("A1", true);
        testValuesMap.put("D0", false);
        testValuesMap.put("D1", false);
        testValuesMap.put("D2", false);
        testValuesMap.put("D3", true);
        testValue = new TestValue(testValuesMap,true);
        testValues.add(testValue);
        //endregion D3
    }

    public List<TestValue> getTestValues() {
        return testValues;
    }
}
