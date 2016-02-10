package barqsoft.footballscores.api;

/**
 * Created by aditlal on 01/02/16.
 */
public class Result {
    private String goalsHomeTeam;

    private String goalsAwayTeam;

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

    @Override
    public String toString() {
        return "ClassPojo [goalsHomeTeam = " + goalsHomeTeam + ", goalsAwayTeam = " + goalsAwayTeam + "]";
    }
}
