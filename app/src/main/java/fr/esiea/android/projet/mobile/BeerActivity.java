package fr.esiea.android.projet.mobile;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BeerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BiersAdapter ba = new BiersAdapter();

        recyclerView = (RecyclerView)findViewById(R.id.rv_biere);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        IntentFilter intentFilter = new IntentFilter(BierUpdate.BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(ba),intentFilter);
        GetBiersServices.startActionGetAllBiers(this);

        recyclerView.setAdapter(ba);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

        JSONArray bieres;

        BiersAdapter() {
            this.bieres = getBiersFromFile();
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            return new BierHolder(li.inflate(R.layout.rv_bier_element, parent, false));
        }

        @Override
        public void onBindViewHolder(final BierHolder holder,final int position) {
            try {
                holder.name.setText(bieres.getJSONObject(position).getString("nom_recette"));
                new DownloadImageTask(holder.img)
                        .execute(bieres.getJSONObject(position).getString("image_url"));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.img.buildDrawingCache();
                        Bitmap image= holder.img.getDrawingCache();
                        Bundle extras = new Bundle();
                        extras.putParcelable("imagebitmap", image);
                        Intent intent=new Intent(getApplicationContext(),RecetteActivity.class);
                        intent.putExtras(extras);
                        try {
                            intent.putExtra("titre",bieres.getJSONObject(position).getString("nom_recette"));
                            intent.putExtra("nom_region", bieres.getJSONObject(position).getString("nom_region"));
                            intent.putExtra("tmpPrep", bieres.getJSONObject(position).getString("temps_preparation_min"));
                            intent.putExtra("tmpCuis", bieres.getJSONObject(position).getString("temps_cuisson_min"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);


                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return bieres.length();
        }

        public void setNewBiere() {
            this.bieres=getBiersFromFile();
            notifyDataSetChanged();
        }

        public class BierHolder extends RecyclerView.ViewHolder {
            private JSONArray biers;
            public ImageView img;
            public ImageView imgRecette;
            public TextView name;
            public TextView name_region;
            public BierHolder(View itemView) {
                super(itemView);
                    img = (ImageView) itemView.findViewById(R.id.imageView);
                    name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
                    name_region = (TextView) itemView.findViewById(R.id.rv_bier_element_name);

            }
        }

        public JSONArray getBiersFromFile() {
            try {
                InputStream is = new FileInputStream(getCacheDir()+"/"+"recettes.json");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                return new JSONArray(new String(buffer, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONArray();
            } catch (JSONException e) {
                e.printStackTrace();
                return new JSONArray();
            }
        }


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Drawable doInBackground(String... urls) {
            String urldisplay = urls[0];
            Drawable d = null;
            try {
                URL url = new URL(urldisplay);
                InputStream content = (InputStream) url.getContent();
                d = Drawable.createFromStream(content, null);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return d;
        }

        protected void onPostExecute(Drawable result) {
            bmImage.setImageDrawable(result);
        }
    }


}


