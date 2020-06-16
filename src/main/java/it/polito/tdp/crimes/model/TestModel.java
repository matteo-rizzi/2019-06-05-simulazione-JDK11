package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo(2014);
		model.simula(2014, 3, 1, 9);
		System.out.println(model.getMalgestiti());
	}

}
