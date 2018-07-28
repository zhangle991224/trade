var mode = 2;



var $playBtn = $('.musicBox button');
var isAppMode = false;
$(function() {
    var music = document.getElementById('music');
    if (!music){return}
    var $musicTime = $('.musicTime');
    /**
     * 16:9展示最上方banner图
     */
    $('.logoPic').height($(document).width() * 0.562);
    // 监听音频事件
    $('#music').on('loadedmetadata durationchange', function() {
        // 监听音乐元文件是否加载完成
        if (music.duration) {
            $musicTime.text(transTime(music.duration));
        }
    }).on('timeupdate', function() {
        // 监听音乐播放时间变化
        var s = transTime(music.currentTime);
        $musicTime.text(s);
    });
    music.onended = function() {
        $('.play').toggleClass('pause');
    };
});
/**
 * 时间转换
 *
 * @param time 时间(秒)
 * @returns {String} 时间字串
 */
function transTime(time) {
    // 检查无效
    if (!time) {
        return '00分00秒';
    }

    // 转化时间
    var minute = Math.floor(time / 60);
    var second = Math.round(time % 60);

    // 组装字串
    var string = '';
    if (minute < 10) {
        string += '0';
    }
    string += minute + '分';
    if (second < 10) {
        string += '0';
    }
    string += second + '秒';

    // 返回字串
    return string;
}