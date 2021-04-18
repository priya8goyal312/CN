package org.probuilder.pgquizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.Toast;

public class SetsActivity extends AppCompatActivity {


    private GridView gridView;

    public String subName="";

    public String getSubName() {

        String subName1=getIntent().getStringExtra("title");
        return subName1;
    }

    static public int setName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        Toolbar toolbar=findViewById(R.id.setsToolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        gridView=findViewById(R.id.gridView);

        //gettind data
        subName=getIntent().getStringExtra("title");
        Toast.makeText(this, subName, Toast.LENGTH_SHORT).show();
        setName=getIntent().getIntExtra("sets",0);

        GridAdapter gridAdapter=new GridAdapter(getIntent().getIntExtra("sets",0),getIntent().getStringExtra("title"));
        gridView.setAdapter(gridAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}