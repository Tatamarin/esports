package com.universidad.torneosesports;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        VideoView video = root.findViewById(R.id.video_view);
        ProgressBar progress = root.findViewById(R.id.video_progress);
        TextView status = root.findViewById(R.id.video_status);
        Button load = root.findViewById(R.id.video_load);

        MediaController controls = new MediaController(getActivity());
        controls.setAnchorView(video);
        video.setMediaController(controls);
        load.setOnClickListener(v -> {
            load.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            status.setText(R.string.video_loading);
            Uri localDemo = Uri.parse("android.resource://"
                    + getActivity().getPackageName() + "/" + R.raw.esports_demo);
            video.setVideoURI(localDemo);
            video.requestFocus();
        });
        video.setOnPreparedListener(player -> {
            progress.setVisibility(View.GONE);
            status.setText(R.string.video_ready);
            video.start();
            controls.show(4000);
        });
        video.setOnErrorListener((player, what, extra) -> {
            progress.setVisibility(View.GONE);
            status.setText(R.string.video_error);
            load.setVisibility(View.VISIBLE);
            return true;
        });
        root.findViewById(R.id.video_clip_one).setOnClickListener(v -> openExternalClip(
                "https://www.youtube.com/results?search_query=valorant+1v4+retake"));
        root.findViewById(R.id.video_clip_two).setOnClickListener(v -> openExternalClip(
                "https://www.youtube.com/results?search_query=valorant+flank+competitive"));
        return root;
    }

    private void openExternalClip(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), R.string.no_browser, Toast.LENGTH_SHORT).show();
        }
    }
}
