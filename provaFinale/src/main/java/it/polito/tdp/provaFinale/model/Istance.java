package it.polito.tdp.provaFinale.model;

public class Istance {
	
	public enum State{
		NOT_EXECUTED,
		PASSED,
		FAILED,
		ISSUE
	}
	
	private Integer id;
	private String scenario;
	private String compagnia;
	private String prodotto;
	private Integer durataMedia;
	private State status;
	private double successRate;
	
	public Istance(Integer id, String scenario, String compagnia, String prodotto) {
		super();
		this.id = id;
		this.scenario = scenario;
		this.compagnia = compagnia;
		this.prodotto = prodotto;
		this.status = State.NOT_EXECUTED;
	}

	public Integer getDurataMedia() {
		return durataMedia;
	}

	public void setDurataMedia(Integer durataMedia) {
		this.durataMedia = durataMedia;
	}

	public String getScenario() {
		return scenario;
	}

	public String getCompagnia() {
		return compagnia;
	}

	public String getProdotto() {
		return prodotto;
	}

	public Integer getId() {
		return id;
	}
	

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return ""+this.id;//this.scenario+"_" + prodotto;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	
	public String stampa() {
		String ritorno = "";
		if(this.scenario.contains("Totale"))
			ritorno+= "RISC_TOT_"+this.prodotto;
		else if(this.scenario.contains("Parziale"))
			ritorno+="RISC_PARZ_"+this.prodotto;
		else if(this.scenario.contains("Emiss"))
			ritorno+="EMIS_"+this.prodotto;
		else if(this.scenario.contains("Standard"))
			ritorno+="Switch_"+this.prodotto;
		else if(this.scenario.contains("Autom"))
			ritorno+="VER_AUTOM_"+this.prodotto;
		else if(this.scenario.contains("Agg"))
			ritorno+="VER_AGG_"+this.prodotto;
		else if(this.id==50)
			ritorno+="LIQ_PAG_NO";
		else if(this.id==51)
			ritorno+="LIQ_PAG_SI";
		else if(this.scenario.contains("Ced"))
			ritorno+="CEDOLE_RAMO_I";
		else
			ritorno+="NOME NON ASSEGNATO";
		
		return ritorno;
	}
	
	
	
	

}
