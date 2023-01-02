import java.security.InvalidAlgorithmParameterException;

public class test {
    public static void main(String[] args) throws InvalidAlgorithmParameterException {
        LinkedList l = new LinkedList();
        l.add();
        l.add();
        l.add();
        LinkedList.Node n = l.getHead();
        n = n.next;
        l.addBefore(n);
        print(l);
    }
    public static void print(Object o) {
        System.out.println(o);
    }
    public static void Code() {
        System.out.println("Code");
    }
    public static void edoc() {
        System.out.println("edoc");
    }
}
