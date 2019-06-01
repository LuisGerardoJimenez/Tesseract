<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[<!DOCTYPE html>]]>
	</jsp:text>
	<html lang="es-MX">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="emeth" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<s:url var="urlRutaContextoTema"
	value="%{#pageContext.request.contextPath}/resources/themes"
	includeContext="true" />
<sj:head debug="true" jqueryui="true" jquerytheme="custom-roosevelt-1.0"
	locale="es" customBasepath="%{#urlRutaContextoTema}" />
<title><s:text name="mx.com.emeth.title" /></title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/components/template/vendor/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/components/template/vendor/metisMenu/metisMenu.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/components/template/dist/css/sb-admin-2.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/components/template/vendor/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/form.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/images/favicon.ico"
	sizes="32x32" />
<link
	href="${pageContext.request.contextPath}/resources/components/template/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<decorator:head />
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"> <!-- -->
					</span> <span class="icon-bar"> <!-- -->
					</span> <span class="icon-bar"> <!-- -->
					</span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}"><img
					alt="Logotipo"
					src="${pageContext.request.contextPath}/resources/images/logo2.png"
					class="logo" /></a>
			</div>
			<!-- /.navbar-header -->
			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw">
							<!--  -->
					</i> <i class="fa fa-caret-down"> <!--  -->
					</i>
				</a>
					<ul class="dropdown-menu dropdown-alerts">
						<li><a href="#">
								<div>
									<i class="fa fa-comment fa-fw"> <!--  -->
									</i> New Comment <span class="pull-right text-muted small">4
										minutes ago</span>
								</div>
						</a></li>
						<li class="divider">
							<!--  -->
						</li>
						<li><a href="#">
								<div>
									<i class="fa fa-twitter fa-fw"> <!--  -->
									</i> 3 New Followers <span class="pull-right text-muted small">12
										minutes ago</span>
								</div>
						</a></li>
						<li class="divider">
							<!--  -->
						</li>
						<li><a href="#">
								<div>
									<i class="fa fa-envelope fa-fw"> <!--  -->
									</i> Message Sent <span class="pull-right text-muted small">4
										minutes ago</span>
								</div>
						</a></li>
						<li class="divider">
							<!--  -->
						</li>
						<li><a href="#">
								<div>
									<i class="fa fa-tasks fa-fw"> <!--  -->
									</i> New Task <span class="pull-right text-muted small">4
										minutes ago</span>
								</div>
						</a></li>
						<li class="divider">
							<!--  -->
						</li>
						<li><a href="#">
								<div>
									<i class="fa fa-upload fa-fw"> <!--  -->
									</i> Server Rebooted <span class="pull-right text-muted small">4
										minutes ago</span>
								</div>
						</a></li>
						<li class="divider">
							<!--  -->
						</li>
						<li><a class="text-center" href="#"> <strong>See
									All Alerts</strong> <i class="fa fa-angle-right"> <!--  -->
							</i>
						</a></li>
					</ul> <!-- /.dropdown-alerts --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw">
							<!--  -->
					</i> <i class="fa fa-caret-down"> <!--  -->
					</i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"> <!--  -->
							</i> User Profile</a></li>
						<li><a href="#"><i class="fa fa-gear fa-fw"> <!--  -->
							</i> Settings</a></li>
						<li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/logout"><i
								class="fa fa-sign-out fa-fw"> <!--  -->
							</i> <s:text name="AC-US-CU3.titulo" /></a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li><a href="index.html"><i class="fa fa-dashboard fa-fw">
									<!--  -->
							</i> Dashboard</a></li>
						<li><a href="#"><i class="fa fa-bar-chart-o fa-fw"> <!--  -->
							</i> Charts<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="flot.html">Flot Charts</a></li>
								<li><a href="morris.html">Morris.js Charts</a></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="tables.html"><i class="fa fa-table fa-fw">
									<!--  -->
							</i> Tables</a></li>
						<li><a href="forms.html"><i class="fa fa-edit fa-fw">
									<!--  -->
							</i> Forms</a></li>
						<li><a href="#"><i class="fa fa-wrench fa-fw"> <!--  -->
							</i> UI Elements<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="panels-wells.html">Panels and Wells</a></li>
								<li><a href="buttons.html">Buttons</a></li>
								<li><a href="notifications.html">Notifications</a></li>
								<li><a href="typography.html">Typography</a></li>
								<li><a href="icons.html"> Icons</a></li>
								<li><a href="grid.html">Grid</a></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="#"><i class="fa fa-sitemap fa-fw"> <!--  -->
							</i> Multi-Level Dropdown<span class="fa arrow"> <!-- -->
							</span></a>
							<ul class="nav nav-second-level">
								<li><a href="#">Second Level Item</a></li>
								<li><a href="#">Second Level Item</a></li>
								<li><a href="#">Third Level <span class="fa arrow">
											<!-- -->
									</span></a>
									<ul class="nav nav-third-level">
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
										<li><a href="#">Third Level Item</a></li>
									</ul> <!-- /.nav-third-level --></li>
							</ul> <!-- /.nav-second-level --></li>
						<li><a href="#"><i class="fa fa-files-o fa-fw"> <!--  -->
							</i> Sample Pages<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level">
								<li><a href="blank.html">Blank Page</a></li>
								<li><a href="login.html">Login Page</a></li>
							</ul> <!-- /.nav-second-level --></li>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>

		<div id="page-wrapper">
			<div class="row">
				<decorator:body />
			</div>
		</div>
	</div>
	<s:hidden id="hdnRutaContexto"
		value="%{#pageContext.request.contextPath}" />
	<s:hidden id="hdnUrlLanguageDataTable"
		value="%{getText('mx.com.emeth.datatable.language')}" />
</body>
<jsp:text>
	<![CDATA[
				<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/vendor/metisMenu/metisMenu.min.js"></script>
   				<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/vendor/bootstrap/js/bootstrap.min.js"></script>
    			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/vendor/raphael/raphael.min.js"></script>
    			<!--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/vendor/morrisjs/morris.min.js"></script>
    			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/data/morris-data.js"></script>-->
    			<script type="text/javascript" src="${pageContext.request.contextPath}/resources/components/template/dist/js/sb-admin-2.js"></script>
    		]]>
</jsp:text>
	</html>
</jsp:root>