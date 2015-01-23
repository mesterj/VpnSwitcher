package util.kite.mester.com.vpnswitcher;

import android.os.IBinder;
import android.os.RemoteException;

import de.blinkt.openvpn.api.*;

/**
 * Created by mester on 2015.01.20..
 */
public class VpnHandler {
  IOpenVPNAPIService service=null;

   private void startProfile() {
       try {
           service.startVPN("config.itware-kite-tablet2");
       } catch (RemoteException e) {
           e.printStackTrace();
       }
   }

    private void stopVpn(){
        try {
            service.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
