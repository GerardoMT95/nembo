<%@page import="it.csi.nembo.nembopratiche.util.NemboUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.FormatUtils"%>
<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<p:set-cu-info />
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<p:breadcrumbs cdu="${useCaseController}" />
	<p:messaggistica />
	<p:testata cu="${useCaseController}" />
	<form name="mainForm" method="post" action="${action}">
	  <input type="hidden" name="submitted" value="true" />
		<div class="container-fluid" id="content">
			<m:panel id="panelModificaInterv">
				<table summary="Elenco Interventi" class="table table-hover table-bordered table-condensed tableBlueTh" data-show-columns="true">
					<thead>
						<tr>
							<c:if test="${progressivo}">
								<th>Progr.</th>
							</c:if>
							<th>Intervento</th>
							<th>Ulteriori informazioni</th>
							<th>Dato/Valore/UM</th>
							<c:if test="${importoUnitario}">
								<th>Importo unitario</th>
							</c:if>
							<th>Importo</th>
							<c:if test="${importoAmmesso}">
               <th>Spesa ammessa</th>
              </c:if>
							<th>Attivit&agrave;</th>
							<th>Partecipanti</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interventi}" var="intervento">
							<tr>
								<c:if test="${progressivo}">
									<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}">${intervento.htmlForProgressivo()}</td>
								</c:if>
								<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}">${intervento.descIntervento}<input type="hidden" name="id"
									value="${intervento.id}" />
								</td>
								<td style="vertical-align: middle" rowspan="${intervento.misurazioneIntervento.size()}"><m:textarea name="ulteriori_informazioni_${intervento.id}"
										preferRequestValues="${preferRequest}" id="ulteriori_informazioni_${intervento.id}">${intervento.ulterioriInformazioni}</m:textarea></td>
								<td style="vertical-align: middle"><c:if test="${intervento.misurazioneIntervento[0].misuraVisibile}">
										<m:textfield name="valore_${intervento.id}_0" id="valore_${intervento.id}" maxlength="20" onkeyup="calcolaImporto(${intervento.id})"
											onchange="calcolaImporto(${intervento.id})" preferRequestValues="${preferRequest}" value="${intervento.misurazioneIntervento[0].valore}">
											<m:input-addon left="true">${intervento.misurazioneIntervento[0].descMisurazione}</m:input-addon>
											<m:input-addon left="false">${intervento.misurazioneIntervento[0].codiceUnitaMisura}</m:input-addon>
										</m:textfield>
									</c:if></td>
								<c:if test="${importoUnitario}">
									<td style="vertical-align: middle; width: 176px" rowspan="${intervento.misurazioneIntervento.size()}"><c:if
											test="${intervento.flagGestioneCostoUnitario=='S'}">
											<m:textfield cssClass="importo_unitario" name="importo_unitario_${intervento.id}" id="importo_unitario_${intervento.id}"
												preferRequestValues="${preferRequest}" type="euro" maxlength="13" value="${intervento.importoUnitario}" onkeyup="calcolaImporto(${intervento.id})"
												onchange="calcolaImporto(${intervento.id})" />
										</c:if></td>
								</c:if>
								<td style="vertical-align: middle; width: 176px" class="numero" rowspan="${intervento.misurazioneIntervento.size()}"><c:choose>
										<c:when test="${intervento.flagGestioneCostoUnitario!='S'}">
											<m:textfield name="importo_${intervento.id}" id="importo_${intervento.id}" preferRequestValues="${preferRequest}" type="euro" maxlength="13"
												value="${intervento.importo}" />
										</c:when>
										<c:otherwise>
											<label id="lbl_importo_${intervento.id}">0,00</label> &euro;
								</c:otherwise>
									</c:choose></td>
                  <c:if test="${importoAmmesso}">
                  <td style="vertical-align: middle" class="numero" rowspan="${intervento.misurazioneIntervento.size()}">
                     <m:textfield name="importo_${intervento.id}" id="importo_${intervento.id}" preferRequestValues="${preferRequest}" type="euro" maxlength="13"
                        value="${intervento.importoAmmesso}" disabled="true"/>
                  </td>
                </c:if>
								<td style="vertical-align: middle; min-width: 150px"><m:select list="${attivita}" name="id_attivita_${intervento.id}"
										id="id_attivita_${intervento.id}" preferRequestValues="${preferRequest}" onchange="onChangeAttivita(${intervento.id})"
										selectedValue="${intervento.idAttivita}" /></td>
								<td style="vertical-align: middle; min-width: 150px">
								<c:set var="idIntervento" value="${intervento.id}"/>
								<c:set var="partecipanti" value="${mapAttivitaPartecipanti[intervento.idAttivita]}" />
								<%@include file="../include/partecipanti.jsp"%>
								</td>

							</tr>
							<c:forEach begin="1" end="${intervento.misurazioneIntervento.size()-1}" var="m" varStatus="status">
								<tr>
									<td style="vertical-align: middle"><m:textfield name="valore_${intervento.id}_${status.index}" id="valore_${intervento.id}_${status.index}"
											maxlength="20" value="${intervento.misurazioneIntervento[status.index].valore}" preferRequestValues="${preferRequest}">
											<m:input-addon left="true">${intervento.misurazioneIntervento[status.index].descMisurazione}</m:input-addon>
											<m:input-addon left="false">${intervento.misurazioneIntervento[status.index].codiceUnitaMisura}</m:input-addon>
										</m:textfield></td>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
				<a role="button" class="btn btn-primary" href="../cunembo${cuNumber}l/index.do">Indietro</a>
				<input type="submit" name="confermaModificaInterventi" role="s" class="btn btn-primary pull-right" value="conferma" />
				<br class="clear" />
				<br />
			</m:panel>
		</div>
	</form>
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    function onChangeAttivita(id)
	  {
  	  var idAttivita=$("#id_attivita_"+id).val();
  	  if (idAttivita!="")
   	  {
        ajaxReplaceElement("load_partecipanti_"+id+"_"+idAttivita+".do", null, "id_partecipante_"+id);   
   	  }
  	  else
    	{
      	$("#id_partecipante_"+id).prop('disabled',true);
      	$("#id_partecipante_"+id).val('');
      }
		}
	  
	  function ajaxReplaceElement(page, formData, idToReplace)
	  {
	    $.post(
	  	  page, 
	  	  formData,
	  	  function(data) 
	      {
          $('#'+idToReplace).replaceWith(data);
          
        }
	    ).fail(function(){alert("Errore nel richiamo della funzionalità")});
	  }
    function calcolaImporto(id)
    {
      $n=Number($("#valore_"+id).val().replace(',','.'));
      if (!isNaN($n))
      {
        $importo=Number($("#importo_unitario_"+id).val().replace(',','.'));
        if (!isNaN($importo))
        {
          var txt=Number($importo*$n).formatCurrency();
          $('#lbl_importo_'+id).html(txt);
          return true;
        }
      }
      $('#lbl_importo_'+id).html("0,00");
      return true;
    }
    $('.importo_unitario').each(
        function(index, tag)
        {
          var tagId=tag.id;
          tagId=tagId.substring(tagId.lastIndexOf('_')+1);
          calcolaImporto(tagId);
        });
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />