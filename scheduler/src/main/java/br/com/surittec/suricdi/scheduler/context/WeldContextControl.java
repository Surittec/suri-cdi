package br.com.surittec.suricdi.scheduler.context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.ContextControl;
import org.jboss.weld.context.AbstractSharedContext;
import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.jboss.weld.context.bound.MutableBoundRequest;

/**
 * From Deltaspike!
 * Weld specific impl of the {@link org.apache.deltaspike.cdise.api.ContextControl}
 */
@Dependent
public class WeldContextControl implements ContextControl {

	private static ThreadLocal<RequestContextHolder> requestContexts = new ThreadLocal<RequestContextHolder>();
	private static ThreadLocal<Map<String, Object>> sessionMaps = new ThreadLocal<Map<String, Object>>();

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private BoundSessionContext sessionContext;

	@Inject
	private Instance<BoundRequestContext> requestContextFactory;

	@Inject
	private BoundConversationContext conversationContext;

	@Override
	public void startContexts() {
		startApplicationScope();
		startSessionScope();
		startRequestScope();
		startConversationScope(null);
	}

	@Override
	public void startContext(Class<? extends Annotation> scopeClass) {
		if (scopeClass.isAssignableFrom(ApplicationScoped.class)) {
			startApplicationScope();
		} else if (scopeClass.isAssignableFrom(SessionScoped.class)) {
			startSessionScope();
		} else if (scopeClass.isAssignableFrom(RequestScoped.class)) {
			startRequestScope();
		} else if (scopeClass.isAssignableFrom(ConversationScoped.class)) {
			startConversationScope(null);
		}
	}

	/**
	 * Currently we can't stop the {@link ApplicationScoped} due to WELD-1072
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void stopContexts() {
		stopConversationScope();
		stopRequestScope();
		stopSessionScope();
		stopApplicationScope(); // can't be done because of WELD-1072
	}

	@Override
	public void stopContext(Class<? extends Annotation> scopeClass) {
		if (scopeClass.isAssignableFrom(ApplicationScoped.class)) {
			stopApplicationScope();
		} else if (scopeClass.isAssignableFrom(SessionScoped.class)) {
			stopSessionScope();
		} else if (scopeClass.isAssignableFrom(RequestScoped.class)) {
			stopRequestScope();
		} else if (scopeClass.isAssignableFrom(ConversationScoped.class)) {
			stopConversationScope();
		}
	}

	/*
	 * start scopes
	 */
	private void startApplicationScope() {
		// Welds ApplicationContext is always active
		// No need to attach any ThreadLocals...
	}

	private void stopApplicationScope() {
		// Welds ApplicationContext gets cleaned at shutdown.
		// X TODO if we really drop the context then we might trash EE
		// X if we do not do it then we loose the ability to cleanup
		// ApplicationScoped beans
		if (applicationContext.isActive()) {
			applicationContext.invalidate();

			// needed for weld < v1.1.9
			if (applicationContext instanceof AbstractSharedContext) {
				((AbstractSharedContext) applicationContext).getBeanStore()
						.clear();
			}
		}
	}

	void startRequestScope() {
		RequestContextHolder rcHolder = requestContexts.get();
		if (rcHolder == null) {
			rcHolder = new RequestContextHolder(requestContextFactory.get(),
					new HashMap<String, Object>());
			requestContexts.set(rcHolder);
		} else {
			throw new IllegalStateException(RequestScoped.class.getName()
					+ " started already");
		}

		rcHolder.getBoundRequestContext().associate(rcHolder.getRequestMap());
		rcHolder.getBoundRequestContext().activate();
	}

	void stopRequestScope() {
		RequestContextHolder rcHolder = requestContexts.get();
		if (rcHolder != null && rcHolder.getBoundRequestContext().isActive()) {
			rcHolder.getBoundRequestContext().invalidate();
			rcHolder.getBoundRequestContext().deactivate();
			rcHolder.getBoundRequestContext().dissociate(
					rcHolder.getRequestMap());
			requestContexts.set(null);
			requestContexts.remove();
		}
	}

	private void startSessionScope() {
		Map<String, Object> sessionMap = sessionMaps.get();
		if (sessionMap == null) {
			sessionMap = new HashMap<String, Object>();
			sessionMaps.set(sessionMap);
		}

		sessionContext.associate(sessionMap);
		sessionContext.activate();

	}

	private void stopSessionScope() {
		if (sessionContext.isActive()) {
			sessionContext.invalidate();
			sessionContext.deactivate();
			sessionContext.dissociate(sessionMaps.get());

			sessionMaps.set(null);
			sessionMaps.remove();
		}
	}

	void startConversationScope(String cid) {
		RequestContextHolder rcHolder = requestContexts.get();
		if (rcHolder == null) {
			startRequestScope();
			rcHolder = requestContexts.get();
		}
		conversationContext.associate(new MutableBoundRequest(
				rcHolder.requestMap, sessionMaps.get()));
		conversationContext.activate(cid);
	}

	void stopConversationScope() {
		RequestContextHolder rcHolder = requestContexts.get();
		if (rcHolder == null) {
			startRequestScope();
			rcHolder = requestContexts.get();
		}
		if (conversationContext.isActive()) {
			conversationContext.invalidate();
			conversationContext.deactivate();
			conversationContext.dissociate(new MutableBoundRequest(rcHolder
					.getRequestMap(), sessionMaps.get()));
		}
	}

	private static class RequestContextHolder {
		private final BoundRequestContext boundRequestContext;
		private final Map<String, Object> requestMap;

		private RequestContextHolder(BoundRequestContext boundRequestContext,
				Map<String, Object> requestMap) {
			this.boundRequestContext = boundRequestContext;
			this.requestMap = requestMap;
		}

		public BoundRequestContext getBoundRequestContext() {
			return boundRequestContext;
		}

		public Map<String, Object> getRequestMap() {
			return requestMap;
		}
	}

}
