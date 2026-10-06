package com.universidad.torneosesports;

import android.app.Activity;
import android.os.Bundle;

public class BracketActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }
}
