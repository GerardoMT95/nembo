package it.csi.nembo.nembopratiche.presentation.quadro.coltureaziendali;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.nembo.nembopratiche.business.IQuadroNemboEJB;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.presentation.BaseController;


public abstract class CUNEMBO305BaseController extends BaseController
{
	  @Autowired
	  protected IQuadroNemboEJB quadroNemboEJB = null;  
	
	  protected Map<Long,StringBuilder> getMapIdSuperficieColturaAnomalia(long idProcedimentoOggetto, HttpServletRequest request) throws InternalUnexpectedException
	  {
		  Map<Long,StringBuilder> mapIdSuperficieColturaAnomalia = new HashMap<Long,StringBuilder>();
		  List<ControlloColturaDTO> elencoControlloColtura = quadroNemboEJB.getListControlloColtura(idProcedimentoOggetto, getArrayIdSuperficieColtura(request));
		  for(ControlloColturaDTO cc : elencoControlloColtura)
		  {
			  if(mapIdSuperficieColturaAnomalia.containsKey(cc.getIdSuperficieColtura()))
			  {
				  mapIdSuperficieColturaAnomalia
				  		.get(cc.getIdSuperficieColtura())
				  		.append("; ")
				  		.append(cc.getDescrizioneAnomalia());
			  }
			  else
			  {
				  StringBuilder sb = new StringBuilder(cc.getDescrizioneAnomalia());
				  mapIdSuperficieColturaAnomalia.put(cc.getIdSuperficieColtura(), sb);
				  Map<String,Boolean> map = new HashMap<String,Boolean>();
				  if(cc.getBloccante() != null && !cc.getBloccante().equals(""))
				  {
					  map.put(cc.getBloccante(),Boolean.TRUE);
				  }
			  }
		  }
		  return mapIdSuperficieColturaAnomalia;
	  }
	  
	  protected abstract long[] getArrayIdSuperficieColtura( HttpServletRequest request);
}
