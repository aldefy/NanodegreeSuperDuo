package barqsoft.footballscores.api;

/**
 * Created by aditlal on 01/02/16.
 */
public class Soccerseason {
    private String href;

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [href = "+href+"]";
    }
}
