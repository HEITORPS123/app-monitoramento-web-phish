package br.arcabouço.app;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.lightbody.bmp.client.ClientUtil;

import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.Proxy;

import com.google.common.net.HttpHeaders;

import br.arcabouço.utils.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class App {
	
	private int instancias;
	private int timeout;
	private String flag;
	private int limite_requisicoes;
	private String fdir;
	private String dirnome;
	private ConfigCaminhos pathdict;
	private Whitelist whitelist;
	private Whitelist blacklist;
	private File[] arquivos;
	private BlockingQueue<String> listaUrls;
	private AtomicBoolean reiniciarProcessos;
	private AtomicBoolean terminarProcessos;
	
	public App() {
		
		this.instancias = 10;
		this.timeout = 15;
		this.flag = "normal";
		this.dirnome = "/home/heitor/mypython/precompiled_packets/urls/repo";
		listaUrls = new LinkedBlockingDeque<String>();
		reiniciarProcessos =  new AtomicBoolean();
		reiniciarProcessos.set(false);
		terminarProcessos =  new AtomicBoolean();
		terminarProcessos.set(false);
	}
	
	/* Inicialização de variáveis.*/
	public App(int instancias,int timeout,String flag,int limite_requisicoes) {
		this.whitelist = new Whitelist();
		this.blacklist = new Whitelist("/home/tlhop/aplicacao_urls/urls/blacklist/");
		this.instancias = instancias;
		this.timeout = timeout;
		this.flag = flag;
		this.limite_requisicoes = limite_requisicoes;
		
		if("teste".equals(this.flag)) {
			this.fdir = "/home/tlhop/aplicacao_urls/urls/teste/finallogs_teste/";
			this.dirnome = "/home/tlhop/aplicacao_urls/urls/teste/repo_teste";
		}else {
			this.fdir = "/home/tlhop/aplicacao_urls/urls/finallogs/";
			this.dirnome = "/home/tlhop/aplicacao_urls/urls/repo";
		}
		listaUrls = new LinkedBlockingDeque<String>();
		reiniciarProcessos =  new AtomicBoolean();
		reiniciarProcessos.set(false);
		terminarProcessos =  new AtomicBoolean();
		terminarProcessos.set(false);
	}
	
	public void configurarCaminhos() {
		pathdict = new ConfigCaminhos();
	}
	
	/* Função que realiza a leitura de arquivos. */
	public void obterArquivos() {
		
		File repo = new File(dirnome);
		if ( repo.isDirectory() ) {
			arquivos = repo.listFiles();
			Arrays.sort(arquivos, Comparator.comparingLong(File::lastModified));
		}else {
			try {
				String data = "Recipiente de urls inexistente: "+ pathdict.getAtributo("data");
				Files.write(Paths.get(pathdict.getAtributo("recip")), data.getBytes());
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		if (arquivos.length == 0) {
			System.exit(0);
		}
		
	}
	
	/* Função que realiza a leitura de URLs. */
	public void obterUrls() {
		Charset charset = Charset.forName("UTF-8");
		for (File arquivo : arquivos) {
			try {
				List<String> linhas = Files.readAllLines(arquivo.toPath(),charset);
				for (String linha : linhas) {
					listaUrls.add(linha);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
			if (!this.flag.contentEquals("teste")) {
				arquivo.delete();
			}
		}
		/*for (int i = 0;i < this.instancias;i++) {
			listaUrls.add("poison_pill");
		}*/
		System.out.println(listaUrls.size());
	}	
	
	/* Função que determina se a aplicação deve parar, realizando
	 * a leitura de um arquivo na pasta shellscripts/sys/operante. */
	public int appOperante() {
		int status = 0;
		int tries = 0;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/home/tlhop/aplicacao_urls/urls/shellscripts/sys/operante"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		String line = null;
		while(tries < 3) {
			try {
				while ((line = br.readLine()) != null) {
				  String[] parts = line.split(" ");
				  status = Integer.parseInt(parts[0]);
				  //System.out.println(status);
				}
				break;
			} catch (IOException e) {
				tries++;
			}
		}
		return status;
	}
	
	/* Função principal. Administa o multithreading */
	@SuppressWarnings("deprecation")
	public void administrarProcessos() {
		SimpleDateFormat dataInicio = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		
		Date data = new Date();
		String dataFormatada = dataInicio.format(data);
		String inicio = "Inicio em "+dataFormatada+"\n";
		try {
			Files.write(Paths.get(fdir+pathdict.getAtributo("inicio")), inicio.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		MonitorMemoria memoryMonitor = new MonitorMemoria(reiniciarProcessos);
		Thread monitor = new Thread(memoryMonitor);
		monitor.start();
		
		List<Thread> listaThreads = new LinkedList<Thread>();
		
		Predicate<Thread> isDead = t -> !t.isAlive();
		
		long tempoInicio = System.nanoTime(); 
		int indice = 0;
		
		while(appOperante() == 1) {
			
			if(terminarProcessos.get()) {
				break;
			}
			
			if(reiniciarProcessos.get()) {
				//terminarProcessos.set(true);
				System.out.println("Esperando");
				for (Thread thread : listaThreads ) {
					try {
						thread.join(600000);
					} catch (InterruptedException e) {
						continue;
					}
				}
				Process pr;
				try {
					pr = Runtime.getRuntime().exec("pkill -9 firefox");
					pr = Runtime.getRuntime().exec("pkill -9 geckodriver");
				} catch (IOException e) {
					e.printStackTrace();
				}
				//terminarProcessos.set(false);
				reiniciarProcessos.set(false);
			}
			listaThreads.removeIf(isDead);
			if(listaThreads.size() >= instancias) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				Processo r = new Processo(listaUrls,terminarProcessos,reiniciarProcessos,indice, pathdict, fdir, whitelist,blacklist,timeout,limite_requisicoes);
				Thread t = new Thread(r);
				listaThreads.add(t);
				t.start();
				System.out.println("Thread "+Integer.toString(indice)+" criada");
				indice += 1;	
			}
		}
		
		for (Thread thread : listaThreads ) {
			try {
				thread.join(600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		monitor.interrupt();
		if (!this.flag.contentEquals("teste")) {
			escreverUrlsRestantes();
		}
		System.out.println("aaaa");
		System.gc();
		
		long tempoFinal = System.nanoTime();
		long tempoDecorrido = tempoFinal - tempoInicio;
		String tempoString = Long.toString(tempoDecorrido) + '\n';
		
		try {
			Files.write(Paths.get(fdir+pathdict.getAtributo("time")), tempoString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void escreverUrlsRestantes() {
		EscritorArquivo restantes = null;
		try {
			restantes = new EscritorArquivo(dirnome+"/urlsrestantes",true,false,"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		while (listaUrls.isEmpty() == false) {
			try {
				String url = listaUrls.take();
				restantes.escreveArquivo(url+"\n");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			restantes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
