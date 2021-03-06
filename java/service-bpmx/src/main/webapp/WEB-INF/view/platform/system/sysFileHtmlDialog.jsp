<%@ page import="com.suneee.core.util.AppConfigUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html ng-app="app" id="ng-app">
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
 <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${ctx}/js/jquery/jquery.js"></script>

<f:link href="Aqua/css/ligerui-all.css"></f:link>
<title>系统附件上传</title>
<f:link href="bootstrap/bootstrap.min.css"></f:link>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/angular/angular.js"></script>
<script type="text/javascript" src="${ctx}/js/angular/angular-sanitize.min.js"></script>
<script type="text/javascript" src="${ctx}/js/angular/angular-file-upload.js"></script>
<script type="text/javascript" src="${ctx}/js/angular/bootstrap.min.js"></script>

<!--[if lte IE 8]>
<script type="text/javascript" src="${ctx}/js/ie/es5-shim.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ie/es5-sham.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ie/respond.min.js"></script>
<![endif]-->

<style type="text/css">
.my-drop-zone { border: dotted 3px lightgray; }
td{vertical-align: middle!important;}
</style>
<script type="text/javascript">
	var type = '${param.type}',
		max = Number('param.max'),
		maxUploadSize = Number('param.maxUploadSize');
	function getData(){
		var scope = $("body").scope();
		return scope;
	}
	
	var app = angular.module("app", [ 'angularFileUpload' ]);
	angular.module('app').config(function ($sceProvider) {
	    $sceProvider.enabled(false);
	});
	app.controller('uploadCtrl',[ '$scope', 'FileUploader', function($scope, FileUploader) {
			var url ="${ctx}/platform/system/sysFile/fileUpload.ht";
			var uploader = $scope.uploader = new FileUploader({
				url : url
			});
			if(max&&typeof max=='number'){
				//上传文件数目上限过滤器
				uploader.filters.push({
					name : 'countFilter',
					fn : function(item, options) {
						var result = this.queue.length < max;
						!result&&($.ligerDialog.alertExt("提示信息","最多只能上传"+max+"个文件"));
						return result;
					}
				});	
			}
			if(type){
				var reg = new RegExp("^.*.("+type+")$");
				//上传文件的文件类型过滤器
				uploader.filters.push({
					name : 'typeFilter',
					fn : function(item, options) {
						var result = reg.test(item.name);
						!result&&($.ligerDialog.alertExt("文件类型只能是"+type,"提示信息"));
						return result;
					}
				});
			}
			if(maxUploadSize&&typeof maxUploadSize=='number'){
				//上传文件的大小过滤器 
				uploader.filters.push({
					name : 'sizeFilter',
					fn : function(item, options) {
						var result = item.size <= maxUploadSize;
						!result&&($.ligerDialog.alertExt("提示信息","单个文件大小不能超过"+maxUploadSize/1024/1024+"M"));
						return result;
					}
				});	
			}
			uploader.onSuccessItem = function(fileItem, response) {
				fileItem.json = {fileId:response.fileId,fileName:response.fileName};
	        };
	        uploader.onErrorItem = function(fileItem, response) {
	        	fileItem.errorMsg = "文件上传失败，详细信息请查看错误日志";
	        };
		}]);
</script>
</head>
<body ng-controller="uploadCtrl" nv-file-drop uploader="uploader" filters="countFilter,typeFilter,sizeFilter">
	<div class="container-fluid">
		<div class="row" style="margin: 0 10px;">
			<div class="pull-left" style="margin:15px 0 10px 10px;width:300px;">
				<input type="file" nv-file-select uploader="uploader" multiple />
				<div style="margin-top:10px;">
					<a href="javaScript:void(0)" ng-click="uploader.uploadAll()" ng-disabled="!uploader.getNotUploadedItems().length" class="btn btn-primary fa-save"><span>全部上传</span></a>
	                <a href="javaScript:void(0)" ng-click="uploader.cancelAll()" ng-disabled="!uploader.isUploading" class="btn btn-default fa-undo"><span>全部取消</span></a>
					<a href="javaScript:void(0)" ng-click="uploader.clearQueue()" ng-disabled="!uploader.queue.length" class="btn btn-danger fa-remove"><span>全部删除</span></a>
				</div>
			</div>
			<div class="pull-right" ng-if="uploader.isHTML5" style="margin-top:10px;width:410px;" nv-file-drop="" uploader="uploader">
                <div nv-file-over="" uploader="uploader" over-class="another-file-over-class" class="well my-drop-zone">
                   	<p>将文件拖拽至此区域</p>
                </div>
            </div>
        </div>
		<div class="row" style="margin: 0 10px;">
			<div class="easyui-panel" title="上传队列" style="padding: 0 10px;">
				<table class="table">
					<thead>
                        <tr>
                        	<th width="80">序号</th>
                            <th>文件</th>
                            <th width="100" ng-show="uploader.isHTML5">大小</th>
                            <th width="100" ng-show="uploader.isHTML5">进度</th>
                            <th width="50">状态</th>
                            <th width="200">操作</th>
                        </tr>
                    </thead>
					<tbody>
						<tr ng-repeat="item in uploader.queue">
							<td>{{$index+1}}</td>
							<td>
								<div style="width:200px;overflow:hidden;" title="{{ item.file.name }}">
									<strong>{{ item.file.name }}</strong>
								</div>
							</td>
							<td ng-show="uploader.isHTML5" nowrap><span>{{ item.file.size/1024/1024|number:2 }} MB</span></td>
							<td ng-show="uploader.isHTML5">
                                <div class="progress" style="margin-bottom: 0;">
                                    <div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
                                </div>
                            </td>
                            <td class="text-center">
                                <span ng-show="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span>
                                <span ng-show="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span>
                                <span ng-show="item.isError"><i class="glyphicon glyphicon-remove" title="{{item.errorMsg}}"></i></span>
                            </td>
                            <td nowrap>
	                            <a href="javaScript:void(0)" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess" class="btn btn-primary fa-save"><span>上传</span></a>
	                            <a href="javaScript:void(0)" ng-click="item.cancel()" ng-disabled="!item.isUploading" class="btn btn-default fa-undo"><span>取消</span></a>
								<a href="javaScript:void(0)" ng-click="item.remove()" class="btn btn-danger fa-remove"><span>删除</span></a>
                            </td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>