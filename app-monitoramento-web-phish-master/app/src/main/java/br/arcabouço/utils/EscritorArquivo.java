package br.arcabouço.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class EscritorArquivo {
	
	private boolean append;
	private boolean flushAutomatico;
	private String charset;
	private String filePath;
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	private PrintWriter wr;
	
	public EscritorArquivo () throws FileNotFoundException, UnsupportedEncodingException {
		append = false;
		flushAutomatico = false;
		charset = "UTF-8";
		filePath = "temp.txt";
		
		File arquivo = new File(filePath);
		if(!arquivo.getParentFile().exists()) {
			arquivo.getParentFile().mkdirs();
		}
		fos = new FileOutputStream(arquivo, append);
		osw = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osw);
        wr = new PrintWriter(bw, flushAutomatico);
	}
	
	public EscritorArquivo (String filePath,boolean append , boolean flushAutomatico,String charset) throws FileNotFoundException, UnsupportedEncodingException {
		this.append = append;
		this.flushAutomatico = flushAutomatico;
		this.charset = charset;
		this.filePath = filePath;
		
		File arquivo = new File(filePath);
	
		fos = new FileOutputStream(arquivo, append);
		osw = new OutputStreamWriter(fos, charset);
        bw = new BufferedWriter(osw);
        wr = new PrintWriter(bw, flushAutomatico);
        
        //System.out.println(filePath);
	}
	
	
	public void escreveArquivo(String texto) {
        wr.write(texto);
    }
	
	public void close() throws IOException {
		wr.close();
		bw.close();
		osw.close();
		fos.close();
	}
	
}
