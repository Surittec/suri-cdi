package br.com.surittec.suricdi.example.util;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import br.com.surittec.suricdi.faces.rewrite.provider.SecureHttpConfigurationProvider;

@RewriteConfiguration
public class RewriteConfigurationProvider extends SecureHttpConfigurationProvider {

	@Override
	public ConfigurationBuilder getConfigurationBuilder(final ServletContext context) {
		return ConfigurationBuilder.begin()
				
				.addRule(Join.path("/").to("/view/home.xhtml"))
				.addRule(Join.path("/403").to("/view/403.xhtml").withInboundCorrection())
				.addRule(Join.path("/404").to("/view/404.xhtml").withInboundCorrection())
				.addRule(Join.path("/500").to("/view/500.xhtml").withInboundCorrection())
				
		;
	}

}
