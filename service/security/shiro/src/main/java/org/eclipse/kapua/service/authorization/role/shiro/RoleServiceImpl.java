/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.kapua.service.authorization.role.shiro;

import java.util.Set;

import org.eclipse.kapua.KapuaEntityNotFoundException;
import org.eclipse.kapua.KapuaException;
import org.eclipse.kapua.commons.util.ArgumentValidator;
import org.eclipse.kapua.commons.service.internal.AbstractKapuaService;
import org.eclipse.kapua.locator.KapuaLocator;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.model.query.KapuaQuery;
import org.eclipse.kapua.service.authentication.shiro.AuthenticationEntityManagerFactory;
import org.eclipse.kapua.service.authorization.AuthorizationService;
import org.eclipse.kapua.service.authorization.permission.Actions;
import org.eclipse.kapua.service.authorization.permission.PermissionFactory;
import org.eclipse.kapua.service.authorization.role.Role;
import org.eclipse.kapua.service.authorization.role.RoleCreator;
import org.eclipse.kapua.service.authorization.role.RoleListResult;
import org.eclipse.kapua.service.authorization.role.RoleService;

/**
 * Role service implementation.
 * 
 * @since 1.0
 *
 */
public class RoleServiceImpl extends AbstractKapuaService implements RoleService
{

    public RoleServiceImpl()
    {
        super(AuthenticationEntityManagerFactory.getInstance());
    }

    @Override
    public Role create(RoleCreator roleCreator)
        throws KapuaException
    {
        ArgumentValidator.notNull(roleCreator, "roleCreator");
        ArgumentValidator.notEmptyOrNull(roleCreator.getName(), "roleCreator.name");
        ArgumentValidator.notNull(roleCreator.getRoles(), "roleCreator.permissions");

        //
        // Check Access
        KapuaLocator locator = KapuaLocator.getInstance();
        AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
        PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
        authorizationService.checkPermission(permissionFactory.newPermission(RoleDomain.ROLE, Actions.write, roleCreator.getScopeId()));
        return entityManagerSession.onEntityManagerInsert(em -> {
            em.beginTransaction();
            Role role = RoleDAO.create(em, roleCreator);
            em.commit();
            return role;
        });
    }

    @Override
    public void delete(KapuaId scopeId, KapuaId roleId) throws KapuaException {
        // Check Access
        KapuaLocator locator = KapuaLocator.getInstance();
        AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
        PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
        authorizationService.checkPermission(permissionFactory.newPermission(RoleDomain.ROLE, Actions.delete, scopeId));

        entityManagerSession.onEntityManagerAction(em -> {
            if (RoleDAO.find(em, roleId) == null) {
                throw new KapuaEntityNotFoundException(Role.TYPE, roleId);
            }

            em.beginTransaction();
            RoleDAO.delete(em, roleId);
            em.commit();
        });
    }

    @Override
    public Role find(KapuaId scopeId, KapuaId roleId)
        throws KapuaException
    {
        ArgumentValidator.notNull(scopeId, "accountId");
        ArgumentValidator.notNull(roleId, "roleId");

        //
        // Check Access
        KapuaLocator locator = KapuaLocator.getInstance();
        AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
        PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
        authorizationService.checkPermission(permissionFactory.newPermission(RoleDomain.ROLE, Actions.read, scopeId));

        return entityManagerSession.onEntityManagerResult(em -> {
            return RoleDAO.find(em, roleId);
        });
    }

    @Override
    public RoleListResult query(KapuaQuery<Role> query)
        throws KapuaException
    {
        ArgumentValidator.notNull(query, "query");
        ArgumentValidator.notNull(query.getScopeId(), "query.scopeId");

        //
        // Check Access
        KapuaLocator locator = KapuaLocator.getInstance();
        AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
        PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
        authorizationService.checkPermission(permissionFactory.newPermission(RoleDomain.ROLE, Actions.read, query.getScopeId()));

        return entityManagerSession.onEntityManagerResult(em -> {
            return RoleDAO.query(em, query);
        });
    }

    @Override
    public long count(KapuaQuery<Role> query)
        throws KapuaException
    {
        ArgumentValidator.notNull(query, "query");
        ArgumentValidator.notNull(query.getScopeId(), "query.scopeId");

        //
        // Check Access
        KapuaLocator locator = KapuaLocator.getInstance();
        AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
        PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
        authorizationService.checkPermission(permissionFactory.newPermission(RoleDomain.ROLE, Actions.read, query.getScopeId()));

        return entityManagerSession.onEntityManagerResult(em -> {
            return RoleDAO.count(em, query);
        });
    }

    @Override
    public RoleListResult merge(Set<RoleCreator> newPermissions)
        throws KapuaException
    {
        // TODO Auto-generated method stub
        return null;
    }
}
