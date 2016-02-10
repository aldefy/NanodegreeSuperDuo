package barqsoft.footballscores.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 01/02/16.
 */
public class ResponseModel
{
    @SerializedName("fixtures")
    private List<Fixtures> fixtures;

    private String count;

    private String timeFrameStart;

    private String timeFrameEnd;

    public List<Fixtures> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixtures> fixtures) {
        this.fixtures = fixtures;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getTimeFrameStart ()
    {
        return timeFrameStart;
    }

    public void setTimeFrameStart (String timeFrameStart)
    {
        this.timeFrameStart = timeFrameStart;
    }

    public String getTimeFrameEnd ()
    {
        return timeFrameEnd;
    }

    public void setTimeFrameEnd (String timeFrameEnd)
    {
        this.timeFrameEnd = timeFrameEnd;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fixtures = "+fixtures+", count = "+count+", timeFrameStart = "+timeFrameStart+", timeFrameEnd = "+timeFrameEnd+"]";
    }
}