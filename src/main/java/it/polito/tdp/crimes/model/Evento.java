package it.polito.tdp.crimes.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{

	public enum EventType {
		ASSEGNAZIONE_AGENTE, INIZIO_GESTIONE_CRIMINE, FINE_GESTIONE_CRIMINE
	}

	private EventType tipo;
	private LocalTime ora;
	private Event crimine;
	private Agente agente;

	public Evento(EventType tipo, LocalTime ora, Event crimine, Agente agente) {
		super();
		this.tipo = tipo;
		this.ora = ora;
		this.crimine = crimine;
		this.agente = agente;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	public Event getCrimine() {
		return crimine;
	}

	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}

	@Override
	public String toString() {
		return "Evento [tipo=" + tipo + ", ora=" + ora + ", crimine=" + crimine + ", agente=" + agente + "]";
	}

	@Override
	public int compareTo(Evento other) {
		return this.ora.compareTo(other.getOra());
	}
	
	

}
