<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<b:error />

		<table id="importiLiquidatiTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  >
		<thead>
        	<tr>    	
				<th>Voce ripartizione</th>
				<th>Percentuale ripartizione</th>
				<th>Importo ripartito</th>	
        	</tr>
	    </thead>
	    <tbody>
	    	<c:choose>
	    	<c:when test="${!empty importi}">
	    	<c:forEach items="${importi}" var="i">
	    	<tr> 	
	    				<td>${i.voceRipartizione}</td>
	    		    	<td style="text-align:right"><fmt:formatNumber maxFractionDigits="4"> ${i.percentualeRipartizione}</fmt:formatNumber>%</td>
	    		    	<td style="text-align:right">&euro;&nbsp;<fmt:formatNumber maxFractionDigits="2">${i.importoRipartito}</fmt:formatNumber></td>
	    	</tr>
	    	</c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<td colspan = "3" >Non sono presenti ripartizioni per questo importo.</td>
	    	</c:otherwise>
	    	</c:choose>
	    </tbody>
	    </table>
	    
	    	<script src="/nembopratiche/js/nembotableformatter.js"></script>
	    