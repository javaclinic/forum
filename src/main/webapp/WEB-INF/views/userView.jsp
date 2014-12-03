<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>
  <h1>forum :: View User</h1>

  <c:choose>
    <c:when test="${CREATED}">
    <p style="color: green">Success! The following user has successfully been created:</p>
    </c:when>
    <c:when test="${UPDATED}">
    <p style="color: green">Success! The following user has successfully been updated:</p>
    </c:when>
    <c:when test="${DELETED}">
    <p style="color: green">Success! The following user has successfully been removed:</p>
    </c:when>
  </c:choose>

    <p>
      <label for="firstname">First Name</label> <input value="${USER.firstname}" disabled="disabled" />
    </p>
    <p>
      <label for="lastname">Last Name</label> <input value="${USER.lastname}" disabled="disabled" />
    </p>
    <p>
      <label for="organization">Organization</label> <input value="${USER.organization}" disabled="disabled" />
    </p>
    <p>
      <label for="title">Title</label> <input value="${USER.title}" disabled="disabled" />
    </p>
    <p>
      <label for="email">Email</label> <input value="${USER.email}" disabled="disabled" />
    </p>
    <p>
      <label for="id">Id</label> <input value="${USER.id}" disabled="disabled"/>
    </p>

  <c:if test="${!DELETED}">
    <p class="buttons">
      <a href="<c:url value='/user_edit.html?id=${USER.id}' />"><button>Edit</button></a>
      <a href="<c:url value='/user_delete.html?id=${USER.id}' />"><button>Delete</button></a>
    </p>
  </c:if>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>
