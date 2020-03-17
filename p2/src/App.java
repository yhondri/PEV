import crossover.CrossoverAlgorithm;
import crossover.UniformBinaryCrossover;
import entities.Configuration;
import helper.ReaderHelper;
import selection.RouletteSelection;
import selection.SelectionAlgorithm;

public class App {

    public static void main(String[] args) {
//        JFrame frame = new JFrame("App");
//        frame.setContentPane(new App().panelMain);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

        ReaderHelper readerHelper = new ReaderHelper();
        readerHelper.readFile("ajuste.txt");

        Configuration configuration = new Configuration("", readerHelper.getChromosomeSize(), 100, 100, 0.6, 0.05, 0.02);
        SelectionAlgorithm selectionAlgorithm = new RouletteSelection();
        CrossoverAlgorithm crossoverAlgorithm = new UniformBinaryCrossover(configuration.getCrossoverValue());
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(configuration, selectionAlgorithm, crossoverAlgorithm, readerHelper.getFlujoMatrix(), readerHelper.getDistanciaMatrix());
        geneticAlgorithm.run();
    }

    public App() {

    }

}
