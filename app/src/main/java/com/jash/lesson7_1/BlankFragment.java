package com.jash.lesson7_1;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    private String text;

    public static Fragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        Fragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public BlankFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        text = getArguments().getString("text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        return textView;
    }

}
