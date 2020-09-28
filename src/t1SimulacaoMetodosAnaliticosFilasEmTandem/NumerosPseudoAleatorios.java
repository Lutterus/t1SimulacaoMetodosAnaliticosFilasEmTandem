package t1SimulacaoMetodosAnaliticosFilasEmTandem;

import java.util.ArrayList;
import java.util.Random;

public class NumerosPseudoAleatorios {

	private Random gerador;

	public NumerosPseudoAleatorios() {
		this.gerador = new Random();
	}

	public double getNumero() {
		double paraRetornar = gerador.nextDouble();
		return paraRetornar;
	}

	public ArrayList<Double> getNumeros(int quantNumeros) {
		ArrayList<Double> paraRetornar = new ArrayList<Double>();

		for (int i = 0; i < quantNumeros; i++) {
			double numeroGerado = gerador.nextDouble();
			paraRetornar.add(numeroGerado);
		}
		return paraRetornar;
	}

	public double getMudancaDeBase(double aleatorioGerado, int intervaloMinimo, int intervaloMaximo) {
		// U(A,B) = (B-A) x U(0,1) + A
		// por partes:
		// U(A,B) = (B-A)
		double paraRetornar = intervaloMaximo - intervaloMinimo;
		// U(A,B) = (B-A) x U(0,1)
		paraRetornar = paraRetornar * aleatorioGerado;
		// U(A,B) = (B-A) x U(0,1) + A
		paraRetornar = paraRetornar + intervaloMinimo;
		return paraRetornar;
	}

}
