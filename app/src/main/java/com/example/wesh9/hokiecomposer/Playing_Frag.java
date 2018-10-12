package com.example.wesh9.hokiecomposer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Playing_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Playing_Frag extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Playing_Frag() {
        // Required empty public constructor
    }

    Button playpause;
    ImageView picture;
    TextView backgroundText;
    Button restart;

    String currentPic;



    String songString;
    String backString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playing_, container, false);
        playpause = (Button)view.findViewById(R.id.playpause);
        restart = (Button)view.findViewById(R.id.restart);
        picture = (ImageView)view.findViewById(R.id.picture);
        backgroundText = (TextView)view.findViewById(R.id.background);
        backgroundText.setText(backString);

        if(songString != null) {
            setImage(songString);
        }






        return view;
    }

    public void setBackString(String back){
        backString = back;
    }

    public void setImage(String song){
        songString = song;
        if(song.equals("Go Tech Go")){
            picture.setImageResource(R.drawable.gotechgo);
        }
        else if (song.equals("Mario")){
            picture.setImageResource(R.drawable.mario);
        }
        else if (song.equals("Tetris")){
            picture.setImageResource(R.drawable.tetris);
        }
        else if (song.equals("Pacman")){
            picture.setImageResource(R.drawable.pman);
        }
        else if(song.equals("Cheering")){
            picture.setImageResource(R.drawable.cheering);
        }
        else if(song.equals("Clapping")){
            picture.setImageResource(R.drawable.clapping);
        } else if(song.equals("Go Hokies")){
            picture.setImageResource(R.drawable.letsgohokies);
        }
        currentPic = song;


    }

    public String getCurrPic(){
        return currentPic;
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

    public void setBackgroundText(String inString){
        backgroundText.setText(inString);
    }



    public void setPP(String pp){
        playpause.setText(pp);
    }


}
