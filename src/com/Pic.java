package com;

/**
 * Created by fil-n on 28.12.17.
 */
public class Pic {
    private int id;
    private String image;
    private String tag;
    private int answers;
    private int rightAnswers;
    private double p;

    public Pic(int id, String image, String tag, int answers, int rightAnswers, double p) {
        this.id = id;
        this.image = image;
        this.tag = tag;
        this.answers = answers;
        this.rightAnswers = rightAnswers;
        this.p = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", tag='" + tag + '\'' +
                ", answers=" + answers +
                ", rightAnswers=" + rightAnswers +
                ", p=" + p +
                '}';
    }
}
