package fr.esiea.android.projet.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class BierUpdate extends BroadcastReceiver{
    public static final String BIERS_UPDATE = "fr.esiea.android.projet.mobile.BIERS_UPDATE";
    private BeerActivity.BiersAdapter ba;
    public BierUpdate(BeerActivity.BiersAdapter bi) {
        ba = bi;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    Log.d(TAG,intent.getAction());
        Toast txtDownloaded = Toast.makeText(context,"JSON OK",Toast.LENGTH_LONG);
        txtDownloaded.show();
        ba.setNewBiere();
}

}
