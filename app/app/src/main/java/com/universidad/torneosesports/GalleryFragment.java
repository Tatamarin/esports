package com.universidad.torneosesports;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GalleryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        bindCard(root, R.id.gallery_final, R.drawable.gallery_valorant_roster,
                R.string.gallery_final_title, R.string.gallery_final_subtitle,
                R.string.gallery_final_detail, R.string.game_valorant);
        bindCard(root, R.id.gallery_lan, R.drawable.gallery_lol_booth,
                R.string.gallery_lan_title, R.string.gallery_lan_subtitle,
                R.string.gallery_lan_detail, R.string.game_lol);
        bindCard(root, R.id.gallery_masters, R.drawable.gallery_valorant_champions,
                R.string.gallery_masters_title, R.string.gallery_masters_subtitle,
                R.string.gallery_masters_detail, R.string.game_valorant);
        return root;
    }

    private void bindCard(View root, int id, int image, int title, int subtitle,
                          int description, int game) {
        root.findViewById(id).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showGalleryDetail(
                        image, title, subtitle, description, game);
            }
        });
    }
}
