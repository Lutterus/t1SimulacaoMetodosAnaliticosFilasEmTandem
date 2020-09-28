package t1SimulacaoMetodosAnaliticosFilasEmTandem;

public class Main {
	public static void main(String[] args) {
		// G/G/ servidores / capacidade da fila
		System.out.println("Fila proposta: G/G/1/5, chegadas entre 2..4, atendimento entre 3..5");

		// media deve ser obtida a partir de quantas execucoes (default=1)
		int media = 5;

		// tempo total de simulacao - usei como tempo total o numero de aleatorios que
		// devem ser gerados
		int aleatorios = 1000;

		// tempo para primeiro cliente
		double tempoPrimeirocliente = (3.0);

		// numero de servidores
		int numeroDeServidores = 1;

		// capacidade da fila
		int capacidadeDaFila = 5;

		// Intervalo de tempo de atendimento de um cliente na fila;
		int intervaloDeAtendimentoMIN = 3;
		int intervaloDeAtendimentoMAX = 5;

		// Intervalo de tempo para a chegada de clientes na fila;
		int intervaloDeChegadaMIN = 2;
		int intervaloDeChegadaMAX = 4;

		// implementação de filas simples
		FilaSimples filaSimples = new FilaSimples(media, aleatorios, tempoPrimeirocliente, numeroDeServidores,
				capacidadeDaFila, intervaloDeAtendimentoMIN, intervaloDeAtendimentoMAX, intervaloDeChegadaMIN,
				intervaloDeChegadaMAX);

		// executa com as variaveis inseridas e printa o resultado
		SimulacaoFilaSimples simulacao = new SimulacaoFilaSimples(filaSimples);
		simulacao.exec();

		// ------------------------------------------------------------------------

		System.out.println("/////////////////////////////////////////////////");
		System.out.println("Fila proposta: G/G/2/5, chegadas entre 2..4, atendimento entre 3..5");

		// numero de servidores
		numeroDeServidores = 2;

		// implementação de filas simples
		filaSimples = new FilaSimples(media, aleatorios, tempoPrimeirocliente, numeroDeServidores, capacidadeDaFila,
				intervaloDeAtendimentoMIN, intervaloDeAtendimentoMAX, intervaloDeChegadaMIN, intervaloDeChegadaMAX);

		// executa com as variaveis inseridas e printa o resultado
		simulacao = new SimulacaoFilaSimples(filaSimples);
		simulacao.exec();

	}
}
