package com.example.diccionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DiccionarioDB {
 
    private static final String TAG = DiccionarioDB.class.getSimpleName();
 
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DIC.db";
 
    // table configuration
    private static final String TABLE_NAME = "Tpalabras";         // Table name
    private static final String TABLE_COLUMN_ID = "_id";     // a column named "_id" is required for cursor
    private static final String TABLE_COLUMN_PAL = "dic_palabra";
    private static final String TABLE_COLUMN_TRA = "dic_traduccion";
    private static final String TABLE_COLUMN_DES = "dic_descripcion";
    
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
 
    public DiccionarioDB(Context aContext) {
         
        openHelper = new DatabaseOpenHelper(aContext);
        database = openHelper.getWritableDatabase();
    }
 
    public void insertData (String palabra, String traduccion, String descripcion) {
        ContentValues contentValues = new ContentValues();
 
        contentValues.put(TABLE_COLUMN_PAL, palabra);
        contentValues.put(TABLE_COLUMN_TRA, traduccion);
        contentValues.put(TABLE_COLUMN_DES, descripcion);
        
        database.insert(TABLE_NAME, null, contentValues);
    }
 
    public Cursor getAllData () {
        String sql = "SELECT * FROM " + TABLE_NAME;
        Log.d(TAG, "getAllData SQL: " + sql);
        return database.rawQuery(sql, null);
    }
    
    public Cursor traducir(String entrada)
    {
    	Log.d("TRADUCIENDO","entrada: "+ entrada);
    	String sql="SELECT * FROM " + TABLE_NAME +" WHERE dic_palabra = '" + entrada +"'";
    	Log.d("TRADUCIENDO", sql);
    	return(database.rawQuery(sql, null));
    }
    
	public int actualizar(int id, String palabra, String traduccion,String descripcion) {
		int filasAfectadas = 0;
		if (database != null) {
			ContentValues cv = new ContentValues();
			cv.put("palabra", palabra);
			cv.put("traduccion", traduccion);
			cv.put("descripcion", descripcion);
			filasAfectadas = (int) database.update(TABLE_NAME, cv, "id_usuario = ?", new String[] { String.valueOf(id) });
		}
		database.close();
		return 0;
	}
	
	public void poblarDb(){
		insertData("estudiante","student", "Persona cuya funcion es estudiar en algun centro educativo");
		insertData("arma","weapon", "es un objeto usado para disparar balas");
		insertData("table","mesa", "mueble de la casa, infaltable en el comedor");
		insertData("table","menu", "conjunto de platos de una comida");
		insertData("table","tabla", "table of contents, es usado como tabla de contenidos");
		insertData("business","de negocios", "asuntos de negocios");
		insertData("business","empresarial", "I put on my business suit, manten una actitud empresarial con los clientes ");
		insertData("business","asunto", "This is not your business, este no es tu asunto");
		insertData("plant","planta", "organismo viviente ");
		insertData("plant","fabrica", "lugar donde se elaboran productos");
	}
 
    private class DatabaseOpenHelper extends SQLiteOpenHelper {
 
        public DatabaseOpenHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create your tables here
 
            String sql = "CREATE TABLE " + TABLE_NAME + "( " + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, " + 
            TABLE_COLUMN_PAL + " TEXT, " + TABLE_COLUMN_TRA + " TEXT, " + TABLE_COLUMN_DES + " TEXT )";
            Log.d(TAG, "onCreate SQL: " + sql);
            sqLiteDatabase.execSQL(sql);
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
            Log.d(TAG, "onUpgrade SQL: " + buildSQL);
            sqLiteDatabase.execSQL(buildSQL);
            onCreate(sqLiteDatabase);
        }
    }
}