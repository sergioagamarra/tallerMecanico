package com.example.tallermecanicoantonio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tallermecanicoantonio.Entidades.Servicio;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListarServiciosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Servicio> servicioArrayList;
    private ArrayList<Servicio> filtrarLista = null;
    private RecyclerViewServicioAdapter servicioAdapter;
    private int cod;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private TextView textView_importe_total;
    private EditText editText_buscar_servicio;
    private String fechaInicio, fechaFin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_servicios);
        textView_importe_total = (TextView) findViewById(R.id.textView_importe_total);
        editText_buscar_servicio = (EditText)findViewById(R.id.editText_buscar_servicio);
        recyclerView = (RecyclerView)findViewById(R.id.rv_servicios);
        inicializarFirebase();
        servicioArrayList = new ArrayList<>();
        servicioAdapter = new RecyclerViewServicioAdapter(this, servicioArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(servicioAdapter);
        Date date = new Date();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            cod = bundle.getInt("codigoKey");
          switch (cod){
              case 1:
                  fechaInicio = bundle.getString("fechaInicio");
                  fechaFin = bundle.getString("fechaFin");
                  try {
                      mostrarServicioDelDia(fechaInicio, fechaFin);
                      calcularImporteDia(fechaInicio, fechaFin);
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }

                  break;
              case 2: mostrarTodosLosServicios();
              textView_importe_total.setEnabled(false);
              textView_importe_total.setVisibility(View.INVISIBLE);
              break;
              case 3:
                  SimpleDateFormat fechaC = new SimpleDateFormat("dd/MM/yyyy");
                  fechaInicio = fechaC.format(date);
                  fechaFin = fechaInicio;
                  //Toast.makeText(this,fechaInicio, Toast.LENGTH_LONG).show();
                  try {
                      mostrarServicioDelDia(fechaInicio, fechaFin);
                      calcularImporteDia(fechaInicio, fechaFin);
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
          }
        }
        editText_buscar_servicio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtar(s.toString());
            }
        });

    }
  /*  private void calcularImporteDia(String fechaInicio, String fechaFin) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        Servicio servicio = null;
        Cursor fila;
        if(fechaFin.equals("2020-01-01")){
            fila = baseDeDatos.rawQuery("select sum(importe) from servicio where fecha = "+"'"+fechaInicio+"'", null);
        }
        else{
            fila = baseDeDatos.rawQuery("select sum(importe) from servicio where date(fecha)  between "+"'"+fechaInicio+"' and '" + fechaFin+"'", null);
        }
        if(fila.moveToFirst()){
            do{
                textView_importe_total.setText("Ingreso Total: $" + fila.getString(0));
            }while(fila.moveToNext());
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();
    }*/
       private void calcularImporteDia(String fechaInicio, String fechaFin) throws ParseException {
           databaseReference.child("Servicio").orderByChild("fecha").startAt(String.valueOf(toMilli(fechaInicio))).endAt(String.valueOf(toMilli(fechaFin))).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   int total = 0;
                   for(DataSnapshot objSnapshot : snapshot.getChildren()){
                       Servicio s = objSnapshot.getValue(Servicio.class);
                       total = total + Integer.parseInt(s.getImporte());
                       textView_importe_total.setText("Ingreso Total: $" + total);
                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
    }

    public void mostrarServicioDelDia(String fechaInicio, String fechaFin) throws ParseException {
        if(fechaInicio == fechaFin){
            databaseReference.child("Servicio").orderByChild("fecha").equalTo(String.valueOf(toMilli(fechaInicio))).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    servicioArrayList.clear();
                    for(DataSnapshot objSnapshot : snapshot.getChildren()){
                        Servicio s = objSnapshot.getValue(Servicio.class);
                        String time=convertTime(Long.valueOf(s.getFecha()));
                        s.setFecha(time);
                        servicioAdapter.agregarServicio(s);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            databaseReference.child("Servicio").orderByChild("fecha").startAt(String.valueOf(toMilli(fechaInicio))).endAt(String.valueOf(toMilli(fechaFin))).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    servicioArrayList.clear();
                    for(DataSnapshot objSnapshot : snapshot.getChildren()){
                        Servicio s = objSnapshot.getValue(Servicio.class);
                        String time = convertTime(Long.valueOf(s.getFecha()));
                        s.setFecha(time);
                        servicioAdapter.agregarServicio(s);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
    public Long toMilli(String dateIn) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) formatter.parse(dateIn);
        long output = date.getTime() / 1000L;
        String str = Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;
        return timestamp;
    }
    /*private void mostrarServicioDelDia(String fechaInicio, String fechaFin) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        Servicio servicio = null;
        Cursor fila;
        if(fechaFin.equals("2020-01-01")){
            fila = baseDeDatos.rawQuery("select * from servicio where fecha = "+"'"+fechaInicio+"'", null);
        }
        else
        {
            fila = baseDeDatos.rawQuery("select * from servicio where date(fecha) between "+"'"+fechaInicio+"' and '" + fechaFin+"'", null);
        }
        //Cursor fila = baseDeDatos.rawQuery("select * from servicio where fecha = '29-6-2020' ", null);
        if(fila.moveToFirst()){
            do{
                servicio = new Servicio();
                servicio.setId(fila.getInt(0));
                servicio.setDni_cliente(fila.getInt(1));
                servicio.setDescripcion(fila.getString(2));
                servicio.setModelo(fila.getString(3));
                servicio.setDominio(fila.getString(4));
                servicio.setImporte(fila.getString(5));
                servicio.setFecha(fila.getString(6));
                servicio.setNombre(consultarCliente(fila.getInt(1)));

                servicioAdapter.agregarServicio(servicio);
            }while(fila.moveToNext());
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();
    }*/

    /*public void mostrarTodosLosServicios(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        Servicio servicio = null;
        Cursor fila = baseDeDatos.rawQuery("select * from servicio", null);
        if(fila.moveToFirst()){
            do{
                servicio = new Servicio();
                servicio.setId(fila.getInt(0));
                servicio.setDni_cliente(fila.getInt(1));
                servicio.setDescripcion(fila.getString(2));
                servicio.setModelo(fila.getString(3));
                servicio.setDominio(fila.getString(4));
                servicio.setImporte(fila.getString(5));
                servicio.setFecha(fila.getString(6));
                servicio.setNombre(consultarCliente(fila.getInt(1)));

                servicioAdapter.agregarServicio(servicio);
            }while(fila.moveToNext());
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();
    }*/
    public void mostrarTodosLosServicios(){
        /*Servicio servicio = null;
        Cursor fila = baseDeDatos.rawQuery("select * from servicio", null);
        if(fila.moveToFirst()){
            do{
                servicio = new Servicio();
                servicio.setId(fila.getInt(0));
                servicio.setDni_cliente(fila.getInt(1));
                servicio.setDescripcion(fila.getString(2));
                servicio.setModelo(fila.getString(3));
                servicio.setDominio(fila.getString(4));
                servicio.setImporte(fila.getString(5));
                servicio.setFecha(fila.getString(6));
                servicio.setNombre(consultarCliente(fila.getInt(1)));

                servicioAdapter.agregarServicio(servicio);
            }while(fila.moveToNext());
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();*/
        databaseReference.child("Servicio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicioArrayList.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Servicio s = objSnapshot.getValue(Servicio.class);
                    String time=convertTime(Long.valueOf(s.getFecha()));
                    s.setFecha(time);
                    servicioAdapter.agregarServicio(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }
  /*  private String consultarCliente(int dni_cliente) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        String nombre;
        Cursor fila = baseDeDatos.rawQuery("select nombre from cliente where dni =" + dni_cliente, null);
        fila.moveToFirst();
        nombre = fila.getString(0);
        return nombre;
    }*/
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void filtar(String texto) {
        filtrarLista = new ArrayList<>();
        ;
        for (Servicio servicio : servicioArrayList) {
            if (servicio.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(servicio);
            }
            servicioAdapter.filtrar(filtrarLista);
        }

    }
}