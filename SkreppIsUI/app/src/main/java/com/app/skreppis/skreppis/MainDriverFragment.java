package com.app.skreppis.skreppis;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainDriverFragment extends Fragment {
    Callbacks mFindPassengerCB;
    View view;
    public interface Callbacks {
        void findPassenger();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_driver, container, false);
        Button passengerButton = (Button) view.findViewById(R.id.p_findride);
        passengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFindPassengerCB.findPassenger();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFindPassengerCB = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ButanPushedCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFindPassengerCB = null;
    }
}