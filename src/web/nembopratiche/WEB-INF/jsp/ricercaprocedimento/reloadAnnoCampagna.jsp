<%@taglib prefix="m" uri="/WEB-INF/mybootstrap.tld"%>
	
<m:checkbox-list 
	name="chk_anno_campagna" onclick="reloadPopupBandi();" id="chk_anno_campagna" list="${all_bandi}"
	valueProperty="annoCampagnaValNVL" textProperty="annoCampagnaNVL" 
	inline="true" checkedProperty="defaultChecked"></m:checkbox-list>	
