<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="modal-dialog" style="width: 900px">                                                                                                         			
					  <div class="modal-content">                                                                                                      			
					    <div class="modal-header">                                                                                                     			
					      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> 	
					      <h4 class="modal-title" id="myModalLabel">Filtro gruppi procedimento</h4>                                                               		
					    </div>                                                                                                                         				
					    <div class="modal-body">   
					    	<input type="radio" name="popupTipoFiltroGruppi" value="OR" checked="checked" />&nbsp;Procedimenti che contengono almeno un gruppo tra quelli selezionati<br/>
					    	<input type="radio" name="popupTipoFiltroGruppi" value="AND" />&nbsp;Procedimenti che contengono tutti i gruppi selezionati
					    	<table class="myovertable table table-hover table-condensed table-bordered tableBlueTh" style="margin-top:1em">
						    	<thead>
						    		<tr>
						    			<th><input type="checkbox" name="selectAllGruppiChk" id="selectAllGruppiChk" onclick="selectAllGruppi();" /></th>
						    			<th>Gruppo</th>
						    			<th>Stati</th>
						    		</tr>
						    	</thead>
						    	<tbody>
									<c:forEach items="${all_gruppiProcedimento}" var="a" varStatus="i">								
							    		<tr>						    		
							    			<td>							    			
							    			<input type="checkbox" name="popupGrupChk" id="popupGrupChk${a.idGruppoOggetto}" onclick="abilitaStatiGruppi('${a.idGruppoOggetto}')" value="${a.idGruppoOggetto}"  checked="checked" /></td>  
							    			<td><input type="hidden" id="descrChkG${a.idGruppoOggetto}" value="<c:out value='${a.descrizione}'></c:out>" /> <c:out value="${a.descrizione}"></c:out></td>
							    			<td>					    					
							    				<c:forEach items="${a.oggetti}" var="b">
							    					<input type="checkbox" name="popupStatiGrupChk${a.idGruppoOggetto}" data-text="<c:out value='${b.stato}'></c:out>"  value="${b.idStato}" <c:if test="${b.idStato!=0}"> checked="checked"</c:if>  />&nbsp;<c:out value="${b.stato}"></c:out><br/>
							    				</c:forEach>						    				
							    			</td>							    			
							    		</tr>
									</c:forEach>						    		
						    	</tbody>
					    	</table>                                                                                                                      					
					    </div>                                                                                                                         				
					    <div class="modal-footer"> 
						    <div class="puls-group" style="margin-top:1em">
						      <div class="pull-left">  
						        <button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
						      </div>
						      <div class="pull-right">  
						        <button type="button" onclick="if(filtraGruppi()){$('#gruppoModal').modal('hide');}" class="btn btn-primary">Conferma</button>
						      </div>
					    	</div>                                                                                                  									                                            			
					    </div>                                                                                                                         				
					  </div>                                                                                                                           				
					</div>    