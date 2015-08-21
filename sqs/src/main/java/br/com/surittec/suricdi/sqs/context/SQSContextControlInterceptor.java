package br.com.surittec.suricdi.sqs.context;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

@SQSContextControl
@Interceptor
public class SQSContextControlInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object createContextControl(InvocationContext ctx) throws Exception {
		ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);
		try {
			ctxCtrl.startContext(RequestScoped.class);
			return ctx.proceed();
		} finally {
			ctxCtrl.stopContext(RequestScoped.class);
		}
	}

}
