package com.universidad.torneosesports;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationActivity extends Activity {
    private static final String PREFS = "team_registration";
    private EditText teamName;
    private EditText captainName;
    private EditText email;
    private EditText roster;
    private Spinner game;
    private CheckBox rules;
    private TextView savedNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        teamName = findViewById(R.id.registration_team);
        captainName = findViewById(R.id.registration_captain);
        email = findViewById(R.id.registration_email);
        roster = findViewById(R.id.registration_roster);
        game = findViewById(R.id.registration_game);
        rules = findViewById(R.id.registration_rules);
        savedNotice = findViewById(R.id.registration_saved_notice);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.registration_games, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        game.setAdapter(adapter);

        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        findViewById(R.id.registration_cancel).setOnClickListener(v -> finish());
        findViewById(R.id.registration_submit).setOnClickListener(v -> submit());
        restoreSavedRegistration();
    }

    private void restoreSavedRegistration() {
        SharedPreferences prefs = getSharedPreferences(PREFS, 0);
        if (!prefs.getBoolean("registered", false)) return;
        teamName.setText(prefs.getString("team", ""));
        captainName.setText(prefs.getString("captain", ""));
        email.setText(prefs.getString("email", ""));
        roster.setText(prefs.getString("roster", ""));
        int gamePosition = prefs.getInt("game", 0);
        if (gamePosition >= 0 && gamePosition < game.getCount()) game.setSelection(gamePosition);
        savedNotice.setVisibility(View.VISIBLE);
    }

    private void submit() {
        String teamValue = teamName.getText().toString().trim();
        String captainValue = captainName.getText().toString().trim();
        String emailValue = email.getText().toString().trim();
        String rosterValue = roster.getText().toString().trim();
        boolean valid = true;

        if (teamValue.length() < 3) {
            teamName.setError(getString(R.string.registration_team_error));
            valid = false;
        }
        if (captainValue.length() < 3) {
            captainName.setError(getString(R.string.registration_captain_error));
            valid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.setError(getString(R.string.registration_email_error));
            valid = false;
        }
        if (rosterValue.length() < 8) {
            roster.setError(getString(R.string.registration_roster_error));
            valid = false;
        }
        if (!rules.isChecked()) {
            rules.setError(getString(R.string.registration_rules_error));
            valid = false;
        } else {
            rules.setError(null);
        }
        if (!valid) return;

        getSharedPreferences(PREFS, 0).edit()
                .putBoolean("registered", true)
                .putString("team", teamValue)
                .putString("captain", captainValue)
                .putString("email", emailValue)
                .putString("roster", rosterValue)
                .putInt("game", game.getSelectedItemPosition())
                .apply();

        savedNotice.setVisibility(View.VISIBLE);
        new AlertDialog.Builder(this)
                .setTitle(R.string.registration_success_title)
                .setMessage(getString(R.string.registration_success_message,
                        teamValue, game.getSelectedItem().toString()))
                .setNegativeButton(R.string.registration_keep_editing, null)
                .setPositiveButton(R.string.registration_done, (dialog, which) -> finish())
                .show();
    }
}
