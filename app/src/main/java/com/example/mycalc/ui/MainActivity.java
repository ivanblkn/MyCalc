package com.example.mycalc.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mycalc.R;
import com.example.mycalc.model.CalculatorImpl;
import com.example.mycalc.model.Operator;
import com.example.mycalc.model.Theme;
import com.example.mycalc.model.ThemeRepository;
import com.example.mycalc.model.ThemeRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalculatorView {

    private TextView resultTxt;

    private CalculatorPresenter presenter;

    private ThemeRepository themeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeRepository = ThemeRepositoryImpl.getInstance(this);

        setTheme(themeRepository.getSavedTheme().getThemeRes());

        setContentView(R.layout.activity_main);

        resultTxt = findViewById(R.id.numberField);
        presenter = new CalculatorPresenter(this, new CalculatorImpl());

        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState);
        }

        Map<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.n1, 1);
        digits.put(R.id.n2, 2);
        digits.put(R.id.n3, 3);
        digits.put(R.id.n4, 4);
        digits.put(R.id.n5, 5);
        digits.put(R.id.n6, 6);
        digits.put(R.id.n7, 7);
        digits.put(R.id.n8, 8);
        digits.put(R.id.n9, 9);
        digits.put(R.id.n0, 0);

        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDigitPressed(digits.get(view.getId()));
            }
        };

        findViewById(R.id.n1).setOnClickListener(digitClickListener);
        findViewById(R.id.n2).setOnClickListener(digitClickListener);
        findViewById(R.id.n3).setOnClickListener(digitClickListener);
        findViewById(R.id.n4).setOnClickListener(digitClickListener);
        findViewById(R.id.n5).setOnClickListener(digitClickListener);
        findViewById(R.id.n6).setOnClickListener(digitClickListener);
        findViewById(R.id.n7).setOnClickListener(digitClickListener);
        findViewById(R.id.n8).setOnClickListener(digitClickListener);
        findViewById(R.id.n9).setOnClickListener(digitClickListener);
        findViewById(R.id.n0).setOnClickListener(digitClickListener);

        Map<Integer, Operator> operators = new HashMap<>();
        operators.put(R.id.plus, Operator.ADD);
        operators.put(R.id.minus, Operator.SUB);
        operators.put(R.id.mult, Operator.MULT);
        operators.put(R.id.divide, Operator.DIV);

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOperatorPressed(operators.get(view.getId()));
                view.setBackgroundResource(R.color.activeBackground);
            }
        };

        findViewById(R.id.plus).setOnClickListener(operatorClickListener);
        findViewById(R.id.minus).setOnClickListener(operatorClickListener);
        findViewById(R.id.mult).setOnClickListener(operatorClickListener);
        findViewById(R.id.divide).setOnClickListener(operatorClickListener);

        findViewById(R.id.dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDotPressed();
            }
        });

        findViewById(R.id.bs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBSPressed();
            }
        });
        findViewById(R.id.key_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCPressed();
            }
        });
        findViewById(R.id.equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEqualsPressed();
            }
        });

        ActivityResultLauncher<Intent> themeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();

                    Theme selectedTheme = (Theme)intent.getSerializableExtra(SelectThemeActivity.EXTRA_THEM);

                    themeRepository.saveTheme(selectedTheme);

                    recreate();
                }
            }
        });
        findViewById(R.id.btn_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
                intent.putExtra(SelectThemeActivity.EXTRA_THEM, themeRepository.getSavedTheme());
                themeLauncher.launch(intent);
            }
        });
    }

    @Override
    public void showResult(String result) {
        resultTxt.setText(result);
    }

    @Override
    public void setActiveOperator(Operator operator, boolean active) {
        View view;
        switch (operator) {
            case ADD:
                view = findViewById(R.id.plus);
                break;
            case SUB:
                view = findViewById(R.id.minus);
                break;
            case MULT:
                view = findViewById(R.id.mult);
                break;
            case DIV:
                view = findViewById(R.id.divide);
                break;
            default:
                view = null;
        }
        if (active) {
            view.setBackgroundResource(R.color.activeBackground);
        } else {
            view.setBackgroundResource(R.color.backgroundOfNumbers);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        presenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.restoreState(savedInstanceState);
    }
}