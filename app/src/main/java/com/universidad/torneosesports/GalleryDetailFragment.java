package com.universidad.torneosesports;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/** Displays an organized, full gallery entry while keeping the main side menu visible. */
public class GalleryDetailFragment extends Fragment {
    private static final String ARG_IMAGE = "image";
    private static final String ARG_TITLE = "title";
    private static final String ARG_SUBTITLE = "subtitle";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_GAME = "game";

    public static GalleryDetailFragment newInstance(
            int image, int title, int subtitle, int description, int game) {
        GalleryDetailFragment fragment = new GalleryDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_IMAGE, image);
        arguments.putInt(ARG_TITLE, title);
        arguments.putInt(ARG_SUBTITLE, subtitle);
        arguments.putInt(ARG_DESCRIPTION, description);
        arguments.putInt(ARG_GAME, game);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_gallery_detail, container, false);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return root;
        }

        ImageView image = root.findViewById(R.id.gallery_detail_image);
        TextView title = root.findViewById(R.id.gallery_detail_title);
        TextView subtitle = root.findViewById(R.id.gallery_detail_subtitle);
        TextView description = root.findViewById(R.id.gallery_detail_description);
        TextView game = root.findViewById(R.id.gallery_detail_game);

        image.setImageResource(arguments.getInt(ARG_IMAGE));
        image.setContentDescription(getString(arguments.getInt(ARG_TITLE)));
        title.setText(arguments.getInt(ARG_TITLE));
        subtitle.setText(arguments.getInt(ARG_SUBTITLE));
        description.setText(arguments.getInt(ARG_DESCRIPTION));
        game.setText(arguments.getInt(ARG_GAME));

        View.OnClickListener goBack = v -> getFragmentManager().popBackStack();
        root.findViewById(R.id.gallery_detail_back).setOnClickListener(goBack);
        root.findViewById(R.id.gallery_detail_back_button).setOnClickListener(goBack);
        return root;
    }
}
