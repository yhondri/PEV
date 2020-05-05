package mutation;

import entities.TreeNode;
import helper.Pair;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Selecciona un nodo rama dentro del árbol y sustituye su función por otra de la misma aridad.*/
public class MutacionFuncionalSimple implements MutationAlgorithm {

    private List<Pair<String, Integer>> functions;

    public MutacionFuncionalSimple(List<Pair<String, Integer>> functions){
        this.functions = functions;
    }

    @Override
    public TreeNode mutate(TreeNode treeNode) {
        int numBranches = treeNode.getCardinal() - treeNode.getLeafCardinal();
        int selectedBranchNode = Utils.getRandom(numBranches, 0);
        TreeNode branchToMutate = getBranch(treeNode, selectedBranchNode);
        int arity = branchToMutate.getChildren().length;
        List<String> sameArityFuncs = getSameArityFunctions(arity);
        branchToMutate.setKey(sameArityFuncs.get(Utils.getRandom(sameArityFuncs.size(), 0)));
        return treeNode;
    }

    /**
     * Crea una colección de las funciones que tengan la aridad especificada.
     * @param arity El número de argumentos a los que se ajustan las funciones a seleccionar.
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
            prevCardinal += treeNode.getChildren()[i].getCardinal() - treeNode.getChildren()[i].getLeafCardinal();
            i++;
        }
        return ret;
    }

}
