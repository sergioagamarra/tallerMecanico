package com.example.tallermecanicoantonio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class MenuServicioFechaActivity extends AppCompatActivity {
    private Button btn_listar_fecha;
    private EditText editText_fecha_inicio, editText_fecha_fin;
    private int dia, mes, año, sYearIni, sMonthIni, sDayIni, cod;
    static final int DATE_FI = 0;
    static final int DATE_FF= 1;
    private String sMes, sDia;
    Calendar C = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_servicio_fecha);
        btn_listar_fecha = (Button)findViewById(R.id.btn_listar_fecha);
        editText_fecha_inicio = (EditText)findViewById(R.id.editText_fecha_inicio);
        editText_fecha_fin = (EditText)findViewById(R.id.editText_fecha_fin);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sMonthIni = C.get(Calendar.MONTH);
        sYearIni = C.get(Calendar.YEAR);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            cod = bundle.getInt("codigoFecha");
            if(cod == 1) {
                try {
                    if (bundle != null) {
                        editText_fecha_fin.setEnabled(false);
                        editText_fecha_fin.setVisibility(View.INVISIBLE);
                        editText_fecha_fin.setText("2020-01-01");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        btn_listar_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ListarServiciosActivity.class);
                intent.putExtra("codigoKey",1);
                intent.putExtra("fechaInicio", editText_fecha_inicio.getText().toString());
                intent.putExtra("fechaFin", editText_fecha_fin.getText().toString());
                startActivity(intent);
            }
        });
        editText_fecha_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_FI);
            }
        });
        editText_fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_FF);
            }
        });
    }
    /* private void colocar_fecha() {
        switch (dia){
            case 1:
                sDia = "01";
                break;
            case 2:
                sDia = "02";
                break;
            case 3:
                sDia = "03";
                break;
            case 4:
                sDia = "04";
                break;
            case 5:
                sDia = "05";
                break;
            case 6:
                sDia = "06";
                break;
            case 7:
                sDia = "07";
                break;
            case 8:
                sDia = "08";
                break;
            case 9:
                sDia = "09";
                break;
            default:
                sDia = String.valueOf(dia);
        }
        switch (mes){
            case 0:
                sMes = "01";
                break;
            case 1:
                sMes = "02";
                break;
            case 2:
                sMes = "03";
                break;
            case 3:
                sMes = "04";
                break;
            case 4:
                sMes = "05";
                break;
            case 5:
                sMes = "06";
                break;
            case 6:
                sMes = "07";
                break;
            case 7:
                sMes = "08";
                break;
            case 8:
                sMes = "09";
                break;
            case 9:
                sMes = "10";
                break;
            case 10:
                sMes = "11";
                break;
            case 11:
                sMes = "12";
                break;

        }

    }*/
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    año = year;
                    mes = monthOfYear + 1;
                    dia = dayOfMonth;
                    editText_fecha_inicio.setText( dia +"/" +mes +"/"+ año);
                }
            };

   /* private void colocar_fecha_inicio() {
        colocar_fecha();
        editText_fecha_inicio.setText( año+"-"+sMes+"-"+sDia);

    }
    private void colocar_fecha_fin() {
        colocar_fecha();
        editText_fecha_fin.setText( año+"-"+sMes+"-"+sDia);
    }*/

    private DatePickerDialog.OnDateSetListener fDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    año = year;
                    mes = monthOfYear + 1;
                    dia = dayOfMonth;
                    editText_fecha_fin.setText( dia +"/" +mes +"/"+ año);
                }
            };
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_FI:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni );
            case DATE_FF:
                return new DatePickerDialog(this, fDateSetListener, sYearIni, sMonthIni, sDayIni );
        }
        return null;
    }
}