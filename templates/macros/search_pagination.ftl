<#function moderationParams o>
	<#assign open = o/>
	<#if open?length == 0>
		<#if openModeration>
			<#assign open = "1"/>
		<#else>
			<#assign open = "0"/>
		</#if>
	</#if>
	
	<#assign baseUrl = "openModeration="+ open/>
	
	<#if (forum?default("")?length > 0)><#assign baseUrl = baseUrl +"&search_forum="+ forum/></#if>
	<#if (category?default("")?length > 0)><#assign baseUrl = baseUrl +"&search_cat="+ category/></#if>
	<#if (kw?default("")?length > 0)><#assign baseUrl = baseUrl +"&search_keywords="+ kw/></#if>
	<#if (author?default("")?length > 0)><#assign baseUrl = baseUrl +"&search_author="+ author/></#if>
	<#if (terms?default("")?length > 0)><#assign baseUrl = baseUrl +"&search_terms="+ terms/></#if>
	<#if (orderBy?default("")?length > 0)><#assign baseUrl = baseUrl +"&sort_dir="+ orderBy/></#if>
	<#if (orderField?default("")?length > 0)><#assign baseUrl = baseUrl +"&sort_by="+ orderField/></#if>
	<#if (postTime?default("")?length > 0)><#assign baseUrl = baseUrl +"&post_time="+ postTime/></#if>
	
	<#return baseUrl/>
</#function>

<#macro searchPagination kw terms forum category orderField orderBy author postTime formName="fp">
	<#if (totalRecords > recordsPerPage)>
		<#assign baseUrl = contextPath +"/jforum" + extension + "?module=search&action=search&"/>
		<#assign baseUrl = baseUrl + moderationParams("") >

		<form name="${formName}">
		<span class="gensmall">${I18n.getMessage("goToPage")}:</span> 
		<select name="p">
			<#list 1 .. totalPages as page>
				<#assign start = recordsPerPage * (page - 1)/>
				<option value="${start}" <#if thisPage == start>selected</#if>>${page}</option>
			</#list>
		</select>
		&nbsp;<input type="button" value="${I18n.getMessage("ForumIndex.goToGo")}" class="mainoption" onClick="document.location = '${JForumContext.encodeURL("${baseUrl}&start=' + document.${formName}.p[document.${formName}.p.selectedIndex].value", "")};">		</form>
	</#if>
</#macro>