package com.sotiris.datumboxapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sotiris.datumboxapp.R;
import com.sotiris.datumboxapp.controllers.SingletonController;

/**
 * @author Sotiris Poulias
 *
 * The one and only activity of datumbox application
 */
public class MainActivity extends Activity {

    private SingletonController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the single instance of controller
        controller = SingletonController.getController(getAssets());

        setContentView(R.layout.activity_main);

        //Get the analyze button
        final Button button = (Button) findViewById(R.id.button);

        //handle onclick event on analyze button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //get the text
                EditText editText = (EditText) findViewById(R.id.editText);
                String txt = editText.getText().toString();

                //get the method selected
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                String method = spinner.getSelectedItem().toString();

                //if text is empty then ask for some input to analyze
                if(TextUtils.isEmpty(txt) || txt.trim().length() == 0) {
                    editText.setError("Enter some text to analyze!");
                    return;
                }

                try {

                    //call the controller to analyze the text
                    String response = controller.analyzeText(txt, method.replaceAll("\\s", ""));

                    //display the response from datumbox API
                    EditText responseText = (EditText) findViewById(R.id.responseText);
                    responseText.setText(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //handle onclick event on analyze button
        final Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //empty the text input
                EditText editText = (EditText) findViewById(R.id.editText);
                editText.setText("");

                //set method selection to default
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                spinner.setSelection(0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
