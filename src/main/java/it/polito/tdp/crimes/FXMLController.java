/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import it.polito.tdp.crimes.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;
	private int anno;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.boxAnno.getValue() == null) {
    		this.txtResult.appendText("Errore! Per creare il grafo devi prima selezionare un anno dall'apposito combobox!\n");
    		return;
    	}
    	Integer anno = this.boxAnno.getValue();
    	this.anno = anno;
    	
    	this.model.creaGrafo(anno);
    	
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
    	this.txtResult.appendText("# ARCHI: " + this.model.nArchi() + "\n\n");
    	
    	for(Integer distretto : this.model.getVertici()) {
    		this.txtResult.appendText("Elenco adiacenti al distretto " + distretto + ":\n");
    		for(Vicino vicino : this.model.getVicini(distretto)) {
    			this.txtResult.appendText(vicino.toString() + "\n");
    		}
    		this.txtResult.appendText("\n");
    	}
    	
    	this.boxMese.getItems().clear();
    	this.boxMese.getItems().addAll(this.model.getMesi(anno));
    }
    
    @FXML
    void doAggiungiGiorni(ActionEvent event) {
    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un anno dall'apposito combobox!\n");
    		return;
    	}
    	Integer mese = this.boxMese.getValue();
    	if(mese == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un mese dall'apposito combobox!\n");
    		return;
    	}
    	
    	this.boxGiorno.getItems().clear();
    	this.boxGiorno.getItems().addAll(this.model.getGiorni(anno, mese));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un anno dall'apposito combobox!\n");
    		return;
    	}
    	else if(this.anno != anno) {
    		this.txtResult.appendText("Errore! L'anno deve essere uguale a quello inserito per la creazione del grafo!\n");
    		return;
    	}
    	Integer mese = this.boxMese.getValue();
    	if(mese == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un mese dall'apposito combobox!\n");
    		return;
    	}
    	Integer giorno = this.boxGiorno.getValue();
    	if(giorno == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un giorno dall'apposito combobox\n");
    		return;
    	}
    	Integer numeroAgenti;
    	try {
    		numeroAgenti = Integer.parseInt(this.txtN.getText());
    		if(numeroAgenti < 1 || numeroAgenti > 10) {
    			this.txtResult.appendText("Errore! Sono ammissibili solo valori di N compresi tra 1 e 10!");
        		return;
    		}
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Errore! Devi inserire un valore numerico intero per N!");
    		return;
    	}
    	
    	this.model.simula(anno, mese, giorno, numeroAgenti);
    	this.txtResult.appendText("Simulo con " + numeroAgenti + " agenti\n");
    	this.txtResult.appendText("NUMERO DEI CRIMINI MAL GESTITI: " + this.model.getMalgestiti());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.getAnni());
    }
}
