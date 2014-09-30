package br.com.surittec.suricdi.faces.rewrite.provider;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.DispatchType;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.SendStatus;

public abstract class SecureHttpConfigurationProvider extends HttpConfigurationProvider{

	@Override
	public final Configuration getConfiguration(ServletContext context) {
		
		return 	getConfigurationBuilder(context)
				
				.addRule()
					.when(Direction.isInbound()
							.andNot(DispatchType.isForward())
							.andNot(Path.matches("{*}javax.faces.resource{*}")))
					.perform(SendStatus.error(404));
	}
	
	@Override
	public int priority() {
		//After annotation configuration provider
		return 101;
	}
	
	public abstract ConfigurationBuilder getConfigurationBuilder(ServletContext context);
	
}
