package com.example.lab08_completablehttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText inputBook;
    private List<Libro> libros = new ArrayList<>();
    private RVAdapter rvAdapter=null;
    private RecyclerView recyclerView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputBook = (EditText)findViewById(R.id.inputbook);

    }

    public void searchBook(View view){

        String searchString = inputBook.getText().toString();
        if(searchString.isEmpty()|| searchString==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.atencion);
            builder.setMessage(R.string.busqueda);
            builder.setPositiveButton(R.string.aceptar,null);
            dialog= builder.create();
            dialog.show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage(R.string.espere);
            dialog= builder.create();
            dialog.show();
            libros.clear();
            if(recyclerView!=null){
                recyclerView.setAdapter(null);
            }
            new GetBook().execute(searchString);
        }

    }
    public class GetBook extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            Spinner spinner = (Spinner)findViewById(R.id.spType);
            String val = spinner.getSelectedItem().toString();
            String search="";
            if(val.equals("Libro") || val.equals("Book")){
                search="books";
            }else if(val.equals("Revista") || val.equals("Magazine")){
                search="magazines";
            }else{
                search="all";
            }
            return NetUtilities.getBookInfo(strings[0],search);
        }

        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                int i = 0;
                String titulo=null;
                String info=null;
                String foto=null;
                String autor=null;
                String lenguaje=null;
                String publicacion=null;
                while( i < itemsArray.length()){
                    JSONObject book = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    JSONObject images= volumeInfo.getJSONObject("imageLinks");
                    try {
                        titulo = volumeInfo.getString("title");
                        info = volumeInfo.getString("infoLink");
                        lenguaje=volumeInfo.getString("language");
                        publicacion=volumeInfo.getString("publishedDate");
                        foto = images.getString("thumbnail");
                        autor = volumeInfo.getString("authors");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    i++;
                    if(autor != null){
                        libros.add(new Libro(titulo,foto,autor,lenguaje,publicacion,info,getApplicationContext()));
                    }else{
                        libros.add(new Libro(titulo,foto,"No hay autores disponibles",lenguaje,publicacion,info,getApplicationContext()));
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            recyclerView= (RecyclerView) findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            rvAdapter= new RVAdapter(libros,getApplicationContext());
            recyclerView.setAdapter(rvAdapter);
            dialog.dismiss();
        }
    }
}