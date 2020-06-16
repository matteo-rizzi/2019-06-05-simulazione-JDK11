package it.polito.tdp.crimes.model;

public class Agente {

	private Integer distretto;
	private boolean libero;

	public Agente(Integer distretto, boolean libero) {
		super();
		this.distretto = distretto;
		this.libero = libero;
	}

	public Integer getDistretto() {
		return distretto;
	}

	public void setDistretto(Integer distretto) {
		this.distretto = distretto;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}

	@Override
	public String toString() {
		return "Agente [distretto=" + distretto + ", libero=" + libero + "]";
	}

}
