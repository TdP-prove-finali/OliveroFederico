package it.polito.tdp.provaFinale.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Run {
	
	private Integer id;
	private Istance istanza;
	private VirtualMachine vm;
	private boolean status; //true = passed, false = failed
	private LocalDate data;
	private LocalTime fistActivity;
	private Integer durata;
	private Integer errorsNumber;
	private Integer warningsNumber;
	
	public Run(Integer id, Istance istanza, VirtualMachine vm, boolean status, LocalDate data, LocalTime fistActivity,
			Integer durata, Integer errorsNumber, Integer warningsNumber) {
		super();
		this.id = id;
		this.istanza = istanza;
		this.vm = vm;
		this.status = status;
		this.data = data;
		this.fistActivity = fistActivity;
		this.durata = durata;
		this.errorsNumber = errorsNumber;
		this.warningsNumber = warningsNumber;
	}

	@Override
	public String toString() {
		return ""+this.id + ", " + this.istanza.getProdotto() + ", " +this.vm.getId() + ", " +this.status + ", " +
					this.errorsNumber + ", " + this.data + ", " + this.fistActivity + ", "+ this.durata;
	}

	public Integer getId() {
		return id;
	}

	public Istance getIstanza() {
		return istanza;
	}

	public VirtualMachine getVm() {
		return vm;
	}

	public boolean isStatus() {
		return status;
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getFistActivity() {
		return fistActivity;
	}

	public Integer getDurata() {
		return durata;
	}

	public Integer getErrorsNumber() {
		return errorsNumber;
	}

	public Integer getWarningsNumber() {
		return warningsNumber;
	}
	
	
	
	
	
}
