package com.kimtis.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kimtis.R;
import com.kimtis.data.CachedData;
import com.kimtis.data.manager.PropertyManager;
import com.ppsoln.commons.position.XYZ;
import com.ppsoln.commons.utility.TransformationFactory;

public class SettingsActivity extends AppCompatActivity {

    EditText edit_x, edit_y, edit_z;
    Button btn_enter;
    TextView text_content;
    LinearLayout linear_now;
    XYZ current_xyz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edit_x = (EditText) findViewById(R.id.edit_x);
        edit_x.setText(PropertyManager.getInstance().getDatumX());
        edit_y = (EditText) findViewById(R.id.edit_y);
        edit_y.setText(PropertyManager.getInstance().getDatumY());
        edit_z = (EditText) findViewById(R.id.edit_z);
        edit_z.setText(PropertyManager.getInstance().getDatumZ());

        text_content = (TextView) findViewById(R.id.text_content);

        try {
            current_xyz = TransformationFactory.toXyz(CachedData.getInstance().getCurrentPosition());
            text_content.setText("Current Lat : " + CachedData.getInstance().getCurrentPosition().getLatitude() +
                    "\nCurrent Lon : " + CachedData.getInstance().getCurrentPosition().getLongitude() +
                    "\nCurrent Alt : " + CachedData.getInstance().getCurrentPosition().getAltitude() +
                    "\nCurrent X : " + current_xyz.getX() +
                    "\nCurrent Y : " + current_xyz.getY() +
                    "\nCurrent Z : " + current_xyz.getZ());
        }catch(Exception e){
            Log.e("toXYZ Error : ","error");
        }


        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyManager.getInstance().setDatumX(edit_x.getText().toString());
                PropertyManager.getInstance().setDatumY(edit_y.getText().toString());
                PropertyManager.getInstance().setDatumZ(edit_z.getText().toString());
                PropertyManager.getInstance().updateDatum();
                Toast.makeText(SettingsActivity.this, "데이터가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        linear_now = (LinearLayout)findViewById(R.id.linear_now);
        linear_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyManager.getInstance().setDatumX(current_xyz.getX()+"");
                PropertyManager.getInstance().setDatumY(current_xyz.getY()+"");
                PropertyManager.getInstance().setDatumZ(current_xyz.getZ()+"");
                Toast.makeText(SettingsActivity.this,"Datum updated with current position", Toast.LENGTH_SHORT).show();

                edit_x.setText(PropertyManager.getInstance().getDatumX());
                edit_y.setText(PropertyManager.getInstance().getDatumY());
                edit_z.setText(PropertyManager.getInstance().getDatumZ());
            }
        });


    }
//    boolean isFirst = true;
//
//    MainActivity.OnNmeaCPCalculatedListener mListener = new MainActivity.OnNmeaCPCalculatedListener() {
//        @Override
//        public void onNmeaCPCalculated() {
//            // TODO
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(isFirst) {
//                        XYZ xyz = TransformationFactory.toXyz(CachedData.getInstance().getCurrentPosition());
//                        text_content.append(xyz.getX() + ", " + xyz.getY() + ", " + xyz.getZ() + "\n");
//                        isFirst = false;
//                    }
//                }
//            });
//
//
//        }
//    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        MainActivity.registerOnNmeaCPCalculatedListener(mListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        MainActivity.unregisterOnNmeaCPCalculatedListener(mListener);
//    }

}
