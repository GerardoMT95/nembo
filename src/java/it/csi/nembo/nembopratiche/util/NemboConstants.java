package it.csi.nembo.nembopratiche.util;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class NemboConstants
{
  public static final int CONNECTION_SIAN_BEAN_TIMEOUT = 60000;

  public static final class SPID
  {
    public static final int BIT_SPID = 32;
  }

  public static final class PAPUA
  {
    public static final class ATTORE
    {
      public static final String BENEFICIARIO      = "BENEFICIARIO";
      public static final String INTERMEDIARIO_CAA = "INTERMEDIARIO_CAA";
    }
  }

  public static final class PROTOCOLLO
  {
	  public static final String CODICE_STAMPE_ISTANZE = "RTI";
	  public static final String NOME_STAMPE_ISTANZE = "DCN - DOMANDA CALAMITA' NATURALI";
	  
	  public static final String CODICE_UFFICIO_PROTOCOLLAZIONE_PROT = "14BB";
	  public static final String NOME_UFFICIO_PROTOCOLLAZIONE_PROT = "UFFICIO SOSTEGNO ALLE IMPRESE AGRICOLE, ALLE INFRASTRUTTURE RURALI ED ALLO SVILUPPO DELLE PROPRIETA' - SEDI POTENZA E MATERA";
	  public static final String ANNOTAZIONI_PROT ="Protocollo SIARB";
  }
  
  public static final class TEMPLATE_JASPER
  {
	  public static final String COMUNICAZIONE_INTEGRAZIONE = "COMI";
	  public static final class PRATICA
	  {
		  public static final String CODICE = "PRT";
	  }
	  
	  public static final class QUADRO
	  {
		  public static final class NOME
		  {
			    public static final String DATI_IDENTIFICATIVI = "QuadroDatiIdentificativi";
			    public static final String AVVIO_CORSI = "QuadroAvvioCorsi";
			    public static final String VOLTURA = "QuadroVoltura";
			    public static final String CONTO_CORRENTE = "QuadroContoCorrente";
			    public static final String ANTICIPO = "QuadroAnticipo";
			    public static final String INTERVENTI = "QuadroInterventi";
			    public static final String ECONOMICO = "QuadroEconomico";
			    public static final String ALLEGATI = "QuadroAllegati";
			    public static final String FIRMA = "QuadroFirma";
			    public static final String DICHIARAZIONI = "QuadroDichiarazioni";
			    public static final String SEGNALAZIONE_DANNI = "QuadroSegnalazioneDanni";
			    public static final String IMPEGNI = "QuadroImpegni";
		  }
		  public static final class CODICE
		  {
			    public static final String DATI_IDENTIFICATIVI = "QDI";
			    public static final String AVVIO_CORSI = "QAC";
			    public static final String VOLTURA = "QV";
			    public static final String CONTO_CORRENTE = "QCC";
			    public static final String ANTICIPO = "QA";
			    public static final String INTERVENTI = "QI";
			    public static final String ECONOMICO = "QE";
			    public static final String ALLEGATI = "QALL";
			    public static final String DICHIARAZIONI = "QDICH";
			    public static final String SEGNALAZIONE_DANNI = "SDAN";
			    public static final String IMPEGNI = "QIMP";
			    public static final String FIRMA = "QF";
		  }
	  }
  }
  
  public static final class PEC
  {
    public static final class TIPO_DOCUMENTO
    {
      public static final int PEC_GENERICA = 9001;
    }
  }

  public static final class TIPO_DOCUMENTO
  {
    public static final int COMUNICAZIONE_ESITO_PAGAMENTO = 2114;
  }

  public static final class JNDI
  {
    public static final String MAIL_SESSION = "java:jboss/mail/Default";
  }

  public static final class MAIL
  {
    public static final String SERVIZI_AGRICOLTURA = "hd_servagri@csi.it";
  }

  public static class FILTRI
  {
    public static final String UGUALE_A     = "eq";
    public static final String DIVERSO_DA   = "neq";
    public static final String CONTIENE     = "cnt";
    public static final String NON_CONTIENE = "ncnt";
    public static final String VUOTO        = "ept";
    public static final String NON_VUOTO    = "nept";

    public static final String MAGGIORE_DI  = "gte";
    public static final String MINORE_DI    = "lte";
  }

  public static class TIPO_BANDO
  {
    public static final String PREMIO           = "P";
    public static final String INVESTIMENTO     = "I";
    public static final String INVESTIMENTO_GAL = "G";
  }

  public static class FLAG_NOTIFICA
  {
    public static final long WARNING   = 1;
    public static final long GRAVE     = 2;
    public static final long BLOCCANTE = 3;
  }

  public static class AGRIWELL
  {
    public static class ARCHIVIAZIONE
    {
      public static class VISIBILITA
      {
        public static final String TUTTI   = "T";
        public static final String PA      = "P";
        public static final String NESSUNO = "N";

      }
    }
  }

  public static class OGGETTO
  {
    public static class TIPO_OGGETTO_DOQUI
    {
      public static final long VERBALE_ISTRUTTORIA = 2104;
    }

    public static class CODICE
    {
      public static final String COMUNICAZIONE_RINUNCIA                      = "DRIN";
      public static final String CORRETTIVA                                  = "CORRE";
      public static final String ISTRUTTORIA_AMMISSIONE_FINANZIAMENTO        = "ISAMM";
      public static final String ISTRUTTORIA_AMMISSIONE_FINANZIAMENTO_GAL    = "ISAMG";
      public static final String ISTRUTTORIA_AMMISSIBILITA_FINANZIAMENTO     = "ISAMB";
      public static final String ISTRUTTORIA_AMMISSIBILITA_FINANZIAMENTO_GAL = "ISABG";
      public static final String ISTRUTTORIA_VARIANTE                        = "ISVAR";
      public static final String ISTRUTTORIA_PAGAMENTO_MISURE_PREMIO         = "ISTPR";
      public static final String ISTRUTTORIA_AMMISSIBILITA_MISURE_PREMIO     = "ISAMC";
      public static final String ISTRUTTORIA_FINALE_PAGAMENTO_MISURE_PREMIO  = "ISTPF";
      public static final String ISTRUTTORIA_RINUNCIA                        = "ISRIF";
      public static final String REVOCA                                      = "REVO";
      public static final String ISTRUTTORIA_ACCONTO                         = "ISACC";
      public static final String ISTRUTTORIA_ANTICIPO                        = "ISANT";
      public static final String ISTRUTTORIA_SALDO                           = "ISSAL";
      public static final String ISTRUTTORIA_DOMANDA_ANNULLAMENTO            = "ISANP";
      public static final String ISTRUTTORIA_VOLTURA                         = "ISVOL";
      public static final String DOMANDA_SALDO                               = "DSAL";
      public static final String DOMANDA_ACCONTO                             = "DACC";
      public static final String DOMANDA_ANTICIPO                            = "DANT";
      public static final String ISTRUTTORIA_AMMISSIBILITA                   = "ISAMB";

      public static final String PREAVVISO_RIGETTO                           = "CRIG";
      public static final String DISPOSIZIONE_IRREVOCABILE_DI_PAGAMENTO      = "DIRPA";
      public static final String DOMANDA_SOSTEGNO                            = "DA";
      public static final String DOMANDA_SOSTEGNO_PAGAMENTO                  = "DAP";
      public static final String DOMANDA_SOSTEGNO_PREMIO                     = "DSP";

      public static final String DOMANDA_ANNULLAMENTO                        = "ANNULL";

    }
  }

  public static class NEMBO
  {
    public static final int  ID_TIPOLOGIA_DOC_NEMBO_SU_DOQUIAGRI         = 2000;
    public static final long ID_CATEGORIA_DOC_VERBALI_NEMBO_SU_DOQUIAGRI = 2004;
    public static final long ID_CATEGORIA_DOC_LETTERE_NEMBO_SU_DOQUIAGRI = 2005;
    public static final int  ID_EDIZIONE_NEMBO_2014_2020                 = 1;
  }

  public static class ESITO
  {
    public static class PIANO_GRAFICO
    {
      public static final String GENERATO_IN_BOZZA   = "G";
      public static final String GENERAZIONE_DOMANDA = "R";
      public static final String IMPORTATO           = "I";
    }

    public static class TIPO
    {
      public static final String ANALISI                  = "A";
      public static final String NON_ISTANZA              = "O";
      public static final String FINALE                   = "F";
      public static final String ISTANZA                  = "I";
      public static final String VISITA_LUOGO             = "V";
      public static final String CONTROLLI_AMMINISTRATIVI = "C";
      public static final String TECNICO_AMMINISTRATIVO   = "T";
      public static final String CONTROLLI_LOCO           = "L";
      public static final String POSITIVO                 = "P";
      public static final String MOTIVAZIONI              = "M";
      public static final String NEGATIVO                 = "N";
      public static final String PARZIALMENTE_POSITIVO    = "PN";
      public static final String DEFINITIVO               = "Z";
      public static final String RINUNCIATA               = "R";

    }

    public static class LISTE_LIQUIDAZIONE
    {
      public static final String LIKE_ESITO_PRATICHE_LIQUIDABILI = "APP-%";
    }

  }

  public static class TEMPO
  {
    public static final int SECONDI_PRIMA_DI_RIGENERARE_UNA_STAMPA = 600;
  }

  public static class STATO
  {
	  public static class PROCEDIMENTO
	    {
	    	 public static class ID
	         {
		        public static final int AMMESSO_FINANZIAMENTO  		= 12;
		        public static final int NON_AMMESSO_FINANZIAMENTO   = 13;
		        public static final int ISTRUTTORIA_AVVIATA   		= 15;
		        public static final int IN_ATTESA_CONTRODEDUZIONE   = 23;
	         }
	    }
	  
    public static class ITER
    {
      public static class ID
      {
        public static final int IN_BOZZA  = 1;
        public static final int TRASMESSO = 10;
        public static final int ANNULLATO = 99;
      }
    }

    public static class LISTE_LIQUIDAZIONE
    {
      public static class FLAG
      {
        public static final String BOZZA        = "B";
        public static final String DA_APPROVARE = "D";
        public static final String APPROVATA    = "A";
      }
    }

    public static class STAMPA
    {
      public static class ID
      {
        public static final int GENERAZIONE_STAMPA_IN_CORSO          = 1;
        public static final int IN_ATTESA_FIRMA_GRAFOMETRICA         = 2;
        public static final int IN_ATTESA_FIRMA_SU_CARTA             = 3;
        public static final int IN_ATTESA_FIRMA_ELETTRONICA_LEGGERA  = 4;
        public static final int FIRMATO_GRAFOMETRICAMENTE            = 5;
        public static final int FIRMATO_CON_CREDENZIALI_BENEFICIARIO = 6;
        public static final int FIRMATO_SU_CARTA                     = 7;
        public static final int STAMPA_FALLITA                       = 8;
        public static final int ANNULLATO                            = 9;
        public static final int STAMPA_ALLEGATA                      = 10;
        public static final int STAMPATA                             = 11;
        public static final int FIRMATO_DIGITALMENTE                 = 13;
        public static final int FIRMATO_DIGITALMENTE_E_PROTOCOLLATO  = 14;
      }

      public static class DESCRIZIONE
      {
        public static final String IN_ATTESA_FIRMA_GRAFOMETRICA        = "In attesa della firma grafometrica";
        public static final String IN_ATTESA_FIRMA_SU_CARTA            = "In attesa della firma su carta";
        public static final String IN_ATTESA_FIRMA_ELETTRONICA_LEGGERA = "In attesa di firma elettronica semplice";
      }
    }

    public static class OGGETTO
    {
      public static class ID
      {
    	  public static final int TRASMESSO             = 10;
          public static final int ISTRUTTORIA_APPROVATA = 22;
          public static final int RICHIESTA_TRASMESSA = 24;
          public static final int RINUNCIA_BENEFICIARIO = 98;
      }
    }

    public static class ESITO
    {
      public static class ID
      {
        public static final int TRASMESSO                             = 4;
        public static final int POSITIVO                              = 1;
        public static final int APPROVATA_POSITIVO                    = 26;
        public static final int APPROVATA_NEGATIVO                    = 27;
        public static final int APPROVATA_PARZIALE                    = 28;

        public static final int ISTRUTTORIE_PREMIO_APPROVATA_POSITIVO = 50;
        public static final int ISTRUTTORIE_PREMIO_APPROVATA_NEGATIVO = 51;
        public static final int ISTRUTTORIE_PREMIO_APPROVATA_PARZIALE = 52;

        public static final int ISTRUTTORIE_INV_APPROVATA_POSITIVO    = 45;
        public static final int ISTRUTTORIE_INV_APPROVATA_NEGATIVO    = 46;
        public static final int ISTRUTTORIE_INV_APPROVATA_PARZIALE    = 47;

        public static class TIPO_ESITO_O
        {
          public static final long POSITIVO              = 5;
          public static final long NEGATIVO              = 6;
          public static final int COMPLETATO = 23;
          public static final long PARZIALMENTE_POSITIVO = 25;
        }

        public static class TIPO_ESITO_I
        {
          public static final long POSITIVO              = 1;
          public static final long NEGATIVO              = 2;
          public static final long PARZIALMENTE_POSITIVO = 25;
        }

        public static class TIPO_ESITO_F
        {
          public static final long POSITIVO              = 14;
          public static final long NEGATIVO              = 15;
          public static final long PARZIALMENTE_POSITIVO = 24;
        }
      }

    }
  }

  public static class PROCEDIMENTO_OGGETTO
  {
    public static class ESITO
    {
      public static final long POSITIVO = 1;
      public static final long NEGATIVO = 2;
    }

    public static class CRITERIO
    {
      public static final String SOVRASCRIVI_IDENTIFICATIVO = "**";
    }
  }

  public static class SERVICE
  {
    public static class AGRIWELL
    {
      public static class RETURN_CODE
      {
        public static final int SUCCESS = 0;
        public static final int FAIL    = 1;
      }
    }
  }

  public static class USECASE
  {
    public static class RIAPERTURA_OGGETTO
    {
      public static final String ISTANZA     = "CU-NEMBO-130";
      public static final String ISTRUTTORIA = "CU-NEMBO-170";
    }

    public static class CHIUSURA_OGGETTO_DEFINITIVA
    {
      public static final String ISTANZA     = "CU-NEMBO-303";
      public static final String ISTRUTTORIA = "CU-NEMBO-304";
    }

    public static class CHIUSURA_OGGETTO
    {
      public static final String ISTANZA     = "CU-NEMBO-125";
      public static final String ISTRUTTORIA = "CU-NEMBO-164";
    }

    public static final String APPROVAZIONE_ISTRUTTORIA = "CU-NEMBO-232";
    public static final String TRASMISSIONE_ISTANZA     = "CU-NEMBO-140";
    public static final String NUOVO_PROCEDIMENTO       = "CU-NEMBO-101";
    public static final String RICERCA_PROCEDIMENTO     = "CU-NEMBO-102";
    public static final String MESSAGGISTICA_ELENCO     = "CU-NEMBO-202";
    public static final String GESTIONE_SISTEMA         = "CU-NEMBO-204";
    public static final String ELENCO_BANDI             = "CU-NEMBO-208";
    public static final String VISUALIZZA_ESTRAZIONI    = "CU-NEMBO-215";
    public static final String GESTIONE_ESTRAZIONI      = "CU-NEMBO-217";
    public static final String REGISTRO_FATTURE         = "CU-NEMBO-273";
    public static final String REPORTISTICA             = "CU-NEMBO-284";
    public static final String CONTROLLI_DICHIARAZIONI  = "CU-NEMBO-282-D";
    public static final String REGISTRO_ANTIMAFIA       = "CU-NEMBO-287-D";

    public static class LISTE_LIQUIDAZIONE
    {
      public static final String CREA_NUOVA = "CU-NEMBO-226";
      public static final String ELENCO     = "CU-NEMBO-227";
      public static final String APPROVA    = "CU-NEMBO-228";
      public static final String ELIMINA    = "CU-NEMBO-229";
      public static final String STAMPA     = "CU-NEMBO-230";
    }

    public static class GESTIONE_SISTEMA_FUNZIONI
    {
      public static final String REFRESH_ELENCO_CDU = "CU-NEMBO-205";
      public static final String MONITORAGGIO       = "CU-NEMBO-206";
    }

    public static class DATI_IDENTIFICATIVI
    {
      public static final String DETTAGLIO             = "CU-NEMBO-103";
      public static final String MODIFICA              = "CU-NEMBO-105";
      public static final String DETTAGLIO_ISTRUTTORIA = "CU-NEMBO-172";
      public static final String MODIFICA_ISTRUTTORIA  = "CU-NEMBO-173";

    }

    public static class DATI_ANTICIPO
    {
      public static final String DETTAGLIO = "CU-NEMBO-169-V";
      public static final String MODIFICA  = "CU-NEMBO-169-M";

    }

    public static class SOSPENSIONE_ANTICIPO
    {
      public static final String DETTAGLIO = "CU-NEMBO-188";
      public static final String MODIFICA  = "CU-NEMBO-189";

    }

    public static class IMPEGNI_ANNUALI
    {
      public static final String DETTAGLIO             = "CU-NEMBO-141";
      public static final String MODIFICA              = "CU-NEMBO-142";
      public static final String DETTAGLIO_ISTRUTTORIA = "CU-NEMBO-194";
      public static final String MODIFICA_ISTRUTTORIA  = "CU-NEMBO-195";
    }

    public static class PARTICELLE_CONDOTTE
    {
      public static final String DETTAGLIO             = "CU-NEMBO-143";
      public static final String MODIFICA              = "CU-NEMBO-144";
      public static final String DETTAGLIO_ISTRUTTORIA = "CU-NEMBO-196";
      public static final String MODIFICA_ISTRUTTORIA  = "CU-NEMBO-197";
      public static final String DETTAGLIO_CORRETTIVA  = "CU-NEMBO-261";
      public static final String MODIFICA_CORRETTIVA   = "CU-NEMBO-262";
    }

    public static class PREDISPOSIZIONE_IMPEGNI
    {
      public static final String DETTAGLIO = "CU-NEMBO-286-V";
      public static final String MODIFICA  = "CU-NEMBO-286-M";

    }

    public static class FRAZIONA_PARTICELLE
    {
      public static final String MODIFICA = "CU-NEMBO-148";
    }

    public static class CALCOLA_PREMIO
    {
      public static final String DETTAGLIO = "CU-NEMBO-145";
      public static final String CALCOLA   = "CU-NEMBO-146";
    }

    public static class CANI_GUARDIANA
    {
      public static final String DETTAGLIO = "CU-NEMBO-152";
      public static final String MODIFICA  = "CU-NEMBO-153";
    }

    public static class RAZZE_PROTETTE
    {
      public static final String DETTAGLIO             = "CU-NEMBO-154";
      public static final String MODIFICA              = "CU-NEMBO-155";
      public static final String DETTAGLIO_ISTRUTTORIA = "CU-NEMBO-198";
      public static final String MODIFICA_ISTRUTTORIA  = "CU-NEMBO-199";
    }

    public static class PASCOLI
    {
      public static final String DETTAGLIO = "CU-NEMBO-147";
      public static final String INSERISCI = "CU-NEMBO-151-I";
      public static final String MODIFICA  = "CU-NEMBO-151-M";
    }

    public static class RIDUZIONI_SANZIONI
    {
      public static final String DETTAGLIO = "CU-NEMBO-214-L";
      public static final String INSERISCI = "CU-NEMBO-214-I";
      public static final String MODIFICA  = "CU-NEMBO-214-M";
      public static final String ELIMINA   = "CU-NEMBO-214-E";

    }

    public static class SANZIONI_MISURE_A_PREMIO
    {
      public static final String DETTAGLIO = "CU-NEMBO-225-L";
      public static final String INSERISCI = "CU-NEMBO-225-I";
      public static final String MODIFICA  = "CU-NEMBO-225-M";
      public static final String ELIMINA   = "CU-NEMBO-225-E";

    }

    public static class STAMPE_OGGETTO
    {
      public static final String INSERISCI_STAMPA_OGGETTO                               = "CU-NEMBO-126-I";
      public static final String GENERA_STAMPA_COMUNICAZIONE_INTEGRAZIONE             = "CU-NEMBO-310";
      public static final String GENERA_STAMPA_PRATICA                                  = "CU-NEMBO-128";
      public static final String GENERA_STAMPA_PSL                                      = "CU-NEMBO-134";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO                        = "CU-NEMBO-200";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_1             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-P1";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_2             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-P2";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_1             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-N1";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_2             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-N2";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_1             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-PN1";
      public static final String GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_2             = GENERA_AMMISSIONE_FINANZIAMENTO
          + "-PN2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO            = "CU-NEMBO-209";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-P1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-P2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-N1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-N2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-PN1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO
          + "-PN2";

      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO                     = "CU-NEMBO-242";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_1          = GENERA_VERBALE_ISTRUTTORIA_ACCONTO
          + "-P1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_2          = GENERA_VERBALE_ISTRUTTORIA_ACCONTO
          + "-P2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_1          = GENERA_VERBALE_ISTRUTTORIA_ACCONTO
          + "-N1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_2          = GENERA_VERBALE_ISTRUTTORIA_ACCONTO
          + "-N2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ACCONTO_PARZIALE_1          = GENERA_VERBALE_ISTRUTTORIA_ACCONTO
          + "-PN1";

      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO                    = "CU-NEMBO-243";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_1         = GENERA_VERBALE_ISTRUTTORIA_ANTICIPO
          + "-P1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_2         = GENERA_VERBALE_ISTRUTTORIA_ANTICIPO
          + "-P2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_1         = GENERA_VERBALE_ISTRUTTORIA_ANTICIPO
          + "-N1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_2         = GENERA_VERBALE_ISTRUTTORIA_ANTICIPO
          + "-N2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_PARZIALE_1         = GENERA_VERBALE_ISTRUTTORIA_ANTICIPO
          + "-PN1";

      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO                       = "CU-NEMBO-244";
      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_1            = GENERA_VERBALE_ISTRUTTORIA_SALDO
          + "-P1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_2            = GENERA_VERBALE_ISTRUTTORIA_SALDO
          + "-P2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_1            = GENERA_VERBALE_ISTRUTTORIA_SALDO
          + "-N1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_2            = GENERA_VERBALE_ISTRUTTORIA_SALDO
          + "-N2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_SALDO_PARZIALE_1            = GENERA_VERBALE_ISTRUTTORIA_SALDO
          + "-PN1";

      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO                     = "CU-NEMBO-245";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_1          = GENERA_LETTERA_ISTRUTTORIA_ACCONTO
          + "-P1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_2          = GENERA_LETTERA_ISTRUTTORIA_ACCONTO
          + "-P2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_1          = GENERA_LETTERA_ISTRUTTORIA_ACCONTO
          + "-N1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_2          = GENERA_LETTERA_ISTRUTTORIA_ACCONTO
          + "-N2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ACCONTO_PARZIALE_1          = GENERA_LETTERA_ISTRUTTORIA_ACCONTO
          + "-PN1";

      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO                    = "CU-NEMBO-246";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_1         = GENERA_LETTERA_ISTRUTTORIA_ANTICIPO
          + "-P1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_2         = GENERA_LETTERA_ISTRUTTORIA_ANTICIPO
          + "-P2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_1         = GENERA_LETTERA_ISTRUTTORIA_ANTICIPO
          + "-N1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_2         = GENERA_LETTERA_ISTRUTTORIA_ANTICIPO
          + "-N2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_PARZIALE_1         = GENERA_LETTERA_ISTRUTTORIA_ANTICIPO
          + "-PN1";

      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO                       = "CU-NEMBO-247";
      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_1            = GENERA_LETTERA_ISTRUTTORIA_SALDO
          + "-P1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_2            = GENERA_LETTERA_ISTRUTTORIA_SALDO
          + "-P2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_1            = GENERA_LETTERA_ISTRUTTORIA_SALDO
          + "-N1";
      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_2            = GENERA_LETTERA_ISTRUTTORIA_SALDO
          + "-N2";
      public static final String GENERA_LETTERA_ISTRUTTORIA_SALDO_PARZIALE_1            = GENERA_LETTERA_ISTRUTTORIA_SALDO
          + "-PN1";

      public static final String GENERA_VARIANTE                                        = "CU-NEMBO-267";
      public static final String GENERA_VARIANTE_POSITIVO_1                             = GENERA_VARIANTE
          + "-P1";
      public static final String GENERA_VARIANTE_POSITIVO_2                             = GENERA_VARIANTE
          + "-P2";
      public static final String GENERA_VARIANTE_NEGATIVO_1                             = GENERA_VARIANTE
          + "-N1";
      public static final String GENERA_VARIANTE_NEGATIVO_2                             = GENERA_VARIANTE
          + "-N2";
      public static final String GENERA_VARIANTE_PARZIALE_1                             = GENERA_VARIANTE
          + "-PN1";
      public static final String GENERA_VARIANTE_PARZIALE_2                             = GENERA_VARIANTE
          + "-PN2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE            = "CU-NEMBO-268";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-P1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-P2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-N1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-N2";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_1 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-PN1";
      public static final String GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_2 = GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE
          + "-PN2";

      public static final String GENERA_VERBALE_INTEG_PAGAMENTO                         = "CU-NEMBO-272";
      public static final String GENERA_LETTERA_INTEG_PAGAMENTO                         = "CU-NEMBO-271";

      public static final String GENERA_LETTERA_PROROGA                                 = "CU-NEMBO-278";
      public static final String GENERA_LETTERA_PROROGA_POSITIVO                        = GENERA_LETTERA_PROROGA
          + "-P";
      public static final String GENERA_LETTERA_PROROGA_NEGATIVO                        = GENERA_LETTERA_PROROGA
          + "-N";

      public static final String GENERA_VERBALE_PROROGA                                 = "CU-NEMBO-279";
      public static final String GENERA_VERBALE_PROROGA_POSITIVO                        = GENERA_VERBALE_PROROGA
          + "-P";
      public static final String GENERA_VERBALE_PROROGA_NEGATIVO                        = GENERA_VERBALE_PROROGA
          + "-N";

      public static final String GENERA_LETTERA_REVOCA                                  = "CU-NEMBO-281";
      public static final String GENERA_LETTERA_PREAVVISO_REVOCA                        = "CU-NEMBO-281-P1";
      public static final String GENERA_LETTERA_REVOCA_DEFINITIVA                       = "CU-NEMBO-281-P2";
      public static final String GENERA_LETTERA_REVOCA_NEGATIVA                         = "CU-NEMBO-281-N";

      public static final String GENERA_LETTERA_VOLTURA                                 = "CU-NEMBO-289";
      public static final String GENERA_LETTERA_VOLTURA_POSITIVO                        = "CU-NEMBO-289-P1";
      public static final String GENERA_LETTERA_VOLTURA_POSITIVO_2                      = "CU-NEMBO-289-P2";

      public static final String GENERA_LETTERA_VOLTURA_NEGATIVO                        = "CU-NEMBO-289-N2";
      public static final String GENERA_LETTERA_VOLTURA_PREAVVISO_RIGETTO               = "CU-NEMBO-289-N1";

      public static final String GENERA_VERBALE_VOLTURA                                 = "CU-NEMBO-290";
      public static final String GENERA_VERBALE_VOLTURA_POSITIVO                        = "CU-NEMBO-290-P1";
      public static final String GENERA_VERBALE_VOLTURA_POSITIVO_2                      = "CU-NEMBO-290-P2";
      public static final String GENERA_VERBALE_VOLTURA_NEGATIVO                        = "CU-NEMBO-290-N1";
      public static final String GENERA_VERBALE_VOLTURA_NEGATIVO_2                      = "CU-NEMBO-290-N2";

      public static final String GENERA_LETTERA_ANNULLAMENTO                            = "CU-NEMBO-292";
      public static final String GENERA_LETTERA_ANNULLAMENTO_POSITIVO                   = "CU-NEMBO-292-P";
      public static final String GENERA_LETTERA_ANNULLAMENTO_NEGATIVO                   = "CU-NEMBO-292-N";

      public static final String GENERA_VERBALE_REVOCA                                  = "CU-NEMBO-293";
      public static final String GENERA_VERBALE_REVOCA_POSITIVO                         = "CU-NEMBO-293-P1";
      public static final String GENERA_VERBALE_REVOCA_POSITIVO_2                       = "CU-NEMBO-293-P2";
      public static final String GENERA_VERBALE_REVOCA_NEGATIVO                         = "CU-NEMBO-293-N";

    }
  }

  public static class FLAGS
  {
    public static final String SI = "S";
    public static final String NO = "N";

    public static class FIRMA_GRAFOMETRICA
    {
      public static String DA_FIRMARE_GRAFOMETRICAMENTE = "S";
      public static String DA_NON_FIRMARE               = "N";
      public static String FIRMATA_GRAFOMETRICAMENTE    = "N";
      public static String FIRMATA_SU_CARTA             = "C";
      public static String FIRMATA_ELETTRONICAMENTE     = "E";
      public static String VALIDATA_UFFICIO             = "U";
    }

    public static class ESTRAZIONE_CAMPIONE
    {
      public static class STATO_ESTRAZIONE
      {
        public static final long CARICATA   = 1;
        public static final long ESTRATTA   = 2;
        public static final long REGISTRATA = 3;
        public static final long ANNULLATA  = 4;
      }

      public static class MODALITA_SELEZIONE
      {
        public static final String NON_ESTRATTA              = "N";
        public static final String CASUALE                   = "C";
        public static final String RISCHIO                   = "R";
        public static final String MANUALE                   = "M";
        public static final String DICHIARAZIONI_SOSTITUTIVE = "D";

        public static class DECODIFICA
        {
          public static final String CASUALE     = "Casuale";
          public static final String RISCHIO     = "Rischio";
          public static final String MANUALE     = "Manuale per verifica impegni OD";
          public static final String SCONOSCIUTA = "<span class=\"text-danger\">Decodifica non riconosciuta</span>";
        }
      }
    }
  }

  public static class COD_ATTORE
  {
    public static String INTERMEDIARIO = "INTERMEDIARIO_CAA";
  }

  public static final class PORTAL
  {
    public static final String NEMBOPRATICHE_LOGIN_PORTAL = "NEMBOPRATICHE_LOGIN_PORTAL";
    public static final String PUBBLICA_AMMINISTRAZIONE   = "RUPAR";
    public static final String PRIVATI_SISPIE             = "SISPIE";
    public static final String SPID                       = "SPID";

  }
  
  public static class NEMBOPRATICHE
  {
    public static String WEB_CONTEXT = "nembopratiche";
    public static int    ID          = 46;
  }
  
  public static class TIPO_PROCEDIMENTO_AGRICOLO
  {
	  public static final int AVVERSITA_ATMOSFERICHE = 53;
	  public static final int AIUTI_DI_STATO = 55;
	  public static final int CONSORZI_IRRIGUI = 56;
  }

  public static class LOGGIN
  {
    public static String LOGGER_NAME = "nembopratiche";

    public static class LOGGER
    {
      public static Logger PRESENTATION = Logger
          .getLogger(LOGGIN.LOGGER_NAME + ".presentation");
    }
  }

  public static final class URL
  {
    public static final class AGRIWELLWEB
    {
      public static final String BASE_URL_PRIVATI                  = "/agriwellweb/secure/sisp/accedi.do";
      public static final String BASE_URL_PUBBLICA_AMMINISTRAZIONE = "/agriwellweb/secure/wrup/accedi.do";
      public static final String BASE_URL_SPID                     = "/agriwellweb/secure/spid/accedi.do";
    }

    public static final class SHIBBOLETH
    {
      public static final String RELATIVE_BASE_URL_PA = "/liv1/Shibboleth.sso/Login?entityID=IDENTITY_PROVIDER_2_PA_SECURE.RUPARPIEMONTE.IT&target=";
    }
  }


  public static class SQL
  {
    public static class SEQUENCE
    {
      public static final String NEMBO_T_PIANO_FINANZIARIO        = "SEQ_NEMBO_T_PIANO_FINANZIARIO";
      public static final String NEMBO_T_ITER_PIANO_FINANZIARIO   = "SEQ_NEMBO_T_ITER_PIANO_FINANZI";
      public static final String NEMBO_T_STORICO_AMBITI_TEMATICI  = "SEQ_NEMBO_T_STORICO_AMBITI_TEM";
      public static final String NEMBO_T_ITER_AMBITI_TEMATICI     = "SEQ_NEMBO_T_ITER_STO_AMBITI_TE";
      public static final String NEMBO_T_MOD_CONCERTAZION_GAL     = "SEQ_NEMBO_T_MOD_CONCERTAZI_GAL";
      public static final String NEMBO_D_LIVELLO                  = "SEQ_NEMBO_D_LIVELLO";
      public static final String NEMBO_W_DETT_INTERV_PROC_OGG     = "SEQ_NEMBO_W_DETT_INTE_PROC_OGG";
      public static final String NEMBO_T_INTERVENTO               = "SEQ_NEMBO_T_INTERVENTO";
      public static final String NEMBO_T_FILE_ALLEGATI_INTERVENTO = "SEQ_NEMBO_T_FILE_ALLEGATI_INTE";
      public static final String NEMBO_T_DATI_COMU_ELEM_QUA       = "SEQ_NEMBO_T_DATI_COMU_ELEM_QUA";
    }

    public static class RESULT_CODE
    {
      public static final int ERRORE_CRITICO       = 1;
      public static final int ERRORE_GRAVE         = 2;
      public static final int ERRORE_NON_BLOCCANTE = 3;
      public static final int NESSUN_ERRORE        = 0;
    }

    public static class MESSAGE
    {
      public static final String PLSQL_ERRORE_CRITICO = "Si &egrave; verificato un errore di sistema. Contattare l'assistenza comunicando il seguente messaggio: ";
      public static final String PLSQL_ERRORE_GRAVE   = "Si &egrave; verificato il seguente errore: ";
    }

  }

  public static class GENERIC
  {
    public static final int ID_TIPO_RUOLO_TECNICO_LIQUIDATORE = 2; 
    public static String    ID_REGIONE_BASILICATA            = "17";
    public static String    ISTAT_PROVINCIA_ESTERA            = "999";
    public static String    SESSION_VAR_FILTER_AZIENDA        = "filtroAziende";                   // TODO:
                                                                                                   // in
                                                                                                   // PSR
                                                                                                   // è
                                                                                                   // stato
                                                                                                   // rimosso
    public static String    SESSION_VAR_FILTER                = "filtro";
    public static String    SESSION_NO_ELENCO_AZIENDA         = "sessionNoElencoAzienda";
    // public static String SESSION_VAR_AZIENDE_COLONNE_NASCOSTE =
    // "colonneNascoste"; //NON USARE (USARE SESSION_VAR_COLONNE_NASCOSTE)
    // TODO:REMOVE
    public static String    SESSION_VAR_COLONNE_NASCOSTE      = "colonneNascoste";
    public static String    SESSION_VAR_NUMERO_PAGINA         = "sessionMapNumeroPagina";
    public static String    SESSION_VAR_ORDINAMENTO           = "sessionMapOrdinamento";           // NON
                                                                                                   // USARE
                                                                                                   // (USARE
                                                                                                   // SESSION_VAR_COLONNA_ORDINAMENTO)
    public static String    SESSION_VAR_PAGE_SIZE             = "sessionMapPageSize";
    public static String    SESSION_VAR_COLONNA_ORDINAMENTO   = "sessionMapNomeColonnaOrdinamento";
    public static String    SESSION_VAR_TIPO_ORDINAMENTO      = "sessionMapTipoOrdinamento";
    public static String    SESSION_VAR_RIGHE_VISIBILI        = "sessionMapRigheVisibili";

    public static long      FONTE_CONTROLLO_PREMIO            = 4;
    public static String    SESSION_VAR_HELP_IS_ACTIVE        = "HELP_IS_ACTIVE";
  }

  public static class GENERIC_ERRORS
  {
    public static final String ERRORE_DI_SISTEMA                         = "Si è verificato un errore di sistema";
    public static final String DATI_NON_TROVATI                          = "Dati non trovati";
    public static final String CUAA_ERRATO                               = "Il CUAA inserito non è valido";
    public static final String AZIENDE_NON_TROVATE                       = "Non è stata trovata nessuna azienda valida ";
    public static final String FUNZIONALITA_NON_CONFIGURTA_CORRETTAMENTE = "Funzionalità non configurata correttamente per il quadro/oggetto";
    public static final String PROCEDIMENTO_TRASMESSO                    = "Impossibile procedere, è già presente a sistema un procedimento trasmesso per l'azienda scelta";
  }

  public static class PAGE
  {
    public static class DO
    {
      public static final String LOGIN = "/" + NEMBOPRATICHE.WEB_CONTEXT
          + "/login/seleziona_ruolo.do";
    }

    public static class JSP
    {
      public static class ERROR
      {
        public static final String SESSION_EXPIRED = "/WEB-INF/jsp/errore/sessioneScaduta.jsp";
        public static final String INTERNAL_ERROR  = "/WEB-INF/jsp/errore/erroreInterno.jsp";
      }
    }
  }

  public static class LIVELLO
  {
    public static class TIPOLOGIA
    {
      public static class ID
      {
        public static final int MISURA      = 1;
        public static final int SOTTOMISURA = 2;
        public static final int OPERAZIONE  = 3;
      }
    }

    public static final String CODICE_LIVELLO_19_2  = "19.2";
    public static final String CODICE_LIVELLO_8_1_1 = "8.1.1";
  }

  public static class MACROCDU
  {
    public static final String VISUALIZZA_ISTANZE   = "VISUALIZZA_ISTANZE";
    public static final String GESTIONE_ISTANZA     = "GESTIONE_ISTANZA";
    public static final String GESTIONE_ISTRUTTORIE = "GESTIONE_ISTRUTTORIE";
    public static final String DATI_SISTEMA         = "DATI_SISTEMA";
    public static final String SUPER_USER           = "SUPER_USER";
    public static final String ESTRAZIONE_CAMPIONE  = "ESTRAZIONE_CAMPIONE";
    public static final String ESTRAZIONE_PAGAMENTI = "ESTRAZIONE_PAGAMENTI";
  }

  public static class PAGINATION
  {
    public static final String CONTENUTO                    = "content";
    public static final String NUOVA_PAGINA                 = "newPage";
    public static final String NUMERO_PAGINE                = "numeroPagine";
    public static final String TOTALE_ELEMENTI              = "totaleElementi";
    public static final String LISTA_COUNT_RISULTATI_PAGINA = "listRisultatiPagina";
    public static final String COUNT_ELEMENTI_PAGINA        = "countElementiPagina";
    public static final String ORDER_PROPERTY               = "orderProperty";
    public static final String ORDER_TYPE                   = "orderType";
    public static final String VISIBLE_COLUMNS              = "visibleColumns";

    public static final String FISRT_PAGE_ROW               = "firstPageRow";
    public static final String LAST_PAGE_ROW                = "lastPageRow";

    public static final String IS_VALID                     = "isValidJSON";

    public static class AZIONI
    {
      public static final String AGGIUNGI  = "icon-circle-arrow-right icon-large";
      public static final String DETTAGLIO = "icon-list icon-large";
    }
  }

  public static class ANAGRAFE
  {
    public static final String ID_FORMA_GIURIDICA_DITTA_INDIVIDUALE                 = "1";
    public static final String ID_FORMA_GIURIDICA_PERS_FISICA_NON_ESERC_ATT_IMPRESA = "52";
  }

  public static class GAL
  {
    public static final String TIPO_ENTE_PUBBLICO       = "EP";
    public static final String TIPO_ENTE_PRIVATO        = "SP";
    public static final String CODICE_PIANO_FINANZIARIO = "G";
  }

  public static class INTERVENTI
  {
    public static class LOCALIZZAZIONE
    {
      public static int PARTICELLE_AZIENDALI                   = 3;
      public static int PARTICELLE_CATASTO                     = 4;
      public static int PARTICELLE_AZIENDALI_IMPIANTI_BOSCHIVI = 8;
    }

    public static final String TIPO_OPERAZIONE_INSERIMENTO               = "I";
    public static final String TIPO_OPERAZIONE_ELIMINAZIONE              = "D";
    public static final String TIPO_OPERAZIONE_MODIFICA                  = "U";
    public static final String TIPO_OPERAZIONE_MODIFICA_QUADRO_ECONOMICO = "E";
  }

  public static class MISURE_PREMIO
  {
    public static final String TIPO_IMPEGNO_BASE       = "B";
    public static final String TIPO_IMPEGNO_AGGIUNTIVO = "A";

    public static class IMPEGNO
    {
      public static final String FLAG_OBBLIGATORIO = "O";
      public static final String FLAG_FACOLTATIVO  = "F";
    }
  }

  public static class QUADRO
  {
    public static class CODICE
    {
      public static final String DATI_IDENTIFICATIVI                   = "DID";
      public static final String DICHIARAZIONI                         = "DICH";
      public static final String SEGNALAZIONE_DANNI                     = "DANNB";
      public static final String IMPEGNI                               = "IMPEG";
      public static final String ALLEGATI                              = "ALLEG";
      public static final String CONTI_CORRENTI                        = "CC";
      public static final String DATA_FINE_LAVORI                      = "DTFIN";
      public static final String INTERVENTI                            = "INTER";
      public static final String INTERVENTI_INFRASTRUTTURE             = "INTCI";
      public static final String INTERVENTI_CON_PARTECIPANTI           = "INTEP";
      public static final String QUADRO_ECONOMICO                      = "QECON";
      public static final String CONTROLLI_AMMINISTRATIVI              = "CTRAM";
      public static final String CONTROLLI_AMMINISTRATIVI_PREMIO       = "CTAMP";
      public static final String CONTROLLI_IN_LOCO_MISURE_INVESTIMENTO = "CTRLC";
      public static final String CONTROLLI_EX_POST                     = "CTREX";
      public static final String DATI_ANTICIPO                         = "DTANT";
      public static final String RENDICONTAZIONE_SPESE                 = "RENSP";
      public static final String RENDICONTAZIONE_SALDO                 = "RESFN";
      public static final String RIDUZIONE_SANZIONI                    = "RISAN";
      public static final String ACCERTAMENTO_ACCONTO                  = "ACTSP";
      public static final String VOLTURA                               = "VOLT";
      public static final String INVESTIMENTI                          = "INVES";
      public static final String SUPERFICIE_COLTURA                    = "SUPCO";
      public static final String ALLEVAMENTI                           = "ALLEV";
      public static final String SCORTE                                = "SCORT";
      public static final String DANNI                                 = "DANNI";
      public static final String COLTURE_AZIENDALI                     = "COLAZ";
      public static final String DOCUMENTI_RICHIESTI                   = "DOCRI";

    }
  }

  public static class AZIONE
  {
    public static class CODICE
    {
      public static final String INSERISCI    = "INSERISCI";
      public static final String MODIFICA     = "MODIFICA";
      public static final String MODIFICA_SAL = "MODIFICA_SAL";
      public static final String ELIMINA      = "ELIMINA";
      public static final String LOCALIZZA    = "LOCALIZZA";
      public static final String ABILITA      = "ABILITA";
    }
  }

  public static class CODICE_INFO
  {
    public static final String PEC_BANCA        = "PEC_BANCA";
    public static final String MAIL_FUNZIONARIO = "MAIL_FUNZIONARIO";
  }

  public static class PARAMETRO
  {
    public static final String DOQUIAGRI_CARTELLA                             = "DOQUIAGRI_CARTELLA";
    public static final String DOQUIAGRI_CLASS_REG                            = "DOQUIAGRI_CLASS_REG";
    public static final String DOQUIAGRI_FASCICOLA                            = "DOQUIAGRI_FASCICOLA";
    public static final String GAL_MAX_POPOL_ELEGG                            = "GAL_MAX_POPOL_ELEGG";
    public static final String GAL_BUDGET_PROCAPITE                           = "GAL_BUDGET_PROCAPITE";
    public static final String MASSIMA_PERCENTUALE_ANTICIPO                   = "ANTICIPO_MAX_PERC";
    public static final String URL_DOMANDA_GRAFICA                            = "URL_DOMANDA_GRAFICA";
    public static final String DATA_RIFERIMENTO_GRAFICA                       = "DATARIFEGRAF";

    public static final String MESSAGGISTICA_MINUTI_VISUALIZZAZIONE_TESTATA   = "MESE";
    public static final String MESSAGGISTICA_MINUTI_ATTESA_VERIFICA_LOGOUT    = "MESL";
    public static final String MESSAGGISTICA_MINUTI_ATTESA_VERIFICA_REFRESH   = "MESR";
    public static final String MAIL_ASSISTENZA_BPOL                           = "MAIL_BPOL";
    public static final String DOQUIAGRI_FLAG_PROT                            = "DOQUIAGRI_FLAG_PROT";
    public static final String BANDO_MIS_16_AZ_1                              = "BANDO_MIS_16_AZ_1";
    public static final String CALL_SIGOP_AMF                                 = "CALL_SIGOP_AMF";
    public static final String HELP_DEFAULT_OPEN                              = "HELP_DEFAULT_OPEN";
    public static final String DOQUIAGRI_FLAG_PEC                             = "DOQUIAGRI_FLAG_PEC";
    public static final String DOQUIAGRI_USR_PSW_SC                           = "DOQUIAGRI_USR_PSW_SC"; // user#password

    public static final String MITTENTE_RICEVUTA                              = "MITTENTE_RICEVUTA";

    public static final String OGGETTO_RICEVUTA                               = "OGGETTO_RICEVUTA";
    public static final String TESTO_RICEVUTA                                 = "TESTO_RICEVUTA";
    public static final String OGGETTO_RICEVUTA_PAG                           = "OGGETTO_RICEVUTA_PAG";
    public static final String TESTO_RICEVUTA_PAG                             = "TESTO_RICEVUTA_PAG";

    public static final String INVIO_PEC_NO_ISTANZE                           = "INVIO_PEC_NO_ISTANZE";
    public static final String DOQUIAGRI_ID_TIPO_DOCUMENTO_LISTA_LIQUIDAZIONE = "ID_TIPO_DOC_LIST_LIQ";
    public static final String DOQUIAGRI_CLASS_REG_LISTE_LIQUIDAZIONE         = "DOQUIAGRI_CLASS_LIQ";
    public static final String DOQUIAGRI_FASCICOLA_LISTE_LIQUIDAZIONE         = "DOQUIAGRI_FASC_LIQ";
    public static final String DOQUIAGRI_LISTE_LIQUIDAZIONE_X_SINISTRA        = "LISTA_LIQ_X_SINISTRA";
    public static final String DOQUIAGRI_LISTE_LIQUIDAZIONE_X_DESTRA          = "LISTA_LIQ_X_DESTRA";
    public static final String DOQUIAGRI_LISTE_LIQUIDAZIONE_Y_BASSO           = "LISTA_LIQ_Y_BASSO";
    public static final String DOQUIAGRI_LISTE_LIQUIDAZIONE_Y_ALTO            = "LISTA_LIQ_Y_ALTO";
    public static final String ESTRAI_PAGAMENTI                               = "ESTRAI_PAGAMENTI";
    
    public static final String EMAIL_COM_INTEGRAZ                = "EMAIL_COM_INTEGRAZ";
    public static final String FIRMA_COM_INTEGRAZIONE                              = "FIRMA_COM_INTEGRAZ";
    public static final String FIRMA_COM_INTEG_SX                              = "FIRMA_COM_INTEG_SX";
    public static final String HEAD_SX_COM_INTEGRAZ                              = "HEAD_SX_COM_INTEGRAZ";
    public static final String HEAD_DX_COM_INTEGRAZ                              = "HEAD_DX_COM_INTEGRAZ";
    public static final String INTRO_COM_INTEGRAZ                              = "INTRO_COM_INTEGRAZ";
    public static final String FINE_COM_INTEGRAZ                              = "FINE_COM_INTEGRAZ";
    public static final String MITTENTE_DENOM                              = "MITTENTE_DENOM";
    public static final String MITTENTE_PSW                           		  = "MITTENTE_PSW";
    public static final String OGGETTO_NO_ISTANZA                             = "OGGETTO_NO_ISTANZA";
    public static final String TESTO_NO_ISTANZA                               = "TESTO_NO_ISTANZA";
    public static final String INVIO_PEC_ISTANZE			                           = "INVIO_PEC_ISTANZE";
     
      
  }

  public static class QUADRO_DINAMICO
  {
    public static class TIPO_OPERAZIONE
    {
      public static final String INSERISCI = "I";
      public static final String MODIFICA  = "M";
      public static final String ELIMINA   = "E";
    }

    public static class TIPO_DATO
    {
      public static final String STR = "STR";
      public static final String MST = "MST";
      public static final String DTA = "DTA";
      public static final String NUM = "NUM";
      public static final String EUR = "EUR";
      public static final String PCT = "PCT";
      public static final String CMB = "CMB";
      public static final String RBT = "RBT";
      public static final String CBT = "CBT";
      public static final String LST = "LST";
      public static final String TXT = "TXT"; // Titolo all'interno della
                                              // tabella
      public static final String DTQ = "DTQ";
      public static final String DTF = "DTF";
      public static final String NU0 = "NU0";
      public static final String ANN = "ANN"; // Anno
      public static final String NUQ = "NUQ";
      public static final String TIT = "TIT"; // Titolo sezione espandibile
      public static final String STM = "STM";
      public static final String HTM = "HTM"; // Html puro, no escape
    }
  }

  public static class MAX
  {
    public static final BigDecimal PERCENTUALE                      = new BigDecimal(
        "100.00").setScale(2);
    public static final BigDecimal IMPORTO_INTERVENTO               = new BigDecimal(
        "9999999999.99").setScale(2);
    public static final BigDecimal VALORE_INTERVENTO                = new BigDecimal(
        "999999999999999999.9999").setScale(4);
    public static final BigDecimal SUPERFICIE_IMPEGNO_INTERVENTO    = new BigDecimal(
        "999999.9999").setScale(4);
    public static final BigDecimal IMPORTO_AMMESSO_QUADRO_ECONOMICO = new BigDecimal(
        "9999999999.99").setScale(2);
  }

  public static class MIN
  {
    public static final BigDecimal SUPERFICIE_IMPEGNO_INTERVENTO = new BigDecimal(
        "0.0001");
    public static final BigDecimal PERCENTUALE_NON_ZERO          = new BigDecimal(
        "0.01");
    public static final BigDecimal IMPORTO_NON_ZERO              = PERCENTUALE_NON_ZERO;
  }

  public static class FIRMA_GRAFOMETRICA
  {
    public static class TESTO
    {
      public static final String FIRMA  = "Firma del richiedente";
      public static final String DATA   = "Data Firma";
      public static final String A_CAPO = "&acapo&";

    }
  }

  public static class ELABORAZIONE_STAMPA
  {
    public static class TESTO
    {
      public static final String PROTOCOLLO = "SPAZIO RISERVATO AL PROTOCOLLO";
    }
  }

  public static class SIAPCOMMWS
  {
    public static class DATI_PROTOCOLLO
    {
      public static class TIPO_PROTOCOLLO
      {
        public static final String INGRESSO = "I";
        public static final String USCITA   = "U";
      }
    }
  }

  public static class OPERAZIONE_PREMIO
  {
    public static final String OP_10_1_8 = "10.1.8";
  }

  public static final class HTML
  {
    public static final String CHECKED = "checked=\"checked\"";
  }

  public static final class TIPO_PAGAMENTO_SIGOP
  {
    public static final String SALDO = "SALDO";
  }

  public static final class ERRORI
  {
    public static final int ELIMINAZIONE_DANNI_CON_INTERVENTI            = -1;
    public static final int ELIMINAZIONE_SCORTE_CON_DANNI_CON_INTERVENTI = -2;

    public static class EVENTI_CALAMITOSI
    {
      public static int BANDI_ESISTENTI = -1;
    }
  }

  public static final class DANNI
  {
    public static final int ALLEVAMENTO                = 1;
    public static final int FABBRICATO                 = 2;
    public static final int MACCHINA_AGRICOLA          = 3;
    public static final int SCORTA                     = 4;
    public static final int PIANTAGIONI_ARBOREE        = 5;
    public static final int TERRENI_RIPRISTINABILI     = 6;
    public static final int TERRENI_NON_RIPRISTINABILI = 7;
    public static final int ALTRO                      = 8;
    public static final int ATTREZZATURA               = 9;
    public static final int SCORTE_MORTE               = 10;
    public static final int ALTRE_PIANTAGIONI          = 11;
  }
  
  public static final class PUNTEGGI
  {
	  
	  public static final String CODICE_INTERVENTI_PREVENZIONE = "PREV";
	  
	  public static final String CODICE_LUPI01 = "LUPI01";
	  public static final String CODICE_LUPI02 = "LUPI02";
	  public static final String CODICE_LUPI03 = "LUPI03";
	  
  }
}
