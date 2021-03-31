package br.arcabouço.utils;

import java.util.List;

import net.lightbody.bmp.core.har.HarEntry;

public class Resposta {
	
	private boolean bloqueado;
	private boolean excecao;
	private String urlLog;
	List<HarEntry> entries;
	
	public Resposta(boolean b1 , boolean b2, String l1) {
		bloqueado = b1;
		excecao = b2;
		urlLog = l1;
	}
	
	public Resposta(boolean b1 , boolean b2, String l1,List<HarEntry> entradas) {
		bloqueado = b1;
		excecao = b2;
		urlLog = l1;
		entries = entradas;
	}
	

	public Boolean getBloqueado() {
		return bloqueado;
	}
	
	public Boolean getExcecao () {
		return excecao;
	}
	
	public String getUrlLog() {
		return urlLog;
	}	
	
	public List<HarEntry> getHar() {
		return entries;
	}
	
	
}
