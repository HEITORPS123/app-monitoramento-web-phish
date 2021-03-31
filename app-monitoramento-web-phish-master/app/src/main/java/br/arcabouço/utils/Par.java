package br.arcabouço.utils;

public class Par implements Comparable<Par> {
	private final String x;
	private final float y;
	
	public Par(String x,float req) {
		this.x = x;
		this.y = req;
	}
	
	public String primeiroValor() {
		return x;
	}
	
	public float segundoValor() {
		return y;
	}

	@Override
	public int compareTo(Par arg0) {
		if(y == arg0.y) {  
			return 0;
		}else if(y > arg0.y) {
			return 1;  
		}else{  
			return -1;  
		} 
	}
	
}
