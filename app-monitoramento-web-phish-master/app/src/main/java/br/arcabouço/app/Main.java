package br.arcabouço.app;

import br.arcabouço.utils.Singleton;

/* Parâmetros:
 * instancias -> numero de processos que acessam urls ao mesmo tempo.
 * timeout -> tempo até que a conexão com uma página expire.
 * flag -> identifica se é uma conexão de teste ou não.*/
public class Main {
	public static void main(String[] args) {
		int instancias = Integer.parseInt(args[0]);
		int timeout = Integer.parseInt(args[1]);
		String flag = args[2];
		int janela_requisicoes = Integer.parseInt(args[3]);
		int limite_requisicoes = Integer.parseInt(args[4]);
		Singleton.getInstance().setParameters(janela_requisicoes,limite_requisicoes);
		
		App aplicacao = new App(instancias,timeout,flag,limite_requisicoes);
		aplicacao.configurarCaminhos();
		aplicacao.obterArquivos();
		aplicacao.obterUrls();
		aplicacao.administrarProcessos();
	}
}
