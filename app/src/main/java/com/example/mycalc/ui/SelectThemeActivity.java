package com.example.mycalc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycalc.R;
import com.example.mycalc.model.CalculatorImpl;
import com.example.mycalc.model.Operator;
import com.example.mycalc.model.Theme;
import com.example.mycalc.model.ThemeRepository;
import com.example.mycalc.model.ThemeRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectThemeActivity extends AppCompatActivity {

    public static final String EXTRA_THEM = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        List<Theme> themes = ThemeRepositoryImpl.getInstance(this).getAll();

        LinearLayout container = findViewById(R.id.container);

        Intent intent = getIntent();

        Theme selectedThem = (Theme) intent.getSerializableExtra(EXTRA_THEM);

        for (Theme theme : themes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_theme,container, false);

            TextView title = itemView.findViewById(R.id.title);
            title.setText(theme.getTitle());

            CardView cardView = itemView.findViewById(R.id.theme_card);

            ImageView checked = itemView.findViewById(R.id.checked);
            ImageView unchecked = itemView.findViewById(R.id.unchecked);

            if (theme.equals(selectedThem)) {
                checked.setVisibility(View.VISIBLE);
                unchecked.setVisibility(View.GONE);
            } else {
                unchecked.setVisibility(View.VISIBLE);
                checked.setVisibility(View.GONE);
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent data = new Intent();
                    data.putExtra(EXTRA_THEM, theme);
                    setResult(Activity.RESULT_OK, data);

                    finish();

                    Toast.makeText(SelectThemeActivity.this, theme.getTitle(), Toast.LENGTH_LONG).show();
                }
            });
            container.addView(itemView);
        }

    }
}