package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private  String typeString;
    private  boolean aBoolean = true;



    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar //เรัยก Toolbar Supprot v.7
        Toolbar toolbar = getView().findViewById(R.id.toobarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar); //MainActivity เหมือนคน  getActivity เป็นตัวเชื่อม
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));//Toolbar เป็นภาษาไทย
        //        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Register"); Toolbar เป็นภาษาอังกฤษ

        //ทำปุ่มย้อนกลับ <-
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);


        // RadioGroup
        RadioGroup radioGroup = getView().findViewById(R.id.ragType);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                aBoolean = false;

                switch (checkedId){
                    case R.id.radProduct:
                        typeString = "Product";
                        break;
                    case R.id.radSales:
                        typeString = "Sales";
                        break;
                    case R.id.radCustomer:
                        typeString = "Customer";
                        break;
                }

            }
        });


    }//Main Method


    //คลิกรูป itemUpload
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemUpload ){

            uploadValueToServer();
            return  true;

        }

        return super.onOptionsItemSelected(item);

    }

    private void uploadValueToServer() {

//        Initial View
        EditText nameEditText = getView().findViewById(R.id.editName);
        EditText surnameEditText = getView().findViewById(R.id.editSurname);
        EditText addressEditText = getView().findViewById(R.id.editAddress);
        EditText phoneEditText = getView().findViewById(R.id.editPhone);
        EditText userEditText = getView().findViewById(R.id.editUser);
        EditText passwordEditText = getView().findViewById(R.id.editPassword);

        String name = nameEditText.getText().toString().trim(); //แปลงค่าText ให้เป็น String , trim ลบค่าที่เว้นวรรคอัตโนวัติ
        String surname = surnameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String user = userEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity()); //การสร้างออปเจ็ค
//        check Spqce  การหาช่องว่าง
        if (name.isEmpty()|| surname.isEmpty() || address.isEmpty() || phone.isEmpty() || user.isEmpty() || password.isEmpty()) {//isEmpty ถ้าไม่มีการกรอกเป็น true

//            Have Space
            myAlertDialog.normalDialog("Have Space", "Please Fill Every Blank");
        } else if (aBoolean){
//            Non Choose
            myAlertDialog.normalDialog("Non Choose Type User", "Please Choose Type User");
        }else{

//            Upload to Server
            try {

                Myconstant myconstant = new Myconstant();
                AddUserThread addUserThread = new AddUserThread(getActivity());
                addUserThread.execute(name,surname,address,phone,user,password,typeString,myconstant.getUrlAddUser());

                if (Boolean.parseBoolean(addUserThread.get())) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }else{
                    myAlertDialog.normalDialog("Cannot Upload","Please Try Again");
                }


            }catch (Exception e){
                e.printStackTrace();
            }


        }




    }

    //สร้างปุ่ม เมณู
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_register, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}
