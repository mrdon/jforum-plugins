<#-- ********************************************* -->
<#-- Displays the topic folder image by its status -->
<#-- ********************************************* -->
<#macro folderImage topic>
	<#if topic.read>
			<#if topic.status == STATUS_UNLOCKED>
				<#if topic.type == TOPIC_ANNOUNCE>
					<img src="templates/${templateName}/images/folder_announce.gif" width="19" height="18">
				<#elseif topic.type == TOPIC_STICKY>
					<img src="templates/${templateName}/images/folder_sticky.gif" width="19" height="18">
				<#else>
					<img src="templates/${templateName}/images/folder.gif" width="19" height="18">
				</#if>
			<#else>
				<img src="templates/${templateName}/images/folder_lock.gif" width="19" height="18">
			</#if>
		<#else>
			<#if topic.status == STATUS_UNLOCKED>
				<#if topic.type == TOPIC_ANNOUNCE>
					<img src="templates/${templateName}/images/folder_announce_new.gif" width="19" height="18">
				<#elseif topic.type == TOPIC_STICKY>
					<img src="templates/${templateName}/images/folder_sticky_new.gif" width="19" height="18">
				<#else>
					<img src="templates/${templateName}/images/folder_new.gif" width="19" height="18">
				</#if>
			<#else>
				<img src="templates/${templateName}/images/folder_lock_new.gif" width="19" height="18">
			</#if>
		</#if>
</#macro>

<#-- ****************** -->
<#-- Moderation buttons -->
<#-- ****************** -->
<#macro moderationButtons>
	 <#if moderator  && openModeration?default(false)>
		<#if can_remove_posts?default(false)><input type="submit" name="topicRemove" value="&nbsp;&nbsp;${I18n.getMessage("Delete")}&nbsp;&nbsp;" class="liteoption" onclick="return validateModerationDelete();"></#if>
		<#if can_move_topics?default(false)><input type="submit" name="topicMove" value="&nbsp;&nbsp;${I18n.getMessage("move")}&nbsp;&nbsp;" class="liteoption" onclick="return verifyModerationCheckedTopics();"></#if>
		<#if can_lockUnlock_topics?default(false)>
			<input type="submit" name="topicLock" value="&nbsp;&nbsp;${I18n.getMessage("Lock")}&nbsp;&nbsp;" class="liteoption" onclick="return verifyModerationCheckedTopics();">
			<input type="submit" name="topicUnlock" value="&nbsp;&nbsp;${I18n.getMessage("Unlock")}&nbsp;&nbsp;" class="liteoption" onclick="return verifyModerationCheckedTopics();">
		</#if>
	  </#if>
</#macro>
