package it.polito.tdp.provaFinale.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		
		Model m = new Model();
		
		for(Istance is : m.istances) {
			System.out.println(is.toString());
		}
		
		
		Scheduling s1 = new Scheduling();
		System.out.println("\n\n SOLUZIONE 1:");
		long startTime1 = System.nanoTime();
		List<List<Istance>> ritorno1 = s1.startScheduling(m.getIstances(), false, 000);
		long endTime1 = System.nanoTime();
	    
	    double elapsedTimeInSeconds1 = (endTime1 - startTime1) / 1e9;
	    System.out.println("\nTempo impiegato: " + elapsedTimeInSeconds1 + " secondi");
		
		
		System.out.println("\n\n SOLUZIONE 2:");
		SchedulingV2 s2 = new SchedulingV2();
		long startTime2 = System.nanoTime();
		s2.startSchedulingV2(m.getIstances(), false, m.vms, 29000);
		long endTime2 = System.nanoTime();
	    
	    double elapsedTimeInSeconds2 = (endTime2 - startTime2) / 1e9;
	    System.out.println("\nTempo impiegato: " + elapsedTimeInSeconds2 + " secondi");
	}

}
