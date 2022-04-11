package com.example.tallermecanicoantonio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallermecanicoantonio.Entidades.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrarClienteActivity extends AppCompatActivity {
    private EditText editText_nombre, editText_dni, editText_email, editText_telefono;
    private Button btn_registrar_cliente;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        editText_nombre = (EditText) findViewById(R.id.editText_nombre);
        editText_dni = (EditText) findViewById(R.id.editText_dni);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_telefono = (EditText) findViewById(R.id.editText_telefono);
        inicializarFirebase();
        btn_registrar_cliente = (Button) findViewById(R.id.btn_regitrar_servicio);
        btn_registrar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCliente();
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void registrarCliente(){

        String dni = editText_dni.getText().toString();
        String nombre = editText_nombre.getText().toString();
        String email = editText_email.getText().toString();
        String telefono = editText_telefono.getText().toString();
        if(!dni.isEmpty()&&!nombre.isEmpty()&&!email.isEmpty()&&!telefono.isEmpty()){
            Cliente c = new Cliente();
            c.setDni(dni);
            c.setNombre(nombre);
            c.setEmail(email);
            c.setTelefono(telefono);
            databaseReference.child("Cliente").child(c.getDni()).setValue(c);
            limpiarCampos();
            Toast.makeText(this,"Cliente Registrado", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }
    }
    public void limpiarCampos() {
        editText_dni.setText("");
        editText_nombre.setText("");
        editText_email.setText("");
        editText_telefono.setText("");
        editText_nombre.requestFocus();
    }

}



   /* public void registrarCliente(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        String dni = editText_dni.getText().toString();
        String nombre = editText_nombre.getText().toString();
        String email = editText_email.getText().toString();
        String telefono = editText_telefono.getText().toString();
        if(!dni.isEmpty()&&!nombre.isEmpty()&&!email.isEmpty()&&!telefono.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("dni", dni);
            registro.put("nombre", nombre);
            registro.put("email", email);
            registro.put("telefono", telefono);
            baseDeDatos.insert("cliente", null, registro);
            baseDeDatos.close();
            editText_dni.setText("");
            editText_nombre.setText("");
            editText_email.setText("");
            editText_telefono.setText("");
            editText_nombre.requestFocus();
            Toast.makeText(this,"Cliente Registrado", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }
    }*/

