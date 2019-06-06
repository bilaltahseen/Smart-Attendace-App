package com.finalart.smartattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class qr_attendance extends AppCompatActivity implements BarcodeRetriever{
    public Barcode barcode;
    public  String uid_get;
    public  String email_get;
    public  String name_get;
    private FirebaseAuth mAuth;
    public List data_list = new ArrayList();
    public BarcodeCapture barcodeCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_attendance);
        barcodeCapture= (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);

    }

    @Override
    public void onPermissionRequestDenied() {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onRetrieved(final Barcode barcode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String detect=barcode.rawValue.toString().trim();
                if(detect.equals("Bilal Khan")) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null)
                    {  TextView disp_email =(TextView)findViewById(R.id.std_email);
                        TextView disp_name =(TextView)findViewById(R.id.std_name);
                        Button atten_bt = (Button)findViewById(R.id.mark_atten);
                        disp_email.setBackground(getResources().getDrawable(R.color.colorPrimary));
                        disp_name.setBackground(getResources().getDrawable(R.color.colorPrimary));
                        email_get = user.getEmail();
                        name_get = user.getDisplayName();
                        uid_get = user.getUid();
                        data_list.add(email_get);
                        data_list.add(name_get);
                        data_list.add("Present");
                        disp_email.setText(email_get);
                        disp_name.setText(name_get);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("StudentData");
                        myRef.child(uid_get).setValue(data_list);
                        final AlertDialog.Builder atten_succs = new AlertDialog.Builder(qr_attendance.this);
                        atten_succs.setTitle("Attendance Marked");
                        atten_succs.setMessage("Your attendance is successfully marked");
                        atten_succs.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                return;

                            }
                        });
                        atten_succs.show();
                        atten_bt.setBackground(getResources().getDrawable(R.color.common_google_signin_btn_text_light_disabled));
                        atten_bt.setText("Attendance Marked");
                        barcodeCapture.stopScanning();


                    }

                }
                else {
                    Toast.makeText(qr_attendance.this, "False", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }
    public void atten_bt(View view)
    {


        if(email_get!=null)
        {


    }
        else{
            return;
        }
    }

}
