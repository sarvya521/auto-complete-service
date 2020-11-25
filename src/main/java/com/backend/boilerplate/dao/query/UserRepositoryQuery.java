package com.backend.boilerplate.dao.query;

/**
 * @author sarvesh
 * @version 0.0.2
 * @since 0.0.2
 */
@SuppressWarnings("squid:S1192")
public final class UserRepositoryQuery {
    public static final String QUERY_FIND_ID_BY_UUID
        = " SELECT u.id"
        + " FROM User u"
        + " WHERE u.uuid = (:uuid)";

    public static final String QUERY_FIND_BY_USERROLES_CONTAINING
        = "SELECT DISTINCT u"
        + " FROM User u"
        + " INNER JOIN u.userRoles ur"
        + " INNER JOIN ur.role r"
        + " WHERE u.id != :userId"
        + " AND r.name IN (:roleNames)";

    public static final String QUERY_FIND_BY_USERROLES_NOT_CONTAINING
        = "SELECT DISTINCT u"
        + " FROM User u"
        + " INNER JOIN u.userRoles ur"
        + " INNER JOIN ur.role r"
        + " WHERE u.id != :userId"
        + " AND r.name NOT IN (:roleNames)";

    public static final String QUERY_GET_USER_ROLES
        = " SELECT DISTINCT r as role, u AS user"
        + " FROM Role r"
        + " INNER JOIN r.userRoles ur"
        + " INNER JOIN ur.user u"
        + " WHERE r.name NOT IN (:roleNames)";

    private UserRepositoryQuery() {
        throw new AssertionError();
    }
}
