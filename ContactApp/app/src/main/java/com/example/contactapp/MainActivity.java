package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    List<ContactModel> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        lvContact = (ListView) findViewById(R.id.lvContact);
        contacts = new ArrayList<>();

        // Register Context Menu <=> Hold item on listview
        registerForContextMenu(lvContact);

        // Add data to contacts
        for(int i = 0; i < 22; i++){
            contacts.add(new ContactModel());
        }

        // Add adapter
        ContactAdapter adapter = new ContactAdapter(contacts);
        lvContact.setAdapter(adapter);

        // Click Event ==> Activity info
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContactInfo.class);

                ContactModel contact = (ContactModel) adapterView.getAdapter().getItem(i);
                intent.putExtra("CONTACT_MODEL", contact);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Choose type: ");
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.call:
                Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mail:
                Toast.makeText(this, "Send Mail", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.message:
                Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}