package util.kite.mester.com.vpnswitcher;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by mester on 2015.01.22..
 */
public class VpnIntentService extends IntentService {

    public static final String KEY_SSID = "KEY_SSID";

    public VpnIntentService() {
        super("VpnIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String getwifissid = intent.getStringExtra(KEY_SSID);
        //Toast.makeText(this,"A serviceében megkapott SSID :  " + getwifissid, Toast.LENGTH_LONG).show();
        Intent mainIntent = new Intent(this,VpnSwitcher.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(mainIntent);
    }
}
