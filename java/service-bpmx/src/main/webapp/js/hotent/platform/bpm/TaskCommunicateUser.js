/**
 * TaskCommunicateUser 流程节点中沟通人员选择。
 * conf:参数如下：
 * actDefId：流程定义ID
 * nodeId：流程节点Id
 * parentActDefId: 父流程定义Id
 * dialogWidth：窗口宽度，默认值650
 * dialogWidth：窗口宽度，默认值400
 * @param conf
 */ 

function TaskCommunicateUser(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmNodeSet/communicate.ht?actDefId="+conf.actDefId+"&nodeId=" + conf.nodeId+"&defId="+conf.defId+"&parentActDefId="+conf.parentActDefId;
	var dialogWidth=600;
	var dialogHeight=400;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:1,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	if(conf.parentActDefId){
		url += "&parentActDefId="+conf.parentActDefId;
	}
	url=url.getNewUrl();
	
	var that = this;
	/*KILLDIALOG*/
	DialogUtil.open({
		height:conf.dialogHeight,
		width: conf.dialogWidth,
		title : '沟通人员配置',
		url: url, 
		isResize: true,
		//自定义参数
		sucCall:function(rtn){
			if (conf.callback) {
				conf.callback.call(this,rtn);
			}
		}
	});
}