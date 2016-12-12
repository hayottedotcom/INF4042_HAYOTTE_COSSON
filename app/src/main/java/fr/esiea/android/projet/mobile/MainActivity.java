package fr.esiea.android.projet.mobile;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        /*final TextView dateZone = (TextView)findViewById(R.id.textView2);
        final DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                dateZone.setText("Date: "+dayOfMonth+"/"+month+"/"+year);
            }
        },2016,11,07);

        Button but2 = (Button)findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show();
            }
        });*/

        ImageView bg = (ImageView) findViewById(R.id.backgroundImage);
        bg.setFitsSystemWindows(true);

    }


    public void Notif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Au bon terroir de chez nous")
                        .setContentText("Vous accédez actuellement à la version Web de notre application, réalisée dans le cadre du projet Web 4A !");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


    public void openNewActivity(View view) {
        Notif();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://82.236.131.8:8080"));
        startActivity(browserIntent);
    }

    public void openBeerActivity(View view) {
        Intent intent = new Intent (this, BeerActivity.class);
        startActivity(intent);
    }






}
