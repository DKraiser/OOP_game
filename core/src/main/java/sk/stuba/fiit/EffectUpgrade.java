package sk.stuba.fiit;

/**
 * Represents an upgrade for an effect with associated costs, level, and an upgrade action.
 */
public class EffectUpgrade {
    private String name;
    private int[] costs;
    private int level;
    private Runnable upgrade;

    /**
     * Constructs a new {@code EffectUpgrade} with the specified name and costs.
     *
     * @param name  the name of the upgrade
     * @param costs an array of costs for each level of the upgrade
     */
    public EffectUpgrade(String name, int[] costs) {
        this.name = name;
        this.costs = costs;
        this.level = 0;
    }

    /**
     * Gets the current cost for the upgrade based on its level.
     *
     * @return the cost for the current upgrade level, or 0 if the level exceeds available costs
     */
    public int getCurrentCost() {
        return level < costs.length ? costs[level] : 0;
    }

    /**
     * Gets the name of the upgrade.
     *
     * @return the name of the upgrade
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the upgrade.
     *
     * @param name the new name of the upgrade
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current level of the upgrade.
     *
     * @return the current level of the upgrade
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the upgrade.
     *
     * @param level the new level for the upgrade
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the upgrade action that will be executed when the upgrade is applied.
     *
     * @return the {@link Runnable} representing the upgrade action
     */
    public Runnable getUpgrade() {
        return upgrade;
    }

    /**
     * Sets the upgrade action for the upgrade.
     *
     * @param upgrade the {@link Runnable} representing the upgrade action
     */
    public void setUpgrade(Runnable upgrade) {
        this.upgrade = upgrade;
    }
}
