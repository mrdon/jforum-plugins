/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.

 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:

 * 1) Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the 
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the 
 * above copyright notice, this list of conditions and 
 * the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor 
 * the names of its contributors may be used to endorse 
 * or promote products derived from this software without 
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT 
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 * 
 * This file creating date: Feb 24, 2003 / 8:25:35 PM
 * net.jforum.util.SystemGlobals.java
 * The JForum Project
 * http://www.jforum.net
 * 
 * $Id: SystemGlobals.java,v 1.2 2004/04/21 23:57:27 rafaelsteil Exp $
 */
package net.jforum.util;

import java.util.HashMap;
import java.util.Properties;

import java.io.IOException;
import java.io.FileInputStream;

/**
  * Guarda variaveis globais do sistema. 
 * Nesta classe estao definidos alguns valores defaults
 * para todo o sistema, como localizacao de arquivos de configuracoes
 * de logs, por exemplo. Note que esta classe nao deve ser usada para
 * propriedades gerais, genericas, de configuracao, mas sim 
 * como um meio de pegar e setar valores para certas coisas
 * que mudam dinamicamente de lugar conforme o sistema onde
 * o programa esta rodando.
 * <br>
 * Por exemplo, arquivos de configuracao de logs podem ser estaticamente
 * programados para serem encontrados em /usr/local/seila, mas e se
 * quando o sistema for enviado para outra maquina este diretorio nao 
 * existir? Um outro exepmlo: os arquivos podem estar em um diretorio X
 * se a aplicacao esta rodando como Desktop, e em um diretorio Y se 
 * estiver rodando como Servlet. 
 * <br>
 * Deixar tais configucoes em um arquivo Properties ( ou XML ) em disco, com 
 * a localizacao de cada um, torna-se de manutencao dificil, uma vez que
 * uma alteracao em tais arquivos seria necessaria ao enviar o programa
 * para outro servidor ou maquina. 
 * Logicamente da para automatizar a terefa com Ant, o que nao eh uma saida
 * ruim, mas por enquanto fica assim.
 * <br>
 * Porem, fica claro que ao usar esta classe torna-se o sistema propicio
 * a erros, uma vez que a diretiva pode ser definica com um nome mas
 * usada com outro, o que ira acarretar em bugs muitas vezes complicados
 * de serem identificados. Use com cuidado, e se possivel, nao use esta classe, 
 * caso uma solucao melhor seja encontrada. 
 * 
 * @author Rafael Steil
 */
public class SystemGlobals 
{
	private static HashMap map;
	private static Properties queries;
	private static Properties i18n;
		
	static {
		map = new HashMap();
	}
	
	private SystemGlobals() { }
	
	/**
	 * Seta um valor para determinada propriedade
	 * 
	 * @param field Nome da propriedade
	 * @param value Valor da propriedade
	 * @see #getValue(String)
	 * */
	public static void setValue(String field, Object value)
	{
		map.put(field, value);
	}
	
	/**
	 * Carrega configuracoes contidas em um arquivo texto.
	 * O arquivo deve ter a forma campo = valor, uma propriedade
	 * por linha.
	 * 
	 * @param file Nome do arquivo a ser carregado
	 * @throws IOException
	 * */
	public static void  loadDefaults(String file) throws Exception
	{
		Properties p = new Properties();
		p.load(new FileInputStream(file));
		map.putAll(p);
	}
	
	/**
	 * Pega o valor de alguma propriedade
	 * 
	 * @param field Nome da propriedade a pegar o valor
	 * @return String contendo o valor da propriedade, ou null caso nao seja encontrada
	 * @see #setValue(String, String)
	 * */
	public static Object getValue(String field)
	{
		return map.get(field);
	}
	
	/**
	 * Seta o diretorio raiz da aplicacao.
	 * <b>Eh necessario incuir a barra no final do nome do diretorio.</b>
	 * 
	 * @param ap String contendo o nome do diretorio onde a aplicacao esta instalada
	 * @see #getApplicationPath
	 * */
	public static void setApplicationPath(String ap)
	{
		setValue("Application.PATH", ap);
	}
	
	/**
	 * Pega o diretorio raiz onde a aplicacao esta instalada
	 * 
	 * @return String contendo o path completo para a raiz da aplicacao
	 * @see #setApplicationPath
	 * */
	public static String getApplicationPath()
	{
		return (String)getValue("Application.PATH");
	}
	
	/**
	 * Seta o diretorio onde os arquivos de configuracoes ou restritos do sistema estao.
	 * Use este metodo para indicar ao sistema onde encontrar o diretorio base dos
	 * arquivos de configuracao, tais como os de database, modulos ou alguma
	 * coisa relacionada.  
	 * <b>Eh necessario incuir a barra no final do nome do diretorio.</b>
	 * 
	 * @param resourceDir Localizacao relativa do diretorio
	 * @see #getApplicationResourceDir
	 * @see #getApplicationPath
	 * */
	public static void setApplicationResourceDir(String resourceDir)
	{
		setValue("Application.ResourceDir", resourceDir);
	}
	
	/**
	 * Pega o nome do diretorio de recursos do sistema.
	 * Este metodo retorna o nome do diretorio onde estao os arquivos
	 * de configuracao do sistema, sempre relativo ao retorno de 
	 * <code>getApplicationPath</code>. 
	 * Caso queira saber o caminho absoluto do diretorio, voce precisa
	 * usar
	 * <blockquote><pre>SystemGlobals.getApplicationPath() + SystemGlobals.getApplicationResourcedir()</pre></blockquote>
	 * 
	 * @return String contendo o nome do diretorio
	 * @see #setApplicationResourceDir
	 * @see #getApplicationPath
	 * */
	public static String getApplicationResourceDir()
	{
		return (String)getValue("Application.ResourceDir");
	}
	
	/**
	* Carrega o arquivo contendo as strings SQL do sistema.
	*
	* @param queryFile Nome do arquivo contendo os SQL	. Precisa ser o caminho completo
	* @throws java.io.IOException
	**/	
	public static void loadQueries(String queryFile) throws IOException
	{
		queries = new Properties();
		queries.load(new FileInputStream(queryFile));
	}

	/**
	 * Pega alguma instrucao SQL com base no nome passado como paramentro
	 * 
	 * @param sql String contendo o nome do SQL no arquivo de configuracoes
	 * @return String SQL, no formato de um <code>PreparedStatement</code>
	 * */
	public static String getSql(String sql)
	{
		return queries.getProperty(sql);
	}
}
