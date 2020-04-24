package main;

import entities.Configuration;
import entities.TreeNode;
import selection.SelectionAlgorithm;

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

        List<TreeNode<String>> treeNodes = getInitialPopulation();
    }

    private List<TreeNode<String>> getInitialPopulation() {
        return null;
    }

    private TreeNode<String> inicializacionCompleta(int maxDepth) {
        return null;
    }
}
