package amsitlab.android.telusurf;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup rgTheme = findViewById(R.id.rgTheme);
        String mode = ThemeManager.getSavedTheme(this);
        if (ThemeManager.DARK.equals(mode)) {
            rgTheme.check(R.id.rbDark);
        } else if (ThemeManager.LIGHT.equals(mode)) {
            rgTheme.check(R.id.rbLight);
        } else {
            rgTheme.check(R.id.rbSystem);
        }

        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedMode = ThemeManager.SYSTEM;
            if (checkedId == R.id.rbDark) {
                selectedMode = ThemeManager.DARK;
            } else if (checkedId == R.id.rbLight) {
                selectedMode = ThemeManager.LIGHT;
            }

            if (!selectedMode.equals(ThemeManager.getSavedTheme(this))) {
                ThemeManager.saveTheme(this, selectedMode);
                recreate();
            }
        });
    }
}
