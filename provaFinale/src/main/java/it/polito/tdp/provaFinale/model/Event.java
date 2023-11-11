package it.polito.tdp.provaFinale.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class Event implements Comparable<Event>{
	
	public enum EventType{
		START_RUN,
		SUCCESS_RUN,
		FAILUR_RUN,
		ISSUE,
		END_MACHINE,
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
		if(this.istance!=null && this.vm!=null)
			return this.time+ ": "+this.typeEvent+",    "+this.istance.toString()+", "+this.vm.getId();
		else
			return this.time+ ": "+this.typeEvent;
	}

	@Override
	public int compareTo(Event o) {
		if(this.time.compareTo(o.getTime())!=0)
			return this.time.compareTo(o.getTime());
		else if(this.typeEvent==EventType.START_RUN)
			return -1;
		else
			return 1;
	}

	public String stampa() {
		
		String evento = "";
		if(this.typeEvent == EventType.SUCCESS_RUN)
			evento = "SUCC";
		else if(this.typeEvent == EventType.FAILUR_RUN)
			evento = "FAIL";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = this.time.format(formatter);
        
		return formattedTime+": "+evento+" "+this.istance.stampa();
		
	}
	
	
	

}
