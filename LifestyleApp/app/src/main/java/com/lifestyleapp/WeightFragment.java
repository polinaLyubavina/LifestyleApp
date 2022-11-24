package com.lifestyleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class WeightFragment extends Fragment implements View.OnClickListener {

    private ConstraintLayout mainLayout;
    private EditText editTextBMR, editTextCalories, editTextBMI;
    private Double poundsToLose;
    private TextView tvPoundsPerWeek, tvHeaderInformation;
    private SeekBar seekBarPoundsPerWeek;
    private RadioButton radioButtonActive, radioButtonSedentary;
    private ImageView profilePhoto;

    private View weight_man_frag_view;
    OnLifePressFromWeightListener lifePressListenerFromWeight;

    private UserViewModel userViewModel;
    //private User user;
    //private Double bmr = 0.0;
    //private Double bmi = 0.0;
    //private boolean isSedentary = false;

    public WeightFragment() {
        // Required empty public constructor
    }

    public interface OnLifePressFromWeightListener {
        public void onLifeBtnPressFromWeight();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListenerFromWeight = (WeightFragment.OnLifePressFromWeightListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressFromWeightListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weight_man_frag_view = inflater.inflate(R.layout.fragment_weight_man, container, false);
        Button buttonLifestyle = weight_man_frag_view.findViewById(R.id.lifestyle_btn_weightman_frag);
        radioButtonActive = weight_man_frag_view.findViewById(R.id.calculatorActiveFrag);
        radioButtonSedentary = weight_man_frag_view.findViewById(R.id.calculatorSedentaryFrag);
        profilePhoto = weight_man_frag_view.findViewById(R.id.weight_man_profile_pic_frag);

        // set click listeners
        buttonLifestyle.setOnClickListener(this);
        radioButtonActive.setOnClickListener(this);
        radioButtonSedentary.setOnClickListener(this);
//IL: imane



       // weightViewModel = ViewModelProviders.of(this).get(WeightViewModel.class);
        userViewModel = new ViewModelProvider(this.getActivity()).get(UserViewModel.class);

        try {
            userViewModel.getProfileViewModelData().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {

                    Double bmr = null;
                    Double bmi = null;
                    try {
                        bmr = BMRCalculators.calculateBMR(user.getWeight(), user.getHeight(), user.getAge(), user.getGender());
                        bmi = BMRCalculators.calculateBMI(user.getWeight(), user.getHeight());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //pounds per week seek bar
                    seekBarPoundsPerWeek = weight_man_frag_view.findViewById(R.id.calculatorPoundsPerWeekFrag);
                    seekBarPoundsPerWeek.setOnSeekBarChangeListener(seekBarChangePoundsPerWeek);

                    //text above pounds per week seek bar
                    poundsToLose = seekBarPoundsPerWeek.getProgress()/10.0;
                    tvPoundsPerWeek = weight_man_frag_view.findViewById(R.id.tvCalculatorChangeTextFrag);
                    tvPoundsPerWeek.setText("Pounds To Change Per Week: " + poundsToLose);

                    if (user != null && user.getProfilePhotoPath() != null)
                    {

                        FileInputStream fis = null;
                        try {
                            fis = getContext().openFileInput(user.getProfilePhotoPath());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        byte[] readBytes = new byte[user.getProfilePhotoSize()];
                        try {
                            fis.read(readBytes);
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap fromFileBmp = BitmapFactory.decodeByteArray(readBytes,0,readBytes.length);
                        profilePhoto.setImageBitmap(fromFileBmp);
                    }

                    if(user != null && user.getHeight() != 0 && user.getWeight() != 0)
                    {
                        tvHeaderInformation.setText("Calculations based on a weight of " + user.getWeight() + " pounds and a height of " + user.getHeight() + " inches.");
                        editTextCalories.setText(String.valueOf((int) BMRCalculators.calculateCaloriesToEat(bmr, poundsToLose, user.getSedentary())));
                        editTextBMR.setText(String.valueOf(bmr.intValue()));
                        editTextBMI.setText(String.valueOf(new DecimalFormat("#.0").format(bmi)));
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // calculate BMR



        return weight_man_frag_view;

    }

    @Override
    public void onStart()
    {
        super.onStart();

        profilePhoto = weight_man_frag_view.findViewById(R.id.weight_man_profile_pic_frag);
        editTextBMI = weight_man_frag_view.findViewById(R.id.bmiEditTextFrag);
        tvHeaderInformation = weight_man_frag_view.findViewById(R.id.headerInformationFrag);
        editTextBMR = weight_man_frag_view.findViewById(R.id.basalMetRateEditTextFrag);
        editTextCalories = weight_man_frag_view.findViewById(R.id.dailyCalEditTextFrag);
        mainLayout = weight_man_frag_view.findViewById(R.id.main_layout);


    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.calculatorActiveFrag:

                userViewModel.setSedentary(false);
                break;

            case R.id.calculatorSedentaryFrag:

                userViewModel.setSedentary(true);

                break;

            case R.id.lifestyle_btn_weightman_frag:
                lifePressListenerFromWeight.onLifeBtnPressFromWeight();
                break;
        }
    }

    // seek bar listener for pounds gained/lost per week
    SeekBar.OnSeekBarChangeListener seekBarChangePoundsPerWeek = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int pounds, boolean fromUser) {

            // updated continuously as the user slides their thumb
/*
            if(user != null)
            {
                poundsToLose = ((double)pounds / 10.0);
                int dailyCalories = (int) BMRCalculators.calculateCaloriesToEat(bmr, poundsToLose, isSedentary);
                tvPoundsPerWeek.setText("Pounds To Change Per Week: " + poundsToLose);

                if((dailyCalories < 1200  && user.getGender() == 1.0) || (dailyCalories < 1000 && user.getGender() == 0.0)){
                    editTextCalories.setText(dailyCalories +  " (WARNING)");
                    Snackbar.make(mainLayout, "WARNING: EXCESSIVELY LOW CALORIC INTAKE", 2500).show();
                } else {
                    editTextCalories.setText(String.valueOf(dailyCalories));
                }

            }


 */

            //Add new func for pound
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
}