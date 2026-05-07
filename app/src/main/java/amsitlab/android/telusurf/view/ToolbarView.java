package amsitlab.android.telusurf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import amsitlab.android.telusurf.R;

public class ToolbarView extends ConstraintLayout {

    private Toolbar toolbar;
    private TextView addressBar;
    private ImageView secureIndicator;
    private ImageButton refreshButton;
    private ImageButton menuButton;

    public ToolbarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToolbarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolbarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);
        toolbar = findViewById(R.id.toolbar);
        addressBar = findViewById(R.id.addressBar);
        secureIndicator = findViewById(R.id.secureIndicator);
        refreshButton = findViewById(R.id.refreshButton);
        menuButton = findViewById(R.id.menuButton);
    }

    @NonNull
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setTitle(@Nullable String title) {
        toolbar.setTitle(title);
    }

    @NonNull
    public ImageButton getMenuButton() {
        return menuButton;
    }

    @NonNull
    public ImageButton getRefreshButton() {
        return refreshButton;
    }

    @NonNull
    public TextView getAddressBar() {
        return addressBar;
    }

    @NonNull
    public ImageView getSecureIndicator() {
        return secureIndicator;
    }
}
