package com.example.tallermecanicoantonio;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Documented;
import java.util.ArrayList;

public class TemplatePDF {
    //private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font ftitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, new BaseColor(0, 43, 61));
    private Font fsubtitulo = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font ftexto = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fencabezado = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
    private Font fresaltado = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, new BaseColor(0, 43, 61));

    /*public TemplatePDF(Context context) {
        this.context = context;
    }*/
    public TemplatePDF() {

    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public void abrirDocumento(){
        crearArchivo();
        try {
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }
        catch (Exception e){
            Log.e("abrirDocumento", e.toString());
        }
    }
    public void cerrarDocumento(){
        document.close();
    }
    public void agregarMetaDatos(String titulo, String tema, String autor){
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }
    public void agregarTitulo(String titulo, String subtitulo, String fecha){
        try {
            paragraph = new Paragraph();
            agregarHijoP(new Paragraph(titulo, ftitulo));
            agregarHijoP(new Paragraph(subtitulo, fsubtitulo));
            agregarHijoP(new Paragraph("Generado: " + fecha, fresaltado));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }
        catch (Exception e){
            Log.e("agregarTitulo", e.toString());
        }
    }
    public void agregarParrafo(String texto){
        try{
            paragraph = new Paragraph(texto, ftexto);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }
        catch (Exception e){
            Log.e("agregarParrafo", e.toString());
        }
    }
    public void crearTabla(String[]encabaezado, ArrayList<String[]> clientes){
        try{
            paragraph = new Paragraph();
            paragraph.setFont(ftexto);
            PdfPTable pdfPTable = new PdfPTable(encabaezado.length);
            float[] columnWidths = new float[]{10f, 25f, 25f, 10f};
            pdfPTable.setWidths(columnWidths);
            //pdfPTable.setSpacingBefore(20);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int columna = 0;
            while(columna < encabaezado.length){
                pdfPCell = new PdfPCell(new Phrase(encabaezado[columna++], fencabezado));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(new BaseColor(0, 43, 61));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(20);
                pdfPTable.addCell(pdfPCell);
            }
            for(int indexR = 0; indexR < clientes.size(); indexR++){
                String[] fila = clientes.get(indexR);
                for(int indexC = 0; indexC < encabaezado.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(fila[indexC], ftexto));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }
        catch (Exception e){
            Log.e("crearTabla", e.toString());
        }
    }
    private void agregarHijoP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }
    private void crearArchivo(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if(!folder.exists()){
            folder.mkdir();
        }
        pdfFile = new File(folder, "ListadoClientes.pdf");
    }
    public void appViewPDF(Activity activity){
        if(pdfFile.exists()){
            Uri uri = Uri.fromFile(pdfFile);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(uri, "application/pdf");
            try{
                activity.startActivity(i);
            }
            catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(),"No cuentas con una app para abrir PDFs", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(activity.getApplicationContext(),"No se encotró el archivo", Toast.LENGTH_LONG).show();
        }
    }
}
