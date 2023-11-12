package it.polito.tdp.provaFinale.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.provaFinale.db.RunDAO;
import it.polito.tdp.provaFinale.model.Event.EventType;


public class Simulator {

	//------------------------INPUT-------------------------
	
	// FROM MODEL
	List<VirtualMachine> machines;
	List<Istance> istances;
	
	//FROM USER
	int stability ;
	Map<String, Boolean> stabilityMap;
	boolean simulazioneIntelligente;
	
	//orari di inizio e fine (16 ore totali)
	private LocalTime startDay = LocalTime.of(8, 0, 0);
	private LocalTime endDay = LocalTime.of(22, 30, 0);
	
	
	
	//----------------STATO DEL SISTEMA--------------
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	//istanze in esecuzione
	private List<Istance> currently;
	
	private List<Istance> concluded = new ArrayList<Istance>();
	private int failures = 0;
	private int emptyMachines = 0;
	private List<Event> successi = new ArrayList<Event>();
	List<Event> issuesList = new ArrayList<Event>();
	private LocalTime endSimulationTime;
	
	SchedulingV2 schedulingClass;
	
	
	public Simulator(List<VirtualMachine> vms, List<Istance> toSimulate, int stability, boolean emissioni, boolean riscatti, boolean switchs, boolean simulazioneIntelligente) {
		
		//this.toSimulate = new ArrayList<List<Istance>>(toSimulate);
		this.machines = new ArrayList<VirtualMachine> (vms);
		this.istances = new ArrayList<Istance>(toSimulate);
		this.queue = new PriorityQueue<Event>();
		this.currently = new ArrayList<Istance>();
		for(VirtualMachine vm : vms) {
			vm.simulation = new ArrayList<Event>();
			vm.regenerateIstancesForSimulation();
		}
		this.simulazioneIntelligente = simulazioneIntelligente;
		this.stability = stability;
		this.stabilityMap = new HashMap<String, Boolean>();
		this.stabilityMap.put("Emissione", emissioni);
		this.stabilityMap.put("EmissioneNoGS", emissioni);
		this.stabilityMap.put("EmissioneTCM", emissioni);		
		this.stabilityMap.put("RiscattoParziale", riscatti);
		this.stabilityMap.put("RiscattoTotale", riscatti);
		this.stabilityMap.put("SwitchStandard", switchs);
		this.schedulingClass = new SchedulingV2();
		this.setSuccessRate();
		
	}
	
	
	public void initialize() {
		
		Event e1 = new Event(this.machines.get(0).getIstances().get(0), this.machines.get(0), this.startDay, EventType.START_RUN);
		Event e2 = new Event(this.machines.get(1).getIstances().get(0), this.machines.get(1), this.startDay, EventType.START_RUN);
		Event e3 = new Event(this.machines.get(2).getIstances().get(0), this.machines.get(2), this.startDay, EventType.START_RUN);
		Event e4 = new Event(this.machines.get(3).getIstances().get(0), this.machines.get(3), this.startDay, EventType.START_RUN);
		Event e5 = new Event(this.machines.get(4).getIstances().get(0), this.machines.get(4), this.startDay, EventType.START_RUN);
		
		Event e6 = new Event(null, null, this.endDay, EventType.END_SIMULATION);
		
		this.queue.add(e1);
		this.queue.add(e2);
		this.queue.add(e3);
		this.queue.add(e4);
		this.queue.add(e5);
		this.queue.add(e6);
		
	}
	
	public void run(){
			
		while(!this.queue.isEmpty()) {
			
			Event event = this.queue.poll();
			// System.out.println(event);	
			processEvent(event);
		}
		
		System.out.println("\n\n---SUCCESSI:-----------");
//		for(Event eee: this.successi) {
//			System.out.println(eee);
//		}
				
		System.out.println("\n---SUCCESSI: "+ this.concluded.size()+"-----------");
		System.out.println("\n---MANCANTI: "+ (49-this.successi.size())+"-----------");
		
		System.out.println("\n---FALLIMENTI: "+ this.failures+"-----------");
		System.out.println("\n---ISSUES: "+ this.issuesList.size()+"-----------");


	}
	
	public void processEvent(Event e) {
		
		System.out.println(e);
		
		LocalTime currentTime = e.getTime();
		
		Istance is = e.getIstance();
		VirtualMachine vm = e.getVm();
		
		Event newEvent = null;
		
		switch (e.getTypeEvent()) {
		
			case START_RUN:
				
				Double x = Math.random();
				Double indexes[] = generateSuccessIndex(is, currently);
				
				//SUCCESS
				if(x <= indexes[0]) {
					
					int successTime = (int)(is.getDurataMedia()/60);
					LocalTime nextEv = currentTime.plus(successTime, ChronoUnit.MINUTES);
					newEvent = new Event(is, vm, nextEv, EventType.SUCCESS_RUN);
					
				}
				//FAILUR
				else if(x > indexes[0] && x <= indexes[1]){
					
					int failurTime ;
					
					Double y = Math.random();
					//Double failurIndexes[] = getFailurIndex(is);
					if(y <= 0.5) 
						failurTime = 5;
					else 
						failurTime = (int)(0.5*is.getDurataMedia()/60);
					
					LocalTime nextEv = currentTime.plus(failurTime, ChronoUnit.MINUTES);
					newEvent = new Event(is, vm, nextEv, EventType.FAILUR_RUN);
		
				}
				//ISSUE
				else {
					
					int failurTime = (int)(0.5*is.getDurataMedia()/60);
					LocalTime nextEv = currentTime.plus(failurTime, ChronoUnit.MINUTES);
					newEvent = new Event(is, vm, nextEv, EventType.ISSUE);
				}
				
				this.queue.add(newEvent);
				this.currently.add(is);
				vm.removeIstance(is);

				
				break;
			
			case SUCCESS_RUN:
				
				if(!this.concluded.contains(is)) {
					this.successi.add(e);
					vm.addEventToSimulation(e);

					//aggiorno il contatore
					this.concluded.add(is);
				}
				else {
					System.out.println("\n\n ---------ERRORE !!!!!!!!!!! istanza: "+is.getId()+", "+is.getScenario()+", "+is.getProdotto()+", "+"--------------\n\n");
					
				}
				
				//tolgo l'istanza da currently
				this.currently.remove(is);
				
				//controllo che sulla vm ci siano altre istanze in coda, e in caso schedulo
				if(vm.getIstances().size()>0) {
					
					Istance newIstance = vm.getIstances().get(0);
					int randomTime = (int) ( (Math.random()+0.01)*120);
					LocalTime nextEv = currentTime.plus(randomTime, ChronoUnit.SECONDS);
					newEvent = new Event(newIstance, vm, nextEv, EventType.START_RUN);
					
				}
				//altrimenti controllo che vi siano altre istanze in code su altre vm, e in caso le schedulo
				else {
					
					Istance newIstance;
					if(simulazioneIntelligente)
						newIstance = findIstanceToRun();
					else
						newIstance = null;
					
					if(newIstance!=null) {
						
						newEvent = new Event(newIstance, vm, currentTime.plus(1, ChronoUnit.MINUTES), EventType.START_RUN);
						
					}
					else
						newEvent = new Event(null, vm, currentTime.plus(1, ChronoUnit.MINUTES), EventType.END_MACHINE);
				}
				
				this.queue.add(newEvent);
				
				break;
				
			case FAILUR_RUN:
				
				this.failures++;
				vm.addEventToSimulation(e);
				
				//tolgo l'istanza da currently
				this.currently.remove(is);
				
				// la riaggiungo in coda alla virtual machine
				//vm.addIstance(is);
				//---------------RILANCIO LA STESSA IS
				//schedulo un evento di start run con la prima istanza presente in lista
				//Istance newIstance = vm.getIstances().get(0);
				newEvent = new Event(is, vm, currentTime.plus(30, ChronoUnit.SECONDS), EventType.START_RUN);
				this.queue.add(newEvent);
				
				break;
				
			case ISSUE:
				
				this.issuesList.add(e);
				
				//tolgo l'istanza da currently
				this.currently.remove(is);
				
				//schedulo un evento di start run con la prima istanza presente in lista
				//Istance toSchedule = vm.getIstances().get(0);
				//newEvent = new Event(toSchedule, vm, currentTime.plus(1, ChronoUnit.MINUTES), EventType.START_RUN);
				
				//controllo che sulla vm ci siano altre istanze in coda, e in caso schedulo
				if(vm.getIstances().size()>0) {
					
					Istance newIstanceI = vm.getIstances().get(0);
					newEvent = new Event(newIstanceI, vm, currentTime.plus(30, ChronoUnit.SECONDS), EventType.START_RUN);
					
				}
				//altrimenti controllo che vi siano altre istanze in code su altre vm, e in caso le schedulo
				else {
					
					Istance newIstanceI;
					if(simulazioneIntelligente)
						newIstanceI = findIstanceToRun();
					else
						newIstanceI = null;
					
					if(newIstanceI!=null) {
						
						newEvent = new Event(newIstanceI, vm, currentTime.plus(1, ChronoUnit.MINUTES), EventType.START_RUN);
						
					}
					else
						newEvent = new Event(null, vm, currentTime.plus(1, ChronoUnit.MINUTES), EventType.END_MACHINE);
				}
				
				this.queue.add(newEvent);
				
				break;
				
			case END_MACHINE:
				
				System.out.println("FINE ESECUZIONE SU MACCHINA: "+vm.getId());
				this.emptyMachines++;
				if(this.emptyMachines==5) {
					newEvent = new Event(null, null, currentTime.plus(1, ChronoUnit.MINUTES), EventType.END_SIMULATION);
					this.queue.add(newEvent);
				}

				break;
				
			case END_SIMULATION:
				this.endSimulationTime = e.getTime();
				this.queue.clear();
				break;
		}
	}

	private Istance findIstanceToRun() {

//		System.out.println("\n\n---------------INIZIO METODO ------------------\n\n");

		
		for(VirtualMachine v : this.machines) {
			int start = v.getIstances().size() -1;
			for(int i=start; i>=0; i--) {
			//for(Istance i1 : v.getIstances()) {
				Istance i1 = v.getIstances().get(i);
				
				boolean flag = false;

				for(Istance i2 : this.currently) {
					if(this.schedulingClass.getConflict(i1, i2))
						flag = true;		
				}
				
				if(!flag) {
					v.removeIstance(i1);
					return i1;
				}
			}
		}
		return null;
	}

	private Double[] getFailurIndex(Istance input) {
		// TODO Auto-generated method stub
		return null;
	}

	/* METODO PER GENERAZIONE DI INTERVALLI DI SUCCESSO, FALLIMENTO, ISSUE
	 * @PARAM prende in input un istanza da eseguire e la lista delle istanze in esecuzione
	 * 		assegna un indice di probabilità di successo in base a conflitti e parametri dell'istanza
	 * @RETURN ritorno un array di double con in posizione 0 la probabilita di successo, 1 il range di fallimento
	 */
	private Double[] generateSuccessIndex(Istance input, List<Istance> currentlyInput) {
		// TODO Auto-generated method stub
		Double[] ritorno = new Double[2];
		ritorno[0] = 0.0;
		ritorno[1] = 0.0;
		
		boolean flag = false;
		for(Istance i1 : currentlyInput) {
			if(this.schedulingClass.getConflict(input, i1)) {
				flag = true;
				break;
			}
		}
		
		if(flag) {
			ritorno[0] = 0.5*input.getSuccessRate();
		}
		else {
			ritorno[0] = input.getSuccessRate();
		}
		
		// prendo la probabilità di non successo e determino la prob di issue come il 10% della prob di fallimento generale
		double failurAndIssueProb = 1.0-ritorno[0]; //ip 0.3
		double issueProb = failurAndIssueProb*0.1;
		ritorno[1] = (1.0-issueProb);
		
//		System.out.println("-----------INDICI: "+ritorno[0]+", "+ritorno[1]);
		return ritorno;
	}
	
	public void setSuccessRate() {
		
		RunDAO rdao = new RunDAO();
		
		
		for(Istance i: this.istances) {
			
			double successRate=0.0;
			
			//recupero dal database numero di successi e di fallimenti, numero massimo di failures in un giorno e media di failures
			Integer successes = rdao.getIstanceSuccesses(i);
			Integer failures = rdao.getFailurDays(i);
			Integer maxDailyFailures = rdao.getMaxFailur(i);
			Double averageFailures = rdao.getAverageFailurs(i);
			
			//controllo se lo scenario dell'istanza in questione sia tra le checkBox selezionate
			boolean scenaryFlag = false;
			if(this.stabilityMap.get(i.getScenario())!=null)
				scenaryFlag = this.stabilityMap.get(i.getScenario());
			
			//-----------------------------ATTRIBUISCO UN SUCCESS RATE ALL'ISTANZA---------------------------
			
			// caso migliore: situazione stabile e nessun problema di scenario
			if(this.stability == 2 && !scenaryFlag) {
				successRate = (successes/(double)(successes+failures));
			}
			
			//caso intermedio: situazione stabile ma problemi di scenario o situazione media e nessun problema di scenario
			else if ((this.stability==2 && scenaryFlag) ||(this.stability==1 && !scenaryFlag)) {
				
				if(averageFailures>1.1)
					successRate = 1/averageFailures;
				else
					successRate = 0.9;
				
			}
			
			//caso peggiore: situazione media ma problemi di scenario o situazione instabile
			else if( (this.stability==1 && scenaryFlag) || this.stability==0) {
				
				successRate = 1/ (double)maxDailyFailures;
				
			}
			
			i.setSuccessRate(successRate);
			
		}
		
	}
	
	public RowIstances getResults(){
		
		String r1 = "NUMERO SUCCESSI: "+this.concluded.size();
		String r2 = "NUMERO FALLIMENTI TOTALI: "+this.failures;
		String r3 = "ISSUES("+this.issuesList.size()+"): ";
		for(Event ee: this.issuesList) {
			r3+= ee.getIstance().stampa()+" ";
		}
		r3.strip();
		int mancanti = 49-this.concluded.size()-this.issuesList.size();
		String r4 = "NUMERO TEST NON ESEGUITI: "+mancanti;
		String r5 = "ORA DI INIZIO: "+this.startDay+" ORA DI FINE: "+this.endSimulationTime;
		RowIstances ritorno = new RowIstances(r1, r2, r3, r4, r5);
		
		return ritorno;
		
	}
	
	
}
