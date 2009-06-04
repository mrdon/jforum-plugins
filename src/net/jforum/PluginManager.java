package net.jforum;

import com.atlassian.plugin.osgi.container.PackageScannerConfiguration;
import com.atlassian.plugin.osgi.container.impl.DefaultPackageScannerConfiguration;
import com.atlassian.plugin.osgi.hostcomponents.HostComponentProvider;
import com.atlassian.plugin.osgi.hostcomponents.ComponentRegistrar;
import com.atlassian.plugin.main.PluginsConfiguration;
import com.atlassian.plugin.main.PluginsConfigurationBuilder;
import com.atlassian.plugin.main.AtlassianPlugins;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.ModuleDescriptorFactory;
import com.atlassian.plugin.DefaultModuleDescriptorFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.concurrent.TimeUnit;

import net.jforum.util.bbcode.BBCodeModuleDescriptor;
import net.jforum.dao.DataAccessDriver;
import net.jforum.dao.TopicDAO;

/**
 * Created by IntelliJ IDEA.
 * User: mrdon
 * Date: Jun 3, 2009
 * Time: 7:04:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginManager
{
    private static AtlassianPlugins plugins;

    public static void start(ServletContext ctx) {
        File pluginsDir = new File(ctx.getRealPath("/WEB-INF/plugins"));
        pluginsDir.mkdir();

        PackageScannerConfiguration scannerConfig = new DefaultPackageScannerConfiguration();
        scannerConfig.getPackageIncludes().add("net.jforum*");

        DefaultModuleDescriptorFactory modules = new DefaultModuleDescriptorFactory();
        modules.addModuleDescriptor("bbcode", BBCodeModuleDescriptor.class);

        HostComponentProvider host = new HostComponentProvider() {
            public void provide(ComponentRegistrar reg)
            {
                reg.register(TopicDAO.class).forInstance(
                    DataAccessDriver.getInstance().newTopicDAO());
            }
        };


        PluginsConfiguration config = new PluginsConfigurationBuilder()
                .pluginDirectory(pluginsDir)
                .packageScannerConfiguration(scannerConfig)
                .moduleDescriptorFactory(modules)
                .hostComponentProvider(host)
                .hotDeployPollingFrequency(2, TimeUnit.SECONDS)
                .build();
        plugins = new AtlassianPlugins(config);

        plugins.start();
    }

    public static void stop() {
        plugins.stop();
        plugins = null;
    }

    public static PluginAccessor getPluginAccessor() {
        return plugins != null ? plugins.getPluginAccessor() : null;
    }

}
