package crossover;

import entities.TreeNode;
import helper.Pair;
//import javafx.util.Pair;

public interface CrossoverAlgorithm {
    Pair<TreeNode, TreeNode> crossOver(TreeNode chromosomeA, TreeNode chromosomeB);
}