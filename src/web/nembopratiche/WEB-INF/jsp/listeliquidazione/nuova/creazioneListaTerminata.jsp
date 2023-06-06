<%@page import="it.csi.nembo.nembopratiche.util.NemboConstants"%>
<%@taglib prefix="p" uri="/WEB-INF/nembopratiche.tld"%>
<%@taglib uri="/WEB-INF/remincl.tld" prefix="r"%>
<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/head.html" />
<body>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/portal_header-small.html" />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/${sessionScope.headerProcedimento}" />
	<p:utente />
	<div class="container-fluid">
		<div class="row">
			<div class="moduletable">
				<ul class="breadcrumb">
					<li><a href="../index.do">Home</a> <span class="divider">/</span></li>
					<li><a href="../cunembo227/index.do">Liste di liquidazione</a><span class="divider">/</span></li>
					<li><a href="../cunembo226/index.do">Nuova lista di liquidazione</a><span class="divider">/</span></li>
					<li class="active">Fine</li>
				</ul>
			</div>
		</div>
	</div>
	<p:messaggistica />
	<div class="container-fluid" id="content">
		<h3>Successo</h3>
		<div id="success-box" class="alert alert-success">
			<strong>Generazione della lista di liquidazione terminata correttamente.</strong>
		</div>
		<span class="please_wait" style="vertical-align: middle"></span><strong>Attendere la creazione del documento di stampa</strong> <br />
		<div class="col-sm-12">
			<a href="../cunembo227/index.do" class="btn  btn-primary pull-right">ritorna all'elenco liste</a>
		</div>
	</div>
	<br />
	<br />
	<r:include resourceProvider="portal" url="/staticresources/assets/application/nembopratiche/include/footer.html" />
	<script type="text/javascript">
    var time = 2000;
    function checkStampa()
    {
      $.getJSON("json/stampa.json", function(data)
      {
        if (data['finished'])
        {
          window.location.href = '../cunembo227/index.do';
        }
        else
        {
          if (time < 64000)
          {
            time += 2000;
          }
          setTimeout(checkStampa, time);
        }
      }).fail(function()
      {
        if (time < 64000)
        {
          time += 2000;
        }
        if (time > 6000)
        {
          alert('Si è verificato un problema nel reperimento dello stato della genenerazione della stampa');
          window.location.href = '../cunembo227/index.do';
        }
        else
        {
          setTimeout(checkStampa, time);
        }
      });
    }
    $(document).ready(checkStampa);
    setTimeout(checkStampa, time);
  </script>
	<r:include resourceProvider="portal" url="/staticresources/assets/global/include/footerSP07.html" />