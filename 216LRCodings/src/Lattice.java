import java.util.Iterator;

public class Lattice<T> {
    private T data;
    Lattice<T> topleftcorner;

    static class LatticeNode<T>{
        T data;
        LatticeNode<T> upper, lower, left, right;
    }
    Lattice(T data){
        this.topleftcorner.data = data;
    }
    public static void main(String[] args){
        Lattice<String> strings = new Lattice<>("root node value");
    }
}