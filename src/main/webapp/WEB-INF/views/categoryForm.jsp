<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>
  <h1>forum :: Save Category</h1>

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

  <form method="POST" action="<c:url value='/category_edit.html'/>">
    <p>
      <label for="name">Name</label> <input type="text" name="name" value="${CATEGORY.name}" />
    </p>
    <p>
      <label for="description">Description</label> <input type="text" name="description" value="${CATEGORY.description}" />
    </p>
    <input type="hidden" name="id" value="${CATEGORY.id}" />
    <p class="buttons">
      <button type="submit">Save</button>
    </p>
  </form>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>
