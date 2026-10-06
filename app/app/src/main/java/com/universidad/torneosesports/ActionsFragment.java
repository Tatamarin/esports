package com.universidad.torneosesports;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ActionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_actions, container, false);
        root.findViewById(R.id.action_register).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), RegistrationActivity.class)));
        root.findViewById(R.id.action_bracket).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), BracketActivity.class)));
        root.findViewById(R.id.action_live).setOnClickListener(v -> openLiveStream());
        root.findViewById(R.id.action_standings).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), StandingsActivity.class)));
        return root;
    }

    private void openLiveStream() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/directory/category/valorant"));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), R.string.no_browser, Toast.LENGTH_SHORT).show();
        }
    }
}
