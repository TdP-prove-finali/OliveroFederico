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
	
	public List<RowIstances> scheduleIstances(boolean shuffle){
		
		List<RowIstances> ritorno = new ArrayList<RowIstances>();
		
		int max = 33500;
		if(shuffle)
			max = 35000;
		
		Scheduling s = new Scheduling();
		List<List<Istance>> lista = s.startScheduling(this.istances, shuffle, max);
		
		this.updateMachines(lista);
		
		boolean flag = false;
		int contatore = 0;
		while(!flag) {
			
			String array[] = new String[5];
			flag = true;
			for(int i=0; i<5; i++) {
				
				if(lista.get(i).size()>contatore) {
					array[i] = lista.get(i).get(contatore).stampa();
					flag = false;
				}
				else 
					array[i] = null;
			}
			RowIstances ri = new RowIstances(array[0],array[1],array[2],array[3],array[4]);
			ritorno.add(ri);
			contatore++;
		}
		
		
		
		return ritorno;
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
	
	
	public List<Istance>getIstanzeFromScenario(String scenario){
		
		List<Istance> ritorno = new ArrayList<Istance>();
		
		for(Istance i : this.istances) {
			if(i.getScenario().contains(scenario))
				ritorno.add(i);
		}
		
		return ritorno;
	}
	
	public void updateMachines(List<List<Istance>> lista) {
		
		for(int i=0; i<5; i++) {
			
			List<Istance> istancesOnVm = lista.get(i);
			for(Istance is: istancesOnVm) {
				this.vms.get(i).addIstance(is);
			}
			
			this.vms.get(i).createDuplicate();
		}
		
		
	}
	
	public List<RowIstances> simulateEvents(int stability, boolean emissioni, boolean riscatti, boolean switchs, boolean simulazioneIntelligente){
		
		Simulator sim = new Simulator(this.vms, this.istances, stability, emissioni, riscatti, switchs, simulazioneIntelligente);
		sim.initialize();
		sim.run();
		
		List<RowIstances> ritorno = new ArrayList<RowIstances>();
		
		boolean flag = false;
		int contatore = 0;
		while(!flag) {
			
			String array[] = new String[5];
			flag = true;
			for(int i=0; i<5; i++) {
				
				List<Event> temp = this.vms.get(i).simulation;
				if(temp.size()>contatore) {
					array[i] = temp.get(contatore).stampa();
					flag = false;
				}
				else
					array[i] = null;
			}
			RowIstances re = new RowIstances(array[0],array[1],array[2],array[3],array[4]);
			ritorno.add(re);
			contatore++;
		}
		
		
		RowIstances last = sim.getResults();
		ritorno.add(last);
		return ritorno;
	}
	
}
