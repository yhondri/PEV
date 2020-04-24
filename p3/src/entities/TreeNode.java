package entities;

public class TreeNode<T> {
    //Padre del nodo
    private TreeNode<T> parent;
    //Dato del nodo (operador o argumento).
    private T data;
    //Hijos del nodo
    private TreeNode[] children;
    //Profundidad máxima del nodo.
    private int maxDepth;
    //Número de argumentos que necesita una función.
    private int functionNumberOfValues;
    private int indexInParent = -1;

    public TreeNode(T data, int functionNumberOfValues, int maxDepth) {
        this.data = data;
        this.maxDepth = maxDepth;
        this.functionNumberOfValues = functionNumberOfValues;
        this.children = new TreeNode[functionNumberOfValues];
    }

    public TreeNode(T data, int maxDepth) {
        this.data = data;
        this.maxDepth = maxDepth;
        this.children = new TreeNode[functionNumberOfValues];
    }

    public void addNodeAt(int index, TreeNode<T> treeNode) {
        children[index] = treeNode;
        treeNode.parent = this;
        treeNode.indexInParent = index;
    }
}