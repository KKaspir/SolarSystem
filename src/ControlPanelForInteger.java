package src;

import javax.swing.*;
import java.awt.*;

public class ControlPanelForInteger extends JPanel {
    private int x; // Объявляем переменную x как член класса
    private JSlider delaySlider;
    private JLabel delayLabel;

    public ControlPanelForInteger(int initialValue, String desc){
        x = initialValue;
        setLayout(new BorderLayout());

        delaySlider = new JSlider(JSlider.HORIZONTAL, 1, 100, x);
        delaySlider.setSize(100,10);
        delaySlider.setMinorTickSpacing(10);
        delaySlider.setMajorTickSpacing(99);
        delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);

        delaySlider.addChangeListener(e -> {
            int newX = ((JSlider) e.getSource()).getValue();
            x = newX; // Обновляем значение переменной x
        });

        delayLabel = new JLabel("        " + desc);

        add(delaySlider, BorderLayout.CENTER);
        add(delayLabel, BorderLayout.NORTH);
    }

    public int getXValue() {
        return x; // Добавляем метод для получения текущего значения x
    }
}

