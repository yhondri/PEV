package selection;

import entities.TreeNode;
import java.util.List;

public interface SelectionAlgorithm<T extends TreeNode> {
	public List<TreeNode> selectPopulation(List<TreeNode> population);
}
