package main;

import entities.Configuration;
import entities.TreeNode;
import helper.Utils;
import javafx.util.Pair;
import selection.SelectionAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticProblem extends Thread {
    private Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private final Random random = new Random();
    private final GeneticAlgorithmDelegate delegate;

    public GeneticProblem(Configuration configuration, GeneticAlgorithmDelegate delegate) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.delegate = delegate;
    }


    @Override
    public void run() {
        super.run();

        List<TreeNode<String>> treeNodes = rampedAndHalfInitialization();

        int i = 0;
        i += 2;
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
}
