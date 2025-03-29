import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip; // Импортируем AudioClip
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File; // Импортируем File для работы с файлами

public class Ball extends Application {

    private double ballX = 200; // Начальная позиция шара по X
    private double ballY = 50; // Начальная позиция шара по Y
    private double ballRadius = 15; // Радиус шара
    private double ballSpeedY = 0; // Начальная скорость шара по Y
    private double ballSpeedX = 0; // Начальная скорость шара по X
    private boolean isFalling = false; // Флаг, указывающий, падает ли шар

    // Линии ромба
    private Line topLine;
    private Line rightLine;
    private Line bottomLine;
    private Line leftLine;

    // Звуковой эффект
    private AudioClip soundEffect;

    @Override
    public void start(Stage primaryStage) {
        // Загружаем звуковой файл
        soundEffect = new AudioClip(new File("path/to/your/sound.wav").toURI().toString());

        // Создаем линии ромба
        topLine = new Line(250.0, 150.0, 350.0, 250.0);
        rightLine = new Line(350.0, 250.0, 250.0, 350.0);
        bottomLine = new Line(250.0, 350.0, 150.0, 250.0);
        leftLine = new Line(150.0, 250.0, 250.0, 150.0);

        // Устанавливаем цвет линий
        topLine.setStroke(Color.BLACK);
        rightLine.setStroke(Color.BLACK);
        bottomLine.setStroke(Color.BLACK);
        leftLine.setStroke(Color.BLACK);

        // Создаем шар
        Circle ball = new Circle(ballX, ballY, ballRadius);
        ball.setFill(Color.RED);

        // Создаем кнопку для запуска анимации
        Button startButton = new Button("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(350);
        startButton.setOnAction(e -> {
            isFalling = true; // Устанавливаем флаг, чтобы начать падение
            ballSpeedY = 2; // Устанавливаем скорость падения
            soundEffect.play(); // Воспроизводим звуковой эффект
        });

        // Создаем панель и добавляем линии, шар и кнопку
        Pane pane = new Pane();
        pane.getChildren().addAll(topLine, rightLine, bottomLine, leftLine, ball, startButton);

        // Создаем сцену
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Baller");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Анимация шара
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isFalling) {
                    // Обновляем позицию шара
                    ballY += ballSpeedY;

                    // Проверка на столкновение с границами окна
                    if (ballY + ballRadius > scene.getHeight()) {
                        ballY = 0; // Перемещаем шар в верхнюю часть экрана
                        ballX = 30; // Устанавливаем шар недалеко от левой границы
                        ballSpeedX = -2; // Устанавливаем скорость движения влево
                    }

                    // Проверка на столкновение с линиями ромба
                    if (isCollidingWithLine(topLine)) {
                        ballSpeedY = -ballSpeedY; // Отскок от верхней линии
                        ballY = 150 - ballRadius; // Устанавливаем шар на уровень верхней линии
                    } else if (isCollidingWithLine(rightLine)) {
                        ballSpeedY = -ballSpeedY; // Отскок от правой линии
                        ballY = 350 + ballRadius; // Устанавливаем шар ниже правой линии
                    } else if (isCollidingWithLine(bottomLine)) {
                        ballSpeedY = -ballSpeedY; // Отскок от нижней линии
                        ballY = 350 + ballRadius; // Устанавливаем шар ниже нижней линии
                    }

                    // Если шар движется влево, обновляем его позицию
                    if (ballX < 0) {
                        // Завершаем программу, если шар выходит за пределы экрана
                        System.exit(0);
                    } else {
                        ballX += ballSpeedX; // Двигаем шар влево
                    }

                    // Устанавливаем новую позицию шара
                    ball.setCenterX(ballX);
                    ball.setCenterY(ballY);
                }
            }

            // Метод для проверки столкновения шара с линией
            private boolean isCollidingWithLine(Line line) {
                double lineY1 = line.getStartY(); // Получаем Y координату начала линии
                double lineY2 = line.getEndY(); // Получаем Y координату конца линии
                double lineX1 = line.getStartX(); // Получаем X координату начала линии
                double lineX2 = line.getEndX(); // Получаем X координату конца линии

                // Проверяем, находится ли шар в пределах X-координат линии
                boolean isWithinX = ballX + ballRadius >= Math.min(lineX1, lineX2) && ballX - ballRadius <= Math.max(lineX1, lineX2);

                // Проверяем, находится ли шар на уровне линии
                boolean isAtLineY = (ballY + ballRadius >= Math.min(lineY1, lineY2) && ballY - ballRadius <= Math.max(lineY1, lineY2));

                return isWithinX && isAtLineY; // Возвращаем true, если шар касается линии
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}