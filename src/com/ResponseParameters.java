package com;

import java.util.ArrayList;
import java.util.List;

public class ResponseParameters {
    private String tag;
    private int numberImages;
    List<String> pics;


    public ResponseParameters(String tag, int numberImages, List<String> pics) {
        this.tag = tag;
        this.numberImages = numberImages;
        this.pics = pics;
    }

    public ResponseParameters() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumberImages() {
        return numberImages;
    }

    public void setNumberImages(int numberImages) {
        this.numberImages = numberImages;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    @Override
    public String toString() {
        String res =  "{" +
                "\"tag\": \"" + tag + "\" ," +
                "\"numberImages\": " + numberImages +", " +
                "\"pics\": [";
        for (String src: pics) {
            res += " \"" +src+"\", ";
        }
        res = res.substring(0, res.length() - 2);
        res +=" ]}";

        return res;
    }
}