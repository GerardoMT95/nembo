Number.prototype.formatCurrency = function(c){
	  let n = this, 
	      c = isNaN(c = Math.abs(c)) ? 2 : c, 
	      d = "," , 
	      t = ".", 
	      s = n < 0 ? "-" : "", 
	      i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", 
	      j = (j = i.length) > 3 ? j % 3 : 0;
	     return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	   };

function preferNumberFormatterSvg($value)
{
  try
  {
    let $number=Number($value);
    if (isNaN($number))
    {
      return $value;
    }
    return $number.formatCurrency(0);
  }
  catch(e)
  {
	  return $value;
  }
}

function createNewChart(idElencoQuery, className, idContainer, isPopup, showStampa)
	{
		let newGrafBlock = document.createElement('div');
		let newInfoBlock = document.createElement('div');
		let newContainerBlock = document.createElement('div');
		newContainerBlock.className = className;
		newGrafBlock.className = "dettChart";
		newInfoBlock.className = "infoChart";
        newGrafBlock.innerHTML = "<div align=\"center\"><span class=\"please_wait\" style=\"vertical-align: middle\"></span></div>";
       
        
        
        let cnt = document.getElementById(idContainer);
        newContainerBlock.appendChild(newInfoBlock);
        newContainerBlock.appendChild(newGrafBlock);
        cnt.appendChild(newContainerBlock);
        
        
        
        
		$.ajax({
			  url: 'https://www.google.com/jsapi?callback',
			  cache: true,
			  dataType: 'script',
			  success: function(){
			    google.load('visualization', '1', {packages:['corechart','table','bar','geochart','gauge'], 'callback' : function()
			      {
			    	$.ajax({
			              url: "getDettaglioGrafico_"+idElencoQuery+".do",
			              dataType: "json",
			              async: false,
			              success: function(jsonObj) 
			              {
			            	  let data = new google.visualization.DataTable(jsonObj.jsonData);
			            	  newGrafBlock.innerHTML = "";
			                  
			            	  if(jsonObj.idTipoVisualizzazione == 1) // Torta
			                  {
			            	   let options;
		                	   if(!isPopup)
	                		   {
			                	   options = {
			                			   sliceVisibilityThreshold:0,
						                    title: jsonObj.descrBreve,
						                    pieHole: 0.4,
						                    is3D:true
						                  };	
	                		   }
		                	   else
		                	   {
			                	   options = {
			                			   sliceVisibilityThreshold:0,
						                    title: jsonObj.descrCompleta,
						                    pieHole: 0.4,
						                    is3D:true
						                  };	
		                	   }
		                	   
			                   let chart = new google.visualization.PieChart(newGrafBlock);
			                   chart.draw(data, options);

			                   if(isPopup)
	                		   {	
			                	   let total = 0;
			                       for (let i = 0; i < data.getNumberOfRows(); i++) {
			                           total += data.getValue(i, 1);
			                       }
			                	   //Mostro il dettaglio dei valori, pre evitare di dover andare sopra al grafico col mouse
			                       let labelSelector = '> g:eq(1) g g text:nth-last-child(1)';
				                   let svg = $('svg', newGrafBlock );
				                   $(labelSelector, svg).each(function (i, v) {
				                     let value = data.getValue(i, 1);
				                     let percent = Number(100 * value / total).toFixed(1);
				                     let newLabel = $(this).text() + ' '+preferNumberFormatterSvg(value)+' (' +percent+'%)';
				                     $(this).text( newLabel );
				                   });
	                		   }
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 2)//  Istogramma Verticale
			                  {
			                	  let view = new google.visualization.DataView(data);
			                      view.setColumns([0, 1,
			                                       { calc: "stringify",
			                                         sourceColumn: 1,
			                                         type: "string",
			                                         role: "annotation" }]);
			                      let options2;
			                      if(!isPopup)
		                		   {	                      
			                	 options2 = {
			                	            title: jsonObj.descrBreve,
			                	            colors: ['green'],
			                	            orientation: 'horizontal',
			                	            legend: { position: "none" },
			                	            hAxis: { 
			                	                slantedText: true, 
			                	                slantedTextAngle: 70 
			                	            } 
			                	 	}
		                		   }
			                      else{options2 = {
			                	            title: jsonObj.descrCompleta,
			                	            colors: ['green'],
			                	            orientation: 'horizontal',
			                	            legend: { position: "none" },
			                	            hAxis: { 
			                	                slantedText: true, 
			                	                slantedTextAngle: 70 
			                	            } 
			                	 	}
			                      
			                	         };  
			                    let chart = new google.visualization.BarChart(newGrafBlock);
			                    chart.draw(view, options2); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 5)//  Istogramma Orizzontale
			                  {
			                	  let view = new google.visualization.DataView(data);
			                      view.setColumns([0, 1,
			                                       { calc: "stringify",
			                                         sourceColumn: 1,
			                                         type: "string",
			                                         role: "annotation" }]);
			                    if(!isPopup)
		                		   {	
			                	let options2 = {
			                	            title: jsonObj.descrBreve,
			                	            colors: ['green'],
			                	            orientation: 'vertical',
			                	            legend: { position: "none" }
			                	         }; 
		                		   }
			                      else{
			                    	  let options2 = {
				                	            title: jsonObj.descrCompleta,
				                	            colors: ['green'],
				                	            orientation: 'vertical',
				                	            legend: { position: "none" },
				                	            chartArea: {
				                	            	   width: '30%' 
				                	            	}
				                	         };   
			                      }
			                	
			                    let chart = new google.visualization.BarChart(newGrafBlock);
			                    chart.draw(view, options2); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 4) // Proienzione
			                  {
			                	  if(!isPopup)
		                		   {	
			                	  let options2 = {
			                	          chart: {
			                	            title: jsonObj.descrBreve,
			                	            subtitle: '',
			                	          },
			                	          bars: 'vertical' // Required for Material Bar Charts.
			                	        }; 
		                		   }
			                	  else
			                		  {
			                		  let options2 = {
				                	          chart: {
				                	            title: jsonObj.descrCompleta,
				                	            subtitle: '',
				                	          },
				                	          bars: 'vertical' // Required for Material Bar Charts.
				                	        }; 
			                		  }
			                	  
			                    let chart = new google.charts.Bar(newGrafBlock);
			                    chart.draw(data, google.charts.Bar.convertOptions(options2)); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 6)//  Istogramma Stacked
			                  {
			                	  if(!isPopup)
		                		   {
			                	let options2 = {
			                	            title: jsonObj.descrBreve,
			                	            chartArea: {width: '50%'},
			                	            fontSize:14,
			                	            isStacked: true
			                	          }; 
		                		   }
			                	  else{
			                		  let options2 = {
				                	            title: jsonObj.descrCompleta,
				                	            chartArea: {width: '50%'},
				                	            fontSize:14,
				                	            isStacked: true
				                	          };  
			                	  }
			                    let chart = new google.visualization.BarChart(newGrafBlock);
			                    chart.draw(data, options2); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 7)//  GeoChart Regioni
			                  {
	                              let options2 = {
	                                region: 'IT',
	                                displayMode: 'markers',
	                                resolution:'provinces',
	                                colorAxis: {colors: ['green', 'blue']}
	                              };
			                    let chart = new google.visualization.GeoChart(newGrafBlock);
			                    chart.draw(data, options2); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 8)//  Cruscotto
			                  {
			                	  
	                              let options2 = {
	                                      width: 900, height: 340,
	                                      redFrom: 90, redTo: 100,
	                                      yellowFrom:75, yellowTo: 90,
	                                      minorTicks: 5,
	                                      animation:{
	                                          duration: 1000,
	                                          easing: 'linear',
	                                        }
	                                    };
	                              
	                              if(!isPopup)
		                		  {
	                            	  options2 = {
		                                      width: 800, height: 240,
		                                      redFrom: 90, redTo: 100,
		                                      yellowFrom:75, yellowTo: 90,
		                                      minorTicks: 5,
		                                      animation:{
		                                          duration: 1000,
		                                          easing: 'linear',
		                                        }
		                                    };
		                		  }
			                    let chart = new google.visualization.Gauge(newGrafBlock);
			                    chart.draw(data, options2); 
			                  }
			                  else if(jsonObj.idTipoVisualizzazione == 9)//  Diagramma LineChart
			                  {
			                	  if(!isPopup)
		                		   {
			                		  let options2 = {
			                	            title: jsonObj.descrBreve,
			                	            legend: {position: 'top', maxLines: 3},
			                	         };  
		                		   }
			                	  else{
			                		  let options2 = {
				                	            title: jsonObj.descrCompleta,
				                	            legend: {position: 'top', maxLines: 3},
				                	         };  
			                	  } 
			                    let chart = new google.visualization.LineChart(newGrafBlock);
			                    chart.draw(data, options2); 
			                  }
			                  else // idTipoVisualizzazione == 3 ---- Elenco
			                  {
			                	  let table = new google.visualization.Table(newGrafBlock);
			                      table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});  
			                  }
			            	  
			                if(!isPopup)
			          	  	{
			                  	newInfoBlock.innerHTML = "<a href=\"dettaglio_"+idElencoQuery+".do\" style=\"text-decoration: none;\"><i class=\"ico24 ico_magnify\" title=\"Mostra dettagli\"></i></a>";
			          	  	}
			          	  	else
			          	  	{
			          	  		if(showStampa)
			          	  		{
				          	  		try
				          	  		{
				          	  		newInfoBlock.innerHTML = "<a href=\"stampa_"+idElencoQuery+".do\" target=\"_blank\">Stampa...</a> &nbsp;&nbsp;&nbsp;&nbsp; <a href=\"" + chart.getImageURI() + "\" target=\"_blank\">Estrai immagine</a>";
				          	  		}catch(err) {
				          	  		newInfoBlock.innerHTML = "<h2>"+jsonObj.descrBreve+"</h2>" ;
				          	  		}
			          	  		}
			          	  	}
			              }
			         });
			      }
			    });
			  }
		});
	}
