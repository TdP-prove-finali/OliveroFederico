package it.polito.tdp.provaFinale.model;

import java.util.Comparator;

public class IstancesComparator implements Comparator<Istance> {

	@Override
	public int compare(Istance o1, Istance o2) {
		// TODO Auto-generated method stub
//		if(o1.getProdotto().contains("492FXA") && !o2.getProdotto().contains("492FXA"))
//			return -1;
//		else if(!o1.getProdotto().contains("492FXA") && o2.getProdotto().contains("492FXA"))
//			return 1;
//		else	
			return -(o1.getDurataMedia()-o2.getDurataMedia());
	}

	
	
}
