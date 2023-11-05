package com.example.clientsockettestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private Button sendButton;
    private TextView connectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);
        connectionStatus = findViewById(R.id.connectionStatus);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the Send button is clicked, execute the client code on a background thread
                startClient();
            }
        });
    }

    private void startClient() {
        String IP = "10.0.2.2"; //edit IP to server's IP
        int port = 5000; //edit to actual port being used

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(IP, port);
                    // This code runs on a background thread

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectionStatus.setText("Connected to server"); // Set the connection status on the UI thread
                        }
                    });

                    String input = userInput.getText().toString(); // Get user input from EditText

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(input);
                    writer.newLine();
                    writer.flush();

                    if (input.equalsIgnoreCase("over")) {
                        // Close the connection if the user input is "over"
                        socket.close();
                    }
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connectionStatus.setText("Connection failed"); // Set the connection status on the UI thread in case of failure
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
