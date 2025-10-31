package isetb.tp6.projetmobile.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class PostulerDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "postuler_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_POSTULER = "postuler_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CV = "cv";
    private static final String COLUMN_DECISION = "decision";
    private static final String COLUMN_Stagiaire_ID = "Stagiaire_id";
    private static final String COLUMN_OFFRE_ID = "offre_id";
    private SQLiteDatabase db;

    public PostulerDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_POSTULER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CV + " TEXT," +
                COLUMN_DECISION + " INTEGER," +
                COLUMN_Stagiaire_ID + " INTEGER," +
                COLUMN_OFFRE_ID + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_Stagiaire_ID + ") REFERENCES Stagiaire_table(id)," +
                "FOREIGN KEY(" + COLUMN_OFFRE_ID + ") REFERENCES offre_table(id)" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTULER);
        onCreate(db);
    }

    public Boolean addPostuler(Postuler postuler) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CV, postuler.getCv());
        values.put(COLUMN_DECISION, postuler.isDecision() ? 1 : 0);
        values.put(COLUMN_Stagiaire_ID, postuler.getStagiaire().getId());
        values.put(COLUMN_OFFRE_ID, postuler.getOffre().getId());

        long x = db.insert(TABLE_POSTULER, null, values);
        return x != -1;
    }

    public void updatePostuler(Postuler postuler, int id) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CV, postuler.getCv());
        values.put(COLUMN_DECISION, postuler.isDecision() ? 1 : 0);
        values.put(COLUMN_Stagiaire_ID, postuler.getStagiaire().getId());
        values.put(COLUMN_OFFRE_ID, postuler.getOffre().getId());
        db.update(TABLE_POSTULER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    public void removePostuler(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_POSTULER, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int getPostulerCount() {
        db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_POSTULER, null, null, null, null, null, null, null);
        return c.getCount();
    }


    public List<Postuler> getPostulerByStagiaireId(long stagiaireId) {
        db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_CV, COLUMN_DECISION, COLUMN_Stagiaire_ID, COLUMN_OFFRE_ID};
        String selection = COLUMN_Stagiaire_ID + " = ?";
        String[] selectionArgs = {String.valueOf(stagiaireId)};

        Cursor cursor = db.query(TABLE_POSTULER, columns, selection, selectionArgs, null, null, null);

        List<Postuler> postulers = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int cvColumnIndex = cursor.getColumnIndex(COLUMN_CV);
            int decisionColumnIndex = cursor.getColumnIndex(COLUMN_DECISION);
            int stagiaireColumnIndex = cursor.getColumnIndex(COLUMN_Stagiaire_ID);
            int offreIdColumnIndex = cursor.getColumnIndex(COLUMN_OFFRE_ID);

            if (idColumnIndex >= 0 && cvColumnIndex >= 0 && decisionColumnIndex >= 0 && stagiaireColumnIndex >= 0 && offreIdColumnIndex >= 0) {
                long id = cursor.getLong(idColumnIndex);
                String cv = cursor.getString(cvColumnIndex);
                boolean decision = cursor.getInt(decisionColumnIndex) == 1;
                long stagiairId = cursor.getLong(stagiaireColumnIndex);
                long offreId = cursor.getLong(offreIdColumnIndex);

                Stagiaire stagiaire = new Stagiaire(stagiairId);
                Offre offre = new Offre(offreId);

                Postuler postuler = new Postuler(id, cv, decision, stagiaire, offre);
                postulers.add(postuler);
            }
        }

        cursor.close();
        return postulers;
    }

    public List<Postuler> getAllPostulers() {
        db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_CV, COLUMN_DECISION, COLUMN_Stagiaire_ID, COLUMN_OFFRE_ID};

        Cursor cursor = db.query(TABLE_POSTULER, columns, null, null, null, null, null);

        List<Postuler> postulers = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int cvColumnIndex = cursor.getColumnIndex(COLUMN_CV);
            int decisionColumnIndex = cursor.getColumnIndex(COLUMN_DECISION);
            int stagiaireColumnIndex = cursor.getColumnIndex(COLUMN_Stagiaire_ID);
            int offreIdColumnIndex = cursor.getColumnIndex(COLUMN_OFFRE_ID);

            if (idColumnIndex >= 0 && cvColumnIndex >= 0 && decisionColumnIndex >= 0 && stagiaireColumnIndex >= 0 && offreIdColumnIndex >= 0) {
                long id = cursor.getLong(idColumnIndex);
                String cv = cursor.getString(cvColumnIndex);
                boolean decision = cursor.getInt(decisionColumnIndex) == 1;
                long stagiairId = cursor.getLong(stagiaireColumnIndex);
                long offreId = cursor.getLong(offreIdColumnIndex);

                Stagiaire stagiaire = new Stagiaire(stagiairId);
                Offre offre = new Offre(offreId);

                Postuler postuler = new Postuler(id, cv, decision, stagiaire, offre);
                postulers.add(postuler);
            }
        }

        cursor.close();
        return postulers;
    }

    public ArrayList<Postuler> getAllPostuler() {
        ArrayList<Postuler> postulers = new ArrayList<>();
        db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_POSTULER, null, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idColumnIndex = c.getColumnIndex(COLUMN_ID);
            int cvColumnIndex = c.getColumnIndex(COLUMN_CV);
            int decisionColumnIndex = c.getColumnIndex(COLUMN_DECISION);

            do {
                if (idColumnIndex >= 0 && cvColumnIndex >= 0 && decisionColumnIndex >= 0) {
                    Long id = c.getLong(idColumnIndex);
                    String cv = c.getString(cvColumnIndex);
                    boolean decision = c.getInt(decisionColumnIndex) == 1;

                    Stagiaire stagiaire = new Stagiaire();
                    Offre offre = new Offre(1L);

                    Postuler postuler = new Postuler(id, cv, decision, stagiaire, offre);
                    postulers.add(postuler);
                }
            } while (c.moveToNext());
        }

        c.close();
        return postulers;
    }

}

