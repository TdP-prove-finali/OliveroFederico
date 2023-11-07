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
	
	public List<RowIstances> scheduleIstances(){
		
		List<RowIstances> ritorno = new ArrayList<RowIstances>();
		
		List<Istance> ridenominazioni = this.getIstanzeFromScenario("Ridenom");
		
		ritorno.add(new RowIstances(ridenominazioni.get(0).toString(), ridenominazioni.get(1).toString(), null, null, null));
		
		Scheduling s = new Scheduling();
		List<List<Istance>> lista = s.startScheduling(this.istances, true);
		
		boolean flag = false;
		int contatore = 0;
		while(!flag) {
			
			String array[] = new String[5];
			for(int i=0; i<5; i++) {
				flag = true;
				if(lista.get(i).size()>contatore) {
					array[i] = lista.get(i).get(contatore).toString();
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
	
	
}
