package mutation;

import entities.TreeNode;
import helper.Utils;

/**
 * Selecciona un nodo funcion al azar del arbol y realiza una permutacion de los hijos
 * de ese nodo.*/
public class MutacionPorPermutacion implements MutationAlgorithm{

    @Override
    public TreeNode mutate(TreeNode treeNode) {
        int numBranches = treeNode.getHeight() - getLeafCardinal(treeNode);
        if(numBranches < 1) return treeNode;
        TreeNode selected = getBranch(treeNode, Utils.getRandom(numBranches, 0));
        reorderChildren(selected);
        return treeNode;
    }

    /**
     * Realiza una permutacion ciclica de los hijos de un nodo funcion dado.
     * @param treeNode nodo cuyos hijos van a ser reordenados.*/
    private void reorderChildren(TreeNode treeNode) {
        if(treeNode.getChildren().length == 1)
            return;
        TreeNode auxiliar;
        TreeNode[] newChildren = new TreeNode[treeNode.getChildren().length];
        auxiliar = treeNode.getChildren()[treeNode.getChildren().length - 1];
        for(int i = treeNode.getChildren().length - 1; i > 0; i--){
            newChildren[i] = treeNode.getChildren()[i-1];
        }
        newChildren[0] = auxiliar;
        treeNode.setChildren(newChildren);
    }

    /**
     * Calcula el numero de hojas que tiene el arbol.
     * @param treeNode Arbol a explorar.
     * @return Numero de hojas que contiene el arbol.*/
    private int getLeafCardinal(TreeNode treeNode) {
        if(treeNode.isLeaf())
            return 1;
        int c = 0;
        for(TreeNode t: treeNode.getChildren()){
            c += getLeafCardinal(t);
        }
        return c;
    }

    /**
     * Recorre el arbol hasta encontrar el nodo rama en la posicion seleccionada. Los nodos del
     * arbol se recorren en preorden.
     * @param treeNode la raiz del arbol.
     * @param sel la posicion del nodo dentro del arbol. Debe ser menor estricto que el numero de nodos rama.
     * @return Devuelve el nodo buscado o null en el caso de que la raiz del arbol sea una hoja.*/
    private TreeNode getBranch(TreeNode treeNode, int sel) {
        TreeNode ret = null;
        int i = 0, prevCardinal = 1;
        if(sel == 0 && !treeNode.isLeaf()){
            return treeNode;
        }
        if(treeNode.isLeaf()){
            return null;
        }
        while(ret == null && i <treeNode.getChildren().length){
            ret = getBranch(treeNode.getChildren()[i], sel - prevCardinal);
            prevCardinal += treeNode.getChildren()[i].getHeight() - getLeafCardinal(treeNode.getChildren()[i]);
            i++;
        }
        return ret;
    }
}
