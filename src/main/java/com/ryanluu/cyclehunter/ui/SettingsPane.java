package com.ryanluu.cyclehunter.ui;

import com.ryanluu.cyclehunter.model.CycleHunterSettings;
import com.ryanluu.cyclehunter.model.LookbackMultiple;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Created by rluu on 11/9/14.
 */
public class SettingsPane extends Pane {
    Logger logger = Logger.getLogger(SettingsPane.class);

    private CycleHunterSettings settings;

    public SettingsPane() {
        // Just create our own CycleHunterSettings object that is generic.
        // We won't do much until it is set again.
        settings = new CycleHunterSettings();

        logger.debug("Got inside the SettingsPane() default constructor.");

    }

    public SettingsPane(CycleHunterSettings s) {
        this.settings = s;

        logger.debug("Got inside the SettingsPane(CycleHunterSettings s) constructor.");

        Label label = new Label("Settings");
        //title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button applyButton = new Button("Apply");

        HBox settingsTitleHBox = new HBox();
        settingsTitleHBox.getChildren().add(label);
        settingsTitleHBox.getChildren().add(applyButton);

        // Planets.
        TitledPane fileTitledPane = createFileTitledPane();
        TitledPane planetsTitledPane = createPlanetsTitledPane();
        TitledPane integerLookbackMultiplesTitledPane = createIntegerLookBackMultiplesTitledPane();
        TitledPane geometricLookbackMultiplesTitledPane = createGeometricLookBackMultiplesTitledPane();


        Accordion accordian = new Accordion();
        accordian.getPanes().add(fileTitledPane);
        accordian.getPanes().add(planetsTitledPane);
        accordian.getPanes().add(integerLookbackMultiplesTitledPane);
        accordian.getPanes().add(geometricLookbackMultiplesTitledPane);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().add(settingsTitleHBox);
        mainLayout.getChildren().add(accordian);

        getChildren().add(mainLayout);
    }

    public void applyCycleHunterSettings(CycleHunterSettings s) {
        settings = s;

        // TODO: Make the settings object consistent with the settings in the widgets.

        // TODO:  then notify the parent that new settings have been set, so that everything else can be redrawn.

    }

    /**
     * Method that is called whenever any of the settings widgets is modified.
     */
    private void handleSettingsChanged() {
        logger.debug("inside handleSettingsChanged()");

        // Make the Apply button an color that shows
        // that the application state is stale, and a refresh is needed.
        // TODO:  add implementation here.
    }

    private TitledPane createFileTitledPane() {
        // Get the filename from settings.
        String oldFilename = settings.getLastOpenedCsvFilename();


        // This is the layout that the widgets will get put into.
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);

        TextField textField = new TextField(oldFilename);
        textField.setOnAction(e -> {
            handleSettingsChanged();
        });

        Button button = new Button("Browse");
        button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open PriceBar CSV file");
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                String newFilename = file.getAbsolutePath();
                if (!newFilename.equals(oldFilename)) {
                    textField.setText(newFilename);
                    handleSettingsChanged();
                }
            }
        });

        hBox.getChildren().add(button);
        vBox.getChildren().add(textField);
        vBox.getChildren().add(hBox);

        // Return value to be sent back.
        TitledPane titledPane = new TitledPane("CSV File", vBox);

        return titledPane;
    }

    private TitledPane createPlanetsTitledPane() {

        // Get the map from settings, which we will use to
        // extract the planets that should be in our list,
        // and whether a particular one is selected or not.
        Map<String, Boolean> planetNamesEnabledMap =
                settings.getPlanetNamesEnabledMap();

        // Create a toggle group.
        ToggleGroup group = new ToggleGroup();

        // This is the layout that the Radio Buttons will go into.
        VBox layout = new VBox();

        // Create the radio buttons.  Add them to the toggle group as we go.
        for (Map.Entry<String, Boolean> entry : planetNamesEnabledMap.entrySet()) {
            String planetName = entry.getKey();
            Boolean enabled = entry.getValue();

            RadioButton rb = new RadioButton(planetName);
            rb.setToggleGroup(group);
            rb.setSelected(enabled);
            rb.setOnAction(e -> handleSettingsChanged());

            // TODO: Here, maybe set an image for each planet?

            layout.getChildren().add(rb);
        }

        // Return value to be sent back.
        TitledPane titledPane = new TitledPane("Planets", layout);

        return titledPane;
    }

    private TitledPane createIntegerLookBackMultiplesTitledPane() {

        // Get the map from settings, which we will use to
        // extract the lookback periods that should be in
        // our list, and whether a particular one is selected or not.
        Map<LookbackMultiple, Boolean> integerLookbackMultiplesEnabledMap =
                settings.getIntegerLookbackMultiplesEnabledMap();

        // This is the layout that all the entries will go into.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        int rowIndex = 0;

        // Create entries for each row within the layout.
        for (Map.Entry<LookbackMultiple, Boolean> entry :
                integerLookbackMultiplesEnabledMap.entrySet()) {
            LookbackMultiple lm = entry.getKey();
            Boolean enabled = entry.getValue();

            //CheckBox cb = new CheckBox(lm.getName());
            //cb.setTooltip(new Tooltip(lm.getName()));
            CheckBox cb = new CheckBox();
            cb.setSelected(enabled);
            cb.setOnAction(e -> handleSettingsChanged());

            NumberTextField ntf = new NumberTextField();
            ntf.setMinValue(new BigDecimal("1"));
            ntf.setMaxWidth(70);
            ntf.setNumber(lm.getLookbackMultiple());
            ntf.numberProperty().addListener(listener -> handleSettingsChanged());

            ColorPicker cp = new ColorPicker(Color.DARKGRAY);
            cp.setOnAction(e -> handleSettingsChanged());

            gridPane.add(cb, 0, rowIndex);
            gridPane.add(ntf, 1, rowIndex);
            gridPane.add(cp, 2, rowIndex);

            rowIndex += 1;
        }

        // Return value to be sent back.
        TitledPane titledPane =
                new TitledPane("Lookback multiples", gridPane);

        return titledPane;
    }

    private TitledPane createGeometricLookBackMultiplesTitledPane() {

        // Get the map from settings, which we will use to
        // extract the lookback periods that should be in
        // our list, and whether a particular one is selected or not.
        Map<LookbackMultiple, Boolean> geometricLookbackMultiplesEnabledMap =
                settings.getGeometricLookbackMultiplesEnabledMap();

        // This is the layout that all the entries will go into.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);

        int rowIndex = 0;


        // Create entries for each row within the layout.
        for (Map.Entry<LookbackMultiple, Boolean> entry :
                geometricLookbackMultiplesEnabledMap.entrySet()) {
            LookbackMultiple lm = entry.getKey();
            Boolean enabled = entry.getValue();

            // Get the lookback multiple rounded to 3 significant digits.
            BigDecimal scaled =
                    lm.getLookbackMultiple().setScale(3, RoundingMode.HALF_UP);

            CheckBox cb = new CheckBox(scaled.toString());
            cb.setTooltip(new Tooltip(lm.getName()));
            cb.setSelected(enabled);
            cb.setOnAction(e -> handleSettingsChanged());

            ColorPicker cp = new ColorPicker(Color.DARKGRAY);
            cp.setOnAction(e -> handleSettingsChanged());

            gridPane.add(cb, 0, rowIndex);
            gridPane.add(cp, 1, rowIndex);

            rowIndex += 1;
        }

        // Return value to be sent back.
        TitledPane titledPane =
                new TitledPane("Lookback multiples (geometric)", gridPane);

        return titledPane;
    }
}
