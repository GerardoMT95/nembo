package it.csi.nembo.nembopratiche.integration;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.nembo.nembopratiche.dto.DecodificaDTO;
import it.csi.nembo.nembopratiche.dto.DocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.dto.DocumentiRichiestiDaVisualizzareDTO;
import it.csi.nembo.nembopratiche.dto.ReferenteProgettoDTO;
import it.csi.nembo.nembopratiche.dto.SezioneDocumentiRichiestiDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.AllevamentiDettaglioPlvDTO;
import it.csi.nembo.nembopratiche.dto.allevamenti.ProduzioneCategoriaAnimaleDTO;
import it.csi.nembo.nembopratiche.dto.assicurazionicolture.AssicurazioniColtureDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDTO;
import it.csi.nembo.nembopratiche.dto.coltureaziendali.ColtureAziendaliDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.danni.DanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.DannoDTO;
import it.csi.nembo.nembopratiche.dto.danni.ParticelleDanniDTO;
import it.csi.nembo.nembopratiche.dto.danni.UnitaMisuraDTO;
import it.csi.nembo.nembopratiche.dto.dannicolture.DanniColtureDTO;
import it.csi.nembo.nembopratiche.dto.fabbricati.FabbricatiDTO;
import it.csi.nembo.nembopratiche.dto.internal.LogParameter;
import it.csi.nembo.nembopratiche.dto.internal.LogVariable;
import it.csi.nembo.nembopratiche.dto.motoriagricoli.MotoriAgricoliDTO;
import it.csi.nembo.nembopratiche.dto.prestitiagrari.PrestitiAgrariDTO;
import it.csi.nembo.nembopratiche.dto.procedimentooggetto.interventi.FiltroRicercaConduzioni;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDTO;
import it.csi.nembo.nembopratiche.dto.scorte.ScorteDecodificaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.ControlloColturaDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioParticellareDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureDettaglioPsrDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColturePlvVegetaleDTO;
import it.csi.nembo.nembopratiche.dto.superficicolture.SuperficiColtureRiepilogoDTO;
import it.csi.nembo.nembopratiche.exception.InternalUnexpectedException;
import it.csi.nembo.nembopratiche.util.NemboConstants;
import it.csi.nembo.nembopratiche.util.NemboUtils;

public class QuadroNemboDAO extends QuadroDAO
{
  private static final String WITH_TMP_ALL_PROD_ZOO_PROD_VEND = " TMP_ALL_PROD_ZOO_PROD_VEND AS (\r\n" + 
		    		"    SELECT 																											\r\n" + 
		    		"        A.ID_CATEGORIA_ANIMALE,\r\n" + 
		    		"        A.DESCRIZIONE_SPECIE_ANIMALE,																				\r\n" + 
		    		"        A.DESCRIZIONE_CATEGORIA_ANIMALE,        \r\n" + 
		    		"        A.ISTAT_COMUNE,\r\n" + 
		    		
		    		"        PZ.PESO_VIVO_MEDIO,\r\n" + 
		    		"        PZ.GIORNATE_LAVORATIVE_MEDIE * A.QUANTITA AS GIORNATE_LAVORATIVE_TOTALI, \r\n" +
		    		"        PZ.EXT_ID_UTENTE_AGGIORNAMENTO, \r\n" +
		    		"        PZ.DATA_ULTIMO_AGGIORNAMENTO, \r\n" +
		    		
		    		"        PZ.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"        PZ.GIORNATE_LAVORATIVE_MEDIE,\r\n" + 
		    		"        PZ.ID_PRODUZIONE_ZOOTECNICA AS PZ_ID_PRODUZIONE_ZOOTECNICA,																					\r\n" + 
		    		"        PV.ID_PRODUZIONE_ZOOTECNICA AS PV_ID_PRODUZIONE_ZOOTECNICA,												\r\n" + 
		    		"        DECODE(PV.ID_PRODUZIONE_ZOOTECNICA, NULL,\r\n" + 
		    		"             0, \r\n" + 
		    		"            (SUM((PV.NUMERO_CAPI * PV.QUANTITA_PRODOTTA - NVL(PV.QUANTITA_REIMPIEGATA,0)) * PV.PREZZO))) AS PLV\r\n" + 
		    		"    FROM																												\r\n" + 
		    		"        TMP_ALLEVAMENTI A,																								\r\n" + 
		    		"        PRODUZIONE_ZOOTECNICA PZ,																						\r\n" + 
		    		"        NEMBO_T_PRODUZIONE_VENDIBILE PV,																				\r\n" + 
		    		"        NEMBO_D_PRODUZIONE P																							\r\n" + 
		    		"    WHERE 																												\r\n" + 
		    		"             A.ID_CATEGORIA_ANIMALE = PZ.EXT_ID_CATEGORIA_ANIMALE														\r\n" + 
		    		"        AND A.ISTAT_COMUNE = PZ.EXT_ISTAT_COMUNE																		\r\n" + 
		    		"        AND PZ.ID_PRODUZIONE_ZOOTECNICA = PV.ID_PRODUZIONE_ZOOTECNICA (+)													\r\n" + 
		    		"        AND P.ID_PRODUZIONE (+) = PV.ID_PRODUZIONE																		\r\n" + 
		    		"    GROUP BY         																									\r\n" + 
		    		"        A.ID_CATEGORIA_ANIMALE,																						\r\n" + 
		    		"        A.DESCRIZIONE_SPECIE_ANIMALE,																					\r\n" + 
		    		"        A.DESCRIZIONE_CATEGORIA_ANIMALE,        \r\n" + 
		    		"        A.ISTAT_COMUNE,\r\n" + 
		    		
		    		"        PZ.PESO_VIVO_MEDIO,\r\n" + 
		    		"        PZ.GIORNATE_LAVORATIVE_MEDIE * A.QUANTITA, \r\n" +
		    		"        PZ.EXT_ID_UTENTE_AGGIORNAMENTO, \r\n" +
		    		"        PZ.DATA_ULTIMO_AGGIORNAMENTO, \r\n" +
		    		
		    		"        PZ.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"        PZ.GIORNATE_LAVORATIVE_MEDIE,\r\n" + 
		    		"        PZ.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"        PV.ID_PRODUZIONE_ZOOTECNICA\r\n" + 
		    		")\r\n";
private static final String WITH_TMP_ALLEVAMENTO_CAT_PROD = " TMP_ALLEVAMENTO_CAT_PROD AS (\r\n" + 
		    		"    SELECT 																											\r\n" + 
		    		"        DAMM.DESCRIZIONE_COMUNE || ' (' || DAMM.SIGLA_PROVINCIA || ')' AS UBICAZIONE_ALLEVAMENTO,						\r\n" + 
		    		"        DAMM.SIGLA_PROVINCIA,																							\r\n" + 
		    		"        DAMM.DESCRIZIONE_COMUNE,																						\r\n" + 
		    		"		 A.CODICE_AZIENDA_ZOOTECNICA, 																					\r\n" +	
		    		"        A.ID_CATEGORIA_ANIMALE,																						\r\n" + 
		    		"        A.ISTAT_COMUNE,																								\r\n" + 
		    		"        A.DESCRIZIONE_SPECIE_ANIMALE,																					\r\n" + 
		    		"        A.DESCRIZIONE_CATEGORIA_ANIMALE,																				\r\n" + 
		    		"        A.QUANTITA,																									\r\n" + 
		    		"        A.UNITA_MISURA_SPECIE,																							\r\n" + 
		    		"        CA.GIORNATE_LAVORATIVE_MEDIE,																					\r\n" + 
		    		"        CA.PESO_VIVO_MEDIO,																							\r\n" + 
		    		"        DA.COEFFICIENTE_UBA,																							\r\n" + 
		    		"        (DA.CONSUMO_ANNUO_UF * A.QUANTITA ) AS UNITA_FORAGGERE,														\r\n" + 
		    		"        P.ID_PRODUZIONE																								\r\n" + 
		    		"    FROM																												\r\n" + 
		    		"        TMP_ALLEVAMENTI A,																								\r\n" + 
		    		"        SMRGAA_V_DECO_ALLEVAMENTI DA,																					\r\n" + 
		    		"        SMRGAA_V_DATI_AMMINISTRATIVI DAMM,																				\r\n" + 
		    		"        NEMBO_D_CATEGORIA_ANIMALE CA,																					\r\n" + 
		    		"        NEMBO_D_PRODUZIONE P																							\r\n" + 
		    		"    WHERE 																												\r\n" + 
		    		"            A.ISTAT_COMUNE = DAMM.ISTAT_COMUNE																			\r\n" + 
		    		"        AND A.ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE														\r\n" + 
		    		"        AND CA.EXT_ID_CATEGORIA_ANIMALE = P.EXT_ID_CATEGORIA_ANIMALE													\r\n" + 
		    		"        AND CA.EXT_ID_CATEGORIA_ANIMALE = DA.ID_CATEGORIA_ANIMALE														\r\n" + 
		    		"\r\n" + 
		    		") ";
private static final String WITH_TMP_ALLEVAMENTI = " TMP_ALLEVAMENTI AS (																							\r\n" + 
		    		"    SELECT 																										\r\n" + 
		    		"        A.CODICE_AZIENDA_ZOOTECNICA,																				\r\n" + 
		    		"        A.ID_CATEGORIA_ANIMALE,																					\r\n" + 
		    		"        A.ISTAT_COMUNE,																							\r\n" + 
		    		"        A.DESCRIZIONE_SPECIE_ANIMALE,																				\r\n" + 
		    		"        A.DESCRIZIONE_CATEGORIA_ANIMALE,																			\r\n" + 
		    		"        A.UNITA_MISURA_SPECIE,																						\r\n" + 
		    		"        SUM(A.QUANTITA) AS QUANTITA																				\r\n" +
		    		"    FROM 																											\r\n" + 
		    		"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,																			\r\n" + 
		    		"        SMRGAA_V_ALLEVAMENTI A																						\r\n" + 
		    		"    WHERE																											\r\n" + 
		    		"        PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO														\r\n" + 
		    		"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
		    		"    GROUP BY																										\r\n" + 
		    		"        A.CODICE_AZIENDA_ZOOTECNICA,																				\r\n" + 
		    		"        A.ID_CATEGORIA_ANIMALE,																					\r\n" + 
		    		"        A.ISTAT_COMUNE,																							\r\n" + 
		    		"        A.DESCRIZIONE_SPECIE_ANIMALE,																				\r\n" + 
		    		"        A.DESCRIZIONE_CATEGORIA_ANIMALE,																			\r\n" + 
		    		"        A.UNITA_MISURA_SPECIE																						\r\n" + 
		    		") ";

private static final String WITH_PRODUZIONE_ZOOTECNICA = "PRODUZIONE_ZOOTECNICA AS(																						\r\n" + 
		    		"    SELECT *																										\r\n" + 
		    		"    FROM NEMBO_T_PRODUZIONE_ZOOTECNICA																				\r\n" + 
		    		"    WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" + 
		    		")";
private static final String THIS_CLASS = QuadroNemboDAO.class.getSimpleName();
  
 
  public List<ScorteDTO> getListScorteByProcedimentoOggetto(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
	String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
	final String QUERY = "SELECT 																\r\n" + 
			"        SM.ID_SCORTA_MAGAZZINO,													\r\n" + 
			"        S.DESCRIZIONE AS DESCRIZIONE_SCORTA,										\r\n" + 
			"        SM.DESCRIZIONE,															\r\n" + 
			"        SM.QUANTITA,																\r\n" + 
			"        UM.ID_UNITA_MISURA,														\r\n" + 
			"        UM.DESCRIZIONE AS DESC_UNITA_MISURA										\r\n" + 
			"FROM NEMBO_T_SCORTA_MAGAZZINO SM,													\r\n" + 
			"     NEMBO_D_SCORTA S,																\r\n" + 
			"     NEMBO_D_UNITA_MISURA UM,														\r\n" + 
			"     NEMBO_T_PROCEDIMENTO_OGGETTO PO												\r\n" + 
			"WHERE SM.ID_SCORTA = S.ID_SCORTA													\r\n" + 
			"      AND UM.ID_UNITA_MISURA = NVL(S.ID_UNITA_MISURA, SM.ID_UNITA_MISURA)			\r\n" + 
			"      AND SM.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO					\r\n" + 
			"      AND SM.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO" 
			;
    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
    try
    {
      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
      return queryForList(QUERY, mapParameterSource, ScorteDTO.class);
    }
    catch (Throwable t)
    {
      InternalUnexpectedException e = new InternalUnexpectedException(t,
          new LogParameter[]
          {
        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
          }, new LogVariable[]
          {}, QUERY, mapParameterSource);
      logInternalUnexpectedException(e, THIS_METHOD);
      throw e;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }
  
	public List<ScorteDTO> getListScorteNonDanneggiateByProcedimentoOggetto(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String QUERY = 
				  "SELECT 																					\r\n" + 
				  "        SM.ID_SCORTA_MAGAZZINO,															\r\n" + 
				  "        S.DESCRIZIONE AS DESCRIZIONE_SCORTA,												\r\n" + 
				  "        SM.DESCRIZIONE,																	\r\n" + 
				  "        SM.QUANTITA,																		\r\n" + 
				  "        UM.ID_UNITA_MISURA,																\r\n" + 
				  "        UM.DESCRIZIONE AS DESC_UNITA_MISURA												\r\n" + 
				  "FROM NEMBO_T_SCORTA_MAGAZZINO SM,														\r\n" + 
				  "     NEMBO_D_SCORTA S,																	\r\n" + 
				  "     NEMBO_D_UNITA_MISURA UM,															\r\n" + 
				  "     NEMBO_T_PROCEDIMENTO_OGGETTO PO														\r\n" + 
				  "WHERE SM.ID_SCORTA = S.ID_SCORTA															\r\n" + 
				  "      AND UM.ID_UNITA_MISURA = NVL(S.ID_UNITA_MISURA, SM.ID_UNITA_MISURA)				\r\n" + 
				  "      AND SM.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO						\r\n" + 
				  "      AND SM.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO							\r\n" + 
				  "      AND SM.ID_SCORTA_MAGAZZINO NOT IN (												\r\n" + 
				  "            SELECT DA.EXT_ID_ENTITA_DANNEGGIATA											\r\n" + 
				  "            FROM NEMBO_T_DANNO_ATM DA													\r\n" + 
				  "            WHERE DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 					\r\n" +
				 			   	     getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)) +"\r\n" + 
				  "            )\r\n"
				  ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource, ScorteDTO.class);
		} catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t,
					new LogParameter[] { new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto) },
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
  
  
  public List<ScorteDTO> getListScorteByIds(long[] arrayIdScortaMagazzino, long idProcedimentoOggetto) throws InternalUnexpectedException
  {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = "SELECT  SM.ID_SCORTA_MAGAZZINO,\r\n" + 
				"        SM.ID_PROCEDIMENTO_OGGETTO,\r\n" + 
				"        SM.ID_SCORTA,\r\n" + 
				"        SM.DESCRIZIONE,\r\n" + 
				"        S.DESCRIZIONE AS DESCRIZIONE_SCORTA,\r\n" + 
				"        SM.QUANTITA,\r\n" + 
				"        NVL(S.ID_UNITA_MISURA, SM.ID_UNITA_MISURA) AS ID_UNITA_MISURA,\r\n" + 
				"        UM.DESCRIZIONE AS DESC_UNITA_MISURA\r\n" + 
				"FROM    NEMBO_T_SCORTA_MAGAZZINO  SM, \r\n" + 
				"        NEMBO_D_SCORTA S,\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM\r\n" + 
				"WHERE   SM.ID_SCORTA = S.ID_SCORTA\r\n" + 
				"        AND UM.ID_UNITA_MISURA = NVL(S.ID_UNITA_MISURA, SM.ID_UNITA_MISURA)\r\n" + 
				"        AND SM.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO\r\n" + 
						 getInCondition("SM.ID_SCORTA_MAGAZZINO", arrayIdScortaMagazzino)
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, ScorteDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ARRAY_ID_SCORTA_MAGAZZINO",arrayIdScortaMagazzino),
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
  }

  
  public ScorteDTO getScortaByIdScortaMagazzino(long idScortaMagazzino) throws InternalUnexpectedException
  {
	String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
    if (logger.isDebugEnabled())
    {
      logger.debug(THIS_METHOD + " BEGIN.");
    }
	final String QUERY = "SELECT 																\r\n" + 
			"        SM.ID_SCORTA_MAGAZZINO,													\r\n" + 
			"        S.DESCRIZIONE AS DESCRIZIONE_SCORTA,										\r\n" + 
			"        S.ID_SCORTA,																\r\n" + 
			"        SM.DESCRIZIONE,															\r\n" + 
			"        SM.QUANTITA,																\r\n" + 
			"        NVL(S.ID_UNITA_MISURA,SM.ID_UNITA_MISURA) AS ID_UNITA_MISURA				\r\n" + 
			"FROM NEMBO_T_SCORTA_MAGAZZINO SM,													\r\n" + 
			"     NEMBO_D_SCORTA S																\r\n" + 
			"WHERE SM.ID_SCORTA = S.ID_SCORTA													\r\n" + 
			" 		AND SM.ID_SCORTA_MAGAZZINO = :ID_SCORTA_MAGAZZINO "		
			;
    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
    try
    {
      mapParameterSource.addValue("ID_SCORTA_MAGAZZINO", idScortaMagazzino, Types.NUMERIC);
      return queryForObject(QUERY, mapParameterSource, ScorteDTO.class);
    }
    catch (Throwable t)
    {
      InternalUnexpectedException e = new InternalUnexpectedException(t,
          new LogParameter[]
          {
        		  new LogParameter("ID_SCORTA_MAGAZZINO",idScortaMagazzino)
          }, new LogVariable[]
          {}, QUERY, mapParameterSource);
      logInternalUnexpectedException(e, THIS_METHOD);
      throw e;
    }
    finally
    {
      if (logger.isDebugEnabled())
      {
        logger.debug(THIS_METHOD + " END.");
      }
    }
  }
  
  
  public long getIdStatoProcedimento(long idProcedimentoOggetto) throws InternalUnexpectedException
  {
	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String  QUERY = "SELECT ID_STATO_OGGETTO\r\n" + 
				"FROM    NEMBO_T_PROCEDIMENTO_OGGETTO PO,\r\n" + 
				"        NEMBO_T_PROCEDIMENTO P\r\n" + 
				"        WHERE   PO.ID_PROCEDIMENTO = P.ID_PROCEDIMENTO\r\n" + 
				"                AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO" 
				
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
  }
  
  public List<DecodificaDTO<Long>> getElencoTipologieScorte() throws InternalUnexpectedException
  {
	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String  QUERY = "SELECT  S.ID_SCORTA AS ID,\r\n" + 
				"        S.DESCRIZIONE AS DESCRIZIONE,\r\n" + 
				"        S.DESCRIZIONE AS CODICE\r\n" + 
				"     \r\n" + 
				"FROM     NEMBO_D_SCORTA S \r\n" + 
				"ORDER BY DESCRIZIONE" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	       return queryForDecodificaLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {}, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
  }
  
  public List<DecodificaDTO<Long>> getUnitaMisuraSelezionata(long idScorta) throws InternalUnexpectedException
  {
	  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = "SELECT  \r\n" + 
				"        UM.ID_UNITA_MISURA AS ID,\r\n" + 
				"        UM.DESCRIZIONE AS DESCRIZIONE,\r\n" + 
				"        UM.DESCRIZIONE AS CODICE\r\n" + 
				"FROM   \r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,\r\n" + 
				"        NEMBO_D_SCORTA S\r\n" + 
				"WHERE   \r\n" + 
				"        UM.ID_UNITA_MISURA = S.ID_UNITA_MISURA\r\n" + 
				"        AND S.ID_SCORTA = :ID_SCORTA" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	        mapParameterSource.addValue("ID_SCORTA", idScorta);
	    	return queryForDecodificaLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_SCORTA", idScorta),
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
  }
  
  public List<DecodificaDTO<Long>> getListUnitaDiMisura() throws InternalUnexpectedException
  {
	    String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = "SELECT  							\r\n" + 
				"        UM.ID_UNITA_MISURA AS ID,				\r\n" + 
				"        UM.DESCRIZIONE AS DESCRIZIONE,			\r\n" + 
				"        UM.DESCRIZIONE AS CODICE				\r\n" + 
				"FROM   										\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM				\r\n" +
				"ORDER BY DESCRIZIONE"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	    	return queryForDecodificaLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {}, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
  }
  
	public Long getUnitaMisuraByScorta(long idScorta) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String  QUERY = "SELECT  						\r\n" + 
				"        S.ID_UNITA_MISURA					\r\n" + 
				"FROM   									\r\n" + 
				"        NEMBO_D_SCORTA S					\r\n" + 
				"WHERE   									\r\n" + 
				"        S.ID_SCORTA = :ID_SCORTA" 
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_SCORTA", idScorta);
			return queryForLongNull(QUERY, mapParameterSource);
		} catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, new LogParameter[] {},
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public long getIdScorteAltro() throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String  QUERY = "SELECT ID_SCORTA FROM NEMBO_D_SCORTA WHERE ID_UNITA_MISURA IS NULL"; 
		
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			return queryForLong(QUERY, mapParameterSource);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
					new LogParameter[] {},
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public long inserisciScorte(long idProcedimentoOggetto, long idScorta, BigDecimal quantita, Long idUnitaMisura, String descrizione) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = "INSERT INTO NEMBO_T_SCORTA_MAGAZZINO 								\r\n" + 
				"    (ID_SCORTA_MAGAZZINO,															\r\n" + 
				"    ID_PROCEDIMENTO_OGGETTO,														\r\n" + 
				"    ID_SCORTA,																		\r\n" + 
				"    DESCRIZIONE,																	\r\n" + 
				"    QUANTITA,																		\r\n" +
				"	 ID_UNITA_MISURA																\r\n)"+ 
				"VALUES (																			\r\n" + 
				"    :SEQ_NEMBO_T_SCORTA_MAGAZZINO,													\r\n" + 
				"    :ID_PROCEDIMENTO_OGGETTO,														\r\n" + 
				"    :ID_SCORTA,																	\r\n" + 
				"    :DESCRIZIONE,																	\r\n" + 
				"    :QUANTITA,																		\r\n" +
				"	 :ID_UNITA_MISURA)" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTScortaMagazzino = 0;
	    try
	    {
	      seqNemboTScortaMagazzino = getNextSequenceValue("SEQ_NEMBO_T_SCORTA_MAGAZZINO");
	      mapParameterSource.addValue("SEQ_NEMBO_T_SCORTA_MAGAZZINO", seqNemboTScortaMagazzino, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_SCORTA", idScorta, Types.NUMERIC);
	      mapParameterSource.addValue("DESCRIZIONE", descrizione, Types.VARCHAR);
	      mapParameterSource.addValue("QUANTITA", quantita, Types.NUMERIC);
	      mapParameterSource.addValue("ID_UNITA_MISURA", idUnitaMisura, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_SCORTA_MAGAZZINO", seqNemboTScortaMagazzino),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_SCORTA", idScorta),
	              new LogParameter("DESCRIZIONE", descrizione),
	              new LogParameter("QUANTITA", quantita),
	              new LogParameter("ID_UNITA_MISURA", idUnitaMisura)
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return seqNemboTScortaMagazzino;
	}
	
	public long modificaScorta(
			ScorteDTO scorta,
			long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"	 UPDATE NEMBO_T_SCORTA_MAGAZZINO 									\r\n" + 
				"    SET 																\r\n" + 
				"    ID_SCORTA = :ID_SCORTA,											\r\n" + 
				"    DESCRIZIONE = :DESCRIZIONE,										\r\n" + 
				"    QUANTITA = :QUANTITA,												\r\n" + 
				"    ID_UNITA_MISURA = :ID_UNITA_MISURA									\r\n" + 
				"    WHERE  ID_SCORTA_MAGAZZINO = :ID_SCORTA_MAGAZZINO					\r\n" +
				"	 AND ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO "	
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTScortaMagazzino = 0;
	    try
	    {
	      mapParameterSource.addValue("ID_SCORTA_MAGAZZINO", scorta.getIdScortaMagazzino(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_SCORTA", scorta.getIdScorta(), Types.NUMERIC);
	      mapParameterSource.addValue("DESCRIZIONE", scorta.getDescrizione(), Types.VARCHAR);
	      mapParameterSource.addValue("QUANTITA", scorta.getQuantita(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_UNITA_MISURA", scorta.getIdUnitaMisura(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_SCORTA_MAGAZZINO", scorta.getIdScortaMagazzino()),
	              new LogParameter("ID_SCORTA", scorta.getIdScorta()),
	              new LogParameter("DESCRIZIONE", scorta.getDescrizione()),
	              new LogParameter("QUANTITA", scorta.getQuantita()),
	              new LogParameter("ID_UNITA_MISURA", scorta.getIdUnitaMisura()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return seqNemboTScortaMagazzino;
	}

	public long eliminaScorteMagazzino(List<Long> listIdScortaMagazzino, long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		"DELETE FROM NEMBO_T_SCORTA_MAGAZZINO WHERE 1=1 " + 
	    		getInCondition("ID_SCORTA_MAGAZZINO", listIdScortaMagazzino) +
	    		" AND ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO "
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_SCORTA_MAGAZZINO",listIdScortaMagazzino, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_SCORTA_MAGAZZINO", listIdScortaMagazzino),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return listIdScortaMagazzino.size();
	}
  

	public int eliminaDanniAssociatiAlleScorteMagazzinoModificateORimosse(List<Long> listIdScortaMagazzino, long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		"DELETE FROM NEMBO_T_DANNO_ATM														\r\n" + 
	    		"WHERE																				\r\n" + 
	    		"    ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO								\r\n" + 
	    		getInCondition("ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA))+"	\r\n" +
	    		getInCondition("EXT_ID_ENTITA_DANNEGGIATA", listIdScortaMagazzino)	
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        	  new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)),
	              new LogParameter("EXT_ID_ENTITA_DANNEGGIATA", listIdScortaMagazzino)
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public long eliminaDanni(List<Long> listIdDannoAtm, long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		"DELETE FROM NEMBO_T_DANNO_ATM WHERE 1=1 " + 
	    		getInCondition("ID_DANNO_ATM", listIdDannoAtm) +
	    		" AND ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO "
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_DANNO_ATM",listIdDannoAtm, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_DANNO_ATM", listIdDannoAtm),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return listIdDannoAtm.size();
	}

	
	public long eliminaDanniConduzioniFromTParticellaDanneggiata(long idProcedimentoOggetto, List<Long> listIdDannoAtm) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    //Attenzione. Non c'è il filtro sul procedimento oggetto. Deve essere fatto precedentemente
	    final String DELETE = 
	    		"DELETE FROM NEMBO_R_PARTICELLA_DANNEGGIATA WHERE 1=1 " +
	    		getInCondition("ID_DANNO_ATM", listIdDannoAtm);
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_DANNO_ATM",listIdDannoAtm, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_DANNO_ATM", listIdDannoAtm),
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	
	public long getNumDanniGiaEsistenti(List<DanniDTO> listDanniDTO, long idProcedimentoOggetto, Integer idDanno) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    List<Long> listIdExtEntitaDanneggiata = new ArrayList<Long>();
	    for(DanniDTO danno : listDanniDTO)
	    {
	    	listIdExtEntitaDanneggiata.add(danno.getExtIdEntitaDanneggiata());
	    }
	    final String QUERY = 
	    		" SELECT COUNT(*)																\r\n" + 
	    		" FROM NEMBO_T_DANNO_ATM														\r\n" + 
	    		" WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO						\r\n" + 
	    		getInCondition("ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(idDanno)) 				 + "\r\n" + 
	    		getInCondition("EXT_ID_ENTITA_DANNEGGIATA", listIdExtEntitaDanneggiata)  
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_DANNO", idDanno, Types.NUMERIC);
	      return queryForLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_DANNO", idDanno)
	          },
	          new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	
	/**
	 * Verifica se un utente che sta inserendo i danni è veramente autorizzato:
	 *  - inserisce danni di una scorta da lui posseduta
	 *  - inserisce danni di un motore agricolo posseduto dalla sua azienda
	 *  - inserisce danni di un fabbricato di sua proprietà rispetto alla dichiarazione di consistenza
	 * @param listDanniDTO
	 * @param idProcedimentoOggetto
	 * @param idDanno
	 * @return 
	 * 			true: se è lecito l'inserimento
	 * 			false: se è illecito l'inserimento
	 * @throws InternalUnexpectedException
	 */
	public boolean isUtenteAutorizzatoInserimentoDanni(List<DanniDTO> listDanniDTO, long idProcedimentoOggetto, Integer idDanno, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
		final String QUERY;
		List<Long> listIdEntitaDanneggiata = new ArrayList<Long>();
		for(DanniDTO danno : listDanniDTO)
		{
			listIdEntitaDanneggiata.add(danno.getExtIdEntitaDanneggiata());
		}
		switch(idDanno)
		{

		case NemboConstants.DANNI.SCORTA:
		case NemboConstants.DANNI.SCORTE_MORTE:

			QUERY =
			"	WITH TMP_SCORTE_MAGAZZINO_PO AS (\r\n" + //conto le scorte magazzino dell'utente e del rispettivo po
			"		   SELECT 	SM.ID_SCORTA_MAGAZZINO														\r\n" + 
			"		   FROM 	NEMBO_T_SCORTA_MAGAZZINO SM														\r\n" + 
			"		   WHERE	SM.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO\r\n" + 
			"	)\r\n" + 
			"	SELECT COUNT(*)\r\n" + //conto quante delle scorte che voglio insierire sono di proprietà del PO
			"	FROM TMP_SCORTE_MAGAZZINO_PO\r\n" + 
				"WHERE 1=1 \r\n"
			+ getInCondition("ID_SCORTA_MAGAZZINO", listIdEntitaDanneggiata)
			;
			break;
		case NemboConstants.DANNI.MACCHINA_AGRICOLA:
		case NemboConstants.DANNI.ATTREZZATURA:
			QUERY =
			"	WITH TMP_MACCHINE_UTENTE AS (\r\n" + 
			"        SELECT M2.ID_MACCHINA																								\r\n" + 
			"        FROM 																												\r\n" + 
			"            SMRGAA_V_MACCHINE M2,																							\r\n" + 
			"            NEMBO_T_PROCEDIMENTO_OGGETTO PO2,																				\r\n" + 
			"            SMRGAA_V_DICH_CONSISTENZA DC2																					\r\n" + 
			"        WHERE																												\r\n" + 
			"            PO2.EXT_ID_DICHIARAZIONE_CONSISTEN = DC2.ID_DICHIARAZIONE_CONSISTENZA											\r\n" + 
			"            AND DC2.ID_AZIENDA = M2.ID_AZIENDA																				\r\n" + 
			"            AND DC2.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN M2.DATA_INIZIO_VALIDITA AND NVL(M2.DATA_FINE_VALIDITA, SYSDATE)	\r\n" + 
			"            AND DC2.ID_PROCEDIMENTO = :DC_ID_PROCEDIMENTO																	\r\n" + 
			"            AND PO2.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" + 
			"	)																														\r\n" + 
			"	SELECT COUNT(*)																											\r\n" + 
			"	FROM TMP_MACCHINE_UTENTE																								\r\n" +
			"	WHERE 	1=1																												\r\n" +
						getInCondition("ID_MACCHINA", listIdEntitaDanneggiata)
			;
			
			break;
		case NemboConstants.DANNI.FABBRICATO:
			QUERY =
			" WITH TMP_FABBRICATI_UTENTE AS (\r\n" +  //elenco dei fabbricati dell'utente
			"				SELECT  																											\r\n" + 
			"						F.ID_FABBRICATO																						       																		\r\n" + 
			"				FROM    SMRGAA_V_FABBRICATI F,																						\r\n" + 
			"						SMRGAA_V_UTE U,																								\r\n" + 
			"						SMRGAA_V_DICH_CONSISTENZA DC,																				\r\n" + 
			"						NEMBO_T_PROCEDIMENTO_OGGETTO PO																				\r\n" + 
			"				WHERE   F.ID_UTE = U.ID_UTE																							\r\n" + 
			"						AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
			"						AND DC.ID_AZIENDA = F.ID_AZIENDA																			\r\n" + 
			"						AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F.DATA_INIZIO_VAL_FABBR AND NVL(DATA_FINE_VAL_FABBR,SYSDATE)	\r\n" + 
			"						AND DC.ID_PROCEDIMENTO = :DC_ID_PROCEDIMENTO																	\r\n" + 
			"						AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO													\r\n" + 
			")																																	\r\n" + 
			"																																	\r\n" + 
			"	SELECT COUNT(*)																													\r\n" + //conto quanti fabbricati che devo modificare non sono dell'utente 
			"	FROM TMP_FABBRICATI_UTENTE																										\r\n" + 
			"	WHERE 1=1																														\r\n" + 
				getInCondition("ID_FABBRICATO", listIdEntitaDanneggiata)
			;

			break;
			
		case NemboConstants.DANNI.ALLEVAMENTO:
			QUERY =
			  "WITH TMP_ALLEVAMENTI AS (																										\r\n" + 
			  "    SELECT 																														\r\n" + 
			  "        A.ID_ALLEVAMENTO																											\r\n" + 
			  "    FROM 																														\r\n" + 
			  "        SMRGAA_V_ALLEVAMENTI A,																									\r\n" + 
			  "        NEMBO_T_PROCEDIMENTO_OGGETTO PO,																							\r\n" + 
			  "        SMRGAA_V_DICH_CONSISTENZA DC																								\r\n" + 
			  "    WHERE 																														\r\n" + 
			  "            PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																\r\n" + 
			  "        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA													\r\n" + 
			  "        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA													\r\n" + 
			  "        AND DC.ID_PROCEDIMENTO = :DC_ID_PROCEDIMENTO																				\r\n" + 
			  "        AND A.ID_ALLEVAMENTO NOT IN 																								\r\n" + 
			  "            (																													\r\n" + 
			  "                SELECT																											\r\n" + 
			  "                    DA.EXT_ID_ENTITA_DANNEGGIATA																					\r\n" + 
			  "                FROM 																											\r\n" + 
			  "                    NEMBO_T_DANNO_ATM DA																							\r\n" + 
			  "                WHERE 																											\r\n" + 
			  "                        ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" + 
			  					   getInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALLEVAMENTO)) +
			  "            )																													\r\n" + 
			  ")																																\r\n" + 
			  "SELECT 																															\r\n" + 
			  "    COUNT(*)																														\r\n" + 
			  "FROM																																\r\n" + 
			  "    TMP_ALLEVAMENTI																												\r\n" + 
			  "WHERE 1=1 " +
			  getInCondition("ID_ALLEVAMENTO", listIdEntitaDanneggiata);
			break;
			case NemboConstants.DANNI.ALTRO:
			default:
				return true;
		}
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      
	      mapParameterSource.addValue("DC_ID_PROCEDIMENTO", idProcedimentoAgricoltura, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      Long nValide = queryForLong(QUERY, mapParameterSource);
	      
	      if(nValide != listIdEntitaDanneggiata.size())
	      {
	    	  return false;
	      }
	      else
	      {
	    	  return true;
	      }
	      
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO", idProcedimentoAgricoltura),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("EXT_ID_ENTITA_DANNEGGIATA", listIdEntitaDanneggiata)
	          },
	          new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public static List<Integer> getListDanniEquivalenti(int idDanno)
	{
		List<Integer> listDanniEquivalenti = new ArrayList<Integer>();
		switch(idDanno)
		{
		case NemboConstants.DANNI.SCORTA:
		case NemboConstants.DANNI.SCORTE_MORTE:
			listDanniEquivalenti.add(NemboConstants.DANNI.SCORTA);
			listDanniEquivalenti.add(NemboConstants.DANNI.SCORTE_MORTE);
			break;
		case NemboConstants.DANNI.MACCHINA_AGRICOLA:
		case NemboConstants.DANNI.ATTREZZATURA:
			listDanniEquivalenti.add(NemboConstants.DANNI.MACCHINA_AGRICOLA);
			listDanniEquivalenti.add(NemboConstants.DANNI.ATTREZZATURA);
			break;
		case NemboConstants.DANNI.ALLEVAMENTO:
			listDanniEquivalenti.add(NemboConstants.DANNI.ALLEVAMENTO);
			break;
		case NemboConstants.DANNI.FABBRICATO:
			listDanniEquivalenti.add(NemboConstants.DANNI.FABBRICATO);
			break;
		case NemboConstants.DANNI.TERRENI_RIPRISTINABILI:
		case NemboConstants.DANNI.TERRENI_NON_RIPRISTINABILI:
		case NemboConstants.DANNI.PIANTAGIONI_ARBOREE:
		case NemboConstants.DANNI.ALTRE_PIANTAGIONI:
			listDanniEquivalenti.add(NemboConstants.DANNI.TERRENI_RIPRISTINABILI);
			listDanniEquivalenti.add(NemboConstants.DANNI.TERRENI_NON_RIPRISTINABILI);
			listDanniEquivalenti.add(NemboConstants.DANNI.PIANTAGIONI_ARBOREE);
			listDanniEquivalenti.add(NemboConstants.DANNI.ALTRE_PIANTAGIONI);
			break;
			
		default:
			listDanniEquivalenti.add(idDanno);
			break;
		}
		return listDanniEquivalenti;
	}
	
	public static List<Integer> getListDanniRiconosciuti()
	{
		List<Integer> listDanniEquivalenti = new ArrayList<Integer>();
		listDanniEquivalenti.add(NemboConstants.DANNI.SCORTA);
		listDanniEquivalenti.add(NemboConstants.DANNI.SCORTE_MORTE);
		listDanniEquivalenti.add(NemboConstants.DANNI.MACCHINA_AGRICOLA);
		listDanniEquivalenti.add(NemboConstants.DANNI.ATTREZZATURA);
		listDanniEquivalenti.add(NemboConstants.DANNI.ALLEVAMENTO);
		listDanniEquivalenti.add(NemboConstants.DANNI.FABBRICATO);
		listDanniEquivalenti.add(NemboConstants.DANNI.TERRENI_RIPRISTINABILI);
		listDanniEquivalenti.add(NemboConstants.DANNI.TERRENI_NON_RIPRISTINABILI);
		listDanniEquivalenti.add(NemboConstants.DANNI.PIANTAGIONI_ARBOREE);
		listDanniEquivalenti.add(NemboConstants.DANNI.ALTRE_PIANTAGIONI);
		listDanniEquivalenti.add(NemboConstants.DANNI.ALTRO);
		return listDanniEquivalenti;
	}

	public long inserisciDanno(DanniDTO danno, Integer idDanno) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = "INSERT INTO NEMBO_T_DANNO_ATM (												\r\n" + 
				"    ID_DANNO_ATM,																			\r\n" + 
				"    ID_DANNO,																				\r\n" + 
				"    EXT_ID_ENTITA_DANNEGGIATA,																\r\n" + 
				"    PROGRESSIVO,																			\r\n" + 
				"    ID_PROCEDIMENTO_OGGETTO,																\r\n" + 
				"    DESCRIZIONE,																			\r\n" + 
				"    QUANTITA,																				\r\n" + 
				"    IMPORTO,																				\r\n" + 
				"    ID_UNITA_MISURA)																		\r\n" + 
				"VALUES (																					\r\n" + 
				"    :ID_DANNO_ATM,																			\r\n" + 
				"    :ID_DANNO,																				\r\n" + 
				"    :EXT_ID_ENTITA_DANNEGGIATA,															\r\n" + 
				"    (SELECT NVL(MAX(PROGRESSIVO),0)+1														\r\n" +
				"		FROM NEMBO_T_DANNO_ATM WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO),	\r\n" + 
				"    :ID_PROCEDIMENTO_OGGETTO,																\r\n" + 
				"    :DESCRIZIONE,																			\r\n" + 
				"    :QUANTITA,																				\r\n" + 
				"    :IMPORTO,																				\r\n" + 
				"    :ID_UNITA_MISURA																		\r\n" + 
				")"  
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTDannoATM = 0;
	    try
	    {
	      seqNemboTDannoATM = getNextSequenceValue("SEQ_NEMBO_T_DANNO_ATM");
	      
	      mapParameterSource.addValue("ID_DANNO_ATM", seqNemboTDannoATM, Types.NUMERIC);
	      mapParameterSource.addValue("ID_DANNO", danno.getIdDanno(), Types.NUMERIC);
	      mapParameterSource.addValue("EXT_ID_ENTITA_DANNEGGIATA", danno.getExtIdEntitaDanneggiata(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", danno.getIdProcedimentoOggetto(), Types.NUMERIC);
	      mapParameterSource.addValue("DESCRIZIONE", danno.getDescrizione(), Types.VARCHAR);
	      mapParameterSource.addValue("QUANTITA", danno.getQuantita(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO", danno.getImporto(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_UNITA_MISURA", danno.getIdUnitaMisura(), Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_DANNO_ATM", seqNemboTDannoATM),
	              new LogParameter("ID_DANNO", danno.getIdDanno()),
	              new LogParameter("EXT_ID_ENTITA_DANNEGGIATA", danno.getExtIdEntitaDanneggiata()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", danno.getIdProcedimentoOggetto()),
	              new LogParameter("DESCRIZIONE", danno.getDescrizione()),
	              new LogParameter("QUANTITA", danno.getQuantita()),
	              new LogParameter("IMPORTO", danno.getImporto()),
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public int inserisciConduzioneDanneggiata(long idProcedimentoOggetto, DanniDTO danno,
			long idUtilizzoDichiarato) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT =  
	    		"INSERT INTO NEMBO_R_PARTICELLA_DANNEGGIATA 				\r\n" + 
	    		"    (ID_DANNO_ATM, EXT_ID_UTILIZZO_DICHIARATO)			\r\n" + 
	    		"VALUES (:ID_DANNO_ATM, :EXT_ID_UTILIZZO_DICHIARATO)			\r\n"  
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_DANNO_ATM", danno.getIdDannoAtm(), Types.NUMERIC);
	      mapParameterSource.addValue("EXT_ID_UTILIZZO_DICHIARATO", idUtilizzoDichiarato, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_DANNO_ATM", danno.getIdDannoAtm()),
	              new LogParameter("EXT_ID_ID_UTILIZZO_DICHIARATO", idUtilizzoDichiarato)
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}


	public UnitaMisuraDTO getUnitaMisuraByIdDanno(Integer idDanno) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String  QUERY = 
				  "SELECT 											"
				  + "UM.ID_UNITA_MISURA, 							"
				  + "UM.CODICE, 									"
				  + "UM.DESCRIZIONE,								"
				  + "UM.DATA_FINE, 									"
				  + "D.ID_DANNO, 									"
				  + "D.DESCRIZIONE AS DESC_DANNO 					"
				+ "FROM NEMBO_D_UNITA_MISURA UM, NEMBO_D_DANNO D 	"
				+ "WHERE UM.ID_UNITA_MISURA = D.ID_UNITA_MISURA 	"
				+ "AND D.ID_DANNO = :ID_DANNO 						"
				+ "AND DATA_FINE IS NULL 							"
				; 	
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_DANNO", idDanno);
			return queryForObject(QUERY, mapParameterSource,UnitaMisuraDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
					new LogParameter[] {},
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public DannoDTO getDannoByIdDanno(int idDanno) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String  QUERY = 
				"SELECT											\r\n" + 
				"    ID_DANNO,									\r\n" + 
				"    DESCRIZIONE,								\r\n" + 
				"    ID_UNITA_MISURA,							\r\n" + 
				"    NOME_TABELLA,								\r\n" + 
				"    CODICE										\r\n" + 
				"FROM 											\r\n" + 
				"    NEMBO_D_DANNO								\r\n" + 
				"WHERE 											\r\n" + 
				"    ID_DANNO = :ID_DANNO						\r\n"  
				; 	
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_DANNO", idDanno);
			return queryForObject(QUERY, mapParameterSource,DannoDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
					new LogParameter[] {},
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	
	private String queryDanniConduzioni(String inConditionArrayIdDannoAtm)
	{
		return 
			"SELECT																					  							\r\n" + 
			"     DA.ID_DANNO_ATM,																								\r\n" + 
			"     DA.ID_DANNO,																									\r\n" + 
			"     D.DESCRIZIONE AS TIPO_DANNO,																					\r\n" + 
			"     DA.DESCRIZIONE AS DESCRIZIONE,																				\r\n" + 
			"     NULL AS EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
			"     NULL AS ID_ELEMENTO,																							\r\n" + 
			"     NULL AS DENOMINAZIONE,																						\r\n" + 
			"     NULL AS DESC_ENTITA_DANNEGGIATA,																				\r\n" + 
			"     DA.QUANTITA,																									\r\n" + 
			"     UM.ID_UNITA_MISURA,																							\r\n" + 
			"     UM.DESCRIZIONE AS DESC_UNITA_MISURA,																			\r\n" + 
			"     DA.IMPORTO,																							 		\r\n" + 
			"     DA.PROGRESSIVO,																							 	\r\n" +
			"     D.NOME_TABELLA																								\r\n" + 	
			" FROM																								  				\r\n" + 
			"	  NEMBO_T_PROCEDIMENTO_OGGETTO PO,																  				\r\n" + 
			"     NEMBO_T_DANNO_ATM DA,																							\r\n" + 
			"     NEMBO_D_DANNO D,																								\r\n" + 
			"     NEMBO_D_UNITA_MISURA UM																						\r\n" + 
			" WHERE																								  				\r\n" + 
			"        PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO														\r\n" + 
			"    AND PO.ID_PROCEDIMENTO_OGGETTO = DA.ID_PROCEDIMENTO_OGGETTO													\r\n" + 
			"    AND DA.ID_DANNO = D.ID_DANNO																					\r\n" + 
			"    AND D.ID_UNITA_MISURA = UM.ID_UNITA_MISURA																		\r\n" + 
			getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.TERRENI_RIPRISTINABILI)) + "\r\n" +
			inConditionArrayIdDannoAtm
			;
	}
	
	
	//TODO: FIXME: devon considerare che quando vado in modifica e cancellazione non basta filtrare per EXT_ID_ENTITA_DANNEGGIATA ma anche per tipo danno, altrimenti rischio di cancellare delle cose buone!
	public List<DanniDTO> getListDanniByProcedimentoOggettoAndArrayIdDannoAtm(long[] arrayIdDannoAtm, long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionArrayIdDannoAtm = " ";
		if(arrayIdDannoAtm != null)
		{
			inConditionArrayIdDannoAtm = getInCondition("DA.ID_DANNO_ATM", arrayIdDannoAtm);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String QUERY = 
				//scorte
				"SELECT DA.ID_DANNO_ATM,																					\r\n" + 
				"       DA.ID_DANNO,																							\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"       S.ID_SCORTA AS ID_ELEMENTO,																				\r\n" + 
				"       S.DESCRIZIONE AS DENOMINAZIONE, 																		\r\n" +
				"       NVL(SM.DESCRIZIONE,'')	AS DESC_ENTITA_DANNEGGIATA,														\r\n" + 
				"       DA.QUANTITA,																							\r\n" + 
				"       UM.ID_UNITA_MISURA,																						\r\n" + 
				"       UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	\r\n" + 
				"       DA.IMPORTO,																								\r\n" + 
				"       DA.PROGRESSIVO,																							\r\n" + 
				"       D.NOME_TABELLA																							\r\n" + 
				"FROM 	 NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"        NEMBO_D_DANNO D,																						\r\n" + 
				"        NEMBO_T_SCORTA_MAGAZZINO SM,																			\r\n" + 
				"        NEMBO_D_SCORTA S,																						\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM																				\r\n" + 
				"WHERE   DA.EXT_ID_ENTITA_DANNEGGIATA = SM.ID_SCORTA_MAGAZZINO													\r\n" + 
				"        AND D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"        AND SM.ID_SCORTA = S.ID_SCORTA																			\r\n" + 
				"        AND UM.ID_UNITA_MISURA = NVL(SM.ID_UNITA_MISURA, S.ID_UNITA_MISURA)									\r\n" + 
						 getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)) +
				"        AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       										\r\n" +
				inConditionArrayIdDannoAtm +"																					\r\n" +
				
				//scorte non censite
				"UNION ALL																					\r\n" + 
				"SELECT DA.ID_DANNO_ATM,																					\r\n" + 
				"       DA.ID_DANNO,																							\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"       NULL ID_ELEMENTO,																				\r\n" + 
				"       NULL AS DENOMINAZIONE, 																		\r\n" +
				"       NULL	AS DESC_ENTITA_DANNEGGIATA,																\r\n" + 
				"       DA.QUANTITA,																							\r\n" + 
				"       NULL AS ID_UNITA_MISURA,																						\r\n" + 
				"       NULL AS DESC_UNITA_MISURA,																	\r\n" + 
				"       DA.IMPORTO,																								\r\n" + 
				"       DA.PROGRESSIVO,																								\r\n" +
				"       D.NOME_TABELLA																							\r\n" + 				
				"FROM 	 NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"        NEMBO_D_DANNO D																						\r\n" + 
				"WHERE   																										\r\n" + 
				"        D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"        AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       										\r\n" +
				"        AND DA.EXT_ID_ENTITA_DANNEGGIATA IS NULL       										\r\n" +
				getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)) +
				inConditionArrayIdDannoAtm + 
				
				//macchine agricole
				" UNION ALL \r\n" +
				"SELECT DA.ID_DANNO_ATM,																						\r\n" + 
				"	   DA.ID_DANNO,																								\r\n" + 
				"	   D.DESCRIZIONE AS TIPO_DANNO,																				\r\n" + 
				"	   DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"	   DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"	   M.ID_MACCHINA AS ID_ELEMENTO,																			\r\n" + 
				"	   M.TIPO_MACCHINA || ' ' || M.DESC_TIPO_MARCA || ' ' || M.DESC_TIPO_CATEGORIA AS DENOMINAZIONE,													\r\n" + 
				"       M.DESC_TIPO_GENERE_MACCHINA AS DESC_ENTITA_DANNEGGIATA,													\r\n" + 
				"	   DA.QUANTITA,																								\r\n" + 
				"	   UM.ID_UNITA_MISURA,																						\r\n" + 
				"	   UM.DESCRIZIONE AS DESC_UNITA_MISURA,																		\r\n" + 
				"	   DA.IMPORTO,																								\r\n" + 
				"	   DA.PROGRESSIVO,																								\r\n" +
				"      D.NOME_TABELLA																							\r\n" + 				
				"FROM 	NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"		NEMBO_D_DANNO D,																						\r\n" + 
				"		NEMBO_D_UNITA_MISURA UM,																				\r\n" + 
				"       SMRGAA_V_MACCHINE M,																					\r\n" + 
				"       NEMBO_T_PROCEDIMENTO_OGGETTO PO,																		\r\n" + 
				"       SMRGAA_V_DICH_CONSISTENZA DC																			\r\n" + 
				"WHERE   DA.EXT_ID_ENTITA_DANNEGGIATA =  M.ID_MACCHINA															\r\n" + 
				"		AND D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"		AND UM.ID_UNITA_MISURA = D.ID_UNITA_MISURA																\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       										\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO       										\r\n" + 
				"		AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA									\r\n" + 
				"		AND DC.ID_AZIENDA = M.ID_AZIENDA																		\r\n" + 
				"		AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN M.DATA_INIZIO_VALIDITA AND NVL(M.DATA_FINE_VALIDITA,SYSDATE)	\r\n" + 
				"		AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO															\r\n" + 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO												\r\n" + 
				getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.MACCHINA_AGRICOLA)) + 			   "\r\n" +
				inConditionArrayIdDannoAtm+																					   "\r\n" +
				
				//macchine agricole non censite
				" UNION ALL \r\n" +
				"SELECT DA.ID_DANNO_ATM,																						\r\n" + 
				"	   DA.ID_DANNO,																								\r\n" + 
				"	   D.DESCRIZIONE AS TIPO_DANNO,																				\r\n" + 
				"	   DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"	   DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"	   NULL AS ID_ELEMENTO,																						\r\n" + 
				"	   NULL AS DENOMINAZIONE,																					\r\n" + 
				"      NULL AS DESC_ENTITA_DANNEGGIATA,																			\r\n" + 
				"	   DA.QUANTITA,																								\r\n" + 
				"	   UM.ID_UNITA_MISURA,																						\r\n" + 
				"	   UM.DESCRIZIONE AS DESC_UNITA_MISURA,																		\r\n" + 
				"	   DA.IMPORTO,																								\r\n" + 
				"	   DA.PROGRESSIVO,																							\r\n" +
				"      D.NOME_TABELLA																							\r\n" + 				
				"FROM 	NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"		NEMBO_D_DANNO D,																						\r\n" + 
				"		NEMBO_D_UNITA_MISURA UM,																				\r\n" + 
				"       NEMBO_T_PROCEDIMENTO_OGGETTO PO																			\r\n" + 
				"WHERE  																										\r\n" + 
				"		D.ID_DANNO = DA.ID_DANNO																				\r\n" + 
				"		AND UM.ID_UNITA_MISURA = D.ID_UNITA_MISURA																\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO       										\r\n" + 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO												\r\n" + 
				"		AND DA.EXT_ID_ENTITA_DANNEGGIATA IS NULL																\r\n" + 
				getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.MACCHINA_AGRICOLA)) + 			   "\r\n" +
				inConditionArrayIdDannoAtm+																					   "\r\n" +
				
				//fabbricati censiti
				"UNION ALL \r\n"+
				"SELECT \r\n" + 
				"       DA.ID_DANNO_ATM,																						\r\n" + 
				"       DA.ID_DANNO,																							\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"       F.ID_FABBRICATO AS ID_ELEMENTO,																			\r\n" + 
				"       NVL(F.DENOMINAZIONE,'') AS DENOMINAZIONE, 																\r\n" + 
				"       F.DESC_TIPOLOGIA_FABBR	AS DESC_ENTITA_DANNEGGIATA,														\r\n" + 
				"       DA.QUANTITA,																							\r\n" + 
				"       UM.ID_UNITA_MISURA,																						\r\n" + 
				"       UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	\r\n" + 
				"       DA.IMPORTO,																								\r\n" + 
				"       DA.PROGRESSIVO,																								\r\n" +
				"       D.NOME_TABELLA																							\r\n" + 					
				"FROM 	NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"        NEMBO_D_DANNO D,																						\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,																				\r\n" + 
				"        SMRGAA_V_FABBRICATI F,																					\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,																		\r\n" + 
				"        SMRGAA_V_DICH_CONSISTENZA DC																			\r\n" + 
				"WHERE           																								\r\n" + 
				"        DA.EXT_ID_ENTITA_DANNEGGIATA = F.ID_FABBRICATO															\r\n" + 
				"        AND D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"        AND UM.ID_UNITA_MISURA = D.ID_UNITA_MISURA																\r\n" + 
				"        AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       										\r\n" + 
				"		 AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO											\r\n" +		
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA								\r\n" + 
				"        AND DC.ID_AZIENDA = F.ID_AZIENDA																		\r\n" + 
				"        AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F.DATA_INIZIO_VAL_FABBR AND NVL(DATA_FINE_VAL_FABBR,SYSDATE)	\r\n" + 
				"        AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																	\r\n" + 
				"        AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 													\r\n" + 
				getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.FABBRICATO)) + "\r\n" +
				inConditionArrayIdDannoAtm + "\r\n" +
				
				//fabbricati non censiti			
				"UNION ALL 																										\r\n"+
				"SELECT 																										\r\n" + 
				"       DA.ID_DANNO_ATM,																						\r\n" + 
				"       DA.ID_DANNO,																							\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			\r\n" + 
				"       NULL AS ID_ELEMENTO,																					\r\n" + 
				"       NULL AS DENOMINAZIONE, 																					\r\n" + 
				"       NULL AS DESC_ENTITA_DANNEGGIATA,																		\r\n" + 
				"       DA.QUANTITA,																							\r\n" + 
				"       UM.ID_UNITA_MISURA,																						\r\n" + 
				"       UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	\r\n" + 
				"       DA.IMPORTO,																								\r\n" + 
				"       DA.PROGRESSIVO,																							\r\n" +
				"       D.NOME_TABELLA																							\r\n" + 					
				"FROM 	NEMBO_T_DANNO_ATM DA,																					\r\n" + 
				"        NEMBO_D_DANNO D,																						\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,																				\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO																		\r\n" + 
				"WHERE           																								\r\n" + 
				"        D.ID_DANNO = DA.ID_DANNO																				\r\n" + 
				"        AND UM.ID_UNITA_MISURA = D.ID_UNITA_MISURA																\r\n" + 
				"		 AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO											\r\n" +		
				"        AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 												\r\n" + 
				"        AND DA.EXT_ID_ENTITA_DANNEGGIATA IS NULL 																\r\n" + 
				getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.FABBRICATO)) + "\r\n" +
				inConditionArrayIdDannoAtm + "\r\n" +

				"UNION ALL \r\n" +
				
				//conduzioni
				queryDanniConduzioni(inConditionArrayIdDannoAtm) + "\r\n" + 
				
				//allevamenti
				"UNION ALL 																											\r\n" +
				"SELECT  																											\r\n" + 
				"       DA.ID_DANNO_ATM,																						 	\r\n" + 
				"       DA.ID_DANNO,																							 	\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			 	\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			 	\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			 	\r\n" + 
				"       A.ID_ALLEVAMENTO AS ID_ELEMENTO,																			\r\n" + 
				"       DECODE(A.DENOMINAZIONE_ALLEVAMENTO, NULL, '', A.DENOMINAZIONE_ALLEVAMENTO || '<br/>' ) || DAM.DESCRIZIONE_COMUNE || ' (' || DAM.SIGLA_PROVINCIA || ')' || '<br/>' || A.INDIRIZZO  AS DENOMINAZIONE, 													 	\r\n" + 
				"       A.DESCRIZIONE_CATEGORIA_ANIMALE  	AS DESC_ENTITA_DANNEGGIATA,														 \r\n" + 
				"       DA.QUANTITA,																							 	\r\n" + 
				"       UM.ID_UNITA_MISURA,																						 	\r\n" + 
				"       UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	 	\r\n" + 
				"       DA.IMPORTO,																								 	\r\n" + 
				"       DA.PROGRESSIVO,																								\r\n" +
				"       D.NOME_TABELLA																								\r\n" + 					
				"FROM 																												\r\n" + 
				"        NEMBO_T_DANNO_ATM DA,																					 	\r\n" + 
				"        NEMBO_D_DANNO D,																						 	\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,																				 	\r\n" + 
				"        SMRGAA_V_ALLEVAMENTI A,																					\r\n" + 
				"        SMRGAA_V_DATI_AMMINISTRATIVI DAM,																			\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,																		 	\r\n" + 
				"        SMRGAA_V_DICH_CONSISTENZA DC																			 	\r\n" + 
				"WHERE  																											\r\n" + 
				"            PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO													\r\n" +
				"		 AND PO.ID_PROCEDIMENTO_OGGETTO = DA.ID_PROCEDIMENTO_OGGETTO												\r\n" +	
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA									\r\n" + 
				"        AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																\r\n" + 
				"        AND A.ISTAT_COMUNE = DAM.ISTAT_COMUNE																		\r\n" + 
				"        AND A.ID_ALLEVAMENTO = DA.EXT_ID_ENTITA_DANNEGGIATA														\r\n" + 
				"        AND DA.ID_DANNO = D.ID_DANNO																				\r\n" + 
				"        AND D.ID_UNITA_MISURA = UM.ID_UNITA_MISURA																	\r\n" +
				"		 AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 													\r\n" +
						 getInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALLEVAMENTO)) + "\r\n" +
						 inConditionArrayIdDannoAtm + "																				\r\n" +
				
				//allevamenti non censiti
				"UNION ALL 																											\r\n" +
				"SELECT  																											\r\n" + 
				"       DA.ID_DANNO_ATM,																						 	\r\n" + 
				"       DA.ID_DANNO,																							 	\r\n" + 
				"       D.DESCRIZIONE AS TIPO_DANNO,																			 	\r\n" + 
				"       DA.DESCRIZIONE AS DESCRIZIONE, 																			 	\r\n" + 
				"       DA.EXT_ID_ENTITA_DANNEGGIATA,																			 	\r\n" + 
				"       NULL AS ID_ELEMENTO,																						\r\n" + 
				"       NULL  AS DENOMINAZIONE, 													 								\r\n" + 
				"       NULL  	AS DESC_ENTITA_DANNEGGIATA,														 					\r\n" + 
				"       DA.QUANTITA,																							 	\r\n" + 
				"       UM.ID_UNITA_MISURA,																						 	\r\n" + 
				"       UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	 	\r\n" + 
				"       DA.IMPORTO,																								 	\r\n" + 
				"       DA.PROGRESSIVO,																								\r\n" +
				"       D.NOME_TABELLA																								\r\n" + 					
				"FROM 																												\r\n" + 
				"        NEMBO_T_DANNO_ATM DA,																					 	\r\n" + 
				"        NEMBO_D_DANNO D,																						 	\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,																				 	\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO																		 	\r\n" + 
				"WHERE  																											\r\n" + 
				"            PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO													\r\n" +
				"		 AND PO.ID_PROCEDIMENTO_OGGETTO = DA.ID_PROCEDIMENTO_OGGETTO												\r\n" +	  
				"        AND DA.ID_DANNO = D.ID_DANNO																				\r\n" + 
				"        AND D.ID_UNITA_MISURA = UM.ID_UNITA_MISURA																	\r\n" +
				"		 AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 													\r\n" +
				"		 AND DA.EXT_ID_ENTITA_DANNEGGIATA IS NULL				 													\r\n" +
						 getInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALLEVAMENTO)) + "\r\n" +
						 inConditionArrayIdDannoAtm + "																				\r\n" +

				//altro
				"UNION ALL																									\r\n" + 
				"SELECT DA.ID_DANNO_ATM,																					\r\n" + 
				"	   DA.ID_DANNO,																							\r\n" + 
				"	   D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"	   DA.DESCRIZIONE AS DESCRIZIONE, 																		\r\n" + 
				"	   DA.EXT_ID_ENTITA_DANNEGGIATA,																		\r\n" + 
				"      NULL AS ID_ELEMENTO,																					\r\n" + 
				"      NULL AS DENOMINAZIONE,																				\r\n" + 
				"      NULL AS DESC_ENTITA_DANNEGGIATA,																		\r\n" + 
				"	   DA.QUANTITA,																							\r\n" + 
				"	   DA.ID_UNITA_MISURA,																					\r\n" + 
				"	   UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	\r\n" + 
				"	   DA.IMPORTO,																							\r\n" + 
				"	   DA.PROGRESSIVO,																						\r\n" +
				"      D.NOME_TABELLA																						\r\n" + 					
				"FROM 																										\r\n" + 
				"       NEMBO_T_DANNO_ATM DA,																				\r\n" + 
				"       NEMBO_D_DANNO D,																					\r\n" + 
				"       NEMBO_D_UNITA_MISURA UM,																			\r\n" + 
				"       NEMBO_T_PROCEDIMENTO_OGGETTO PO																		\r\n" + 
				"WHERE   																									\r\n" + 
				"		D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"		AND UM.ID_UNITA_MISURA = DA.ID_UNITA_MISURA															\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       									\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO       									\r\n" + 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO											\r\n" +
				getInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALTRO)) + "		\r\n" +
				inConditionArrayIdDannoAtm + "\r\n" +

				//tutti i danni non contemplati precedentemente
				"UNION ALL																									\r\n" + 
				"SELECT DA.ID_DANNO_ATM,																					\r\n" + 
				"	   DA.ID_DANNO,																							\r\n" + 
				"	   D.DESCRIZIONE AS TIPO_DANNO,																			\r\n" + 
				"	   DA.DESCRIZIONE AS DESCRIZIONE, 																		\r\n" + 
				"	   DA.EXT_ID_ENTITA_DANNEGGIATA,																		\r\n" + 
				"      NULL AS ID_ELEMENTO,																					\r\n" + 
				"      NULL AS DENOMINAZIONE,																				\r\n" + 
				"      NULL AS DESC_ENTITA_DANNEGGIATA,																		\r\n" + 
				"	   DA.QUANTITA,																							\r\n" + 
				"	   NVL(DA.ID_UNITA_MISURA, D.ID_UNITA_MISURA) AS ID_UNITA_MISURA,																					\r\n" + 
				"	   UM.DESCRIZIONE AS DESC_UNITA_MISURA,																	\r\n" + 
				"	   DA.IMPORTO,																							\r\n" + 
				"	   DA.PROGRESSIVO,																						\r\n" +
				"      D.NOME_TABELLA																						\r\n" + 					
				"FROM 																										\r\n" + 
				"       NEMBO_T_DANNO_ATM DA,																				\r\n" + 
				"       NEMBO_D_DANNO D,																					\r\n" + 
				"       NEMBO_D_UNITA_MISURA UM,																			\r\n" + 
				"       NEMBO_T_PROCEDIMENTO_OGGETTO PO																		\r\n" + 
				"WHERE   																									\r\n" + 
				"		D.ID_DANNO = DA.ID_DANNO																			\r\n" + 
				"		AND UM.ID_UNITA_MISURA = NVL(DA.ID_UNITA_MISURA,D.ID_UNITA_MISURA)															\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       									\r\n" + 
				"		AND DA.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO       									\r\n" + 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO											\r\n" +
				getNotInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniRiconosciuti()) + "		\r\n" +
				inConditionArrayIdDannoAtm + "\r\n" +
				
				"ORDER BY PROGRESSIVO																								\r\n"
						 

        ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO", idProcedimentoAgricoltura, Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,DanniDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_PROCEDIMENTO", idProcedimentoAgricoltura),
					new LogParameter("ID_DANNO_ATM", arrayIdDannoAtm)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<AllevamentiDTO> getListAllevamentiByIdDannoAtm(long idProcedimentoOggetto, long[] arrayIdDannoAtm)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionArrayIdDannoAtm = " ";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		if(arrayIdDannoAtm != null)
		{
			inConditionArrayIdDannoAtm = getInCondition("DAT.ID_DANNO_ATM", arrayIdDannoAtm);
		}
		final String QUERY = 
				"SELECT																																	\r\n" + 
				"    A.ID_ALLEVAMENTO,																													\r\n" + 
				"    A.DESCRIZIONE_CATEGORIA_ANIMALE,																									\r\n" + 
				"    A.DESCRIZIONE_SPECIE_ANIMALE,																										\r\n" + 
				"    A.INDIRIZZO,																														\r\n" + 
				"    A.DENOMINAZIONE_ALLEVAMENTO,																										\r\n" + 
				"    A.QUANTITA,																														\r\n" + 
				"    DA.DESCRIZIONE_COMUNE,																												\r\n" + 
				"    DA.SIGLA_PROVINCIA																													\r\n" + 
				"FROM 																																	\r\n" + 
				"    SMRGAA_V_ALLEVAMENTI A,																											\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,																									\r\n" + 
				"    SMRGAA_V_DATI_AMMINISTRATIVI DA,\r\n" + 
				"    NEMBO_T_DANNO_ATM DAT\r\n" + 
				"WHERE 																																	\r\n" + 
				"        PO.ID_PROCEDIMENTO_OGGETTO  =:ID_PROCEDIMENTO_OGGETTO																			\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN =  A.ID_DICHIARAZIONE_CONSISTENZA\r\n" + 
				"    AND A.ID_ALLEVAMENTO = DAT.EXT_ID_ENTITA_DANNEGGIATA\r\n" + 
				"    AND A.ISTAT_COMUNE = DA.ISTAT_COMUNE																\r\n" + 
					 getInCondition("DAT.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALLEVAMENTO)) + "\r\n" +
					 inConditionArrayIdDannoAtm
        ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,AllevamentiDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_DANNO_ATM", arrayIdDannoAtm)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<DanniDTO> getListDanniConduzioni (long idProcedimentoOggetto, long[] arrayIdDannoAtm)
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionArrayIdDannoAtm = " ";
		if(arrayIdDannoAtm != null)
		{
			inConditionArrayIdDannoAtm = getInCondition("DA.ID_DANNO_ATM", arrayIdDannoAtm);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String QUERY = queryDanniConduzioni(inConditionArrayIdDannoAtm);
		
        ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,DanniDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_DANNO_ATM", arrayIdDannoAtm)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public int modificaDanno(DanniDTO danno, long idProcedimentoOggetto) throws InternalUnexpectedException
	
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"UPDATE NEMBO_T_DANNO_ATM	SET											\r\n" + 
	    		"    DESCRIZIONE = 	:DESCRIZIONE,										\r\n" + 
	    		"    QUANTITA = 	:QUANTITA,											\r\n" + 
	    		"    IMPORTO = 		:IMPORTO											\r\n" + 
	    		"WHERE 																	\r\n" + 
	    		"    ID_PROCEDIMENTO_OGGETTO = 	:ID_PROCEDIMENTO_OGGETTO				\r\n" + 
	    		"    AND ID_DANNO_ATM = 		:ID_DANNO_ATM							\r\n" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("DESCRIZIONE", danno.getDescrizione(), Types.VARCHAR);
	      mapParameterSource.addValue("QUANTITA", danno.getQuantita(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO", danno.getImporto(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_DANNO_ATM", danno.getIdDannoAtm(), Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("DESCRIZIONE", danno.getDescrizione()),
	              new LogParameter("QUANTITA", danno.getQuantita()),
	              new LogParameter("IMPORTO", danno.getImporto()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", danno.getIdProcedimentoOggetto()),
	              new LogParameter("ID_DANNO_ATM", danno.getIdDannoAtm()),
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}


	public List<ScorteDecodificaDTO> getListDecodicaScorta() throws InternalUnexpectedException
	{
		  String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		    if (logger.isDebugEnabled())
		    {
		      logger.debug(THIS_METHOD + " BEGIN.");
		    }
			final String  QUERY = 
					"SELECT 													\r\n" + 
					"    ID_SCORTA,												\r\n" + 
					"    DESCRIZIONE,											\r\n" + 
					"    ID_UNITA_MISURA										\r\n" + 
					"FROM NEMBO_D_SCORTA" 
					;
		    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
		       return queryForList(QUERY, mapParameterSource,ScorteDecodificaDTO.class);
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {}, new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
	}


	public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		return getListMotoriAgricoli(idProcedimentoOggetto, null, idProcedimentoAgricoltura);
	}

	public List<MotoriAgricoliDTO> getListMotoriAgricoli(long idProcedimentoOggetto, long[] arrayIdMacchina, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionIdMacchina = " ";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		if(arrayIdMacchina != null)
		{
			inConditionIdMacchina = getInCondition("ID_MACCHINA", arrayIdMacchina);
		}

		final String QUERY = "SELECT   																									\r\n" + 
				"    M.ID_MACCHINA,  																										\r\n" + 
				"    M.DESC_TIPO_GENERE_MACCHINA,  																							\r\n" + 
				"    M.DESC_TIPO_CATEGORIA,  																								\r\n" + 
				"    M.DESC_TIPO_MARCA,  																									\r\n" + 
				"    M.TIPO_MACCHINA,   																									\r\n" + 
				"    M.POTENZA_KW,  																										\r\n" + 
				"    M.DATA_CARICO  																										\r\n" + 
				"FROM 	 																													\r\n" + 
				"	SMRGAA_V_MACCHINE M,							  																		\r\n" + 
				" 	NEMBO_T_PROCEDIMENTO_OGGETTO PO,																						\r\n" + 
				"    SMRGAA_V_DICH_CONSISTENZA DC																							\r\n" + 
				"WHERE 																														\r\n" + 
				"    PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA													\r\n" + 
				"    AND DC.ID_AZIENDA = M.ID_AZIENDA																						\r\n" + 
				"    AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN M.DATA_INIZIO_VALIDITA AND NVL(M.DATA_FINE_VALIDITA, SYSDATE)			\r\n" + 
				"    AND DC.ID_PROCEDIMENTO = :DC_ID_PROCEDIMENTO																			\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																\r\n" +
				inConditionIdMacchina + " \r\n" +
				"ORDER BY   																												\r\n" + 
				"    M.DESC_TIPO_GENERE_MACCHINA,  																							\r\n" + 
				"    M.DESC_TIPO_CATEGORIA, 																								\r\n" + 
				"    M.DESC_TIPO_MARCA,  																									\r\n" + 
				"    M.TIPO_MACCHINA "
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("DC_ID_PROCEDIMENTO", idProcedimentoAgricoltura,Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,MotoriAgricoliDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("DC_ID_PROCEDIMENTO", idProcedimentoAgricoltura),
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		return getListMotoriAgricoliNonDanneggiati(null, idProcedimentoOggetto, idProcedimentoAgricoltura);
	}
	
	public List<MotoriAgricoliDTO> getListMotoriAgricoliNonDanneggiati(long[] arrayIdMacchina, long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		String condizioneIdMacchina=" ";
		if(arrayIdMacchina != null)
		{
			condizioneIdMacchina = getInCondition("M.ID_MACCHINA", arrayIdMacchina);
		}
		
		final String QUERY = "SELECT   																							\r\n" + 
				"    M.ID_MACCHINA,  																								\r\n" + 
				"    M.DESC_TIPO_GENERE_MACCHINA,  																					\r\n" + 
				"    M.DESC_TIPO_CATEGORIA,  																						\r\n" + 
				"    M.DESC_TIPO_MARCA,  																							\r\n" + 
				"    M.TIPO_MACCHINA,  																								\r\n" + 
				"    M.POTENZA_KW,  																								\r\n" + 
				"    M.DATA_CARICO  																								\r\n" + 
				"FROM   																											\r\n" + 
				"    SMRGAA_V_MACCHINE M,																							\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,																				\r\n" + 
				"    SMRGAA_V_DICH_CONSISTENZA DC																					\r\n" + 
				"WHERE   																											\r\n" + 
				"     M.ID_MACCHINA NOT IN 	 																						\r\n" + 
				"    (  																											\r\n" + 
				"        SELECT  EXT_ID_ENTITA_DANNEGGIATA									  										\r\n" + 
				"        FROM    NEMBO_T_DANNO_ATM DA										  										\r\n" + 
				"        WHERE										  										\r\n" + 
				"        		 DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO	  											\r\n" +
								 getInCondition("DA.ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.MACCHINA_AGRICOLA)) +  "\r\n" +
				"    )   																											\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"    AND DC.ID_AZIENDA = M.ID_AZIENDA																				\r\n" + 
				"    AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN M.DATA_INIZIO_VALIDITA AND NVL(M.DATA_FINE_VALIDITA, SYSDATE)	\r\n" + 
				"    AND DC.ID_PROCEDIMENTO = :DC_ID_PROCEDIMENTO																	\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" +
				condizioneIdMacchina +"																								\r\n "+
				" ORDER BY   																										\r\n" + 
				"    M.DESC_TIPO_GENERE_MACCHINA,  																					\r\n" + 
				"    M.DESC_TIPO_CATEGORIA,  																						\r\n" + 
				"    M.DESC_TIPO_MARCA,  																							\r\n" + 
				"    M.TIPO_MACCHINA "
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("DC_ID_PROCEDIMENTO", idProcedimentoAgricoltura,Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto,Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,MotoriAgricoliDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("DC_ID_PROCEDIMENTO", idProcedimentoAgricoltura),
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_MACCHINA", condizioneIdMacchina)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<PrestitiAgrariDTO> getListPrestitiAgrari(long idProcedimentoOggetto, long[] arrayIdPrestitiAgrari) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionPrestitiAgrari = " ";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		if(arrayIdPrestitiAgrari != null)
		{
			inConditionPrestitiAgrari=getInCondition("PA.ID_PRESTITI_AGRARI", arrayIdPrestitiAgrari);
		}
		final String QUERY = 
				  "SELECT \r\n" + 
				  "    PA.ID_PRESTITI_AGRARI,\r\n" + 
				  "    PA.DATA_SCADENZA,\r\n" + 
				  "    PA.FINALITA_PRESTITO,\r\n" + 
				  "    PA.IMPORTO,\r\n" + 
				  "    PA.ISTITUTO_EROGANTE,\r\n" + 
				  "    PA.ID_PROCEDIMENTO_OGGETTO\r\n" + 
				  "FROM \r\n" + 
				  "    NEMBO_T_PRESTITI_AGRARI PA\r\n" + 
				  "WHERE \r\n" + 
				  "    PA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO " +
				  	   inConditionPrestitiAgrari
				  
				  ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource, PrestitiAgrariDTO.class);
		} catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t,
					new LogParameter[] { 
							new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto), 
							new LogParameter("ID_PRESTITI_AGRARI", arrayIdPrestitiAgrari) 
							},
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public long inserisciPrestitoAgrario(PrestitiAgrariDTO prestito) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = 
	    		"	 INSERT INTO NEMBO_T_PRESTITI_AGRARI (\r\n" + 
	    		"    ID_PRESTITI_AGRARI,     \r\n" + 
	    		"    DATA_SCADENZA,          \r\n" + 
	    		"    FINALITA_PRESTITO,      \r\n" + 
	    		"    IMPORTO,                \r\n" + 
	    		"    ISTITUTO_EROGANTE,      \r\n" + 
	    		"    ID_PROCEDIMENTO_OGGETTO)\r\n" + 
	    		"VALUES (\r\n" + 
	    		"    :SEQ_NEMBO_T_PRESTITI_AGRARI,     \r\n" + 
	    		"    :DATA_SCADENZA,          \r\n" + 
	    		"    :FINALITA_PRESTITO,      \r\n" + 
	    		"    :IMPORTO,               \r\n" + 
	    		"    :ISTITUTO_EROGANTE,      \r\n" + 
	    		"    :ID_PROCEDIMENTO_OGGETTO)" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTPrestitiAgrari = 0;
	    try
	    {
	      seqNemboTPrestitiAgrari = getNextSequenceValue("SEQ_NEMBO_T_PRESTITI_AGRARI");
	      mapParameterSource.addValue("SEQ_NEMBO_T_PRESTITI_AGRARI", seqNemboTPrestitiAgrari, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", prestito.getIdProcedimentoOggetto(), Types.NUMERIC);
	      mapParameterSource.addValue("DATA_SCADENZA", prestito.getDataScadenza(), Types.DATE);
	      mapParameterSource.addValue("FINALITA_PRESTITO", prestito.getFinalitaPrestito(), Types.VARCHAR);
	      mapParameterSource.addValue("IMPORTO", prestito.getImporto(), Types.NUMERIC);
	      mapParameterSource.addValue("ISTITUTO_EROGANTE", prestito.getIstitutoErogante(), Types.VARCHAR);
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("SEQ_NEMBO_T_PRESTITI_AGRARI", seqNemboTPrestitiAgrari),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", prestito.getIdProcedimentoOggetto()),
	              new LogParameter("DATA_SCADENZA", prestito.getDataScadenza()),
	              new LogParameter("FINALITA_PRESTITO", prestito.getFinalitaPrestito()),
	              new LogParameter("IMPORTO", prestito.getImporto()),
	              new LogParameter("ISTITUTO_EROGANTE", prestito.getIstitutoErogante())
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return seqNemboTPrestitiAgrari;
	}

	public int eliminaPrestitiAgrari(List<Long> listIdPrestitiAgrari, long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE =
				"DELETE FROM NEMBO_T_PRESTITI_AGRARI										\r\n" + 
				"WHERE 																		\r\n" + 
				"    ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO						\r\n" + 
					 getInCondition("ID_PRESTITI_AGRARI", listIdPrestitiAgrari)
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PRESTITI_AGRARI", listIdPrestitiAgrari),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public int modificaPrestitiAgrari(long idProcedimentoOggetto, PrestitiAgrariDTO prestito) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"UPDATE NEMBO_T_PRESTITI_AGRARI SET																		\r\n" + 
	    		"    FINALITA_PRESTITO = :FINALITA_PRESTITO,															\r\n" + 
	    		"    ISTITUTO_EROGANTE = :ISTITUTO_EROGANTE,															\r\n" + 
	    		"    IMPORTO = :IMPORTO,																				\r\n" + 
	    		"    DATA_SCADENZA = :DATA_SCADENZA																		\r\n" + 
	    		"WHERE 																									\r\n" + 
	    		"    ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO													\r\n" +
	    		" 	 AND ID_PRESTITI_AGRARI  = :ID_PRESTITI_AGRARI														\r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("FINALITA_PRESTITO", prestito.getFinalitaPrestito(), Types.VARCHAR);
	      mapParameterSource.addValue("ISTITUTO_EROGANTE", prestito.getIstitutoErogante(), Types.VARCHAR);
	      mapParameterSource.addValue("IMPORTO", prestito.getImporto(), Types.NUMERIC);
	      mapParameterSource.addValue("DATA_SCADENZA", prestito.getDataScadenza(), Types.DATE);
	      mapParameterSource.addValue("ID_PRESTITI_AGRARI", prestito.getIdPrestitiAgrari(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("FINALITA_PRESTITO", prestito.getFinalitaPrestito()),
	              new LogParameter("ISTITUTO_EROGANTE", prestito.getIstitutoErogante()),
	              new LogParameter("IMPORTO", prestito.getImporto()),
	              new LogParameter("DATA_SCADENZA", prestito.getDataScadenza()),
	              new LogParameter("ID_PRESTITI_AGRARI", prestito.getIdPrestitiAgrari()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public List<FabbricatiDTO> getListFabbricati(long idProcedimentoOggetto, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT  F.ID_UTE,																										\r\n" + 
				"        U.DESCRIZIONE_COMUNE,																							\r\n" + 
				"        F.ID_FABBRICATO,																							\r\n" + 
				"        U.SIGLA_PROVINCIA,																								\r\n" + 
				"        U.INDIRIZZO,																									\r\n" + 
				"        F.DESC_TIPOLOGIA_FABBR AS TIPO_FABBRICATO,																		\r\n" + 
				"        F.DESC_FORMA_FABBR AS TIPOLOGIA,																				\r\n" + 
				"        F.SUPERFICIE,																									\r\n" + 
				"        F.DIMENSIONE,																									\r\n" + 
				"        F.UNITA_MISURA_TIPOLOGIA_FABBR        																			\r\n" + 
				"FROM    SMRGAA_V_FABBRICATI F,																							\r\n" + 
				"        SMRGAA_V_UTE U,																								\r\n" + 
				"        SMRGAA_V_DICH_CONSISTENZA DC,																					\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO																				\r\n" + 
				"WHERE   F.ID_UTE = U.ID_UTE																							\r\n" + 
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"        AND DC.ID_AZIENDA = F.ID_AZIENDA																				\r\n" + 
				"        AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F.DATA_INIZIO_VAL_FABBR AND NVL(DATA_FINE_VAL_FABBR,SYSDATE)		\r\n" + 
				"        AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																		\r\n" + 
				"        AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO															"
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO", idProcedimentoAgricoltura,Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,FabbricatiDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO", idProcedimentoAgricoltura),
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public FabbricatiDTO getFabbricato(long idProcedimentoOggetto, long idFabbricato, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT  F.ID_UTE,																									 	\r\n" +
				"		 F.ID_FABBRICATO,																								\r\n" +	
				"        U.DESCRIZIONE_COMUNE,																						 	\r\n" + 
				"        U.SIGLA_PROVINCIA,																							 	\r\n" + 
				"        U.INDIRIZZO,																								 	\r\n" + 
				"        F.DESC_TIPOLOGIA_FABBR AS TIPO_FABBRICATO,																	 	\r\n" + 
				"        F.DESC_FORMA_FABBR AS TIPOLOGIA,																			 	\r\n" + 
				"        F.DENOMINAZIONE,																								\r\n" + 
				"        F.LARGHEZZA,																									\r\n" + 
				"        F.LUNGHEZZA,																									\r\n" + 
				"        F.ALTEZZA,																										\r\n" + 
				"        F.SUPERFICIE,																								 	\r\n" + 
				"        F.DIMENSIONE,																									\r\n" + 
				"        F.UNITA_MISURA_TIPOLOGIA_FABBR,																				\r\n" + 
				"        F.ANNO_COSTRUZIONE,																							\r\n" + 
				"        F.UTM_X,																										\r\n" + 
				"        F.UTM_Y,																										\r\n" + 
				"        F.NOTE,																										\r\n" + 
				"        F.DATA_INIZIO_VAL_FABBR,																						\r\n" + 
				"        F.DATA_FINE_VAL_FABBR      																		 			\r\n" + 
				"FROM    SMRGAA_V_FABBRICATI F,																						 	\r\n" + 
				"        SMRGAA_V_UTE U,																								\r\n" + 
				"        SMRGAA_V_DICH_CONSISTENZA DC,																				 	\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO																				\r\n" + 
				"WHERE   F.ID_UTE = U.ID_UTE																							\r\n" + 
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"        AND DC.ID_AZIENDA = F.ID_AZIENDA																				\r\n" + 
				"        AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F.DATA_INIZIO_VAL_FABBR AND NVL(DATA_FINE_VAL_FABBR,SYSDATE)	 	\r\n" + 
				"        AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																	 	\r\n" + 
				"        AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" +
				"		 AND ID_FABBRICATO = :ID_FABBRICATO																							\r\n"		
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO", idProcedimentoAgricoltura,Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ID_FABBRICATO", idFabbricato, Types.NUMERIC);
			return queryForObject(QUERY, mapParameterSource,FabbricatiDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
					new LogParameter[] 
							{
									new LogParameter("ID_PROCEDIMENTO", idProcedimentoAgricoltura),
									new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
									new LogParameter("ID_UTE", idFabbricato)
							},
							new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public Long getNDanniScorte(long idProcedimentoOggetto, long[] arrayIdScortaMagazzino) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String QUERY = 
				"SELECT COUNT(*) AS N_DANNI_SCORTE														\r\n" + 
				"FROM NEMBO_T_DANNO_ATM																	\r\n" + 
				"WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO  								\r\n" +
				getInCondition("ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)) + "	\r\n" +
				getInCondition("EXT_ID_ENTITA_DANNEGGIATA", arrayIdScortaMagazzino);
				
        ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForLong(QUERY, mapParameterSource);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_DANNO", getListDanniEquivalenti(NemboConstants.DANNI.SCORTA)),
					new LogParameter("EXT_ID_ENTITA_DANNEGGIATA", arrayIdScortaMagazzino)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<FabbricatiDTO> getListFabbricatiNonDanneggiati(long idProcedimentoOggetto, long[] arrayIdFabbricato, int idProcedimentoAgricoltura) throws InternalUnexpectedException
	{

		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionIdFabbricato = "";
		if(arrayIdFabbricato != null)
		{
			inConditionIdFabbricato = getInCondition("F.ID_FABBRICATO", arrayIdFabbricato);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT  F.ID_UTE,																									\r\n" + 
				"        U.DESCRIZIONE_COMUNE,																						\r\n" + 
				"        F.ID_FABBRICATO,																							\r\n" + 
				"        U.SIGLA_PROVINCIA,																							\r\n" + 
				"        U.INDIRIZZO,																								\r\n" + 
				"        F.DESC_TIPOLOGIA_FABBR AS TIPO_FABBRICATO,																	\r\n" + 
				"        F.DESC_FORMA_FABBR AS TIPOLOGIA,																			\r\n" + 
				"        F.SUPERFICIE,																								\r\n" + 
				"        F.DIMENSIONE,																								\r\n" + 
				"        F.UNITA_MISURA_TIPOLOGIA_FABBR        																		\r\n" + 
				"FROM    SMRGAA_V_FABBRICATI F,																						\r\n" + 
				"        SMRGAA_V_UTE U,																								\r\n" + 
				"        SMRGAA_V_DICH_CONSISTENZA DC,																				\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO																				\r\n" + 
				"WHERE   F.ID_UTE = U.ID_UTE																							\r\n" + 
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = DC.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"        AND DC.ID_AZIENDA = F.ID_AZIENDA																				\r\n" + 
				"        AND DC.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F.DATA_INIZIO_VAL_FABBR AND NVL(DATA_FINE_VAL_FABBR,SYSDATE)		\r\n" + 
				"        AND DC.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																		\r\n" + 
				"        AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" + 
						 inConditionIdFabbricato + 																					   "\r\n" +
				"        AND F.ID_FABBRICATO NOT IN (																					\r\n" + 
				"                    SELECT 																																											\r\n" + 
				"                            F2.ID_FABBRICATO																																						\r\n" + 
				"                    FROM 	NEMBO_T_DANNO_ATM DA2,																								\r\n" + 
				"                            NEMBO_D_DANNO D2,																									\r\n" + 
				"                            NEMBO_D_UNITA_MISURA UM2,																							\r\n" + 
				"                            SMRGAA_V_FABBRICATI F2,																							\r\n" + 
				"                            NEMBO_T_PROCEDIMENTO_OGGETTO PO2,																					\r\n" + 
				"                            SMRGAA_V_DICH_CONSISTENZA DC2																						\r\n" + 
				"                    WHERE           																											\r\n" + 
				"                            DA2.EXT_ID_ENTITA_DANNEGGIATA = F2.ID_FABBRICATO																	\r\n" + 
				"                            AND D2.ID_DANNO = DA2.ID_DANNO																						\r\n" + 
				"                            AND UM2.ID_UNITA_MISURA = D2.ID_UNITA_MISURA																		\r\n" + 
				"                            AND DA2.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO       													\r\n" + 

				"                            AND PO2.EXT_ID_DICHIARAZIONE_CONSISTEN = DC2.ID_DICHIARAZIONE_CONSISTENZA											\r\n" + 
				"                            AND DC2.ID_AZIENDA = F2.ID_AZIENDA																					\r\n" + 
				"                            AND DC2.DATA_INSERIMENTO_DICHIARAZIONE BETWEEN F2.DATA_INIZIO_VAL_FABBR AND NVL(F2.DATA_FINE_VAL_FABBR,SYSDATE)	\r\n" + 
				"                            AND DC2.ID_PROCEDIMENTO = :ID_PROCEDIMENTO																			\r\n" + 
				"                            AND PO2.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 														\r\n" + 
				"        )"
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO", idProcedimentoAgricoltura,Types.NUMERIC);
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,FabbricatiDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO", idProcedimentoAgricoltura),
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public SuperficiColtureRiepilogoDTO getSuperficiColtureRiepilogo(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"WITH particelle AS (																				\r\n" + 
				"    SELECT																							\r\n" + 
				"        sc.id_superficie_coltura,																	\r\n" + 
				"        sp.id_particella,																			\r\n" + 
				"        sp.sup_catastale,																			\r\n" + 
				"        cu.superficie_utilizzata,																	\r\n" + 
				"        cu.flag_sau																				\r\n" + 
				"    FROM																							\r\n" + 
				"        nembo_t_superficie_coltura sc,																\r\n" + 
				"        nembo_t_procedimento_oggetto po,															\r\n" + 
				"        smrgaa_v_storico_particella sp,															\r\n" + 
				"        smrgaa_v_conduzione_utilizzo cu															\r\n" + 
				"    WHERE																							\r\n" + 
				"        sc.id_procedimento_oggetto = po.id_procedimento_oggetto									\r\n" + 
				"        AND sc.ext_id_utilizzo = cu.id_utilizzo													\r\n" + 
				"        AND po.ext_id_dichiarazione_consisten = cu.id_dichiarazione_consistenza					\r\n" + 
				"        AND cu.id_storico_particella = sp.id_storico_particella									\r\n" + 
				"        AND po.id_procedimento_oggetto =:ID_PROCEDIMENTO_OGGETTO									\r\n" + 
				"        AND cu.comune = sc.ext_istat_comune									\r\n" + 
				"),sau AS (																							\r\n" + 
				"    SELECT																							\r\n" + 
				"        SUM(DECODE(flag_sau,'S',superficie_utilizzata,0) ) AS sau_s,								\r\n" + 
				"        SUM(DECODE(flag_sau,'N',superficie_utilizzata,0) ) AS sau_n,								\r\n" + 
				"        SUM(DECODE(flag_sau,'A',superficie_utilizzata,0) ) AS sau_a								\r\n" + 
				"    FROM																							\r\n" + 
				"        particelle																					\r\n" + 
				") SELECT																							\r\n" + 
				"    (																								\r\n" + 
				"        SELECT																						\r\n" + 
				"            SUM(sup_catastale)																		\r\n" + 
				"        FROM																						\r\n" + 
				"            (																						\r\n" + 
				"                SELECT DISTINCT																	\r\n" + 
				"                    id_particella,																	\r\n" + 
				"                    sup_catastale																	\r\n" + 
				"                FROM																				\r\n" + 
				"                    particelle																		\r\n" + 
				"            )																						\r\n" + 
				"    ) AS sup_catastale,																			\r\n" + 
				"    (																								\r\n" + 
				"        SELECT																						\r\n" + 
				"            SUM(superficie_utilizzata)																\r\n" + 
				"        FROM																						\r\n" + 
				"            particelle																				\r\n" + 
				"    ) AS superficie_utilizzata,																	\r\n" + 
				"    sau_s,																							\r\n" + 
				"    sau_n,																							\r\n" + 
				"    sau_a																							\r\n" + 
				"  FROM																								\r\n" + 
				"    sau "
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForObject(QUERY, mapParameterSource,SuperficiColtureRiepilogoDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<SuperficiColtureDettaglioDTO> getListSuperficiColtureDettaglio(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return getListSuperficiColtureDettaglio(idProcedimentoOggetto,null);
	}
	

	private String getWithRiepilogoSuperficieColtura(String inConditionIdSuperficieColtura)
	{
		//TMP_SUPERFICIE_COLTURA 
		final String WITH_RIEPILOGO_SUPERFICIE_COLTURA =
				"WITH TMP_SUPERFICIE_COLTURA AS (    																\r\n" + 
				"     SELECT 																						\r\n" + 
				"        DA.SIGLA_PROVINCIA,						 												\r\n" + 
				"		DA.DESCRIZIONE_PROVINCIA AS DESC_PROVINCIA,													\r\n" + 
				"		DA.DESCRIZIONE_COMUNE AS DESC_COMUNE,														\r\n" + 
				"		CU.ID_UTILIZZO,																				\r\n" + 
				"		CU.COD_TIPO_UTILIZZO,																		\r\n" + 
				"		CU.DESC_TIPO_UTILIZZO,																		\r\n" + 
				"		CU.COD_TIPO_UTILIZZO_SECONDARIO,															\r\n" + 
				"		CU.DESC_TIPO_UTILIZZO_SECONDARIO,															\r\n" + 
				"       CU.SUPERFICIE_UTILIZZATA,																	\r\n" + 
				"		SC.ID_SUPERFICIE_COLTURA,																	\r\n" + 
				"       SC.EXT_ISTAT_COMUNE,																		\r\n" + 
				"       SC.COLTURA_SECONDARIA,																 		\r\n" + 
				"		SC.PRODUZIONE_HA,																			\r\n" + 
				"		SC.GIORNATE_LAVORATE,																		\r\n" + 
				"		SC.UF_REIMPIEGATE,																			\r\n" + 
				"		SC.QLI_REIMPIEGATI,																		 	\r\n" + 
				"		SC.PREZZO,																					\r\n" + 
				"       SC.NOTE,																					\r\n" + 
				"       SC.RECORD_MODIFICATO,																		\r\n" + 
				"        (SELECT U.UF_PRODOTTE																		\r\n" + 
				"			FROM NEMBO_D_UTILIZZO U																	\r\n" + 
				"			WHERE U.EXT_ID_UTILIZZO = SC.EXT_ID_UTILIZZO) AS UF_PRODOTTE,							\r\n" +
				"		SC.PRODUZIONE_TOTALE_DANNO, 																		\r\n" +	
				"		SC.PREZZO_DANNEGGIATO 																				\r\n" +	
				"	FROM																							\r\n" + 
				"		NEMBO_T_SUPERFICIE_COLTURA SC,																\r\n" + 
				"		NEMBO_T_PROCEDIMENTO_OGGETTO PO,															\r\n" + 
				"		SMRGAA_V_CONDUZIONE_UTILIZZO CU,															\r\n" + 
				"       SMRGAA_V_DATI_AMMINISTRATIVI DA																\r\n" + 
				"	WHERE																							\r\n" + 
				"		PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO										\r\n" + 
				"            AND PO.ID_PROCEDIMENTO_OGGETTO = SC.ID_PROCEDIMENTO_OGGETTO						 	\r\n" + 
				"                AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA			\r\n" + 
				"                    AND CU.ID_UTILIZZO = SC.EXT_ID_UTILIZZO										\r\n" + 
				"                        AND CU.COMUNE = SC.EXT_ISTAT_COMUNE										\r\n" + 
				"                            AND SC.EXT_ISTAT_COMUNE = DA.ISTAT_COMUNE								\r\n" + 
											inConditionIdSuperficieColtura + " 										\r\n" +
				"), TMP_RIEPILOGO_SC AS (																			\r\n" + 
				"SELECT 																							\r\n" + 
				"            ID_SUPERFICIE_COLTURA,																	\r\n" + 
				"            EXT_ISTAT_COMUNE,																		\r\n" + 
				"            NOTE,																					\r\n" + 
				"            RECORD_MODIFICATO,																		\r\n" + 
				"            DESC_COMUNE || ' (' || SIGLA_PROVINCIA || ')' AS UBICAZIONE_TERRENO,					\r\n" + 
				"            DESC_PROVINCIA,																		\r\n" + 
				"            DESC_COMUNE,																			\r\n" + 
				"            ID_UTILIZZO,																			\r\n" + 
				"            COD_TIPO_UTILIZZO,																		\r\n" + 
				"            DESC_TIPO_UTILIZZO,																	\r\n" + 
				"            COD_TIPO_UTILIZZO_SECONDARIO,															\r\n" + 
				"            DESC_TIPO_UTILIZZO_SECONDARIO,															\r\n" + 
				"            DECODE(COLTURA_SECONDARIA,'S',															\r\n" + 
				"                NVL(DESC_TIPO_UTILIZZO_SECONDARIO,DESC_TIPO_UTILIZZO) || ' [' || NVL(COD_TIPO_UTILIZZO_SECONDARIO,COD_TIPO_UTILIZZO) || '] ' || '(secondario)',	  \r\n" + 
				"                    DESC_TIPO_UTILIZZO || ' [' || COD_TIPO_UTILIZZO || ']'							\r\n" + 
				"                ) AS TIPO_UTILIZZO_DESCRIZIONE,													\r\n" + 
				"            COLTURA_SECONDARIA,																	\r\n" + 
				"            SUM(SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,									\r\n" + 
				"            PRODUZIONE_HA,																			\r\n" + 
				"            ROUND(PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA),2) AS PRODUZIONE_DICHIARATA,			\r\n" + 
				"            ROUND(GIORNATE_LAVORATE * SUM(SUPERFICIE_UTILIZZATA),2) AS GIORNATE_LAVORATIVE_DICH,	\r\n" + 
				"            ROUND((UF_PRODOTTE * PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA)),2) AS UF_TOTALI,		\r\n" + 
				"            QLI_REIMPIEGATI,																		\r\n" + 
				"            UF_REIMPIEGATE,																		\r\n" + 
				"            DECODE(																				\r\n" + 
				"                NVL(UF_REIMPIEGATE,0), 0,															\r\n" + 
				"                ROUND((PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA) - NVL(QLI_REIMPIEGATI,0)),2),	\r\n" + 
				"                ROUND((PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA) - (NVL(UF_REIMPIEGATE,0) / UF_PRODOTTE )),2)    	  \r\n" + 
				"                )AS PLV_TOT_QUINTALI,																\r\n" + 
				"            PREZZO,																				\r\n" + 
				"            ROUND(PREZZO * 																		\r\n" + 
				"                DECODE(																			\r\n" + 
				"                 NVL(UF_REIMPIEGATE,0), 0,															\r\n" + 
				"                ROUND((PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA) - NVL(QLI_REIMPIEGATI,0)),2),	\r\n" + 
				"                ROUND((PRODUZIONE_HA * SUM(SUPERFICIE_UTILIZZATA) - (NVL(UF_REIMPIEGATE,0) / UF_PRODOTTE )),2) \r\n" + 
				"                ),2) AS PLV_TOT_DICH,																\r\n" + 
				"            UF_PRODOTTE, 																			\r\n" + 
				"            PRODUZIONE_TOTALE_DANNO, 																			\r\n" + 
				"            PREZZO_DANNEGGIATO 																				\r\n" + 
				"FROM 																								\r\n" + 
				"    TMP_SUPERFICIE_COLTURA TSC																		\r\n" + 
				"GROUP BY 																							\r\n" + 
				"    ID_SUPERFICIE_COLTURA, 																		\r\n" + 
				"    PRODUZIONE_TOTALE_DANNO, 																			\r\n" + 
				"    PREZZO_DANNEGGIATO, 																				\r\n" + 
				"    EXT_ISTAT_COMUNE, 																				\r\n" + 
				"    SIGLA_PROVINCIA, 																				\r\n" + 
				"    DESC_PROVINCIA, 																				\r\n" + 
				"    DESC_COMUNE, 																					\r\n" + 
				"    ID_UTILIZZO, 																					\r\n" + 
				"    COD_TIPO_UTILIZZO,	 																			\r\n" + 
				"    DESC_TIPO_UTILIZZO, 																			\r\n" + 
				"    COD_TIPO_UTILIZZO_SECONDARIO, 																	\r\n" + 
				"    DESC_TIPO_UTILIZZO_SECONDARIO, 																\r\n" + 
				"    COLTURA_SECONDARIA,																			\r\n" + 
				"    PRODUZIONE_HA, 																				\r\n" + 
				"    GIORNATE_LAVORATE, 																			\r\n" + 
				"    UF_REIMPIEGATE, 																				\r\n" + 
				"    QLI_REIMPIEGATI, 																				\r\n" + 
				"    PREZZO, 																						\r\n" + 
				"    NOTE, 																							\r\n" + 
				"    RECORD_MODIFICATO, 																			\r\n" + 
				"    UF_PRODOTTE, 																					\r\n" + 
				"    DESC_COMUNE || ' (' || SIGLA_PROVINCIA || ')', 												\r\n" + 
				"    DESC_PROVINCIA, 																				\r\n" + 
				"    DESC_COMUNE, 																					\r\n" + 
				"    DECODE(COLTURA_SECONDARIA,'S', 																\r\n" + 
				"        NVL(DESC_TIPO_UTILIZZO_SECONDARIO,DESC_TIPO_UTILIZZO) || ' [' || NVL(COD_TIPO_UTILIZZO_SECONDARIO,COD_TIPO_UTILIZZO) || '] ' || '(secondario)', DESC_TIPO_UTILIZZO || ' [' || COD_TIPO_UTILIZZO || ']' )\r\n" + 
				" ORDER BY	 																						\r\n" +
				" 	SIGLA_PROVINCIA, 																				\r\n" +
				" 	DESC_COMUNE, 																					\r\n" +
				" 	TIPO_UTILIZZO_DESCRIZIONE 																		\r\n" +
				" ) 																								\r\n"
				;
			return WITH_RIEPILOGO_SUPERFICIE_COLTURA;
	}
	
	
	public List<SuperficiColtureDettaglioDTO> getListSuperficiColtureDettaglio(long idProcedimentoOggetto, List<Long> idSuperficieColtura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		String inConditionIdSuperficieColtura = " ";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		if(idSuperficieColtura != null && !idSuperficieColtura.toString().equals(""))
		{
			inConditionIdSuperficieColtura = getInCondition("ID_SUPERFICIE_COLTURA", idSuperficieColtura);
		}
		

		final String QUERY =
				getWithRiepilogoSuperficieColtura(inConditionIdSuperficieColtura) + 
				"SELECT * FROM TMP_RIEPILOGO_SC";
				;
		
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,SuperficiColtureDettaglioDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<ControlloColturaDTO> getListControlloColtura(long idProcedimentoOggetto, long[] arrayIdSuperficieColtura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		String inConditionIdSuperficieColtura = " ";
		if(arrayIdSuperficieColtura != null)
		{
			inConditionIdSuperficieColtura = getInCondition("CC.ID_SUPERFICIE_COLTURA", arrayIdSuperficieColtura);
		}
		
		final String QUERY = 
				"SELECT																								\r\n" + 
				"    cc.id_superficie_coltura,																		\r\n" + 
				"    cc.id_controllo_coltura,																		\r\n" + 
				"    cc.bloccante,																					\r\n" + 
				"    cc.descrizione_anomalia																		\r\n" + 
				"FROM																								\r\n" + 
				"    nembo_t_superficie_coltura sc,																	\r\n" + 
				"    nembo_t_procedimento_oggetto po,																\r\n" + 
				"    smrgaa_v_storico_particella sp,																\r\n" + 
				"    smrgaa_v_conduzione_utilizzo cu,																\r\n" + 
				"    nembo_t_controllo_coltura cc																	\r\n" + 
				"WHERE																								\r\n" + 
				"    po.id_procedimento_oggetto = sc.id_procedimento_oggetto										\r\n" + 
				"    AND po.ext_id_dichiarazione_consisten = cu.id_dichiarazione_consistenza						\r\n" + 
				"        AND sc.ext_id_utilizzo = cu.id_utilizzo													\r\n" + 
				"            AND cu.id_storico_particella = sp.id_storico_particella								\r\n" + 
				"                AND po.id_procedimento_oggetto =:ID_PROCEDIMENTO_OGGETTO							\r\n" +
				"                	AND sc.id_superficie_coltura = cc.id_superficie_coltura \r\n" +
									inConditionIdSuperficieColtura + 
				"GROUP BY cc.id_superficie_coltura, cc.id_controllo_coltura, cc.bloccante, cc.descrizione_anomalia	\r\n" + 
				"ORDER BY																							\r\n" + 
				"    cc.id_superficie_coltura, cc.id_controllo_coltura"
		
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,ControlloColturaDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public SuperficiColtureDettaglioDTO getSuperficiColtureDettaglio(long idProcedimentoOggetto,
			long idSuperficieColtura) throws InternalUnexpectedException
	{
		List<Long> listIdSuperficieColtura = new ArrayList<Long>();
		listIdSuperficieColtura.add(idSuperficieColtura);
		List<SuperficiColtureDettaglioDTO> list = this.getListSuperficiColtureDettaglio(idProcedimentoOggetto, listIdSuperficieColtura);
		if(list == null)
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	
	public SuperficiColtureDettaglioPsrDTO getSuperficiColtureDettaglioPsrDTO(long idProcedimentoOggetto, long idSuperficieColtura) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				" WITH TMP_PARTICELLE AS (																										\r\n" + 
				"SELECT																															\r\n" + 
				"    SC.ID_SUPERFICIE_COLTURA,																									\r\n" + 
				"    SC.PRODUZIONE_HA,																											\r\n" + 
				"    (																															\r\n" + 
				"        SELECT																													\r\n" + 
				"            PH.PRODUZIONE_HA_MEDIA																								\r\n" + 
				"        FROM																													\r\n" + 
				"            NEMBO_D_PRODUZIONE_HA PH																							\r\n" + 
				"        WHERE																													\r\n" + 
				"            PH.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE															\r\n" + 
				"            AND PH.EXT_ID_UTILIZZO = CU.ID_UTILIZZO																			\r\n" + 
				"    ) AS PRODUZIONE_HA_MEDIA,        																							\r\n" +
				
				"    (\r\n" + 
				"        SELECT\r\n" + 
				"            PH.PRODUZIONE_HA_MIN\r\n" + 
				"        FROM\r\n" + 
				"            NEMBO_D_PRODUZIONE_HA PH\r\n" + 
				"        WHERE\r\n" + 
				"            PH.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE\r\n" + 
				"            AND PH.EXT_ID_UTILIZZO = CU.ID_UTILIZZO\r\n" + 
				"    ) AS PRODUZIONE_HA_MIN,\r\n" + 
				"    (\r\n" + 
				"        SELECT\r\n" + 
				"            PH.PRODUZIONE_HA_MAX\r\n" + 
				"        FROM\r\n" + 
				"            NEMBO_D_PRODUZIONE_HA PH\r\n" + 
				"        WHERE\r\n" + 
				"            PH.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE\r\n" + 
				"            AND PH.EXT_ID_UTILIZZO = CU.ID_UTILIZZO\r\n" + 
				"    ) AS PRODUZIONE_HA_MAX, \r\n" +
	
				"    SC.GIORNATE_LAVORATE,																										\r\n" + 
				"    (																															\r\n" + 
				"        SELECT																													\r\n" + 
				"            GL.GIORNATE_LAVORATE_MEDIE																							\r\n" + 
				"        FROM																													\r\n" + 
				"            NEMBO_D_GG_LAVORATE GL																								\r\n" + 
				"        WHERE																													\r\n" + 
				"            GL.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE															\r\n" + 
				"            AND GL.EXT_ID_UTILIZZO = CU.ID_UTILIZZO																			\r\n" + 
				"    ) AS GIORNATE_LAVORATE_MEDIE,																								\r\n" +
				
				"	(																															\r\n" + 
				"        SELECT																													\r\n" + 
				"            GL.GIORNATE_LAVORATE_MIN																							\r\n" + 
				"        FROM																													\r\n" + 
				"            NEMBO_D_GG_LAVORATE GL																								\r\n" + 
				"        WHERE																													\r\n" + 
				"            GL.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE															\r\n" + 
				"            AND GL.EXT_ID_UTILIZZO = CU.ID_UTILIZZO																			\r\n" + 
				"    ) AS GIORNATE_LAVORATE_MIN,																								\r\n" + 
				"    (																															\r\n" + 
				"        SELECT																													\r\n" + 
				"            GL.GIORNATE_LAVORATE_MAX																							\r\n" + 
				"        FROM																													\r\n" + 
				"            NEMBO_D_GG_LAVORATE GL																								\r\n" + 
				"        WHERE																													\r\n" + 
				"            GL.EXT_ID_ZONA_ALTIMETRICA = SP.ID_ZONA_ALTIMETRICA_COMUNE															\r\n" + 
				"            AND GL.EXT_ID_UTILIZZO = CU.ID_UTILIZZO																			\r\n" + 
				"    ) AS GIORNATE_LAVORATE_MAX,																								\r\n" + 
				
				"    SC.QLI_REIMPIEGATI,																										\r\n" + 
				"    SC.UF_REIMPIEGATE,																											\r\n" + 
				"    SC.PREZZO,																													\r\n" + 
				"    U.PREZZO_MEDIO,																											\r\n" + 
				"    U.PREZZO_MIN,																											\r\n" + 
				"    U.PREZZO_MAX,																											\r\n" + 
				"    U.UF_PRODOTTE,																												\r\n" + 
				"    SUM(CU.SUPERFICIE_UTILIZZATA) AS SUM_SUPERFICIE_UTILIZZATA																	\r\n" + 
				"FROM																															\r\n" + 
				"    NEMBO_T_SUPERFICIE_COLTURA SC,																								\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,																							\r\n" + 
				"    SMRGAA_V_STORICO_PARTICELLA SP,																							\r\n" + 
				"    SMRGAA_V_CONDUZIONE_UTILIZZO CU,																							\r\n" + 
				"    NEMBO_D_UTILIZZO U																											\r\n" + 
				"WHERE																															\r\n" + 
				"    PO.ID_PROCEDIMENTO_OGGETTO = SC.ID_PROCEDIMENTO_OGGETTO																	\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA													\r\n" + 
				"    AND SC.EXT_ID_UTILIZZO = CU.ID_UTILIZZO																					\r\n" + 
				"    AND SC.EXT_ISTAT_COMUNE = CU.COMUNE																						\r\n" + 
				"    AND CU.ID_STORICO_PARTICELLA = SP.ID_STORICO_PARTICELLA																	\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO																	\r\n" + 
				"    AND SC.ID_SUPERFICIE_COLTURA =:ID_SUPERFICIE_COLTURA																		\r\n" + 
				"    AND SC.EXT_ID_UTILIZZO = U.EXT_ID_UTILIZZO																					\r\n" + 
				"GROUP BY																														\r\n" + 
				"    SC.ID_SUPERFICIE_COLTURA,																									\r\n" + 
				"    SC.PRODUZIONE_HA,																											\r\n" + 
				"    SC.QLI_REIMPIEGATI,																										\r\n" + 
				"    SC.GIORNATE_LAVORATE,																										\r\n" + 
				"    SC.UF_REIMPIEGATE,																											\r\n" + 
				"    SC.PREZZO,																													\r\n" + 
				"    U.UF_PRODOTTE,																												\r\n" + 
				"    U.PREZZO_MEDIO,																											\r\n" + 
				"    U.PREZZO_MIN,																											\r\n" + 
				"    U.PREZZO_MAX,																											\r\n" + 
				"    CU.ID_UTILIZZO,																											\r\n" + 
				"    SP.ID_ZONA_ALTIMETRICA_COMUNE																								\r\n" + 
				"),																																\r\n" + 
				"TMP_PLV_PARTICELLE AS (																										\r\n" + 
				"    SELECT 																													\r\n" + 
				"        P.ID_SUPERFICIE_COLTURA,																								\r\n" + 
				"        DECODE(NVL(P.UF_REIMPIEGATE,0), 0,																					  	\r\n" + 
				"            ROUND((P.PRODUZIONE_HA * SUM_SUPERFICIE_UTILIZZATA - NVL(P.QLI_REIMPIEGATI,0)),2),									\r\n" + 
				"            ROUND(((P.PRODUZIONE_HA * SUM_SUPERFICIE_UTILIZZATA) - (NVL(P.UF_REIMPIEGATE,0) / P.UF_PRODOTTE)),2)   			\r\n" + 
				"        ) AS PLV_TOT_Q,																										\r\n" + 
				"        DECODE(NVL(P.UF_REIMPIEGATE,0), 0,																					  	\r\n" + 
				"            ROUND((P.PRODUZIONE_HA_MEDIA * SUM_SUPERFICIE_UTILIZZATA - NVL(P.QLI_REIMPIEGATI,0)),2),							\r\n" + 
				"            ROUND(((P.PRODUZIONE_HA_MEDIA * SUM_SUPERFICIE_UTILIZZATA) - (NVL(P.UF_REIMPIEGATE,0) / P.UF_PRODOTTE )),2)   		\r\n" + 
				"        ) AS PLV_TOT_Q_CALC      																								\r\n" + 
				"    FROM TMP_PARTICELLE P																										\r\n" +
				")																																\r\n" + 
				"SELECT 																														\r\n" + 
				"    ROUND(P.PRODUZIONE_HA * SUM_SUPERFICIE_UTILIZZATA,2) AS PROD_TOTALE,														\r\n" + 
				"    P.PRODUZIONE_HA_MEDIA * SUM_SUPERFICIE_UTILIZZATA AS PROD_TOTALE_CALC,														\r\n" + 
				"    ROUND(P.GIORNATE_LAVORATE * SUM_SUPERFICIE_UTILIZZATA,2)  AS GIORNATE_LAVORATIVE_TOT,										\r\n" + 
				"    P.GIORNATE_LAVORATE_MEDIE * SUM_SUPERFICIE_UTILIZZATA AS GIORNATE_LAVORATIVE_TOT_CALC,										\r\n" + 
				"    ROUND(P.UF_PRODOTTE * P.PRODUZIONE_HA * SUM_SUPERFICIE_UTILIZZATA,2) AS UF_TOT,											\r\n" + 
				"    P.UF_PRODOTTE * P.PRODUZIONE_HA_MEDIA * SUM_SUPERFICIE_UTILIZZATA AS UF_TOT_CALC,											\r\n" + 
				"    PP.PLV_TOT_Q,																												\r\n" + 
				"    PP.PLV_TOT_Q_CALC,																											\r\n" + 
				"    ROUND((PP.PLV_TOT_Q * P.PREZZO),2) AS PLV_TOT_DICH,																					\r\n" + 
				"    (PLV_TOT_Q_CALC * P.PREZZO_MEDIO) AS PLV_TOT_DICH_CALC,																	\r\n" + 
				"    P.*       																													\r\n" + 
				"FROM TMP_PARTICELLE P,																											\r\n" + 
				"     TMP_PLV_PARTICELLE PP																										\r\n" + 
				"WHERE P.ID_SUPERFICIE_COLTURA = PP.ID_SUPERFICIE_COLTURA"
		
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ID_SUPERFICIE_COLTURA", idSuperficieColtura, Types.NUMERIC);
			return queryForObject(QUERY, mapParameterSource,SuperficiColtureDettaglioPsrDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_SUPERFICIE_COLTURA", idSuperficieColtura),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<SuperficiColtureDettaglioParticellareDTO> getListDettaglioParticellareSuperficiColture(
			long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT\r\n" + 
				"    sp.id_storico_particella,																\r\n" + 
				"    sp.sezione,																			\r\n" + 
				"    sp.foglio,																				\r\n" + 
				"    sp.particella,																			\r\n" + 
				"    sp.subalterno,																			\r\n" + 
				"    sp.sup_catastale,																		\r\n" + 
				"    cu.superficie_utilizzata,																\r\n" + 
				"    cu.desc_titolo_possesso,																\r\n" + 
				"    cu.desc_zona_altimetrica																\r\n" + 
				"FROM																						\r\n" + 
				"    nembo_t_superficie_coltura sc,															\r\n" + 
				"    nembo_t_procedimento_oggetto po,														\r\n" + 
				"    smrgaa_v_storico_particella sp,														\r\n" + 
				"    smrgaa_v_conduzione_utilizzo cu,														\r\n" + 
				"    nembo_d_utilizzo u																		\r\n" + 
				"WHERE																						\r\n" + 
				"    po.id_procedimento_oggetto = sc.id_procedimento_oggetto								\r\n" + 
				"    AND po.ext_id_dichiarazione_consisten = cu.id_dichiarazione_consistenza				\r\n" + 
				"    AND sc.ext_id_utilizzo = cu.id_utilizzo												\r\n" + 
				"    AND cu.id_storico_particella = sp.id_storico_particella								\r\n" + 
				"    AND sc.ext_id_utilizzo = u.ext_id_utilizzo \r\n" +
				"    AND po.id_procedimento_oggetto =:ID_PROCEDIMENTO_OGGETTO								\r\n" + 
				"    AND sc.id_superficie_coltura =:ID_SUPERFICIE_COLTURA									\r\n" +
				"    AND cu.comune = sc.ext_istat_comune													\r\n" 
				;
		
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ID_SUPERFICIE_COLTURA", idSuperficieColtura, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,SuperficiColtureDettaglioParticellareDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_SUPERFICIE_COLTURA", idSuperficieColtura),
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}


	public int eliminaControlloColtura(long idProcedimentoOggetto, long idSuperficieColtura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		"DELETE FROM NEMBO_T_CONTROLLO_COLTURA							\r\n" + 
	    		"WHERE 															\r\n" + 
	    		"	 ID_SUPERFICIE_COLTURA IN 									\r\n" + 
	    		"    (SELECT ID_SUPERFICIE_COLTURA								\r\n" + 
	    		"    FROM NEMBO_T_SUPERFICIE_COLTURA							\r\n" + 
	    		"    WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 	\r\n" +
	    		" 			AND ID_SUPERFICIE_COLTURA =:ID_SUPERFICIE_COLTURA ) \r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_SUPERFICIE_COLTURA",idSuperficieColtura, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_SUPERFICIE_COLTURA", idSuperficieColtura)
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public int inserisciControlloColtura(long idProcedimentoOggetto, long idSuperficieColtura,
			ControlloColturaDTO controlloColtura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = 
	    		"INSERT INTO NEMBO_T_CONTROLLO_COLTURA								\r\n" + 
	    		"(ID_CONTROLLO_COLTURA,ID_SUPERFICIE_COLTURA,DESCRIZIONE_ANOMALIA,	\r\n" + 
	    		"BLOCCANTE)															\r\n" + 
	    		"VALUES (   														\r\n" + 
	    		"    :SEQ_NEMBO_T_CONTROLLO_COLTURA,								\r\n" + 
	    		"    :ID_SUPERFICIE_COLTURA,										\r\n" + 
	    		"    :DESCRIZIONE_ANOMALIA,											\r\n" + 
	    		"    :BLOCCANTE)"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTControlloColtura = 0;
	    try
	    {
	      seqNemboTControlloColtura = getNextSequenceValue("SEQ_NEMBO_T_CONTROLLO_COLTURA");
	      mapParameterSource.addValue("SEQ_NEMBO_T_CONTROLLO_COLTURA", seqNemboTControlloColtura, Types.NUMERIC);
	      mapParameterSource.addValue("ID_SUPERFICIE_COLTURA", idSuperficieColtura, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("DESCRIZIONE_ANOMALIA", controlloColtura.getDescrizioneAnomalia(), Types.VARCHAR);
	      mapParameterSource.addValue("BLOCCANTE", controlloColtura.getBloccante(), Types.VARCHAR);
	      return namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("SEQ_NEMBO_T_CONTROLLO_COLTURA", seqNemboTControlloColtura),
	              new LogParameter("ID_SUPERFICIE_COLTURA", idSuperficieColtura),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("DESCRIZIONE_ANOMALIA", controlloColtura.getDescrizioneAnomalia()),
	              new LogParameter("BLOCCANTE", controlloColtura.getBloccante()),
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public void updateSuperficieColtura(long idProcedimentoOggetto, SuperficiColtureDettaglioPsrDTO superficieColturaDettaglioDTO) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"UPDATE NEMBO_T_SUPERFICIE_COLTURA									\r\n" + 
	    		"SET 																\r\n" + 
	    		"        PRODUZIONE_HA = :PRODUZIONE_HA,							\r\n" + 
	    		"        GIORNATE_LAVORATE = :GIORNATE_LAVORATE,					\r\n" + 
	    		"        QLI_REIMPIEGATI = :QLI_REIMPIEGATI,						\r\n" + 
	    		"        UF_REIMPIEGATE = :UF_REIMPIEGATE,							\r\n" + 
	    		"        PREZZO = :PREZZO,											\r\n" + 
	    		"        NOTE = :NOTE,												\r\n" + 
	    		"		 RECORD_MODIFICATO = 'S'									\r\n" +	
	    		"WHERE   ID_SUPERFICIE_COLTURA = :ID_SUPERFICIE_COLTURA				\r\n" + 
	    		"        AND ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO		\r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_SUPERFICIE_COLTURA", superficieColturaDettaglioDTO.getIdSuperficieColtura(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("PRODUZIONE_HA", superficieColturaDettaglioDTO.getProduzioneHa(), Types.NUMERIC);
	      mapParameterSource.addValue("GIORNATE_LAVORATE", superficieColturaDettaglioDTO.getGiornateLavorate(), Types.NUMERIC);
	      mapParameterSource.addValue("QLI_REIMPIEGATI", superficieColturaDettaglioDTO.getQliReimpiegati(), Types.NUMERIC);
	      mapParameterSource.addValue("UF_REIMPIEGATE", superficieColturaDettaglioDTO.getUfReimpiegate(), Types.NUMERIC);
	      mapParameterSource.addValue("PREZZO", superficieColturaDettaglioDTO.getPrezzo(), Types.NUMERIC);
	      mapParameterSource.addValue("NOTE", superficieColturaDettaglioDTO.getNote(), Types.VARCHAR);
	      namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_SUPERFICIE_COLTURA", superficieColturaDettaglioDTO.getIdSuperficieColtura()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("PRODUZIONE_HA", superficieColturaDettaglioDTO.getProduzioneHa()),
	              new LogParameter("GIORNATE_LAVORATE", superficieColturaDettaglioDTO.getGiornateLavorate()),
	              new LogParameter("QLI_REIMPIEGATI", superficieColturaDettaglioDTO.getQliReimpiegati()),
	              new LogParameter("UF_REIMPIEGATE", superficieColturaDettaglioDTO.getUfReimpiegate()),
	              new LogParameter("PREZZO", superficieColturaDettaglioDTO.getPrezzo()),
	              new LogParameter("NOTE", superficieColturaDettaglioDTO.getNote())
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public List<SuperficiColturePlvVegetaleDTO> getListSuperficiColturePlvVegetale(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
			getWithRiepilogoSuperficieColtura(" ") + 
				"SELECT  																																								\r\n" + 
				"    ID_UTILIZZO,	 																																					\r\n" + 
				"    TIPO_UTILIZZO_DESCRIZIONE, 																																		\r\n" + 
				"    SUM(SUPERFICIE_UTILIZZATA) as SUPERFICIE_UTILIZZATA, 																												\r\n" + 
				"    SUM(PRODUZIONE_HA * SUPERFICIE_UTILIZZATA) as PRODUZIONE_Q, 																										\r\n" + 
				"    SUM(GIORNATE_LAVORATIVE_DICH) as GIORNATE_LAV_PER_SUP_UTIL, 																										\r\n" + 
				"    SUM(UF_TOTALI) as UF, 																																				\r\n" + 
				"    SUM(NVL(QLI_REIMPIEGATI,0)) AS REIMPIEGHI_Q, 																														\r\n" + 
				"    SUM(NVL(UF_REIMPIEGATE,0)) AS REIMPIEGHI_UF, 																														\r\n" + 
				"    SUM(PLV_TOT_DICH) AS PLV 																																			\r\n" + 
				"FROM TMP_RIEPILOGO_SC PV 																																				\r\n" + 
				"GROUP BY  																																								\r\n" + 
				"    ID_UTILIZZO, 																																						\r\n" + 
				"    TIPO_UTILIZZO_DESCRIZIONE 		"				
				
				;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,SuperficiColturePlvVegetaleDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}


	public List<DecodificaDTO<String>> getListComuniPerProvinciaConTerreniInConduzioneDanniSuperficiColture(long idProcedimentoOggetto, String istatProvincia) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT 																						\r\n" + 
				"    CU.COMUNE AS CODICE,																		\r\n" + 
				"    CU.COMUNE AS ID,																			\r\n" + 
				"    CU.DESC_COMUNE AS DESCRIZIONE																\r\n" + 
				"FROM 																							\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,															\r\n" + 
				"    SMRGAA_V_CONDUZIONE_UTILIZZO CU															\r\n" + 
				"WHERE 																							\r\n" + 
				"        PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA					\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO									\r\n" + 
				"    AND CU.ISTAT_PROVINCIA = :ISTAT_PROVINCIA													\r\n" + 
				"GROUP BY 																						\r\n" + 
				"    CU.COMUNE,																					\r\n" + 
				"    CU.DESC_COMUNE																				\r\n" + 
				"ORDER BY CU.DESC_COMUNE "
				;

		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ISTAT_PROVINCIA", istatProvincia, Types.VARCHAR);
			return queryForDecodificaString(QUERY, mapParameterSource);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ISTAT_PROVINCIA", istatProvincia)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}


	public List<DecodificaDTO<String>> getListProvinciaConTerreniInConduzione(long idProcedimentoOggetto,
			String ID_REGIONE_PIEMONTE) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT 																				\r\n" + 
				"    CU.ISTAT_PROVINCIA AS CODICE,														\r\n" + 
				"    CU.ISTAT_PROVINCIA AS ID,															\r\n" + 
				"    CU.DESC_PROVINCIA AS DESCRIZIONE													\r\n" + 
				"FROM 																					\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,													\r\n" + 
				"    SMRGAA_V_CONDUZIONE_UTILIZZO CU													\r\n" + 
				"WHERE 																					\r\n" + 
				"    	 PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA			\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO							\r\n" + 
				"    AND CU.ID_REGIONE = :ID_REGIONE													\r\n" + 
				"GROUP BY 																				\r\n" + 
				"    CU.ISTAT_PROVINCIA,																\r\n" + 
				"    CU.DESC_PROVINCIA																	\r\n" + 
				"ORDER BY CU.DESC_PROVINCIA "
				;

		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ID_REGIONE", ID_REGIONE_PIEMONTE, Types.VARCHAR);
			return queryForDecodificaString(QUERY, mapParameterSource);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_REGIONE", ID_REGIONE_PIEMONTE)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<DecodificaDTO<String>> getListSezioniPerComuneConTerreniInConduzioneDanniSuperficiColture(
			long idProcedimentoOggetto, String istatComune) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				"SELECT 																											\r\n" + 
				"    NVL(CU.SEZIONE,'null') AS ID,                                             										\r\n" + 
				"    NVL(CU.SEZIONE,'null') AS CODICE,                                         										\r\n" + 
				"    DECODE(CU.SEZIONE, NULL, 'Non presente', CU.SEZIONE || ' - ' || CU.DESC_SEZIONE) AS DESCRIZIONE               	\r\n" + 
				"FROM 																												\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,																				\r\n" + 
				"    SMRGAA_V_CONDUZIONE_UTILIZZO CU																				\r\n" + 
				"WHERE 																												\r\n" + 
				"    PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA										\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO														\r\n" + 
				"    AND CU.COMUNE = :ISTAT_COMUNE																					\r\n" + 
				"GROUP BY 																											\r\n" + 
				"    CU.SEZIONE, CU.DESC_SEZIONE, NVL(CU.SEZIONE,'null'), DECODE(CU.SEZIONE, NULL, 'Non presente', CU.SEZIONE || ' - ' || CU.DESC_SEZIONE)	\r\n" + 
				"ORDER BY CU.DESC_SEZIONE	"
				;

		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
			return queryForDecodificaString(QUERY, mapParameterSource);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ISTAT_COMUNE", istatComune)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<ParticelleDanniDTO> getListConduzioniDannoSelezionate(
			long idProcedimentoOggetto, 
			long[] arrayIdUtilizzoDichiarato,
			boolean piantagioniArboree) throws InternalUnexpectedException
	{
		return getListConduzioniDanno(
				idProcedimentoOggetto, 
				null, 
				false, 
				arrayIdUtilizzoDichiarato, 
				piantagioniArboree);
	}
	
	public List<ParticelleDanniDTO> getListConduzioniDannoEscludendoGiaSelezionate(
			long idProcedimentoOggetto,
			FiltroRicercaConduzioni filtroRicercaConduzioni,
			boolean piantagioniArboreee) throws InternalUnexpectedException
	{
		return getListConduzioniDanno(
				idProcedimentoOggetto, 
				filtroRicercaConduzioni, 
				true, 
				null, 
				piantagioniArboreee
				);
	}
	
	public List<ParticelleDanniDTO> getListConduzioniDanno (
				long idProcedimentoOggetto,  
				FiltroRicercaConduzioni filtroRicercaConduzioni,
				boolean escludiDaRicercaParticelleFiltro,
				long[] arrayIdUtilizzoDichiarato,
				boolean piantagioniArboree) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT																					  				\r\n" ).append(
		"     CU.ID_UTILIZZO_DICHIARATO,															  						\r\n" ).append(
		"     CU.FLAG_ARBOREO,															  									\r\n" ).append(
		"     SP.ISTAT_COMUNE,															  									\r\n" ).append( 
		"     SP.DESC_COMUNE || ' (' || SP.SIGLA_PROVINCIA || ')' as COMUNE,												\r\n" ).append( 
		"     SP.SEZIONE,																									\r\n" ).append( 
		"     SP.SEZIONE || '-' || SP.DESC_SEZIONE AS DESC_SEZIONE,															\r\n" ).append( 
		"     SP.FOGLIO,																									\r\n" ).append( 
		"     SP.PARTICELLA,																								\r\n" ).append( 
		"     SP.SUBALTERNO,																								\r\n" ).append( 
		"     SP.SUP_CATASTALE,																								\r\n" ).append( 
		"     '[' || CU.COD_TIPO_UTILIZZO || '] ' || CU.DESC_TIPO_UTILIZZO AS OCCUPAZIONE_SUOLO,							\r\n" ).append( 
		"     '[' || CU.CODICE_DESTINAZIONE || '] ' || CU.DESCRIZIONE_DESTINAZIONE AS DESTINAZIONE,							\r\n" ).append( 
		"     '[' || CU.COD_DETTAGLIO_USO || '] ' || CU.DESC_TIPO_DETTAGLIO_USO AS USO,										\r\n" ).append( 
		"     '[' || CU.CODICE_QUALITA_USO || '] ' || CU.DESCRIZIONE_QUALITA_USO AS QUALITA,								\r\n" ).append( 
		"     '[' || CU.COD_TIPO_VARIETA || '] ' || CU.DESC_TIPO_VARIETA AS VARIETA,            							\r\n" ).append( 
		"     CU.SUPERFICIE_UTILIZZATA																						\r\n" ).append( 
		" FROM																								  				\r\n" ).append( 
		"     NEMBO_T_PROCEDIMENTO_OGGETTO PO,																  				\r\n" ).append( 
		"     SMRGAA_V_STORICO_PARTICELLA SP,																  				\r\n" ).append( 
		"     SMRGAA_V_CONDUZIONE_UTILIZZO CU																				\r\n" ).append( 
		" WHERE																								  				\r\n" ).append( 
		"     	  PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA						  				\r\n" ).append( 
		"     AND CU.ID_STORICO_PARTICELLA = SP.ID_STORICO_PARTICELLA								  						\r\n" ).append( 
		"     AND PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO														\r\n" );
		if(filtroRicercaConduzioni != null)
		{
			sb.append("		AND SP.FOGLIO = :FOGLIO \r\n" );
			sb.append("		AND SP.ISTAT_COMUNE = :ISTAT_COMUNE \r\n" );
			if(filtroRicercaConduzioni.getSezione() != null && !filtroRicercaConduzioni.getSezione().equals(""))
			{
				sb.append("		AND SP.SEZIONE = :SEZIONE \r\n" );
			}
			if(filtroRicercaConduzioni.getParticella() != null)
			{
				sb.append("		AND SP.PARTICELLA = :PARTICELLA \r\n");
			}
			if(filtroRicercaConduzioni.getSubalterno() != null && !filtroRicercaConduzioni.getSubalterno().equals(""))
			{
				sb.append("		AND SP.SUBALTERNO = :SUBALTERNO \r\n");
			}
			if(filtroRicercaConduzioni.getChiaviConduzioniInserite() != null 
					&& filtroRicercaConduzioni.getChiaviConduzioniInserite().length>0
					&& escludiDaRicercaParticelleFiltro)
			{
				
				sb.append(getNotInCondition("CU.ID_UTILIZZO_DICHIARATO", NemboUtils.ARRAY.toListOfLong(filtroRicercaConduzioni.getChiaviConduzioniInserite())));
			}
		}
		if(piantagioniArboree == true)
		{
			sb.append("		AND CU.FLAG_ARBOREO = 'S' \r\n");
		}

		if(arrayIdUtilizzoDichiarato != null)
		{
			sb.append(getInCondition("CU.ID_UTILIZZO_DICHIARATO", arrayIdUtilizzoDichiarato));
		}
		sb.append("  ORDER BY SP.SIGLA_PROVINCIA, SP.DESC_COMUNE");
		
		final String QUERY = sb.toString();
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			if(filtroRicercaConduzioni != null)
			{
				mapParameterSource.addValue("ISTAT_COMUNE", filtroRicercaConduzioni.getIstatComune(), Types.VARCHAR);
				mapParameterSource.addValue("SEZIONE", filtroRicercaConduzioni.getSezione(), Types.VARCHAR);
				mapParameterSource.addValue("FOGLIO", filtroRicercaConduzioni.getFoglio(), Types.NUMERIC);
				if(filtroRicercaConduzioni.getParticella() != null)
				{
					mapParameterSource.addValue("PARTICELLA", filtroRicercaConduzioni.getParticella(), Types.NUMERIC);
				}
				if(filtroRicercaConduzioni.getSubalterno() != null && !filtroRicercaConduzioni.getSubalterno().equals(""))
				{
					mapParameterSource.addValue("SUBALTERNO", filtroRicercaConduzioni.getSubalterno(), Types.VARCHAR);
				}
			}
			return queryForList(QUERY, mapParameterSource,ParticelleDanniDTO.class);
		} 
		catch (Throwable t)
		{
			LogParameter[] logParameters; 
			List<LogParameter> listLogParamaters = new ArrayList<LogParameter>();
			listLogParamaters.add(new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto));
			if(filtroRicercaConduzioni != null)
			{
				listLogParamaters.add(new LogParameter("ISTAT_COMUNE", filtroRicercaConduzioni.getIstatComune()));
				listLogParamaters.add(new LogParameter("SEZIONE", filtroRicercaConduzioni.getSezione()));
				listLogParamaters.add(new LogParameter("FOGLIO", filtroRicercaConduzioni.getFoglio()));
				listLogParamaters.add(new LogParameter("PARTICELLA", filtroRicercaConduzioni.getParticella()));
				listLogParamaters.add(new LogParameter("SUBALTERNO", filtroRicercaConduzioni.getSubalterno()));
				listLogParamaters.add(new LogParameter("ID_UTILIZZO_DICHIARATO", filtroRicercaConduzioni.getChiaviConduzioniInserite()));
			}
			listLogParamaters.add(new LogParameter("escludiDaRicercaParticelleFiltro",escludiDaRicercaParticelleFiltro));
			listLogParamaters.add(new LogParameter("arrayIdUtilizzoDichiarato",arrayIdUtilizzoDichiarato));
			logParameters = new LogParameter[listLogParamaters.size()];
			logParameters = listLogParamaters.toArray(logParameters);
			
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				logParameters,
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
	
	public List<ParticelleDanniDTO> getListConduzioniDanno(
			long idProcedimentoOggetto, 
			long idDannoAtm) throws InternalUnexpectedException
	{
		return getListConduzioniDanno(idProcedimentoOggetto, new long[]{idDannoAtm});
	}
	
	public List<ParticelleDanniDTO> getListConduzioniDanno(
			long idProcedimentoOggetto, 
			long[] arrayIdDannoAtm) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		String inConditionIdDannoAtm=" ";
		if(arrayIdDannoAtm != null)
		{
			inConditionIdDannoAtm = getInCondition("DA.ID_DANNO_ATM", arrayIdDannoAtm);
		}
		
		StringBuilder sb = new StringBuilder();
			
					sb.append(
					" SELECT																					  				\r\n" ).append(
					"     CU.ID_UTILIZZO_DICHIARATO,															  						\r\n" ).append(
					"     CU.FLAG_ARBOREO,															  									\r\n" ).append(
					"     SP.ISTAT_COMUNE,															  									\r\n" ).append( 
					"     SP.DESC_PROVINCIA,															  									\r\n" ).append( 
					"     SP.DESC_COMUNE,															  									\r\n" ).append( 
					"     SP.DESC_COMUNE || ' (' || SP.SIGLA_PROVINCIA || ')' as COMUNE,												\r\n" ).append( 
					"     SP.SEZIONE,																									\r\n" ).append( 
					"     SP.SEZIONE || '-' || SP.DESC_SEZIONE AS DESC_SEZIONE,															\r\n" ).append( 
					"     SP.FOGLIO,																									\r\n" ).append( 
					"     SP.PARTICELLA,																								\r\n" ).append( 
					"     SP.SUBALTERNO,																								\r\n" ).append( 
					"     SP.SUP_CATASTALE,																								\r\n" ).append( 
					"     CU.DESC_TIPO_UTILIZZO,																								\r\n" ).append( 
					"     '[' || CU.COD_TIPO_UTILIZZO || '] ' || CU.DESC_TIPO_UTILIZZO AS OCCUPAZIONE_SUOLO,							\r\n" ).append( 
					"     '[' || CU.CODICE_DESTINAZIONE || '] ' || CU.DESCRIZIONE_DESTINAZIONE AS DESTINAZIONE,							\r\n" ).append( 
					"     '[' || CU.COD_DETTAGLIO_USO || '] ' || CU.DESC_TIPO_DETTAGLIO_USO AS USO,										\r\n" ).append( 
					"     '[' || CU.CODICE_QUALITA_USO || '] ' || CU.DESCRIZIONE_QUALITA_USO AS QUALITA,								\r\n" ).append( 
					"     '[' || CU.COD_TIPO_VARIETA || '] ' || CU.DESC_TIPO_VARIETA AS VARIETA,            							\r\n" ).append( 
					"     CU.SUPERFICIE_UTILIZZATA,																						\r\n" ).append( 
					"     DA.ID_DANNO_ATM,																								\r\n" ).append( 
					"     DA.PROGRESSIVO																								\r\n" ).append( 
					" FROM																								  				\r\n" ).append( 
					"     NEMBO_T_PROCEDIMENTO_OGGETTO PO,																  				\r\n" ).append( 
					"     SMRGAA_V_STORICO_PARTICELLA SP,																  				\r\n" ).append( 
					"     SMRGAA_V_CONDUZIONE_UTILIZZO CU,																				\r\n" ).append( 
					"     NEMBO_R_PARTICELLA_DANNEGGIATA PD,																				\r\n" ).append( 
					"     NEMBO_T_DANNO_ATM DA																				\r\n" ).append( 
					" WHERE																								  				\r\n" ).append( 
					"     	  PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA						  				\r\n" ).append( 
					"     AND CU.ID_STORICO_PARTICELLA = SP.ID_STORICO_PARTICELLA								  						\r\n" ).append( 
					"     AND PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO														\r\n" ).append(
					"     AND DA.ID_DANNO_ATM = PD.ID_DANNO_ATM																			\r\n" ).append(
					"	  AND PD.EXT_ID_UTILIZZO_DICHIARATO = CU.ID_UTILIZZO_DICHIARATO 												\r\n" ).append(
							inConditionIdDannoAtm ).append(
					" ORDER BY DA.PROGRESSIVO"				
					);
					;
	    final String QUERY = sb.toString();
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return queryForList(QUERY, mapParameterSource,ParticelleDanniDTO.class);
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_DANNO_ATM", arrayIdDannoAtm)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public BigDecimal getSumSuperficiCatastaliParticelle(long idProcedimentoOggetto, long[] arrayIdUtilizzoDichiarato) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		
		final String QUERY = 
				" SELECT 																					\r\n" + 
				"        SUM(CU.SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA						     	\r\n" + 
				" FROM																				 		\r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,												 	\r\n" + 
				"        SMRGAA_V_STORICO_PARTICELLA SP,												 	\r\n" + 
				"        SMRGAA_V_CONDUZIONE_UTILIZZO CU												 	\r\n" + 
				" WHERE																				 		\r\n" + 
				"            PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA		 	\r\n" + 
				"        AND CU.ID_STORICO_PARTICELLA = SP.ID_STORICO_PARTICELLA 						 	\r\n" + 
				"        AND PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO							\r\n" + 
				getInCondition("CU.ID_UTILIZZO_DICHIARATO", arrayIdUtilizzoDichiarato)
				;

		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			return new BigDecimal(queryForString(QUERY, mapParameterSource));
		} 
		catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t, 
				new LogParameter[] 
				{
					new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
					new LogParameter("ID_UTILIZZO_DICHIARATO", arrayIdUtilizzoDichiarato)
				},
				new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}

	public List<AllevamentiDTO> getDettaglioAllevamento(long idProcedimentoOggetto, long idCategoriaAnimale, String istatComune)
		throws InternalUnexpectedException
	{
		return getListRiepilogoAllevamenti(idProcedimentoOggetto, idCategoriaAnimale, istatComune, true);
	}
	
	private String getWithTmpAllevamenti(boolean isDettaglio)
	{
		String queryTmpAllevamenti = 
				"TMP_ALLEVAMENTI AS (																							\r\n" +
				"    SELECT 																										\r\n" + 
				"        A.CODICE_AZIENDA_ZOOTECNICA,																				\r\n" + 
				"        A.ID_CATEGORIA_ANIMALE,																					\r\n" + 
				"        A.ISTAT_COMUNE,																							\r\n" + 
				"        A.DESCRIZIONE_SPECIE_ANIMALE,																				\r\n" + 
				"        A.DESCRIZIONE_CATEGORIA_ANIMALE,																			\r\n" + 
				"        A.UNITA_MISURA_SPECIE,\r\n" + 
				"        SUM(A.QUANTITA) AS QUANTITA\r\n" + 
				"    FROM \r\n" + 
				"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,\r\n" + 
				"        SMRGAA_V_ALLEVAMENTI A\r\n" + 
				"    WHERE\r\n" + 
				"        PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO\r\n" + 
				"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA\r\n";
				if(isDettaglio)
				{
					queryTmpAllevamenti = queryTmpAllevamenti +
							
							"        AND A.ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE 															\r\n" + 
							"        AND A.ISTAT_COMUNE = :ISTAT_COMUNE																			\r\n"
							;
				}
				queryTmpAllevamenti = queryTmpAllevamenti +
				"    GROUP BY																										\r\n" + 
				"        A.CODICE_AZIENDA_ZOOTECNICA,																				\r\n" + 
				"        A.ID_CATEGORIA_ANIMALE,																					\r\n" + 
				"        A.ISTAT_COMUNE,																							\r\n" + 
				"        A.DESCRIZIONE_SPECIE_ANIMALE,																				\r\n" + 
				"        A.DESCRIZIONE_CATEGORIA_ANIMALE,																																												\r\n" + 
				"        A.UNITA_MISURA_SPECIE\r\n" + 
				") 	";
				return queryTmpAllevamenti;
	}
	
	//Questa query fu sviluppata per gestire casi di assenza di record in NEMBO_T_PRODUZIONE_ZOOTECNICA. Funzionando a dovere non l'ho più modificata.
	public List<AllevamentiDTO> getListRiepilogoAllevamenti(long idProcedimentoOggetto, Long idCategoriaAnimale, String istatComune, boolean isDettaglio) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
	    String QUERY = 
	    		"WITH " +
	    		WITH_PRODUZIONE_ZOOTECNICA + 
	    		", "  +
	    		WITH_TMP_ALLEVAMENTI + 
	    		" , " + 
	    		WITH_TMP_ALLEVAMENTO_CAT_PROD + 
	    		" , " + 
	    		WITH_TMP_ALL_PROD_ZOO_PROD_VEND + 
	    		
	    		", TMP_FINALE AS ( \r\n" +
		    		"SELECT \r\n" + 
		    		"	 TACP.CODICE_AZIENDA_ZOOTECNICA, \r\n" +
		    		"    TACP.SIGLA_PROVINCIA,\r\n" + 
		    		"    TACP.DESCRIZIONE_COMUNE,\r\n" + 
		    		"    TACP.UBICAZIONE_ALLEVAMENTO,\r\n" + 
		    		"    TACP.ID_CATEGORIA_ANIMALE,																						\r\n" + 
		    		"    TACP.ISTAT_COMUNE,																								\r\n" + 
		    		"    TACP.DESCRIZIONE_SPECIE_ANIMALE,																					\r\n" + 
		    		"    TACP.DESCRIZIONE_CATEGORIA_ANIMALE,																				\r\n" + 
		    		"    TACP.QUANTITA,																									\r\n" + 
		    		"    TACP.UNITA_MISURA_SPECIE,																							\r\n" + 
		    		"    TACP.COEFFICIENTE_UBA,																							\r\n" + 
		    		"    TACP.UNITA_FORAGGERE,\r\n" + 
		    		"    NVL(TAPZPV.GIORNATE_LAVORATIVE_MEDIE,TACP.GIORNATE_LAVORATIVE_MEDIE) AS GIORNATE_LAVORATIVE_MEDIE,\r\n" + 
		    		"    (NVL(TAPZPV.GIORNATE_LAVORATIVE_MEDIE,TACP.GIORNATE_LAVORATIVE_MEDIE) * TACP.QUANTITA) AS GIORNATE_LAVORATIVE,\r\n" + 
		    		"    TAPZPV.PZ_ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"    TAPZPV.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"    TAPZPV.PV_ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"    TAPZPV.DATA_ULTIMO_AGGIORNAMENTO,\r\n" + 
		    		"    TAPZPV.EXT_ID_UTENTE_AGGIORNAMENTO,\r\n" + 
		    		
		    		"    NVL(TAPZPV.PESO_VIVO_MEDIO,TACP.PESO_VIVO_MEDIO) AS PESO_VIVO_MEDIO,\r\n" + 
		    		"    TAPZPV.GIORNATE_LAVORATIVE_TOTALI, \r\n" +
		    		
		    		"    DECODE(TAPZPV.PLV, NULL, 0, TAPZPV.PLV) AS PLV\r\n" + 
		    		"FROM     \r\n" + 
		    		"    TMP_ALLEVAMENTO_CAT_PROD TACP,\r\n" + 
		    		"    TMP_ALL_PROD_ZOO_PROD_VEND TAPZPV\r\n" + 
		    		"WHERE\r\n" + 
		    		"        TACP.ID_CATEGORIA_ANIMALE = TAPZPV.ID_CATEGORIA_ANIMALE\r\n" + 
		    		"    AND TACP.ISTAT_COMUNE = TAPZPV.ISTAT_COMUNE\r\n" + 
		    		"GROUP BY\r\n" + 
		    		"	 TACP.CODICE_AZIENDA_ZOOTECNICA, \r\n" +
		    		"    TACP.SIGLA_PROVINCIA,\r\n" + 
		    		"    TACP.DESCRIZIONE_COMUNE,\r\n" + 
		    		"    TACP.UBICAZIONE_ALLEVAMENTO,\r\n" + 
		    		"    TACP.ID_CATEGORIA_ANIMALE,																						\r\n" + 
		    		"    TACP.ISTAT_COMUNE,																								\r\n" + 
		    		"    TACP.DESCRIZIONE_SPECIE_ANIMALE,																					\r\n" + 
		    		"    TACP.DESCRIZIONE_CATEGORIA_ANIMALE,																				\r\n" + 
		    		"    TACP.QUANTITA,																									\r\n" + 
		    		"    TACP.UNITA_MISURA_SPECIE,																							\r\n" + 
		    		"    TACP.COEFFICIENTE_UBA,																							\r\n" + 
		    		"    TACP.UNITA_FORAGGERE,\r\n" + 
		    		"    NVL(TAPZPV.GIORNATE_LAVORATIVE_MEDIE,TACP.GIORNATE_LAVORATIVE_MEDIE),\r\n" + 
		    		"    (NVL(TAPZPV.GIORNATE_LAVORATIVE_MEDIE,TACP.GIORNATE_LAVORATIVE_MEDIE) * TACP.QUANTITA),\r\n" + 
		    		"    TAPZPV.PZ_ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"    TAPZPV.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
		    		"    TAPZPV.PV_ID_PRODUZIONE_ZOOTECNICA,\r\n" + 		    		
		    		"    TAPZPV.DATA_ULTIMO_AGGIORNAMENTO,\r\n" + 
		    		"    TAPZPV.EXT_ID_UTENTE_AGGIORNAMENTO,\r\n" + 
		    		
					"    NVL(TAPZPV.PESO_VIVO_MEDIO,TACP.PESO_VIVO_MEDIO),\r\n" + 
		    		"    TAPZPV.GIORNATE_LAVORATIVE_TOTALI, \r\n" +
		    		
		    		"    DECODE(TAPZPV.PLV, NULL, 0, TAPZPV.PLV)\r\n" + 
		    		"ORDER BY\r\n" + 
		    		"    TACP.SIGLA_PROVINCIA,\r\n" + 
		    		"    TACP.DESCRIZIONE_COMUNE,\r\n" + 
		    		"    TACP.DESCRIZIONE_SPECIE_ANIMALE,																					\r\n" + 
		    		"    TACP.DESCRIZIONE_CATEGORIA_ANIMALE \r\n" +
		    		") \r\n" +
		    		"SELECT \r\n" + 
		    		"    TF.*,\r\n" + 
		    		"    (SELECT NOTE\r\n" + 
		    		"     FROM NEMBO_T_PRODUZIONE_ZOOTECNICA PZ\r\n" + 
		    		"     WHERE \r\n" + 
		    		"            PZ.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO\r\n" + 
		    		"        AND PZ.EXT_ID_CATEGORIA_ANIMALE = TF.ID_CATEGORIA_ANIMALE\r\n" + 
		    		"        AND PZ.EXT_ISTAT_COMUNE = TF.ISTAT_COMUNE \r\n" + 
		    		"    ) AS NOTE\r\n" + 
		    		"FROM \r\n" + 
		    		"    TMP_FINALE TF "
	    		;
	    		if(isDettaglio)
	    		{
	    			QUERY = QUERY +
	    					"WHERE \r\n" +
	    					"	 		TF.ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE 	\r\n" +
	    					"		AND TF.ISTAT_COMUNE = :ISTAT_COMUNE 					\r\n"
	    					;
	    		}
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      if(isDettaglio)
	      {
	    	  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
	      }
	      if(isDettaglio)
	      {
	    	  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
	    	  mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
	      }
	      return queryForList(QUERY, mapParameterSource, AllevamentiDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale),
	        		  new LogParameter("ISTAT_COMUNE",istatComune)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	



	
	public List<AllevamentiDettaglioPlvDTO> getListDettaglioAllevamenti 
			(long idProcedimentoOggetto, long idCategoriaAnimale, String istatComune) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
	    final String QUERY =
	    		"WITH " + 
	    		WITH_PRODUZIONE_ZOOTECNICA + 
	    		", "  +
	    		WITH_TMP_ALLEVAMENTI + 
	    		", " + 
	    		" TMP_PROD_VEND_DETT AS (\r\n" + 
	    		"    SELECT 																											\r\n" + 
	    		"        P.ID_PRODUZIONE,\r\n" + 
	    		"        P.DESCRIZIONE AS DESC_PRODUZIONE,\r\n" + 
	    		"        PV.NUMERO_CAPI,\r\n" + 
	    		"        PV.QUANTITA_PRODOTTA,\r\n" + 
	    		"        UM.ID_UNITA_MISURA,\r\n" + 
	    		"        UM.DESCRIZIONE AS DESC_UNITA_MISURA,\r\n" + 
	    		"        PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI AS PROD_LORDA,\r\n" + 
	    		"        PV.QUANTITA_REIMPIEGATA,\r\n" + 
	    		"        PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI - PV.QUANTITA_REIMPIEGATA AS PROD_NETTA,\r\n" + 
	    		"        PV.PREZZO,\r\n" + 
	    		"        (PV.NUMERO_CAPI * PV.QUANTITA_PRODOTTA - PV.QUANTITA_REIMPIEGATA) * PV.PREZZO AS IMPORTO_TOTALE,\r\n" + 
	    		"        A.ISTAT_COMUNE,\r\n" + 
	    		"		 A.CODICE_AZIENDA_ZOOTECNICA,\r\n" +
	    		"        PZ.ID_PRODUZIONE_ZOOTECNICA,\r\n" + 
	    		"        PZ.GIORNATE_LAVORATIVE_MEDIE,\r\n" + 
	    		"        PZ.ID_PRODUZIONE_ZOOTECNICA AS PZ_ID_PRODUZIONE_ZOOTECNICA,																					\r\n" + 
	    		"        PV.ID_PRODUZIONE_ZOOTECNICA AS PV_ID_PRODUZIONE_ZOOTECNICA												\r\n" + 
	    		"    FROM																												\r\n" + 
	    		"        TMP_ALLEVAMENTI A,		\r\n" + 
	    		"        PRODUZIONE_ZOOTECNICA PZ,\r\n" + 
	    		"        NEMBO_T_PRODUZIONE_VENDIBILE PV,																				\r\n" + 
	    		"        NEMBO_D_PRODUZIONE P,\r\n" + 
	    		"        NEMBO_D_UNITA_MISURA UM\r\n" + 
	    		"    WHERE 																												\r\n" + 
	    		"             A.ID_CATEGORIA_ANIMALE = PZ.EXT_ID_CATEGORIA_ANIMALE	  											\r\n" + 
	    		"        AND PZ.ID_PRODUZIONE_ZOOTECNICA = PV.ID_PRODUZIONE_ZOOTECNICA 												\r\n" + 
	    		"        AND P.ID_PRODUZIONE = PV.ID_PRODUZIONE                    \r\n" + 
	    		"        AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA\r\n" + 
	    		"        AND A.ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE\r\n" + 
	    		"        AND A.ISTAT_COMUNE = :ISTAT_COMUNE\r\n" + 
	    		")\r\n" + 
	    		"SELECT * \r\n" + 
	    		"FROM   TMP_PROD_VEND_DETT\r\n" + 
	    		"ORDER BY DESC_PRODUZIONE"
	    		;
	    
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
			  mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
		      return queryForList(QUERY, mapParameterSource, AllevamentiDettaglioPlvDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale),
	        		  new LogParameter("ISTAT_COMUNE",istatComune)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}	
	
	
	public List<DecodificaDTO<Integer>> getListProduzioniVendibili(long idCategoriaAnimale) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
		String QUERY = 
				"    SELECT 																						\r\n" + 
				"        P.ID_PRODUZIONE AS ID,																		\r\n" + 
				"        P.ID_PRODUZIONE AS CODICE,																	\r\n" + 
				"        P.DESCRIZIONE AS DESCRIZIONE																\r\n" + 
				"    FROM NEMBO_D_PRODUZIONE P																		\r\n" + 
				"    WHERE P.EXT_ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE										\r\n" +
				"    ORDER BY DESCRIZIONE "
				;
	    
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
		      return queryForDecodificaInteger(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public List<DecodificaDTO<Integer>> getListUnitaMisuraProduzioniVendibili(long idCategoriaAnimale) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
		String QUERY = 
				"    SELECT 																							\r\n" + 
				"        	P.ID_PRODUZIONE AS ID,																		\r\n" + 
				"       	P.ID_PRODUZIONE AS CODICE,																	\r\n" + 
				"        	UM.DESCRIZIONE AS DESCRIZIONE																\r\n" + 
				"    FROM 	NEMBO_D_PRODUZIONE P,																		\r\n" +
				"			NEMBO_D_UNITA_MISURA UM 																	\r\n" +
				"    WHERE 		P.EXT_ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE										\r\n" +
				"    		AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA													\r\n"
				;
	    
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
		      return queryForDecodificaInteger(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniCategorieAnimali(
			long idProcedimentoOggetto,
			long idCategoriaAnimale,
			String istatComune
			) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
		final String QUERY =
				"WITH PRODUZIONE_VENDIBILE AS (																	\r\n" + 
				"	SELECT																						\r\n" + 
				"		PV.ID_PRODUZIONE																		\r\n" + 
				"	FROM 																						\r\n" + 
				"		NEMBO_T_PROCEDIMENTO_OGGETTO PO,														\r\n" + 
				"		SMRGAA_V_ALLEVAMENTI A,																	\r\n" + 
				"		NEMBO_T_PRODUZIONE_ZOOTECNICA PZ,														\r\n" + 
				"		NEMBO_T_PRODUZIONE_VENDIBILE PV															\r\n" + 
				"	WHERE																						\r\n" + 
				"		PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO									\r\n" + 
				"		AND A.ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE										\r\n" + 
				"		AND A.ISTAT_COMUNE = :ISTAT_COMUNE														\r\n" + 
				"		AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA					\r\n" + 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO  = PZ.ID_PROCEDIMENTO_OGGETTO							\r\n" + 
				"		AND A.ID_CATEGORIA_ANIMALE = PZ.EXT_ID_CATEGORIA_ANIMALE								\r\n" + 
				"		AND PZ.ID_PRODUZIONE_ZOOTECNICA = PV.ID_PRODUZIONE_ZOOTECNICA							\r\n" + 
				"	GROUP BY PV.ID_PRODUZIONE																	\r\n" + 
				"), PRODUZIONE_ZOOTECNICA AS (																	\r\n" + 
				"    SELECT  *																					\r\n" + 
				"    FROM																						\r\n" + 
				"        NEMBO_T_PRODUZIONE_ZOOTECNICA PZ														\r\n" + 
				"    WHERE 																						\r\n" + 
				"        PZ.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO									\r\n" + 
				")																								\r\n" + 
				"SELECT 																						\r\n" + 
				"	 P.*,																						\r\n" + 
				"    CA.*,																						\r\n" + 
				"    UM.*																						\r\n" + 
				"FROM 																							\r\n" + 
				"    NEMBO_D_PRODUZIONE P,																		\r\n" + 
				"    NEMBO_D_CATEGORIA_ANIMALE CA,																\r\n" + 
				"    NEMBO_D_UNITA_MISURA UM																	\r\n" + 
				"WHERE 																							\r\n" + 
				"    P.EXT_ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE											\r\n" + 
				"    AND P.EXT_ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE								\r\n" + 
				"    AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA													\r\n" + 
				"	AND P.ID_PRODUZIONE NOT IN																	\r\n" + 
				"        (																						\r\n" + 
				"			SELECT PV.ID_PRODUZIONE																\r\n" + 
				"			FROM 																				\r\n" + 
				"                PRODUZIONE_VENDIBILE PV														\r\n" + 
				"		)																						\r\n" + 
				"ORDER BY P.DESCRIZIONE "
				;
	    
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
			  mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
		      return queryForList(QUERY, mapParameterSource, ProduzioneCategoriaAnimaleDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale),
	        		  new LogParameter("ISTAT_COMUNE",istatComune)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioniVendibiliGiaInserite(
			long idProcedimentoOggetto,
			long idCategoriaAnimale,
			String istatComune) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT																						\r\n").append(
				"        PV.ID_PRODUZIONE,																	\r\n").append( 
				"        PV.ID_PRODUZIONE_VENDIBILE,														\r\n").append( 
				"        PV.ID_PRODUZIONE_ZOOTECNICA,														\r\n").append( 
				"        PV.NUMERO_CAPI,																	\r\n").append( 
				"        PV.PREZZO,																			\r\n").append( 
				"        PV.QUANTITA_PRODOTTA,																\r\n").append( 
				"        PV.QUANTITA_REIMPIEGATA,															\r\n").append( 
				"        P.PREZZO_MIN,																		\r\n").append( 
				"        P.PREZZO_MEDIO,																	\r\n").append( 
				"        P.PREZZO_MAX,																		\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MIN,															\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MAX,															\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MEDIA,															\r\n").append( 
				"        P.DESCRIZIONE AS DESC_PRODUZIONE,													\r\n").append( 
				"        UM.ID_UNITA_MISURA,																\r\n").append( 
				"        UM.DESCRIZIONE AS DESC_UNITA_MISURA,												\r\n").append( 
				"        CA.PESO_VIVO_MIN,																	\r\n").append( 
				"        CA.PESO_VIVO_MEDIO,																\r\n").append( 
				"        CA.PESO_VIVO_MAX,																	\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MIN,														\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MEDIE,														\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MAX,														\r\n").append( 
				"        CA.CONSUMO_ANNUO_UF,																\r\n").append( 
				"        CA.NUMERO_MAX_ANIMALI_PER_HA,														\r\n").append( 
				"        CA.AGEA_COD																		\r\n").append( 
				"	FROM 																					\r\n").append( 
				"		NEMBO_T_PROCEDIMENTO_OGGETTO PO,													\r\n").append( 
				"		SMRGAA_V_ALLEVAMENTI A,																\r\n").append( 
				"		NEMBO_T_PRODUZIONE_ZOOTECNICA PZ,													\r\n").append( 
				"		NEMBO_T_PRODUZIONE_VENDIBILE PV,													\r\n").append( 
				"       NEMBO_D_PRODUZIONE P,																\r\n").append( 
				"       NEMBO_D_UNITA_MISURA UM,															\r\n").append( 
				"       NEMBO_D_CATEGORIA_ANIMALE CA														\r\n").append( 
				"	WHERE																					\r\n").append( 
				"		PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO								\r\n").append( 
				"		AND A.ID_CATEGORIA_ANIMALE = :ID_CATEGORIA_ANIMALE									\r\n").append( 
				"		AND A.ISTAT_COMUNE = :ISTAT_COMUNE													\r\n").append( 
				"		AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA				\r\n").append( 
				"		AND PO.ID_PROCEDIMENTO_OGGETTO  = PZ.ID_PROCEDIMENTO_OGGETTO						\r\n").append( 
				"		AND A.ID_CATEGORIA_ANIMALE = PZ.EXT_ID_CATEGORIA_ANIMALE							\r\n").append( 
				"		AND PZ.ID_PRODUZIONE_ZOOTECNICA = PV.ID_PRODUZIONE_ZOOTECNICA						\r\n").append( 
				"       AND PV.ID_PRODUZIONE = P.ID_PRODUZIONE												\r\n").append( 
				"       AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA											\r\n").append( 
				"       AND P.EXT_ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE						\r\n").append( 
				"GROUP BY																					\r\n").append( 
				"        PV.ID_PRODUZIONE,																	\r\n").append( 
				"        PV.ID_PRODUZIONE_VENDIBILE,														\r\n").append( 
				"        PV.ID_PRODUZIONE_ZOOTECNICA,														\r\n").append( 
				"        PV.NUMERO_CAPI,																	\r\n").append( 
				"        PV.PREZZO,																			\r\n").append( 
				"        PV.QUANTITA_PRODOTTA,																\r\n").append( 
				"        PV.QUANTITA_REIMPIEGATA,															\r\n").append( 
				"        P.PREZZO_MIN,																		\r\n").append( 
				"        P.DESCRIZIONE,																		\r\n").append( 
				"        P.PREZZO_MEDIO,																	\r\n").append( 
				"        P.PREZZO_MAX,																		\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MIN,															\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MAX,															\r\n").append( 
				"        P.QUANTITA_PRODOTTA_MEDIA,															\r\n").append( 
				"        UM.ID_UNITA_MISURA,																\r\n").append( 
				"        UM.DESCRIZIONE,																	\r\n").append( 
				"        CA.PESO_VIVO_MIN,																	\r\n").append( 
				"        CA.PESO_VIVO_MEDIO,																\r\n").append( 
				"        CA.PESO_VIVO_MAX,																	\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MIN,														\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MEDIE,														\r\n").append( 
				"        CA.GIORNATE_LAVORATIVE_MAX,														\r\n").append( 
				"        CA.CONSUMO_ANNUO_UF,																\r\n").append( 
				"        CA.NUMERO_MAX_ANIMALI_PER_HA,														\r\n").append( 
				"        CA.AGEA_COD");
				;
	    final String QUERY = sb.toString();
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
			  mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
		      return queryForList(QUERY, mapParameterSource, ProduzioneCategoriaAnimaleDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale),
	        		  new LogParameter("ISTAT_COMUNE",istatComune)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public List<ProduzioneCategoriaAnimaleDTO> getListProduzioni(
			long idProcedimentoOggetto,
			long idCategoriaAnimale,
			String istatComune
			) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    
		String QUERY =
				"WITH " +
				getWithTmpAllevamenti(true)+ " \r\n" +
				"SELECT																				\r\n" + 
				"        P.ID_PRODUZIONE,															\r\n" + 
				"        P.PREZZO_MEDIO,																\r\n" + 
				"        P.PREZZO_MAX,																\r\n" + 
				"        P.QUANTITA_PRODOTTA_MIN,													\r\n" + 
				"        P.QUANTITA_PRODOTTA_MAX,													\r\n" + 
				"        P.QUANTITA_PRODOTTA_MEDIA,													\r\n" + 
				"        P.DESCRIZIONE AS DESC_PRODUZIONE,											\r\n" + 
				"        UM.ID_UNITA_MISURA,															\r\n" + 
				"        UM.DESCRIZIONE AS DESC_UNITA_MISURA,										\r\n" + 
				"        CA.PESO_VIVO_MIN,															\r\n" + 
				"        CA.PESO_VIVO_MEDIO,															\r\n" + 
				"        CA.PESO_VIVO_MAX,															\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MIN,													\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MEDIE,												\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MAX,													\r\n" + 
				"        CA.CONSUMO_ANNUO_UF,														\r\n" + 
				"        CA.NUMERO_MAX_ANIMALI_PER_HA,												\r\n" + 
				"        CA.AGEA_COD,																\r\n" + 
				"        A.QUANTITA																	\r\n" + 
				"	FROM 																													\r\n" + 
				"        TMP_ALLEVAMENTI A,														\r\n" + 
				"        NEMBO_D_PRODUZIONE P,														\r\n" + 
				"        NEMBO_D_UNITA_MISURA UM,														\r\n" + 
				"        NEMBO_D_CATEGORIA_ANIMALE CA													\r\n" + 
				"	WHERE																															\r\n" + 
				"        A.ISTAT_COMUNE = :ISTAT_COMUNE													\r\n" + 
				"		AND A.ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE						\r\n" + 
				"		AND CA.EXT_ID_CATEGORIA_ANIMALE = P.EXT_ID_CATEGORIA_ANIMALE					\r\n" + 
				"        AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA										\r\n" + 
				"GROUP BY																				\r\n" + 
				"        P.ID_PRODUZIONE,																\r\n" + 
				"        P.PREZZO_MIN,																	\r\n" + 
				"        P.DESCRIZIONE,																	\r\n" + 
				"        P.PREZZO_MEDIO,																\r\n" + 
				"        P.PREZZO_MAX,																	\r\n" + 
				"        P.QUANTITA_PRODOTTA_MIN,														\r\n" + 
				"        P.QUANTITA_PRODOTTA_MAX,														\r\n" + 
				"        P.QUANTITA_PRODOTTA_MEDIA,														\r\n" + 
				"        UM.ID_UNITA_MISURA,															\r\n" + 
				"        UM.DESCRIZIONE,																\r\n" + 
				"        CA.PESO_VIVO_MIN,																\r\n" + 
				"        CA.PESO_VIVO_MEDIO,															\r\n" + 
				"        CA.PESO_VIVO_MAX,																\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MIN,													\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MEDIE,													\r\n" + 
				"        CA.GIORNATE_LAVORATIVE_MAX,													\r\n" + 
				"        CA.CONSUMO_ANNUO_UF,															\r\n" + 
				"        CA.NUMERO_MAX_ANIMALI_PER_HA,													\r\n" + 
				"        CA.AGEA_COD, 																	\r\n" + 
				"        A.QUANTITA "
				;
	    
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			  mapParameterSource.addValue("ID_CATEGORIA_ANIMALE", idCategoriaAnimale, Types.NUMERIC);
			  mapParameterSource.addValue("ISTAT_COMUNE", istatComune, Types.VARCHAR);
		      return queryForList(QUERY, mapParameterSource, ProduzioneCategoriaAnimaleDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_CATEGORIA_ANIMALE",idCategoriaAnimale),
	        		  new LogParameter("ISTAT_COMUNE",istatComune)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public long inserisciProduzioneZootecnica(long idProcedimentoOggetto, AllevamentiDTO produzioneZootecnica, long idUtenteLogin)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = 
	    		"INSERT INTO NEMBO_T_PRODUZIONE_ZOOTECNICA						\r\n" + 
	    		"    (   ID_PRODUZIONE_ZOOTECNICA,								\r\n" + 
	    		"        ID_PROCEDIMENTO_OGGETTO,								\r\n" + 
	    		"        EXT_ID_CATEGORIA_ANIMALE,								\r\n" + 
	    		"        EXT_ISTAT_COMUNE,										\r\n" + 
	    		"        PESO_VIVO_MEDIO,										\r\n" + 
	    		"        GIORNATE_LAVORATIVE_MEDIE,								\r\n" + 
	    		"        NOTE,													\r\n" + 
	    		"        DATA_ULTIMO_AGGIORNAMENTO,								\r\n" + 
	    		"        EXT_ID_UTENTE_AGGIORNAMENTO							\r\n" + 
	    		"    )															\r\n" + 
	    		"VALUES															\r\n" + 
	    		"    (   														\r\n" + 
	    		"        :ID_PRODUZIONE_ZOOTECNICA,								\r\n" + 
	    		"        :ID_PROCEDIMENTO_OGGETTO,								\r\n" + 
	    		"        :EXT_ID_CATEGORIA_ANIMALE,								\r\n" + 
	    		"        :EXT_ISTAT_COMUNE,										\r\n" + 
	    		"        :PESO_VIVO_MEDIO,										\r\n" + 
	    		"        :GIORNATE_LAVORATIVE_MEDIE,							\r\n" + 
	    		"        :NOTE,													\r\n" + 
	    		"        SYSDATE,												\r\n" + 
	    		"        :EXT_ID_UTENTE_AGGIORNAMENTO							\r\n" + 
	    		"    )"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboProduzioneZootecnica = 0;
	    try
	    {
	      seqNemboProduzioneZootecnica = getNextSequenceValue("SEQ_NEMBO_T_PRODUZIONE_ZOOTECN");
	      mapParameterSource.addValue("ID_PRODUZIONE_ZOOTECNICA", seqNemboProduzioneZootecnica, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("EXT_ID_CATEGORIA_ANIMALE", produzioneZootecnica.getIdCategoriaAnimale(), Types.NUMERIC);
	      mapParameterSource.addValue("EXT_ISTAT_COMUNE", produzioneZootecnica.getIstatComune(), Types.VARCHAR);
	      mapParameterSource.addValue("PESO_VIVO_MEDIO", produzioneZootecnica.getPesoVivoMedio(), Types.NUMERIC);
	      mapParameterSource.addValue("GIORNATE_LAVORATIVE_MEDIE", produzioneZootecnica.getGiornateLavorativeMedie(), Types.NUMERIC);
	      mapParameterSource.addValue("NOTE", produzioneZootecnica.getNote(), Types.CLOB);
	      mapParameterSource.addValue("EXT_ID_UTENTE_AGGIORNAMENTO", idUtenteLogin, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PRODUZIONE_ZOOTECNICA", seqNemboProduzioneZootecnica),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("EXT_ID_CATEGORIA_ANIMALE", produzioneZootecnica.getIdCategoriaAnimale()),
	              new LogParameter("PESO_VIVO_MEDIO", produzioneZootecnica.getPesoVivoMedio()),
	              new LogParameter("GIORNATE_LAVORATIVE_MEDIE", produzioneZootecnica.getGiornateLavorativeMedie()),
	              new LogParameter("NOTE", produzioneZootecnica.getNote()),
	              new LogParameter("EXT_ID_UTENTE_AGGIORNAMENTO", idUtenteLogin)
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return seqNemboProduzioneZootecnica;
	}

	public void modificaProduzioneZootecnica(
			long idProcedimentoOggetto, 
			AllevamentiDTO produzioneZootecnica,
			long idUtenteLogin
			)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"UPDATE NEMBO_T_PRODUZIONE_ZOOTECNICA													\r\n" + 
	    		"SET 																					\r\n" + 
	    		"    PESO_VIVO_MEDIO = :PESO_VIVO_MEDIO,												\r\n" + 
	    		"    GIORNATE_LAVORATIVE_MEDIE = :GIORNATE_LAVORATIVE_MEDIE,							\r\n" + 
	    		"    DATA_ULTIMO_AGGIORNAMENTO = SYSDATE,												\r\n" + 
	    		"    EXT_ID_UTENTE_AGGIORNAMENTO = :EXT_ID_UTENTE_AGGIORNAMENTO,												\r\n" + 
	    		"    NOTE = :NOTE																		\r\n" + 
	    		"WHERE 																					\r\n" + 
	    		"    	 ID_PRODUZIONE_ZOOTECNICA = :ID_PRODUZIONE_ZOOTECNICA							\r\n" +	
	    		"    AND ID_PROCEDIMENTO_OGGETTO  = :ID_PROCEDIMENTO_OGGETTO							\r\n" +	
	    		"    AND EXT_ISTAT_COMUNE = :EXT_ISTAT_COMUNE											\r\n" 	
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
		      mapParameterSource.addValue("ID_PRODUZIONE_ZOOTECNICA", produzioneZootecnica.getIdProduzioneZootecnica(), Types.NUMERIC);
		      mapParameterSource.addValue("EXT_ISTAT_COMUNE", produzioneZootecnica.getIstatComune(), Types.VARCHAR);
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		      mapParameterSource.addValue("PESO_VIVO_MEDIO", produzioneZootecnica.getPesoVivoMedio(), Types.NUMERIC);
		      mapParameterSource.addValue("GIORNATE_LAVORATIVE_MEDIE", produzioneZootecnica.getGiornateLavorativeMedie(), Types.NUMERIC);
		      mapParameterSource.addValue("NOTE", produzioneZootecnica.getNote(), Types.CLOB);
		      mapParameterSource.addValue("EXT_ID_UTENTE_AGGIORNAMENTO", idUtenteLogin, Types.NUMERIC);
		      namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        	  new LogParameter("ID_PRODUZIONE_ZOOTECNICA", produzioneZootecnica.getIdProduzioneZootecnica()),
	        	  new LogParameter("EXT_ISTAT_COMUNE", produzioneZootecnica.getIstatComune()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("PESO_VIVO_MEDIO",produzioneZootecnica.getPesoVivoMedio() ),
	              new LogParameter("GIORNATE_LAVORATIVE_MEDIE", produzioneZootecnica.getGiornateLavorativeMedie()),
	              new LogParameter("NOTE", produzioneZootecnica.getNote()),
	              new LogParameter("EXT_ID_UTENTE_AGGIORNAMENTO",idUtenteLogin)
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public long inserisciProduzioneVendibile(long idProcedimentoOggetto, ProduzioneCategoriaAnimaleDTO produzione, long idProduzioneZootecnica) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT = 
	    		"INSERT INTO NEMBO_T_PRODUZIONE_VENDIBILE								\r\n" + 
	    		" (																		\r\n" + 
	    		"     ID_PRODUZIONE_VENDIBILE,											\r\n" + 
	    		"     ID_PRODUZIONE_ZOOTECNICA,											\r\n" + 
	    		"     ID_PRODUZIONE,													\r\n" + 
	    		"     NUMERO_CAPI,														\r\n" + 
	    		"     QUANTITA_PRODOTTA,												\r\n" + 
	    		"     QUANTITA_REIMPIEGATA,												\r\n" + 
	    		"     PREZZO															\r\n" + 
	    		" )																		\r\n" + 
	    		"VALUES																	\r\n" + 
	    		" (																		\r\n" + 
	    		"     :ID_PRODUZIONE_VENDIBILE,											\r\n" + 
	    		"     :ID_PRODUZIONE_ZOOTECNICA,										\r\n" + 
	    		"     :ID_PRODUZIONE,													\r\n" + 
	    		"     :NUMERO_CAPI,														\r\n" + 
	    		"     :QUANTITA_PRODOTTA,												\r\n" + 
	    		"     :QUANTITA_REIMPIEGATA,											\r\n" + 
	    		"     :PREZZO															\r\n" + 
	    		" )"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTProduzioneVendibile = 0;
	    try
	    {
	      seqNemboTProduzioneVendibile = getNextSequenceValue("SEQ_NEMBO_T_PRODUZIONE_VENDIBI");
	      mapParameterSource.addValue("ID_PRODUZIONE_VENDIBILE", seqNemboTProduzioneVendibile, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PRODUZIONE_ZOOTECNICA", idProduzioneZootecnica, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PRODUZIONE", produzione.getIdProduzione(), Types.NUMERIC);
	      mapParameterSource.addValue("NUMERO_CAPI", produzione.getNumeroCapi(), Types.NUMERIC);
	      mapParameterSource.addValue("QUANTITA_PRODOTTA", produzione.getQuantitaProdotta(), Types.NUMERIC);
	      mapParameterSource.addValue("QUANTITA_REIMPIEGATA", produzione.getQuantitaReimpiegata(), Types.NUMERIC);
	      mapParameterSource.addValue("PREZZO", produzione.getPrezzo(), Types.NUMERIC);
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PRODUZIONE_VENDIBILE", seqNemboTProduzioneVendibile),
	              new LogParameter("ID_PRODUZIONE_ZOOTECNICA", idProduzioneZootecnica),
	              new LogParameter("ID_PRODUZIONE", produzione.getIdProduzione()),
	              new LogParameter("NUMERO_CAPI", produzione.getNumeroCapi()),
	              new LogParameter("QUANTITA_PRODOTTA", produzione.getQuantitaProdotta()),
	              new LogParameter("QUANTITA_REIMPIEGATA", produzione.getQuantitaReimpiegata()),
	              new LogParameter("PREZZO", produzione.getPrezzo())
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return seqNemboTProduzioneVendibile;		
	}

	public void eliminaProduzioniVendibili(long idProduzioneZootecnica) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		" DELETE FROM NEMBO_T_PRODUZIONE_VENDIBILE							\r\n" + 
	    		" WHERE ID_PRODUZIONE_ZOOTECNICA = :ID_PRODUZIONE_ZOOTECNICA		\r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PRODUZIONE_ZOOTECNICA",idProduzioneZootecnica, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PRODUZIONE_ZOOTECNICA", idProduzioneZootecnica)
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public BigDecimal getPlvZootecnicaUfProdotte(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT  																														\r\n" + 
				"    NVL(SUM(U.UF_PRODOTTE * NVL(SC.PRODUZIONE_HA,0)  * CU.SUPERFICIE_UTILIZZATA - NVL(SC.UF_REIMPIEGATE,0)),0)  AS UF_PRODOTTE_NETTE	\r\n" + 
				"FROM 																															\r\n" + 
				"    NEMBO_T_SUPERFICIE_COLTURA SC,																								\r\n" + 
				"    NEMBO_D_UTILIZZO U,																										\r\n" + 
				"    SMRGAA_V_CONDUZIONE_UTILIZZO CU,																							\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO																							\r\n" + 
				"WHERE 																															\r\n" + 
				"    PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																		\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA													\r\n" + 
				"    AND CU.ID_UTILIZZO = SC.EXT_ID_UTILIZZO																					\r\n" + 
				"    AND SC.EXT_ID_UTILIZZO = U.EXT_ID_UTILIZZO																					\r\n" + 
				"GROUP BY																														\r\n" + 
				"    CU.ID_UTILIZZO																												\r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForBigDecimal(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }		
	}
	
	public BigDecimal getPlvZootecnicaUfNecessarie(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT 																	\r\n" + 
				"    NVL(SUM(CA.CONSUMO_ANNUO_UF * A.QUANTITA),0) AS UF_NECESSARIE  				\r\n" + 
				"FROM 																		\r\n" + 
				"    SMRGAA_V_ALLEVAMENTI A,												\r\n" + 
				"    NEMBO_D_CATEGORIA_ANIMALE CA,											\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO										\r\n" + 
				"WHERE 																		\r\n" + 
				"    PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO					\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA	\r\n" + 
				"    AND A.ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE				\r\n" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForBigDecimal(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }		
	}
	
	private String WITH_UBA_SAU = "WITH TMP_UBA AS (																				\r\n" + 
			"    SELECT																						\r\n" + 
			"       SUM(A.QUANTITA * DA.COEFFICIENTE_UBA) AS UBA_TOTALE										\r\n" + 
			"    FROM 																						\r\n" + 
			"        SMRGAA_V_ALLEVAMENTI A,																\r\n" + 
			"        NEMBO_D_CATEGORIA_ANIMALE CA,															\r\n" + 
			"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,														\r\n" + 
			"        SMRGAA_V_DECO_ALLEVAMENTI DA															\r\n" + 
			"    WHERE 																						\r\n" + 
			"        PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO									\r\n" + 
			"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA					\r\n" + 
			"        AND A.ID_CATEGORIA_ANIMALE = CA.EXT_ID_CATEGORIA_ANIMALE								\r\n" + 
			"        AND A.ID_CATEGORIA_ANIMALE = DA.ID_CATEGORIA_ANIMALE									\r\n" + 
			"), TMP_SAU AS (																				\r\n" + 
			"    SELECT																						\r\n" + 
			"        SUM(CU.SUPERFICIE_UTILIZZATA) AS SAU_TOTALE											\r\n" + 
			"    FROM 																						\r\n" + 
			"        NEMBO_T_PROCEDIMENTO_OGGETTO PO,														\r\n" + 
			"        SMRGAA_V_CONDUZIONE_UTILIZZO CU														\r\n" + 
			"    WHERE 																						\r\n" + 
			"        PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO									\r\n" + 
			"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA				\r\n" + 
			"        AND CU.FLAG_SAU = 'S'																	\r\n" + 
			")																								\r\n";
	
	public BigDecimal getPlvZootecnicaRapportoUbaSau(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		
		final String QUERY = 
				WITH_UBA_SAU + 
				"SELECT																							\r\n" + 
				"    UBA_TOTALE / SAU_TOTALE AS RAPPORTO_UBA_SAU 												\r\n" + 
				"FROM																							\r\n" + 
				"    TMP_UBA,																					\r\n" + 
				"    TMP_SAU"					
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForBigDecimal(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }	
	}
	
	public BigDecimal getPlvZootecnicaUba(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	
		final String QUERY = 
				WITH_UBA_SAU + 
				"SELECT																							\r\n" + 
				"    NVL(UBA_TOTALE,0)																					\r\n" + 
				"FROM																							\r\n" + 
				"    TMP_UBA																					\r\n" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForBigDecimal(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }	
	}
	
	public BigDecimal getPlvZootecnicaSau(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		
		final String QUERY = 
				WITH_UBA_SAU + 
				"SELECT																							\r\n" + 
				"    NVL(SAU_TOTALE,0)																					\r\n" + 
				"FROM																							\r\n" + 
				"    TMP_SAU"					
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForBigDecimal(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }	
	}
	
	public List<AllevamentiDTO> getListAllevamenti(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		return getListRiepilogoAllevamenti(idProcedimentoOggetto, null, null, false);
	}
	
	//Esempio di query con doppia outer join - in cui lo stessa dichiarazione di consistenza potrebbe essere condivisa da due procedimento_oggetto diversi
	public List<AllevamentiDettaglioPlvDTO> getListPlvZootecnicaDettaglioAllevamenti(long idProcedimentoOggetto) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"WITH PRODUZIONE_ZOOTECNICA AS (																								\r\n" + 
				"    SELECT *																													\r\n" + 
				"    FROM NEMBO_T_PRODUZIONE_ZOOTECNICA																							\r\n" + 
				"    WHERE ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																	\r\n" + 
				")																																\r\n" + 
				"SELECT 																														\r\n" + 
				"    A.CODICE_AZIENDA_ZOOTECNICA,  																								\r\n" + 
				"    A.DESCRIZIONE_SPECIE_ANIMALE, 																								\r\n" + 
				"    A.DESCRIZIONE_CATEGORIA_ANIMALE, 																							\r\n" + 
				"    PV.ID_PRODUZIONE, 																											\r\n" + 
				"    P.DESCRIZIONE AS DESC_PRODUZIONE,																							\r\n" + 
				"    PV.NUMERO_CAPI,																											\r\n" + 
				"    PV.QUANTITA_PRODOTTA,																										\r\n" + 
				"    PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI AS PROD_LORDA,																		\r\n" + 
				"    PV.QUANTITA_REIMPIEGATA,																									\r\n" + 
				"    PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI - NVL(PV.QUANTITA_REIMPIEGATA,0) AS PROD_NETTA,												\r\n" + 
				"    UM.DESCRIZIONE AS DESC_UNITA_MISURA,																						\r\n" + 
				"    PV.PREZZO,																													\r\n" + 
				"    (PV.NUMERO_CAPI * PV.QUANTITA_PRODOTTA - NVL(PV.QUANTITA_REIMPIEGATA,0)) * PV.PREZZO AS PLV										\r\n" + 
				"FROM																															\r\n" + 
				"     NEMBO_T_PROCEDIMENTO_OGGETTO PO,																							\r\n" + 
				"     NEMBO_T_PRODUZIONE_VENDIBILE PV,																							\r\n" + 
				"     PRODUZIONE_ZOOTECNICA PZ,																									\r\n" + 
				"     SMRGAA_V_ALLEVAMENTI A,																									\r\n" + 
				"     NEMBO_D_PRODUZIONE P,																										\r\n" + 
				"     NEMBO_D_UNITA_MISURA UM																									\r\n" + 
				"WHERE 																															\r\n" + 
				"        PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																	\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA														\r\n" + 
				"    AND A.ID_CATEGORIA_ANIMALE = PZ.EXT_ID_CATEGORIA_ANIMALE																	\r\n" + 
				"    AND PZ.ID_PRODUZIONE_ZOOTECNICA = PV.ID_PRODUZIONE_ZOOTECNICA																\r\n" + 
				"    AND P.ID_UNITA_MISURA = UM.ID_UNITA_MISURA																					\r\n" + 
				"    AND PV.ID_PRODUZIONE = P.ID_PRODUZIONE																						\r\n" + 
				"GROUP BY																														\r\n" + 
				"    A.CODICE_AZIENDA_ZOOTECNICA,																								\r\n" + 
				"    A.DESCRIZIONE_SPECIE_ANIMALE,																								\r\n" + 
				"    A.DESCRIZIONE_CATEGORIA_ANIMALE,																							\r\n" + 
				"    PV.ID_PRODUZIONE,																											\r\n" + 
				"    P.DESCRIZIONE,																												\r\n" + 
				"    PV.NUMERO_CAPI,																											\r\n" + 
				"    PV.QUANTITA_PRODOTTA,																										\r\n" + 
				"    PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI,																						\r\n" + 
				"    PV.QUANTITA_REIMPIEGATA,																									\r\n" + 
				"    PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI - NVL(PV.QUANTITA_REIMPIEGATA,0),															\r\n" + 
				"    UM.DESCRIZIONE,																											\r\n" + 
				"    PV.PREZZO,																													\r\n" + 
				"    (PV.QUANTITA_PRODOTTA * PV.NUMERO_CAPI - NVL(PV.QUANTITA_REIMPIEGATA,0)) * PV.PREZZO 												\r\n" +
				"ORDER BY 																														\r\n" +
				"	 A.DESCRIZIONE_SPECIE_ANIMALE, 																								\r\n" +
				"	 A.DESCRIZIONE_CATEGORIA_ANIMALE, 																							\r\n" +
				"	 P.DESCRIZIONE 																												\r\n" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, AllevamentiDettaglioPlvDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	
	public List<AllevamentiDTO> getListAllevamentiSingoli(long idProcedimentoOggetto, long[] arrayIdAllevamento, boolean onlyNonDanneggiati) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    String inConditionIdAllevamento =  " ";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    if(arrayIdAllevamento != null)
	    {
	    	inConditionIdAllevamento = getInCondition("A.ID_ALLEVAMENTO", arrayIdAllevamento);
	    }
	    
	    String QUERY = 
				"SELECT																																	\r\n" + 
				"    A.ID_ALLEVAMENTO,																													\r\n" + 
				"    A.DESCRIZIONE_CATEGORIA_ANIMALE,																									\r\n" + 
				"    A.DESCRIZIONE_SPECIE_ANIMALE,																										\r\n" + 
				"    A.INDIRIZZO,																														\r\n" + 
				"    A.DENOMINAZIONE_ALLEVAMENTO,																										\r\n" + 
				"    A.QUANTITA,																														\r\n" + 
				"    DA.DESCRIZIONE_COMUNE,																												\r\n" + 
				"    DA.SIGLA_PROVINCIA																													\r\n" + 
				"FROM 																																	\r\n" + 
				"    SMRGAA_V_ALLEVAMENTI A,																											\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,																									\r\n" + 
				"    SMRGAA_V_DATI_AMMINISTRATIVI DA																									\r\n" + 
				"WHERE 																																	\r\n" + 
				"        PO.ID_PROCEDIMENTO_OGGETTO  =:ID_PROCEDIMENTO_OGGETTO																			\r\n" + 
				"    AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = A.ID_DICHIARAZIONE_CONSISTENZA																\r\n" + 
				"    AND A.ISTAT_COMUNE = DA.ISTAT_COMUNE 																								\r\n" +
					 inConditionIdAllevamento;
		if(onlyNonDanneggiati)
		{
			QUERY = QUERY +
					"    AND A.ID_ALLEVAMENTO NOT IN																										\r\n" + 
					"        (																																\r\n" + 
					"            SELECT 																													\r\n" + 
					"                DAT.EXT_ID_ENTITA_DANNEGGIATA																							\r\n" + 
					"            FROM 																														\r\n" + 
					"                NEMBO_T_DANNO_ATM DAT																									\r\n" + 
					"            WHERE 																														\r\n" + 
					"                DAT.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO																	\r\n" + 
									 getInCondition("DAT.ID_DANNO",QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.ALLEVAMENTO)) + " 			\r\n" +
					"        ) 																																\r\n"
					;
		}
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, AllevamentiDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	
	public List<AllevamentiDTO> getListAllevamentiSingoliNonDanneggiati(long idProcedimentoOggetto, long[] arrayIdAllevamento) throws InternalUnexpectedException
	{
		return getListAllevamentiSingoli(idProcedimentoOggetto, arrayIdAllevamento, true);
	}

	public List<DecodificaDTO<Long>> getListDanniDecodificaDTO(long idProcedimentoOggetto)
		throws InternalUnexpectedException
	{	
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		
	    final String QUERY =
				"SELECT																	\r\n" + 
				"    DA.ID_DANNO_ATM AS CODICE,												\r\n" + 
				"    DA.ID_DANNO_ATM AS ID,											\r\n" + 
				"    DA.DESCRIZIONE AS DESCRIZIONE										\r\n" + 
				"FROM																	\r\n" + 
				"    NEMBO_T_DANNO_ATM DA												\r\n" + 
				"WHERE 																	\r\n" + 
				"    DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO "
				;
		
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForDecodificaLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
		
	}
	
	private String getListProgressivoInterventi()
	{
		String query = 
			"        SELECT 																\r\n" + 
    		"            PROGRESSIVO														\r\n" + 
    		"        FROM 																	\r\n" + 
    		"            NEMBO_R_DANNO_ATM_INTERVENTO										\r\n" + 
    		"        WHERE 																	\r\n" + 
    		"            ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO					\r\n"
    		;
		return query;
	}

	public long getNInterventiAssociatiDanni(long idProcedimentoOggetto, long[] arrayIdDannoAtm) 
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String QUERY = 
	    		"SELECT 																		\r\n" + 
	    		"    COUNT(*) AS N_INTERVENTI_DANNI												\r\n" + 
	    		"FROM 																			\r\n" + 
	    		"    NEMBO_T_DANNO_ATM 															\r\n" + 
	    		"WHERE 																			\r\n" + 
	    		"        ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO						\r\n" + 
	    		getInCondition("ID_DANNO_ATM", arrayIdDannoAtm) +
	    		"    AND PROGRESSIVO IN (   													\r\n" + 
	    		getListProgressivoInterventi() +
	    		"    )"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_DANNO_ATM",arrayIdDannoAtm)
	          },
	          new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public long getNInterventiAssociatiDanniScorte(long idProcedimentoOggetto, List<Long> listIdScortaMagazzino) 
			throws InternalUnexpectedException
		{
			String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
			String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		    if (logger.isDebugEnabled())
		    {
		      logger.debug(THIS_METHOD + " BEGIN.");
		    }
		    final String QUERY = 
		    	"SELECT 																		  					\r\n" + 
		    	"	COUNT(*) AS N_INTER_DANNI_SCORTE																\r\n" + 
		    	"FROM 																			  					\r\n" + 
		    	"	NEMBO_T_DANNO_ATM DA,																			\r\n" + 
		    	"   NEMBO_T_SCORTA_MAGAZZINO SM																		\r\n" + 
		    	"WHERE 																			  					\r\n" + 
		    	"        DA.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO						  				\r\n" + 
		    	"    AND DA.EXT_ID_ENTITA_DANNEGGIATA = SM.ID_SCORTA_MAGAZZINO										\r\n" +
		    	getInCondition("SM.ID_SCORTA_MAGAZZINO", listIdScortaMagazzino) + 								"	\r\n" +
		    	getInCondition("DA.ID_DANNO", QuadroNemboDAO.getListDanniEquivalenti(NemboConstants.DANNI.SCORTA))+"\r\n" +
		    	"	AND DA.PROGRESSIVO IN (   													  					\r\n" + 
		    	getListProgressivoInterventi() +
		    	"	)"
					;
		    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
		      
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		      return queryForLong(QUERY, mapParameterSource);
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
		              new LogParameter("ID_SCORTA_MAGAZZINO",listIdScortaMagazzino)
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
		}

	public ColtureAziendaliDTO getRiepilogoColtureAziendali(long idProcedimentoOggetto)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String QUERY = 
	    	"WITH TMP_PLV_ELENCO AS (																																		\r\n" + 
	    	"	SELECT	 																																					\r\n" + 
	    	"        SC.EXT_ISTAT_COMUNE,																																	\r\n" + 
	    	"        CU.ID_UTILIZZO,																																		\r\n" + 
	    	"        SUM(CU.SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,																								\r\n" + 
	    	"        SC.ID_SUPERFICIE_COLTURA,																																\r\n" + 
	    	"        SC.PRODUZIONE_TOTALE_DANNO,																															\r\n" + 
	    	"        SC.PREZZO_DANNEGGIATO,																																	\r\n" + 
	    	"        SC.PERCENTUALE_DANNO,																																	\r\n" + 
	    	"        SC.PREZZO,																																				\r\n" + 
	    	"        SC.PRODUZIONE_HA																																		\r\n" + 
	    	"    FROM																							 															\r\n" + 
	    	"		NEMBO_T_SUPERFICIE_COLTURA SC,																 															\r\n" + 
	    	"		NEMBO_T_PROCEDIMENTO_OGGETTO PO,																	 													\r\n" + 
	    	"		SMRGAA_V_CONDUZIONE_UTILIZZO CU																															\r\n" + 
	    	"	WHERE																							 															\r\n" + 
	    	"		PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO										 															\r\n" + 
	    	"			AND PO.ID_PROCEDIMENTO_OGGETTO = SC.ID_PROCEDIMENTO_OGGETTO						 	 																\r\n" + 
	    	"				AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA			 																\r\n" + 
	    	"					AND CU.ID_UTILIZZO = SC.EXT_ID_UTILIZZO										 																\r\n" + 
	    	"						AND CU.COMUNE = SC.EXT_ISTAT_COMUNE										 																\r\n" + 
	    	"    GROUP BY 																																					\r\n" + 
	    	"        SC.EXT_ISTAT_COMUNE,																																	\r\n" + 
	    	"        CU.ID_UTILIZZO,																																		\r\n" + 
	    	"        SC.ID_SUPERFICIE_COLTURA,																																\r\n" + 
	    	"        SC.PRODUZIONE_TOTALE_DANNO,																															\r\n" + 
	    	"        SC.PREZZO_DANNEGGIATO,																																	\r\n" + 
	    	"        SC.PERCENTUALE_DANNO,																																	\r\n" + 
	    	"        SC.PREZZO,																																				\r\n" + 
	    	"        SC.PRODUZIONE_HA    																																	\r\n" + 
	    	" )																																								\r\n" + 
	    	"SELECT 																																						\r\n" + 
	    	"    SUM(PE.SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,																									\r\n" + 
	    	"    SUM(PE.PRODUZIONE_HA * PE.SUPERFICIE_UTILIZZATA * PE.PREZZO) AS TOTALE_PLV_ORDINARIA,																		\r\n" + 
	    	"    SUM(DECODE(PE.PRODUZIONE_TOTALE_DANNO, NULL,																												\r\n" + 
	    	"        (PE.PRODUZIONE_HA * PE.SUPERFICIE_UTILIZZATA * PE.PREZZO),																								\r\n" + 
	    	"        (PE.PRODUZIONE_TOTALE_DANNO * PE.PREZZO_DANNEGGIATO))) AS TOTALE_PLV_EFFETTIVA																			\r\n" + 
	    	"FROM TMP_PLV_ELENCO PE "
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForObject(QUERY, mapParameterSource, ColtureAziendaliDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
	          },
	          new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public List<ColtureAziendaliDettaglioDTO> getListColtureAziendali(
			long idProcedimentoOggetto,
			long[] arrayIdSuperficieColtura)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    String inConditionArrayIdSuperficieColtura = " ";
	    if(arrayIdSuperficieColtura != null)
	    {
	    	inConditionArrayIdSuperficieColtura = getInCondition("SC.ID_SUPERFICIE_COLTURA", arrayIdSuperficieColtura);
	    }
	    final String QUERY = 
	    	"WITH TMP_PLV_ELENCO AS (																																		\r\n" + 
	    	"	SELECT	 																																					\r\n" + 
	    	"        SC.EXT_ISTAT_COMUNE,																																	\r\n" + 
	    	"        DA.SIGLA_PROVINCIA,																																	\r\n" + 
	    	"        DA.DESCRIZIONE_PROVINCIA AS DESC_PROVINCIA,													 														\r\n" + 
	    	"		DA.DESCRIZIONE_COMUNE AS DESC_COMUNE,																													\r\n" + 
	    	"        CU.ID_UTILIZZO,																																		\r\n" + 
	    	"        DECODE(COLTURA_SECONDARIA,'S',																   															\r\n" + 
	    	"            NVL(DESC_TIPO_UTILIZZO_SECONDARIO, DESC_TIPO_UTILIZZO) || '(secondario)' || ' [' || NVL(COD_TIPO_UTILIZZO_SECONDARIO,COD_TIPO_UTILIZZO) || '] ',	\r\n" + 
	    	"                   DESC_TIPO_UTILIZZO || ' [' || COD_TIPO_UTILIZZO || ']'						   																\r\n" + 
	    	"            ) AS TIPO_UTILIZZO_DESCRIZIONE,																													\r\n" + 
	    	"        CU.SUPERFICIE_UTILIZZATA,																																\r\n" + 
	    	"        SC.ID_SUPERFICIE_COLTURA,																																\r\n" + 
	    	"        SC.PRODUZIONE_TOTALE_DANNO,																															\r\n" + 
	    	"        SC.PREZZO_DANNEGGIATO,																																	\r\n"+ 
	    	"        SC.PERCENTUALE_DANNO,																																	\r\n" + 
	    	"        SC.RECORD_MODIFICATO,																																	\r\n" + 
	    	"        SC.PREZZO,																																				\r\n" + 
	    	"        SC.PRODUZIONE_HA,																																		\r\n" + 
	    	"        (SELECT COUNT(*) AS N_S																																\r\n" + 
	    	"         FROM 																																					\r\n" + 
	    	"            NEMBO_T_CONTROLLO_COLTURA CC1																														\r\n" + 
	    	"         WHERE 																																				\r\n" + 
	    	"                CC1.ID_SUPERFICIE_COLTURA = SC.ID_SUPERFICIE_COLTURA 																							\r\n" + 
	    	"            AND CC1.BLOCCANTE = 'S') AS N_BLOCCANTE_S,																											\r\n" + 
	    	"        (SELECT COUNT(*) AS N_N																																\r\n" + 
	    	"        FROM 																																					\r\n" + 
	    	"            NEMBO_T_CONTROLLO_COLTURA CC2																														\r\n" + 
	    	"        WHERE 																																					\r\n" + 
	    	"                CC2.ID_SUPERFICIE_COLTURA = SC.ID_SUPERFICIE_COLTURA																							\r\n" + 
	    	"            AND CC2.BLOCCANTE = 'N') AS N_BLOCCANTE_N																											\r\n" +
	    				
	    	"    FROM																							 															\r\n" + 
	    	"		NEMBO_T_SUPERFICIE_COLTURA SC,																 															\r\n" + 
	    	"		NEMBO_T_PROCEDIMENTO_OGGETTO PO,																	 													\r\n" + 
	    	"		SMRGAA_V_CONDUZIONE_UTILIZZO CU,															 															\r\n" + 
	    	"		SMRGAA_V_DATI_AMMINISTRATIVI DA																															\r\n" + 
	    	"	WHERE																							 															\r\n" + 
	    	"		PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO										 															\r\n" + 
	    	"			AND PO.ID_PROCEDIMENTO_OGGETTO = SC.ID_PROCEDIMENTO_OGGETTO						 	 																\r\n" + 
	    	"				AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA			 																\r\n" + 
	    	"					AND CU.ID_UTILIZZO = SC.EXT_ID_UTILIZZO										 																\r\n" + 
	    	"						AND CU.COMUNE = SC.EXT_ISTAT_COMUNE										 																\r\n" + 
	    	"							AND SC.EXT_ISTAT_COMUNE = DA.ISTAT_COMUNE																							\r\n" +
	    	 								inConditionArrayIdSuperficieColtura + 
	    	")																																								\r\n" + 
	    	"SELECT 																																						\r\n" + 
	    	"    PE.ID_SUPERFICIE_COLTURA,																																	\r\n" + 
	    	"    DECODE(N_BLOCCANTE_S, 0,																																	\r\n" + 
	    	"        DECODE(N_BLOCCANTE_N, 0, NULL, 'N'), 'S') AS BLOCCANTE,																								\r\n" + 
	    	"    PE.EXT_ISTAT_COMUNE,																																		\r\n" + 
	    	"    PE.ID_UTILIZZO,																																			\r\n" + 
	    	"    PE.TIPO_UTILIZZO_DESCRIZIONE,																																\r\n" + 
	    	"    PE.SIGLA_PROVINCIA,																																		\r\n" + 
	    	"    PE.DESC_COMUNE,																																			\r\n" + 
	    	"    PE.DESC_COMUNE || ' (' || PE.SIGLA_PROVINCIA || ')' AS UBICAZIONE_TERRENO,																					\r\n" + 
	    	"    SUM(PE.SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,																									\r\n" + 
	    	"    -- PLV ORDINARIA\r\n" + 
	    	"    PE.PRODUZIONE_HA, 																																			\r\n" + 
	    	"    TO_NUMBER(DECODE(PE.PRODUZIONE_HA, NULL,																													\r\n" + 
	    	"        NULL,																																					\r\n" + 
	    	"        ROUND(PE.PRODUZIONE_HA * SUM(PE.SUPERFICIE_UTILIZZATA),2))) AS PRODUZIONE_DICHIARATA, 																			\r\n" + 
	    	"    PE.PREZZO,																																					\r\n" + 
	    	"    TO_NUMBER(DECODE(PE.PRODUZIONE_HA, NULL,																													\r\n" + 
	    	"        NULL,																																					\r\n" + 
	    	"        ROUND(PE.PRODUZIONE_HA * SUM(PE.SUPERFICIE_UTILIZZATA) * PE.PREZZO, 2))) AS TOTALE_EURO_PLV_ORD,																	\r\n" + 
	    	"    --PLV EFFETTIVA																																			\r\n" + 
	    	"    PE.PRODUZIONE_TOTALE_DANNO,																																\r\n" + 
	    	"    PE.PREZZO_DANNEGGIATO,																																		\r\n" + 
	    	"    TO_NUMBER(DECODE(PE.PRODUZIONE_TOTALE_DANNO,NULL,																											\r\n" + 
	    	"        NULL,																																					\r\n" + 
	    	"        DECODE(PE.PREZZO_DANNEGGIATO, NULL,																													\r\n" + 
	    	"            NULL,																																				\r\n" + 
	    	"            ROUND(PE.PRODUZIONE_TOTALE_DANNO * PE.PREZZO_DANNEGGIATO, 2)) 																								\r\n" + 
	    	"    )) AS TOTALE_EURO_PLV_EFF,																																	\r\n" + 
	    	"    PE.PERCENTUALE_DANNO,																																		\r\n" + 
	    	"    PE.RECORD_MODIFICATO																																		\r\n" + 
	    	"FROM																																							\r\n" + 
	    	"    TMP_PLV_ELENCO PE																																			\r\n" + 
	    	"GROUP BY																																						\r\n" + 
	    	"    PE.ID_SUPERFICIE_COLTURA,																																	\r\n" + 
	    	"    DECODE(N_BLOCCANTE_S, 0,																																	\r\n" + 
	    	"        DECODE(N_BLOCCANTE_N, 0, NULL, 'N'), 'S'),																												\r\n" + 
	    	"    PE.EXT_ISTAT_COMUNE,																																		\r\n" + 
	    	"    PE.ID_UTILIZZO,																																			\r\n" + 
	    	"    PE.TIPO_UTILIZZO_DESCRIZIONE,																																\r\n" + 
	    	"    PE.SIGLA_PROVINCIA,																																		\r\n" + 
	    	"    PE.DESC_COMUNE,																																			\r\n" + 
	    	"    PE.DESC_COMUNE || ' (' || PE.SIGLA_PROVINCIA || ')',																										\r\n" + 
	    	"    PE.PRODUZIONE_HA,																																			\r\n" + 
	    	"    PE.PREZZO,																																					\r\n" + 
	    	"    PE.PRODUZIONE_TOTALE_DANNO,																																\r\n" + 
	    	"    PE.PREZZO_DANNEGGIATO,																																		\r\n" + 
	    	"    PE.PERCENTUALE_DANNO,																																		\r\n" + 
	    	"    PE.RECORD_MODIFICATO																																		\r\n" + 
	    	"ORDER BY    																																					\r\n" + 
	    	"    PE.SIGLA_PROVINCIA,																																		\r\n" + 
	    	"    PE.DESC_COMUNE,																																			\r\n" + 
	    	"    PE.TIPO_UTILIZZO_DESCRIZIONE"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, ColtureAziendaliDettaglioDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
	          },
	          new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public long updateColturaAziendale(long idProcedimentoOggetto, ColtureAziendaliDettaglioDTO coltura) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE = 
	    		"	 UPDATE NEMBO_T_SUPERFICIE_COLTURA 									\r\n" + 
				"    SET 																\r\n" + 
				"    PRODUZIONE_HA = :PRODUZIONE_HA,									\r\n" + 
				"    PREZZO = :PREZZO,													\r\n" + 
				"    RECORD_MODIFICATO = 'S',											\r\n" + 
				"    PRODUZIONE_TOTALE_DANNO = :PRODUZIONE_TOTALE_DANNO,				\r\n" + 
				"    PREZZO_DANNEGGIATO = :PREZZO_DANNEGGIATO,							\r\n" + 
				"    PERCENTUALE_DANNO = :PERCENTUALE_DANNO								\r\n" + 
				"    WHERE  	ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO		\r\n" +
				"	 		AND ID_SUPERFICIE_COLTURA = :ID_SUPERFICIE_COLTURA "	
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("PRODUZIONE_HA", coltura.getProduzioneHa(), Types.NUMERIC);
	      mapParameterSource.addValue("PREZZO", coltura.getPrezzo(), Types.NUMERIC);
	      mapParameterSource.addValue("PRODUZIONE_TOTALE_DANNO", coltura.getProduzioneTotaleDanno(), Types.NUMERIC);
	      mapParameterSource.addValue("PREZZO_DANNEGGIATO", coltura.getPrezzoDanneggiato(), Types.NUMERIC);
	      mapParameterSource.addValue("PERCENTUALE_DANNO", coltura.getPercentualeDanno(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_SUPERFICIE_COLTURA", coltura.getIdSuperficieColtura(), Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("PRODUZIONE_HA", coltura.getProduzioneHa()),
	              new LogParameter("PREZZO",coltura.getPrezzo()),
	              new LogParameter("PRODUZIONE_TOTALE_DANNO", coltura.getProduzioneTotaleDanno()),
	              new LogParameter("PREZZO_DANNEGGIATO", coltura.getPrezzoDanneggiato()),
	              new LogParameter("PRODUZIONE_TOTALE_DANNO", coltura.getProduzioneTotaleDanno()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_SUPERFICIE_COLTURA", coltura.getIdSuperficieColtura())
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto)
		throws InternalUnexpectedException
	{
		return getListAssicurazioniColture(idProcedimentoOggetto,null);
	}
	
	public List<AssicurazioniColtureDTO> getListAssicurazioniColture(long idProcedimentoOggetto, long[] arrayIdAssicurazioniColture)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    String inConditionAssicurazioniColture = "";
	    if (logger.isDebugEnabled())
	    {
	    	logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    if(arrayIdAssicurazioniColture != null)
	    {
	    	inConditionAssicurazioniColture = getInCondition("ID_ASSICURAZIONI_COLTURE", arrayIdAssicurazioniColture);
	    }
		final String QUERY = 
				"SELECT 																	\r\n" + 
				"    AC.ID_ASSICURAZIONI_COLTURE,											\r\n" + 
				"    AC.ID_PROCEDIMENTO_OGGETTO,											\r\n" + 
				"    AC.ID_CONSORZIO_DIFESA,												\r\n" + 
				"    AC.NOME_ENTE_PRIVATO,													\r\n" + 
				"    AC.NUMERO_SOCIO_POLIZZA,												\r\n" + 
				"    AC.IMPORTO_PREMIO,														\r\n" + 
				"    AC.IMPORTO_ASSICURATO,													\r\n" + 
				"    AC.IMPORTO_RIMBORSO,													\r\n" + 
				"    CD.EXT_ID_PROVINCIA,													\r\n" + 
				"    CD.DESCRIZIONE AS DESCRIZIONE_CONSORZIO								\r\n" + 
				"FROM    																	\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,										\r\n" + 
				"    NEMBO_T_ASSICURAZIONI_COLTURE AC,										\r\n" + 
				"    NEMBO_D_CONSORZIO_DIFESA CD											\r\n" + 
				"WHERE 																		\r\n" + 
				"    AC.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO				\r\n" + 
				"    AND AC.ID_CONSORZIO_DIFESA = CD.ID_CONSORZIO_DIFESA (+)				\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 				\r\n" +
				inConditionAssicurazioniColture
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, AssicurazioniColtureDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto),
	        		  new LogParameter("ID_ASSICURAZIONI_COLTURE",arrayIdAssicurazioniColture)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public AssicurazioniColtureDTO getRiepilogoAssicurazioniColture(long idProcedimentoOggetto)
			throws InternalUnexpectedException
		{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	    	logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT 																	\r\n" + 
				"    SUM(AC.IMPORTO_PREMIO),												\r\n" + 
				"    SUM(AC.IMPORTO_ASSICURATO),											\r\n" + 
				"    SUM(AC.IMPORTO_RIMBORSO) 												\r\n" + 
				"FROM    																	\r\n" + 
				"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,										\r\n" + 
				"    NEMBO_T_ASSICURAZIONI_COLTURE AC										\r\n" + 
				"WHERE 																		\r\n" + 
				"    AC.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO				\r\n" + 
				"    AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO 				\r\n" 
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForObject(QUERY, mapParameterSource, AssicurazioniColtureDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public int eliminaAssicurazioniColture(long idProcedimentoOggetto, long[] arrayIdAssicurazioniColture)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String DELETE = 
	    		"DELETE FROM NEMBO_T_ASSICURAZIONI_COLTURE					\r\n" + 
	    		"WHERE 														\r\n" + 
	    		"    ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO		\r\n" + 
	    		getInCondition("ID_ASSICURAZIONI_COLTURE", arrayIdAssicurazioniColture)
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto, Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(DELETE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_ASSICURAZIONI_COLTURE", arrayIdAssicurazioniColture),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	          },
	          new LogVariable[]
	          {}, DELETE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	public long inserisciAssicurazioniColture(long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String INSERT =
				"INSERT INTO NEMBO_T_ASSICURAZIONI_COLTURE				\r\n" + 
				"        (ID_ASSICURAZIONI_COLTURE,						\r\n" + 
				"        ID_CONSORZIO_DIFESA,							\r\n" + 
				"        NOME_ENTE_PRIVATO,								\r\n" + 
				"        NUMERO_SOCIO_POLIZZA,							\r\n" + 
				"        IMPORTO_PREMIO,								\r\n" + 
				"        IMPORTO_ASSICURATO,							\r\n" + 
				"        IMPORTO_RIMBORSO,								\r\n" + 
				"        ID_PROCEDIMENTO_OGGETTO						\r\n" + 
				"    )													\r\n" + 
				"VALUES 												\r\n" + 
				"    (													\r\n" + 
				"        :ID_ASSICURAZIONI_COLTURE,						\r\n" + 
				"        :ID_CONSORZIO_DIFESA,							\r\n" + 
				"        :NOME_ENTE_PRIVATO,							\r\n" + 
				"        :NUMERO_SOCIO_POLIZZA,							\r\n" + 
				"        :IMPORTO_PREMIO,								\r\n" + 
				"        :IMPORTO_ASSICURATO,							\r\n" + 
				"        :IMPORTO_RIMBORSO,								\r\n" + 
				"        :ID_PROCEDIMENTO_OGGETTO						\r\n" + 
				"    )"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long idAssicurazioniColture = 0;
	    try
	    {
	      idAssicurazioniColture = getNextSequenceValue("SEQ_NEMBO_T_ASSICURAZIONI_COLT");
	      mapParameterSource.addValue("ID_ASSICURAZIONI_COLTURE", idAssicurazioniColture, Types.NUMERIC);
	      mapParameterSource.addValue("ID_CONSORZIO_DIFESA", assicurazioniColture.getIdConsorzioDifesa(), Types.NUMERIC);
	      mapParameterSource.addValue("NOME_ENTE_PRIVATO", assicurazioniColture.getNomeEntePrivato(), Types.VARCHAR);
	      mapParameterSource.addValue("NUMERO_SOCIO_POLIZZA", assicurazioniColture.getNumeroSocioPolizza(), Types.VARCHAR);
	      mapParameterSource.addValue("IMPORTO_PREMIO", assicurazioniColture.getImportoPremio(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO_ASSICURATO", assicurazioniColture.getImportoAssicurato(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO_RIMBORSO", assicurazioniColture.getImportoRimborso(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_ASSICURAZIONI_COLTURE", idAssicurazioniColture),
	              new LogParameter("ID_CONSORZIO_DIFESA", assicurazioniColture.getIdConsorzioDifesa()),
	              new LogParameter("NOME_ENTE_PRIVATO", assicurazioniColture.getNomeEntePrivato()),
	              new LogParameter("NUMERO_SOCIO_POLIZZA", assicurazioniColture.getNumeroSocioPolizza()),
	              new LogParameter("IMPORTO_PREMIO", assicurazioniColture.getImportoPremio()),
	              new LogParameter("IMPORTO_ASSICURATO", assicurazioniColture.getImportoAssicurato()),
	              new LogParameter("IMPORTO_RIMBORSO", assicurazioniColture.getImportoRimborso()),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto)
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	    return idAssicurazioniColture;
	}

	public List<DecodificaDTO<Integer>> getListConsorzi(String idProvincia)
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT 												\r\n" + 
				"    ID_CONSORZIO_DIFESA AS ID,							\r\n" + 
				"    ID_CONSORZIO_DIFESA AS CODICE,						\r\n" + 
				"    DESCRIZIONE										\r\n" + 
				"FROM 													\r\n" + 
				"    NEMBO_D_CONSORZIO_DIFESA							\r\n" + 
				"WHERE													\r\n" + 
				"    EXT_ID_PROVINCIA = :ID_PROVINCIA					\r\n"  
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROVINCIA", idProvincia, Types.VARCHAR);
	      return queryForDecodificaInteger(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROVINCIA",idProvincia)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public List<Long> getListConsorzioDifesa(String[] arrayProvincePiemonte)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT 												\r\n" + 
				"    ID_CONSORZIO_DIFESA AS ID							\r\n" + 
				"FROM 													\r\n" + 
				"    NEMBO_D_CONSORZIO_DIFESA							\r\n" + 
				"WHERE													\r\n" +
				"    1=1 \r\n" +
				getInCondition("EXT_ID_PROVINCIA", Arrays.asList(arrayProvincePiemonte))
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      return queryForList(QUERY, mapParameterSource, Long.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("EXT_ID_PROVINCIA",arrayProvincePiemonte)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }		
	}

	public long modificaAssicurazioniColture(long idProcedimentoOggetto, AssicurazioniColtureDTO assicurazioniColture)
		throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String UPDATE =
				"UPDATE NEMBO_T_ASSICURAZIONI_COLTURE										\r\n" + 
				"SET  																		\r\n" + 
				"    ID_CONSORZIO_DIFESA = :ID_CONSORZIO_DIFESA,							\r\n" + 
				"    NOME_ENTE_PRIVATO = :NOME_ENTE_PRIVATO,								\r\n" + 
				"    NUMERO_SOCIO_POLIZZA = :NUMERO_SOCIO_POLIZZA,							\r\n" + 
				"    IMPORTO_PREMIO = :IMPORTO_PREMIO,										\r\n" + 
				"    IMPORTO_ASSICURATO = :IMPORTO_ASSICURATO,								\r\n" + 
				"    IMPORTO_RIMBORSO = :IMPORTO_RIMBORSO									\r\n" + 
				"WHERE																		\r\n" + 
				"        ID_ASSICURAZIONI_COLTURE =:ID_ASSICURAZIONI_COLTURE				\r\n" + 
				"    AND ID_PROCEDIMENTO_OGGETTO  =:ID_PROCEDIMENTO_OGGETTO					\r\n"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_ASSICURAZIONI_COLTURE", assicurazioniColture.getIdAssicurazioniColture(), Types.NUMERIC);
	      mapParameterSource.addValue("ID_CONSORZIO_DIFESA", assicurazioniColture.getIdConsorzioDifesa(), Types.NUMERIC);
	      mapParameterSource.addValue("NOME_ENTE_PRIVATO", assicurazioniColture.getNomeEntePrivato(), Types.VARCHAR);
	      mapParameterSource.addValue("NUMERO_SOCIO_POLIZZA", assicurazioniColture.getNumeroSocioPolizza(), Types.VARCHAR);
	      mapParameterSource.addValue("IMPORTO_PREMIO", assicurazioniColture.getImportoPremio(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO_ASSICURATO", assicurazioniColture.getImportoAssicurato(), Types.NUMERIC);
	      mapParameterSource.addValue("IMPORTO_RIMBORSO", assicurazioniColture.getImportoRimborso(), Types.NUMERIC);
	      return namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("ID_ASSICURAZIONI_COLTURE", assicurazioniColture.getIdAssicurazioniColture()),
	              new LogParameter("ID_CONSORZIO_DIFESA", assicurazioniColture.getIdConsorzioDifesa()),
	              new LogParameter("NOME_ENTE_PRIVATO", assicurazioniColture.getNomeEntePrivato()),
	              new LogParameter("NUMERO_SOCIO_POLIZZA", assicurazioniColture.getNumeroSocioPolizza()),
	              new LogParameter("IMPORTO_PREMIO", assicurazioniColture.getImportoPremio()),
	              new LogParameter("IMPORTO_ASSICURATO", assicurazioniColture.getImportoAssicurato()),
	              new LogParameter("IMPORTO_RIMBORSO", assicurazioniColture.getImportoRimborso())
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}
	
	
	//Query Complessa PLV
	public List<DanniColtureDTO> getListDanniColture(long idProcedimentoOggetto) 
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String QUERY =
	    		"WITH TMP_SUPERFICI AS (    \r\n" + 
	    		"SELECT \r\n" + 
	    		"    CU.ID_UTILIZZO,																			\r\n" + 
	    		"    CU.COD_TIPO_UTILIZZO,																	\r\n" + 
	    		"    CU.DESC_TIPO_UTILIZZO,																	\r\n" + 
	    		"    CU.COD_TIPO_UTILIZZO_SECONDARIO,														\r\n" + 
	    		"    CU.DESC_TIPO_UTILIZZO_SECONDARIO,\r\n" + 
	    		"    SC.COLTURA_SECONDARIA,\r\n" + 
	    		"    DECODE(SC.COLTURA_SECONDARIA, 'S',\r\n" + 
	    		"        NVL(DESC_TIPO_UTILIZZO_SECONDARIO, DESC_TIPO_UTILIZZO) || '(secondario)' || ' [' || NVL(COD_TIPO_UTILIZZO_SECONDARIO,COD_TIPO_UTILIZZO) || '] ',\r\n" + 
	    		"        DESC_TIPO_UTILIZZO || ' [' || COD_TIPO_UTILIZZO || ']'\r\n" + 
	    		"    ) AS TIPO_UTILIZZO_DESCRIZIONE,\r\n" + 
	    		"    \r\n" + 
	    		"    SUM(NVL(SC.PRODUZIONE_HA * CU.SUPERFICIE_UTILIZZATA,0)) AS TOT_QLI_PLV_ORD,\r\n" + 
	    		"    SUM(NVL(SC.PRODUZIONE_HA * CU.SUPERFICIE_UTILIZZATA * SC.PREZZO,0)) AS TOT_EURO_PLV_ORD,\r\n" + 
	    		"    SUM(SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,\r\n" + 
	    		"    \r\n" + 
	    		"    DECODE(SC.PRODUZIONE_TOTALE_DANNO, NULL,\r\n" + 
	    		"        SUM(NVL(SC.PRODUZIONE_HA * CU.SUPERFICIE_UTILIZZATA, 0)),\r\n" + 
	    		"        SC.PRODUZIONE_TOTALE_DANNO) AS TOT_QLI_PLV_EFF,\r\n" + 
	    		"        \r\n" + 
	    		"    DECODE(SC.PRODUZIONE_TOTALE_DANNO, NULL,\r\n" + 
	    		"        SUM(NVL(SC.PRODUZIONE_HA * CU.SUPERFICIE_UTILIZZATA * SC.PREZZO, 0)),\r\n" + 
	    		"        SC.PRODUZIONE_TOTALE_DANNO * SC.PREZZO_DANNEGGIATO) AS TOT_EURO_PLV_EFF        \r\n" + 
	    		"FROM																						\r\n" + 
	    		"    NEMBO_T_SUPERFICIE_COLTURA SC,															\r\n" + 
	    		"    NEMBO_T_PROCEDIMENTO_OGGETTO PO,														\r\n" + 
	    		"    SMRGAA_V_CONDUZIONE_UTILIZZO CU																											\r\n" + 
	    		"WHERE																						\r\n" + 
	    		"    PO.ID_PROCEDIMENTO_OGGETTO =:ID_PROCEDIMENTO_OGGETTO									\r\n" + 
	    		"    AND PO.ID_PROCEDIMENTO_OGGETTO = SC.ID_PROCEDIMENTO_OGGETTO						 	\r\n" + 
	    		"        AND PO.EXT_ID_DICHIARAZIONE_CONSISTEN = CU.ID_DICHIARAZIONE_CONSISTENZA			\r\n" + 
	    		"            AND CU.ID_UTILIZZO = SC.EXT_ID_UTILIZZO										\r\n" + 
	    		"                AND CU.COMUNE = SC.EXT_ISTAT_COMUNE			\r\n" + 
	    		"\r\n" + 
	    		"GROUP BY\r\n" + 
	    		"    \r\n" + 
	    		"    CU.ID_UTILIZZO,																			\r\n" + 
	    		"    CU.COD_TIPO_UTILIZZO,																	\r\n" + 
	    		"    CU.DESC_TIPO_UTILIZZO,																	\r\n" + 
	    		"    CU.COD_TIPO_UTILIZZO_SECONDARIO,														\r\n" + 
	    		"    CU.DESC_TIPO_UTILIZZO_SECONDARIO,\r\n" + 
	    		"    SC.ID_SUPERFICIE_COLTURA,\r\n" + 
	    		"    SC.PRODUZIONE_TOTALE_DANNO,\r\n" + 
	    		"    SC.PREZZO_DANNEGGIATO,\r\n" + 
	    		"    SC.COLTURA_SECONDARIA\r\n" + 
	    		")\r\n" + 
	    		"    SELECT \r\n" + 
	    		"        ID_UTILIZZO,																			\r\n" + 
	    		"        COD_TIPO_UTILIZZO,																	\r\n" + 
	    		"        DESC_TIPO_UTILIZZO,																	\r\n" + 
	    		"        COD_TIPO_UTILIZZO_SECONDARIO,														\r\n" + 
	    		"        DESC_TIPO_UTILIZZO_SECONDARIO,\r\n" + 
	    		"        COLTURA_SECONDARIA,\r\n" + 
	    		"        TIPO_UTILIZZO_DESCRIZIONE,\r\n" + 
	    		"        SUM(SUPERFICIE_UTILIZZATA) AS SUPERFICIE_UTILIZZATA,\r\n" + 
	    		"        SUM(TOT_QLI_PLV_ORD) AS TOT_QLI_PLV_ORD,\r\n" + 
	    		"        SUM(TOT_EURO_PLV_ORD) AS TOT_EURO_PLV_ORD,\r\n" + 
	    		"        SUM(TOT_QLI_PLV_EFF) AS TOT_QLI_PLV_EFF,\r\n" + 
	    		"        SUM(TOT_EURO_PLV_EFF) AS TOT_EURO_PLV_EFF\r\n" + 
	    		"    FROM \r\n" + 
	    		"        TMP_SUPERFICI\r\n" + 
	    		"    GROUP BY    \r\n" + 
	    		"        ID_UTILIZZO,																			\r\n" + 
	    		"        COD_TIPO_UTILIZZO,																	\r\n" + 
	    		"        DESC_TIPO_UTILIZZO,																	\r\n" + 
	    		"        COD_TIPO_UTILIZZO_SECONDARIO,														\r\n" + 
	    		"        DESC_TIPO_UTILIZZO_SECONDARIO,\r\n" + 
	    		"        COLTURA_SECONDARIA,\r\n" + 
	    		"        TIPO_UTILIZZO_DESCRIZIONE\r\n" + 
	    		"    ORDER BY\r\n" + 
	    		"        TIPO_UTILIZZO_DESCRIZIONE\r\n"
	    		;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForList(QUERY, mapParameterSource, DanniColtureDTO.class);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

	public Long getNColtureDanneggiate(long idProcedimentoOggetto)
			throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
	    String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
		final String QUERY = 
				"SELECT 														\r\n" + 
				"    COUNT(*) AS N												\r\n" + 
				"FROM 															\r\n" + 
				"    NEMBO_T_SUPERFICIE_COLTURA									\r\n" + 
				"WHERE 															\r\n" + 
				"    ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO			\r\n" + 
				"    AND PRODUZIONE_TOTALE_DANNO IS NOT NULL"
				;
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    try
	    {
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      return queryForLong(QUERY, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	        		  new LogParameter("ID_PROCEDIMENTO_OGGETTO",idProcedimentoOggetto)
	          }, new LogVariable[]
	          {}, QUERY, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
	}

		 
	  public void updateAltroDocRichiesto(long idProcedimentoOggetto, String altroDocRichiesto) throws InternalUnexpectedException{
		  
		  String THIS_METHOD = "[" + THIS_CLASS + "::updateAltroDocRichiesto]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  long result;
		  final String QUERY = " UPDATE NEMBO_T_DOCUMENTI_RICHIESTI                    \n"
						   + " SET                                                   \n"
						   + "     ALTRO_DOC_RICHIESTO = :ALTRODOCRICHIESTO          \n"
						   + " WHERE                                                 \n"
						   + "     ID_PROCEDIMENTO_OGGETTO = :IDPROCEDIMENTOOGGETTO \n";
		  MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
			  mapParameterSource.addValue("ALTRODOCRICHIESTO", altroDocRichiesto, Types.VARCHAR);
		      mapParameterSource.addValue("IDPROCEDIMENTOOGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		      result = namedParameterJdbcTemplate.update(QUERY, mapParameterSource);
		      if(result==0){
		    	  final String QUERY_I = " INSERT INTO NEMBO_T_DOCUMENTI_RICHIESTI (     	\n"
									 + "     ID_DOCUMENTI_RICHIESTI,                   		\n"
									 + "     ID_PROCEDIMENTO_OGGETTO,                  \n"
									 + "     ALTRO_DOC_RICHIESTO                       \n"
									 + " ) VALUES (                                    \n"
									 + "     SEQ_NEMBO_T_DOCUMENTI_RICHIEST.NEXTVAL, \n"
									 + "     :IDPROCEDIMENTOOGGETTO,                 	\n"
									 + "     :ALTRODOCRICHIESTO                      	\n"
									 + " )                                             \n";
		    	  mapParameterSource.addValue("IDPROCEDIMENTOOGGETTO", idProcedimentoOggetto, Types.NUMERIC);
			      mapParameterSource.addValue("ALTRODOCRICHIESTO", altroDocRichiesto, Types.VARCHAR);
			      result = namedParameterJdbcTemplate.update(QUERY_I, mapParameterSource);
		      }
		      
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("IDPROCEDIMENTOOGGETTO", idProcedimentoOggetto),
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }

	  }
	  
	  public void insertRTipoDocRichiesti(long idProcedimentoOggetto, List<Long> listTipoDoc) throws InternalUnexpectedException{
		  String THIS_METHOD = "[" + THIS_CLASS + "::insertRTipoDocRichiesti]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY =   " INSERT INTO NEMBO_R_TIPO_DOCUMENTI_RICHIES (          \n"
							  + " ID_DOCUMENTI_RICHIESTI,                               \n"
							  + " ID_TIPO_DOC_RICHIESTI                                 \n"
							  + " )                                                     \n"
							  + " SELECT                                                \n"
							  + " DR.ID_DOCUMENTI_RICHIESTI,                            \n"
							  + " :IDTIPODOCUMENTIRICHIESTI                             \n"
							  + " FROM                                                  \n"
							  + " NEMBO_T_DOCUMENTI_RICHIESTI DR                        \n"
							  + " WHERE                                                 \n"
							  + " DR.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO \n"	;

		  MapSqlParameterSource[] mapParameterSource = new MapSqlParameterSource[listTipoDoc.size()];
		    try
		    {
		    	for(int i=0; i<listTipoDoc.size();i++){
		    		mapParameterSource[i] = new MapSqlParameterSource();
		    		mapParameterSource[i].addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
				    mapParameterSource[i].addValue("IDTIPODOCUMENTIRICHIESTI", listTipoDoc.get(i), Types.NUMERIC);
		    	}
		      
		      namedParameterJdbcTemplate.batchUpdate(QUERY, mapParameterSource);
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto),
		              new LogParameter("listTipoDoc", listTipoDoc)
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
	  }
	  	  
	  public void deleteRTipoDocRichiesti(long idProcedimentoOggetto) throws InternalUnexpectedException{
		  String THIS_METHOD = "[" + THIS_CLASS + "::deleteRTipoDocRichiesti]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY =  " DELETE FROM NEMBO_R_TIPO_DOCUMENTI_RICHIES                        \n"
							  + " WHERE                                                             \n"
							  + "     ID_DOCUMENTI_RICHIESTI = (                                    \n"
							  + "         SELECT                                                    \n"
							  + "             DR.ID_DOCUMENTI_RICHIESTI                             \n"
							  + "         FROM                                                      \n"
							  + "             NEMBO_T_DOCUMENTI_RICHIESTI DR                        \n"
							  + "         WHERE                                                     \n"
							  + "             DR.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO \n"
							  + "     )					                                            \n";
		  MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		      namedParameterJdbcTemplate.update(QUERY, mapParameterSource);
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }

	  }
	    
	  public List<SezioneDocumentiRichiestiDTO> getListDocumentiRichiestiDaVisualizzare(long idProcedimentoOggetto, Boolean isVisualizzazione) throws InternalUnexpectedException{
		  
		  String THIS_METHOD = "[" + THIS_CLASS + "::getListDocumentiRichiestiDaVisualizzare]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY =  " WITH DOCUMENTI_RICHIESTI AS (                                                                                 \n"
				  			  + "     SELECT                                                                                                    \n"
							  + "         DR.*,                                                                                                 \n"
							  + "         TDR.ID_TIPO_DOC_RICHIESTI                                                                             \n"
							  + "     FROM                                                                                                      \n"
							  + "         NEMBO_T_DOCUMENTI_RICHIESTI DR,                                                                       \n"
							  + "         NEMBO_R_TIPO_DOCUMENTI_RICHIES TDR                                                                    \n"
							  + "     WHERE                                                                                                     \n"
							  + "         DR.ID_DOCUMENTI_RICHIESTI = TDR.ID_DOCUMENTI_RICHIESTI                                                \n"
							  + "         AND DR.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO                                             \n"
							  + " )                                                                                                             \n"
							  + " SELECT                                                                                                        \n"
							  + "     RTDR.ID_DOCUMENTI_RICHIESTI,                                                                              \n"
							  + "     TDR.ID_TIPO_DOC_RICHIESTI,                                                                                \n"
							  + "     TDR.DESCRIZIONE,                                                                                          \n"
							  + "     TDR.ORDINE,                                                                                               \n"
							  + "     SDR.CODICE,                                                                                               \n"
							  + "     SDR.TESTO_SEZIONE                                                                                  \n"
							  + " FROM                                                                                                          \n"
							  + "     NEMBO_D_TIPO_DOC_RICHIESTI TDR                                                                            \n"
							  + "     LEFT OUTER JOIN DOCUMENTI_RICHIESTI RTDR ON ( TDR.ID_TIPO_DOC_RICHIESTI = RTDR.ID_TIPO_DOC_RICHIESTI ),   \n"
							  + "     NEMBO_T_PROCEDIMENTO_OGGETTO PO,                                                                          \n"
							  + "     NEMBO_D_SEZIONE_DOC_RICHIESTI SDR                                                                         \n"
							  + " WHERE                                                                                                         \n"
							  + "     TDR.DATA_INIZIO_VALIDITA <= NVL(PO.DATA_FINE, SYSDATE)                                                    \n"
							  + "     AND SDR.ID_SEZIONE_DOC_RICHIESTI = TDR.ID_SEZIONE_DOC_RICHIESTI                                           \n"
							  + "     AND ( TDR.DATA_FINE_VALIDITA IS NULL                                                                      \n"
							  + "           OR TDR.DATA_FINE_VALIDITA >= TRUNC(NVL(PO.DATA_FINE, SYSDATE)) )                                    \n"
							  + "     AND PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO                                                 \n"
							  + " UNION ALL                                                                                                     \n"
							  + " SELECT                                                                                                        \n"
							  + "     DR.ID_DOCUMENTI_RICHIESTI,                                                                                \n"
							  + "     NULL,                                                                                                     \n"
							  + "     DR.ALTRO_DOC_RICHIESTO AS DESCRIZIONE,                                                                                    \n"
							  + "     9999999999,                                                                                               \n"
							  + "     SDR.CODICE,                                                                                               \n"
							  + "     SDR.TESTO_SEZIONE                                                                                        \n"
							  + " FROM                                                                                                          \n"
							  + "     NEMBO_D_SEZIONE_DOC_RICHIESTI SDR, NEMBO_T_PROCEDIMENTO_OGGETTO PO                                        \n"
							  + "     LEFT OUTER JOIN NEMBO_T_DOCUMENTI_RICHIESTI DR ON DR.ID_PROCEDIMENTO_OGGETTO = PO.ID_PROCEDIMENTO_OGGETTO \n"
							  + " WHERE                                                                                                         \n"
							  + "     PO.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO                                                     \n"
							  + "     AND SDR.CODICE = 'H'                                                                                      \n"
							  + (isVisualizzazione ? " AND DR.ALTRO_DOC_RICHIESTO IS NOT NULL\n" : "")
							  + " ORDER BY                                                                                                      \n"
							  + "     5,                                                                                                        \n"
							  + "     4                                                                                                         \n";
		    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		     
		      return namedParameterJdbcTemplate.query(QUERY, mapParameterSource,
		              new ResultSetExtractor<List<SezioneDocumentiRichiestiDTO>>()
		              {
		                @Override
		                public List<SezioneDocumentiRichiestiDTO> extractData(ResultSet rs)
		                    throws SQLException, DataAccessException
		                {
		                  ArrayList<SezioneDocumentiRichiestiDTO> list = new ArrayList<SezioneDocumentiRichiestiDTO>();
		                  ArrayList<DocumentiRichiestiDaVisualizzareDTO> listDoc = new ArrayList<DocumentiRichiestiDaVisualizzareDTO>();
		                  SezioneDocumentiRichiestiDTO sezioneDocumentiRichiestiDTO = null;
		                  DocumentiRichiestiDaVisualizzareDTO doc = null;
		                  String lastIdSezione = null;
		                  String  idSezione = null;
		                  while (rs.next())
		                  {
		                	idSezione = rs.getString("CODICE");
		                    if (lastIdSezione == null || !lastIdSezione.equals(idSezione))
		                    {
		                    	if((isVisualizzazione && rs.getLong("ID_DOCUMENTI_RICHIESTI")!=0) || !isVisualizzazione){
		                    		if(sezioneDocumentiRichiestiDTO!=null)
				                    	  list.add(sezioneDocumentiRichiestiDTO);
				                      
				                      sezioneDocumentiRichiestiDTO = new SezioneDocumentiRichiestiDTO();

				                      
				                      doc = new DocumentiRichiestiDaVisualizzareDTO();
				                      
				                      sezioneDocumentiRichiestiDTO.setDescrizione(rs.getString("TESTO_SEZIONE"));
				                      sezioneDocumentiRichiestiDTO.setIdSezione(rs.getString("CODICE"));
				                      if(rs.getString("ID_DOCUMENTI_RICHIESTI")!=null){
				                    	  sezioneDocumentiRichiestiDTO.setContatoreDoc(sezioneDocumentiRichiestiDTO.getContatoreDoc()+1);
				                    	  doc.setIdDocumentiRichiesti(rs.getLong("ID_DOCUMENTI_RICHIESTI"));
				                      }
				                      
				                      doc.setIdTipoDocRichiesti(rs.getLong("ID_TIPO_DOC_RICHIESTI"));	                      
				                      doc.setDescrizione(rs.getString("DESCRIZIONE"));
				                      doc.setOrdine(rs.getLong("ORDINE"));
				                      listDoc = new ArrayList<DocumentiRichiestiDaVisualizzareDTO>();
				                      listDoc.add(doc);
				                      sezioneDocumentiRichiestiDTO.setList(listDoc);	
				                      lastIdSezione = idSezione;
		                    	}		                      		                      
		                      
		                    }else{
		                    	if((isVisualizzazione && rs.getLong("ID_DOCUMENTI_RICHIESTI")!=0) || !isVisualizzazione){
		                    		doc = new DocumentiRichiestiDaVisualizzareDTO();
			                    	
			                    	if(rs.getString("ID_DOCUMENTI_RICHIESTI")!=null){
				                    	  sezioneDocumentiRichiestiDTO.setContatoreDoc(sezioneDocumentiRichiestiDTO.getContatoreDoc()+1);
				                    	  doc.setIdDocumentiRichiesti(rs.getLong("ID_DOCUMENTI_RICHIESTI"));

			                    	}
			                    	doc.setIdTipoDocRichiesti(rs.getLong("ID_TIPO_DOC_RICHIESTI"));
				                    doc.setDescrizione(rs.getString("DESCRIZIONE"));
				                    doc.setOrdine(rs.getLong("ORDINE"));
				                    listDoc.add(doc);	
			                    	lastIdSezione = idSezione;
		                    	}
		                    	
		                    }
		                    
		                  }
		                  if(sezioneDocumentiRichiestiDTO!=null)
	                    	  list.add(sezioneDocumentiRichiestiDTO);
		                  
		                  return list.isEmpty() ? null : list;
		                }
		              });
		        }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto)
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD + "");
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
	  }

	  public List<DocumentiRichiestiDTO> getDocumentiRichiesti(long idProcedimentoOggetto) throws InternalUnexpectedException{
		  String THIS_METHOD = "[" + THIS_CLASS + "::getDocumentiRichiesti]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY =  " SELECT                                                    \n"
							  + "     DR.ID_DOCUMENTI_RICHIESTI,                            \n"
							  + "     DR.ID_PROCEDIMENTO_OGGETTO,                           \n"
							  + "     DR.ALTRO_DOC_RICHIESTO                                \n"
							  + " FROM                                                      \n"
							  + "     NEMBO_T_DOCUMENTI_RICHIESTI DR                        \n"
							  + " WHERE                                                     \n"
							  + "     DR.ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO \n";
		    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		    try
		    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		     
		      List<DocumentiRichiestiDTO> result = queryForList(QUERY, mapParameterSource, DocumentiRichiestiDTO.class);
		      return result;
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto)
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD + "");
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
		 }

	  
	  
	  public List<String> getListTestoSezioni() throws InternalUnexpectedException {
		  String THIS_METHOD = "[" + THIS_CLASS + "::getListTestoSezioni]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY =	" SELECT                                \n"
							  + "     SDR.TESTO_SEZIONE                 \n"
							  + " FROM                                  \n"
							  + "     NEMBO_D_SEZIONE_DOC_RICHIESTI SDR \n"
							  + " ORDER BY 								\n"
							  + "	  SDR.CODICE						\n";
							  
		  MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		  try
		  {
			  return namedParameterJdbcTemplate.query(QUERY, mapParameterSource,
              new ResultSetExtractor<List<String>>()
              {
                @Override
                public List<String> extractData(ResultSet rs)
                    throws SQLException, DataAccessException
                {
                  ArrayList<String> list = new ArrayList<String>();
                  while (rs.next())
                  {
                	  if(rs.getString("TESTO_SEZIONE")!=null){
                		  list.add(rs.getString("TESTO_SEZIONE"));
                	  }else{
                		  list.add("-");
                	  }
                  }
     
                  return list.isEmpty() ? null : list;
                }
              });
		  }
		  catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]{},
		          new LogVariable[]{}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD);
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }    
	  }

	public ReferenteProgettoDTO getReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto) throws InternalUnexpectedException {
		String THIS_METHOD = "[" + THIS_CLASS + "::getReferenteProgettoByIdProcedimentoOggetto]";
		  if (logger.isDebugEnabled())
		  {	
			  logger.debug(THIS_METHOD + " BEGIN.");
		  }
		  final String QUERY = 	" SELECT                                                 \n"
							  + "     TRP.COGNOME,                                       \n"
							  + "     TRP.NOME,                                          \n"
							  + "     TRP.CODICE_FISCALE,                                \n"
							  + "     VDA.DESCRIZIONE_PROVINCIA,                         \n"
							  + "     VDA.DESCRIZIONE_COMUNE,                            \n"
							  + "	  TRP.EXT_ISTAT_COMUNE,								 \n"
							  + "     TRP.CAP,                                           \n"
							  + "     TRP.TELEFONO,                                      \n"
							  + "     TRP.CELLULARE,                                     \n"
							  + "     TRP.EMAIL                                          \n"
							  + " FROM                                                   \n"
							  + "     NEMBO_T_REFERENTE_PROGETTO TRP,                    \n"
							  + "     SMRGAA_V_DATI_AMMINISTRATIVI VDA                   \n"
							  + " WHERE                                                  \n"
							  + "     ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO \n"
							  + "     AND VDA.ISTAT_COMUNE = TRP.EXT_ISTAT_COMUNE        \n";
							  
		  MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		  try
		    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		     
		      ReferenteProgettoDTO referente = queryForObject(QUERY, mapParameterSource, ReferenteProgettoDTO.class);
		      return referente;
		    }
		    catch (Throwable t)
		    {
		      InternalUnexpectedException e = new InternalUnexpectedException(t,
		          new LogParameter[]
		          {
		              new LogParameter("idProcedimentoOggetto", idProcedimentoOggetto)
		          },
		          new LogVariable[]
		          {}, QUERY, mapParameterSource);
		      logInternalUnexpectedException(e, THIS_METHOD + "");
		      throw e;
		    }
		    finally
		    {
		      if (logger.isDebugEnabled())
		      {
		        logger.debug(THIS_METHOD + " END.");
		      }
		    }
	}

	public void insertReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto, String nome, String cognome,
			String codiceFiscale, String comune, String cap, String telefono, String cellulare,
			String email) throws InternalUnexpectedException {
		String methodName = "insertReferenteProgettoByIdProcedimentoOggetto";
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String INSERT =    " INSERT INTO NEMBO_T_REFERENTE_PROGETTO (    \n"
	    		 + "     ID_REFERENTE_PROGETTO,                  \n"
	    		 + "     ID_PROCEDIMENTO_OGGETTO,                \n"
	    		 + "     NOME,                                   \n"
	    		 + "     COGNOME,                                \n"
	    		 + "     CODICE_FISCALE,                         \n"
	    		 + "     CAP,                                    \n"
	    		 + "     EXT_ISTAT_COMUNE,                       \n"
	    		 + "     TELEFONO,                               \n"
	    		 + "     CELLULARE,                              \n"
	    		 + "     EMAIL                                   \n"
	    		 + " ) VALUES (                                  \n"
	    		 + "     :SEQ_NEMBO_T_REFERENTE_PROGETTO, \n"
	    		 + "     :ID_PROCEDIMENTO_OGGETTO,               \n"
	    		 + "     UPPER(:NOME),                           \n"
	    		 + "     UPPER(:COGNOME),                        \n"
	    		 + "     :CODICE_FISCALE,                        \n"
	    		 + "     :CAP,                                   \n"
	    		 + "     :EXT_ISTAT_COMUNE,                      \n"
	    		 + "     :TELEFONO,                              \n"
	    		 + "     :CELLULARE,                             \n"
	    		 + "     :EMAIL,                                 \n"
	    		 + "     SYSDATE,                                \n"
	    		 + "     :EXT_ID_UTENTE                          \n"
	    		 + " )                                           \n";
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTReferenteProgetto = 0;
	    try
	    {
	      seqNemboTReferenteProgetto = getNextSequenceValue("SEQ_NEMBO_T_REFERENTE_PROGETTO");
	      mapParameterSource.addValue("SEQ_NEMBO_T_REFERENTE_PROGETTO", seqNemboTReferenteProgetto, Types.NUMERIC);
	      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
	      mapParameterSource.addValue("NOME", nome, Types.VARCHAR);
	      mapParameterSource.addValue("COGNOME", cognome, Types.VARCHAR);
	      mapParameterSource.addValue("CODICE_FISCALE", codiceFiscale, Types.VARCHAR);
		  mapParameterSource.addValue("CAP", cap, Types.VARCHAR);
		  mapParameterSource.addValue("EXT_ISTAT_COMUNE", comune, Types.VARCHAR);
		  mapParameterSource.addValue("TELEFONO", telefono, Types.VARCHAR);
		  mapParameterSource.addValue("CELLULARE", cellulare, Types.VARCHAR);
		  mapParameterSource.addValue("EMAIL", email, Types.VARCHAR);
	    						  
	      namedParameterJdbcTemplate.update(INSERT, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("SEQ_NEMBO_T_REFERENTE_PROGETTO", seqNemboTReferenteProgetto),
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("NOME", nome),
	              new LogParameter("COGNOME", cognome),
	              new LogParameter("CODICE_FISCALE", codiceFiscale),
	              new LogParameter("CAP", cap),
	              new LogParameter("EXT_ISTAT_COMUNE", comune),
	              new LogParameter("TELEFONO", telefono),
	              new LogParameter("CELLULARE", cellulare),
	              new LogParameter("EMAIL", email),
	          },
	          new LogVariable[]
	          {}, INSERT, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
		
	}

	public void updateReferenteProgettoByIdProcedimentoOggetto(long idProcedimentoOggetto, String nome, String cognome,
			String codiceFiscale, String comune, String cap, String telefono, String cellulare,
			String email) throws InternalUnexpectedException {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
	    if (logger.isDebugEnabled())
	    {
	      logger.debug(THIS_METHOD + " BEGIN.");
	    }
	    final String UPDATE =" UPDATE NEMBO_T_REFERENTE_PROGETTO                      \n"
	    				   + " SET                                                    \n"
	    				   + "     NOME = UPPER(:NOME),                               \n"
	    				   + "     COGNOME = UPPER(:COGNOME),                         \n"
	    				   + "     CODICE_FISCALE = :CODICE_FISCALE,                  \n"
	    				   + "     CAP = :CAP,                                        \n"
	    				   + "     EXT_ISTAT_COMUNE = :EXT_ISTAT_COMUNE,              \n"
	    				   + "     TELEFONO = :TELEFONO,                              \n"
	    				   + "     CELLULARE = :CELLULARE,                            \n"
	    				   + "     EMAIL = :EMAIL                                     \n"
	    				   + " WHERE                                                  \n"
	    				   + "     ID_PROCEDIMENTO_OGGETTO = :ID_PROCEDIMENTO_OGGETTO \n";
	    MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
	    long seqNemboTScortaMagazzino = 0;
	    try
	    {
		      mapParameterSource.addValue("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto, Types.NUMERIC);
		      mapParameterSource.addValue("NOME", nome, Types.VARCHAR);
		      mapParameterSource.addValue("COGNOME", cognome, Types.VARCHAR);
		      mapParameterSource.addValue("CODICE_FISCALE", codiceFiscale, Types.VARCHAR);
			  mapParameterSource.addValue("CAP", cap, Types.VARCHAR);
			  mapParameterSource.addValue("EXT_ISTAT_COMUNE", comune, Types.VARCHAR);
			  mapParameterSource.addValue("TELEFONO", telefono, Types.VARCHAR);
			  mapParameterSource.addValue("CELLULARE", cellulare, Types.VARCHAR);
			  mapParameterSource.addValue("EMAIL", email, Types.VARCHAR);
	      namedParameterJdbcTemplate.update(UPDATE, mapParameterSource);
	    }
	    catch (Throwable t)
	    {
	      InternalUnexpectedException e = new InternalUnexpectedException(t,
	          new LogParameter[]
	          {
	              new LogParameter("ID_PROCEDIMENTO_OGGETTO", idProcedimentoOggetto),
	              new LogParameter("NOME", nome),
	              new LogParameter("COGNOME", cognome),
	              new LogParameter("CODICE_FISCALE", codiceFiscale),
	              new LogParameter("CAP", cap),
	              new LogParameter("EXT_ISTAT_COMUNE", comune),
	              new LogParameter("TELEFONO", telefono),
	              new LogParameter("CELLULARE", cellulare),
	              new LogParameter("EMAIL", email)
	          },
	          new LogVariable[]
	          {}, UPDATE, mapParameterSource);
	      logInternalUnexpectedException(e, THIS_METHOD);
	      throw e;
	    }
	    finally
	    {
	      if (logger.isDebugEnabled())
	      {
	        logger.debug(THIS_METHOD + " END.");
	      }
	    }
		
	}

	public String getNomeTabellaByIdDanno(Integer idDanno) throws InternalUnexpectedException
	{
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		String THIS_METHOD = "[" + THIS_CLASS + "::" + methodName + "]";
		if (logger.isDebugEnabled())
		{
			logger.debug(THIS_METHOD + " BEGIN.");
		}
		final String QUERY = 
				"SELECT NOME_TABELLA FROM NEMBO_D_DANNO WHERE ID_DANNO = :ID_DANNO"
				  ;
		MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
		try
		{
			mapParameterSource.addValue("ID_DANNO", idDanno, Types.NUMERIC);
			return queryForString(QUERY, mapParameterSource);
		} catch (Throwable t)
		{
			InternalUnexpectedException e = new InternalUnexpectedException(t,
					new LogParameter[] { new LogParameter("ID_DANNO", idDanno) },
					new LogVariable[] {}, QUERY, mapParameterSource);
			logInternalUnexpectedException(e, THIS_METHOD);
			throw e;
		} finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(THIS_METHOD + " END.");
			}
		}
	}
}