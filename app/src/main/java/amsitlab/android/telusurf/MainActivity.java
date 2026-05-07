package amsitlab.android.telusurf;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View toolbarMenuLayout;
    private View toolbarTabLayout;
    private TextView toolbarTab;
    private TabListAdapter tabListAdapter;
    private final List<String> openedTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applySavedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateLogo();
        setupToolbarMenu();
        setupToolbarTab();
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
        toolbar = findViewById(R.id.toolbar);
        toolbarMenuLayout = findViewById(R.id.toolbarMenuLayout);

        setupToolbarMenuList();

        ImageButton toolbarMenu = findViewById(R.id.toolbarMenu);
        ImageButton btnExit = findViewById(R.id.btnExit);
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        ImageButton btnCloseMenu = findViewById(R.id.btnCloseMenu);

        toolbarMenu.setOnClickListener(v -> {
            toolbar.setVisibility(View.GONE);
            toolbarMenuLayout.setVisibility(View.VISIBLE);
        });

        btnCloseMenu.setOnClickListener(v -> {
            toolbarMenuLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        });

        btnSettings.setOnClickListener(v -> openSettings());
        btnExit.setOnClickListener(v -> finishAffinity());
    }

    private void setupToolbarTab() {
        toolbarTabLayout = findViewById(R.id.toolbarTabLayout);
        toolbarTab = findViewById(R.id.toolbarTab);
        RecyclerView tabList = findViewById(R.id.tabList);
        ImageButton btnTabAdd = findViewById(R.id.btnTabAdd);

        openedTabs.add(getHomeTitle());
        tabListAdapter = new TabListAdapter(openedTabs);
        tabList.setLayoutManager(new LinearLayoutManager(this));
        tabList.setAdapter(tabListAdapter);

        ViewGroup.LayoutParams params = tabList.getLayoutParams();
        int perItemHeight = (int) (56 * getResources().getDisplayMetrics().density);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = perItemHeight * 4;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        tabList.setLayoutParams(params);

        toolbarTab.setOnClickListener(v -> {
            toolbar.setVisibility(View.GONE);
            toolbarTabLayout.setVisibility(View.VISIBLE);
            toolbarMenuLayout.setVisibility(View.GONE);
        });

        btnTabAdd.setOnClickListener(v -> {
            openedTabs.add(getHomeTitle());
            updateTabCount();
        });

        updateTabCount();
    }

    private String getHomeTitle() {
        String language = Locale.getDefault().getLanguage();
        return "id".equals(language) ? "Beranda" : "Home";
    }

    private void updateTabCount() {
        toolbarTab.setText(String.valueOf(openedTabs.size()));
        tabListAdapter.notifyDataSetChanged();
    }

    private void setupToolbarMenuList() {
        RecyclerView toolbarMenuList = findViewById(R.id.toolbarMenuList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        toolbarMenuList.setLayoutManager(layoutManager);

        List<Integer> menuItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            menuItems.add(R.drawable.ic_menu_placeholder);
        }
        toolbarMenuList.setAdapter(new ToolbarMenuAdapter(menuItems));
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

    private static class ToolbarMenuAdapter extends RecyclerView.Adapter<ToolbarMenuAdapter.ViewHolder> {
        private final List<Integer> items;

        ToolbarMenuAdapter(List<Integer> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toolbar_menu, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.icon.setImageResource(items.get(position));
            holder.label.setText("");
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView label;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.itemIcon);
                label = itemView.findViewById(R.id.itemLabel);
            }
        }
    }

    private class TabListAdapter extends RecyclerView.Adapter<TabListAdapter.ViewHolder> {
        private final List<String> items;

        TabListAdapter(List<String> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tabFavicon.setImageResource(R.drawable.ic_launcher_foreground);
            holder.tabTitle.setText(items.get(position));

            holder.itemView.setOnClickListener(v -> {
                toolbarTabLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            });

            holder.btnCloseTab.setOnClickListener(v -> {
                int adapterPosition = holder.getBindingAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && items.size() > 1) {
                    items.remove(adapterPosition);
                    updateTabCount();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView tabFavicon;
            TextView tabTitle;
            ImageButton btnCloseTab;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tabFavicon = itemView.findViewById(R.id.tabFavicon);
                tabTitle = itemView.findViewById(R.id.tabTitle);
                btnCloseTab = itemView.findViewById(R.id.btnCloseTab);
            }
        }
    }
}
