package org.probuilder.pgquizz;

public class ScoreSetToFirebaseHelper {

    private String subName,score;
    private int setName;
    private float per;

    public ScoreSetToFirebaseHelper(String subName, String score, int setName, float per) {
        this.subName = subName;
        this.score = score;
        this.setName = setName;
        this.per = per;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getSetName() {
        return setName;
    }

    public void setSetName(int setName) {
        this.setName = setName;
    }

    public float getPer() {
        return per;
    }

    public void setPer(float per) {
        this.per = per;
    }
}


