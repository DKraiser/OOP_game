package sk.stuba.fiit;

import sk.stuba.fiit.waves.Wave;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class WaveCollection extends TreeMap<Float, Wave> {

    public void addWave(Wave wave) {
        put((float) size() * (-1), wave);
        calculateWaveSelectionFloats();
    }

    public Wave getWave(float selector) {
        for (float s : keySet()) {
            if (s > selector) {
                return get(s);
            }
        }
        return null;
    }

    private void calculateWaveSelectionFloats() {
        float sum = 0;
        int used = 0;
        Map.Entry<Float, Wave> entry;
        Map<Float, Wave> recalculatedWaves = new TreeMap<>();

        Iterator<Map.Entry<Float, Wave>> entryIterator = entrySet().iterator();
        while (entryIterator.hasNext()) {
            entry = entryIterator.next();
            sum += entry.getValue().getWaveRarity().ordinal() + 1;
        }

        entryIterator = entrySet().iterator();
        while (entryIterator.hasNext() && used < sum) {
            entry = entryIterator.next();
            used += entry.getValue().getWaveRarity().ordinal() + 1;
            recalculatedWaves.put(used / sum, entry.getValue());
        }
        clear();
        putAll(recalculatedWaves);
    }
}
