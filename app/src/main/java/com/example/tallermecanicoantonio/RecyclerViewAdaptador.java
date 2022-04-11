package com.example.tallermecanicoantonio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tallermecanicoantonio.Entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.clienteView> implements View.OnClickListener {
    private List<Cliente> clienteList = new ArrayList<>();
    private Context context;
    private ArrayList<Cliente> clienteArrayList;

    private View.OnClickListener listener;

    public RecyclerViewAdaptador( Context context, ArrayList<Cliente> clienteList) {
        this.clienteList = clienteList;
        this.context = context;
        this.clienteArrayList = clienteList;
    }

    @NonNull
    @Override
    public clienteView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cliente,viewGroup,false);
        view.setOnClickListener(this);
        return new clienteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull clienteView clienteView, int i) {
    Cliente cliente = clienteList.get(i);
    clienteView.dni.setText("Número de Documento: "+ String.valueOf(cliente.getDni()));
    clienteView.email.setText("Email: "+ cliente.getEmail());
    clienteView.nombre.setText(cliente.getNombre());
    clienteView.telefono.setText("Teléfono: "+cliente.getTelefono());
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public void agregarCliente(Cliente cliente){
        clienteList.add(cliente);
        this.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public class clienteView extends RecyclerView.ViewHolder{
        private TextView nombre, dni, email, telefono;
        public clienteView(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.textView_nombre_cliente);
            dni = (TextView) itemView.findViewById(R.id.textView_dni_cliente);
            email = (TextView) itemView.findViewById(R.id.textView_email);
            telefono = (TextView) itemView.findViewById(R.id.textView_telefono);
        }
    }
    public void filtrar(ArrayList<Cliente> filtrarCliente){
        this.clienteList = filtrarCliente;
        notifyDataSetChanged();

    }

}
