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
 * $Id: SystemGlobals.java,v 1.1 2004/06/01 19:47:20 pieter2 Exp $
 */
package net.jforum.util.preferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;


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
public class SystemGlobals implements VariableStore
{
    private static SystemGlobals globals;
    
    private String defaultConfig;
    private String installationConfig;
    
    private Properties defaults;
    private Properties installation;
    private Properties queries;
    
    private VariableExpander expander;
    
    /**
     * Initialize the global configuration
     * 
     * @param appPath The application path (normally the path to the webapp base dir
     * @param defaults The file containing system defaults (when null, defaults to <appPath>/WEB-INF/config/default.conf)
     * @param installation The specific installation realm (when null, defaults to System.getProperty("user"))
     */
	static public void initGlobals(String appPath, String defaults, String installKey) throws IOException {
	    globals = new SystemGlobals(appPath, defaults, installKey);
	}
	
	private SystemGlobals(String appPath, String defaultConfig, String installKey) throws IOException {
	    expander = new VariableExpander(this, "${", "}");

	    if (defaultConfig == null) {
	        defaultConfig = appPath + "/WEB-INF/config/SystemGlobals.properties";
	    }
	    if (installKey == null) {
	        installKey = System.getProperty("user.name");
	    }

	    this.defaultConfig = defaultConfig;
	    defaults = new Properties();
	    
	    defaults.put(ConfigKeys.APPLICATION_PATH, appPath);
	    defaults.put(ConfigKeys.INSTALLATION, installKey);
	    loadDefaultsImpl();
	    
	    
	    installation = new Properties(defaults);
	    this.installationConfig = getVariableValue(ConfigKeys.INSTALLATION_CONFIG); 
	    loadInstallationImpl();

		queries = new Properties();
	}
	
	/**
	 * Seta um valor para determinada propriedade
	 * 
	 * @param field Nome da propriedade
	 * @param value Valor da propriedade
	 * @see #getVariableValue(String)
	 * */
	public static void setValue(String field, String value)
	{
	    globals.setValueImpl(field, value);
	}
	
	private void setValueImpl(String field, Object value) {
	    String defaultValue = (String) defaults.get(field);
	    if (defaultValue == null) {
	        throw new RuntimeException("unknown property: " + field);
	    }
	    
	    if (defaultValue.equals(value)) {
	        installation.remove(field);
	    } else {
	        installation.put(field, value);
	    }
	    expander.clearCache();
	}
	
	public static void setTransientValue(String field, String value) {
	    globals.setTransientValueImpl(field, value);
	}
	
	private void setTransientValueImpl(String field, String value) {
	    defaults.put(field, value);
	    installation.remove(field);
	    expander.clearCache();
	}
	
	/**
	 * Load system defaults
	 * 
	 * @throws IOException
	 */
	public static void  loadDefaults() throws IOException
	{
	    globals.loadDefaultsImpl();
	}

	/**
	 * Load installation defaults
	 * 
	 * @throws IOException
	 */
	public static void loadInstallation() throws IOException
	{
	    globals.loadInstallationImpl();
	}
	
	private void loadDefaultsImpl() throws IOException
	{
	    FileInputStream input = new FileInputStream(defaultConfig);
	    defaults.load(input);
	    input.close();
	    expander.clearCache();
	}

	private void loadInstallationImpl() throws IOException
	{
	    try {
	        FileInputStream input = new FileInputStream(installationConfig);
		    defaults.load(input);
		    input.close();
		    expander.clearCache();
	    } catch (IOException e) {
	        System.err.println("*WARNING* cannot load installation specific properties: " + installationConfig);
	    }
	}
	
	/**
	 * Save installation defaults
	 * 
	 * @throws IOException when the file could not be written
	 */
	public static void saveInstallation() throws IOException
	{
	    globals.saveInstallationImpl();
	}
	
	private void saveInstallationImpl() throws IOException
	{
	    FileOutputStream out = new FileOutputStream(installationConfig);
	    installation.store(out, "Installation specific configuration options\n" +
	            "# Please restart the JForum webapplication after editing this file.");
	    out.close();
	}
	

	/**
	 * Pega o valor de alguma propriedade
	 * 
	 * @param field Nome da propriedade a pegar o valor
	 * @return String contendo o valor da propriedade, ou null caso nao seja encontrada
	 * @see #setValue(String, String)
	 * */
	public static String getValue(String field)
	{
	    return globals.getVariableValue(field);
	}
	
	/**
	 * Retrieve an integer-valued configuration field
	 * 
	 * @param field Name of the configuration option
	 * @return The value of the configuration option
	 * @exception NullPointerException when the field does not exists
	 */
	public static int getIntValue(String field) {
	    return Integer.parseInt(getValue(field));
	}
	
	/**
	 * Retrieve an boolean-values configuration field
	 * 
	 * @param field name of the configuration option
	 * @return The value of the configuration option
	 * @exception NullPointerException when the field does not exists
	 */
	public static boolean getBoolValue(String field) {
	    return getValue(field).equals("true");
	}
	
	
	/**
	 * Return the value of a configuration value as a variable. Variable expansion is performe
	 * on the result.
	 * 
	 * @param field The field name to retrieve
	 * @return The value of the field if present  
	 */
	
	public String getVariableValue(String field)
	{
	    String preExpansion = installation.getProperty(field);
	    if (preExpansion == null) {
	        throw new RuntimeException("unknown property: " + field);
	    }
	    return expander.expandVariables(preExpansion);
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
		setValue(ConfigKeys.APPLICATION_PATH, ap);
	}
	
	/**
	 * Pega o diretorio raiz onde a aplicacao esta instalada
	 * 
	 * @return String contendo o path completo para a raiz da aplicacao
	 * @see #setApplicationPath
	 * */
	public static String getApplicationPath()
	{
		return getValue(ConfigKeys.APPLICATION_PATH);
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
		return getValue(ConfigKeys.RESOURCE_DIR);
	}
	
	/**
	* Carrega o arquivo contendo as strings SQL do sistema.
	*
	* @param queryFile Nome do arquivo contendo os SQL	. Precisa ser o caminho completo
	* @throws java.io.IOException
	**/	
	public static void loadQueries(String queryFile) throws IOException
	{
	    globals.loadQueriesImpl(queryFile);
	}

	private void loadQueriesImpl(String queryFile) throws IOException
	{
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
		return globals.getSqlImpl(sql);
	}
	
	private String getSqlImpl(String sql)
	{
	    return queries.getProperty(sql);
	}
	
	/**
	 * Retrieve an iterator that iterates over all known configuration keys
	 * 
	 * @return An iterator that iterates over all known configuration keys
	 */
	public static Iterator fetchConfigKeyIterator()
	{
	    return globals.fetchConfigKeyIteratorImpl();
	}
	
	private Iterator fetchConfigKeyIteratorImpl() {
	    return defaults.keySet().iterator();
	}
	
}
