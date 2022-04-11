package com.example.tallermecanicoantonio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tallermecanicoantonio.Entidades.Servicio;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewServicioAdapter extends RecyclerView.Adapter<RecyclerViewServicioAdapter.servicioView> {
    private List<Servicio> servicioList = new ArrayList<>();
    private Context context;
    private ArrayList<Servicio> servicioArrayList;
    //private View.OnClickListener listener;

    public RecyclerViewServicioAdapter( Context context, ArrayList<Servicio> servicioList) {
        this.servicioList = servicioList;
        this.context = context;
        this.servicioArrayList = servicioList;
    }
    @NonNull
    @Override
    public servicioView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_servicio,viewGroup,false);
        //view.setOnClickListener(this);
        return new servicioView(view);
    }
    @Override
    public void onBindViewHolder(@NonNull servicioView servicioView, int i) {
        Servicio servicio = servicioList.get(i);

        servicioView.dni.setText(servicio.getNombre());
        servicioView.descripcion.setText("Descripcion: "+ servicio.getDescripcion());
        servicioView.auto.setText(servicio.getModelo() + " - " + servicio.getDominio());
        servicioView.fecha.setText("Fecha: "+servicio.getFecha());
        servicioView.importe.setText("Importe: " + servicio.getImporte());
    }



    @Override
    public int getItemCount() {
        return servicioList.size();
    }

    public void agregarServicio(Servicio servicio){
       servicioList.add(servicio);
        this.notifyDataSetChanged();

    }

    public class servicioView extends RecyclerView.ViewHolder{
        private TextView dni, descripcion, auto, fecha, importe;
        public servicioView(@NonNull View itemView) {
            super(itemView);
            dni = (TextView) itemView.findViewById(R.id.textView_dni_servicio);
            descripcion = (TextView) itemView.findViewById(R.id.textView_descripcion);
            auto = (TextView) itemView.findViewById(R.id.textView_modelo_dominio);
            fecha = (TextView) itemView.findViewById(R.id.textView_fecha);
            importe = (TextView)itemView.findViewById(R.id.textView_importe);
        }
    }
    public void filtrar(ArrayList<Servicio> filtrarCliente){
        this.servicioList = filtrarCliente;
        notifyDataSetChanged();

    }
}
