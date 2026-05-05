package amsitlab.android.telusurf;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_ADD_NEW_TAB = "extra_add_new_tab";
    private static final List<String> TAB_LIST = new ArrayList<>();
    private static int currentTabIndex = 0;

    private TextView tvTabCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ensureInitialTab();
        handleCreateTabIntent();

        ImageButton btnHome = findViewById(R.id.btnHome);
        tvTabCount = findViewById(R.id.tvTabCount);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tvTabCount.setOnClickListener(v -> showTabListDialog());
        updateTabCount();
    }

    private void ensureInitialTab() {
        if (TAB_LIST.isEmpty()) {
            TAB_LIST.add(getString(R.string.default_tab_name, 1));
            currentTabIndex = 0;
        }
    }

    private void handleCreateTabIntent() {
        boolean shouldCreateTab = getIntent().getBooleanExtra(EXTRA_ADD_NEW_TAB, false);
        if (shouldCreateTab) {
            int nextTabNumber = TAB_LIST.size() + 1;
            TAB_LIST.add(getString(R.string.default_tab_name, nextTabNumber));
            currentTabIndex = TAB_LIST.size() - 1;
        }
    }

    private void updateTabCount() {
        tvTabCount.setText(String.valueOf(TAB_LIST.size()));
    }

    private void showTabListDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tab_list);

        ListView lvTabs = dialog.findViewById(R.id.lvTabs);
        ImageButton btnAddTab = dialog.findViewById(R.id.btnAddTab);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TAB_LIST) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(position == currentTabIndex ? Color.BLUE : Color.BLACK);
                return view;
            }
        };

        lvTabs.setAdapter(adapter);
        lvTabs.setOnItemClickListener((parent, view, position, id) -> {
            currentTabIndex = position;
            adapter.notifyDataSetChanged();
        });

        btnAddTab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra(EXTRA_ADD_NEW_TAB, true);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }
}
