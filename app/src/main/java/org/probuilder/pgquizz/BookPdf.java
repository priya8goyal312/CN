package org.probuilder.pgquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class BookPdf extends AppCompatActivity {

    private PDFView pdfView;
    private String titleOfBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pdf);

        titleOfBook = getIntent().getStringExtra("title");
        pdfView = findViewById(R.id.pdfView);

        if (titleOfBook.equals("Science")) {
            pdfView.fromAsset("science.pdf").load();

        }else if(titleOfBook.equals("Maths")){
            pdfView.fromAsset("maths.pdf").load();

        }if(titleOfBook.equals("NDA")){
            pdfView.fromAsset("army.pdf").load();

        }if(titleOfBook.equals("Commerce")){
            pdfView.fromAsset("commerce.pdf").load();

        }if(titleOfBook.equals("Arts")){
            pdfView.fromAsset("science.pdf").load();

        }if(titleOfBook.equals("English")){
            pdfView.fromAsset("science.pdf").load();

        }



    }
}