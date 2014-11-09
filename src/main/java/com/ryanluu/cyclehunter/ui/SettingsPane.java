package com.ryanluu.cyclehunter.ui;

import com.ryanluu.cyclehunter.CycleHunterSettings;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
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

        Text title = new Text("Settings");
        //title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button applyButton = new Button("Apply");

        HBox settingsTitleHBox = new HBox();
        settingsTitleHBox.getChildren().add(title);
        settingsTitleHBox.getChildren().add(applyButton);



        // Planets.

        TitledPane planetChooser = createPlanetsTitledPane();

        Accordion accordian = new Accordion();
        accordian.getPanes().addAll(planetChooser);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().add(settingsTitleHBox);
        mainLayout.getChildren().add(accordian);

        getChildren().add(mainLayout);
    }

    public void applyCycleHunterSettings(CycleHunterSettings s) {
        settings = s;
        // TODO:  Do stuff.
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
            // TODO: Here, maybe set an image for each planet?

            layout.getChildren().add(rb);
        }

        // Return value to be sent back.
        TitledPane titledPane = new TitledPane("Planets", layout);

        return titledPane;
    }


}
