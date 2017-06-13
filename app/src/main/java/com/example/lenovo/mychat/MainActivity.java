package com.example.lenovo.mychat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button sndBtn = findViewById(R.id.sendButton);
//        EditText messageText = findViewById(R.id.messageText);
//        ListView msgList = findViewById(R.id.messagesList);

        Button sndBtn = (Button) findViewById(R.id.sendButton);
        final EditText msgText = (EditText) findViewById(R.id.messageText);
        ListView msgList = (ListView) findViewById(R.id.messagesList);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");


        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatMessage chat = new chatMessage("harsh",msgText.getText().toString());
//                myRef.setValue(chat);
                myRef.push().setValue(chat);
                msgText.setText("");
//                msgText.setText("");
//                ref.push().setValue(chat);
            }
        });

        final List<chatMessage> messages = new LinkedList<>();
        final ArrayAdapter<chatMessage> adapter = new ArrayAdapter<chatMessage>(
//                this, android.R.class.layout.two_line_list_item, messages
        this, android.R.layout.two_line_list_item, messages
        ){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null){
                        convertView = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
                    }
                chatMessage chat = messages.get(position);
                ((TextView)convertView.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView)convertView.findViewById(android.R.id.text2)).setText(chat.getMessage());
                return convertView;
            }
        };
        msgList.setAdapter(adapter);
                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        chatMessage chat = dataSnapshot.getValue(chatMessage.class);
                        messages.add(chat);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        chatMessage chat = dataSnapshot.getValue(chatMessage.class);
                        messages.add(chat);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
