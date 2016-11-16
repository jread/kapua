/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.commons.model.misc;

import java.util.Map;

import org.eclipse.kapua.KapuaException;
import org.eclipse.kapua.commons.configuration.AbstractKapuaConfigurableService;
import org.eclipse.kapua.model.config.metatype.KapuaTocd;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.model.query.KapuaListResult;
import org.eclipse.kapua.model.query.KapuaQuery;

public class CollisionServiceImpl extends AbstractKapuaConfigurableService implements CollisionEntityService
{

    private static final long serialVersionUID = -5296593780771944081L;

    public CollisionServiceImpl()
    {
        super(CollisionServiceImpl.class.getName(), "CollisionServiceImplDomain", CollisionEntityManagerFactory.getInstance());
    }

    public CollisionEntity insert(String testField) throws KapuaException
    {
        CollisionEntityCreator collisionEntityCreator = new CollisionEntityCreator(testField);
        return entityManagerSession.onEntityManagerInsert(em -> {
            CollisionEntity collisionEntity = null;
            try {
                em.beginTransaction();
                collisionEntity = CollisionEntityDAO.create(em, collisionEntityCreator);
                em.commit();
            }
            catch (Exception e) {
                em.rollback();
                throw e;
            }
            return CollisionEntityDAO.find(em, collisionEntity.getId());
        });
    }

    @Override
    public CollisionEntity create(CollisionEntityCreator creator) throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CollisionEntity find(KapuaId scopeId, KapuaId entityId) throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KapuaListResult<CollisionEntity> query(KapuaQuery<CollisionEntity> query) throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count(KapuaQuery<CollisionEntity> query) throws KapuaException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(KapuaId scopeId, KapuaId entityId) throws KapuaException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CollisionEntity findByName(String name) throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KapuaTocd getConfigMetadata() throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getConfigValues(KapuaId scopeId) throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setConfigValues(KapuaId scopeId, Map<String, Object> values) throws KapuaException
    {
        // TODO Auto-generated method stub
        
    }

}
