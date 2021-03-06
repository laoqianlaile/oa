<%@page pageEncoding="UTF-8" import="com.suneee.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>选择用户 </title>
	<%@include file="/commons/include/form.jsp" %>
	<f:link href="tree/zTreeStyle.css"></f:link>
	<script type="text/javascript" 	src="${ctx}/js/tree/jquery.ztree.js"></script>
	<style  type="text/css"> .ztree {padding: 5px 0 0 5px;}</style>
	<script type="text/javascript">
		var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
		var isSingle=${isSingle};
		var scope =eval("("+dialog.get("scope")+")"); 
		
		var rol_Tree=null; 
		var org_Tree=null; 
		var pos_Tree=null; 
		var onl_Tree=null; 
		var accordion = null;
		var type ="";
		var typeVal="";
		if(scope){
			type = scope.type;
			typeVal = scope.value; 
		}

		
		//树展开层数
		var expandDepth = 1; 
		forbidF5("Chrome");//禁止刷新页面
		$(function(){
			//布局
			$("#defLayout").ligerLayout({
				 topHeight :10,
				 leftWidth: 180,
				 rightWidth: 156,
				 bottomHeight :40,
				 height: '90%',
				 allowTopResize:false,
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 onHeightChanged: heightChanged,
				 minLeftWidth:200,
				 allowLeftResize:false
			});
			
			var findStr = '';
			//快速查找
			$("input.quick-find").bind('keyup',function(){
				var str = $(this).val();
				if(!str)return;
				if(str==findStr)return;
				findStr = str;
				var  tbody = $("#userList"), 	
					 firstTr = $('tr.hidden',tbody);
				$("tr",tbody).each(function(){
					var me = $(this),
						span = $('span',me),
						spanStr = span.html();
					if(!spanStr)return true;						
					if(spanStr.indexOf(findStr)>-1){
						$(this).insertAfter(firstTr);
					}
				});
			});
			
			var height = $(".l-layout-center").height();
			$("#leftMemu").ligerAccordion({ height: height-40, speed: null });
		    accordion = $("#leftMemu").ligerGetAccordionManager();
		    $(".l-accordion-content").height($(".l-accordion-content").height()-5)
		    load_Org_Tree();
		    if(${isShowRole}){
		    	load_Rol_Tree();
		    }
		    if(${isShowPos}){
		    	load_Pos_Tree();
		    }
		    if(${isShowOnlineUser}){
		    	load_Onl_Tree();
		    } 
		    
		    heightChanged();
		    
		    handleSelects();
		    
		    var  src="${ctx}/platform/system/sysUser/selector.ht?isSingle=${isSingle}&type="+type+"&typeVal="+typeVal;
		    $("#userListFrame").attr("src",src);

		    
		});
		function heightChanged(options){
			if(options){
			    if (accordion && options.middleHeight - 28 > 0){
				    $("#SEARCH_BY_ORG").height(options.middleHeight - 163-50);
			    	if(${isShowRole}) 		 $("#SEARCH_BY_ROL").height(options.middleHeight - 183-75-7);
				    if(${isShowPos}) 		 $("#SEARCH_BY_POS").height(options.middleHeight - 140-70-5);
				    if(${isShowOnlineUser})  $("#SEARCH_BY_ONL").height(options.middleHeight -163 -50);
			        accordion.setHeight(options.middleHeight - 28);
			    }
			}else{
			    var height = $(".l-layout-center").height();
				$("#SEARCH_BY_ROL").height(height - 183-75-7);
			    if(${isShowRole}) 		$("#SEARCH_BY_ORG").height(height - 163-50);
			    if(${isShowPos}) 		$("#SEARCH_BY_POS").height(height - 140-70-5);
			    if(${isShowOnlineUser}) $("#SEARCH_BY_ONL").height(height - 163-50);
		    }
		}
		
		function setCenterTitle(title){
			
			$("#centerTitle").empty();
			$("#centerTitle").append(title);
			
		};
		
		function load_Pos_Tree(){
			var demId=$("#demPos").val();
			var setting = {
		    		data: {
						key : {
							name: "posName",
							title: "posName"
						},
						simpleData: {
							enable: true,
							idKey: "posId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
						var url="${ctx}/platform/system/sysUser/selector.ht";
						var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_POS%>&posId=" + treeNode.posId+"&type="+type+"&typeVal="+typeVal;
							$("#userListFrame").attr("src", url + p);
							setCenterTitle("按岗位查找:" + treeNode.posName);
						}
					}
				};
				var url="${ctx}/platform/system/position/getBySupOrgId.ht?type="+type+"&typeVal="+typeVal;
				var para= {demId : demId};
				$.post(url,para,function(result){
					pos_Tree = $.fn.zTree.init($("#SEARCH_BY_POS"), setting,result);
					if(expandDepth!=0)
					{
						pos_Tree.expandAll(false);
						var nodes = pos_Tree.getNodesByFilter(function(node){
							return (node.level < expandDepth);
						});
						if(nodes.length>0){
							for(var i=0;i<nodes.length;i++){
								pos_Tree.expandNode(nodes[i],true,false);
							}
						}
					}else pos_Tree.expandAll(true);
				});
		};
		
		//判断是否为子结点,以改变图标	
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode){
		  	 var children=treeNode.children;
			  	 if(children.length==0){
			  		treeNode.isParent=true;
			  		pos_Tree = $.fn.zTree.getZTreeObj("SEARCH_BY_POS");
			  		pos_Tree.updateNode(treeNode);
			  	 }
			}
		};
		
		function load_Org_Tree(){
			var value=$("#dem").val();
			var setting = {
					data: {
						key : {
							
							name: "orgName",
							title: "orgName"
						},
					
						simpleData: {
							enable: true,
							idKey: "orgId",
							pIdKey: "orgSupId",
							rootPId: -1
						}
					},
					async: {
						enable: true,
						url:"${ctx}/platform/system/sysOrg/getTreeData.ht?demId="+value+"&type="+type+"&typeVal="+typeVal,
						autoParam:["orgId","orgSupId"]
					},
					callback:{
						onClick: function(event, treeId, treeNode){
							var url="${ctx}/platform/system/sysUser/selector.ht";
							//var includSub=($("#includSub").attr("checked"))?1:0;//判断是否包含子类，该功能已去掉
							var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ORG%>&orgId="+treeNode.orgId+"&path="+treeNode.path+"&type="+type+"&typeVal="+typeVal;
							$("#userListFrame").attr("src",url+p);
							setCenterTitle("按组织查找:"+treeNode.orgName);
						},
						onAsyncSuccess: orgTreeOnAsyncSuccess
					}
					
				};
				org_Tree=$.fn.zTree.init($("#SEARCH_BY_ORG"), setting);
		};
		//判断是否为子结点,以改变图标	
		function orgTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode){
		  	 	var children=treeNode.children;
			  	 if(children.length==0){
			  		treeNode.isParent=true;
			  		org_Tree = $.fn.zTree.getZTreeObj("SEARCH_BY_ORG");
			  		org_Tree.updateNode(treeNode);
			  	 }
			}
		};
		function load_Rol_Tree(){
			var systemId=$("#systemId").val();
			var roleName=$("#Q_roleName_SL").val();
			var setting = {
		    		data: {
						key : {
							name: "roleName",
							title: "roleName"
						},
						simpleData: {
							enable: true,
							idKey: "roleId",
							rootPId: -1
						}
					},
		    		callback: {
						onClick: function(event, treeId, treeNode){
						var url="${ctx}/platform/system/sysUser/selector.ht";
						var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ROL%>&roleId=" + treeNode.roleId+"&type="+type+"&typeVal="+typeVal;
							$("#userListFrame").attr("src", url + p);
							setCenterTitle("按角色查找:" + treeNode.roleName);
						}
					}
				};
				var url="${ctx}/platform/system/sysRole/getAll.ht";
				var para= {systemId : systemId,Q_roleName_SL : roleName};
				$.post(url,para,function(result){
					rol_Tree = $.fn.zTree.init($("#SEARCH_BY_ROL"), setting,result);
					if(expandDepth!=0)
					{
						rol_Tree.expandAll(false);
						var nodes = rol_Tree.getNodesByFilter(function(node){
							return (node.level < expandDepth);
						});
						if(nodes.length>0){
							for(var i=0;i<nodes.length;i++){
								rol_Tree.expandNode(nodes[i],true,false);
							}
						}
					}else rol_Tree.expandAll(true);
				});
			};

			
			function load_Onl_Tree(){
				var value=$("#onl").val();
				var setting = {
			    		data: {
							key : {
								name: "orgName",
								title: "orgName"
							},
							simpleData: {
								enable: true,
								idKey: "orgId",
								pIdKey : "orgSupId",
								rootPId: -1
							}
						},
			    		callback: {
							onClick: function(event, treeId, treeNode){
								var url="${ctx}/platform/system/sysUser/selector.ht";
								var p="?isSingle=${isSingle}&searchBy=<%=SysUser.SEARCH_BY_ONL%>&path="+treeNode.path+"&type="+type+"&typeVal="+typeVal;
								$("#userListFrame").attr("src",url+p);
								setCenterTitle(treeNode.orgName);
							}
						}
			    };
				var url= "${ctx}/platform/system/sysOrg/getTreeOnlineData.ht?type="+type+"&typeVal="+typeVal;
				var para="demId=" + value;
				$.post(url,para,function(result){
					org_Tree = $.fn.zTree.init($("#SEARCH_BY_ONL"), setting,result);
					if(expandDepth!=0)
					{
						org_Tree.expandAll(false);
						var nodes = org_Tree.getNodesByFilter(function(node){
							return (node.level < expandDepth);
						});
						if(nodes.length>0){
							for(var i=0;i<nodes.length;i++){
								org_Tree.expandNode(nodes[i],true,false);
							}
						}
					}else org_Tree.expandAll(true);
				});
				
			};
				
				
			function dellAll() {
				$("#sysUserList").empty();
			};
			function del(obj) {
				var tr = $(obj).parents("tr");
				$(tr).remove();
			};
			
			function add(data,flag) {
				
				var aryTmp=data.split("#");
				var userId=aryTmp[0];
				if(flag){
					$("tr#user_" + userId).remove();
				}else{
					var len= $("#user_" + userId).length;
					if(len>0) return;
					var aryData=['<tr id="user_'+userId+'">',
						'<td>',
						'<input type="hidden" class="pk" name="userData" value="'+data +'"><span> ',
						aryTmp[2],
						'</span></td>',
						'<td><a onclick="javascript:del(this);" class="link del" ></a> </td>',
						'</tr>'];
					$("#sysUserList").append(aryData.join(""));
				}
			};
		
			function selectMulti(obj) {
				if ($(obj).attr("checked") == "checked"){
					var data = $(obj).val();
					add(data);
				}else{
					var data = $(obj).val();
					add(data);
				}	
			};
		
			function selectAll(obj) {
				var state = $(obj).attr("checked");
				var rtn=state == undefined?false:true;
				checkAll(rtn);
			};
		
			function checkAll(checked) {
				$("#userListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
					$(this).attr("checked", checked);
					if (checked) {
						var data = $(this).val();
						add(data);
					}else{
						var data = $(this).val();
						add(data);
					}
				});
			};
			
			function clearUser(){
				var rtn={
					userIds: '',
					fullnames: '',
					emails: '',
					mobiles: ''
				};
				dialog.get("sucCall")(rtn);
				dialog.close();
			}
			
			function selectUser(){
				var pleaseSelect= "请选择用户!";
				var chIds;
				if(isSingle==true){
					chIds = $('#userListFrame').contents().find(":input[name='userData'][checked]");
				
				}else{
					chIds = $("#sysUserList").find(":input[name='userData']");
				}
				
				if (!chIds||chIds.length == 0) {
					alert(pleaseSelect);
					return;
				}
				
				var aryuserIds=[];
				var aryAccounts=[];
				var aryfullnames=[];
				var aryemails=[];
				var arymobiles=[];
				var aryorgNames=[];
				$.each(chIds,function(i,ch){
					var aryTmp=$(ch).val().split("#");
					aryuserIds.push(aryTmp[0]);
					aryAccounts.push(aryTmp[1]);
					aryfullnames.push(aryTmp[2]);
					aryemails.push(aryTmp[3]);
					arymobiles.push(aryTmp[4]);
					aryorgNames.push(aryTmp[5]);
				});
				
				var obj={userIds:aryuserIds.join(","),accounts:aryAccounts.join(","), fullnames:aryfullnames.join(","),
						emails:aryemails.join(","),mobiles:arymobiles.join(","),orgNames:aryorgNames.join(",")};
				
				try{
					dialog.get("sucCall")(obj);
				}catch(e){
					
				}
				dialog.close();
			}
			
			var handleSelects=function(){
				//var    selectUsers    =    window.dialogArguments ;
				var    selectUsers    =dialog.get("selectUsers");
				debugger
				if(selectUsers.selectUserIds && selectUsers.selectUserNames){
					var ids=selectUsers.selectUserIds.split(","); 
					var names=selectUsers.selectUserNames.split(","); 
					for(var i=0;i<ids.length;i++){
						if(names[i]!=undefined&&names[i]!="undefined"&&names[i]!=null&&names[i]!=""){
							add(ids[i]+"##"+names[i]+"#");
						}
					}
				}
			}		
	</script>

<style type="text/css">
#defLayoutBox{padding:10px;}
.ztree {
	overflow: auto;
}
.label {
	color: #6F8DC6;
	text-align: right;
	padding-right: 6px;
	padding-left: 0px;
	font-weight: bold;
}
.quick-find {
	width:35px;
}
html { overflow-x: hidden; }
.l-layout-right{left:511px;}
.l-layout-left, .l-layout-center, .l-layout-right { height:85%;}
.l-accordion-content {}
.l-accordion-content .ztree {}	
.table-detail,.table-grid{border: 0}
</style>
</head>
<body>
<div id="defLayoutBox">
	<div id="defLayout">
		<div id="leftMemu"  class="panel-debox"  position="left" title="查询条件" style="overflow: auto; float:left;width: 100%; height:90%">
			<div title="按组织查找" style="overflow: hidden;">
				<table border="0" width="100%" class="table-detail b0">
					<tr >
						<td width="50px" nowrap="nowrap">维度:
						</td>
						<td>
							<select id="dem" name="dem" onchange="load_Org_Tree()" style="width:80%">
								<c:forEach var="demen" items="${demensionList}">
									<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div id="SEARCH_BY_ORG" class='ztree'></div>
			</div>
			<c:if test="${isShowPos}">
			<div title="按岗位查找" style="overflow: hidden;">
				<table border="0" width="100%" class="table-detail">
					<tr >
						<td  width="50px" nowrap="nowrap"><span class="label">维度:</span>
						</td>
						<td>
							<select id="demPos" name="demPos" onchange="load_Pos_Tree()"  style="width:80%">
								<c:forEach var="demen" items="${demensionList}">
									<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div id="SEARCH_BY_POS" class='ztree'></div>
			</div>
			</c:if>
			<c:if test="${isShowRole}">
			<div title="按角色查找" style="overflow: hidden;">
				<div class="tree-title" style="width: 100%;">
					<div class="panel-detail">
						<table border="0" width="100%" class="table-detail">
							<tr>
								<td  width="50px" nowrap="nowrap">系统:
								</td>
								<td colspan="2">
									<select id="systemId" name="systemId" onchange="load_Rol_Tree()" style="width:80%">
										<c:forEach var="sys" items="${subSystemList}">
											<option value="${sys.systemId}">${ sys.sysName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td>角色:
								</td>
								<td>
									<input id="Q_roleName_SL" name="Q_roleName_SL" type="text" style="width:80%">
								</td>
								<td>
									<a class="link detail" href="javascript:load_Rol_Tree();"  style="width:80%"></a>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="SEARCH_BY_ROL" class='ztree'></div>
			</div>
			</c:if>
			<c:if test="${isShowOnlineUser}">
			<div title="在线用户" style="overflow: hidden;">
				
				<table border="0" width="50px" class="table-detail">
					<tr >
						<td nowrap="nowrap"><span class="label">维度:</span>
						</td>
						<td style="width:70%;">
							<select id="onl" name="onl" onchange="load_Onl_Tree()" style="width:90%">
								<c:forEach var="demen" items="${demensionList}">
									<option  value="${demen.demId}" <c:if test="${demen.demId==1}">selected</c:if>>${ demen.demName}</option>
								</c:forEach>
							</select>								
						</td>							
					</tr>
				</table>
				
				<div id="SEARCH_BY_ONL" class='ztree'></div>
			</div>
			</c:if>
		</div>
		<div position="center">
			<div id="centerTitle" class="l-layout-header">全部用户</div>
			<iframe id="userListFrame" name="userListFrame" height="100%" width="100%" frameborder="0" ></iframe>
		</div>
		<c:if test="${isSingle==false}">
			<div position="right" style="overflow:auto;height:90%;width:160px;"  title="<span><a onclick='javascript:dellAll();' class='link del'>清空 </a></span>">
				<table width="145" id="sysUserList" class="table-grid table-list" 	id="0" cellpadding="1" cellspacing="1">
					<tbody id="userList">
       					<tr class="hidden"></tr>
       				</tbody>
				</table>
			</div>
		</c:if>
	</div>
	<div position="bottom"  class="bottom" style="margin-top:5px;">
		<%--<a href="javascript:;" class="button"  onclick="clearUser()"><span  class="iconfont cancel"  ></span><span>清空</span></a>--%>
		<a href="javascript:;" class="button" style="margin-left:10px;"  onclick="dialog.close()"><span class="iconfont cancel"></span><span >取消</span></a>
		<a href="javascript:;" class="button"  onclick="selectUser()" style="margin-right:10px;" ><span class="iconfont ok "></span><span >确定</span></a>

		<%--<a href="javascript:;" class="button"  onclick="clearUser()"><span  class="iconfont icon-init"  ></span><span>清空</span></a>
		<a href="javascript:;" class="button" style="margin-left:10px;"  onclick="dialog.close()"><span class="iconfont icon-close"></span><span >取消</span></a>
		<a href="javascript:;" class="button"  onclick="selectUser()" style="margin-right:10px;" ><span class="iconfont icon-zhengque "></span><span >确定</span></a>--%>
		
	</div>
	</div>
</body>
</html>


