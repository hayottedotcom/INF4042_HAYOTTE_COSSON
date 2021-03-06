package fr.esiea.android.projet.mobile;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPlatServices extends IntentService {

    public static final String ACTION_GET_ALL_BIERS = "fr.esiea.projet.mobile.ACTION_GET_ALL_BIERS";

    public GetPlatServices() {
        super("GetPlatServices");
    }

    public static void startActionGetAllBiers(Context context) {
        Intent intent = new Intent(context, GetPlatServices.class);
        intent.setAction(ACTION_GET_ALL_BIERS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_BIERS.equals(action)) {
                //getAllBiers();
                handleActionBiers();
            }
        }
    }

    /*private void getAllBiers() {
        Log.d("LOOOOOG", "This is Service Downloader!!!:D");

        PlatDbHelper db_helper = new PlatDbHelper(this);
        SQLiteDatabase db = db_helper.getWritableDatabase();

        URL url;
        JSONArray jsa;
        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                jsa = new JSONArray(new String(buffer,"UTF-8"));

                for(int i =0; i < jsa.length(); i++){
                    JSONObject jso = (JSONObject)jsa.get(i);
                    ContentValues values = new ContentValues();
                    values.put(PlatDbHelper.BeerEntry.CBIERNAME, jso.getString("name"));
                    values.put(PlatDbHelper.BeerEntry.CDESC, jso.getString("description"));

                    db.insert(PlatDbHelper.BeerEntry.TABLE_NAME, null, values);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

    private void handleActionBiers() {
        Log.d("TAG", "Thread service name:"+Thread.currentThread().getName());
        URL url = null;
        try {
            url = new URL("http://82.236.131.8/jsonAuBonTerroirDeChezNous.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(), "recettes.json"));
                Log.d("TAG", "Recettes téléchargées !");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PatUpdate.BIERS_UPDATE));


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}