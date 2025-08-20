package codebuds.poketmon.aggregate;

import java.util.List;

public class PoketMon {
    private int poketNum;
    private String name;
    private int Level;
    private double catchRate;
    private double appearRate;
    private List<Skil> skils;

    public PoketMon() {
    }

    public PoketMon(int poketNum, String name, int level, double catchRate, double appearRate, List<Skil> skils) {
        this.poketNum = poketNum;
        this.name = name;
        Level = level;
        this.catchRate = catchRate;
        this.appearRate = appearRate;
        this.skils = skils;
    }

    public int getPoketNum() {
        return poketNum;
    }

    public void setPoketNum(int poketNum) {
        this.poketNum = poketNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public double getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(double catchRate) {
        this.catchRate = catchRate;
    }

    public double getAppearRate() {
        return appearRate;
    }

    public void setAppearRate(double appearRate) {
        this.appearRate = appearRate;
    }

    public List<Skil> getSkils() {
        return skils;
    }

    public void setSkils(List<Skil> skils) {
        this.skils = skils;
    }

    @Override
    public String toString() {
        return "PoketMon{" +
                "poketNum=" + poketNum +
                ", name='" + name + '\'' +
                ", Level=" + Level +
                ", catchRate=" + catchRate +
                ", appearRate=" + appearRate +
                ", skils=" + skils +
                '}';
    }
}
