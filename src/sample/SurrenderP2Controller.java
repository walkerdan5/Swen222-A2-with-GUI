package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by danielwalker on 3/09/17.
 */
public class SurrenderP2Controller {

    Scene playScene;

    @FXML
    public void menuClicked(ActionEvent event) throws IOException {
        Parent playGameParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        playScene = new Scene(playGameParent, 800, 500);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.hide();
        gameStage.setScene(playScene);
        gameStage.show();
    }
}
