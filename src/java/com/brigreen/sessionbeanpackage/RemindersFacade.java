/*
 * Created by Chris Hoffman on 2014.10.28  * 
 * Copyright © 2014 Chris Hoffman. All rights reserved. * 
 */

package com.brigreen.sessionbeanpackage;

import com.brigreen.planneroftheapes.Reminders;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Chris
 */
@Stateless
public class RemindersFacade extends AbstractFacade<Reminders> {
    @PersistenceContext(unitName = "planneroftheapesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RemindersFacade() {
        super(Reminders.class);
    }
    
}
