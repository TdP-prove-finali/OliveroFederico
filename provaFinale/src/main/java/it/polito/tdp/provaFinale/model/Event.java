package it.polito.tdp.provaFinale.model;

import java.time.LocalTime;


public class Event {
	
	public enum EventType{
		START_RUN,
		SUCCESS_RUN,
		FAILUR_RUN,
		END_SIMULATION
	}
	
	private Istance istance;
	private Run run;	
	private VirtualMachine vm;
	private LocalTime time;
	private EventType typeEvent;
	
	public Event(Istance istance, VirtualMachine vm, LocalTime time, EventType typeEvent) {
		super();
		this.istance = istance;
		this.vm = vm;
		this.time = time;
		this.typeEvent = typeEvent;
	}

	public Istance getIstance() {
		return istance;
	}

	public void setIstance(Istance istance) {
		this.istance = istance;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public VirtualMachine getVm() {
		return vm;
	}

	public void setVm(VirtualMachine vm) {
		this.vm = vm;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(EventType typeEvent) {
		this.typeEvent = typeEvent;
	}
	
	@Override
	public String toString() {
		return this.time+ ": "+this.typeEvent+",    "+this.istance;
	}
	
	
	

}
