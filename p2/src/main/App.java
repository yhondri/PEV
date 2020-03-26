package main;

import crossover.*;
import entities.Configuration;
import entities.Solution;
import helper.ReaderHelper;
import mutation.*;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;
import selection.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class App implements GeneticAlgorithmDelegate {
    private JButton resetButton;
    private JPanel panelMain;
    private JLabel numberOfGenerationsLabel;
    private JLabel populationSizeLabel;
    private JComboBox selectionAlgorithmComboBox;
    private JLabel selectionAlgorithmLabel;
    private JComboBox crossoverAlgorithmComboBox;
    private JPanel crossoverAlgorithmPanel;
    private JComboBox mutationComboBox;
    private JPanel mutationPanel;
    private JPanel elitePanel;
    private JButton runButton;
    private JPanel chartPanel;
    private JSpinner populationSizeSpinner;
    private JSpinner numberOfGenerationsSpinner;
    private JSpinner crossoverValueSpinner;
    private JSpinner mutationPercentSpinner;
    private JSpinner eliteSpinner;
    private JComboBox problemComboBox;
    private JTextPane absoluteBestJTextPane;

    private Plot2DPanel plot2DPanel;
    private LinePlot bestLinePlot;
    private LinePlot averageLinePlot;
    private LinePlot worseLinePlot;
    private LinePlot absoluteBestLinePlot;

    private final ReaderHelper readerHelper = new ReaderHelper();
    private Configuration configuration;
    private boolean isFloatProblem = false;
    private ArrayList<double[]> bestArrayList;
    private ArrayList<double[]> averageArrayList;
    private ArrayList<double[]> worseArrayList;
    private ArrayList<double[]> absoluteBestArrayList;

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public App() {
        setupView();
        initChartPanel();
        setupListeners();
    }

    private void setupView() {
        SpinnerNumberModel populationSpinnerDataModel = new SpinnerNumberModel(100, 20, 1000, 1);
        populationSizeSpinner.setModel(populationSpinnerDataModel);
        SpinnerNumberModel numberOfGenerationsSpinnerDataModel = new SpinnerNumberModel(100, 20, 1000, 1);
        numberOfGenerationsSpinner.setModel(numberOfGenerationsSpinnerDataModel);

        String[] selectionAlgorithms = new String[]{"Ruleta", "Torneo", "E. Universal", "Truncamiento", "Restos"};
        DefaultComboBoxModel selectionModel = new DefaultComboBoxModel(selectionAlgorithms);
        selectionAlgorithmComboBox.setModel(selectionModel);
        String[] crossoverAlgorithms = new String[]{"PMX", "Cruce por orden (OX)", "Cruce por ciclos (CX)", "Cruce por recombinacion de rutas", "Cruce por codificación ordinal"};
        DefaultComboBoxModel crossoverModel = new DefaultComboBoxModel(crossoverAlgorithms);
        crossoverAlgorithmComboBox.setModel(crossoverModel);
        SpinnerNumberModel crossoverSpinnerDataModel = new SpinnerNumberModel(0.6, 0.0, 100.0, 0.01);
        crossoverValueSpinner.setModel(crossoverSpinnerDataModel);
        JFormattedTextField crossoverSpinnerTextField = ((JSpinner.DefaultEditor) crossoverValueSpinner.getEditor()).getTextField();
        crossoverSpinnerTextField.setEditable(false);
        crossoverSpinnerTextField.setBackground(Color.white);

        String[] mutationAlgorithms = new String[] {"Mutación por Inversión", "Mutación por Intercambio", "Mutación por inserción", "Mutación heurística"};
        DefaultComboBoxModel mutationModel = new DefaultComboBoxModel(mutationAlgorithms);
        mutationComboBox.setModel(mutationModel);
        SpinnerNumberModel mutationSpinnerDataModel = new SpinnerNumberModel(0.05, 0.0, 100.0, 0.01);
        mutationPercentSpinner.setModel(mutationSpinnerDataModel);
        JFormattedTextField mutationSpinnerTextField = ((JSpinner.DefaultEditor) mutationPercentSpinner.getEditor()).getTextField();
        mutationSpinnerTextField.setEditable(false);
        mutationSpinnerTextField.setBackground(Color.white);

        SpinnerNumberModel eliteSpinnerDataModel = new SpinnerNumberModel(0.02, 0.0, 100.0, 0.01);
        eliteSpinner.setModel(eliteSpinnerDataModel);
        JFormattedTextField eliteSpinnerTextField = ((JSpinner.DefaultEditor) eliteSpinner.getEditor()).getTextField();
        eliteSpinnerTextField.setEditable(false);
        eliteSpinnerTextField.setBackground(Color.white);

        String[] problems = new String[]{"Ajuste", "datos12", "datos15", "datos30", "tai100a", "tai256c"};
        DefaultComboBoxModel problemsModel = new DefaultComboBoxModel(problems);
        problemComboBox.setModel(problemsModel);
    }

    private void initChartPanel() {
        configuration = new Configuration("", readerHelper.getChromosomeSize(), 100, 100, 0.6, 0.05, 0.02);
        bestArrayList = new ArrayList<>(configuration.getNumberOfGenerations());
        bestLinePlot = new LinePlot("Best", Color.red, new double[][]{{0, 0}});
        averageArrayList = new ArrayList<>(configuration.getNumberOfGenerations());
        averageLinePlot = new LinePlot("Average", Color.green, new double[][]{{0, 0}});
        worseArrayList = new ArrayList<>(configuration.getNumberOfGenerations());
        worseLinePlot = new LinePlot("Worse", Color.yellow, new double[][]{{0, 0}});
        absoluteBestArrayList = new ArrayList<>(configuration.getNumberOfGenerations());
        absoluteBestLinePlot = new LinePlot("Absolute best", Color.blue, new double[][]{{0, 0}});

        plot2DPanel = new Plot2DPanel();
        plot2DPanel.addLegend("SOUTH");
        plot2DPanel.setAxisLabel(0, "Generaciones");
        plot2DPanel.setAxisLabel(1, "Fitness");
        plot2DPanel.addPlot(bestLinePlot);
        plot2DPanel.addPlot(averageLinePlot);
        plot2DPanel.addPlot(worseLinePlot);
        plot2DPanel.addPlot(absoluteBestLinePlot);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1;
        chartPanel.add(plot2DPanel, SwingConstants.CENTER);
    }

    private void setupListeners() {
        runButton.addActionListener(e -> {
            setupAlgorithm();
        });

        resetButton.addActionListener(e -> {
            this.initChartPanel();
            this.plot2DPanel.doLayout();
        });
    }

    private void setupAlgorithm() {
        int populationSize = (int) populationSizeSpinner.getValue();
        int numberOfGenerations = (int) numberOfGenerationsSpinner.getValue();
        double crossoverValue = (double) crossoverValueSpinner.getValue();
        double mutationValue = (double) mutationPercentSpinner.getValue();
        double eliteValue = (double) eliteSpinner.getValue();

        SelectionAlgorithm selectionAlgorithm = null;
        switch (selectionAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                selectionAlgorithm = new RouletteSelection();
                break;
            case 1:
                selectionAlgorithm = new TournamentSelection();
                break;
            case 2:
                selectionAlgorithm = new UniversalStochastic();
                break;
            case 3:
                selectionAlgorithm = new TruncationSelection();
                break;
            case 4:
                selectionAlgorithm = new RemainsSelection();
                break;
            default:
                break;
        }

        MutationAlgorithm mutationAlgorithm = null;
        switch (mutationComboBox.getSelectedIndex()) {
            case 0:
                mutationAlgorithm = new MutacionPorInversion();
                break;
            case 1:
                mutationAlgorithm = new MutacionPorIntercambio();
                break;
            case 2:
                mutationAlgorithm = new MutacionPorInsercion();
                break;
            case 3:
                mutationAlgorithm = new MutacionHeuristica();
                break;
            default:
                break;
        }

        CrossoverAlgorithm crossoverAlgorithm = null;
        switch (crossoverAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                crossoverAlgorithm = new PMXCrossover();
                break;
            case 1:
                crossoverAlgorithm = new CrucePorOrdenOX();
                break;
            case 2:
                crossoverAlgorithm = new CrucePorCiclosCX();
                break;
            case 3:
                crossoverAlgorithm = new CrucePorRecombinacionDeRutas();
                break;
            case 4:
                crossoverAlgorithm = new CrucePorCodificacionOrdinal();
                break;
            default:
                break;
        }

        String problemFileName = null;
        switch (problemComboBox.getSelectedIndex()) {
            case 0:
                problemFileName = "ajuste.txt";
                break;
            case 1:
                problemFileName = "datos12.txt";
                break;
            case 2:
                problemFileName = "datos15.txt";
                break;
            case 3:
                problemFileName = "datos30.txt";
                break;
            case 4:
                problemFileName = "tai100a.txt";
                break;
            case 5:
                problemFileName = "tai256c.txt";
                break;
            default:
                break;
        }

        readerHelper.readFile(problemFileName);
        FitnessCalculator fitnessCalculator = new FitnessCalculator(readerHelper.getFlujoMatrix(), readerHelper.getDistanciaMatrix());
        if (mutationAlgorithm instanceof MutacionHeuristica) {
           ((MutacionHeuristica)mutationAlgorithm).setFitnessCalculator(fitnessCalculator);
        }

        configuration = new Configuration(problemFileName, readerHelper.getChromosomeSize(), populationSize, numberOfGenerations, crossoverValue, mutationValue, eliteValue);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, fitnessCalculator, this);

        initChartPanel();
        geneticAlgorithm.start();
    }

    @Override
    public void didEvaluateGeneration(int generation, Solution solution) {
        SwingUtilities.invokeLater(() -> {
            absoluteBestJTextPane.setText(solution.getAbsoluteBestRepresentation());
            bestArrayList.add(new double[]{generation, solution.getBestFitness()});
            averageArrayList.add(new double[]{generation, solution.getAverageFitness()});
            worseArrayList.add(new double[]{generation, solution.getWorstFitness()});
            absoluteBestArrayList.add(new double[]{generation, solution.getAbsoluteBest()});

            plot2DPanel.changePlotData(0, bestArrayList.toArray(new double[generation+1][]));
            plot2DPanel.changePlotData(1, averageArrayList.toArray(new double[generation+1][]));
            plot2DPanel.changePlotData(2, worseArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.changePlotData(3, absoluteBestArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.setFixedBounds(0, 0, generation);
        });
    }
}
