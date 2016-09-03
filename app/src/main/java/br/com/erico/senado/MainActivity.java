package br.com.erico.senado;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGeneratePdf(View view) {

        Log.i("onGeneratePdf", String.valueOf(isExternalStorageWritable()));
        createFolder();

    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void createFolder() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
        String name = "f" + format.format(Calendar.getInstance().getTime());

        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name
        );

        if (!file.exists()) {
            file.mkdirs();
        }
        Log.i("createFolder", file.toString());

        try {
            createPdf(file.toString() + File.separator + "hello.pdf");
            String message = "File created";
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("createFolder", e.getMessage());
        }
    }

    public void createPdf(String filename)
            throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        for (Deputado deputado : new DeputadosEleitos().getBacanas()) {
            document.add(new Paragraph(deputado.getNome() + " (" + deputado.getPartido() + ")"));
        }
        // step 5
        document.close();
    }

 }
