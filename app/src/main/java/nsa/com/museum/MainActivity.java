package nsa.com.museum;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

public class MainActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {

    GCellBeaconScanManager scanMan;
    ListView lv;
    ArrayAdapter beaconAdap;
    ArrayList <String> beacontest = new ArrayList<>();
    ArrayList <String> historyBeacons = new ArrayList<>();


    ArrayList<String> museums = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    EditText postcodeInput;
    ListView museumsList;
    Button searchBtn;
    Button findBtn;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        checkPermissions();

        //Create adapter for beacons
        beaconAdap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, beacontest);

        //Create a manager with the application context
        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);


        //Start a scan
        scanMan.startScanningForBeacons();

        postcodeInput = (EditText) findViewById(R.id.editSearch);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        museumsList = (ListView) findViewById(R.id.museumsList);
        findBtn = (Button) findViewById(R.id.findBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = postcodeInput.getText().toString();
                if (postcode.length() < 6 ) {
                    Toast error = Toast.makeText(getApplicationContext(), "Please enter a valid postcode", Toast.LENGTH_SHORT);
                    error.show();
                }
                else {
                    Toast nearYou = Toast.makeText(getApplicationContext(), "Loaded Museums near you", Toast.LENGTH_SHORT);
                    nearYou.show();
                    museums.add("Cardiff");
                    museums.add("Newport");
                    museums.add("Birmingham");
                    museums.add("London");
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, museums);
                    museumsList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ((EditText)findViewById(R.id.editSearch)).setText(" ");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        museumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Loaded: " + adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                Intent beacons = new Intent(getApplicationContext(), BeaconActivity.class);
                startActivity(beacons);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(getApplicationContext(), NewSettingsActivity.class);
                startActivity(settings);
                return true;

            case R.id.action_help:
                Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        for (GCelliBeacon beacon : list) {
            if(beaconAdap.getPosition(beacon.getProxUuid().getStringFormattedUuid()) == -1) {

                beaconAdap.add(beacon.getProxUuid().getStringFormattedUuid());
            }
        }
    }

    /**
     * Ignore ALL of the methods below
     * @param gCellBeaconRegion
     */

    @Override
    public void didEnterBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didExitBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didRangeBeaconsinRegion(GCellBeaconRegion gCellBeaconRegion, List<GCelliBeacon> list) {

    }

    @Override
    public void bleNotSupported() {

    }

    @Override
    public void bleNotEnabled() {

    }

    @Override
    public void locationPermissionsDenied() {

    }

    public void checkPermissions() {
        //Request permission if ANY permissions have been denied
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions(getApplicationContext(), permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERM_CODE);
        }
    }

    /**
     * Iterate through all permissions provided and ensure all have been approved
     * @param context
     * @param permissions
     * @return
     */
    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Called when the user has dealt with the permissions box and we are told if they granted or denied access
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001: {
                /**
                 * In order to not bother users, we should only be asking for permissions when they perform actions that require it.
                 * So, asking for 10 permissions each time an app starts is bad practice.
                 * We should ask for each when they do something that requires (such as disabling wifi or taking a picture)
                 */
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }
        }
    }
}






