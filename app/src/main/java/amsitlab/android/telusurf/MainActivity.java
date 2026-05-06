package amsitlab.android.telusurf;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbarMenu();
        onCreateLogo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreateLogo();
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    private void setupToolbarMenu() {
        View toolbar = findViewById(R.id.toolbar);
        View toolbarMenuPopup = findViewById(R.id.toolbarMenuPopup);
        ImageButton toolbarMenu = findViewById(R.id.toolbarMenu);
        TextView menuSettings = findViewById(R.id.menuSettings);

        toolbarMenu.setOnClickListener(v -> {
            toolbar.setVisibility(View.GONE);
            toolbarMenuPopup.setVisibility(View.VISIBLE);
        });

        menuSettings.setOnClickListener(v -> {
            toolbarMenuPopup.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            openSettings();
        });
    }
    private void onCreateLogo() {
        ImageView ivLogo = findViewById(R.id.ivLogo);

        boolean isDarkMode =
                (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                        == Configuration.UI_MODE_NIGHT_YES;

        ImageViewCompat.setImageTintList(
                ivLogo,
                isDarkMode ? null : ContextCompat.getColorStateList(this, android.R.color.black));

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int logoSize = (int) (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) * 0.42f);

        ivLogo.getLayoutParams().width = logoSize;
        ivLogo.getLayoutParams().height = logoSize;
        ivLogo.requestLayout();
    }
}
