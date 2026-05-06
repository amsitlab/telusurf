package amsitlab.android.telusurf;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
