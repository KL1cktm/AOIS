package minimization;

import java.util.ArrayList;
import java.util.List;

public class CarnotMap3 extends CarnotMap {
    public CarnotMap3(List<Character> keys) {
        this.keys = keys;
    }

    @Override
    public void createMap() {
        createHorizonList();
        createVerticalList();
        fillTable();
    }
    @Override
    public void removeKeys() {
        this.keysVert.add(keys.get(0));
        this.keysHor.add(keys.get(1));
        this.keysHor.add(keys.get(2));
    }
    private void createHorizonList() {
        this.horizonValue.add("00");
        this.horizonValue.add("01");
        this.horizonValue.add("11");
        this.horizonValue.add("10");
    }

    private void createVerticalList() {
        this.verticalValue.add("0");
        this.verticalValue.add("1");
    }

    private void fillTable() {
        int num = 0;
        for (int i = 0; i < 2; i++) {
            List<Character> line = new ArrayList<>();
            num = fillLineOfMap(line,num);
            this.table.add(line);
        }
    }
}

