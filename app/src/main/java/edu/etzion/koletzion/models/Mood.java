package edu.etzion.koletzion.models;

public class Mood {
    private MyMood myMood;
    private String aboutMyMood;

    //Constructor

    public Mood(MyMood myMood, String aboutMyMood) {
        this.myMood = myMood;
        this.aboutMyMood = aboutMyMood;
    }

    // Getters

    public MyMood getMyMood() {
        return myMood;
    }
    public String getAboutMyMood() {
        return aboutMyMood;
    }

    // toString
    @Override
    public String toString() {
        return "Mood{" +
                "myMood=" + myMood +
                ", aboutMyMood='" + aboutMyMood + '\'' +
                '}';
    }

}
