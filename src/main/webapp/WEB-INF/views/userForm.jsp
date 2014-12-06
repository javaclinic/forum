<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>
  <h1>forum :: Save User</h1>

  <c:choose>
    <c:when test="${fn:length(ERRORS) !=0 }">
    <p style="color: red">Please fix the errors:</p>
      <ul>
        <c:forEach var="error" items="${ERRORS}">
          <li style="color: red">${error}</li>
        </c:forEach>
      </ul>
    </c:when>
  </c:choose>

  <form method="POST" action="<c:url value='/user_edit.html'/>">
    <p>
      <label for="firstname">First Name</label> <input type="text" name="firstname" value="${USER.firstname}" />
    </p>
    <p>
      <label for="lastname">Last Name</label> <input type="text" name="lastname" value="${USER.lastname}" />
    </p>
    <p>
      <label for="organization">Organization</label> <input type="text" name="organization" value="${USER.organization}" />
    </p>
    <p>
      <label for="title">Title</label> <input type="text" name="title" value="${USER.title}" />
    </p>
    <p>
      <label for="email">Email</label> <input type="text" name="email" value="${USER.email}" />
    </p>
    <p>
      <label for="password">Password</label> <input type="password" name="password" value="" />
    </p>
    <p>
      <label for="password_verification">Password Verification</label> <input type="password" name="password_verification" value="" />
    </p>
    <input type="hidden" name="id" value="${USER.id}" />
    <p class="buttons">
      <button type="submit">Save</button>
    </p>
  </form>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>
