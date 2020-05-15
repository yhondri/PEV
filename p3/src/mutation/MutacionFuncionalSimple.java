package mutation;

import entities.TreeNode;
import helper.Pair;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Selecciona un nodo rama dentro del arbol y sustituye su funcion por otra de la misma aridad.*/
public class MutacionFuncionalSimple implements MutationAlgorithm {

    private List<Pair<String, Integer>> functions;

    public MutacionFuncionalSimple(List<Pair<String, Integer>> functions){
        this.functions = functions;
    }

    @Override
    public TreeNode mutate(TreeNode treeNode) {
        int numBranches = treeNode.getHeight() - getLeafCardinal(treeNode);
        if(numBranches < 1) return treeNode;
        int selectedBranchNode = Utils.getRandom(numBranches, 0);
        TreeNode branchToMutate = getBranch(treeNode, selectedBranchNode);
        int arity = branchToMutate.getChildren().length;
        List<String> sameArityFuncs = getSameArityFunctions(arity);
        branchToMutate.setKey(sameArityFuncs.get(Utils.getRandom(sameArityFuncs.size(), 0)));
        return treeNode;
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
     * Crea una coleccion de las funciones que tengan la aridad especificada.
     * @param arity El numero de argumentos a los que se ajustan las funciones a seleccionar.
     * @return Lista con las claves de las funciones de aridad "arity"
     * */
    private List<String> getSameArityFunctions(int arity) {
        List<String> ret = new ArrayList<>();
        for(Pair<String, Integer> p :functions){
            if(p.getValue() == arity)
                ret.add(p.getKey());
        }
        return ret;
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
