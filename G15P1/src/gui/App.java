package gui;

import entities.Configuration;
import entities.CrossoverAlgorithmType;
import entities.MutationAlgorithmType;
import entities.Solution;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;
import problems.Problem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class App implements Problem.Delegate {
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

    private Plot2DPanel plot2DPanel;
    private LinePlot bestLinePlot;
    private LinePlot averageLinePlot;
    private LinePlot worseLinePlot;
    private LinePlot absoluteBestLinePlot;

    private Configuration configuration;
    private ArrayList<double[]> bestArrayList;
    private ArrayList<double[]> averageArrayList;
    private ArrayList<double[]> worseArrayList;
    private ArrayList<double[]> absoluteBestArrayList;

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
    }

    private void setupView() {
        SpinnerNumberModel populationSpinnerDataModel = new SpinnerNumberModel(100, 20, 1000, 1);
        populationSizeSpinner.setModel(populationSpinnerDataModel);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) populationSizeSpinner.getEditor()).getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.white);

        numberOfGenerationsSpinner.setModel(populationSpinnerDataModel);
        JFormattedTextField numberOfGenerationsSpinnerTextField = ((JSpinner.DefaultEditor) numberOfGenerationsSpinner.getEditor()).getTextField();
        numberOfGenerationsSpinnerTextField.setEditable(false);
        numberOfGenerationsSpinnerTextField.setBackground(Color.white);

        String[] selectionAlgorithms = new String[] {"Ruleta"};
        DefaultComboBoxModel selectionModel = new DefaultComboBoxModel(selectionAlgorithms);
        selectionAlgorithmComboBox.setModel(selectionModel);
        String[] crossoverAlgorithms = new String[] {"Monopunto"};
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
    }

    private void initChartPanel() {
        configuration = new Configuration(100, 100, CrossoverAlgorithmType.SINGLE_POINT_CROSSOVER, 0.6, MutationAlgorithmType.BASIC_MUTATION, 0.05, 0.02);
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

    @Override
    public void didEvaluateGeneration(int generation, Solution solution) {
        SwingUtilities.invokeLater(() -> {
            bestArrayList.add(new double[] {generation, solution.getBestFitness()});
            averageArrayList.add(new double[] {generation, solution.getAverageFitness()});
            worseArrayList.add(new double[] {generation, solution.getWorstFitness()});
            //absoluteBestArrayList.add(new double[] {generation, solution.geta()});
        });
    }
}
