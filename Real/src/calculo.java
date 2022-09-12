
import java.util.Scanner;

import javax.swing.JOptionPane;

public class calculo {

	public class Calculo {

		public static void main(String[] args) {
	     
			Scanner input = new Scanner(System.in);
			
			String numR= JOptionPane.showInputDialog(null,"Digite o valor em real RS:");
			double r= Double.parseDouble(numR);
			
			
			double d= r/5.10;
			System.out.println("O valor em dolar é: " + d);
		}
	}

}