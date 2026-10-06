package com.universidad.torneosesports;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    private static final int PICK_PROFILE_IMAGE = 1001;
    private static final String PREFS = "editable_profile";
    private static final String KEY_EXPERIENCE = "experience";
    private static final String KEY_IMAGE_URI = "image_uri";

    private ImageView profileImage;
    private TextView experience;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = root.findViewById(R.id.profile_image);
        experience = root.findViewById(R.id.profile_experience);
        preferences = getActivity().getSharedPreferences(PREFS, 0);
        profileImage.setClipToOutline(true);
        loadSavedProfile();
        root.findViewById(R.id.edit_profile_image).setOnClickListener(v -> chooseImage());
        root.findViewById(R.id.edit_experience).setOnClickListener(v -> editExperience());
        root.findViewById(R.id.restore_profile_image).setOnClickListener(v -> restoreDefaultImage());
        return root;
    }

    private void loadSavedProfile() {
        experience.setText(preferences.getString(KEY_EXPERIENCE, getString(R.string.profile_bio)));
        String savedUri = preferences.getString(KEY_IMAGE_URI, null);
        if (savedUri != null) {
            try {
                profileImage.setImageURI(Uri.parse(savedUri));
                return;
            } catch (Exception ignored) {
                preferences.edit().remove(KEY_IMAGE_URI).apply();
            }
        }
        profileImage.setImageResource(R.drawable.esports_profile_avatar);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PROFILE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_PROFILE_IMAGE || resultCode != android.app.Activity.RESULT_OK
                || data == null || data.getData() == null) return;
        Uri uri = data.getData();
        try {
            getActivity().getContentResolver().takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            profileImage.setImageURI(uri);
            preferences.edit().putString(KEY_IMAGE_URI, uri.toString()).apply();
            Toast.makeText(getActivity(), R.string.profile_image_saved, Toast.LENGTH_SHORT).show();
        } catch (Exception error) {
            Toast.makeText(getActivity(), R.string.profile_image_error, Toast.LENGTH_LONG).show();
        }
    }

    private void editExperience() {
        EditText input = new EditText(getActivity());
        int padding = (int) (18 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding, padding, padding);
        input.setMinLines(5);
        input.setGravity(android.view.Gravity.TOP | android.view.Gravity.START);
        input.setText(experience.getText());
        input.setSelection(input.length());
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.edit_experience_title)
                .setView(input)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, null)
                .create();
        dialog.setOnShowListener(ignored -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(v -> {
                    String value = input.getText().toString().trim();
                    if (value.length() < 20) {
                        input.setError(getString(R.string.experience_too_short));
                        return;
                    }
                    experience.setText(value);
                    preferences.edit().putString(KEY_EXPERIENCE, value).apply();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), R.string.experience_saved, Toast.LENGTH_SHORT).show();
                }));
        dialog.show();
    }

    private void restoreDefaultImage() {
        preferences.edit().remove(KEY_IMAGE_URI).apply();
        profileImage.setImageResource(R.drawable.esports_profile_avatar);
        Toast.makeText(getActivity(), R.string.profile_image_restored, Toast.LENGTH_SHORT).show();
    }
}
