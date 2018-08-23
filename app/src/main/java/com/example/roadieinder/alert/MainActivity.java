package com.example.roadieinder.alert;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String check_user ,check_pass ,tag = this.getPackageName();

    Button login,cancel ;

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.button_login);
        cancel =(Button)findViewById(R.id.button_cancel);

        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_user = user.getText().toString();
                check_pass = pass.getText().toString();

                if(check_user.length() == 0 || check_pass.length()==0){
                    toast("Field Empty");
                }
                else {
                    if (isExternalStorageWritable()) {
                        getPublicAlbumStorageDir(check_user);
                        AlertDialog.Builder dialogBox = new AlertDialog.Builder(MainActivity.this);
                        dialogBox.setMessage("Should we Proceed").setCancelable(false)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        toast("Done");
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        user.setText(null);
                                        pass.setText(null);
                                        String name = getPublicAlbumStorageDir(check_user).getName();
                                        toast(name);
                                    }
                                }).show();
                    }
                }
            }
        });

    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public File getPublicAlbumStorageDir(String username) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), username);
        if (!file.mkdirs()) {
            Log.e(tag,"Directory not found");
        }
        return file;
    }
}
