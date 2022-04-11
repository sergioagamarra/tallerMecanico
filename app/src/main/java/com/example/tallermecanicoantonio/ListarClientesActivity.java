package com.example.tallermecanicoantonio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.sax.Element;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tallermecanicoantonio.Entidades.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListarClientesActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_DATAS = "EXTRA_DATAs";
    private RecyclerView recyclerView;
    private ArrayList<Cliente> clienteArrayList;
    private ArrayList<Cliente> filtrarLista = null;
    private RecyclerViewAdaptador clienteAdapter;
    private EditText editText_buscar_cliente;
    private int cod;
    private Button btn_exportar_pdf;
    private TextView textView_nombre;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String[] encabezado = {"DNI", "NOMBRE", "E-MAIL", "TELÉFONO"};
    private String texto = "Listado de todos los clientes del taller Antonio Gamarra";
    private Bitmap bitmap, escala;
    private TemplatePDF templatePDF;
    //private String dni;

    //private List<Cliente> listCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_clientes);
        recyclerView = (RecyclerView)findViewById(R.id.rv_clientes);
        editText_buscar_cliente = (EditText)findViewById(R.id.editText_buscar_cliente);
        btn_exportar_pdf = (Button)findViewById(R.id.btn_exportar_pdf_cliente);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo1);
        escala = Bitmap.createScaledBitmap(bitmap, 129, 50, false);
        inicializarFirebase();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            cod = bundle.getInt("codigoKey");
            if(cod == 1) {
                try {
                    if (bundle != null) {
                        btn_exportar_pdf.setEnabled(false);
                        btn_exportar_pdf.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        clienteArrayList = new ArrayList<>();
        clienteAdapter = new RecyclerViewAdaptador(this, clienteArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        clienteAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"DNI: "+ clienteArrayList.get(recyclerView.getChildAdapterPosition(v)).getDni(), Toast.LENGTH_LONG).show();
                /*int dni = (clienteArrayList.get(recyclerView.getChildAdapterPosition(v)).getDni());
                Intent intent = new Intent (v.getContext(), RegistrarServicioActivity.class);
                intent.putExtra("DNI", dni);
                startActivity(intent);*/
                String dni;
                String nombre;
                if(filtrarLista == null){
                    dni = String.valueOf(clienteArrayList.get(recyclerView.getChildAdapterPosition(v)).getDni());
                    nombre = clienteArrayList.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                    //Toast.makeText(getApplicationContext(),"ENTRO", Toast.LENGTH_LONG).show();
                }
                else{
                    dni = String.valueOf(filtrarLista.get(recyclerView.getChildAdapterPosition(v)).getDni());
                    nombre = filtrarLista.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                    //Toast.makeText(getApplicationContext(),"NO ENTRO", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DATA, dni);
                intent.putExtra(EXTRA_DATAS, nombre);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(clienteAdapter);
        mostrarClientes();

        editText_buscar_cliente.addTextChangedListener(new TextWatcher() {
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



   /*  btn_exportar_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                //canvas.drawText("Taller Antonio Gamarra", 40, 50 , paint);
                canvas.drawBitmap(escala, 40, 50, paint);

                pdfDocument.finishPage(page);

                File file = new File(Environment.getExternalStorageDirectory(), "/Taller.pdf");
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();

            }
        });*/
        btn_exportar_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                templatePDF = new TemplatePDF();
                templatePDF.abrirDocumento();
                templatePDF.agregarMetaDatos("Clientes", "Listado de Clientes", "Antonio Gamarra");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                String currentDateandTime = sdf.format(new Date());
                templatePDF.agregarTitulo("Taller Antonio Gamarra", "Listado de Clientes", currentDateandTime);
                //templatePDF.agregarParrafo(texto);
                templatePDF.crearTabla(encabezado, getClientes());
                templatePDF.cerrarDocumento();
                Toast.makeText( getApplicationContext(),"Se generó el PDF", Toast.LENGTH_LONG).show();
                if(templatePDF.getPdfFile().exists()){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.fromFile(templatePDF.getPdfFile()), "application/pdf");
                    try{
                        startActivity(i);
                    }
                    catch (ActivityNotFoundException e){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                        Toast.makeText(getApplicationContext(),"No cuentas con una app para abrir PDFs", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No se encotró el archivo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private ArrayList<String[]> getClientes(){
        ArrayList<String[]> filas = new ArrayList<>();
        for (Cliente cliente : clienteArrayList){
            filas.add(new String[]{cliente.getDni(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono()});
        }
        //filas.add(new String[]{"1", "Sergio Gamarra", "sergioagamarra@gmail.com", "3884596172"});
        //filas.add(new String[]{"2", "Natalia Estrada", "nati.tachito@gmail.com", "3884608497"});
        return filas;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
   /* public void mostrarClientes(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        Cliente cliente = null;
        Cursor fila = baseDeDatos.rawQuery("select * from cliente", null);
        if(fila.moveToFirst()){
            do{
               cliente = new Cliente();
               cliente.setDni(fila.getString(0));
               cliente.setNombre(fila.getString(1));
               cliente.setEmail(fila.getString(2));
               cliente.setTelefono(fila.getString(3));
               clienteAdapter.agregarCliente(cliente);
            }while(fila.moveToNext());
        }
                //MOSTRAR LOS DATOS
        baseDeDatos.close();
    }*/
    public void mostrarClientes(){
      /*  Cliente cliente = null;
        Cursor fila = baseDeDatos.rawQuery("select * from cliente", null);
        if(fila.moveToFirst()){
            do{
                cliente = new Cliente();
                cliente.setDni(fila.getString(0));
                cliente.setNombre(fila.getString(1));
                cliente.setEmail(fila.getString(2));
                cliente.setTelefono(fila.getString(3));
                clienteAdapter.agregarCliente(cliente);
            }while(fila.moveToNext());
        }
        //MOSTRAR LOS DATOS
        baseDeDatos.close();*/
        databaseReference.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clienteArrayList.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Cliente c = objSnapshot.getValue(Cliente.class);
                    clienteAdapter.agregarCliente(c);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void filtar(String texto){
        filtrarLista = new ArrayList<>();;
        for (Cliente cliente : clienteArrayList){
            if(cliente.getNombre().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(cliente);
            }
            clienteAdapter.filtrar(filtrarLista);
        }
    }
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}