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
    public MultiplexorTestValueSixInputs(List<List<Boolean>> data) {
        testValues = new ArrayList<>();
        for (List<Boolean> row : data) {
            Map<String, Boolean> testValuesMap = new HashMap<>();
            testValuesMap.put("A0", row.get(0));
            testValuesMap.put("A1", row.get(1));
            testValuesMap.put("D0", row.get(2));
            testValuesMap.put("D1", row.get(3));
            testValuesMap.put("D2", row.get(4));
            testValuesMap.put("D3", row.get(5));
            TestValue testValue = new TestValue(testValuesMap, row.get(6));
            testValues.add(testValue);
        }
    }
    public List<TestValue> getTestValues() {
        return testValues;
    }
}
