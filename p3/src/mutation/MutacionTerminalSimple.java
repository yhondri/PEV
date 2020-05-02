package mutation;

import entities.TreeNode;
import helper.Utils;

import java.util.List;

/**
 * Seleccciona al azar un símbolo teminal dentro del individuo, y se sustituye por otro diferente dentro
 * del conjunto de de símbolos de terminales posibles.
 */
public class MutacionTerminalSimple implements MutationAlgorithm {
    private List<String> terminalList;

    public MutacionTerminalSimple(List<String> terminalList) {
        this.terminalList = terminalList;
    }

    @Override
    public TreeNode mutate(TreeNode treeNode) {
        TreeNode leafTreeNode = getLeaf(treeNode);
        int terminalIndex = Utils.getRandom(terminalList.size(), 0);
        String newTerminal = terminalList.get(terminalIndex);
        leafTreeNode.setKey(newTerminal);
        return treeNode;
    }

    /**
     * Los terminales son hojas.
     * @param treeNode El nodo del que se va a extraer la hoja.
     * @return La hoja extraída.
     */
    private TreeNode getLeaf(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return treeNode;
        }

        TreeNode selectedNode = null;
        for (int i = 0; i < treeNode.getChildren().length && selectedNode == null; i++) {
            selectedNode = getLeaf(treeNode.getChildren()[i]);
        }

        return selectedNode;
    }
}
