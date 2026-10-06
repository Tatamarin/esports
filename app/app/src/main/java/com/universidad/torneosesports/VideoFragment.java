package com.universidad.torneosesports;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoFragment extends Fragment {
    private static final String DEMO_VIDEO =
            "https://rr5---sn-q4flrnez.googlevideo.com/videoplayback?expire=1791187755&ei=ywbDavWNKualir4Pk-by-QY&ip=46.202.248.252&id=o-ALYzsQH2g2XavAjJpDfuNAd_eCPtBnup3yaDPZ65u5gF&itag=18&source=youtube&requiressl=yes&xpc=EgVo2aDSNQ%3D%3D&rms=au%2Cau&bui=AWzQHwpMUUsfdaqZkdVatl7tYsQnHrqrFHOuZ8WHX6HG_5j_L0G1NM9FtetIe0zUSJjCWB_KUdnV5pjR&spc=I-rgIS7lvyAa9vEmH85YaSdEWpzdVqjfDTSy9ElApyZexLTWB545RywALbvHSsluPGs&vprv=1&svpuc=1&mime=video%2Fmp4&rqh=1&cnr=14&ratebypass=yes&dur=599.980&lmt=1762034230103968&fexp=51565115,52112904,52184226,52250513&c=ANDROID&txp=5538534&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cxpc%2Cbui%2Cspc%2Cvprv%2Csvpuc%2Cmime%2Crqh%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AE0s2JYwRQIhAICPpUk4gL55C-2l46WcNXYqBkIu4lczM6KAYfFZLJnIAiBSufEgFgt8C--ysd2jTRs-2ntW3UGXGHZqByvf352Epg%3D%3D&redirect_counter=1&rm=sn-q4feed76&rrc=104&req_id=82d3e611e975a3ee&cms_redirect=yes&cmsv=e&cps=113&ipbypass=yes&met=1791166162,&mh=rV&mip=152.202.113.5&mm=31&mn=sn-q4flrnez&ms=au&mt=1791165661&mv=m&mvi=5&pl=19&lsparams=cps,ipbypass,met,mh,mip,mm,mn,ms,mv,mvi,pl,rms&lsig=APaTxxMwRAIgfR2ewwlH-OInyrtnLDToxBu-BnHgQDu-E6U2TnSC8WQCIGIhlBRTqgtEMbWwm7Rpmul9i3HpPUkTt8miSN8trmYh";

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
            progress.setVisibility(View.VISIBLE);
            status.setText(R.string.video_loading);
            video.setVideoURI(Uri.parse(DEMO_VIDEO));
            video.requestFocus();
        });
        video.setOnPreparedListener(player -> {
            progress.setVisibility(View.GONE);
            status.setText(R.string.video_ready);
            controls.show(4000);
        });
        video.setOnErrorListener((player, what, extra) -> {
            progress.setVisibility(View.GONE);
            status.setText(R.string.video_error);
            return true;
        });
        return root;
    }
}
