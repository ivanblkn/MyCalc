package com.example.mycalc.ui;

import android.os.Bundle;

import com.example.mycalc.model.Calculator;
import com.example.mycalc.model.Operator;

import java.text.DecimalFormat;

public class CalculatorPresenter {
    private CalculatorView view;
    private Calculator calculator;
    DecimalFormat myFormat = new DecimalFormat("###########.#########");

    private double prevArg;
    private double currentArg;
    private Operator selectedOperator;
    private int dotCounter;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
        dotCounter = -1;

    }

    public CalculatorPresenter(CalculatorView view, Calculator calculator, Bundle inState) {
        this.view = view;
        this.calculator = calculator;
    }

    public void onDigitPressed(int digit) {
        if (dotCounter<0) {
            currentArg = currentArg * 10 + digit;
        } else {
            currentArg = currentArg + digit/Math.pow(10,++dotCounter);
        }

        showFormatted(currentArg);
    }

    public void onOperatorPressed(Operator operator) {
        if (selectedOperator == null) {
            if (prevArg == 0) prevArg = currentArg;

        } else {
            prevArg = calculator.perform(prevArg, currentArg, selectedOperator);
            showValue(prevArg);
            view.setActiveOperator(selectedOperator, false);
        }
        currentArg = 0;
        dotCounter = -1;
        selectedOperator = operator;
        view.setActiveOperator(selectedOperator, true);
    }

    public void onDotPressed() {
        if (dotCounter < 0) dotCounter = 0;
        showFormatted(currentArg);
    }

    public void onBSPressed() {
        if (dotCounter<0) {
            currentArg = Math.floor(currentArg / 10);
        } else if (dotCounter == 0) {
            dotCounter--;
        } else {
            double scale = Math.pow(10, --dotCounter);
            currentArg = Math.floor(currentArg*scale) / scale;
        }
        showFormatted(currentArg);
    }

    public void onCPressed() {
        selectedOperator = null;
        dotCounter = -1;
        currentArg = 0;
        prevArg = 0;
        showFormatted(currentArg);
    }

    public void onEqualsPressed() {
        if (selectedOperator != null) {
            prevArg = calculator.perform(prevArg, currentArg, selectedOperator);
            showValue(prevArg);
            view.setActiveOperator(selectedOperator, false);
        }
        currentArg = 0;
        dotCounter = -1;
        selectedOperator = null;
    }

    public void showFormatted(double value) {
        if (dotCounter < 0) {
            view.showResult(String.format("%.0f", value));
        } else if (dotCounter == 0) {
            view.showResult(String.format("%.1f", value));
        } else {
            view.showResult(String.format("%."+ String.valueOf(dotCounter)+ "f",value));
        }
    }

    public void showValue(double value) {
        if (value == 0) {
            view.showResult("");
        } else {
            String s = myFormat.format(value);
            if (s.length() > 11) {
                view.showResult(myFormat.format(value).substring(0, 11));
            } else {
                view.showResult(myFormat.format(value));
            }
        }
    }

    public void saveState(Bundle outState) {
        outState.putDouble("prevArg",    prevArg);
        outState.putDouble("currentArg", currentArg);
        if (selectedOperator == null) {
            outState.putInt("selectedOperator", -1);
        } else {
            outState.putInt("selectedOperator", selectedOperator.ordinal());
        }
        outState.putInt("dotCounter", dotCounter);
    }

    public void restoreState(Bundle inState) {
        prevArg = inState.getDouble("prevArg");
        currentArg = inState.getDouble("currentArg");
        int sO = inState.getInt("selectedOperator");
        if (sO == -1) {
            selectedOperator = null;
        } else {
            selectedOperator = Operator.values()[sO];
            view.setActiveOperator(selectedOperator, true);
        }
        dotCounter = inState.getInt("dotCounter");
        showValue(currentArg);
    }
}
