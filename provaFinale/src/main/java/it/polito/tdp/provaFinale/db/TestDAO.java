package it.polito.tdp.provaFinale.db;

import java.util.List;

import it.polito.tdp.provaFinale.model.Istance;

public class TestDAO {

	public static void main(String[] args) {
		
		IstanceDAO idao = new IstanceDAO();
		List<Istance> istances = idao.getAllIstances();
		
		RunDAO rdao = new RunDAO();
		
		for(Istance i : istances) {
			System.out.println(i+", "+rdao.getAvgSuccessDuration(i));
		}
		
		

	}

}
