package com.example.tallermecanicoantonio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuAdminActivity extends AppCompatActivity {
    private CardView cv_clientes, cv_servicios, cv_admin, cv_cerrar_adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        cv_clientes = (CardView)findViewById(R.id.cv_gestion_cliente);
        cv_servicios = (CardView)findViewById(R.id.cv_gestion_servicio);
        cv_admin = (CardView)findViewById(R.id.cv_gestion_admin);
        cv_cerrar_adm = (CardView)findViewById(R.id.cv_admin_cerrar);
        cv_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GestionClientesActivity.class);
                //intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });
        cv_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GestionServiciosActivity.class);
                //intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });
        cv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GestionAdminActivity.class);
                //intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });
        cv_cerrar_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}