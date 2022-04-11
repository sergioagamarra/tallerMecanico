package com.example.tallermecanicoantonio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tallermecanicoantonio.Entidades.Servicio;

public class MainActivity extends AppCompatActivity {
    private CardView cv_cliente;
    private CardView cv_servicio;
    private CardView cv_lista_clientes;
    private CardView cv_lista_servicios;
    private CardView cv_admistrador;
    private CardView cv_cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv_cliente = (CardView)findViewById(R.id.cv_registrar_cliente);
        cv_servicio = (CardView)findViewById(R.id.cv_registrar_servicio);
        cv_lista_clientes = (CardView)findViewById(R.id.cv_listar_clientes);
        cv_lista_servicios = (CardView)findViewById(R.id.cv_listar_servicios);
        cv_admistrador = (CardView)findViewById(R.id.cv_admin);
        cv_cerrar = (CardView) findViewById(R.id.cv_cerrar);
        cv_cliente.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RegistrarClienteActivity.class);
                startActivity(intent);
            }
        });
        cv_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RegistrarServicioActivity.class);
                //intent.putExtra("DNI", 0);
                startActivity(intent);
            }
        });
        cv_lista_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListarClientesActivity.class);
                //intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });
        cv_lista_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MenuServicioActivity.class);
                //intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });
        cv_admistrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                final EditText usuario = (EditText) mView.findViewById(R.id.editText_usuario);
                final EditText contrasenia = (EditText) mView.findViewById(R.id.editText_contrasenia);
                Button btn_entrar = (Button) mView.findViewById(R.id.btn_iniciar_sesion);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                btn_entrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(consultarAdmin(usuario.getText().toString(), contrasenia.getText().toString())){
                            Intent intent = new Intent (v.getContext(), MenuAdminActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplication(),"Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }
        });
        cv_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_exit, null);
                Button btn_si = (Button) mView.findViewById(R.id.btn_salir_si);
                Button btn_no = (Button) mView.findViewById(R.id.btn_salir_no);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                btn_si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private Boolean consultarAdmin(String usuario, String contrasenia) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        Boolean res = false;
        Servicio servicio = null;
        Cursor fila = baseDeDatos.rawQuery("select * from admin " +
                "where usuario = " + "'" + usuario + "' AND contrasenia = " + "'" + contrasenia + "'" , null);
        if(fila.moveToFirst()){
            res = true;
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();
        return res;
    }

}
