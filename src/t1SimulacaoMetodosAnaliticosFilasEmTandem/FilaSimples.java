package t1SimulacaoMetodosAnaliticosFilasEmTandem;

import t1SimulacaoMetodosAnaliticosFilasEmTandem.Evento;

public class FilaSimples {
	// resultado final deve ser obtivo a partir do resoltado de quntas iteracoes
	private int media;

	// limite de aleatorios/tempo da execucao
	private int aleatorios;

	// backUp dos numeros aleatorios
	private int aleatoriosBackUp;

	// Tempo para primeiro cliente
	private double primeiroCliente;

	// Número de servidores;
	private int numeroDeServidores;

	// quantidade de pessoas sendo atendidas
	private int sendoAtendidos = 0;

	// Capacidade da fila.
	private int capacidadeDaFila;

	// Intervalo de tempo de atendimento de um cliente na fila;
	private int intervaloDeAtendimentoMIN;
	private int intervaloDeAtendimentoMAX;

	// Intervalo de tempo para a chegada de clientes na fila;
	private int intervaloDeChegadaMIN;
	private int intervaloDeChegadaMAX;

	// tempo global atual
	private double tempoGlobal;

	// tempo global anterior (ultima iteracao)
	private double tempoGlobalAnterior;

	// numero de clientes que nao couberam na fila
	private int clientesPerdidos = 0;

	// fila
	private double vetorFila[];

	// ponteiro para referencia da posicao da fila em que estamos
	private int ponteiroDaFila = 0;

	// fila para calculo final
	private double vetorFinal[];

	// numero de clientes que nao couberam na fila
	private int clientesPerdidosFinal = 0;

	public FilaSimples(int media, int aleatorios, double primeiroCliente, int numeroDeServidores, int capacidadeDaFila,
			int intervaloDeAtendimentoMIN, int intervaloDeAtendimentoMAX, int intervaloDeChegadaMIN,
			int intervaloDeChegadaMAX) {
		this.media = media;
		this.aleatorios = aleatorios;
		this.primeiroCliente = primeiroCliente;
		this.numeroDeServidores = numeroDeServidores;
		this.capacidadeDaFila = capacidadeDaFila;
		this.intervaloDeAtendimentoMIN = intervaloDeAtendimentoMIN;
		this.intervaloDeAtendimentoMAX = intervaloDeAtendimentoMAX;
		this.intervaloDeChegadaMIN = intervaloDeChegadaMIN;
		this.intervaloDeChegadaMAX = intervaloDeChegadaMAX;
		this.vetorFila = new double[capacidadeDaFila + 1];
		this.vetorFinal = new double[capacidadeDaFila + 1];
		this.aleatoriosBackUp = aleatorios;
	}

	public int getAtendimentoMIN() {
		return intervaloDeAtendimentoMIN;
	}

	public int getAtendimentoMAX() {
		return intervaloDeAtendimentoMAX;
	}

	public double getTempo() {
		return tempoGlobal;
	}

	public void setTempo(double tempo) {
		tempoGlobalAnterior = tempoGlobal;
		tempoGlobal = tempo;
	}

	public Evento primeiroCliente() {
		return new Evento("CHEGADA", primeiroCliente, primeiroCliente);
	}

	public boolean possuiEspaco() {
		if (ponteiroDaFila < capacidadeDaFila) {
			return true;
		} else {
			return false;
		}
	}

	public void contabilizaTempo(double tempoGlobalAtual, String tipo) {
		double quantoTempoSePassou = tempoGlobalAtual - tempoGlobalAnterior;
		if (tipo.contentEquals("CHEGADA")) {
			vetorFila[ponteiroDaFila] = vetorFila[ponteiroDaFila] + quantoTempoSePassou;
			if (ponteiroDaFila < vetorFila.length-1) {
				ponteiroDaFila++;
			}

		} else {
			vetorFila[ponteiroDaFila] = vetorFila[ponteiroDaFila] + quantoTempoSePassou;
			if (ponteiroDaFila > 0) {
				ponteiroDaFila--;
			}

		}
	}

	public boolean podeAgendarSaida() {
		if (sendoAtendidos < numeroDeServidores && ponteiroDaFila > 0) {
			sendoAtendidos++;
			return true;
		}
		return false;
	}

	public void contabilizaPerda() {
		clientesPerdidos++;
	}

	public boolean existeAlguemParaSerAtendido() {
		if (ponteiroDaFila > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getChegadaMIN() {
		return intervaloDeChegadaMIN;
	}

	public int getChegadaMAX() {
		return intervaloDeChegadaMAX;
	}

	public int getAleatorios() {
		return aleatorios;
	}

	public void setAleatorios() {
		this.aleatorios--;
	}

	public int getMedia() {
		return media;
	}

	public void print() {
		double aux = 0;
		for (int i = 0; i < vetorFila.length; i++) {
			aux = aux + vetorFila[i];
		}
		
		if (aux != tempoGlobal) {
			System.out.println("--------------------------");			
			System.out.println("tempo incorreto");
			System.out.println("soma dos tempo:" + aux);
			System.out.println("tempo global: " + tempoGlobal);
		}

		// metodo para retornar a fila para o estado original, antes de comecar a nova
		// iteracao
		recuperarEstados();
	}

	public void resultadoFinal() {
		double aux;
		
		System.out.println("----------------RESULTADO FINAL----------------");
		for (int i = 0; i < vetorFinal.length; i++) {
			aux = vetorFinal[i] / media;
			double porcentagem = aux/tempoGlobalAnterior;
			porcentagem = porcentagem*100;
			System.out.println("tempo " + i + ": " + aux + " | prob: " + porcentagem + "%");
		}
		aux = clientesPerdidosFinal / media;
		System.out.println("clientes perdidos: " + aux);

	}

	private void recuperarEstados() {
		for (int i = 0; i < vetorFila.length; i++) {
			// guarda os valores no vetor final
			vetorFinal[i] = vetorFila[i] + vetorFinal[i];
			// zero os valores do vetor usado na simulacao
			vetorFila[i] = 0;
		}
		// guarda os clientes perdidos na variavel final
		clientesPerdidosFinal = clientesPerdidosFinal + clientesPerdidos;
		// zera o valor dos clientes perdidos na variavel usada na simulacao
		clientesPerdidos = 0;
		// reseta a quantidade de aleatorios disponiveis
		aleatorios = aleatoriosBackUp;
		// reseta a quantidade de pessoas sendo atendidas
		sendoAtendidos = 0;
		// reseta o tempo global da iteracao anterior
		tempoGlobalAnterior = tempoGlobal;
		// reseta o tempo global
		tempoGlobal = 0;
		// reseta o ponteiro da fila
		ponteiroDaFila = 0;
	}

	public int getPonteiro() {
		return ponteiroDaFila;
	}
}
