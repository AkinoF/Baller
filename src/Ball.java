import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Ball extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создаем ромб с помощью Polygon
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(
                200.0, 100.0,  // верхняя вершина
                300.0, 200.0,  // правая вершина
                200.0, 300.0,  // нижняя вершина
                100.0, 200.0   // левая вершина
        );

        // Устанавливаем цвет ромба
        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK); // Устанавливаем цвет линий
        diamond.setStrokeWidth(2); // Устанавливаем ширину линий

        // Создаем линию, чтобы убрать верхнюю левую линию
        Line line1 = new Line(100.0, 200.0, 200.0, 100.0);
        line1.setStroke(Color.WHITE); // Устанавливаем цвет линии в белый, чтобы скрыть ее
        line1.setStrokeWidth(2); // Устанавливаем ширину линии

        // Создаем панель и добавляем ромб и линию
        Pane pane = new Pane();
        pane.getChildren().addAll(diamond, line1);

        // Создаем сцену и устанавливаем ее на сцену
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Ромб на JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}