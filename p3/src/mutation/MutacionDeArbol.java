package mutation;

import entities.TreeNode;
import helper.Pair;
import helper.Utils;

import java.util.List;

/**
 * Selecciona un nodo al azar del árbol y crea un árbol aleatorio a partir de él.*/
public class MutacionDeArbol implements MutationAlgorithm{
    private final List<Pair<String, Integer>> functions;
    private final List<String> terminals;
    private final int maxDepth;

    public MutacionDeArbol(List<Pair<String, Integer>> functions, List<String> terminals, int maxDepth){
        this.functions = functions;
        this.terminals = terminals;
        this.maxDepth = maxDepth;
    }

    @Override
    public TreeNode mutate(TreeNode treeNode) {
        TreeNode aMutar = treeNode.getNodeAtIndex(Utils.getRandom(treeNode.getHeight(),0));
        if(aMutar.getParent() == null)
            return randomizeTree(maxDepth);
        else{
            aMutar.getParent().getChildren()[aMutar.getIndexInParent()] = randomizeTree(maxDepth - calcDepthTo(aMutar));
        }
        return treeNode;
    }

    /**
     * Dada una profundidad máxima, se crea un árbol cuya profundidad estará entre 1 y dicha profundidad máxima
     * @param depth profundidad máxima del árbol.
     * @return árbol de profundidad n, 1 <= n <= depth.*/
    private TreeNode randomizeTree(int depth) {
        int selection = Utils.getRandom(terminals.size()+functions.size(), 0);
        if(selection < terminals.size() || depth == 1)
            return new TreeNode(terminals.get(Utils.getRandom(terminals.size(), 0)), maxDepth);
        TreeNode ret;
        selection -= terminals.size();
        Pair<String, Integer> functionPair = functions.get(selection);
        ret = new TreeNode(functionPair.getKey(), functionPair.getValue(), maxDepth);
        for (int i = 0; i < functionPair.getValue(); i++) {
            ret.addNodeAt(i, randomizeTree(depth -1));
        }
        return ret;
    }

    /**
     * Calcula la distancia desde este nodo hasta la raíz del árbol.
     * @param treeNode Nodo desde el cual se va a realizar el cálculo.
     * @return la distancia desde este nodo hasta la raíz del árbol.*/
    private int calcDepthTo(TreeNode treeNode){
        if(treeNode.getParent() == null)
            return 0;
        return 1 + calcDepthTo(treeNode.getParent());
    }
}
