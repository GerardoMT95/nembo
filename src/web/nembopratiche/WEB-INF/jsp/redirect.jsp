<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${warning!=null}">
<span class="warning_big" style="vertical-align: middle; float:left"></span><table><tr style="vertical-align:center;height:128px"><td>${warning}</td></tr></table>
<br class="clear"/>
</c:if>
<c:if test="${error!=null}">
<span class="fail_big" style="vertical-align: middle; float:left"></span><table><tr style="vertical-align:center;height:128px"><td>${error}</td></tr></table>
<br class="clear"/>
</c:if>
<c:if test="${success!=null}">
<span class="success_big" style="vertical-align: middle; float:left"></span><table><tr style="vertical-align:center;height:128px"><td>${success}</td></tr></table>
<br class="clear"/>
</c:if>
<script type="text/javascript">
window.location.href="${url}";
</script>