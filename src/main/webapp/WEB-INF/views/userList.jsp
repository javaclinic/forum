<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>

  <h1>forum :: Users</h1>
  <c:choose>
    <c:when test="${fn:length(USERS) == 0}">
      <p>No users.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Organization</th>
            <th>Title</th>
            <th>Email</th>
            <th>Since</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${USERS}" var="user">
            <tr>
              <td class="right-aligned">${user.id}</td>
              <td>${user}</td>
              <td>${user.organization}</td>
              <td>${user.title}</td>
              <td>${user.email}</td>
              <td><fmt:formatDate type="date" dateStyle="long" value="${user.created}" /></td>
              <td>
                <a href="<c:url value='/user_edit.html?id=${user.id}'/>"><button>Edit</button></a>
                <a href="<c:url value='/user_delete.html?id=${user.id}'/>"><button>Delete</button></a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>

