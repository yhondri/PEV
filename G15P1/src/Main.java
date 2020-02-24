
public class Main {

    int numGenerations = 0;

    public static void main(String[] args) {
        System.out.println("Hello World !!");
        switch (args[0]) {
		case "1":
			new Problem().run();
			break;
		case "2":    
        new ProblemHolder().run();
			break;
		default:
			break;
		}
    }
}


