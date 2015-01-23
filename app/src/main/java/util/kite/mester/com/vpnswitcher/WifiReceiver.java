package util.kite.mester.com.vpnswitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import de.blinkt.openvpn.api.IOpenVPNStatusCallback;

/**
 * Created by mester on 2015.01.20..
 */
public class WifiReceiver extends BroadcastReceiver {

    private final String LOGTAG="VPNSERVICE";
    public String wifissid ;

    final String EXTRA_NAME = "de.blinkt.openvpn.shortcutProfileName";

    @Override
    public void onReceive(Context context, Intent intent) {
       Toast.makeText(context, "Receiver started", Toast.LENGTH_LONG).show();
       Log.i(LOGTAG,"Receiver started");

    /*    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null) {
            if (info.isConnected()) {
                // Do your work.
                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                ssid = wifiInfo.getSSID();
                Toast.makeText(context, "SSID : " + ssid, Toast.LENGTH_LONG).show();
            }
        }*/

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        wifissid = wifiMgr.getConnectionInfo().getSSID();

        Log.i(LOGTAG, "Az ssid: " + wifissid);

        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Ekkor nem kell vpn vagy disconnectálni kell
        if ("\"KITE\""==wifissid.intern()) {
            Toast.makeText(context,"KITE intern egyenlő : " + wifissid,Toast.LENGTH_LONG).show();
        } else if ("KITE"!=wifissid.intern()) {
            Toast.makeText(context,"KITE intern nem egyenlő: " + wifissid,Toast.LENGTH_LONG).show();
        }



        if (wifissid.toUpperCase().equals("\"KITE\"")) {
            Toast.makeText(context, "Need disconnect VPN", Toast.LENGTH_LONG).show();
            Log.i(LOGTAG, "Need disconnect VPN");
            disconnect(context);
            Log.i(LOGTAG, "VPN Disconnected");
        }

        // Ekkor kell openvpn vagy connectálni kell.
        else if (mobile.isConnected() || (wifi.isConnected() && !wifissid.toUpperCase().equals("KITE"))) {
            Toast.makeText(context,"Connecting VPN",Toast.LENGTH_LONG).show();
            Log.i(LOGTAG, "Need connecting VPN");
            connect(context);
            Log.i(LOGTAG, "Connected to VPN");
        }
    }

    public void connect(Context c) {

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.setClassName("de.blinkt.openvpn", "de.blinkt.openvpn.LaunchVPN");
        shortcutIntent.putExtra(EXTRA_NAME, "config.itware-kite-tablet2");
        c.startActivity(shortcutIntent);
    }

    public void disconnect(Context c) {

        Intent vpnIntent = new Intent(c,VpnIntentService.class);
        vpnIntent.putExtra(VpnIntentService.KEY_SSID, wifissid);
        c.startService(vpnIntent);
    }


}
