package com.example.tallermecanicoantonio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.tallermecanicoantonio.Entidades.Servicio;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrarServicioActivity extends AppCompatActivity {
    private EditText editText_fecha, editText_dni_cliente, editText_descripcion, editText_modelo, editText_importe, editText_dominio;
    private Button btn_buscar_cliente, btn_registrar_servicio;
    private int dia, mes, año, sYearIni, sMonthIni, sDayIni;
    private int dni;
    private String nombre;
    static final int DATE_ID = 0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    long maxid = 0;
    Calendar C = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_servicio);
        editText_fecha = (EditText)findViewById(R.id.editText_fecha);
        editText_dni_cliente = (EditText)findViewById(R.id.editText_dni_cliente);
        editText_descripcion = (EditText) findViewById(R.id.editText_descripcion);
        editText_modelo = (EditText) findViewById(R.id.editText_auto);
        editText_dominio = (EditText) findViewById(R.id.editText_dominio);
        editText_importe = (EditText) findViewById(R.id.editText_importe);
        inicializarFirebase();
        btn_buscar_cliente = (Button)findViewById(R.id.btn_buscar_cliente);
        databaseReference.child("Servicio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_registrar_servicio = (Button) findViewById(R.id.btn_regitrar_servicio);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sMonthIni = C.get(Calendar.MONTH);
        sYearIni = C.get(Calendar.YEAR);


        editText_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });
        btn_buscar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = null;
                Intent intent = new Intent(v.getContext(), ListarClientesActivity.class);
                /*intent.putExtra("codigoKey", 1);
                startActivity(intent);*/
                startActivityForResult(intent, 1);
            }
        });

        btn_registrar_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registrarServicio();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // First we need to check if the requestCode matches the one we used.
        if(requestCode == 1) {

            // The resultCode is set by the DetailActivity
            // By convention RESULT_OK means that whatever
            // DetailActivity did was executed successfully
            if(resultCode == Activity.RESULT_OK) {
                // Get the result from the returned Intent
                final String result = data.getStringExtra(ListarClientesActivity.EXTRA_DATA);
                editText_dni_cliente.setText(result);
                final String resultado = data.getStringExtra(ListarClientesActivity.EXTRA_DATAS);
                nombre = resultado;
                editText_descripcion.requestFocus();


                // Use the data - in this case, display it in a Toast.
                //Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
            } else {
                // setResult wasn't successfully executed by DetailActivity
                // Due to some error or flow of control. No data to retrieve.
            }
        }
    }

   private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    año = year;
                    mes = monthOfYear + 1;
                    dia = dayOfMonth;
                    //colocar_fecha();
                    editText_fecha.setText(dia + "/" + mes + "/" + año);//( año+"-"+sMes+"-"+sDia);
                }
            };
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, R.style.datepicker, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

   /* public void registrarServicio(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        String dni = editText_dni_cliente.getText().toString();
        //String id = "0";
        String descripcion = editText_descripcion.getText().toString();
        String modelo = editText_modelo.getText().toString();
        String importe = editText_importe.getText().toString();
        String dominio = editText_dominio.getText().toString();
        String fecha = editText_fecha.getText().toString();
        if(!dni.isEmpty()&&!descripcion.isEmpty()&&!importe.isEmpty()&&!dominio.isEmpty()&&!fecha.isEmpty()){
            ContentValues registro = new ContentValues();
            //registro.put("id", id);
            registro.put("dni_cliente", dni);
            registro.put("modelo", modelo);
            registro.put("dominio", dominio);
            registro.put("importe", importe);
            registro.put("descripcion", descripcion);
            registro.put("fecha", fecha);
            baseDeDatos.insert("servicio", null, registro);
            baseDeDatos.close();
            limpiarCampos();
            editText_dni_cliente.requestFocus();
            Toast.makeText(this,"Servicio Registrado", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }
    }*/
    public void registrarServicio() throws ParseException {
        String dni = editText_dni_cliente.getText().toString();
        String descripcion = editText_descripcion.getText().toString();
        String modelo = editText_modelo.getText().toString();
        String importe = editText_importe.getText().toString();
        String dominio = editText_dominio.getText().toString();
        String fecha = editText_fecha.getText().toString();
        Long timestamp = toMilli(fecha);
        //String nom = consultarCliente(dni);
        if(!dni.isEmpty()&&!descripcion.isEmpty()&&!importe.isEmpty()&&!dominio.isEmpty()&&!fecha.isEmpty()){
            final Servicio s = new Servicio();
            s.setDni_cliente(dni);
            s.setDescripcion(descripcion);
            s.setModelo(modelo);
            s.setDominio(dominio);
            s.setFecha(String.valueOf(timestamp));
            s.setImporte(importe);
            s.setNombre(nombre);
            //Toast.makeText(this, s.getFecha() , Toast.LENGTH_LONG).show();
            limpiarCampos();
            databaseReference.child("Servicio").child(String.valueOf(maxid + 1)).setValue(s);
            editText_dni_cliente.requestFocus();
            Toast.makeText(this,"Servicio Registrado", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void limpiarCampos(){
        editText_dni_cliente.setText("");
        editText_descripcion.setText("");
        editText_modelo.setText("");
        editText_dominio.setText("");
        editText_importe.setText("");
        editText_fecha.setText("");
    }
    public Long toMilli(String dateIn) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) formatter.parse(dateIn);
        long output = date.getTime() / 1000L;
        String str = Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;
        return timestamp;
    }
    /*public String consultarCliente(String dni_cliente) {
        databaseReference = firebaseDatabase.getInstance().getReference("Cliente");
        Query q = databaseReference.orderByChild("dni").equalTo(dni_cliente);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Cliente c = objSnapshot.getValue(Cliente.class);
                    nombre = c.getNombre();
                    //Toast.makeText(RegistrarServicioActivity.this, nombre , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(this,nombre + " " + dni_cliente, Toast.LENGTH_LONG).show();
        return nombre;
    }*/
}