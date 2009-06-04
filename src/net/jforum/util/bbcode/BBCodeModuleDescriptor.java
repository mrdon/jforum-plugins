package net.jforum.util.bbcode;

import com.atlassian.plugin.descriptors.AbstractModuleDescriptor;
import com.atlassian.plugin.AutowireCapablePlugin;

/**
 * Created by IntelliJ IDEA.
 * User: mrdon
 * Date: Jun 3, 2009
 * Time: 6:32:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BBCodeModuleDescriptor extends AbstractModuleDescriptor<BBCode>
{
    public BBCode getModule()
    {
        return ((AutowireCapablePlugin)getPlugin()).autowire(getModuleClass());
    }
}
