<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboApplication"%>
<%
	Logger logger = Logger.getLogger(NemboConstants.LOGGIN.LOGGER_NAME + ".presentation");
	String host = InetAddress.getLocalHost().getHostName();
	ResourceBundle res = ResourceBundle.getBundle("config");
	String servers= res.getString("nembo.gestione.servers");
	logger.debug("["+host+"] :: RICHIAMATA RELOAD.JSP"); 
	
	if(servers!= null && servers.indexOf(host)>0)
	{		
		NemboUtils.APPLICATION.reloadCDU();
		logger.info("["+host+"] :: AGGIORNATO ELENCO CDU");	
		NemboUtils.APPLICATION.loadSyncSegnapostiStampe();
		logger.info("["+host+"] :: AGGIORNATO SEGNAPOSTO STAMPE");	
	}
	
%>
