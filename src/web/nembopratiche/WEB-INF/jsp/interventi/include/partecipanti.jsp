<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<m:select list="${partecipanti}" name="id_partecipante_${idIntervento}" selectedValue="${intervento.idPartecipante}" id="id_partecipante_${idIntervento}" preferRequestValues="${preferRequest}" disabled="${partecipanti==null}"/>