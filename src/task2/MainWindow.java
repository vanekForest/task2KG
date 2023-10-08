package task2;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(int width, int height) throws HeadlessException {
        DrawPanel panel = new DrawPanel(width, height, 100);
        this.add(panel);
    }
}