package com.example.Project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class mainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    TextView txtView;
    TextView txtView2;
    EditText UserName;
    EditText Password;
    Button btnRoom1;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.main);

            txtView = (TextView) findViewById(R.id.textViewUser);
            btnRoom1 = (Button) findViewById(R.id.btnRoom1);
            txtView2 = (TextView) findViewById(R.id.textViewPass);
            UserName = (EditText) findViewById(R.id.editTextUser);
            Password = (EditText) findViewById(R.id.editTextPass);

        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void Login(View v)
    {
        String user,pass;
        int res;
        try {
            user = UserName.getText().toString();
            pass = Password.getText().toString();
            res = db.ReturnUserName(user,pass);
            if(res == 1)
            {
                Intent i = new Intent(mainActivity.this, activity1.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(getBaseContext(), "The Username and Password combination is not valid, try again",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
