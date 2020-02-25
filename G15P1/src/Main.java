import problems.Problem1;
import problems.Problem2;
import problems.Problem3;

public class Main {

    int numGenerations = 0;

    public static void main(String[] args) {
        System.out.println("Hello World !!");
        switch (args[0]) {
		case "1":
			new Problem().run(new Problem1());
			break;
		case "2":
        new Problem().run(new Problem2());
			break;
		case "3":
	        new Problem().run(new Problem3());
			break;
		default:
			break;
		}
    }
}


