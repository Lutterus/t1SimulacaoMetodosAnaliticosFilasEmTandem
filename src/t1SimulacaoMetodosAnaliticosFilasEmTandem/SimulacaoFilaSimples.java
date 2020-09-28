package t1SimulacaoMetodosAnaliticosFilasEmTandem;

import t1SimulacaoMetodosAnaliticosFilasEmTandem.Escalonador;
import t1SimulacaoMetodosAnaliticosFilasEmTandem.Evento;
import t1SimulacaoMetodosAnaliticosFilasEmTandem.FilaSimples;

public class SimulacaoFilaSimples {

	private FilaSimples filaSimples;
	private Escalonador escalonador;

	public SimulacaoFilaSimples(FilaSimples filaSimples) {
		// recebe a fila simples (modelo de fila com os valores)
		this.filaSimples = filaSimples;
		// instancia o escalonador de eventos
		escalonador = new Escalonador(filaSimples);
	}

	public void exec() {
		for (int i = 0; i < filaSimples.getMedia(); i++) {
			// insere o primeiro cliente na fila
			preparaExecucao();

			while (filaSimples.getAleatorios() > 0) {
				// pega o proximo evento do escalonador
				Evento evento = escalonador.getProximoEvento();

				// atualiza o tempo da execucao
				contabilizaTempoGlobal(evento.getTempoGlobal());

				// certificacao de que valor e valido
				if (evento != null) {
					// se for uma chegada
					if (evento.getTipo().contentEquals("CHEGADA")) {
						lidarComChegadas(evento, 1);

						// se for uma saida
					} else {
						lidarComSaidas(evento);
					}
				}
			}

			// limpa o resto da fila
			Evento evento2 = escalonador.getProximoEvento();
			while (evento2 != null) {
				// atualiza o tempo da execucao
				contabilizaTempoGlobal(evento2.getTempoGlobal());

				if (evento2.getTipo().contentEquals("CHEGADA")) {
					lidarComChegadas(evento2, 0);

					// se for uma saida
				} else {
					lidarComSaidas(evento2);
				}
				evento2 = escalonador.getProximoEvento();
			}
			// printa o resultado da execucao
			filaSimples.print();
			// reseta o escalonador
			escalonador = new Escalonador(filaSimples);
		}
		// finalizou a execucao, guarda o resultado
		filaSimples.resultadoFinal();

	}

	private void lidarComSaidas(Evento evento) {
		// fila--
		filaSimples.contabilizaTempo(evento.getTempoGlobal(), evento.getTipo());
		// se fila>=1 agenda saida
		if (filaSimples.existeAlguemParaSerAtendido()) {
			escalonador.agendaSaida(filaSimples.getAtendimentoMIN(), filaSimples.getAtendimentoMAX(),
					filaSimples.getTempo());
		}
	}

	private void lidarComChegadas(Evento evento, int i) {
		// verifica se possui espaco na fila
		if (filaSimples.possuiEspaco()) {
			// fila++
			filaSimples.contabilizaTempo(evento.getTempoGlobal(), evento.getTipo());
			// verifica se pode agendar uma saida
			if (filaSimples.podeAgendarSaida()) {
				escalonador.agendaSaida(filaSimples.getAtendimentoMIN(), filaSimples.getAtendimentoMAX(),
						filaSimples.getTempo());
			}
		} else {
			// se nao possui espaco, perda
			filaSimples.contabilizaPerda();
			//
			filaSimples.contabilizaTempo(evento.getTempoGlobal(), evento.getTipo());
		}
		// sempre agenda chegada
		if (i == 1) {
			escalonador.agendaChegada(filaSimples.getChegadaMIN(), filaSimples.getChegadaMAX(), filaSimples.getTempo());
		}
	}

	private void contabilizaTempoGlobal(double tempo) {
		filaSimples.setTempo(tempo);
	}

	private void preparaExecucao() {
		// adiciona o primeiro cliente no escalonador
		escalonador.add(filaSimples.primeiroCliente());
	}

}
