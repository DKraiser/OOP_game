package sk.stuba.fiit;

import sk.stuba.fiit.waves.Wave;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A specialized {@link TreeMap} that manages a collection of {@link Wave} objects,
 * using weighted rarity-based keys to facilitate probabilistic selection.
 *
 * <p>Each {@code Wave} is assigned a floating-point key based on its rarity.
 * These keys are recalculated upon every addition to ensure fair distribution
 * for selection using the {@link #getWave(float)} method.
 */
public class WaveCollection extends TreeMap<Float, Wave> {

    /**
     * Adds a new {@link Wave} to the collection and recalculates selection keys
     * based on rarity.
     *
     * @param wave the wave to be added
     */
    public void addWave(Wave wave) {
        put((float) size() * (-1), wave);
        calculateWaveSelectionFloats();
    }

    /**
     * Returns a {@link Wave} based on the provided selector value.
     * Waves are selected based on rarity-weighted intervals.
     *
     * @param selector a float value typically between 0 and 1 used to select a wave
     * @return the selected wave, or {@code null} if no match is found
     */
    public Wave getWave(float selector) {
        for (float s : keySet()) {
            if (s > selector) {
                return get(s);
            }
        }
        return null;
    }

    /**
     * Recalculates the floating-point keys for all waves in the collection.
     * The calculation is based on the rarity of each wave, which influences
     * its selection weight. More rare waves occupy smaller intervals.
     */
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
