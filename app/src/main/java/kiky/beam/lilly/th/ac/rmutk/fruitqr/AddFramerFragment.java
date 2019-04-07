package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFramerFragment extends Fragment {

    private Myconstant myconstant = new Myconstant();
    private String idRecord, nameFruit, dateString, amountString, unitString;
    private TextView dateTextView;
    private boolean nameFruitABoolean = true;


    public AddFramerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        Create NameFruit
        createNameFruit();


//        Set Date
        setDate();


//        Show View
        showView();


//        Save Controller
        saveController();

    }   // Main Method

    private void saveController() {

        Button button = getView().findViewById(R.id.btnSave);
        final MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = getView().findViewById(R.id.edtAmount);
                amountString = editText.getText().toString().trim();
                dateString = dateTextView.getText().toString();

                if (nameFruitABoolean) {
                    myAlertDialog.normalDialog("Non Choose Name Fruit",
                            "Please Choose Name Fruit");
                } else if (amountString.isEmpty()) {
                    myAlertDialog.normalDialog("No Amount ?", "Please Fill Amount");
                } else {

                    Log.d("7April", "idRecord ==>> " + idRecord);
                    Log.d("7April", "Name ==> " + nameFruit);
                    Log.d("7April", "Amount ==> " + amountString);
                    Log.d("7April", "Unit ==> " + unitString);
                    Log.d("7April", "Date ==> " + dateString);

                    comfirmUpload();

                }


            }   // onClick
        });
    }

    private void comfirmUpload() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Confirm Data");
        builder.setMessage("Name = " + nameFruit + "\n" + "Amount = " + amountString + " " + unitString + "\n" + "Date = " + dateString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadToServer();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void uploadToServer() {

        try {

            AddDetailFramerThread addDetailFramerThread = new AddDetailFramerThread(getActivity());
            addDetailFramerThread.execute(idRecord, nameFruit, amountString, unitString,
                    dateString, myconstant.getUrlAddDetailFramer());

            if (Boolean.parseBoolean(addDetailFramerThread.get())) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentServiceFragment, new ShowListFramerFragment()).commit();
            } else {
                Toast.makeText(getActivity(), "Cannot Upload Please Try Again", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDate() {

        Spinner spinner = getView().findViewById(R.id.spinnerUnit);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myconstant.getUnits());
        spinner.setAdapter(stringArrayAdapter);

        final String[] unitStrings = myconstant.getUnits();
        unitString = unitStrings[0];

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitString = unitStrings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                unitString = unitStrings[0];
            }
        });

        dateTextView = getView().findViewById(R.id.txtShowDate);

        final Calendar calendar = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTextView.setText(dateFormat.format(calendar.getTime()));

        Button button = getView().findViewById(R.id.btnSetDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year, month, dayOfMonth);
                        dateTextView.setText(dateFormat.format(calendar1.getTime()));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();


            }
        });

    }

    private void showView() {
        try {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myconstant.getNameFileSharePreference(), Context.MODE_PRIVATE);
            idRecord = sharedPreferences.getString("idLogin", "");

            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn.execute("id", idRecord, myconstant.getUrlGetUserWhereId());

            String result = getDataWhereOneColumn.get();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            TextView nameTextView = getView().findViewById(R.id.txtName);
            nameTextView.setText(jsonObject.getString("Name"));

            TextView addressTextView = getView().findViewById(R.id.txtAddress);
            addressTextView.setText(jsonObject.getString("Address"));

            TextView phoneTextView = getView().findViewById(R.id.txtPhone);
            phoneTextView.setText(jsonObject.getString("Phone"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNameFruit() {

        final String[] strings = myconstant.getFavoriteFruits();

        Spinner spinner = getView().findViewById(R.id.spinnerFruit);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myconstant.getFavoriteFruits());
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameFruit = strings[position];
                checkStatusFruit(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nameFruit = strings[0];
            }
        });

    }

    private void checkStatusFruit(int position) {
        if (position == 0) {
            nameFruitABoolean = true;
        } else {
            nameFruitABoolean = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_framer, container, false);
    }

}
