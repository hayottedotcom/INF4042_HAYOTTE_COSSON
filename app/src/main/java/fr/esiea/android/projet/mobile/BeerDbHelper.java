package fr.esiea.android.projet.mobile;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class BeerDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Beer.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BeerEntry.TABLE_NAME + " (" +
                    BeerEntry._ID + " INTEGER PRIMARY KEY," +
                    BeerEntry.CBIERNAME + TEXT_TYPE + COMMA_SEP +
                    BeerEntry.CDESC + TEXT_TYPE + " )";


    public BeerDbHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public static class BeerEntry implements BaseColumns {
        public static final String TABLE_NAME = "bier_entry";
        public static final String CBIERNAME = "bier_name";
        public static final String CDESC = "bier_desc";
    }

}