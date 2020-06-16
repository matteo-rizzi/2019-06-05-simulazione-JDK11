package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulator {

	// CODA DEGLI EVENTI
	private PriorityQueue<Evento> queue;

	// PARAMETRI DI SIMULAZIONE
	private Integer anno = 2014;
	private Integer mese = 1;
	private Integer giorno = 2;
	private Integer numeroAgenti = 5;

	// MODELLO DEL MONDO
	private List<Agente> agenti;

	private Model model;
	private Graph<Integer, DefaultWeightedEdge> grafo;

	// VALORI DA CALCOLARE
	private Integer malgestiti;

	public Simulator(Model model, Graph<Integer, DefaultWeightedEdge> grafo) {
		this.model = model;
		this.grafo = grafo;
	}

	public Integer getMalgestiti() {
		return malgestiti;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public void setMese(Integer mese) {
		this.mese = mese;
	}

	public void setGiorno(Integer giorno) {
		this.giorno = giorno;
	}

	public void setNumeroAgenti(Integer numeroAgenti) {
		this.numeroAgenti = numeroAgenti;
	}

	public void init() {
		this.queue = new PriorityQueue<>();
		this.agenti = new ArrayList<>();
		this.malgestiti = 0;
		for (int i = 0; i < this.numeroAgenti; i++) {
			this.agenti.add(new Agente(this.model.distrettoMinimo(anno), true));
		}

		for (Event e : this.model.getEventsByAnnoMeseGiorno(anno, mese, giorno)) {
			LocalTime oraEvento = e.getReported_date().toLocalTime();
			Evento evento = new Evento(EventType.ASSEGNAZIONE_AGENTE, oraEvento, e, null);
			queue.add(evento);
		}
	}

	public void run() {
		while (!queue.isEmpty()) {
			Evento e = queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		switch (e.getTipo()) {
		case ASSEGNAZIONE_AGENTE:
			Agente daAssegnare = null;
			Double min = null;
			for (Agente a : this.agenti) {
				if (a.isLibero()) {
					if (e.getCrimine().getDistrict_id() == a.getDistretto()) {
						daAssegnare = a;
						min = 0.0;
					} else {
						if (min == null || this.grafo.getEdgeWeight(this.grafo.getEdge(e.getCrimine().getDistrict_id(), a.getDistretto())) < min) {
							daAssegnare = a;
							min = this.grafo.getEdgeWeight(this.grafo.getEdge(e.getCrimine().getDistrict_id(), a.getDistretto()));
						}
					}
				}
			}
			if (daAssegnare == null) {
				// malgestito
				this.malgestiti++;
				System.out.println("Crimine malgestito");
			} else {
				// mando agente nel distretto
				Long durata = (long) ((min * 60) / 60);
				Duration d = Duration.ofMinutes(durata);
				LocalTime oraArrivoDistretto = e.getOra().plus(d);
				daAssegnare.setLibero(false);
				daAssegnare.setDistretto(e.getCrimine().getDistrict_id());
				this.queue.add(
						new Evento(EventType.INIZIO_GESTIONE_CRIMINE, oraArrivoDistretto, e.getCrimine(), daAssegnare));
			}
			break;
		case INIZIO_GESTIONE_CRIMINE:
			if (e.getOra().isAfter(e.getCrimine().getReported_date().toLocalTime().plusMinutes(15))) {
				// non sono arrivato in tempo
				this.malgestiti++;
				System.out.println("Crimine malgestito");
			}

			Duration permanenza;
			if (e.getCrimine().getOffense_category_id().equals("all-other-crimes")) {
				double probabilita = Math.random();
				if (probabilita < 0.5)
					permanenza = Duration.ofMinutes(60);
				else
					permanenza = Duration.ofMinutes(120);
			} else
				permanenza = Duration.ofMinutes(120);

			LocalTime fineGestione = e.getOra().plus(permanenza);
			this.queue.add(new Evento(EventType.FINE_GESTIONE_CRIMINE, fineGestione, e.getCrimine(), e.getAgente()));

			break;
		case FINE_GESTIONE_CRIMINE:
			e.getAgente().setLibero(true);
			System.out.println(e.getAgente());
			break;
		}

	}

}
