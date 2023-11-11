package it.polito.tdp.provaFinale.model;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine implements Comparable<VirtualMachine>{
	
	private String id;
	List<Istance> istances;
	int duration;
	int machineNumber;
	List<Event> simulation;

	public VirtualMachine(String id) {
		super();
		this.id = id;
		this.istances = new ArrayList<Istance>();
		this.machineNumber = this.setMachineNumber(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Integer getDuration() {
		
		Integer ritorno = 0;
		for(Istance i: this.istances) {
			ritorno+=i.getDurataMedia();
		}
		this.duration = ritorno; 
		return ritorno;
	}

	public List<Istance> getIstances() {
		return istances;
	}

	public void setIstances(List<Istance> istances) {
		this.istances = istances;
	}
	
	public void addIstance(Istance is) {
		this.istances.add(is);
		this.duration +=is.getDurataMedia();
	}
	
	public void removeIstance(Istance is) {
		if(this.istances.remove(is))
			this.duration -= is.getDurataMedia();
	}

	@Override
	public int compareTo(VirtualMachine vm) {
		
		if(this.duration!=vm.duration)
			return this.duration - vm.duration;
		else 
			return this.machineNumber-vm.machineNumber;
	}
	
	private int setMachineNumber(String id) {
		
		for(int i=1; i<6; i++) {
			String toContain= ""+i;
			if(id.contains(toContain))
				return i;
			
				
		}
		return 0;
	}

	public int getMachineNumber() {
		return machineNumber;
	}

	public List<Event> getSimulation() {
		return simulation;
	}
	
	public void addEventToSimulation(Event e) {
		this.simulation.add(e);
	}
	
}
