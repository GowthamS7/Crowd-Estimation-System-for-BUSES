package com.example.relatimedatabase;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.relatimedatabase.databinding.ActivityReadDataBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ReadData extends AppCompatActivity {

    ActivityReadDataBinding binding;
    DatabaseReference reference;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadData.this,ListActivity.class);
                startActivity(intent);
            }
        });
        binding = ActivityReadDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.readdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etusername.getText().toString();
                if (!username.isEmpty()){

                    readData(username);
                }else{

                    Toast.makeText(ReadData.this,"PLease Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void readData(String username) {

        reference = FirebaseDatabase.getInstance().getReference("Buses");
        reference.child(username).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){

                if (task.getResult().exists()){
                    Toast.makeText(ReadData.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                    DataSnapshot dataSnapshot = task.getResult();
                    String countIN = String.valueOf(dataSnapshot.child("countIN").getValue());
                    String countOUT = String.valueOf(dataSnapshot.child("countOUT").getValue());
                    binding.tvcountIN.setText(countIN);
                    binding.tvcountOUT.setText(countOUT);


                            int value1 = dataSnapshot.child("countIN").getValue(Integer.class);
                            int value2 = dataSnapshot.child("countOUT").getValue(Integer.class);


                            int result = value1 - value2;


                            TextView rel = findViewById(R.id.rel);
                            rel.setText(" " + result);






                }else {

                    Toast.makeText(ReadData.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                }


            }else {

                Toast.makeText(ReadData.this,"Failed to read",Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void list(View view) {
        startActivity(new Intent(this,ListActivity.class));
    }
    public void location(View view) {
                startActivity(new Intent(this, Map.class));
    }

}