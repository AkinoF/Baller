import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Ball extends Application {

    private double ballX = 200; // Начальная позиция шара по X
    private double ballY = 50; // Начальная позиция шара по Y (над ромбом)
    private double ballRadius = 15; // Радиус шара
    private double ballSpeedY = 2; // Скорость шара по Y (только вниз)

    @Override
    public void start(Stage primaryStage) {
        // Создаем ромб
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(200.0, 100.0, 300.0, 200.0, 200.0, 300.0, 100.0, 200.0);

        // Устанавливаем цвет ромба
        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK); // Устанавливаем цвет линий
        diamond.setStrokeWidth(2); // Устанавливаем ширину линий

        // Создаем линию, чтобы убрать верхнюю левую линию
        Line line1 = new Line(100.0, 200.0, 200.0, 100.0);
        line1.setStroke(Color.WHITE); // Устанавливаем цвет линии в белый, чтобы скрыть ее
        line1.setStrokeWidth(2); // Устанавливаем ширину линии

        // Создаем шар
        Circle ball = new Circle(ballX, ballY, ballRadius);
        ball.setFill(Color.RED);

        // Создаем панель и добавляем ромб, линию и шар
        Pane pane = new Pane();
        pane.getChildren().addAll(diamond, line1, ball);

        // Создаем сцену
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Baller");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Анимация шара
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Обновляем позицию шара
                ballY += ballSpeedY;

                // Проверка на столкновение с границами окна
                if (ballY + ballRadius > scene.getHeight()) {
                    ballY = scene.getHeight() - ballRadius; // Останавливаем шар на нижней границе
                    ballSpeedY = -ballSpeedY; // Отскок от нижней границы
                }

                // Проверка на столкновение с ромбом
                if (diamond.contains(ballX, ballY + ballRadius)) {
                    // Если шар касается ромба, инвертируем направление движения
                    ballSpeedY = -ballSpeedY;
                    ballY = 200 - ballRadius; // Устанавливаем шар на уровень ромба
                }

                // Устанавливаем новую позицию шара
                ball.setCenterX(ballX);
                ball.setCenterY(ballY);
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}