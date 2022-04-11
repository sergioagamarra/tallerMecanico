package com.example.tallermecanicoantonio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuServicioActivity extends AppCompatActivity {
    private Button btn_servicio_dia, btn_servicio_mes, btn_servicio_todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_servicio);
        btn_servicio_dia = (Button)findViewById(R.id.btn_listar_dia);
        btn_servicio_mes = (Button)findViewById(R.id.btn_listar_mes);
        btn_servicio_todo = (Button)findViewById(R.id.btn_listar_todo);
        btn_servicio_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListarServiciosActivity.class);
                intent.putExtra("codigoKey",3);
                startActivity(intent);
            }
        });
        btn_servicio_mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MenuServicioFechaActivity.class);
                intent.putExtra("codigoFecha",2);
                startActivity(intent);
            }
        });
        btn_servicio_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListarServiciosActivity.class);
                intent.putExtra("codigoKey",2);
                startActivity(intent);
            }
        });

    }
}