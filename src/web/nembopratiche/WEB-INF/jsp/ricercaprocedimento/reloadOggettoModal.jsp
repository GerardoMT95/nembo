<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	
					<div class="modal-dialog" style="width: 900px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Filtro oggetti procedimento</h4>
							</div>
							<div class="modal-body">
								<input type="radio" name="popupTipoFiltroOggetti" value="OR" <c:if test="${tipoFiltroOggetti == 'OR' || empty tipoFiltroOggetti || tipoFiltroOggetti == null}"> checked="checked" </c:if> />&nbsp;Procedimenti
								che contengono almeno un gruppo tra quelli selezionati<br /> <input type="radio" name="popupTipoFiltroOggetti" value="AND"
									<c:if test="${tipoFiltroOggetti == 'AND' }"> checked="checked" </c:if> />&nbsp;Procedimenti che contengono tutti i gruppi selezionati

								<table class="myovertable table table-hover table-condensed table-bordered tableBlueTh" style="margin-top: 1em">
									<thead>
										<tr>
											<th><input type="checkbox" name="selectAllGruppiChk" id=selectAllGruppiChk onclick="selectAllGruppi();" /></th>
											<th>Gruppi</th>
											<th>Stati</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${all_gruppiProcedimento}" var="a" varStatus="i">
											<c:choose>
												<c:when test="${i.index == 0}">
													<c:set var="oldDescrGruppo" scope="session" value="${a.descrizione}" />
													<tr class="info borderInfo">
														<td >														
															<input type="checkbox" name="popupGruppoChk" id="popupGruppoChk_${a.idGruppoOggetto}" onclick="abilitaStatiGruppo('${a.idGruppoOggetto}')"
																value="${a.idGruppoOggetto}"  <c:if test="${fn:length(a.stati)>1}"> checked="checked"</c:if>  <c:if test="${fn:length(a.stati)<=1 }">disabled="disabled" </c:if>/>
														</td>
														<td>																
															<input type="hidden" id="descrGruppoChk_${a.idGruppoOggetto}" value="${a.descrizione}" /> 
															<c:out value="${a.descrizione}" />
														</td>
														<td>
														<a class="content_gruppo_${a.idGruppoOggetto} mostraNascondi_${a.idGruppoOggetto} panel-collapse collapse pull-right" href="javascript:void(0)" data-toggle="collapse" data-target=".content_oggetti_${a.idGruppoOggetto}" onclick="changeText(${a.idGruppoOggetto});">
																	Mostra oggetti</a>
																	
															<div id="tdStatiGruppo_${a.idGruppoOggetto}" class="panel-collapse">
															<c:if test="${fn:length(a.stati)>1 }">
															<c:forEach items="${a.stati}" var="b">
																<input type="checkbox" name="popupStatiGruppoChk_${a.idGruppoOggetto}" data-text="<c:out value='${b.descrizione}'></c:out>"
																	value="${b.idEsito}" <c:if test="${b.idEsito != 0}">checked="checked"</c:if>  />&nbsp;<c:out value="${b.descrizione}"></c:out>
																	<br />
																	</c:forEach>
															</c:if>
															</div>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:if test="${a.descrizione != oldDescrGruppo }">
														<c:set var="oldDescrGruppo" scope="session" value="${a.descrizione}" />
														<tr class="info borderInfo">
															<td>
																<input type="checkbox" name="popupGruppoChk" id="popupGruppoChk_${a.idGruppoOggetto}" onclick="abilitaStatiGruppo('${a.idGruppoOggetto}')"
																	value="${a.idGruppoOggetto}" <c:if test="${fn:length(a.stati)>1}"> checked="checked"</c:if> <c:if test="${fn:length(a.stati)<=1 }">disabled="disabled" </c:if>/>

															</td>
															<td>
																<input type="hidden" id="descrGruppoChk_${a.idGruppoOggetto}" value="${a.descrizione}" /> 
																<c:out value="${a.descrizione}" />
															</td>
															<td>
															
															<a class="pull-right content_gruppo_${a.idGruppoOggetto} mostraNascondi_${a.idGruppoOggetto} panel-collapse collapse" href="javascript:void(0)" data-toggle="collapse" data-target=".content_oggetti_${a.idGruppoOggetto}" onclick="changeText(${a.idGruppoOggetto});">
																	Mostra oggetti</a>
																		
															<div id="tdStatiGruppo_${a.idGruppoOggetto}" class="panel-collapse">
																<c:if test="${fn:length(a.stati)>1 }">
																<c:forEach items="${a.stati}" var="b">
																<input type="checkbox" name="popupStatiGruppoChk_${a.idGruppoOggetto}" data-text="<c:out value='${b.descrizione}'></c:out>"
																	value="${b.idEsito}" <c:if test="${b.idEsito != 0}"> checked="checked"</c:if> />&nbsp;<c:out value="${b.descrizione}"></c:out>
																	<br /></c:forEach>
																	</c:if>
																	</div>
															</td>
														</tr>
													</c:if>
												</c:otherwise>
											</c:choose>
												<tr class="content_oggetti content_oggetti_${a.idGruppoOggetto} panel-collapse collapse blueColor">
													<td colspan="3" class="info">
														<div class="innerTable"><table class="myovertable table table-hover table-condensed table-bordered tableBlueTh" >
															<thead>
																<tr>
																	<th>
																		<input type="checkbox" name="selectAllOggettiGruppo" id="selectAllOggettiGruppo_${a.idGruppoOggetto}" onclick="selezionaAllOggettiGruppo('${a.idGruppoOggetto}');" />
																		<!--  <a href="javascript:void(0)" data-toggle="collapse" data-target="#content_oggetti_${a.idGruppoOggetto}" onclick="changeIcon(${a.idGruppoOggetto})">
																			<i id="ico_${a.idGruppoOggetto}" class="icon-expand" style="color:white;text-decoration:none;"></i>
																		</a>-->
																	</th>
																	<th>Oggetti</th>
																	<th>Esiti</th>
																</tr>
															</thead>
															<tbody id="content_oggetti_${a.idGruppoOggetto}" class="content_oggetti content_oggetti_${a.idGruppoOggetto} panel-collapse collapse in">
																<c:forEach items="${a.oggetti}" var="o">
																<c:if test="${fn:length(o.esitiOggetto)>0}">
																	<tr>
																	<td>
																		<input type="checkbox" name="popupOggChk" id="popupOggChk_${a.idGruppoOggetto}_${o.idLegameGruppoOggetto}" onclick="abilitaStatiOggetto('${a.idGruppoOggetto}','${o.idLegameGruppoOggetto}')"
																		value="${o.idLegameGruppoOggetto}"  <c:if test="${a.selected}">disabled="disabled"</c:if>/>
																	</td>
																	<td>
																		<input type="hidden" id="descrChk_${o.idLegameGruppoOggetto}" value="${o.descrizione}" /> 
																		<c:out value="${o.descrizione}"></c:out>
																	</td>
																	<td>
																		<c:forEach items="${o.esitiOggetto}" var="c">
																			<input type="checkbox" name="popupStatiChk_${o.idLegameGruppoOggetto}" data-text="<c:out value='${c.descrizione}'></c:out>" value="${c.idEsito}"
																				<c:if test="${c.idEsito!=0}"> checked="checked"</c:if> />&nbsp;<c:out value="${c.descrizione}"></c:out>
																			<br />
																		</c:forEach>
																	</td>
																	</tr></c:if>
																</c:forEach>
															</tbody>
														</table></div>
													</td>
											<tr><td colspan="5"></td></tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<div class="puls-group" style="margin-top: 1em">
									<div class="pull-left">
										<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
									</div>
									<div class="pull-right">
										<button type="button" onclick="if(filtraGruppi()){$('#oggettoModal').modal('hide');}" class="btn btn-primary">Conferma</button>
									</div>
								</div>
							</div>
						</div>
					</div>