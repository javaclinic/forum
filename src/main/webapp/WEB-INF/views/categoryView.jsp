<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>
  <h1>forum :: View Category</h1>

  <c:choose>
    <c:when test="${CREATED}">
    <p style="color: green">Success! The following category has successfully been created:</p>
    </c:when>
    <c:when test="${UPDATED}">
    <p style="color: green">Success! The following category has successfully been updated:</p>
    </c:when>
    <c:when test="${DELETED}">
    <p style="color: green">Success! The following category has successfully been removed:</p>
    </c:when>
  </c:choose>

    <p>
      <label for="name">Name</label> <input value="${CATEGORY.name}" disabled="disabled" />
    </p>
    <p>
      <label for="description">Description</label> <input value="${CATEGORY.description}" disabled="disabled" />
    </p>
    <p>
      <label for="id">Id</label> <input value="${CATEGORY.id}" disabled="disabled" />
    </p>

  <c:if test="${!DELETED}">
    <p class="buttons">
      <a href="<c:url value='/category_edit.html?id=${CATEGORY.id}' />"><button>Edit</button></a>
      <a href="<c:url value='/category_delete.html?id=${CATEGORY.id}' />"><button>Delete</button></a>
    </p>
  </c:if>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>
