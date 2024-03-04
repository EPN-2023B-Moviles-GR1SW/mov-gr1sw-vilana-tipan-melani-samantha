package com.example.proyectoiib

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

class RecyclerViewAdaptorLibro(
    private val contexto: InicioActivity,
    private val lista: ArrayList<Libro>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewAdaptorLibro.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var authActual = FirebaseAuth.getInstance().currentUser
        val tituloTextView: TextView
        val autorTextView: TextView
        val generoTextView: TextView
        //val urlLibroTextView: TextView
        //val urlLibroTextView: TextView
        //val urlLibroTituloTextView: TextView
        val botonEliminar: ImageView
        val botonEditar: ImageView
        //val imagenLibro: ImageView

        init {
            tituloTextView = view.findViewById(R.id.tv_rvnr_tituloLibro)
            autorTextView = view.findViewById(R.id.tv_rvnr_autorLibro)
            generoTextView = view.findViewById(R.id.tv_rvnr_generoLibro)
            //urlLibroTextView = view.findViewById(R.id.tv_urlLibro)

            //urlLibroTextView = view.findViewById(R.id.tv_rvnr_urlLibroV)
            //urlLibroTituloTextView = view.findViewById(R.id.tv_rvnr_urlLibro)
            //imagenLibro = view.findViewById(R.id.img_libro)


            botonEliminar = view.findViewById<ImageView?>(R.id.iv_rvnr_eliminar)
            botonEliminar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    abrirDialogoEliminar(position)
                    notifyDataSetChanged()
                }
            }
            botonEditar = view.findViewById(R.id.im_rvnr_editar)
            botonEditar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val libroSeleccionado = lista[position]
                    val intent = Intent(contexto, EditarActivity::class.java)
                    intent.putExtra("libroId", libroSeleccionado.id)
                    contexto.startActivity(intent)
                }

            }
        }

        fun abrirDialogoEliminar(position: Int) {
            val builder = AlertDialog.Builder(contexto)
            builder.setTitle("Desea Eliminar?")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                val libroSeleccionado = lista[position]
                val db = Firebase.firestore
                val libroRef =
                    db.collection("usuarios").document(authActual!!.uid).collection("libros")
                        .document(libroSeleccionado.id!!)
                lista.removeAt(position)
                notifyDataSetChanged()
                libroRef.delete()
                    .addOnSuccessListener { }
                    .addOnFailureListener { }

            }
            builder.setNegativeButton("Cancelar", null)
            val dialogo = builder.create()
            dialogo.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.activity_recycler_view_adaptor_libro,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val libroActual = this.lista[position]
        Log.d("RecyclerViewAdaptor", "Titulo: ${libroActual.titulo}")
        holder.tituloTextView.text = libroActual.titulo
        holder.autorTextView.text = libroActual.autor
        holder.generoTextView.text = libroActual.genero
        //Glide.with(holder.imagenLibro.context).load(libroActual.getImagen()).into(holder.imagenLibro)



        if (libroActual.urlLibro == null) {
            //holder.urlLibroTituloTextView.visibility = View.INVISIBLE
            //holder.urlLibroTextView.visibility = View.INVISIBLE
            Log.d("RecyclerViewAdaptor", "URL del libro: ${libroActual.getUrlLibroString()}")
        } else {
            //holder.urlLibroTextView.text = libroActual.getUrlLibroString()
        }

    }

    override fun getItemCount(): Int {
        return this.lista.size
    }


}
