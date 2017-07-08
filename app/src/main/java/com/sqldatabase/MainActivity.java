package com.sqldatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sql.DBHelper;
import viewmodel.PersonViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView infoTxt;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button showButton;
    private EditText idEdt;
    private EditText nameEdt;
    private EditText surnameEdt;
    private TextView allPersonTxt;
    private DBHelper mydb = null;
    private String name;
    private String surname;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEvents();
    }

    public void initEvents() {
        addButton = (Button) findViewById(R.id.activity_main_button_add);
        updateButton = (Button) findViewById(R.id.activity_main_button_update);
        deleteButton = (Button) findViewById(R.id.activity_main_button_delete);
        showButton = (Button) findViewById(R.id.activity_main_button_show_person);
        nameEdt = (EditText) findViewById(R.id.activity_main_edt_name);
        surnameEdt = (EditText) findViewById(R.id.activity_main_edt_surname);
        allPersonTxt = (TextView) findViewById(R.id.activity_main_txt_allperson);
        idEdt = (EditText) findViewById(R.id.activity_main_edt_id);

        mydb = new DBHelper(this);


        addButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        showButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_button_add:
                addPerson();
                break;
            case R.id.activity_main_button_update:
                updatePerson();
            case R.id.activity_main_button_delete:
                deletePerson();
            case R.id.activity_main_button_show_person:
                showPerson();
        }
    }

    private void showPerson() {
        int i;
        ArrayList<PersonViewModel> personList = mydb.getAllPeople();

        String listString = "";

        for (PersonViewModel s : personList) {
            listString += s.getId() + "  " + s.getName() + "   " + s.getSurname() + "\n";

        }
        allPersonTxt.setText(listString);

    }


    private void addPerson() {


        name = nameEdt.getText().toString();
        surname = surnameEdt.getText().toString();
        mydb.addPeople(name, surname);


    }


    private void updatePerson() {

        mydb.updatePerson(Integer.valueOf(idEdt.getText().toString()), nameEdt.getText().toString(), surnameEdt.getText().toString());
    }

    private void deletePerson() {

        mydb.deletePerson(Integer.valueOf(idEdt.getText().toString()));
    }
}
