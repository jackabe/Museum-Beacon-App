package nsa.com.museum;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class LanguageChangeActivity extends AppCompatActivity {

    Button language;
    Button eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        language = (Button) findViewById(R.id.lang);
        eng = (Button) findViewById(R.id.eng);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uses code from http://stackoverflow.com/questions/2900023/change-language-programmatically-in-android
                Locale myLocale = new Locale("cy");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(getApplicationContext(), LanguageChangeActivity.class);
                startActivity(refresh);
                finish();
            }
        });

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uses code from http://stackoverflow.com/questions/2900023/change-language-programmatically-in-android
                Locale myLocale = new Locale("en");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(getApplicationContext(), LanguageChangeActivity.class);
                startActivity(refresh);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(getApplicationContext(), NewSettingsActivity.class);
                startActivity(settings);
                return true;

            case R.id.action_help:
                Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
                return true;

            case R.id.action_login:
                Intent login = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(login);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
