package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.*;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


public class Main extends Application implements EventHandler {
    Stage primaryStage;
    final int step = 10;
    final int MAX_HEIGHT = 800;
    final int MAX_WIDTH = 800;
    final int MIN_HEIGHT = 500;
    final int MIN_WIDTH = 500;

    int width = MIN_WIDTH;
    int height = MIN_HEIGHT;
    //stage size
    Button btnAdd = new Button("Size++");
    Button btnSub = new Button("Size--");
    int counter = 0;
    Label lbl = new Label(String.valueOf(counter));
    //factorial
    Label lblFactorialName = new Label("Calculate Factorial");
    Button btnFactorial = new Button("Calculate");
    TextField txtFactorial = new TextField();
    Label lblFactorialResult = new Label("Factorial =");
    //fibonacci
    Label lblFiboName = new Label("Fibonaci sequence");
    TextField txtFiboLimit = new TextField();
    Button btnFibo = new Button("Generate Fibonacci sequence ");
    Label lblFiboResult = new Label();
    //uppercase abc
    Label lblupAbc = new Label();
    Label lblCba = new Label("text");


    @Override
    public void handle(Event e) {
        if (e.getSource() == btnAdd) {
            width = Math.min(MAX_WIDTH, width + step);
            height = Math.min(MAX_HEIGHT, height + step);
            counter++;
            lbl.setText(String.valueOf(counter));
        } else if (e.getSource() == btnSub) {
            width = Math.max(MIN_WIDTH, width - step);
            height = Math.max(MIN_HEIGHT, height - step);
            counter++;
            lbl.setText(String.valueOf(counter));
        } else
            System.out.println("Nierozpoznana kontrolka.");
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        btnAdd.setOnAction(this);
        btnSub.setOnAction(this);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        btnFactorial.setOnAction(actionEvent -> {
            try {
                int number = Integer.parseInt(txtFactorial.getText());
                txtFactorial.setStyle("-fx-control-inner-background: #FEFEFE;");
                BigInteger result = BigInteger.ONE;
                if (number < 20) {
                    lblFactorialResult.setText("Factorial = " + String.valueOf(BigInteger.valueOf(
                            LongStream.range(1, number + 1)
                                    .reduce((previous, current) -> previous * current)
                                    .getAsLong()
                    )));
                } else {
                    for (int i = 1; i <= number; i++) {
                        result = result.multiply(BigInteger.valueOf(i));
                    }
                    lblFactorialResult.setText("Factorial = " + String.valueOf(result));
                }
            }catch(NumberFormatException e){
                txtFactorial.setStyle("-fx-control-inner-background: #ff726f;");
                lblFactorialResult.setText("incorrect input!");
            }

        });

        btnFibo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    int limit = Integer.parseInt(txtFiboLimit.getText());
                    txtFiboLimit.setStyle("-fx-control-inner-background: #FEFEFE;");
                    String collect = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                            .limit(limit)                                   //number to which the sequece is generated
                            .map(t -> t[0])                                 //we only need to display first element of a pair
                            .map(String::valueOf)                           // convert to string
                            .collect(Collectors.joining(", "));     // collect numbers separated with comma
                    lblFiboResult.setText(collect);
                }catch(NumberFormatException e){
                    txtFiboLimit.setStyle("-fx-control-inner-background: #ff726f;");
                    lblFiboResult.setText("incorrect input!");
                }
            }
        });

        VBox vboxMain = new VBox(btnAdd, btnSub, lbl, lblFactorialName,
                txtFactorial, btnFactorial, lblFactorialResult, lblFiboName, txtFiboLimit, btnFibo,
                lblFiboResult, lblupAbc, lblCba);
        vboxMain.setPadding(new Insets(20));
        vboxMain.setAlignment(Pos.CENTER);
        vboxMain.setSpacing(10);
        vboxMain.setBorder(new Border(new BorderStroke(Color.rgb(133, 170, 197), BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(20))));
        vboxMain.setBackground(new Background(new BackgroundFill(Color.rgb(239, 208, 145), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vboxMain);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("( ͡° ͜ʖ ͡°)");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setResizable(false);
        primaryStage.show();


        txtFactorial.setPromptText("input value");
        txtFiboLimit.setPromptText("input limit value");


        lblupAbc.setText(String.valueOf(
                IntStream.rangeClosed('a', 'z')
                        .mapToObj(c -> " " + (char) c)
                        .collect(Collectors.joining())
                        .toCharArray()).toUpperCase());
        lblCba.setText(String.valueOf(
                IntStream.rangeClosed('a', 'z')
                        .mapToObj(c -> " " + (char) c)
                        .sorted(Collections.reverseOrder())
                        .collect(Collectors.joining())
                        .toCharArray()).toUpperCase());
    }

    private void closeProgram(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("You see this butons? They work as intended");
        alert.setContentText("Are you sure, you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            primaryStage.close();
        } else {
            alert.close();
        }
    }
}
