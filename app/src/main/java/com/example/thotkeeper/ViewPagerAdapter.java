package com.example.thotkeeper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    MainActivity mainActivity;
    public ViewPagerAdapter(@NonNull MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Meto el MainActivity para hacer operaciones como si fuera un mediador, pero no sería necesario.
        if(position == 1){
            EntradasFragment entradasFragment = new EntradasFragment();
            entradasFragment.setMainActivity(mainActivity);
            return entradasFragment;
        }
        //Si no es posicion 1, es posicion 0, para simplificar codigo en vez de usar switch con default y eso.
        ChatsFragment chatsFragment = ChatsFragment.newInstance();
        chatsFragment.setMainActivity(mainActivity);
        mainActivity.setChatsFragment(chatsFragment);
        return chatsFragment;

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
