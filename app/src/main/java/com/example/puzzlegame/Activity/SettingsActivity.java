package com.example.puzzlegame.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzlegame.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize UI elements
        CheckBox musicCheckBox = findViewById(R.id.musicCheckBox);
        RadioGroup themeRadioGroup = findViewById(R.id.themeRadioGroup);
        RadioButton lightThemeRadioButton = findViewById(R.id.lightThemeRadioButton);
        RadioButton darkThemeRadioButton = findViewById(R.id.darkThemeRadioButton);

        // Load user preferences
        boolean isMusicEnabled = sharedPreferences.getBoolean("music_enabled", true);
        boolean isDarkTheme = sharedPreferences.getBoolean("dark_theme", false);

        musicCheckBox.setChecked(isMusicEnabled);
        lightThemeRadioButton.setChecked(!isDarkTheme);
        darkThemeRadioButton.setChecked(isDarkTheme);

        // Set listeners for UI elements
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("music_enabled", isChecked);
                editor.apply();
            }
        });

        themeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkedId == R.id.lightThemeRadioButton) {
                    editor.putBoolean("dark_theme", false);
                } else if (checkedId == R.id.darkThemeRadioButton) {
                    editor.putBoolean("dark_theme", true);
                }
                editor.apply();
            }
        });
    }
}
