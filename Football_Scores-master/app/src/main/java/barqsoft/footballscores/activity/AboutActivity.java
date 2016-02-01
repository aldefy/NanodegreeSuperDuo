package barqsoft.footballscores.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import barqsoft.footballscores.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class AboutActivity extends AppCompatActivity {


    @Bind(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setupToolBar();
        setupFragment();
    }

    private void setupToolBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AboutFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Fragment containing a simple view.
     */
    public static class AboutFragment extends Fragment {

        public AboutFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_about, container, false);
        }
    }
}
