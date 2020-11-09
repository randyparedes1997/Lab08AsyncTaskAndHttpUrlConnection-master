package com.example.lab08_completablehttp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LibroViewHolder> {

    List<Libro> libros;
    Context context;

    public RVAdapter(List<Libro> personas,Context context){
        this.libros = personas;
        this.context= context;
    }

    @NonNull
    @Override
    public RVAdapter.LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books,parent,false);
        LibroViewHolder libroViewHolder = new LibroViewHolder(view);
        return libroViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.LibroViewHolder holder, int position) {
        holder.tituloLibro.setText(libros.get(position).titulo);
        holder.autores.setText(libros.get(position).autor);
        if(libros.get(position).foto==null || libros.get(position).foto==""){
            holder.fotoLibro.setImageResource(R.drawable.no_im);
        }else{
            Picasso.with(context).load(libros.get(position).foto).into(holder.fotoLibro);
        }
        holder.publicacion.setText(libros.get(position).publicacion);
        holder.idioma.setText(libros.get(position).lenguaje);
        holder.btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(libros.get(position).info);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tituloLibro;
        TextView autores;
        ImageView fotoLibro;
        TextView publicacion;
        TextView idioma;
        Button btnMas;

        public LibroViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cb);
            tituloLibro = (TextView) itemView.findViewById(R.id.bookTitle);
            autores = (TextView) itemView.findViewById(R.id.txtAutor);
            fotoLibro = (ImageView) itemView.findViewById(R.id.ivBook);
            publicacion = (TextView) itemView.findViewById(R.id.txtPublicacion);
            idioma = (TextView) itemView.findViewById(R.id.txtLenguaje);
            btnMas = (Button) itemView.findViewById(R.id.btnDetalle);
        }
    }
}
