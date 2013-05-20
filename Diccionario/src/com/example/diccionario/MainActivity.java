package com.example.diccionario;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private CustomCursorAdapter customAdapter;
    private DiccionarioDB dicDB;
    private static final int ENTER_DATA_REQUEST_CODE = 1;
    private ListView listView;
     
    private EditText editTextentrada;
    private Button buscar;
    private Button otros;
    private String url = "http://www.wordreference.com/es/translation.asp?tranword=";
    private int idSelected;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        dicDB = new DiccionarioDB(this);
        setBotones();
        listView = (ListView) findViewById(R.id.list_data);
        listView.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	idSelected =Integer.parseInt("" + parent.getItemIdAtPosition(position));
            	Log.d("ID","id: " + idSelected);
            	TextView TV_traduccion =(TextView)findViewById(R.id.TV_traduccion);
            	TV_traduccion.setText(""+idSelected);
            	//parent.getId();
            	//parent.getSelectedView();
            	//parent.getSelectedItem();
            	//Log.d("LIST","id: " + parent.getItemIdAtPosition(position));
                Log.d("list", "click en item: " + position);
                //parent.getSelectedView().findViewById(Integer.parseInt(""+id));
            }
        }
        
        );
        //lanzamos un nuevo thread para la consulta a la BD  y el cursoradapter
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                customAdapter = new CustomCursorAdapter(MainActivity.this, dicDB.getAllData());
                listView.setAdapter(customAdapter);
            }
        });
    }
  
    public void onClickEnterData(View btnAdd) {
        startActivityForResult(new Intent(this, AgregarActivity.class), ENTER_DATA_REQUEST_CODE);
    }
    public void setBotones(){
        buscar=(Button)findViewById(R.id.buscar);
        buscar.setOnClickListener(onClickBuscar);
        
        otros=(Button)findViewById(R.id.otros);
        otros.setOnClickListener(onClickOtros);  
        
        editTextentrada=(EditText)findViewById(R.id.ET_entrada);
    }
    
	private View.OnClickListener onClickBuscar = new View.OnClickListener() {
		public void onClick(View v) {
		   	//usamos otro thread para las busquedas y el cursoradapter
	        new Handler().post(new Runnable() {
	            @Override
	            public void run() {
	            	String palabra = editTextentrada.getText().toString();
	            	Log.d("thread", "la entrada es: "+palabra);
	                customAdapter = new CustomCursorAdapter(MainActivity.this, dicDB.traducir(palabra));
	                listView.setAdapter(customAdapter);
	            }
	        });
		}
	};
    
	private View.OnClickListener onClickOtros = new View.OnClickListener() {
		public void onClick(View v) {
		   	//usamos otro thread para las busquedas y el cursoradapter
	        new Handler().post(new Runnable() {
	            @Override
	            public void run() {
	            	String palabra = editTextentrada.getText().toString();
	            	Log.d("thread", "la url es: "+ url+palabra);
	            	
	            	Intent i = new Intent(Intent.ACTION_VIEW);
	        		i.setData(Uri.parse(url+palabra));
	        		startActivity(i);
	            }
	        });

		}
	};
    
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTER_DATA_REQUEST_CODE && resultCode == RESULT_OK) {
        	dicDB.insertData(data.getExtras().getString("tag_pal"), data.getExtras().getString("tag_tra"), data.getExtras().getString("tag_des"));
            customAdapter.changeCursor(dicDB.getAllData());
        }
    }
    
	@Override
	public void onDestroy(){
		super.onDestroy();
		//dicDB.close();
	}
}
