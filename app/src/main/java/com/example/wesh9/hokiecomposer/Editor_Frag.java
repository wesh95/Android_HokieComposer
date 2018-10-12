package com.example.wesh9.hokiecomposer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Editor_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Editor_Frag extends Fragment{

    private OnFragmentInteractionListener mListener;

    Button play;
    Spinner background;
    Spinner overlap1;
    Spinner overlap2;
    Spinner overlap3;

    SeekBar ovseek1;
    SeekBar ovseek2;
    SeekBar ovseek3;

    int backTime = 0;

    String backSong;



    String currSong;

    public Editor_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editor_, container, false);
        play = (Button)view.findViewById(R.id.play);
        background = (Spinner)view.findViewById(R.id.backgroundSpinner);
        overlap1 = (Spinner)view.findViewById(R.id.overlap1);
        overlap2 = (Spinner)view.findViewById(R.id.overlap2);
        overlap3 = (Spinner)view.findViewById(R.id.overlap3);
        ovseek1 = (SeekBar)view.findViewById(R.id.ovseek1);
        ovseek2 = (SeekBar)view.findViewById(R.id.ovseek2);
        ovseek3 = (SeekBar)view.findViewById(R.id.ovseek3);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String getBackground(){
        return background.getSelectedItem().toString();

    }

    public String getCurrSong(){
        return currSong;
    }


    public void setCurrSong(String song){
        this.currSong = song;
    }

    public void setBackTime(int bt){
        ovseek1.setMax(bt);
        ovseek2.setMax(bt);
        ovseek3.setMax(bt);
        backTime = bt;
    }

    public int[] getSeekPositions(){
        int[] pos = new int[3];
        pos[0] = ovseek1.getProgress();
        pos[1] = ovseek2.getProgress();
        pos[2] = ovseek3.getProgress();

        return pos;
    }

    public String[] getOVsongs(){
        String[] songs =new String[3];
        songs[0] = overlap1.getSelectedItem().toString();
        songs[1] = overlap2.getSelectedItem().toString();
        songs[2] = overlap3.getSelectedItem().toString();
        return songs;
    }


}
