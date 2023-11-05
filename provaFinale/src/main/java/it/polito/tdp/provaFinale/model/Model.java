package it.polito.tdp.provaFinale.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.provaFinale.db.IstanceDAO;
import it.polito.tdp.provaFinale.db.RunDAO;

public class Model {
	
	IstanceDAO idao;
	RunDAO rdao;
	List<Istance> istances;
	List<Run> runs;
	List<VirtualMachine> vms;
	
	
	public Model() {
		
		this.vms = generateVirtualMachines();
		
		this.idao = new IstanceDAO();
		this.istances = idao.getAllIstances();
		
		this.rdao = new RunDAO();
		setAverageDuration();
		
		
		
	}
	
	public List<List<Istance>> scheduleIstances(){
		
		Scheduling s = new Scheduling();
		return s.startScheduling(this.istances, true);
	}


	private void setAverageDuration() {
		
		for(Istance is : this.istances) {
			int avgDuration = this.rdao.getAvgSuccessDuration(is);
			is.setDurataMedia(avgDuration);
		}
	}


	private List<VirtualMachine> generateVirtualMachines() {

		List<VirtualMachine> ritorno = new ArrayList<VirtualMachine>();
		
		for(int i=1; i<=5; i++) {
			String id = "UFT-ONE-"+i;
			VirtualMachine vm = new VirtualMachine(id);
			ritorno.add(vm);
			
		}
		
		return ritorno;
	}


	public List<Istance> getIstances() {
		return istances;
	}


	public void setIstances(List<Istance> istances) {
		this.istances = istances;
	}
	
	
	

}
