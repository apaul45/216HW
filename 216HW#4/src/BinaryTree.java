import java.util.*;

public class BinaryTree<T> implements Iterable<T>{
    T data;
    BinaryTree<T> leftChild;
    BinaryTree<T> rightChild;
    
     public BinaryTree(){
         data = null;
         leftChild = null;
         rightChild = null;
    }
    public BinaryTree(T data) throws IllegalArgumentException{
         this.data = data;
         leftChild = null;
         rightChild = null;
    }

    public BinaryTree<T> getLeftChild() {
        return leftChild;
    }

    public BinaryTree<T> getRightChild() {
        return rightChild;
    }
    public void addLeftChild(BinaryTree<T> i) throws IllegalArgumentException{
        leftChild = i;
    }
    public void addRightChild(BinaryTree<T> i) throws IllegalArgumentException{
            rightChild = i;
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(this);
    }

    private class BinaryTreeIterator implements Iterator<T> {

        private Queue<BinaryTree<T>> queue = new LinkedList<BinaryTree<T>>();

        BinaryTreeIterator(BinaryTree<T> tree) {
            if (tree.data != null)
                queue.add(tree); // starts with the root node
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException();
            BinaryTree<T> tree = queue.remove();
            if (tree.getLeftChild() != null)
                queue.add(tree.getLeftChild());
            if (tree.getRightChild() != null)
                queue.add(tree.getRightChild());
            return tree.data;
        }
    }


}
