package fr.esiea.android.projet.mobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class RecetteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView   imgRecette = (ImageView) findViewById(R.id.imageViewRecette);
        TextView    txtRecette = (TextView) findViewById(R.id.titrerecette);
        TextView    nomRegion = (TextView) findViewById(R.id.nomRegion);
        TextView    tmpPrep = (TextView) findViewById(R.id.tmpPrep);
        TextView    tmpCuis = (TextView) findViewById(R.id.tmpCuis);


        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        imgRecette.setImageBitmap(bmp);

        txtRecette.setText(getIntent().getStringExtra("titre"));
        nomRegion.setText(getIntent().getStringExtra("nom_region"));
        tmpPrep.setText(getIntent().getStringExtra("tmpPrep"));
        tmpCuis.setText(getIntent().getStringExtra("tmpCuis"));

        String name_recette = txtRecette.getText().toString();
        setTitle(name_recette);
    }





    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), BeerActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
