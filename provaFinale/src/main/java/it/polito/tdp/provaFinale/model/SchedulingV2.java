package it.polito.tdp.provaFinale.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchedulingV2 {

	private int livelloMax;
	private int max = 0;
	private boolean flag = false;
	
	
	public void startSchedulingV2(List<Istance> istances, boolean randomScheduling, List<VirtualMachine>vms, int maxInput) {
		
		this.max = maxInput;
		
		List<Istance> start = this.reduce(istances);
		
		
		Collections.sort(start, new IstancesComparator());
		
		if(randomScheduling)
			Collections.shuffle(start);
		
		schedule(start, vms);
		
		//return ritorno;
		
	}
	
	
	/*  METODO DI INIZIO SCHEDULAZIONE
	 * @PARAM prende in input una lista di istanze da schedulare e un numero di macchine su cui schedulare
	 * avvia la ricorsione
	 * @RETURN restituisce la schedulazione sotto forma di lista di liste di istanze  
	 */
	public void schedule(List<Istance> toSchedule, List<VirtualMachine> ritorno){
		
		List<VirtualMachine> parziale = new ArrayList<VirtualMachine>(ritorno);
		
		
		livelloMax = toSchedule.size();
		ricerca(toSchedule, parziale, ritorno, 0);
		//return ritorno;
	}
	
	
	public void ricerca(List<Istance> toSchedule, List<VirtualMachine> parziale, List<VirtualMachine> ritorno, int livello) {
		
		//---------CONDIZIONI DI USCITA--------------
		
		//se tutte schedulate e soluzione migliore, salvo e ritorno
		int worst = getBiggestDuration(parziale);
		
		if(livello == livelloMax && worst < max) {
			
			flag = true;
			max = worst;
			stampa(parziale);
			for(int i=0; i<5; i++) {
				ritorno.get(i).setIstances(new ArrayList<Istance>(parziale.get(i).istances));
			}
			
			System.out.println("-------fine, migliore--------\n");
			return;
		}
		
		//se tutte schedulate ma soluzione non migliore ritorno
		else if(livello == livelloMax)
			return;
		
		//se non ancora schedulate tutte ma soluzione gia peggiore, ritorno
		else if(worst > max)
			return;
		
		
		// PRENDO LA PRIMA ISTANZA DELLA LISTA
		Istance is = toSchedule.get(0);
		
		
		//per ogni macchina
		for(int i : this.getOrdine(parziale)) {
			
			VirtualMachine vm = parziale.get(i);
			//se istanza non in conflitto con altre istanze su altre macchine, aggiungi e ricerca
			if(!inConflict(is, vm, parziale) ) { //&& !scheduled(is, parziale)) {
				
				vm.addIstance(is);
				toSchedule.remove(is);
				if(!flag) {
					ricerca(toSchedule, parziale, ritorno,livello+1);
					vm.removeIstance(is);
					toSchedule.add(is);
				}	
				
			}
			
		}

	}
	
	
	
	private boolean inConflict(Istance is, VirtualMachine vm, List<VirtualMachine> parziale) {
		
		int posizioneIs = vm.getIstances().size();
		
		for(VirtualMachine v : parziale) {
			if(v.getId().compareTo(vm.getId())!=0) {
				
				for(int j=posizioneIs-1; j<=posizioneIs+1; j++) {
					if(j>=0 && j<v.getIstances().size()) {
						Istance i = v.istances.get(j);
						if(getConflict(i, is))
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/*   METODO PER DECRETARE CONFLITTO TRA DUE ISTANZE
	 * @PARAM  prende in input 2 istanze
	 * @RETURN  restituisce True se in conflitto tra loro, False altrimenti
	 */
	public boolean getConflict(Istance i1, Istance i2) {
		
		//se stesso prodotto
		if(i1.getProdotto().compareTo(i2.getProdotto())==0)
			return true;
		
		//se stesso scenario e stessa compagnia
		if(i1.getCompagnia().compareTo(i2.getCompagnia())==0 && i1.getScenario().compareTo(i2.getScenario())==0)
			return true;
		
		// se 2 riscatti o 2 switch
		if(i1.getScenario().substring(0, 6).compareTo(i2.getScenario().substring(0, 6))==0 &&
				(i1.getScenario().compareTo("SwitchStandard")==0 || i1.getScenario().contains("Riscatto")))
			return true;
		
		// se un riscatto e una liquidazione
		if( (i1.getScenario().contains("Riscatto") && i2.getScenario().contains("Liquidaz") )
				|| (i1.getScenario().contains("Liquidaz") && i2.getScenario().contains("Riscatto")))
			return true;
		
		// se due versamenti della stessa compagnia
		if(i1.getScenario().contains("Versamento") && i2.getScenario().contains("Versamento")
				&& i1.getCompagnia().compareTo(i2.getCompagnia())==0)
			return true;
		
		return false;
	}

	
	private int getBiggestDuration(List<VirtualMachine> parziale) {
		
		int worst = 0;
		
		for(VirtualMachine vm : parziale) {
			int duration = vm.getDuration();
			if(duration>worst)
				worst = duration;
		}
		
		return worst;
	}


	//stampa il numero del tentativo attuale, il max, e la schedulazione
		public void stampa(List<VirtualMachine> daStampare) {
			//System.out.println(contatore);
			System.out.println(max);
			int dimensione = 0;
			for(VirtualMachine vm : daStampare) {
				System.out.println(vm.getIstances());
				dimensione+=vm.getIstances().size();
			}
			System.out.println(dimensione);
		}
	
	//------------------------------------METODI PREPARATORI------------------------------------
		private List<Istance> reduce(List<Istance> istances) {
			// TODO Auto-generated method stub
		
			List<Istance> ritorno = new ArrayList<Istance>();
			
			for(Istance i : istances) {
				if(! (i.getScenario().contains("Ced") || i.getScenario().contains("Riden") ||
																	i.getScenario().contains("Riv")) )
						ritorno.add(i);
			
			}
			
			return ritorno;
			
		}
		
		public Integer[] getOrdine(List<VirtualMachine> parziale) {
			
			Integer ritorno[]= new Integer[5];
			List<VirtualMachine> temp = new ArrayList<VirtualMachine>(parziale);
			Collections.sort(temp);
			
			for(int c=0; c<5; c++) {
				ritorno[c] = temp.get(c).machineNumber-1;
			}
			return ritorno;
		}
}
