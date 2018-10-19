import java.util.*;

public class DungeonNode<T> {
    public T data;
    public DungeonNode<T> parent;
    public List<DungeonNode<T>> children;
    private List<DungeonNode<T>> index;
    boolean raided;
    
    public DungeonNode(T data) {
    	this.data = data;
    	this.children = new LinkedList<DungeonNode<T>>();
    	this.index = new LinkedList<DungeonNode<T>>();
    	this.index.add(this);
    	this.raided = false;
    }
               
	public DungeonNode<T> addChild(T child) {
		DungeonNode<T> childNode = new DungeonNode<T>(child);
		childNode.parent = this;
		this.children.add(childNode);
		
		return childNode;
	}
	
	public DungeonNode<T> GetRoot() {
		DungeonNode<T> temp = this;
		while (temp.parent != null) {
			temp = temp.parent;
		}
		
		return temp;
	}
	
    public boolean IsRoot() {
    	return parent == null;
    }
    
    public boolean IsLeaf() {
    	return children.size() == 0;
    }
	
	public String toString() {
		return data != null ? data.toString() : "[data null]";
	}
}