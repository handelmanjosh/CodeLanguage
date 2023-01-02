import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;

public class SyntaxTree {
    private SyntaxNode head;
    public SyntaxTree() {
        this.head = new SyntaxNode(null);
    }
    public class SyntaxNode {
        HashMap<Character, SyntaxNode> children;
        Runnable function;
        public SyntaxNode(Runnable r) {
            this.children = new HashMap<Character, SyntaxNode>(){{
                put('c', null);
                put('o', null);
                put('d', null);
                put('e', null);
                put('C', null);
                put('O', null);
                put('D', null);
                put('E', null);
            }};
            this.function = r;
        }
    }
    public void add(String methodName, Runnable r) {
        add(this.head, methodName, r);
    }
    private void add(SyntaxNode m, String methodName, Runnable r) {
        char c = methodName.charAt(0);
        SyntaxNode nextNode = m.children.get(c);
        if (nextNode == null) {
            if (methodName.length() == 1) {
                m.children.put(c, new SyntaxNode(r));
            } else {
                SyntaxNode next = new SyntaxNode(null);
                m.children.put(c, next);
                add(next, methodName.substring(1, methodName.length()), r);
            }
        } else {
            add(nextNode, methodName.substring(1, methodName.length()), r);
        }
    }
    public Runnable find(String methodName) throws InvalidAlgorithmParameterException {
        if (methodName.length() != 4) throw new InvalidAlgorithmParameterException();
        if (invalid(methodName)) throw new InvalidAlgorithmParameterException();
        return find(this.head, methodName);
    }
    private Runnable find(SyntaxNode current, String methodName) {
        char c = methodName.charAt(0);
        SyntaxNode next = current.children.get(c);
        if (methodName.length() == 1) {
            return next.function;
        } else {
            return find(next, methodName.substring(1, methodName.length()));
        }
    }
    public boolean invalid(String methodName) {
        HashMap<Character, Integer> stuff = new HashMap<Character, Integer>(){{
            put('c', 0); put('o', 0); put('d', 0); 
            put('e', 0); put('C', 0); put('O', 0);
            put('D', 0); put('E', 0);
        }};
        for (int i = 0; i < methodName.length(); i++) {
            char c = methodName.charAt(i);
            if (!stuff.containsKey(c)) return true;
            int n = stuff.get(c);
            if (n == 1) return true;
            stuff.put(c, 1);
        }
        return false;
    }
}
