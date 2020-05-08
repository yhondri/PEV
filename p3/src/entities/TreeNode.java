package entities;

import static java.lang.Integer.max;

public class TreeNode implements Comparable<TreeNode>  {
    //Padre del nodo
    private TreeNode parent;
    //Dato del nodo (operador o argumento).
    private String key;
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

    public TreeNode(String key, int functionNumberOfValues, int maxDepth) {
        this.key = key;
        this.maxDepth = maxDepth;
        this.functionNumberOfValues = functionNumberOfValues;
        this.children = new TreeNode[functionNumberOfValues];
    }

    public TreeNode(String key, int maxDepth) {
        this.key = key;
        this.maxDepth = maxDepth;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void addNodeAt(int index, TreeNode treeNode) {
        children[index] = treeNode;
        treeNode.parent = this;
        treeNode.indexInParent = index;
    }

    public TreeNode[] getChildren() {
        return children;
    }

    public int getHeight() {
        if (isLeaf()) {
            return 1;
        }

        int totalHeight = 1;
        for (TreeNode treeNode : children) {
            totalHeight += treeNode.getHeight();
        }

        return totalHeight;
    }

//    public int getDepth() {
//        if (isLeaf()) {
//            return 1;
//        }
//
//        int depth = 0;
//        for (TreeNode child: children) {
//            depth = max(depth, child.getDepth());
//            if (depth > maxDepth) {
//                return maxDepth;
//            }
//        }
//
//        return depth + 1;
//    }

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

    public TreeNode getNodeAtIndex(int index) {
        return children[index];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(TreeNode obj) {
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

    public TreeNode getCopy() {
        TreeNode copy = new TreeNode(key, functionNumberOfValues, maxDepth);

        if (children != null) {
            copy.children = new TreeNode[children.length];
            for (int i = 0; i < children.length; i++) {
                copy.children[i] = children[i].getCopy();
            }
        }

        return copy;
    }

    public int getIndexInParent() {
        return indexInParent;
    }

    public void removeParent() {
        parent = null;
        indexInParent = -1;
    }

    /**
     * From Java documentation: However, This is true for the simple cases.
     * If you need to concatenate inside the loop, it is always suggested to use  StringBuilder.
     * @return La representación del TreeNode.
     */
    public String getRepresentation() {
        if (isLeaf()) {
            return key;
        }

        StringBuilder representationStringBuilder = new StringBuilder("(" +key);
        for (TreeNode node : children) {
            representationStringBuilder.append(" ");
            String chilRepresentation = node.getRepresentation();
            representationStringBuilder.append(chilRepresentation);
        }
        representationStringBuilder.append(")");
        return representationStringBuilder.toString();
    }

    @Override
    public String toString() {
        return getRepresentation();
    }

    public void setChildren(TreeNode[] children) {
        this.children = children;
    }
}