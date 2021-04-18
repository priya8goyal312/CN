package org.probuilder.pgquizz;

public class MyScoreModel {

    private int setName;
    private String subName;
    private int per;

    public MyScoreModel(int setName, String subName, int per) {
        this.setName = setName;
        this.subName = subName;
        this.per = per;
    }

    public int getSetName() {
        return setName;
    }

    public void setSetName(int setName) {
        this.setName = setName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }
}
