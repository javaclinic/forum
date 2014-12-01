<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<jsp:include page="/WEB-INF/views/comp/head.jsp" />
<body>
  <h1>forum :: Error</h1>

  <pre style="color: red">
    ${ERROR}
  </pre>

<jsp:include page="/WEB-INF/views/comp/menu.jsp" />

</body>
</html>
