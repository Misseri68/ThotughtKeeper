package com.example.thotkeeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EntradasFragment extends Fragment {
    private MainActivity mainA;

    public EntradasFragment() {
    }


    public static EntradasFragment newInstance() {
        EntradasFragment entradasFragment = new EntradasFragment();
        return entradasFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.entradas_fragment, container, false);
    }

    public void setMainActivity(MainActivity activity) {
        this.mainA = activity;
    }

}