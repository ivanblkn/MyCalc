package com.example.mycalc.ui;

import com.example.mycalc.model.Operator;

public interface CalculatorView {
    void showResult(String result);
    void setActiveOperator(Operator operator, boolean active);
}
