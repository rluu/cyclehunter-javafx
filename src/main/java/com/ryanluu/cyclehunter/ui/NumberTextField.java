package com.ryanluu.cyclehunter.ui;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 * Textfield implementation that accepts formatted number and stores them in a
 * BigDecimal property The user input is formatted when the focus is lost or the
 * user hits RETURN.
 *
 * @author Thomas Bolz, modified and augmented by Ryan Luu.
 */
public class NumberTextField extends TextField {

    private final NumberFormat nf;
    private ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>();

    private ObjectProperty<BigDecimal> minValue = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> maxValue = new SimpleObjectProperty<>();

    public final BigDecimal getNumber() {
        return number.get();
    }

    public final void setNumber(BigDecimal value) {
        number.set(value);
    }

    public ObjectProperty<BigDecimal> numberProperty() {
        return number;
    }

    /**
     * Sets the range that the NumberSpinner can be.
     * @param minValue
     * @param maxValue
     */
    public void setRange(BigDecimal minValue, BigDecimal maxValue) {
        if (minValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("minValue cannot be larger than maxValue.");
        }

        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    public BigDecimal getMinValue() {
        return minValue.get();
    }

    public ObjectProperty<BigDecimal> minValueProperty() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue.set(minValue);
    }

    public BigDecimal getMaxValue() {
        return maxValue.get();
    }

    public ObjectProperty<BigDecimal> maxValueProperty() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue.set(maxValue);
    }

    public NumberTextField() {
        this(BigDecimal.ZERO);
    }

    public NumberTextField(BigDecimal value) {
        this(value, NumberFormat.getInstance());
        initHandlers();
    }

    public NumberTextField(BigDecimal value, NumberFormat nf) {
        super();
        this.nf = nf;
        initHandlers();
        setNumber(value);
    }

    private void initHandlers() {

        // try to parse when focus is lost or RETURN is hit
        setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                parseAndFormatInput();
            }
        });


        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //System.out.println("oldValue == " + oldValue);
                //System.out.println("newValue == " + newValue);
                //System.out.println("getText() == " + getText());

                if (!newValue.booleanValue()) {

                    // Field is blank, use the old number.
                    String input = getText();
                    if (input == null || input.length() == 0) {
                        setText(nf.format(number.get()));
                    }

                    parseAndFormatInput();
                }
            }
        });

        // Set text in field if BigDecimal property is changed from outside.
        numberProperty().addListener(new ChangeListener<BigDecimal>() {

            @Override
            public void changed(ObservableValue<? extends BigDecimal> obserable, BigDecimal oldValue, BigDecimal newValue) {
                setText(nf.format(newValue));
            }
        });
    }

    /**
     * Tries to parse the user input to a number according to the provided
     * NumberFormat
     */
    private void parseAndFormatInput() {
        try {
            String input = getText();
            if (input == null || input.length() == 0) {
                return;
            }
            Number parsedNumber = nf.parse(input);
            BigDecimal newValue = new BigDecimal(parsedNumber.toString());

            BigDecimal minValue = getMinValue();
            BigDecimal maxValue = getMaxValue();

            //System.out.println("newValue == " + newValue);
            //System.out.println("minValue == " + minValue);
            //System.out.println("maxValue == " + maxValue);

            if (minValue != null && newValue.compareTo(minValue) < 0) {
                //System.out.println("New value is below the minimum allowed value.");

                // New value is below the minimum allowed value.
                // Use the minimum.
                setNumber(minValue);
                setText(minValue.toString());
                selectAll();
            }
            else if (maxValue != null && newValue.compareTo(maxValue) > 0) {
                //System.out.println("New value is above the maximum allowed value.");

                // New value is above the maximum allowed value.
                // Use the maximum.
                setNumber(maxValue);
                setText(maxValue.toString());
                selectAll();
            }
            else {
                //System.out.println("Normal use case.");

                setNumber(newValue);
                setText(newValue.toString());
                selectAll();
            }
        } catch (ParseException ex) {
            //System.out.println("ParseException.");

            // If parsing fails keep old number
            setText(nf.format(number.get()));
        }
    }
}
