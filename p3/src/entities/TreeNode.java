package entities;

public class TreeNode<T> implements Comparable<TreeNode<T>>  {
    //Padre del nodo
    private TreeNode<T> parent;
    //Dato del nodo (operador o argumento).
    private T key;
    //Hijos del nodo
    private TreeNode[] children;
    //Profundidad máxima del nodo.
    private int maxDepth;
    //Número de argumentos que necesita una función.
    private int functionNumberOfValues;
    private int indexInParent = -1;
    private double fitness;
    private double acumulatedFitness;
    private double grade;

    public TreeNode(T key, int functionNumberOfValues, int maxDepth) {
        this.key = key;
        this.maxDepth = maxDepth;
        this.functionNumberOfValues = functionNumberOfValues;
        this.children = new TreeNode[functionNumberOfValues];
    }

    public TreeNode(T key, int maxDepth) {
        this.key = key;
        this.maxDepth = maxDepth;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public void addNodeAt(int index, TreeNode<T> treeNode) {
        children[index] = treeNode;
        treeNode.parent = this;
        treeNode.indexInParent = index;
    }

    public TreeNode[] getChildren() {
        return children;
    }

    public int calculateDepth() {
        if (isLeaf()) {
            return 1;
        }

        int totalHeight = 1;
        for (TreeNode<String> treeNode : children) {
            totalHeight = treeNode.calculateDepth();
        }

        return totalHeight;
    }

    /**
     * Verifica si el nodo es una hoja, es decir, si no tiene nodos hijos.
     * @return Devuelve sí, si el nodo tiene hijos, no en caso ccontrario.
     */
    public boolean isLeaf() {
        return (subtreeHeight() == 0);
    }

    /**
     * Calcula la altura de este nodo.
     * @return Devuelve la altura del nodo.
     */
    public int subtreeHeight() {
        if (children == null) {
            return 0;
        } else {
            return children.length;
        }
    }

    public TreeNode<T> getNodeAtIndex(int index) {
        return children[index];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(TreeNode<T> obj) {
        return Double.compare(this.fitness, obj.getFitness());
    }

    public void setAcumulatedFitness(double acumulatedFitness) {
        this.acumulatedFitness = acumulatedFitness;
    }

    public double getAcumulatedFitness() {
        return acumulatedFitness;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public TreeNode<T> getCopy() {
        TreeNode copy = new TreeNode(key, functionNumberOfValues, maxDepth);

        if (children != null) {
            copy.children = new TreeNode[children.length];
            for (int i = 0; i < children.length; i++) {
                copy.children[i] = children[i].getCopy();
            }
        }

        return copy;
    }
}