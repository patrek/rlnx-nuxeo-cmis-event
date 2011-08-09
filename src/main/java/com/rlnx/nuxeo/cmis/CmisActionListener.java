package com.rlnx.nuxeo.cmis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;

public class CmisActionListener implements EventListener {

	private static final Log log = LogFactory.getLog(CmisActionListener.class);
	
//	public void handleEvent(EventBundle eventBundle) throws ClientException {
//		log.error("nous avons un event bundle en traitement");
//		log.info(eventBundle.getClass().getName());
//        if (eventBundle instanceof ReconnectedEventBundleImpl) {
//            ReconnectedEventBundleImpl reconnectedEventBundle = (ReconnectedEventBundleImpl) eventBundle;
//            sendMessages(reconnectedEventBundle.getEventNames());
//        }
//	}
//
//	private void sendMessages(List<String> eventNames) {
//		for (String name : eventNames) {
//			log.info("rlnx got event named " + name);
//		}
//	}

	public void handleEvent(Event event) throws ClientException {
		String eventName = event.getName();
		log.info("Got event named " + eventName);
		if (DocumentEventTypes.DOCUMENT_LOCKED.equals(eventName)){
			log.debug("Time to checkout document");
		}
		else if (DocumentEventTypes.DOCUMENT_UNLOCKED.equals(eventName)){
			log.debug("Time to checkin document");
		}
		else if (DocumentEventTypes.DOCUMENT_CHECKEDIN.equals(eventName)){
			log.debug("Time to unlock document");
		}
		else if (DocumentEventTypes.DOCUMENT_CHECKEDOUT.equals(eventName)){
			log.debug("Time to lock document");
		}

		log.info(event.getContext().getClass().getName());
	}
}
