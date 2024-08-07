package src;

import javax.swing.*;
import java.awt.*;


public class ControlPanel extends JPanel {

    private Planet planet;
    private JSlider starCountSlider;
    private JLabel planetNameLabel;

    public ControlPanel(Planet planet) {
        this.planet = planet;

        setLayout(new BorderLayout());


        starCountSlider = new JSlider(0, 100, planet.getMass());
        starCountSlider.setSize(100,10);
        starCountSlider.setMajorTickSpacing(50);
        starCountSlider.setMinorTickSpacing(10);
        starCountSlider.setPaintTicks(true);
        starCountSlider.setPaintLabels(true);


        starCountSlider.addChangeListener(e -> {
            int newMassCount = ((JSlider) e.getSource()).getValue();
            planet.setMass(newMassCount);
        });

        planetNameLabel = new JLabel("Масса объекта '" + planet.getName() + "'", SwingConstants.CENTER);

        add(starCountSlider, BorderLayout.CENTER);
        add(planetNameLabel, BorderLayout.NORTH);
    }
}



