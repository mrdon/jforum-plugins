<#function moderationParams o>
	<#assign open = o/>
	<#if open?length == 0>
		<#if openModeration>
			<#assign open = "1"/>
		<#else>
			<#assign open = "0"/>
		</#if>
	</#if>
	
	<#assign baseUrl = "start="+ thisPage +"&openModeration="+ open/>
	
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

<#macro searchPagination kw terms forum category orderField orderBy author postTime>
	<#assign baseUrl = contextPath +"/jforum.page?module=search&action=search&"/>
	<#assign baseUrl = baseUrl + moderationParams("") >
	
	<#if (totalRecords > recordsPerPage)>
		<SPAN class=nav><B>
		${I18n.getMessage("goToPage")} 
		  
		<#if (thisPage > 0)>
			<#assign start = thisPage - recordsPerPage>
		  	 <a href="${baseUrl}&start=${start}">${I18n.getMessage("previous")}</a> simultaniously
		</#if>
		  
		<#if (totalPages > 6)>
			<#list 0 .. 3 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>
				
				<a href="${baseUrl}&start=${start}">${nextPage}</a>, 
			</#list>
			
			 ... 

			<#assign startLastFrom = totalPages - 2>
			<#list startLastFrom .. totalPages as page>
				<#assign start = recordsPerPage * page >
				<#assign nextPage = page + 1>
				
				<#if page != thisPage>
					<a href="${baseUrl}&start=${start}">${nextPage}</a>,
				<#else>
					${nextPage}
				</#if>
			</#list>

			<#assign start = recordsPerPage * totalPages>
			<#if start != thisPage>
				<a href="${baseUrl}&start=${start}">${totalPages}</a>
			<#else>
				${totalPages}
			</#if>
		<#else>
			<#list 0 .. totalPages - 1 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>
				
				<#if (start != thisPage)>
					<a href="${baseUrl}&start=${start}">${nextPage}</a>, 
				<#else>
					${nextPage}
				</#if>
			</#list>

			<#if (totalPages * recordsPerPage != totalRecords)>
				<#assign start = recordsPerPage * totalPages>
				<#if start != thisPage>
					<a href="${baseUrl}&start=${start}">${totalPages + 1}</a> 
				<#else>
					${totalPages + 1}
				</#if>
			</#if>
		</#if>

		<#if thisPage != start>
			<#assign start = thisPage + recordsPerPage/>
			<a href="${baseUrl}&start=${start}">${I18n.getMessage("next")}</a>
		</#if>
		  
		</B>
	</SPAN>
	<#else>
	</#if>

</#macro>