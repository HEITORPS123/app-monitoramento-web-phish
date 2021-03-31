package br.arcabouço.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonitorMemoria implements Runnable {
	
	private AtomicBoolean reiniciarProcessos;
	
	public MonitorMemoria(AtomicBoolean rp) {
		reiniciarProcessos = rp;
	}
	
	public void run() {
		int nReinicios = 0;
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				return;
			}
			
			Process p;
			try {
				p = Runtime.getRuntime().exec("free -t -m");
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			String output = "";
			String tokens = "";
			
			try {
				while ((line = buf.readLine()) != null) {
					output += line + "\n";
				}
				tokens = output.split("\n")[1];
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			String[] lista_output = tokens.split("\\s+");
			double mem_percent = ((Double.parseDouble(lista_output[1]) - Double.parseDouble(lista_output[6]))/Double.parseDouble(lista_output[1]))*100;
			if ( mem_percent > 70.0 ) {
				nReinicios++;
				System.out.println("Reiniciando "+nReinicios);
				reiniciarProcessos.set(true);
				try {
					p = Runtime.getRuntime().exec("pkill -9 firefox");
					p.waitFor();
					p = Runtime.getRuntime().exec("pkill -9 geckodriver");
					p.waitFor();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println(mem_percent);		
		}
	}
}
