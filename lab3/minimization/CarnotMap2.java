package minimization;

import java.util.ArrayList;
import java.util.List;

public class CarnotMap2 extends CarnotMap {
    public CarnotMap2(List<Character> keys) {
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
    }
    private void createHorizonList() {
        this.horizonValue.add("0");
        this.horizonValue.add("1");
    }
    private void createVerticalList() {
        this.verticalValue.add("0");
        this.verticalValue.add("1");
    }
    private void fillTable() {
        int num=0;
        for (int i=0;i<2;i++) {
            List<Character> line = new ArrayList<>();
            line.add(this.normalForm.get(num++));
            line.add(this.normalForm.get(num++));
            this.table.add(line);
        }
    }
}

