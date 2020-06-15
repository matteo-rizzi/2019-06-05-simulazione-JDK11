package it.polito.tdp.crimes.model;

public class Vicino implements Comparable<Vicino>{

	private Integer id;
	private Double distanza;

	public Vicino(Integer id, Double distanza) {
		super();
		this.id = id;
		this.distanza = distanza;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int compareTo(Vicino other) {
		return this.distanza.compareTo(other.distanza);
	}

	@Override
	public String toString() {
		return String.format("Distretto %d (distanza: %.3f km)", this.id, this.distanza);
	}

	
	
}
