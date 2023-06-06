<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html"/>
<body>
  <span class="fail_big" style="vertical-align:middle;float:left"></span><br /><br />${messaggio}
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html"/>
