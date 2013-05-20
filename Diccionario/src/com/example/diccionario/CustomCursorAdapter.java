package com.example.diccionario;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
 
public class CustomCursorAdapter extends CursorAdapter {
 
    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }
 
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    	//como se vera cada item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.row_item, parent, false);
        return retView;
    }
 
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    	//tomar la data de cursor y ponerlo en el view
        TextView textViewPalabra = (TextView) view.findViewById(R.id.row_palabra);
        textViewPalabra.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        Log.d("BDpalabra",": "+ cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        
        TextView textViewTraduccion = (TextView) view.findViewById(R.id.row_traduccion);
        textViewTraduccion.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        Log.d("BDtraduccion",": "+ cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        
        TextView textViewDescripcion = (TextView) view.findViewById(R.id.row_descripcion);
        textViewDescripcion.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
        Log.d("BDdescripcion",": "+ cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
    }
}