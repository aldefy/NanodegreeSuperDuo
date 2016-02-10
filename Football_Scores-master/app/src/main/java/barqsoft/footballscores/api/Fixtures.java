package barqsoft.footballscores.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 01/02/16.
 */
public class Fixtures {

    private Result result;

    private String status;

    private String matchday;

    @SerializedName("_links")
    private Links _links;

    private String awayTeamName;

    private String date;

    private String homeTeamName;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    @Override
    public String toString() {
        return "ClassPojo [result = " + result + ", status = " + status + ", matchday = " + matchday + ", _links = " + _links + ", awayTeamName = " + awayTeamName + ", date = " + date + ", homeTeamName = " + homeTeamName + "]";
    }
}
