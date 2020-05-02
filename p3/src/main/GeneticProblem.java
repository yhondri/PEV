package main;

import entities.*;
import helper.Utils;
import javafx.util.Pair;
import mutation.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.*;

public class GeneticProblem extends Thread {
    private Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private MutationAlgorithm mutationAlgorithm;
    private final Random random = new Random();
    private final GeneticAlgorithmDelegate delegate;

    public GeneticProblem(Configuration configuration, GeneticAlgorithmDelegate delegate, SelectionAlgorithm selectionAlgorithm, MutationAlgorithm mutationAlgorithm) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.delegate = delegate;
        this.mutationAlgorithm = mutationAlgorithm;
    }

    @Override
    public void run() {
        super.run();
        List<TreeNode<String>> population = rampedAndHalfInitialization();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population, 0, 0);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);

        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<TreeNode<String>> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            int numberOfcrossovers = 0; //TODO: crossPopulation(population);
            int numberOfMutations = mutatePopulation(population);
            addElite(population, eliteList);
            solution = evaluatePopulation(population, numberOfcrossovers, numberOfMutations);
            if (!isBetterFitness(solution.getAbsoluteBest(), absBest)) {
                solution.setAbsoluteBest(absBest);
            }
            absBest = solution.getAbsoluteBest();
            solutions.add(solution);
            delegate.didEvaluateGeneration(i, solution);
        }

        delegate.onEndSearch(solution);
        delegate.areButtonsEnabled(true);

        int i = 0;
        i += 2;
    }

    private boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest <= absBest;
    }

    private List<TreeNode<String>> rampedAndHalfInitialization() {
        int treeMaxDepth = configuration.getPopulationSize()/configuration.getMaxDepth();

        List<TreeNode<String>> treeNodeList = new ArrayList<>(configuration.getPopulationSize());
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            int subTreeMaxDepth = (i/treeMaxDepth)+1;
            TreeNode<String> treeNode;
            if (i%2 == 0) {
                treeNode = getTreeNodeByFullInitialization(0, subTreeMaxDepth);
            } else {
                treeNode = getTreeNodeByGrowInitialization(0, subTreeMaxDepth);
            }

            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    /**
     * Tomamos nodos del conjunto de funciones hasta que llegamos a una profundidad máxima.
     * A partir de esta profundidad se sotman los símbolos del conjunto de terminales.
     * @param depth profundidad del nodo a crear.
     * @param maxDepth profundidad máxima del subárbol.
     * @return Devuelve un nuevo nodo que puede ser un nodo función o terminal.
     */
    private TreeNode<String> getTreeNodeByFullInitialization(int depth, int maxDepth) {
        TreeNode<String> treeNode;
        if (depth < maxDepth) {
            int functionIndex = Utils.getRandom(configuration.getNumberOfFunctions(),0);
            Pair<String, Integer> functionPair = configuration.getFunctionAtIndex(functionIndex);
            treeNode = new TreeNode<>(functionPair.getKey(), functionPair.getValue(), maxDepth);
            for (int i = 0; i < functionPair.getValue(); i++) {
                treeNode.addNodeAt(i, getTreeNodeByFullInitialization(depth + 1, maxDepth));
            }
        } else {
            int terminalIndex = Utils.getRandom(configuration.getNumberOfTerminals(),0);
            String terminal = configuration.getTerminalAtIndex(terminalIndex);
            treeNode = new TreeNode<>(terminal, maxDepth);
        }
        return treeNode;
    }

    /**
     * Creamos nodos de la función hasta llegar a una profundidad máxima definida. Una vez alcanzada la profundidad máxima
     * solo tomamos los símbolos de la lista de términales.
     * @param depth Profundidad actual del nodo.
     * @param maxDepth Profundidad máxima del nodo.
     * @return Devuelve un nuevo nodo que puede ser un nodo función o terminal.
     */
    private TreeNode<String> getTreeNodeByGrowInitialization(int depth, int maxDepth) {
        TreeNode<String> treeNode;
        if (depth < maxDepth) {
            int functionIndex = Utils.getRandom(configuration.getNumberOfFunctions() + configuration.getNumberOfTerminals(),0);
            if (functionIndex >= configuration.getNumberOfFunctions()) {
                String terminal = configuration.getTerminalAtIndex(functionIndex - configuration.getNumberOfFunctions());
                treeNode = new TreeNode<>(terminal, configuration.getMaxDepth());
            } else {
                Pair<String, Integer> functionPair = configuration.getFunctionAtIndex(functionIndex);
                treeNode = new TreeNode<>(functionPair.getKey(), functionPair.getValue(), configuration.getMaxDepth());
                //Obtenemos el número de terminales necesarios para la función.
                for (int i = 0; i < functionPair.getValue(); i++) {
                    TreeNode<String> childrenNode = getTreeNodeByGrowInitialization(depth+1, maxDepth);
                    treeNode.addNodeAt(i, childrenNode);
                }
            }
        } else {
            int terminalIndex = Utils.getRandom(configuration.getNumberOfTerminals(),0);
            String terminal = configuration.getTerminalAtIndex(terminalIndex);
            treeNode = new TreeNode<>(terminal, configuration.getMaxDepth());
        }
        return treeNode;
    }

    private Solution evaluatePopulation(List<TreeNode<String>> population, int numberOfCrossover, int numberOfMutations) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (TreeNode treeNode : population) {
            double fitness = evaluateTreeNode(treeNode);
            treeNode.setFitness(fitness);
            totalFitness += fitness;
        }

        population = sortPopulation(population);
        TreeNode<String> bestTreeNode = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/configuration.getPopulationSize());
        solution.setBestFitness(bestTreeNode.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestTreeNode.getFitness());

        //TODO
        solution.setAbsoluteBestRepresentation("Falta representaión");

        double acumulatedFitness = 0;
        for (int i = population.size()-1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness()/totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
            population.get(i).setGrade(population.get(i).getFitness() / totalFitness);
        }

        return solution;
    }

    private List<TreeNode<String>>sortPopulation(List<TreeNode<String>> population) {
        Collections.sort(population, Collections.reverseOrder());
        return population;
    }

    private double evaluateTreeNode(TreeNode<String> treeNode) {
//        if (treeNode.calculateDepth() > configuration.getMaxDepth()) {
//            treeNode
//        }

        double fitness = 0;

        if (treeNode.isLeaf()) {
            return fitness;
        }

        fitness += treeNode.calculateDepth();
        for (TestValue testValue : configuration.getMultiplexorTestValue().getTestValues()) {
            Boolean result = evaluateFunctionTreeNode(treeNode, testValue.getValuesMap());
            if (result != testValue.getResult()) {
                fitness += 10;
            }
        }

        return fitness;
    }

    private Boolean evaluateTreeNode(TreeNode<String> treeNode, Map<String, Boolean> values) {
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
     * @param values Mapa de los valores del multiplexor contra los que se va a evaluar la función.
     * @return El resultado de la evaluación.
     */
    private Boolean evaluateFunctionTreeNode(TreeNode<String> treeNode, Map<String, Boolean> values) {
        Boolean firstNode = evaluateTreeNode(treeNode.getNodeAtIndex(0), values);
        if (Function.NOT == Function.valueOf(treeNode.getKey())) {
            return !firstNode;
        }

        Boolean secondNode = evaluateTreeNode(treeNode.getNodeAtIndex(1), values);
        if (Function.AND == Function.valueOf(treeNode.getKey())) {
            return firstNode && secondNode;
        }

        if (Function.OR == Function.valueOf(treeNode.getKey())) {
            return firstNode || secondNode;
        }

        Boolean thirdNode = evaluateTreeNode(treeNode.getNodeAtIndex(2), values);
        if (Function.IF == Function.valueOf(treeNode.getKey())) {
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
     * @param population Los individuos a mutar.
     * @return Devuelve el número de mutaciones que se han producido.
     */
    private int mutatePopulation(List<TreeNode<String>> population) {
        int numberOfMutation = 0;
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            TreeNode<String> treeNode;
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


    private List<TreeNode<String>> getElite(List<TreeNode<String>> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        population = sortPopulation(population);
        List<TreeNode<String>> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 && j < eliteLength; i--, j++) {
            TreeNode<String> newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<TreeNode<String>> population, List<TreeNode<String>> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }
}
