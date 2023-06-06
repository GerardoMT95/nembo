package it.csi.nembo.nembopratiche.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.nembo.nembopratiche.dto.procedimentooggetto.stampa.StampaOggettoDTO;
import it.csi.nembo.nembopratiche.util.stampa.AmmissioneFinanziamento;
import it.csi.nembo.nembopratiche.util.stampa.AmmissioneFinanziamentoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.AmmissioneFinanziamentoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.ComunicazioneIntegrazione;
import it.csi.nembo.nembopratiche.util.stampa.DomandaPSL;
import it.csi.nembo.nembopratiche.util.stampa.Pratica;
import it.csi.nembo.nembopratiche.util.stampa.PreavvisoDiRigettoParziale;
import it.csi.nembo.nembopratiche.util.stampa.PreavvisoDiRigettoTotale;
import it.csi.nembo.nembopratiche.util.stampa.Stampa;
import it.csi.nembo.nembopratiche.util.stampa.StampaListaLiquidazione;
import it.csi.nembo.nembopratiche.util.stampa.VerbaleIstruttoriaDomandaSostegno;
import it.csi.nembo.nembopratiche.util.stampa.VerbaleIstruttoriaDomandaSostegnoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.VerbaleIstruttoriaDomandaSostegnoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.acconto.LetteraIstruttoriaAcconto;
import it.csi.nembo.nembopratiche.util.stampa.acconto.LetteraIstruttoriaAccontoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.acconto.LetteraIstruttoriaAccontoParziale;
import it.csi.nembo.nembopratiche.util.stampa.acconto.LetteraIstruttoriaAccontoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.acconto.LetteraIstruttoriaAccontoPreavvisoRigetto;
import it.csi.nembo.nembopratiche.util.stampa.acconto.VerbaleIstruttoriaAcconto;
import it.csi.nembo.nembopratiche.util.stampa.acconto.VerbaleIstruttoriaAccontoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.acconto.VerbaleIstruttoriaAccontoParziale;
import it.csi.nembo.nembopratiche.util.stampa.acconto.VerbaleIstruttoriaAccontoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.LetteraIstruttoriaAnticipo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.LetteraIstruttoriaAnticipoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.LetteraIstruttoriaAnticipoParziale;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.LetteraIstruttoriaAnticipoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.LetteraIstruttoriaAnticipoPreavvisoRigetto;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.VerbaleIstruttoriaAnticipo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.VerbaleIstruttoriaAnticipoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.VerbaleIstruttoriaAnticipoParziale;
import it.csi.nembo.nembopratiche.util.stampa.anticipo.VerbaleIstruttoriaAnticipoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.LetteraIstruttoriaSaldo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.LetteraIstruttoriaSaldoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.LetteraIstruttoriaSaldoParziale;
import it.csi.nembo.nembopratiche.util.stampa.saldo.LetteraIstruttoriaSaldoPositivo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.LetteraIstruttoriaSaldoPreavvisoRigetto;
import it.csi.nembo.nembopratiche.util.stampa.saldo.VerbaleIstruttoriaSaldo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.VerbaleIstruttoriaSaldoNegativo;
import it.csi.nembo.nembopratiche.util.stampa.saldo.VerbaleIstruttoriaSaldoParziale;
import it.csi.nembo.nembopratiche.util.stampa.saldo.VerbaleIstruttoriaSaldoPositivo;

/**
 * Classe astratta per le funzioni di utilità sulle stringhe. La classe è
 * abstract perchè non deve essere usata direttamente ma solo dalla sua
 * implementazione nella costante Utils.STRING
 * 
 * @author Stefano Einaudi (Matr. 70399)
 * 
 */
public abstract class StampaUtils
{
  public static Map<String, Stampa> MAP_STAMPE = new HashMap<String, Stampa>();
  static
  {
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_STAMPA_PRATICA,
        new Pratica());
    MAP_STAMPE.put(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_STAMPA_PSL,
        new DomandaPSL());
    MAP_STAMPE.put(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_STAMPA_COMUNICAZIONE_INTEGRAZIONE, new ComunicazioneIntegrazione());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO,
        new AmmissioneFinanziamento());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_1,
        new AmmissioneFinanziamentoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_2,
        new AmmissioneFinanziamentoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_1,
        new PreavvisoDiRigettoTotale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_2,
        new AmmissioneFinanziamentoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_1,
        new PreavvisoDiRigettoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_2,
        new PreavvisoDiRigettoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_AMMISSIONE_FINANZIAMENTO_PARZIALE_2));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO,
        new VerbaleIstruttoriaDomandaSostegno());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_1,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_2,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_1,
        new VerbaleIstruttoriaDomandaSostegnoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_2,
        new VerbaleIstruttoriaDomandaSostegnoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_1,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_2,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_SOSTEGNO_PARZIALE_2));
    MAP_STAMPE.put(NemboConstants.USECASE.LISTE_LIQUIDAZIONE.STAMPA,
        new StampaListaLiquidazione());

    // Gestione bandi misure a premio

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO,
        new VerbaleIstruttoriaAcconto());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_1,
        new VerbaleIstruttoriaAccontoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_1,
        new VerbaleIstruttoriaAccontoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_2,
        new VerbaleIstruttoriaAccontoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_2,
        new VerbaleIstruttoriaAccontoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_PARZIALE_1,
        new VerbaleIstruttoriaAccontoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ACCONTO_PARZIALE_1));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO,
        new VerbaleIstruttoriaAnticipo());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_1,
        new VerbaleIstruttoriaAnticipoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_1,
        new VerbaleIstruttoriaAnticipoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_2,
        new VerbaleIstruttoriaAnticipoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_2,
        new VerbaleIstruttoriaAnticipoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_PARZIALE_1,
        new VerbaleIstruttoriaAnticipoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_ANTICIPO_PARZIALE_1));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO,
        new VerbaleIstruttoriaSaldo());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_1,
        new VerbaleIstruttoriaSaldoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_1,
        new VerbaleIstruttoriaSaldoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_2,
        new VerbaleIstruttoriaSaldoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_2,
        new VerbaleIstruttoriaSaldoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_PARZIALE_1,
        new VerbaleIstruttoriaSaldoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_SALDO_PARZIALE_1));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO,
        new LetteraIstruttoriaAcconto());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_1,
        new LetteraIstruttoriaAccontoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_1,
        new LetteraIstruttoriaAccontoPreavvisoRigetto(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_2,
        new LetteraIstruttoriaAccontoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_2,
        new LetteraIstruttoriaAccontoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_PARZIALE_1,
        new LetteraIstruttoriaAccontoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ACCONTO_PARZIALE_1));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO,
        new LetteraIstruttoriaAnticipo());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_1,
        new LetteraIstruttoriaAnticipoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_1,
        new LetteraIstruttoriaAnticipoPreavvisoRigetto(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_2,
        new LetteraIstruttoriaAnticipoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_2,
        new LetteraIstruttoriaAnticipoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_PARZIALE_1,
        new LetteraIstruttoriaAnticipoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_ANTICIPO_PARZIALE_1));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO,
        new LetteraIstruttoriaSaldo());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_1,
        new LetteraIstruttoriaSaldoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_1,
        new LetteraIstruttoriaSaldoPreavvisoRigetto(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_2,
        new LetteraIstruttoriaSaldoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_2,
        new LetteraIstruttoriaSaldoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_PARZIALE_1,
        new LetteraIstruttoriaSaldoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_LETTERA_ISTRUTTORIA_SALDO_PARZIALE_1));

    /* STAMPE VARIANTE */
    MAP_STAMPE.put(NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE,
        new AmmissioneFinanziamento());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_POSITIVO_1,
        new AmmissioneFinanziamentoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_POSITIVO_2,
        new AmmissioneFinanziamentoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_NEGATIVO_1,
        new PreavvisoDiRigettoTotale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_NEGATIVO_2,
        new AmmissioneFinanziamentoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_PARZIALE_1,
        new PreavvisoDiRigettoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_PARZIALE_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_PARZIALE_2,
        new PreavvisoDiRigettoParziale(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VARIANTE_PARZIALE_2));

    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE,
        new VerbaleIstruttoriaDomandaSostegno());
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_1,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_2,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_POSITIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_1,
        new VerbaleIstruttoriaDomandaSostegnoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_2,
        new VerbaleIstruttoriaDomandaSostegnoNegativo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_NEGATIVO_2));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_1,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_1));
    MAP_STAMPE.put(
        NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_2,
        new VerbaleIstruttoriaDomandaSostegnoPositivo(
            NemboConstants.USECASE.STAMPE_OGGETTO.GENERA_VERBALE_ISTRUTTORIA_DOMANDA_VARIANTE_PARZIALE_2));

   

  }

  public Stampa getStampaFromCdU(String cuName)
  {
    return MAP_STAMPE.get(cuName);
  }

  public boolean hasInvioPec(String flagInvioPec, List<StampaOggettoDTO> stampe)
  {
    if (NemboConstants.FLAGS.SI.equals(flagInvioPec))
    {
      for (StampaOggettoDTO item : stampe)
      {
        if (NemboConstants.FLAGS.SI.equals(item.getFlagInviaMail()))
        {
          return true;
        }
      }
    }
    return false;
  }

}
