package com.example.diccionario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
 
public class AgregarActivity extends Activity {
 
    EditText editTextPalabra;
    EditText editTextTraduccion;
    EditText editTextDescripcion;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar);

        editTextPalabra = (EditText) findViewById(R.id.et_palabra);
        editTextTraduccion = (EditText) findViewById(R.id.et_traduccion); 
        editTextDescripcion =(EditText) findViewById(R.id.et_descripcion);
    }
 
    public void onClickAdd (View btnAdd) {

        String palabra = editTextPalabra.getText().toString();
        String traduccion = editTextTraduccion.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
 
        if ( palabra.length() != 0 && traduccion.length() != 0 ) {
 
            Intent newIntent = getIntent();
            newIntent.putExtra("tag_pal", palabra);
            newIntent.putExtra("tag_tra", traduccion);
            newIntent.putExtra("tag_des", descripcion);
 
            this.setResult(RESULT_OK, newIntent);
            finish();
        }
    }
}