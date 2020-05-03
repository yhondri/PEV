package main;

import crossover.CrossoverAlgorithm;
import crossover.OperadorDeCruce;
import entities.Configuration;
import entities.MultiplexorTestValue;
import entities.MultiplexorTestValueSixInputs;
import entities.Solution;
import javafx.util.Pair;
import mutation.MutacionTerminalSimple;
import mutation.MutationAlgorithm;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;
import selection.RouletteSelection;
import selection.SelectionAlgorithm;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App implements GeneticAlgorithmDelegate {
    //region UI
    private JPanel panelMain;
    private JButton resetButton;
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
    private JTextPane absoluteBestJTextPane;
    private JCheckBox useIfFunctionCheckbox;
    private JSpinner maxDepthSpinner;
    //endregion UI

    private List<Pair<String, Integer>> functions;
    private List<String> terminalList;
    private Configuration configuration;
    private Plot2DPanel plot2DPanel;
    private LinePlot bestLinePlot;
    private LinePlot averageLinePlot;
    private LinePlot worseLinePlot;
    private LinePlot absoluteBestLinePlot;
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
        initData();
        setupView();
        initChartPanel();
        setupListeners();
    }

    private void initData() {
        functions = new ArrayList<>();
        functions.add(new Pair<>("AND", 2));
        functions.add(new Pair<>("OR", 2));
        functions.add(new Pair<>("NOT", 2));

        terminalList = new ArrayList<>(6);
        terminalList.add("A0");
        terminalList.add("A1");
        terminalList.add("D0");
        terminalList.add("D1");
        terminalList.add("D2");
        terminalList.add("D3");
    }

    private void setupView() {
        SpinnerNumberModel maxDepthDataModel = new SpinnerNumberModel(3, 1, 100, 1);
        maxDepthSpinner.setModel(maxDepthDataModel);

        SpinnerNumberModel populationSpinnerDataModel = new SpinnerNumberModel(100, 20, 10000, 1);
        populationSizeSpinner.setModel(populationSpinnerDataModel);
        SpinnerNumberModel numberOfGenerationsSpinnerDataModel = new SpinnerNumberModel(100, 20, 10000, 1);
        numberOfGenerationsSpinner.setModel(numberOfGenerationsSpinnerDataModel);

        String[] selectionAlgorithms = new String[]{"Ruleta", "Torneo", "E. Universal", "Truncamiento", "Restos"};
        DefaultComboBoxModel selectionModel = new DefaultComboBoxModel(selectionAlgorithms);
        selectionAlgorithmComboBox.setModel(selectionModel);
        String[] crossoverAlgorithms = new String[]{"PMX", "Cruce por orden (OX)", "Cruce por ciclos (CX)", "Cruce por recombinacion de rutas", "Cruce por codificación ordinal", "Cruce YI"};
        DefaultComboBoxModel crossoverModel = new DefaultComboBoxModel(crossoverAlgorithms);
        crossoverAlgorithmComboBox.setModel(crossoverModel);
        SpinnerNumberModel crossoverSpinnerDataModel = new SpinnerNumberModel(0.6, 0.0, 100.0, 0.01);
        crossoverValueSpinner.setModel(crossoverSpinnerDataModel);
        JFormattedTextField crossoverSpinnerTextField = ((JSpinner.DefaultEditor) crossoverValueSpinner.getEditor()).getTextField();
        crossoverSpinnerTextField.setEditable(false);
        crossoverSpinnerTextField.setBackground(Color.white);

        String[] mutationAlgorithms = new String[]{"Mutación terminal simple"};
        DefaultComboBoxModel mutationModel = new DefaultComboBoxModel(mutationAlgorithms);
        mutationComboBox.setModel(mutationModel);
        SpinnerNumberModel mutationSpinnerDataModel = new SpinnerNumberModel(0.05, 0.0, 1, 0.01);
        mutationPercentSpinner.setModel(mutationSpinnerDataModel);
        JFormattedTextField mutationSpinnerTextField = ((JSpinner.DefaultEditor) mutationPercentSpinner.getEditor()).getTextField();
        mutationSpinnerTextField.setEditable(false);
        mutationSpinnerTextField.setBackground(Color.white);

        SpinnerNumberModel eliteSpinnerDataModel = new SpinnerNumberModel(0.02, 0.0, 1, 0.01);
        eliteSpinner.setModel(eliteSpinnerDataModel);
        JFormattedTextField eliteSpinnerTextField = ((JSpinner.DefaultEditor) eliteSpinner.getEditor()).getTextField();
        eliteSpinnerTextField.setEditable(false);
        eliteSpinnerTextField.setBackground(Color.white);
    }

    private void initChartPanel() {
        configuration = new Configuration(100, 100);
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
        int maxDepth = (int) maxDepthSpinner.getValue();

        SelectionAlgorithm selectionAlgorithm = null;
        switch (selectionAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                selectionAlgorithm = new RouletteSelection();
                break;
            case 1:
//                selectionAlgorithm = new TournamentSelection();
                break;
            case 2:
//                selectionAlgorithm = new UniversalStochastic();
                break;
            case 3:
//                selectionAlgorithm = new TruncationSelection();
                break;
            case 4:
//                selectionAlgorithm = new RemainsSelection();
                break;
            default:
                break;
        }

        MutationAlgorithm mutationAlgorithm = null;
        switch (mutationComboBox.getSelectedIndex()) {
            case 0:
                mutationAlgorithm = new MutacionTerminalSimple(terminalList);
                break;
            case 1:
//                mutationAlgorithm = new MutacionPorIntercambio();
                break;
            case 2:
//                mutationAlgorithm = new MutacionPorInsercion();
                break;
            case 3:
//                mutationAlgorithm = new MutacionHeuristica();
                break;
            case 4:
//                mutationAlgorithm = new MutacionYI();
                break;
            default:
                break;
        }

        CrossoverAlgorithm crossoverAlgorithm = new OperadorDeCruce(crossoverValue);
//        switch (crossoverAlgorithmComboBox.getSelectedIndex()) {
//            case 0:
////                crossoverAlgorithm = new PMXCrossover();
//                break;
//            case 1:
////                crossoverAlgorithm = new CrucePorOrdenOX();
//                break;
//            case 2:
////                crossoverAlgorithm = new CrucePorCiclosCX();
//                break;
//            case 3:
////                crossoverAlgorithm = new CrucePorRecombinacionDeRutas();
//                break;
//            case 4:
////                crossoverAlgorithm = new CrucePorCodificacionOrdinal();
//                break;
//            case 5:
////                crossoverAlgorithm = new CruceYI();
//                break;
//            default:
//                break;
//        }

        MultiplexorTestValue multiplexorTestValue = new MultiplexorTestValueSixInputs();

        if (useIfFunctionCheckbox.isSelected()) {
            functions.add(new Pair<>("IF", 3));
        }

        configuration = new Configuration(functions, terminalList,0, populationSize, numberOfGenerations, crossoverValue, mutationValue, eliteValue, maxDepth, multiplexorTestValue);
        GeneticProblem geneticAlgorithm = new GeneticProblem(configuration, this, selectionAlgorithm, mutationAlgorithm, crossoverAlgorithm);

        initChartPanel();
        geneticAlgorithm.start();
    }

    // region GeneticAlgorithmDelegate

    @Override
    public void onStarSearch() {
        SwingUtilities.invokeLater(() -> {
            absoluteBestJTextPane.setText("Cargando...");
        });
    }

    @Override
    public void didEvaluateGeneration(int generation, Solution solution) {
        SwingUtilities.invokeLater(() -> {
            bestArrayList.add(new double[]{generation, solution.getBestFitness()});
            averageArrayList.add(new double[]{generation, solution.getAverageFitness()});
            worseArrayList.add(new double[]{generation, solution.getWorstFitness()});
            absoluteBestArrayList.add(new double[]{generation, solution.getAbsoluteBest()});

            plot2DPanel.changePlotData(0, bestArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.changePlotData(1, averageArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.changePlotData(2, worseArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.changePlotData(3, absoluteBestArrayList.toArray(new double[generation + 1][]));
            plot2DPanel.setFixedBounds(0, 0, generation);
        });
    }

    @Override
    public void areButtonsEnabled(boolean enabled) {
        runButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
    }

    @Override
    public void onEndSearch(Solution solution) {
        SwingUtilities.invokeLater(() -> {
            absoluteBestJTextPane.setText(solution.getAbsoluteBestRepresentation());
        });
    }

    // endregion GeneticAlgorithmDelegate
}
