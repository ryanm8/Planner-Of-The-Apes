/*
 * Created by Brian Green on 2014.10.24  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */

package com.brigreen.sessionbeanpackage;

import com.brigreen.planneroftheapes.Documents;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian
 */
@Stateless
public class DocumentsFacade extends AbstractFacade<Documents> {
    @PersistenceContext(unitName = "com.brigreen_planneroftheapes_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentsFacade() {
        super(Documents.class);
    }
    
}
