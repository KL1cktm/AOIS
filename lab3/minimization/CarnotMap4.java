package minimization;

import java.util.ArrayList;
import java.util.List;

public class CarnotMap4 extends CarnotMap{
    public CarnotMap4(List<Character> keys) {
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
        this.keysVert.add(keys.get(1));
        this.keysHor.add(keys.get(2));
        this.keysHor.add(keys.get(3));
    }
    private void createHorizonList() {
        this.horizonValue.add("00");
        this.horizonValue.add("01");
        this.horizonValue.add("11");
        this.horizonValue.add("10");
    }
    private void createVerticalList() {
        this.verticalValue.add("00");
        this.verticalValue.add("01");
        this.verticalValue.add("11");
        this.verticalValue.add("10");
    }
    private void fillTable() {
        int num = 0;
        for (int i = 0; i < 4; i++) {
            List<Character> line = new ArrayList<>();
            if (i == 2) {
                num = num+4;
            }
            if (i == 3) {
                num = num-8;
            }
            num = fillLineOfMap(line,num);
            this.table.add(line);
        }
    }
}
