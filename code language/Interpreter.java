import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {
    static LinkedList.Node current = null;
    static LinkedList data = new LinkedList();
    static LinkedList.Node reference = null;

    static HashMap<String, Runnable> specials = new HashMap<String, Runnable>() {{
        put("code^", Interpreter::code_special);
        put("CODE^", Interpreter::CODE_special);
    }};
    public static void main(String[] args) throws FileNotFoundException, InvalidAlgorithmParameterException {
        data.add();
        current = data.getHead();

        String filename = "program.code"; 
        File file = new File(filename);
        Scanner reader = new Scanner(file);
        String code = "";

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            code = code + " " + line;
        }
        reader.close();
        evaluateLine(code);
    }
    public static void evaluateLine(String s) {
        SyntaxTree lang = new SyntaxTree();
        lang.add("Code", Interpreter::Code); //increment index 0 of selected
        lang.add("cOde", Interpreter::cOde); //increment index 1 of selected
        lang.add("coDe", Interpreter::coDe); //...
        lang.add("codE", Interpreter::codE); //...
        lang.add("code", Interpreter::code); //print out selected
        lang.add("CODE", Interpreter::CODE); //print out entire list
        lang.add("Edoc", Interpreter::Edoc); //decrement index 1 of selected
        lang.add("eDoc", Interpreter::eDoc); //...
        lang.add("edOc", Interpreter::edOc); //...
        lang.add("edoC", Interpreter::edoC); //...
        lang.add("EDOc", Interpreter::EDOc); //move selected back 1
        lang.add("eDOC", Interpreter::eDOC); //move selected forward 1
        lang.add("CODe", Interpreter::CODe); //add new node in front of selected
        lang.add("cODE", Interpreter::cODE); //add new node behind selected

        String[] line = s.split(" ");
        Runnable r;
        for (int i = 0; i < line.length; i++) {
            String command = line[i];
            if (command.equals("") || command.equals("coDE")) continue;
            if (command.equals("COde")) {
                i = i + loop(line, i + 1);
                continue;
            }

            if (command.equals("code?")) {
                i = i + conditional(line, i + 1);
                continue;
            }
            try {
                //System.out.println("command: " + command);
                r = lang.find(command);
            } catch (InvalidAlgorithmParameterException e) {
                if (command.length() == 5 && command.charAt(4) == '^') {
                    r = specials.get(command);
                } else {
                    r = null;
                }
            }
            if (r == null) {
                System.out.println("Invalid syntax: " + command);
            } else {
                r.run();
            }
        }
    }
    public static int loop(String[] line, int count) {
        StringBuilder inLoop = new StringBuilder();
        int skip = 0;
        int loops = 0;
        for (int i = count; i < line.length; i++) {
            String s = line[i];
            if (s.equals("COde")) loops++;
            if (s.equals("coDE")) { //this is the problem, must count instances of coDE
                if (loops == 0) {
                    break;
                } else {
                    loops--;
                    inLoop.append(s);
                }
                skip++;
            } else {
                inLoop.append(s).append(" ");
            }
            skip++;
        }
        String loopee = inLoop.toString();
        //System.out.println("in loop: " + loopee);
        int sum = sum(current.value);
        while (sum > 0) {
            evaluateLine(loopee);
            sum--;
        }
        return skip;
    }
    public static int conditional(String[] line, int count) {
        int totalNum = 0;
        boolean where = true;
        StringBuilder ifTrue = new StringBuilder();
        StringBuilder ifFalse = new StringBuilder();
        for (int i = count; i < line.length; i++) {
            String s = line[i];
            if (s.equals("|")) {
                totalNum++;
                break;
            }
            if (s.equals("?")) {
                where = false;
            } else {
                if (where) {
                    ifTrue.append(s).append(" ");
                } else {
                    ifFalse.append(s).append(" ");
                }
            }
            totalNum++;
        }
        if (sum(current.value) % 2 == 0) {
            evaluateLine(ifTrue.toString());
        } else {
            evaluateLine(ifFalse.toString());
        }
        return totalNum;
    }
    public static void doAll(String s) {
        String[] line = s.split(" ");
        for (String string : line) {
            
        }
    }
    public static int sum(int[] a) {
        int total = 0;
        for (int i : a) {
            total += i;
        }
        return total;
    }
    public static void print(Object o) {
        System.out.println(o);
    }
    public static void Code() {
        current.addFirst();
    }
    public static void cOde() {
        current.addSecond();
    }
    public static void coDe() {
        current.addThird();
    }
    public static void codE() {
        current.addFourth();
    }
    public static void Edoc() {
        current.subtractFirst();
    }
    public static void eDoc() {
        current.substractSecond();
    }
    public static void edOc() {
        current.subtractThird();
    }
    public static void edoC() {
        current.subtractFourth();
    }
    public static void code() {
        System.out.println(current);
    }
    public static void CODE() {
        System.out.println(data);
    }
    public static void code_special() {
        System.out.println(current.toASCIIString());
    }
    public static void CODE_special() {
        System.out.println(data.toASCIIString());
    }
    public static void cODE() {
        data.addAfter(current);
    }
    public static void CODe() {
        data.addBefore(current);
    }
    public static void EDOc() {
        current = current.previous;
    }
    public static void eDOC() {
        current = current.next;
    }

}
