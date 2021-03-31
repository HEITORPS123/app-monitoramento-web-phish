package br.arcabouço.utils;

import java.util.Date;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

public class ConfigCaminhos {
	
	private String httpLog;
	private String httpException;
	private String recip;
	private String sourcePage;
	private String firefoxException;
	private String accessLog;
	private String tcp;
	private String timeUrls;
	private String time;
	private String filesMade;
	private String cadeiaUrls;
	private String inicio;
	private String data;
	
	public ConfigCaminhos() {
		String data = obterData();
		this.data = data;
		String hostname = obterHostName();
		
		httpLog = data+".http."+hostname;
		httpException = data+".http_exception."+hostname;
		recip = data+".recip."+hostname;
	    sourcePage = data+".source_page."+hostname;
	    firefoxException = data+".firefox_exception."+hostname;
	    accessLog = data+".access_log."+hostname;
	    tcp = data+".tcp."+hostname;
	    timeUrls = data+".time_urls."+hostname;
	    time = data+".time."+hostname;
	    filesMade = data+".files_made."+hostname;
	    cadeiaUrls = data+".cadeia_urls."+hostname;
	    inicio = data+".inicio."+hostname;
	}
	
	public String obterData () {
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyyMMddHHmmss");
		Date data = new Date();
		String dataFormatada = timestamp.format(data);
		return dataFormatada;
	}
	
	public String obterHostName() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			String hostname = ip.getHostName();
			return hostname;
		}catch (UnknownHostException exc) {
			return "";
		}
	}
	
	public String getAtributo(String att) {
		if ("httpLog".equals(att)) {
			return httpLog;
		}else if ("httpException".equals(att)) {
			return httpException;
		}else if ("recip".equals(att)) {
			return recip; 
		}else if ("sourcePage".equals(att)) {
			return sourcePage;
		}else if ("firefoxException".equals(att)) {
			return firefoxException;
		}else if ("accessLog".equals(att)) {
			return accessLog;
		}else if ("tcp".equals(att)) {
			return tcp;
		}else if ("timeUrls".equals(att)) {
			return timeUrls;
		}else if ("time".equals(att)) {
			return time;
		}else if ("cadeiaUrls".equals(att)) {
			return cadeiaUrls;
		}else if ("inicio".equals(att)) {
			return inicio;
		}else if ("data".equals(att)) {
			return data;
		}else {
			return "exception";
		}
	}
	
}
