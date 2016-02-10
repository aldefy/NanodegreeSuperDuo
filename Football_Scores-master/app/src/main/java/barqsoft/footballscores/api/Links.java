package barqsoft.footballscores.api;

/**
 * Created by aditlal on 01/02/16.
 */
public class Links {

    private AwayTeam awayTeam;

    private Soccerseason soccerseason;

    private Self self;

    private HomeTeam homeTeam;

    public AwayTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(AwayTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Soccerseason getSoccerseason() {
        return soccerseason;
    }

    public void setSoccerseason(Soccerseason soccerseason) {
        this.soccerseason = soccerseason;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public HomeTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(HomeTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    @Override
    public String toString() {
        return "ClassPojo [awayTeam = " + awayTeam + ", soccerseason = " + soccerseason + ", self = " + self + ", homeTeam = " + homeTeam + "]";
    }
}
