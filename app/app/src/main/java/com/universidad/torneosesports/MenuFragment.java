package com.universidad.torneosesports;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment extends Fragment {
    public interface OnMenuOptionSelectedListener {
        void onMenuOptionSelected(MenuOption option);
    }

    private OnMenuOptionSelectedListener listener;
    private TextView selectedView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnMenuOptionSelectedListener)) {
            throw new IllegalStateException("MainActivity must implement menu navigation");
        }
        listener = (OnMenuOptionSelectedListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        bind(root, R.id.menu_profile, MenuOption.PROFILE);
        bind(root, R.id.menu_gallery, MenuOption.GALLERY);
        bind(root, R.id.menu_video, MenuOption.VIDEO);
        bind(root, R.id.menu_web, MenuOption.WEB);
        bind(root, R.id.menu_actions, MenuOption.ACTIONS);
        return root;
    }

    private void bind(View root, int viewId, final MenuOption option) {
        final TextView view = root.findViewById(viewId);
        view.setOnClickListener(v -> {
            if (selectedView != null) {
                selectedView.setSelected(false);
            }
            view.setSelected(true);
            selectedView = view;
            listener.onMenuOptionSelected(option);
        });
    }
}
