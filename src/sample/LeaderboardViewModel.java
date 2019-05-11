package sample;

public class LeaderboardViewModel {
    private String username;
    private Integer score;
    private Integer duration;

    public LeaderboardViewModel(String username, Integer score, Integer duration) {
        this.username = username;
        this.score = score;
        this.duration = duration;
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
}
