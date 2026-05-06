package amsitlab.android.telusurf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputUrlMain = findViewById(R.id.input_url_main);
        inputUrlMain.setOnEditorActionListener((TextView v, int actionId, android.view.KeyEvent event) -> {
            boolean isEnterKey = event != null
                    && event.getAction() == android.view.KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER;
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_GO
                    || actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE
                    || isEnterKey) {
                handleInput(v.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void handleInput(String input) {
        String trimmedInput = input == null ? "" : input.trim();
        if (trimmedInput.isEmpty()) {
            return;
        }

        Uri targetUri;
        if (looksLikeUrl(trimmedInput)) {
            String normalizedUrl = trimmedInput.matches("^(?i)https?://.*")
                    ? trimmedInput
                    : "https://" + trimmedInput;
            targetUri = Uri.parse(normalizedUrl);
        } else {
            targetUri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(trimmedInput));
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, targetUri);
        startActivity(intent);
    }

    private boolean looksLikeUrl(String input) {
        Uri parsedUri = Uri.parse(input);
        if (parsedUri.getScheme() != null) {
            return parsedUri.isHierarchical() && parsedUri.getHost() != null;
        }

        return android.util.Patterns.DOMAIN_NAME.matcher(input).matches();
    }
}
