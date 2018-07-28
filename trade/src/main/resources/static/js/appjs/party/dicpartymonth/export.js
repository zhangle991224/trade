$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		exportexcle();
	}
});
function exportexcle() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/party/dicpartymonth/export",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}

function changeType(th) {
    $('input[name=month]').val($(th).val());
}