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
	private VirtualMachine vm;
	
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

	public void setVm(VirtualMachine vm) {
		this.vm = vm;
	}

	@Override
	public String toString() {
		return this.scenario.substring(0, 5)+"_" + prodotto+"_"+this.id+"_"+this.durataMedia;
	}

	
	
	
	
	

}
