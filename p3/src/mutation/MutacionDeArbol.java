package mutation;

import entities.TreeNode;
import helper.Pair;
import helper.Utils;

import java.util.List;

/**
 * Selecciona un nodo al azar del arbol y crea un arbol aleatorio a partir de el.*/
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
        TreeNode aMutar = getNodeAtIndex(treeNode, Utils.getRandom(treeNode.getHeight(),0));
        if(aMutar == null || aMutar.getParent() == null)
            return randomizeTree(maxDepth);
        else{
            aMutar.getParent().getChildren()[aMutar.getIndexInParent()] = randomizeTree(maxDepth - calcDepthTo(aMutar));
        }
        return treeNode;
    }

    /**
     * Dada una profundidad maxima, se crea un arbol cuya profundidad estara entre 1 y dicha profundidad maxima
     * @param depth profundidad maxima del arbol.
     * @return arbol de profundidad n, 1 <= n <= depth.*/
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
     * Calcula la distancia desde este nodo hasta la raiz del arbol.
     * @param treeNode Nodo desde el cual se va a realizar el calculo.
     * @return la distancia desde este nodo hasta la raiz del arbol.*/
    private int calcDepthTo(TreeNode treeNode){
        if(treeNode.getParent() == null)
            return 0;
        return 1 + calcDepthTo(treeNode.getParent());
    }

    /**
     * Busca el nodo i-esimo en preorden de un arbol dado
     * @param root arbol del que se va a sacar el nodo.
     * @param index indice del nodo a seleccionar
     * @return devuelve el nodo buscado, o null en el caso en el que el
     * indice supere el numero de nodos del arbol*/
    private TreeNode getNodeAtIndex(TreeNode root, int index){
        if(index == 0)
            return root;
        if(root.isLeaf()) return null;
        int i = 1;
        TreeNode ret = getNodeAtIndex(root.getChildren()[0], index - 1);
        while(i < root.getChildren().length && ret == null){
            ret = getNodeAtIndex(root.getChildren()[1], index - 1 - root.getChildren()[i - 1].getHeight());
            i++;
        }
        return ret;
    }
}
