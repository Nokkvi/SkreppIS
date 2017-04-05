package com.app.skreppis.skreppis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class MessageBox extends Fragment {

    private static final String BOX_STATE = "boxState";

    private String boxState;

    private TextView mMessageOne;
    private TextView mMessageTwo;
    private Chronometer mChrono;
    private Button bOkay;
    private Button bTry;
    private Button bCancel;
    private Button bRate;
    private Button bAccept;
    private Button bReject;

    private OnStateChangeListener mStateListener;

    public MessageBox() {
        // Required empty public constructor
    }


    public static MessageBox newInstance(String boxState) {
        MessageBox fragment = new MessageBox();
        Bundle args = new Bundle();
        args.putString(BOX_STATE, boxState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            boxState = getArguments().getString(BOX_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message_box, container, false);
        mMessageOne = (TextView) v.findViewById(R.id.mb_message1);
        mMessageTwo = (TextView) v.findViewById(R.id.mb_message2);
        mChrono = (Chronometer) v.findViewById(R.id.mb_timer);
        bOkay = (Button) v.findViewById(R.id.btn_mb_ok);
        bTry = (Button) v.findViewById(R.id.btn_mb_try);
        bAccept = (Button) v.findViewById(R.id.btn_mb_accept);
        bCancel = (Button) v.findViewById(R.id.btn_mb_cancel);
        bRate = (Button) v.findViewById(R.id.btn_mb_rate);
        bReject = (Button) v.findViewById(R.id.btn_mb_reject);

        bOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushOkay();
            }
        });
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushTry();
            }
        });
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushAccept();
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushCancel();
            }
        });
        bRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushRate();
            }
        });
        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushReject();
            }
        });

        setState(boxState);

        return v;
    }

    private void pushOkay() {
        //TODO: Ýtt á Ok hnapp
    }

    private void pushCancel() {
        //TODO: Ýtt á Cancel hnapp. Glugga lokað
        //Hættir við ferð í ákveðnum stöðum
    }

    private void pushRate() {
        //TODO: Ýtt á Rate hnapp. Farið yfir í RateActivity og bílstjóra gefinn einkun
    }

    private void pushTry() {
        //TODO: Ýtt á Try Again hnapp ef bílstjóri hafnaði. Farið aftur í leitarniðurstöður án fyrri bílstjóra
    }

    private void pushAccept() {
        //TODO: Ýtt á Accept takka. Farþegi valinn
    }

    private void pushReject() {
        //TODO: Ýtt á Reject takka. Farþega hafnað
    }

    private void setState(String state){
        layoutClear();

        //
        switch (state) {
            case "DriverActive":
                mMessageOne.setText(R.string.mb_driver_active);
                return;
            case "DriverRequest":
                mMessageOne.setText(R.string.mb_driver_request);
                //TODO: Getta farþega sem requestar
                String p = getString(R.string.mb_passenger_default);
                mMessageTwo.setVisibility(View.VISIBLE);
                mMessageTwo.setText(p);
                bAccept.setVisibility(View.VISIBLE);
                bReject.setVisibility(View.VISIBLE);
                return;
            case "PassengerWaiting":
                mMessageOne.setText(R.string.mb_driver_pickup);
                //TODO: Getta staðsetningu farþega
                String l = getString(R.string.mb_location_default);
                mMessageTwo.setVisibility(View.VISIBLE);
                mMessageTwo.setText(l);
                bCancel.setVisibility(View.VISIBLE);
                return;
            case "PassengerCancels":
                mMessageOne.setText(R.string.mb_driver_cancel);
                bOkay.setVisibility(View.VISIBLE);
                return;
            case "DriverDestination":
                mMessageOne.setText(R.string.mb_driver_dest);
                //TODO: Getta áfangastað farþega
                String d = getString(R.string.mb_location_default);
                mMessageTwo.setVisibility(View.VISIBLE);
                mMessageTwo.setText(d);
                bCancel.setVisibility(View.VISIBLE);
                return;
            case "RequestSent":
                mMessageOne.setText(R.string.mb_passenger_request);
                //TODO: Getta bílstjóra sem beðið var um
                String dr = getString(R.string.mb_passenger_default);
                mMessageTwo.setVisibility(View.VISIBLE);
                mMessageTwo.setText(dr);
                return;
            case "RequestRejected":
                mMessageOne.setText(R.string.mb_passenger_rejected);
                bOkay.setVisibility(View.VISIBLE);
                bTry.setVisibility(View.VISIBLE);
                return;
            case "RequestTimeout":
                mMessageOne.setText(R.string.mb_passenger_timeout);
                bOkay.setVisibility(View.VISIBLE);
                bTry.setVisibility(View.VISIBLE);
                return;
            case "RequestSuccess":
                mMessageOne.setText(R.string.mb_passenger_pickup);
                //TODO: Implementa eta klukkuna
                mChrono.setVisibility(View.VISIBLE);
                bCancel.setVisibility(View.VISIBLE);
                return;
            case "DestEta":
                mMessageOne.setText(R.string.mb_passenger_dest);
                //TODO: Implementa eta klukkuna
                mChrono.setVisibility(View.VISIBLE);
                bCancel.setVisibility(View.VISIBLE);
                return;
            case "DriverCancelsE":
                mMessageOne.setText(R.string.mb_passenger_dcancel);
                bOkay.setVisibility(View.VISIBLE);
                return;
            case "DriverCancelsL":
                mMessageOne.setText(R.string.mb_passenger_dcancel);
                bOkay.setVisibility(View.VISIBLE);
                bRate.setVisibility(View.VISIBLE);
                return;
            case "YouCancelE":
                mMessageOne.setText(R.string.mb_passenger_pcancel);
                bOkay.setVisibility(View.VISIBLE);
                return;
            case "YouCancelL":
                mMessageOne.setText(R.string.mb_passenger_pcancel);
                bOkay.setVisibility(View.VISIBLE);
                bRate.setVisibility(View.VISIBLE);
                return;
            case "Arrival":
                mMessageOne.setText(R.string.mb_arrival);
                bOkay.setVisibility(View.VISIBLE);
                bRate.setVisibility(View.VISIBLE);
                return;
            default:
                mMessageOne.setText(R.string.mb_default);
        }
    }

    public void layoutClear(){
        mMessageOne.setText(R.string.mb_default);
        mMessageTwo.setVisibility(View.GONE);
        mMessageTwo.setText(R.string.mb_location_default);
        mChrono.setVisibility(View.GONE);
        bAccept.setVisibility(View.GONE);
        bReject.setVisibility(View.GONE);
        bOkay.setVisibility(View.GONE);
        bTry.setVisibility(View.GONE);
        bCancel.setVisibility(View.GONE);
        bRate.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStateChangeListener) {
            mStateListener = (OnStateChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStateChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStateListener = null;
    }



    public interface OnStateChangeListener {
        void onStateChange(String state);
    }
}
