<%@ page import="bvdj.Dj" %>



<div class="fieldcontain ${hasErrors(bean: djInstance, field: 'bands', 'error')} ">
	<label for="bands">
		<g:message code="dj.bands.label" default="Bands" />
		
	</label>
	<g:textField name="bands" value="${djInstance?.bands}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: djInstance, field: 'isdj', 'error')} ">
	<label for="isdj">
		<g:message code="dj.isdj.label" default="Isdj" />
		
	</label>
	<g:checkBox name="isdj" value="${djInstance?.isdj}" />
</div>

<div class="fieldcontain ${hasErrors(bean: djInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dj.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${djInstance?.name}"/>
</div>

