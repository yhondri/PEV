package crossover;

import entities.TreeNode;
import helper.Pair;
import helper.Utils;
//import javafx.util.Pair;

public class OperadorDeCruce implements CrossoverAlgorithm {
    private double crossOverProbability;

    public OperadorDeCruce(double crossOverProbability) {
        this.crossOverProbability = crossOverProbability;
    }


    @Override
    public Pair<TreeNode, TreeNode> crossOver(TreeNode treeNodeA, TreeNode treeNodeB) {
        TreeNode newNodeA = treeNodeA.getCopy();
        TreeNode newNodeB = treeNodeB.getCopy();

        TreeNode treeNodeFunctionA = getFunctionNode(newNodeA);
        TreeNode treeNodeFunctionB = getFunctionNode(newNodeB);

        if (treeNodeFunctionA.getParent() == null && treeNodeFunctionB.getParent() == null) {
            newNodeA = treeNodeFunctionB;
            newNodeB = treeNodeFunctionB;
        } else if(treeNodeFunctionA.getParent() == null) {
            treeNodeFunctionB.getParent().addNodeAt(treeNodeFunctionB.getIndexInParent(), treeNodeFunctionA);
            treeNodeFunctionB.removeParent();
            newNodeA = treeNodeFunctionB;
        } else if(treeNodeFunctionB.getParent() == null) {
            treeNodeFunctionA.getParent().addNodeAt(treeNodeFunctionA.getIndexInParent(), treeNodeFunctionB);
            treeNodeFunctionA.removeParent();
            newNodeB = treeNodeFunctionA;
        } else {
            TreeNode treeNodeFunctionBParentCopy = treeNodeFunctionB.getParent();
            int treeNodeFunctionBParentCopyIndex = treeNodeFunctionB.getIndexInParent();
            treeNodeFunctionA.getParent().addNodeAt(treeNodeFunctionA.getIndexInParent(), treeNodeFunctionB);
            treeNodeFunctionBParentCopy.addNodeAt(treeNodeFunctionBParentCopyIndex, treeNodeFunctionA);
        }

        return new Pair<>(newNodeA, newNodeB);
    }

    private TreeNode getFunctionNode(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return treeNode;
        }

        TreeNode selectedNode = null;
        while (selectedNode == null) {
            selectedNode = getRandomFunctionNode(treeNode, crossOverProbability);
        }

        return selectedNode;
    }

    private TreeNode getRandomFunctionNode(TreeNode treeNode, double crossOverProbability) {
        int randomValue = Utils.getRandom(1, 0);
        if (randomValue < crossOverProbability) {
            return treeNode;
        }

        if (!treeNode.isLeaf()) {
            TreeNode selectedNode = null;
            for (int i = 0; i < treeNode.getChildren().length && selectedNode == null; i++) {
                selectedNode = getRandomFunctionNode(treeNode.getChildren()[i], crossOverProbability);
            }
            return selectedNode;
        }
        return null;
    }

}
