package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Simulator sim;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public List<Integer> getAnni() {
		List<Integer> anni = this.dao.getAnni();
		Collections.sort(anni);
		return anni;
	}
	
	public List<Integer> getMesi(int anno) {
		return this.dao.getMesi(anno);
	}
	
	public List<Integer> getGiorni(int anno, int mese) {
		return this.dao.getGiorni(anno, mese);
	}
	
	public Integer distrettoMinimo(int anno) {
		return this.dao.distrettoMinimo(anno);
	}
	
	public List<Event> getEventsByAnnoMeseGiorno(int anno, int mese, int giorno) {
		return dao.getEventsByAnnoMeseGiorno(anno, mese, giorno);
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getDistretti());
		
		// aggiungo gli archi
		for(CoordinataDistretto primo : this.dao.getCoordinateDistretti(anno)) {
			for(CoordinataDistretto secondo : this.dao.getCoordinateDistretti(anno)) {
				if(!primo.equals(secondo)) {
					Double peso = LatLngTool.distance(new LatLng(primo.getLat(), primo.getLon()), new LatLng(secondo.getLat(), secondo.getLon()), LengthUnit.KILOMETER);
					Graphs.addEdge(this.grafo, primo.getId(), secondo.getId(), peso);
				}
			}
		}
	}
	
	public List<Integer> getVertici() {
		List<Integer> vertici = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Vicino> getVicini(Integer distretto) {
		List<Vicino> vicini = new ArrayList<>();
		for(Integer vicino : Graphs.neighborListOf(this.grafo, distretto)) {
			vicini.add(new Vicino(vicino, this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, vicino))));
		}
		Collections.sort(vicini);
		return vicini;
	}
	
	public void simula(int anno, int mese, int giorno, int numeroAgenti) {
		sim = new Simulator(this, this.grafo);
		sim.setAnno(anno);
		sim.setGiorno(giorno);
		sim.setMese(mese);
		sim.setNumeroAgenti(numeroAgenti);
		sim.init();
		sim.run();
	}
	
	public int getMalgestiti() {
		return this.sim.getMalgestiti();
	}
}
