package entities;

public class TreeNode<T> {

    private T data;
    private TreeNode<T> parent;
    private TreeNode[] children;
    private int maxDepth;

    public TreeNode(T data, int maxDepth) {
        this.data = data;
        this.maxDepth = maxDepth;
        this.children = new TreeNode[this.maxDepth];
    }
}