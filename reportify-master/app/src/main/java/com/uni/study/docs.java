package com.uni.study;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uni.study.model.pdfData;

import java.util.ArrayList;
import java.util.List;

public class docs extends AppCompatActivity {

    FloatingActionButton addBtn;
    ImageButton returnBtn;
    ListView listView;
    DatabaseReference dbReference;
    List<pdfData> pdfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);

        listView = findViewById(R.id.docsList);
        pdfFiles= new ArrayList<>();
        dbReference= FirebaseDatabase.getInstance().getReference("uploadDoc");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()) {
                    pdfData pdf = ds.getValue(pdfData.class);
                    pdfFiles.add(pdf);
                }

                String[] uploadNames = new String[pdfFiles.size()];
                for (int i=0; i < uploadNames.length; i++) {
                    uploadNames[i] = pdfFiles.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploadNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);

                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };

                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

       // startActivity(new Intent(getApplicationContext(),docs.class));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pdfData pdfF = pdfFiles.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfF.getUrl()));
                startActivity(intent);
            }
        });















        ////////////////////////////



        addBtn = findViewById(R.id.addFile);
        returnBtn = findViewById(R.id.dashReturn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(docs.this, addDocs.class);
                startActivity(intent);
                finish();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(docs.this, dash.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
