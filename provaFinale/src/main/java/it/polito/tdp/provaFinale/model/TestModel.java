package it.polito.tdp.provaFinale.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args)  {

		
		//Model m = new Model();
		
//		for(Istance is : m.istances) {
//			System.out.println(is.toString());
//		}
		
		
		
		//System.out.println("\n\n ----------------------------NUOVA SIMULAZIONE: "+cont+"------------------------\n");;
//		Scheduling s1 = new Scheduling();
//		System.out.println("\n\n SOLUZIONE 1:");
//		long startTime1 = System.nanoTime();
//		List<List<Istance>> ritorno1 = s1.startScheduling(m.getIstances(), false, 38000);
//		long endTime1 = System.nanoTime();
//	    
//	    double elapsedTimeInSeconds1 = (endTime1 - startTime1) / 1e9;
//	    System.out.println("\nTempo impiegato: " + elapsedTimeInSeconds1 + " secondi");
		
	    //m.updateMachines(ritorno1);
	    
//	    List<RowIstances> toPrint = m.scheduleIstances();
//	    for(RowIstances ri : toPrint) {
//	    	System.out.println(ri);
//	    }
//	    List<RowIstances> toPrint2 = m.simulateEvents(0, false, false, false);
//	    for(RowIstances ri : toPrint2) {
//	    	System.out.println(ri);
//	    }
		
//		System.out.println("\n\n SOLUZIONE 2:");
//		SchedulingV2 s2 = new SchedulingV2();
//		long startTime2 = System.nanoTime();
//		s2.startSchedulingV2(m.getIstances(), false, m.vms, 29000);
//		long endTime2 = System.nanoTime();
//	    
//	    double elapsedTimeInSeconds2 = (endTime2 - startTime2) / 1e9;
//	    System.out.println("\nTempo impiegato: " + elapsedTimeInSeconds2 + " secondi");
	    
//		System.out.println("\n\n SIMULAZIONE:");
//		int stab = 1;
////		if(cont>=20)
////			stab = 1;
////		else if(cont>=40)
////			stab = 2;
//	    Simulator ss = new Simulator(m.vms,m.istances, stab, false, false, false);
//	    ss.initialize();
//	    ss.run();
	    //ss.setSuccessRate();
		
		
		
		
//         ---------------------TESTING DI SCHEDULING S1 E S2----------------
		boolean flag = true;
		
		int max = 38000;
		for(int cont=1; cont<6; cont++) {
			
			if(flag)
				break;
			
			Model m = new Model();
			
			if(cont==1)
				System.out.println("\n\n            -----CONFRONTO TRA SCHEDULING V1 E V2-----");
			
			System.out.println("\n           TENTATIVO "+cont+": MAX = "+max);
			
			Scheduling s1 = new Scheduling();
			long startTime1 = System.nanoTime();
			List<List<Istance>> ritorno1 = s1.startScheduling(m.getIstances(), false, max);
			long endTime1 = System.nanoTime();
		    
		    double elapsedTimeInSeconds1 = (endTime1 - startTime1) / 1e9;
		    System.out.println("           SOLUZIONE 1, tempo impiegato: " + elapsedTimeInSeconds1 + " secondi");
			
			
			SchedulingV2 s2 = new SchedulingV2();
			long startTime2 = System.nanoTime();
			s2.startSchedulingV2(m.getIstances(), false, m.vms, max);
			long endTime2 = System.nanoTime();
		    
		    double elapsedTimeInSeconds2 = (endTime2 - startTime2) / 1e9;
		    System.out.println("           SOLUZIONE 2, tempo impiegato: " + elapsedTimeInSeconds2 + " secondi");

		    max -=2000;
			
		}
		
		
		Model m2 = new Model();
		m2.scheduleIstances(false);
		m2.simulateEvents(2, false, false, false, false);
		
		
		
		
		}
	

}
