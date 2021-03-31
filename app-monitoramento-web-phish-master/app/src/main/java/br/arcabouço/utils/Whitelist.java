package br.arcabouço.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Whitelist {
	private String dir;
	private Map<String,Integer> whitelistedUrls;
	
	public Whitelist() {
		whitelistedUrls = new HashMap<String,Integer>();
		dir = "/home/tlhop/aplicacao_urls/urls/whitelist/";
		getWhitelistedUrls();
	}
	
	public Whitelist(String dir) {
		whitelistedUrls = new HashMap<String,Integer>();
		this.dir = dir;
		getWhitelistedUrls();
	}
	
	public void getWhitelistedUrls () {
		File[] arquivos = null;
		File repo = new File(dir);
		if ( repo.isDirectory() ) {
			arquivos = repo.listFiles();
			Arrays.sort(arquivos, Comparator.comparingLong(File::lastModified));
		}else {
			
		}
		
		Charset charset = Charset.forName("UTF-8");
		for (File arquivo : arquivos) {
			try {
				List<String> linhas = Files.readAllLines(arquivo.toPath(),charset);
				for (String linha : linhas) {
					whitelistedUrls.put(linha, 0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
	synchronized public boolean isInWhitelist(String url) {
		return whitelistedUrls.containsKey(url);
	}
	
}
