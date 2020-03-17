import entities.Configuration;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
//        JFrame frame = new JFrame("App");
//        frame.setContentPane(new App().panelMain);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

        Configuration configuration = new Configuration("", 5, 100, 0.6, 0.05, 0.02);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(configuration);
        geneticAlgorithm.run();
    }

    public App() {

    }
}
