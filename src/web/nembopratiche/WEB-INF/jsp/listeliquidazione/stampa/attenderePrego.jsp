<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <b:error />
  <span class="please_wait" style="vertical-align:middle"></span> Attendere prego, generazione della stampa in corso...
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
<form id="reloadForm">
<% String strSeconds=request.getParameter("seconds");
  long seconds=4;
  try
  {
    seconds=new Long(strSeconds);    
  }
  catch(Exception e)
  {
    // Ignoro ==> seconds resta a 4
  }
  if (seconds<256)
  {
    seconds = seconds*2;
  }
%>
  <input type="hidden" name="seconds" value="<%=seconds%>" />;
</form>
<script type="text/javascript">
  setTimeout(function(){ document.getElementById('reloadForm').submit()}, <%=seconds*1000%>);
</script>
