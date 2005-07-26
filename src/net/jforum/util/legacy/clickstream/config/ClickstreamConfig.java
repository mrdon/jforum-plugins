package net.jforum.util.legacy.clickstream.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Clickstream configuration data.
 *
 * @author <a href="plightbo@hotmail.com">Patrick Lightbody</a>
 * @author Rafael Steil (little hacks for JForum)
 * @version $Id: ClickstreamConfig.java,v 1.3 2005/07/26 03:05:35 rafaelsteil Exp $
 */
public class ClickstreamConfig {
    private String loggerClass;
    private List botAgents = new ArrayList();
    private List botHosts = new ArrayList();

    public void addBotAgent(String agent) {
        botAgents.add(agent);
    }

    public void addBotHost(String host) {
        botHosts.add(host);
    }

    public List getBotAgents() {
        return botAgents;
    }

    public List getBotHosts() {
        return botHosts;
    }
}
