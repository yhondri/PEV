package main;

import crossover.CrossoverAlgorithm;
import entities.*;
import helper.Pair;
import helper.Utils;
import mutation.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.*;

public class GeneticProblem extends Thread {
    private Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private MutationAlgorithm mutationAlgorithm;
    private final Random random = new Random();
    private final GeneticAlgorithmDelegate delegate;
    private final CrossoverAlgorithm crossoverAlgorithm;
    //Control del Bloating, k para penalizaión bien fundamentada (página 42 T7).
    private static final double k = 0.25;
    private ControlBloating controlBloating;
    private InitializationMethod initializationMethod;

    public GeneticProblem(Configuration configuration, GeneticAlgorithmDelegate delegate, InitializationMethod initializationMethod, SelectionAlgorithm selectionAlgorithm, MutationAlgorithm mutationAlgorithm, CrossoverAlgorithm crossoverAlgorithm, ControlBloating controlBloating) {
        this.configuration = configuration;
        this.initializationMethod = initializationMethod;
        this.selectionAlgorithm = selectionAlgorithm;
        this.delegate = delegate;
        this.mutationAlgorithm = mutationAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.controlBloating = controlBloating;
    }

    @Override
    public void run() {
        super.run();
        delegate.onStarSearch();

        List<TreeNode> population;
        switch (initializationMethod) {
            case RAMPED_HALF:
                population = rampedAndHalfInitialization();
                break;
            case CRECIENTE:
                population = getInitialPopulationByGrowInitialization();
                break;
            case COMPLETA:
                population = getInitialPopulationByFullInitialization();
                break;
            default:
                throw new RuntimeException("Método no implementado");
        }

        List<Solution> solutions = new ArrayList<>();
        Pair<Solution, List<TreeNode>> result = evaluatePopulation(population, 0, 0);
        Solution solution = result.getKey();
        population = result.getValue();

        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);

        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<TreeNode> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            int numberOfcrossovers = crossPopulation(population);
            int numberOfMutations = mutatePopulation(population);
            addElite(population, eliteList);
            result = evaluatePopulation(population, numberOfcrossovers, numberOfMutations);
            solution = result.getKey();
            population = result.getValue();

            if (!isBetterFitness(solution.getAbsoluteBest(), absBest)) {
                solution.setAbsoluteBest(absBest);
            }
            absBest = solution.getAbsoluteBest();
            solutions.add(solution);
            delegate.didEvaluateGeneration(i, solution);
        }

        delegate.onEndSearch(solution);
        delegate.areButtonsEnabled(true);
    }

    private boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest <= absBest;
    }

    private List<TreeNode> rampedAndHalfInitialization() {
        int treeMaxDepth = configuration.getPopulationSize() / configuration.getMaxDepth();

        List<TreeNode> treeNodeList = new ArrayList<>(configuration.getPopulationSize());
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            int subTreeMaxDepth = (i / treeMaxDepth) + 1;
            TreeNode treeNode;
            if (i % 2 == 0) {
                treeNode = getTreeNodeByFullInitialization(0, subTreeMaxDepth);
            } else {
                treeNode = getTreeNodeByGrowInitialization(0, subTreeMaxDepth);
            }

            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    private List<TreeNode> getInitialPopulationByFullInitialization() {
        int treeMaxDepth = configuration.getPopulationSize() / configuration.getMaxDepth();
        List<TreeNode> treeNodeList = new ArrayList<>(configuration.getPopulationSize());
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            int subTreeMaxDepth = (i / treeMaxDepth) + 1;
            TreeNode treeNode = getTreeNodeByFullInitialization(0, subTreeMaxDepth);
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    /**
     * Tomamos nodos del conjunto de funciones hasta que llegamos a una profundidad máxima.
     * A partir de esta profundidad se sotman los símbolos del conjunto de terminales.
     * @param depth    profundidad del nodo a crear.
     * @param maxDepth profundidad máxima del subárbol.
     * @return Devuelve un nuevo nodo que puede ser un nodo función o terminal.
     */
    private TreeNode getTreeNodeByFullInitialization(int depth, int maxDepth) {
        TreeNode treeNode;
        if (depth < maxDepth) {
            int functionIndex = Utils.getRandom(configuration.getNumberOfFunctions(), 0);
            Pair<String, Integer> functionPair = configuration.getFunctionAtIndex(functionIndex);
            treeNode = new TreeNode(functionPair.getKey(), functionPair.getValue(), maxDepth);
            for (int i = 0; i < functionPair.getValue(); i++) {
                treeNode.addNodeAt(i, getTreeNodeByFullInitialization(depth + 1, maxDepth));
            }
        } else {
            int terminalIndex = Utils.getRandom(configuration.getNumberOfTerminals(), 0);
            String terminal = configuration.getTerminalAtIndex(terminalIndex);
            treeNode = new TreeNode(terminal, configuration.getMaxDepth());
        }
        return treeNode;
    }

    private List<TreeNode> getInitialPopulationByGrowInitialization() {
        int treeMaxDepth = configuration.getPopulationSize() / configuration.getMaxDepth();
        List<TreeNode> treeNodeList = new ArrayList<>(configuration.getPopulationSize());
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            int subTreeMaxDepth = (i / treeMaxDepth) + 1;
            TreeNode treeNode = getTreeNodeByGrowInitialization(0, subTreeMaxDepth);
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    /**
     * Creamos nodos de la función hasta llegar a una profundidad máxima definida. Una vez alcanzada la profundidad máxima
     * solo tomamos los símbolos de la lista de términales.
     * @param depth    Profundidad actual del nodo.
     * @param maxDepth Profundidad máxima del nodo.
     * @return Devuelve un nuevo nodo que puede ser un nodo función o terminal.
     */
    private TreeNode getTreeNodeByGrowInitialization(int depth, int maxDepth) {
        TreeNode treeNode;
        if (depth < maxDepth) {
            int functionIndex = Utils.getRandom(configuration.getNumberOfFunctions() + configuration.getNumberOfTerminals(), 0);
            if (functionIndex >= configuration.getNumberOfFunctions()) {
                String terminal = configuration.getTerminalAtIndex(functionIndex - configuration.getNumberOfFunctions());
                treeNode = new TreeNode(terminal, configuration.getMaxDepth());
            } else {
                Pair<String, Integer> functionPair = configuration.getFunctionAtIndex(functionIndex);
                treeNode = new TreeNode(functionPair.getKey(), functionPair.getValue(), configuration.getMaxDepth());
                //Obtenemos el número de terminales necesarios para la función.
                for (int i = 0; i < functionPair.getValue(); i++) {
                    TreeNode childrenNode = getTreeNodeByGrowInitialization(depth + 1, maxDepth);
                    treeNode.addNodeAt(i, childrenNode);
                }
            }
        } else {
            int terminalIndex = Utils.getRandom(configuration.getNumberOfTerminals(), 0);
            String terminal = configuration.getTerminalAtIndex(terminalIndex);
            treeNode = new TreeNode(terminal, configuration.getMaxDepth());
        }
        return treeNode;
    }

    private Pair<Solution, List<TreeNode>> evaluatePopulation(List<TreeNode> population, int numberOfCrossover, int numberOfMutations) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return new Pair<>(solution, population);
        }

        double totalFitness = 0;
        for (TreeNode treeNode : population) {
            double fitness = evaluateTreeNode(treeNode);
            treeNode.setFitness(fitness);
            totalFitness += fitness;
        }

        //Control bloating
        population = controlBloating(population);

        population = sortPopulation(population);
        TreeNode bestTreeNode = population.get(population.size() - 1);
        solution.setAverageFitness(totalFitness / configuration.getPopulationSize());
        solution.setBestFitness(bestTreeNode.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestTreeNode.getFitness());

        String absoluteBestRepresentation = String.format("Representation: %s\nBestFitness: %s", bestTreeNode.getRepresentation(), bestTreeNode.getFitness());
        solution.setAbsoluteBestRepresentation(absoluteBestRepresentation);

        double acumulatedFitness = 0;
        for (int i = population.size() - 1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness() / totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
            population.get(i).setGrade(population.get(i).getFitness() / totalFitness);
        }

        return new Pair<>(solution, population);
    }

    private List<TreeNode> sortPopulation(List<TreeNode> population) {
        Collections.sort(population, Collections.reverseOrder());
        return population;
    }

    /**
     * Cruza los individuos de una poblacción para producir individuos que combinan característiccas de los progenitores.
     *
     * @param population Los individuos a cruzar.
     * @return Devuelve el número de curces que se han producido.
     */
    private int crossPopulation(List<TreeNode> population) {
        List<Integer> selectedForCrossoverList = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            double crossoverResult = random.nextDouble();
            if (crossoverResult < configuration.getCrossoverValue()) {
                selectedForCrossoverList.add(i);
            }
        }

        if (selectedForCrossoverList.size() % 2 == 1) {
            int randomPosition = random.nextInt(selectedForCrossoverList.size());
            selectedForCrossoverList.remove(randomPosition);
        }

        for (int i = 0; i < selectedForCrossoverList.size(); i += 2) {
            int position1 = selectedForCrossoverList.get(i);
            int position2 = selectedForCrossoverList.get(i + 1);

            TreeNode chromosomeA = population.get(position1);
            TreeNode chromosomeB = population.get(position2);

            Pair<TreeNode, TreeNode> result = crossoverAlgorithm.crossOver(chromosomeA, chromosomeB);

            population.set(position1, result.getKey());
            population.set(position2, result.getValue());
        }

        return selectedForCrossoverList.size();
    }

    /**
     * Ejecuta sobre la población pasada por parámetros el control de bloating seleccionado.
     *
     * @param population Población sobre la que se va a ejecutar el control de bloating.
     * @return Población modificada tras aplicar el control de bloating.
     */
    private List<TreeNode> controlBloating(List<TreeNode> population) {
        double averageFitness = calculateAverageFitness(population);
        double averageSize = calculateAverageSize(population);
        int n = 2;
        if (controlBloating == ControlBloating.METODO_TARPEIAN) {
            return executeTarpeianBloatingControl(population, averageSize, n);
        } else {
            return executePenaltyBloatingControl(population, averageSize, averageFitness);
        }
    }

    /**
     * Aplica el control de bloating por el método Tarpeian sobre una población dada.
     *
     * @param population  Población sobre la que se va a aplicar el control de bloating.
     * @param averageSize Tamaño medio de la población.
     * @param n           Número n necesario para calcular la probabilidad de aplicar el control de bloating.
     * @return Población modificada tras aplicar el control de bloating.
     */
    private List<TreeNode> executeTarpeianBloatingControl(List<TreeNode> population, double averageSize, int n) {
        List<TreeNode> resultPopulation = new ArrayList<>(population.size());

        for (TreeNode treeNode : population) {
            if ((treeNode.getHeight() > averageSize)) {
                if (Utils.random.nextInt() %n == 0) {
                    treeNode.setFitness(Integer.MAX_VALUE);
                }
            }
            resultPopulation.add(treeNode);
        }

        return resultPopulation;
    }

    /**
     * Aplica el control de bloating por el método Penalización bien fundamentada en la población pasada por parámetro.
     *
     * @param population     Población sobre la que se va a aplicar el control de bloating.
     * @param averageSize    Tamaño medio de la población.
     * @param averageFitness Media del fitness de la población pasada por parámetro.
     * @return Población modificada tras aplicar el control de bloating.
     */
    private List<TreeNode> executePenaltyBloatingControl(List<TreeNode> population, double averageSize, double averageFitness) {
        List<TreeNode> resultPopulation = new ArrayList<>(population.size());
        double varianza = 0;
        double covarianza = 0;

        for (TreeNode treeNode : population) {
            varianza += (treeNode.getHeight() - averageSize) * (treeNode.getHeight() - averageSize);
            covarianza += (treeNode.getFitness() - averageFitness) * (treeNode.getHeight() - averageSize);
        }

        varianza /= population.size();
        covarianza /= population.size();
        double k = covarianza / varianza;
        if (k != k) { //Control NaN error
            k = 0;
        }

        for (TreeNode treeNode : population) {
            double newFitness = treeNode.getFitness() - k * treeNode.getHeight();
            treeNode.setFitness(newFitness);
            resultPopulation.add(treeNode);
        }

        return resultPopulation;
    }

    /**
     * Calcula el fitness medio sobre la población pasada por parámetro.
     *
     * @param population Población sobre la que se va a realizar el cálculo.
     * @return El fitness medio de la población.
     */
    private double calculateAverageFitness(List<TreeNode> population) {
        double averageFitness = 0;
        for (TreeNode treeNode : population) {
            averageFitness += treeNode.getFitness();
        }
        return averageFitness;
    }

    /**
     * Calcula el tamaño medio de los individuos la población pasada por parámetro.
     *
     * @param population Población sobre la que se va a realizar el cálculo.
     * @return El tamaño medio de los individuos de la población.
     */
    private double calculateAverageSize(List<TreeNode> population) {
        double averageSize = 0;
        for (TreeNode treeNode : population) {
            averageSize += treeNode.getHeight();
        }
        return (averageSize / population.size());
    }

    private double evaluateTreeNode(TreeNode treeNode) {
        double fitness = treeNode.getHeight() * k;
        if (treeNode.getHeight() == 1) {

        }
        for (TestValue testValue : configuration.getMultiplexorTestValue().getTestValues()) {
            Boolean result = evaluateTreeNode(treeNode, testValue.getValuesMap());
            if (result != testValue.getResult()) {
                fitness += 10;
            }
        }
        return fitness;
    }

    private Boolean evaluateTreeNode(TreeNode treeNode, Map<String, Boolean> values) {
        if (treeNode.isLeaf()) {
            return values.get(treeNode.getKey());
        }
        return evaluateFunctionTreeNode(treeNode, values);
    }

    /**
     * Evalua el nodo pasado por parámetro según su valor.
     * Según la función se recibe uno o dos argumentos.
     * Not -> 1 argumento.
     * AND - OR -> 2 argumentos.
     * IF -> 3 argumentos (XYZ). Primero se evalua X, si X es true, evaluamos Y, si Y es false, evaluamos Z.
     * @param treeNode El nodo a evaluar.
     * @param values   Mapa de los valores del multiplexor contra los que se va a evaluar la función.
     * @return El resultado de la evaluación.
     */
    private Boolean evaluateFunctionTreeNode(TreeNode treeNode, Map<String, Boolean> values) {
        Boolean firstNode = evaluateTreeNode(treeNode.getNodeAtIndex(0), values);
        Function treeNodeFunction = Function.valueOf(treeNode.getKey());
        if (Function.NOT == treeNodeFunction) {
            return !firstNode;
        }

        Boolean secondNode = evaluateTreeNode(treeNode.getNodeAtIndex(1), values);
        if (Function.AND == treeNodeFunction) {
            return firstNode && secondNode;
        }

        if (Function.OR == treeNodeFunction) {
            return firstNode || secondNode;
        }

        Boolean thirdNode = evaluateTreeNode(treeNode.getNodeAtIndex(2), values);
        if (Function.IF == treeNodeFunction) {
            if (firstNode) {
                return secondNode;
            } else {
                return thirdNode;
            }
        }
        return null;
    }

    /**
     * Se aplica una pequeña modificación en uno o mas genes de un individuo.
     *
     * @param population Los individuos a mutar.
     * @return Devuelve el número de mutaciones que se han producido.
     */
    private int mutatePopulation(List<TreeNode> population) {
        int numberOfMutation = 0;
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            TreeNode treeNode;
            double result = Utils.random.nextDouble();
            if (result > configuration.getMutationValue()) {
                treeNode = population.get(i).getCopy();
            } else {
                treeNode = mutationAlgorithm.mutate(population.get(i));
                numberOfMutation += 1;
            }

            population.set(i, treeNode);
        }

        return numberOfMutation;
    }


    private List<TreeNode> getElite(List<TreeNode> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        population = sortPopulation(population);
        List<TreeNode> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 && j < eliteLength; i--, j++) {
            TreeNode newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<TreeNode> population, List<TreeNode> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }
}
