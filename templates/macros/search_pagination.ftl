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

	<#if (searchArgs.keywords?size > 0)><#assign baseUrl = baseUrl +"&amp;search_keywords="+ searchArgs.rawKeywords()/></#if>
	<#if (searchArgs.forumId > 0)><#assign baseUrl = baseUrl +"&amp;search_forum="+ searchArgs.forumId/></#if>
	<#if (searchArgs.author > 0)><#assign baseUrl = baseUrl +"&amp;search_author="+ searchArgs.author/></#if>
	<#if (searchArgs.matchType?default("")?length > 0)><#assign baseUrl = baseUrl +"&amp;match_type="+ searchArgs.matchType/></#if>
	<#if (searchArgs.orderDir?default("")?length > 0)><#assign baseUrl = baseUrl +"&amp;sort_dir="+ searchArgs.orderDir/></#if>
	<#if (searchArgs.orderBy?default("")?length > 0)><#assign baseUrl = baseUrl +"&amp;sort_by="+ searchArgs.orderBy/></#if>

	<#return baseUrl/>
</#function>

<#macro searchPagination kw terms forum orderField orderBy author postTime>
	<#assign baseUrl = contextPath +"/jforum" + extension + "?module=search&amp;action=search&amp;"/>
	<#assign baseUrl = baseUrl + moderationParams("")/>

	<#if (totalRecords > recordsPerPage)>
		<span class="gensmall"><b>${I18n.getMessage("goToPage")}:

		<#assign numberOfActiveLinks = 7/>
		<#assign numberOfSideLinks = 3/>

		<#if (thisPage > 1)>
			<#assign start = (thisPage - 2) * recordsPerPage/>
			<a href="${baseUrl}&amp;start=${start}">${I18n.getMessage("previous")}</a>&nbsp;
		</#if>

		<#if (totalPages > numberOfActiveLinks)>
			<#if (thisPage <= numberOfSideLinks)>
				<#assign startPageAt = 1/>
				<#assign stopPageAt = numberOfActiveLinks/>
			<#else>
				<#assign startPageAt = (thisPage - numberOfSideLinks) >
				<#if (thisPage >= (totalPages - numberOfSideLinks)) >
					<#assign startPageAt = (totalPages - (numberOfActiveLinks - 1))/>
				</#if>
				
				<#assign stopPageAt = startPageAt + (numberOfActiveLinks - 1)/>
			</#if>

			<#if (startPageAt > 1) >
				...
			</#if>

			<#list startPageAt .. stopPageAt as page >
				<#assign start = recordsPerPage * (page-1) >

				<#if thisPage == page>
					${page}
				<#else>
					<a href="${baseUrl}&amp;start=${start}">${page}</a>
				</#if>
				<#if (page < totalPages) >,</#if>
			</#list>
			
			<#if (stopPageAt < totalPages)>
				...
			</#if>
		<#else>
			<#list 1 .. totalPages as page >
				<#assign start = recordsPerPage * (page - 1)/>

				<#if thisPage == page>
					${page}
				<#else>
					<a href="${baseUrl}&amp;start=${start}">${page}</a>
				</#if>
				<#if (page < totalPages) >,</#if>
			</#list>
		</#if>

		<#if thisPage < totalPages >
			<#assign start = thisPage * recordsPerPage/>
			&nbsp;<a href="${baseUrl}&amp;start=${start}">${I18n.getMessage("next")}</a>
		</#if>
		</b>
	</span>
	</#if>
</#macro>