package com.rlnx.nuxeo.cmis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.VersioningOption;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

public class CmisActionListener implements EventListener {

	private static final Log log = LogFactory.getLog(CmisActionListener.class);

	public void handleEvent(Event event) throws ClientException {
		String eventName = event.getName();
		log.debug("Got event named " + eventName);
		EventContext ctx = event.getContext();
		if (ctx instanceof DocumentEventContext) {
			DocumentModel document = ((DocumentEventContext) ctx)
					.getSourceDocument();
			if (null != document) {
				if (log.isDebugEnabled()) {
					log.debug("Status before : document "
							+ (document.isCheckedOut() ? "checkedout "
									: "checkedin ")
							+ (document.isLocked() ? "locked" : "unlocked"));
				}
				if (DocumentEventTypes.DOCUMENT_LOCKED.equals(eventName)) {
					if (!document.isCheckedOut()) {
						document.checkOut();
					}
				} else if (DocumentEventTypes.DOCUMENT_UNLOCKED
						.equals(eventName)) {
					if (document.isCheckedOut()) {
						document.checkIn(VersioningOption.NONE, "unlocked");
					}
				} else if (DocumentEventTypes.DOCUMENT_CHECKEDIN
						.equals(eventName)) {
					if (document.isLocked()) {
						document.removeLock();
					}
				} else if (DocumentEventTypes.DOCUMENT_CHECKEDOUT
						.equals(eventName)) {
					if (!document.isLocked()) {
						document.setLock();
					}
				}
				if (log.isDebugEnabled()) {
					log.debug("Status after : document "
							+ (document.isCheckedOut() ? "checkedout "
									: "checkedin ")
							+ (document.isLocked() ? "locked" : "unlocked"));
				}
			}
		}
	}
}
