package sk.stuba.fiit;

public class EffectUpgrade {
    private String name;
    private int[] costs;
    private int level;
    private Runnable upgrade;

    public EffectUpgrade(String name, int[] costs) {
        this.name = name;
        this.costs = costs;
        this.level = 0;
    }

    public int getCurrentCost() {
        return level < costs.length ? costs[level] : 0;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public Runnable getUpgrade() {
        return upgrade;
    }
    public void setUpgrade(Runnable upgrade) {
        this.upgrade = upgrade;
    }
}
