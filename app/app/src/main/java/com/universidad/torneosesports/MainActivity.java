package com.universidad.torneosesports;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/** Hosts the permanent navigation fragment and swaps only the detail fragment. */
public class MainActivity extends Activity implements MenuFragment.OnMenuOptionSelectedListener {
    private static final String MENU_TAG = "menu";
    private static final String CONTENT_TAG = "content";
    private static final String GALLERY_BACK_STACK = "gallery_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.left_container, new MenuFragment(), MENU_TAG)
                    .replace(R.id.right_container, new HomeFragment(), CONTENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onMenuOptionSelected(MenuOption option) {
        getFragmentManager().popBackStackImmediate(
                GALLERY_BACK_STACK, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Fragment fragment;
        switch (option) {
            case PROFILE:
                fragment = new ProfileFragment();
                break;
            case GALLERY:
                fragment = new GalleryFragment();
                break;
            case VIDEO:
                fragment = new VideoFragment();
                break;
            case WEB:
                fragment = new WebFragment();
                break;
            case ACTIONS:
                fragment = new ActionsFragment();
                break;
            default:
                fragment = new HomeFragment();
        }

        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.right_container, fragment, CONTENT_TAG)
                .commit();
    }

    public void showGalleryDetail(int image, int title, int subtitle, int description, int game) {
        Fragment detail = GalleryDetailFragment.newInstance(
                image, title, subtitle, description, game);
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.right_container, detail, CONTENT_TAG)
                .addToBackStack(GALLERY_BACK_STACK)
                .commit();
    }
}
