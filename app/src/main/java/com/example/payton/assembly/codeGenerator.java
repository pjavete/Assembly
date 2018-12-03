package com.example.payton.assembly;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class codeGenerator extends AppCompatActivity {
    //variables to be used through different
    Button copyButton;
    TextView eventCode;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);
        copyButton = findViewById(R.id.copyButton);
        eventCode = findViewById(R.id.eventCode);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/thicc.ttf");
        copyButton.setTypeface(typeface);

        //retrieves the event id and sets it as the eventCode
        Intent intent = getIntent();
        code = intent.getStringExtra("eventCode");
        eventCode.setText(code);
    }

    public void copyCode(View view){
        //copies the event code onto the user's clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("event code", code);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied!", Toast.LENGTH_LONG).show();
        finish();
        onBackPressed();
        //:(((
    }
}
