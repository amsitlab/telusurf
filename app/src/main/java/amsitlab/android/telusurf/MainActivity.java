package amsitlab.android.telusurf;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import amsitlab.android.telusurf.view.ToolbarView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ToolbarView toolbarView;
    private View toolbarMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateLogo();
        setupToolbarMenu();
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
        toolbarView = findViewById(R.id.toolbarView);
        toolbarMenuLayout = findViewById(R.id.toolbarMenuLayout);

        setupToolbarMenuList();

        ImageButton toolbarMenu = toolbarView.getMenuButton();
        ImageButton btnExit = findViewById(R.id.btnExit);
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        ImageButton btnCloseMenu = findViewById(R.id.btnCloseMenu);

        toolbarMenu.setOnClickListener(v -> {
            toolbarView.setVisibility(View.GONE);
            toolbarMenuLayout.setVisibility(View.VISIBLE);
        });

        btnCloseMenu.setOnClickListener(v -> {
            toolbarMenuLayout.setVisibility(View.GONE);
            toolbarView.setVisibility(View.VISIBLE);
        });

        btnSettings.setOnClickListener(v -> openSettings());
        btnExit.setOnClickListener(v -> finishAffinity());
    }

    private void setupToolbarMenuList() {
        RecyclerView toolbarMenuList = findViewById(R.id.toolbarMenuList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        toolbarMenuList.setLayoutManager(layoutManager);

        List<Integer> menuItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            menuItems.add(R.drawable.ic_menu_placeholder);
        }
        toolbarMenuList.setAdapter(new ToolbarView.MenuAdapter(menuItems));
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
