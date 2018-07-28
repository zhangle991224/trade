$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#contentImg', //绑定元素
        url: '/common/sysFile/upload', //上传接口
        accept: 'image/*',
        done: function (r) {
            layer.msg(r.msg);
            $('#goodsImg').val(r.fileName);
            var $img = $('#img');
            $img.attr('src', r.fileName);
            $('#img').attr('hidden', false);

        },
        error: function (r) {
            layer.msg(r.msg);
        }
    });
    $($('.layui-upload-file')[0]).attr('accept', 'image/*');

});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/trade/goods/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
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