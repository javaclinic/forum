<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<head>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>" />
</head>
<body>

  <h1>forum :: Categories</h1>
  <c:choose>
    <c:when test="${fn:length(CATEGORIES) == 0}">
      <p>No users.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${CATEGORIES}" var="category">
            <tr>
              <td>${category.id}</td>
              <td>${category.name}</td>
              <td>${category.description}</td>
              <td>
                <a href="<c:url value='/category_edit.html?id=${category.id}'/>"><button>Edit</button></a> 
                <a href="<c:url value='/category_delete.html?id=${category.id}'/>"><button>Delete</button></a>
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

