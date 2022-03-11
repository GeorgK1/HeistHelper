package com.georgkivivali.heisthelper.activities;

import static com.georgkivivali.heisthelper.MainActivity.EXTRA_TIME;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.georgkivivali.heisthelper.MainActivity;
import com.georgkivivali.heisthelper.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SaveHeistActivity extends AppCompatActivity {
    EditText heistTakeField;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button saveHeistButton;
    TextView timeLabel;

    private int radioId;

    public void navigateToStart() {
        Intent i = new Intent(SaveHeistActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void callWebHook(String time, String buyer, String take) {
        //http part
        OkHttpClient client = new OkHttpClient();

        String webHookUrl = "url here";
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", time + " " + buyer + " " + "$" + take)
                .build();
        Request request = new Request.Builder()
                .url(webHookUrl)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });

        Toast.makeText(getApplicationContext(), "Heist saved!", Toast.LENGTH_SHORT).show();
    }


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_heist_activity);

        timeLabel = findViewById(R.id.timeLabel);
        saveHeistButton = findViewById(R.id.saveHeistButton);
        heistTakeField = findViewById(R.id.heistTakeField);

        radioGroup = findViewById(R.id.buyerRadioGroup);
        radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Intent intent = getIntent();
        String timeLabelText = intent.getStringExtra(EXTRA_TIME);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
            }
        });

        saveHeistButton.setOnClickListener(view -> {

            if (heistTakeField.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please insert a value", Toast.LENGTH_SHORT).show();
            } else {
                navigateToStart();

                callWebHook(timeLabelText, radioButton.getText().toString(), heistTakeField.getText().toString());
            }


        });

        timeLabel.setText(timeLabelText);
    }
}
