package it.polito.tdp.provaFinale.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scheduling {

	private int max;
	private boolean flag;
	private int contatore;
	private int livelloMax;
	private int nShuffle=0;
	
	public Scheduling() {
		max = 34000;
		flag = false;
		contatore = 0;
	}
	
	
	public List<List<Istance>> startScheduling(List<Istance> istances, boolean randomScheduling) {
		
		List<Istance> start = this.reduce(istances);
		
		
		Collections.sort(start, new IstancesComparator());
		if(randomScheduling)
			Collections.shuffle(start);
		
		List<List<Istance>> ritorno = schedule(start, 5);
		
		return ritorno;
		
	}
	


	/*  METODO DI INIZIO SCHEDULAZIONE
	 * @PARAM prende in input una lista di istanze da schedulare e un numero di macchine su cui schedulare
	 * avvia la ricorsione
	 * @RETURN restituisce la schedulazione sotto forma di lista di liste di istanze  
	 */
	public List<List<Istance>> schedule(List<Istance> toSchedule, int nVM){
		
		//creo un parziale, contenente una lista di istanze per macchina (nMacchine passato in input)
		List<List<Istance>> parziale = new ArrayList<List<Istance>>();
		for(int m=0; m<nVM; m++) {
			List<Istance> listaM = new ArrayList<Istance>();
			parziale.add(listaM);
		}
		List<List<Istance>> ritorno = new ArrayList<List<Istance>>();
		System.out.println("-------first: "+toSchedule.get(0)+"--------\n");
		
		livelloMax = toSchedule.size();
		ricerca(toSchedule, parziale, ritorno, 0);
		return ritorno;
	}
	
	/*  METODO PER LA RICERCA
	 * @PARAM
	 * 
	 * @RETURN
	 */
	public void ricerca(List<Istance> start, List<List<Istance>> parziale, List<List<Istance>>ritorno,  int livello) {

		
		
		contatore++;
		
		//condizione di uscita
		
		if(livello == livelloMax && getDurationSlowestMachine(parziale) < max) {
			flag = true;
			max = getDurationSlowestMachine(parziale);
			stampa(parziale);
			ritorno.clear();
			for(int i=0; i<parziale.size();i++) {
				//System.out.println(parziale.get(i));
				List<Istance> vmRitorno = new ArrayList<Istance>(parziale.get(i));
				ritorno.add(vmRitorno);
			}
			System.out.println("-------fine, migliore--------\n");
			return;
			}
		
		//se tutte le istanze sono state schedulate ma non si ha un tempo migliore del max, torna ai passi precedenti
		else if(livello==livelloMax) {
			//System.out.println("-------fine--------\n");
			//stampa(parziale);
			return;
		}
		
		// se tempo attuale della macchina piu lenta peggiore di max, anche se non ancora schedulate tutte
		// le istanze, torna indietro ed esegui ricorsione( non perdere tempo)
		else if(getDurationSlowestMachine(parziale)>max) {
			//stampa(parziale);
			//System.out.println("-------troppo tempo!!--------\n");
			return;
		}
		
		
		//parte ricorsiva
		
		
		//prendi la prima istanza in start
		
		
		Istance is = start.get(0);
		if(contatore<livelloMax) {
			//Collections.sort(parziale, new ListComparator());
		}

		//per ogni macchina
		for(int m : this.getOrdine(parziale)) {

			//se istanza non in conflitto con altre istanze su altre macchine, aggiungi e ricerca
			if(!inConflict(is, m, parziale) && !scheduled(is, parziale)) {
				
				parziale.get(m).add(is);	
				start.remove(is);
				if(!flag) {
					ricerca(start, parziale, ritorno,livello+1);
					parziale.get(m).remove(is);
					start.add(is);
				}	
				
			}
			
			
		}
		
		
		
	}
	
	
	/*
	 * 
	 * -------------------------------FUNZIONI AUSILIARIE------------------------------
	 * 
	 */
	

	
	//ritorna il tempo totale di esecuzione delle istanze sulla macchina piu lenta
	public int getDurationSlowestMachine(List<List<Istance>> parziale) {

		int max = 0;
		int contatore;
		
		for(List<Istance> vm : parziale) {
			
			contatore = 0;
			for(Istance is: vm) {
				contatore += is.getDurataMedia();
			}
			if(contatore>max) 
				max = contatore;
			
		}
		return max;
	}
	
	
	//ritorna false se l'istanza is puo essere schedulata sulla macchina m, in un preciso istante(aka in base al parziale)
	private boolean inConflict(Istance is, int m, List<List<Istance>> parziale) {
		
		int posizioneIs = parziale.get(m).size();
		
		for(int k=0; k<parziale.size(); k++) {
			if(k!=m) {
				for(int j=posizioneIs-1; j<=posizioneIs+1; j++) {
					if(j>=0 && j<parziale.get(k).size()) {
						Istance i = parziale.get(k).get(j);
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
	
	//stampa il numero del tentativo attuale, il max, e la schedulazione
	public void stampa(List<List<Istance>> daStampare) {
		System.out.println(contatore);
		System.out.println(max);
		int dimensione = 0;
		for(List<Istance> lista : daStampare) {
			System.out.println(lista);
			dimensione+=lista.size();
		}
		System.out.println(dimensione);
	}
	
	private boolean scheduled(Istance is, List<List<Istance>> parziale) {
		// TODO Auto-generated method stub
		
		for(List<Istance> listaVm : parziale)
			for(Istance i : listaVm) {
				if(i.getScenario().compareTo(is.getScenario())==0 
						&& i.getProdotto().compareTo(is.getProdotto())==0
						&& i.getDurataMedia()==is.getDurataMedia())
					return true;
			}
		
		return false;
	}
	
	
	
	
	/*
	//ritorna true se tutte le istanze sono state schedulate, aka totale istanze in parziale=start
	private boolean allScheduled(List<List<Istance>> parziale, List<Istance> start) {

		int totale = 0;
		for(List<Istance> vm: parziale) {
			totale += vm.size();
		}
		if(totale == start.size())
			return true;
		
		return false;
	}
	*/
	
	public int getDurationMachine(List<Istance> vm) {
		int totale = 0;
		for(Istance i : vm) {
			totale+=i.getDurataMedia();
		}
		return totale;
	}
	
	public List<Integer> getOrdine(List<List<Istance>> parziale){
		
		List<List<Istance>> temp = new ArrayList<List<Istance>>(parziale);
		
		List<Integer> ritorno = new ArrayList<Integer>();
		List<Integer> durate = new ArrayList<Integer>();
		
		Map<Integer, Integer> mappa = new HashMap<Integer, Integer>();
		
		for(int i=0 ; i<parziale.size(); i++) {
			int durata = this.getDurationMachine(parziale.get(i));
			mappa.put(durata, i);
			durate.add(durata);
		}
		Collections.sort(durate);
		for(Integer ii: durate) {
			ritorno.add(mappa.get(ii));
		}
		return ritorno;
		
	}
	
	public boolean tutteConAlmeno4Elementi(List<List<Istance>> parziale) {
	
		for(List<Istance> list: parziale) {
			if(list.size()<4)
				return false;
		}
		return true;
		
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
}
