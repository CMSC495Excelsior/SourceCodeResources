import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ReportGUI_SendingPopup {

    public static Scene startPopup(String message){
        // Create Label
        Label confirmLabel = new Label(message);
        confirmLabel.setMaxWidth(Double.MAX_VALUE);
        confirmLabel.setAlignment(Pos.CENTER);
        confirmLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Create Buttons
        Button finishButton = new Button("Finish");

        //Create Action Handlers
        finishButton.setOnAction(event -> ReportGUI_StartPage.getStage().setScene(ReportGUI_EmailPage.startEmailPage(
                "Please choose an option", "Choose your provider")));

        HBox labelBox = new HBox();
        labelBox.getChildren().addAll(confirmLabel, finishButton);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(10,20,10,20));
        labelBox.setSpacing(30);

        // Create new BorderPane to nest layouts inside
        BorderPane borderPane = new BorderPane();
        borderPane.backgroundProperty().setValue(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setCenter(labelBox);

        Scene endScene = new Scene(borderPane);
        //scene.setFill(Color.BEIGE);


        return endScene;
    }
}
