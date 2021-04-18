package org.probuilder.pgquizz;

public class ScoreModel {

    private String subName;
    private int setName;
    private int per;

    public ScoreModel(){}

    public ScoreModel(String subName, int setName, int per) {
        this.subName = subName;
        this.setName = setName;
        this.per = per;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getSetName() {
        return setName;
    }

    public void setSetName(int setName) {
        this.setName = setName;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }
}
