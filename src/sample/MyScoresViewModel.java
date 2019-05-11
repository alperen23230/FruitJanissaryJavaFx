package sample;

import java.sql.Date;
import java.time.LocalDate;

public class MyScoresViewModel {
    private String username;
    private Integer score;
    private Integer duration;
    private String playDate;
    private String firstName;
    private String lastName;

    public MyScoresViewModel(String username, Integer score, Integer duration, String playDate, String firstName, String lastName) {
        this.username = username;
        this.score = score;
        this.duration = duration;
        this.playDate = playDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }
}
