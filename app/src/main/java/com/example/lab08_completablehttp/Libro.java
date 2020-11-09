package com.example.lab08_completablehttp;

import android.content.Context;

public class Libro {
    String titulo;
    String info;
    String foto;
    String autor;
    String lenguaje;
    String publicacion;
    private Context ctx;

    public Libro(String titulo, String foto, String autor, String lenguaje, String publicacion,String info,Context ctx) {
        this.ctx=ctx;
        if(foto!=null){
            foto= foto.replace("http","https");
        }
        if(autor.contains("[")){
            autor=autor.replace("[","");
            autor=autor.replace("]","");
            autor=autor.replace("\"","");
        }
        this.titulo = titulo;
        this.foto = foto;
        this.autor =ctx.getString(R.string.autor) +" "+autor;
        this.lenguaje =ctx.getString(R.string.idioma)+" "+ lenguaje;
        this.publicacion = ctx.getString(R.string.publicacion)+" "+publicacion;
        this.info=info;
    }
}
