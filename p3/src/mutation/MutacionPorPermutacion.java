package mutation;

import entities.TreeNode;
import helper.Utils;

/**
 * Selecciona un nodo función al azar del árbol y realiza una permutación de los hijos
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
     * Realiza una permutación cíclica de los hijos de un nodo función dado.
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
     * Calcula el número de hojas que tiene el árbol.
     * @param treeNode Árbol a explorar.
     * @return Número de hojas que contiene el árbol.*/
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
     * Recorre el árbol hasta encontrar el nodo rama en la posición seleccionada. Los nodos del
     * árbol se recorren en preorden.
     * @param treeNode la raíz del árbol.
     * @param sel la posición del nodo dentro del árbol. Debe ser menor estricto que el número de nodos rama.
     * @return Devuelve el nodo buscado o null en el caso de que la raíz del árbol sea una hoja.*/
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
