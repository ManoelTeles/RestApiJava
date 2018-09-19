package com.informationsys.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Config {
	
	private static Config instance = null;
	Properties configuracao = new Properties();
	
	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	
	public static Config getInstance(){
		if (instance == null){
			instance = new Config();
		}
		return instance;
	}
	
	protected Config(){
		getProperties();
	}
	
	private void getProperties(){
		try{
			String propertyHome = System.getenv("API_PROPERTIES");  
			if (propertyHome == null){
				propertyHome = this.getClass().getResource("/").toURI().getPath();
			}
			
			logger.debug("API Properties home: {}",propertyHome);
			InputStream file = new FileInputStream(propertyHome+"api_config.properties");
			configuracao.load(file);
			logger.debug("Finalizacao da carga de arquivo de configuracoes");
			if (System.getenv("API_PROPERTIES") != null){
				setLoggerLocation(configuracao.getProperty("logger_properties_location"));
				logger.info("==================****************======================");
				logger.info("                     API  UOL                         ");
				logger.info("                     Versao " + configuracao.getProperty("versao"));
				logger.info("========================================================");
				logger.info("Logger configurado corretamente");
				logger.debug("Logger Properties home: {}",configuracao.getProperty("logger_properties_location"));
			} else {
				setLoggerLocation(this.getClass().getResource("/").toURI().getPath()+"log4j.properties");
				logger.warn("Nao foi possivel utilizar o log4j.properties local. Utilizando o interno");
			}
		} catch(IOException | URISyntaxException e){
			logger.error("Erro ao obter o arquivo de configuracoes: {}",e);
		}
	}
	
	private void setLoggerLocation(String propertiesLocation){
		Properties p  = new Properties();
		try{
			p.load(new FileInputStream(propertiesLocation));
			PropertyConfigurator.configure(p);
		} catch (IOException e){
			logger.error("Erro no carregamento das propriedades do log: {}",e);
		}
	}
	
	public String obterPropriedade(String chave){
		return configuracao.getProperty(chave);
	}

}
