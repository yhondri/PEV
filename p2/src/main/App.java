package main;

import crossover.*;
import entities.Configuration;
import entities.Solution;
import helper.ReaderHelper;
import mutation.*;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;
import helper.ResourceHelper;
import selection.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

        String[] mutationAlgorithms = new String[]{"Mutación por Inversión", "Mutación por Intercambio", "Mutación por inserción", "Mutación heurística", "Mutación YI"};
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

        String[] problems = new String[]{"Ajuste", "datos12", "datos15", "datos30"};//, "tai100a", "tai256c"};
        DefaultComboBoxModel problemsModel = new DefaultComboBoxModel(problems);
        problemComboBox.setModel(problemsModel);
    }

    private void initChartPanel() {
        configuration = new Configuration("", readerHelper.getChromosomeSize(), 100, 100, 0.6, 0.05, 0.02, "--");
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
            case 4:
                mutationAlgorithm = new MutacionYI();
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
            case 5:
                crossoverAlgorithm = new CruceYI();
                break;
            default:
                break;
        }

        String[] costesOptimos = new String[]{"50", "224416", "388214", "1818146"};
        String costeOptimo = costesOptimos[problemComboBox.getSelectedIndex()];
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

        InputStream url = new ResourceHelper().getFileInputStream(problemFileName);
        try {
            readerHelper.readFile(url);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "¡Ups ha ocurrido un error al intentar leer el fichero seleccionado!");
        }

        FitnessCalculator fitnessCalculator = new FitnessCalculator(readerHelper.getFlujoMatrix(), readerHelper.getDistanciaMatrix());
        if (mutationAlgorithm instanceof MutacionHeuristica) {
            ((MutacionHeuristica) mutationAlgorithm).setFitnessCalculator(fitnessCalculator);
        }

        configuration = new Configuration(problemFileName, readerHelper.getChromosomeSize(), populationSize, numberOfGenerations, crossoverValue, mutationValue, eliteValue, costeOptimo);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, fitnessCalculator, this);

        initChartPanel();
        geneticAlgorithm.start();
    }

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panelMain.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(14, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        populationSizeLabel = new JLabel();
        populationSizeLabel.setText("Tamaño de población");
        panel1.add(populationSizeLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numberOfGenerationsLabel = new JLabel();
        numberOfGenerationsLabel.setText("Número de generaciones");
        panel1.add(numberOfGenerationsLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectionAlgorithmLabel = new JLabel();
        selectionAlgorithmLabel.setText("Selección");
        panel1.add(selectionAlgorithmLabel, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectionAlgorithmComboBox = new JComboBox();
        panel1.add(selectionAlgorithmComboBox, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        crossoverAlgorithmPanel = new JPanel();
        crossoverAlgorithmPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(crossoverAlgorithmPanel, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        crossoverAlgorithmPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        crossoverAlgorithmPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        crossoverAlgorithmComboBox = new JComboBox();
        panel2.add(crossoverAlgorithmComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Tipo de cruce");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        crossoverAlgorithmPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Crossover %");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        crossoverValueSpinner = new JSpinner();
        panel3.add(crossoverValueSpinner, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mutationPanel = new JPanel();
        mutationPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(mutationPanel, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mutationPanel.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Tipo de mutación");
        panel4.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mutationComboBox = new JComboBox();
        panel4.add(mutationComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        mutationPanel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mutationPanel.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panel5.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Mutación");
        panel5.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mutationPercentSpinner = new JSpinner();
        panel5.add(mutationPercentSpinner, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        elitePanel = new JPanel();
        elitePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(elitePanel, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Élite %");
        elitePanel.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        elitePanel.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        eliteSpinner = new JSpinner();
        elitePanel.add(eliteSpinner, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        resetButton = new JButton();
        resetButton.setText("Reiniciar");
        panel6.add(resetButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        runButton = new JButton();
        runButton.setText("Ejecutar algoritmo");
        panel6.add(runButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        populationSizeSpinner = new JSpinner();
        panel1.add(populationSizeSpinner, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numberOfGenerationsSpinner = new JSpinner();
        panel1.add(numberOfGenerationsSpinner, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Problema");
        panel1.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        problemComboBox = new JComboBox();
        panel1.add(problemComboBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        absoluteBestJTextPane = new JTextPane();
        panel1.add(absoluteBestJTextPane, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout(0, 0));
        panelMain.add(chartPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
