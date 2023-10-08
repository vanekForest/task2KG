package task2;

import java.awt.*;

public class CircleArc {

    // Определение переменных для координат центра окружности, радиуса, начального и конечного углов и цветов
    private final int centerX, centerY, radius;
    private final int startAngle, endAngle;
    private final Color startColor, endColor;

    // Конструктор класса CircleArc
    public CircleArc(int centerX, int centerY, int radius, int startAngle, int endAngle, Color startColor, Color endColor) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    // Метод для рисования дуги окружности
    public void draw(Graphics2D g) {
        int x = radius;
        int y = 0;
        int p = 1 - radius;

        // Отображение начальной точки окружности
        if (isAngleInRange(180)) {
            putPixel(g, x + centerX, -y + centerY, computeColor(0));
            putPixel(g, -x + centerX, -y + centerY, computeColor(180));
        }

        // Если радиус больше 0, то рисуем две дополнительные точки
        if (radius > 0) {
            if (isAngleInRange(270)) {
                putPixel(g, y + centerX, x + centerY, computeColor(90));
                putPixel(g, -y + centerX, x + centerY, computeColor(270));
            }
        }

        // Алгоритм Брезенхема для рисования окружности
        while (x >= y) {
            y++;

            // Вычисление параметра решения p
            if (p <= 0) {
                p = p + 2 * y + 1;
            } else {
                x--;
                p = p + 2 * y - 2 * x + 1;
            }

            // Рисуем точки в 4 квадрантах
            checkAndDrawPixel(g, x + centerX, y + centerY);   // 1-й квадрант
            checkAndDrawPixel(g, -x + centerX, y + centerY);  // 2-й квадрант
            checkAndDrawPixel(g, -x + centerX, -y + centerY); // 3-й квадрант
            checkAndDrawPixel(g, x + centerX, -y + centerY);  // 4-й квадрант

            // Рисуем дополнительные точки, если x не равно y
            if (x != y) {
                checkAndDrawPixel(g, y + centerX, x + centerY);
                checkAndDrawPixel(g, -y + centerX, x + centerY);
                checkAndDrawPixel(g, -y + centerX, -x + centerY);
                checkAndDrawPixel(g, y + centerX, -x + centerY);
            }
        }
    }

    // Метод для проверки и рисования пикселя на основе угла
    private void checkAndDrawPixel(Graphics2D g, int x, int y) {
        int angle = normalizeAngle((int) Math.toDegrees(Math.atan2(centerY - y, x - centerX)));
        if (isAngleInRange(angle)) {
            putPixel(g, x, y, computeColor(angle));
        }
    }

    private boolean isAngleInRange(int angle) {
        return angle >= startAngle && angle <= endAngle + 1;
    }


    // Метод для нормализации угла в диапазоне [0, 360)
    private int normalizeAngle(int angle) {
        if (angle < 0) angle = 360 + angle;  // преобразование отрицательных углов в соответствующие положительные углы
        return angle % 360;
    }


    // Метод для рисования пикселя с заданным цветом
    private void putPixel(Graphics2D g, int x, int y, Color color) {
        g.setColor(color);
        g.fillOval(x, y, 20, 20);
    }

    // Метод для вычисления цвета пикселя на основе угла
    private Color computeColor(int angle) {
        float fraction = (float) (angle - startAngle) / (endAngle - startAngle);
        int redStart = startColor.getRed();
        int greenStart = startColor.getGreen();
        int blueStart = startColor.getBlue();
        int redEnd = endColor.getRed();
        int greenEnd = endColor.getGreen();
        int blueEnd = endColor.getBlue();

        int red = (int) (redStart + fraction * (redEnd - redStart));
        int green = (int) (greenStart + fraction * (greenEnd - greenStart));
        int blue = (int) (blueStart + fraction * (blueEnd - blueStart));

        // Обеспечиваем, чтобы цветовые значения находились в диапазоне [0, 255]
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return new Color(red, green, blue);
    }
}
