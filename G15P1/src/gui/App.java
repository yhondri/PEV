package gui;

import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import crossoveralgorithm.binaryCrossover.SinglePointBinaryCrossover;
import crossoveralgorithm.binaryCrossover.UniformBinaryCrossover;
import crossoveralgorithm.floatCrossover.ArithmeticFloatCrossover;
import crossoveralgorithm.floatCrossover.FloatCrossoverAlgorithm;
import crossoveralgorithm.floatCrossover.SinglePointFloatCrossover;
import crossoveralgorithm.floatCrossover.UniformFloatCrossover;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.binaryMutation.BasicBinaryMutation;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import mutationalgorithm.floatMutation.FloatMutationAlgorithm;
import mutationalgorithm.floatMutation.UniformFloatMutation;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;
import problems.*;
import problems.binaryProblems.Problem1;
import problems.binaryProblems.Problem2;
import problems.binaryProblems.Problem3;
import problems.binaryProblems.Problem4;
import problems.floatProblems.Problem4Float;
import selection.RouletteSelection;
import selection.SelectionAlgorithm;
import selection.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("ALL")
public class App implements BinaryProblem.Delegate, FloatProblem.Delegate {
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
    private JSpinner nValueSpinner;
    private JTextPane absoluteBestJTextPane;

    private Plot2DPanel plot2DPanel;
    private LinePlot bestLinePlot;
    private LinePlot averageLinePlot;
    private LinePlot worseLinePlot;
    private LinePlot absoluteBestLinePlot;

    private Configuration configuration;
    private boolean isFloatProblem = false;
    private ArrayList<double[]> bestArrayList;
    private ArrayList<double[]> averageArrayList;
    private ArrayList<double[]> worseArrayList;
    private ArrayList<double[]> absoluteBestArrayList;

    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        String[] selectionAlgorithms = new String[] {"Ruleta", "Est. Universal", "Torneo", "Truncamiento"};
        DefaultComboBoxModel selectionModel = new DefaultComboBoxModel(selectionAlgorithms);
        selectionAlgorithmComboBox.setModel(selectionModel);
        String[] crossoverAlgorithms = new String[]{"Monopunto", "Uniforme"};
        DefaultComboBoxModel crossoverModel = new DefaultComboBoxModel(crossoverAlgorithms);
        crossoverAlgorithmComboBox.setModel(crossoverModel);
        SpinnerNumberModel crossoverSpinnerDataModel = new SpinnerNumberModel(0.6, 0.0, 100.0, 0.01);
        crossoverValueSpinner.setModel(crossoverSpinnerDataModel);
        JFormattedTextField crossoverSpinnerTextField = ((JSpinner.DefaultEditor) crossoverValueSpinner.getEditor()).getTextField();
        crossoverSpinnerTextField.setEditable(false);
        crossoverSpinnerTextField.setBackground(Color.white);

        String[] mutationAlgorithms = new String[] {"Basic mutation"};
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

        String[] problems = new String[]{"Problema 1", "Problema 2", "Problema 3", "Problema 4", "Problema 4 (R)"};
        DefaultComboBoxModel problemsModel = new DefaultComboBoxModel(problems);
        problemComboBox.setModel(problemsModel);

        SpinnerNumberModel nValueSpinnerDataModel = new SpinnerNumberModel(1, 1, 7, 1);
        nValueSpinner.setModel(nValueSpinnerDataModel);
        JFormattedTextField nValueSpinnerTextField = ((JSpinner.DefaultEditor) nValueSpinner.getEditor()).getTextField();
        nValueSpinnerTextField.setEditable(false);
        nValueSpinnerTextField.setBackground(Color.white);
        nValueSpinner.setEnabled(false);
    }

    private void initChartPanel() {
        configuration = new Configuration(100, 100, 0.6,0.05, 0.02, 1);
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
        problemComboBox.addActionListener (e -> {
            isFloatProblem = problemComboBox.getSelectedIndex() == 4;
            boolean nValueEnabled = (problemComboBox.getSelectedIndex() == 3 || isFloatProblem);
            nValueSpinner.setEnabled(nValueEnabled);

            if (!nValueEnabled) {
                nValueSpinner.setValue(1);
            }
            if (isFloatProblem) {
                DefaultComboBoxModel floatCrossover = new DefaultComboBoxModel(new String[]{"Monopunto", "Discreto Uniforme", "Aritmético"});
                crossoverAlgorithmComboBox.setModel(floatCrossover);
                DefaultComboBoxModel floatMutation = new DefaultComboBoxModel(new String[]{"Uniforme"});
                mutationComboBox.setModel(floatMutation);
            } else {
                DefaultComboBoxModel floatCrossover = new DefaultComboBoxModel(new String[]{"Monopunto", "Uniforme"});
                crossoverAlgorithmComboBox.setModel(floatCrossover);
                DefaultComboBoxModel floatMutation = new DefaultComboBoxModel(new String[]{"Basic mutation"});
                mutationComboBox.setModel(floatMutation);
            }
        });

        runButton.addActionListener(e -> {
            if (!isFloatProblem)
                this.setupBinaryAlgorithm();
            else
                this.setupFloatAlgorithm();
        });

        resetButton.addActionListener(e -> {
            this.initChartPanel();
            this.plot2DPanel.doLayout();
        });
    }

    private void setupBinaryAlgorithm() {
        int populationSize = (int) populationSizeSpinner.getValue();
        int numberOfGenerations = (int) numberOfGenerationsSpinner.getValue();
        double crossoverValue = (double) crossoverValueSpinner.getValue();
        double mutationValue = (double) mutationPercentSpinner.getValue();
        double eliteValue = (double) eliteSpinner.getValue();
        int nValue = (int) nValueSpinner.getValue();

        SelectionAlgorithm selectionAlgorithm = null;
        switch (selectionAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                selectionAlgorithm = new RouletteSelection();
                break;
            case 1:
                selectionAlgorithm = new UniversalStochastic();
                break;
            case 2:
                selectionAlgorithm = new TournamentSelection();
                break;
            case 3:
                selectionAlgorithm = new TruncationSelection();
                break;
            default:
                break;
        }

        BinaryCrossoverAlgorithm binaryCrossoverAlgorithm = null;
        switch (crossoverAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                binaryCrossoverAlgorithm = new SinglePointBinaryCrossover(random);
                break;
            case 1:
                binaryCrossoverAlgorithm = new UniformBinaryCrossover(0.5);
                break;
            default:
                break;
        }

        BinaryMutationAlgorithm mutationAlgorithm = null;
        switch (mutationComboBox.getSelectedIndex()) {
            case 0:
                mutationAlgorithm = new BasicBinaryMutation();
            default:
                break;
        }

        Configuration configuration = new Configuration(populationSize, numberOfGenerations, crossoverValue, mutationValue, eliteValue, nValue);

        BinaryProblem problem = null;
        switch (problemComboBox.getSelectedIndex()) {
            case 0:
                problem = new Problem1(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, this);
                break;
            case 1:
                problem = new Problem2(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, this);
                break;
            case 2:
                problem = new Problem3(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, this);
                break;
            case 3:
                problem = new Problem4(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, this);
                break;
            default:
                break;
        }
        initChartPanel();
        plot2DPanel.layout();
        problem.start();
    }

    private void setupFloatAlgorithm() {
        int populationSize = (int) populationSizeSpinner.getValue();
        int numberOfGenerations = (int) numberOfGenerationsSpinner.getValue();
        double crossoverValue = (double) crossoverValueSpinner.getValue();
        double mutationValue = (double) mutationPercentSpinner.getValue();
        double eliteValue = (double) eliteSpinner.getValue();
        int nValue = (int) nValueSpinner.getValue();

        SelectionAlgorithm selectionAlgorithm = null;
        switch (selectionAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                selectionAlgorithm = new RouletteSelection();
                break;
            case 1:
                selectionAlgorithm = new UniversalStochastic();
                break;
            case 2:
                selectionAlgorithm = new TournamentSelection();
                break;
            case 3:
                selectionAlgorithm = new TruncationSelection();
                break;
            default:
                break;
        }

        FloatCrossoverAlgorithm floatCrossoverAlgorithm = null;
        switch (crossoverAlgorithmComboBox.getSelectedIndex()) {
            case 0:
                floatCrossoverAlgorithm = new SinglePointFloatCrossover(random);
                break;
            case 1:
                floatCrossoverAlgorithm = new UniformFloatCrossover(0.5);
                break;
            case 2:
                floatCrossoverAlgorithm = new ArithmeticFloatCrossover(0.5);
                break;
            default:
                break;
        }

        FloatMutationAlgorithm mutationAlgorithm = null;
        switch (mutationComboBox.getSelectedIndex()) {
            case 0:
                mutationAlgorithm = new UniformFloatMutation();
            default:
                break;
        }

        Configuration configuration = new Configuration(populationSize, numberOfGenerations, crossoverValue, mutationValue, eliteValue, nValue);

        FloatProblem problem = new Problem4Float(configuration, selectionAlgorithm, floatCrossoverAlgorithm, mutationAlgorithm, this);
        initChartPanel();
        plot2DPanel.layout();
        problem.start();
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
