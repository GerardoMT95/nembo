<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
	
<m:checkbox-list 
	name="chk_anno_evento" onclick="reloadPopupEventi();" id="chk_anno_evento" list="${all_eventi}"
	valueProperty="annoEvento" textProperty="annoEvento" 
	inline="true" checkedProperty="defaultChecked">
</m:checkbox-list>	
