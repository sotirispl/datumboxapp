package com.sotiris.datumboxapp.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sotiris.datumboxapp.R;
import com.sotiris.datumboxapp.controllers.SingletonController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private SingletonController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = SingletonController.getController(getAssets());

        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO: add loading animation

                EditText editText = (EditText) findViewById(R.id.editText);
                String txt = editText.getText().toString();

                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                String method = spinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(txt) || txt.replace(getString(R.string.editTextInitial), "").trim().length() == 0) {
                    editText.setError("Enter some text to analyze!");
                    return;
                }
                try {
                    String response = controller.analyzeText(txt, method.replaceAll("\\s", ""));
                    EditText responseText = (EditText) findViewById(R.id.responseText);
                    responseText.setText(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
