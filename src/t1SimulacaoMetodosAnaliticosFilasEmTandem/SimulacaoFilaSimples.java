package t1SimulacaoMetodosAnaliticosFilasEmTandem;

public class SimulacaoFilaSimples {
	private FilaSimples fila1;
	private Escalonador escalonador;
	private FilaSimples fila2;

	public SimulacaoFilaSimples(FilaSimples filaSimples1, FilaSimples filaSimples2) {
		this.fila1 = filaSimples1;
		this.fila2 = filaSimples2;
		escalonador = new Escalonador(fila1);

	}

	public void exec() {
		// usa como referencia a media apenas a primeira fila
		// assumimos que a media de execuacao e SEMPRE a mesma em TODAS as filas
		// adicionas]

		for (int i = 0; i < fila1.getMedia(); i++) {
			// insere o primeiro cliente em todos os ecalonadores de todas as filas
			preparaExecucao();

			// a quantidade de aleatorios eh um numero independente e que pode ser alcancado
			// a partir de qualquer fila
			while (fila1.getAleatorios() >= 0) {

				// pega o proximo evento do escalonador
				Evento evento = escalonador.getProximoEvento();

				// certificacao de que valor e valido
				if (evento != null) {
					// contailiza o tempo
					contabilizaTempo(evento);

					// se for uma chegada
					if (evento.getTipo().contentEquals("CHEGADA")) {
						lidarComChegadas(evento);

						// se for uma saida
					} else {
						lidarComSaidas(evento);
					}
				}
			}
			resetaFila();
		}

		printResultado();

	}

	private void preparaExecucao() {
		escalonador.add(new Evento("CHEGADA", 0, fila1.getPrimeirocliente()));

	}

	private void contabilizaTempo(Evento evento) {
		// tempo atual - tempo anterior
		// tempo atual = tempo do evento
		// tempo anterior = tempo global desatualizado

		// entao:
		// altera o tempo global anterior para o tempo global atual (quando aconteceu o
		// ultimo evento)
		fila1.updateTempoGlobalAnterior();

		// altera o tempo global para tempo global do evento (quando o mesmo ira
		// acontecer)
		fila1.updateTempoGlobal(evento.getTempoGlobal());

		// atualiza o tempo no vetor
		fila1.updateTempoFila();

	}

	private void lidarComChegadas(Evento evento) {
		// verifica se ha espaco na fila
		if (fila1.haEspaco()) {
			// aumenta o ponteiro da fila
			fila1.setPonteiro(fila1.getPonteiro() + 1);

			// se a fila eh menor ou igual a quantidade de servidores
			if (fila1.podeAgendar()) {
				// agenda uma saida
				escalonador.agendaSaida(fila1.getAtendimentoMIN(), fila1.getAtendimentoMAX(), fila1.getTempoGlobal());
			}

		} else {
			fila1.clientePerdido();
		}

		// sempre agenda uma chegada ao final
		escalonador.agendaChegada(fila1.getChegadaMIN(), fila1.getChegadaMAX(), fila1.getTempoGlobal());
	}

	private void lidarComSaidas(Evento evento) {
		//verifica se eh possivel diminuir a fila
		if(fila1.getPonteiro()>0) {
			// diminui a fila
			fila1.setPonteiro(fila1.getPonteiro() - 1);
		}
		

		// se a fila eh menor ou igual a quantidade de servidores
		if (fila1.haAlguemEmEspera()) {
			escalonador.agendaSaida(fila1.getAtendimentoMIN(), fila1.getAtendimentoMAX(), fila1.getTempoGlobal());
		}
	}

	private void resetaFila() {
		fila1.resetaFila();
	}

	private void printResultado() {
		// print estado final do vetor e clientes perdidos
		fila1.resultadoFinal();
	}
}
