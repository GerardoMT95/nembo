
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="container-fluid">
		
		
		
		<table class="table table-hover table-striped table-bordered tableBlueTh">
			<thead>
				<tr>
					<th>Stato</th>
					<th>Dal</th>
					<th>Utente</th>
					<th>Note</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${elenco}" var="a">
					<tr>
						<td class="noPadding"><c:out value="${a.descrizione}"></c:out></td>
						<td class="noPadding"><fmt:formatDate value="${a.dataInizio}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
						<td class="noPadding"><c:out value="${a.descUtenteAggiornamento}"></c:out></td>
						<td class="noPadding"><c:out value="${a.note}"></c:out></td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
				
			
		<c:if test="${elencoGruppi!=null}">	
			<table class="table table-hover table-striped table-bordered tableBlueTh">
				<thead>
					<tr>
						<th>Operativit&agrave;</th>
						<th>Dal</th>
						<th>Utente</th>
						<th>Motivazioni</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${elencoGruppi}" var="a">
						<tr>
							<td class="noPadding"><c:out value="${a.flagGruppoChiusoDecod}"></c:out></td>
							<td class="noPadding"><fmt:formatDate value="${a.dataInizio}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
							<td class="noPadding"><c:out value="${a.utenteAggiornamento}"></c:out></td>
							<td class="noPadding"><c:out value="${a.motivazioni}"></c:out></td>
						</tr>
					</c:forEach>			
				</tbody>
			</table>
		</c:if>	
			
				
				
		<div class="form-group puls-group" style="margin-top: 1.5em">
			<div class="col-sm-12">
				<button type="button" data-dismiss="modal" class="btn btn-default" >Chiudi</button>
			</div>
		</div>
	</div>