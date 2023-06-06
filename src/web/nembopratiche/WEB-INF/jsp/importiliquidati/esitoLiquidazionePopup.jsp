<%@taglib prefix="b" uri="/WEB-INF/bootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<b:error />
<div class="responsive-table table-responsive">
       <table id="importiLiquidatiTable" class="bootstrap-table table table-hover table-striped table-bordered tableBlueTh "
		  >
		<thead>
        	<tr>
				<th>Fondo</th>
				<th>Anno esercizio</th>	
				<th>Numero decreto</th>	
				<th>Data decreto</th>	
				<th>Numero mandato</th>
				<th>Data mandato</th>	
				<th>Importo pagato</th>	
				<th>Importo recupero</th>	
        	</tr>
	    </thead>
	    <tbody>
	    	<tr>
	    				<td>${importoEsito.codiceFondo}</td>
	    				<td><span class ="pull-right">${importoEsito.annoEsercizio}</span></td>
	    				<td><span class ="pull-right">${importoEsito.numeroDecreto}</span></td>
	    				<td>${importoEsito.dataCreazioneDecretoStr}</td>
	    				<td><span class ="pull-right">${importoEsito.numeroMandato}</span></td>
	    		    	<td>${importoEsito.dataCreazioneMandatoStr}</td>
	    		    	<td><span class ="pull-right">&euro;&nbsp;<fmt:formatNumber maxFractionDigits="2">${importoEsito.importoPagato}</fmt:formatNumber></span></td>
	    		    	<td><span class ="pull-right">&euro;&nbsp;<fmt:formatNumber maxFractionDigits="2">${importoEsito.importoRecupero}</fmt:formatNumber></span></td>
	    	</tr>
	    </tbody>
	    </table>
	    </div>