package com.ryanluu.cyclehunter;

import com.ryanluu.cyclehunter.ui.SettingsPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;


public class App extends Application {

    private static App app;
    private Stage stage;
    private Scene scene;
    private BorderPane root;

    private CycleHunterSettings settings;

    /**
     * Holds the absolute canonical path of the CSV file
     * containing the price bar OHLC information.
     */
    private String priceBarCsvFilename = "";



    @Override
    public void init() {
        // Start up Log4j with the BasicConfigurator.
        BasicConfigurator.configure();

        // Load saved settings from file.
        // Upon failure of loading settings,
        // settings will be null and we should quit.
        settings = CycleHunterSettings.loadSettings();
    }

    @Override
    public void start(Stage primaryStage) {

        // Settings can be null if initialization failed.
        // Only continue to start the application if
        // settings is okay.

        if (settings != null) {
            primaryStage.setTitle("Cycle Hunter");

            Label label = new Label("Hello World.");

            BorderPane root = new BorderPane();

            SettingsPane settingsPane = new SettingsPane(settings);

            root.setLeft(settingsPane);

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();

//            Dialogs.create()
//                    .owner(stage)
//                    .title("Information Dialog")
//                    .masthead("Look, an Information Dialog")
//                    .message("I have a great message for you!")
//                    .showInformation();
        }
    }

    @Override
    public void stop() {

        if (settings != null) {
            // Save settings.
            settings.saveSettings();
        }
    }


    public static void main( String[] args )
    {
        Application.launch(args);
    }
}
