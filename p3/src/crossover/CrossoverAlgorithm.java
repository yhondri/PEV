package crossover;

import entities.TreeNode;
import javafx.util.Pair;

public interface CrossoverAlgorithm {
    Pair<TreeNode, TreeNode> crossOver(TreeNode chromosomeA, TreeNode chromosomeB);
}