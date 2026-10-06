package com.universidad.torneosesports;

import android.app.Activity;
import android.os.Bundle;

public class StandingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }
}
