<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<!DOCTYPE html>
<html>
<head>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet" media="screen" /> 
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" integrity="sha384-UM1JrZIpBwVf5jj9dTKVvGiiZPZTLVoq4sfdvIe9SBumsvCuv6AHDNtEiIb5h1kU" crossorigin="anonymous"></script>
	<script src="/nembopratiche/js/nemboReportistica.js"></script>
	<style type="text/css">
	.dettChart {
    width: 100%;
    height: 700px;
}
	</style>
</head>
<body>
	<div class="container-fluid" id="content" style="margin-bottom:5em;width: 100%; height: 700px">
		<div id="onePageChartContainer" align="center"></div>
		<script type="text/javascript"> createNewChart(${dettIdElencoQuery},'onePageSingleChart','onePageChartContainer',true,false);</script>
	</div>
	
</body>	
</html>