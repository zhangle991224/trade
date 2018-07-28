

(function e(t, n, r) {
    function s(o, u) {
        if (!n[o]) {
            if (!t[o]) {
                var a = typeof require == "function" && require;
                if (!u && a) return a(o, !0);
                if (i) return i(o, !0);
                var f = new Error("Cannot find module '" + o + "'");
                throw f.code = "MODULE_NOT_FOUND", f;
            }
            var l = n[o] = {
                exports: {}
            };
            t[o][0].call(l.exports, function (e) {
                var n = t[o][1][e];
                return s(n ? n : e);
            }, l, l.exports, e, t, n, r);
        }
        return n[o].exports;
    }

    var i = typeof require == "function" && require;
    for (var o = 0; o < r.length; o++) s(r[o]);
    return s;
})({
    1: [function (require, module, exports) {
        var process = module.exports = {};
        process.nextTick = function () {
            var canSetImmediate = typeof window !== "undefined" && window.setImmediate;
            var canPost = typeof window !== "undefined" && window.postMessage && window.addEventListener;
            if (canSetImmediate) {
                return function (f) {
                    return window.setImmediate(f);
                };
            }
            if (canPost) {
                var queue = [];
                window.addEventListener("message", function (ev) {
                    var source = ev.source;
                    if ((source === window || source === null) && ev.data === "process-tick") {
                        ev.stopPropagation();
                        if (queue.length > 0) {
                            var fn = queue.shift();
                            fn();
                        }
                    }
                }, true);
                return function nextTick(fn) {
                    queue.push(fn);
                    window.postMessage("process-tick", "*");
                };
            }
            return function nextTick(fn) {
                setTimeout(fn, 0);
            };
        }();
        process.title = "browser";
        process.browser = true;
        process.env = {};
        process.argv = [];
        function noop() {
        }

        process.on = noop;
        process.addListener = noop;
        process.once = noop;
        process.off = noop;
        process.removeListener = noop;
        process.removeAllListeners = noop;
        process.emit = noop;
        process.binding = function (name) {
            throw new Error("process.binding is not supported");
        };
        process.cwd = function () {
            return "/";
        };
        process.chdir = function (dir) {
            throw new Error("process.chdir is not supported");
        };
    }, {}],
    2: [function (require, module, exports) {
        "use strict";
        var asap = require("asap");
        module.exports = Promise;
        function Promise(fn) {
            if (typeof this !== "object") throw new TypeError("Promises must be constructed via new");
            if (typeof fn !== "function") throw new TypeError("not a function");
            var state = null;
            var value = null;
            var deferreds = [];
            var self = this;
            this.then = function (onFulfilled, onRejected) {
                return new self.constructor(function (resolve, reject) {
                    handle(new Handler(onFulfilled, onRejected, resolve, reject));
                });
            };
            function handle(deferred) {
                if (state === null) {
                    deferreds.push(deferred);
                    return;
                }
                asap(function () {
                    var cb = state ? deferred.onFulfilled : deferred.onRejected;
                    if (cb === null) {
                        (state ? deferred.resolve : deferred.reject)(value);
                        return;
                    }
                    var ret;
                    try {
                        ret = cb(value);
                    } catch (e) {
                        deferred.reject(e);
                        return;
                    }
                    deferred.resolve(ret);
                });
            }

            function resolve(newValue) {
                try {
                    if (newValue === self) throw new TypeError("A promise cannot be resolved with itself.");
                    if (newValue && (typeof newValue === "object" || typeof newValue === "function")) {
                        var then = newValue.then;
                        if (typeof then === "function") {
                            doResolve(then.bind(newValue), resolve, reject);
                            return;
                        }
                    }
                    state = true;
                    value = newValue;
                    finale();
                } catch (e) {
                    reject(e);
                }
            }

            function reject(newValue) {
                state = false;
                value = newValue;
                finale();
            }

            function finale() {
                for (var i = 0, len = deferreds.length; i < len; i++) handle(deferreds[i]);
                deferreds = null;
            }

            doResolve(fn, resolve, reject);
        }

        function Handler(onFulfilled, onRejected, resolve, reject) {
            this.onFulfilled = typeof onFulfilled === "function" ? onFulfilled : null;
            this.onRejected = typeof onRejected === "function" ? onRejected : null;
            this.resolve = resolve;
            this.reject = reject;
        }

        function doResolve(fn, onFulfilled, onRejected) {
            var done = false;
            try {
                fn(function (value) {
                    if (done) return;
                    done = true;
                    onFulfilled(value);
                }, function (reason) {
                    if (done) return;
                    done = true;
                    onRejected(reason);
                });
            } catch (ex) {
                if (done) return;
                done = true;
                onRejected(ex);
            }
        }
    }, {
        asap: 4
    }],
    3: [function (require, module, exports) {
        "use strict";
        var Promise = require("./core.js");
        var asap = require("asap");
        module.exports = Promise;
        function ValuePromise(value) {
            this.then = function (onFulfilled) {
                if (typeof onFulfilled !== "function") return this;
                return new Promise(function (resolve, reject) {
                    asap(function () {
                        try {
                            resolve(onFulfilled(value));
                        } catch (ex) {
                            reject(ex);
                        }
                    });
                });
            };
        }

        ValuePromise.prototype = Promise.prototype;
        var TRUE = new ValuePromise(true);
        var FALSE = new ValuePromise(false);
        var NULL = new ValuePromise(null);
        var UNDEFINED = new ValuePromise(undefined);
        var ZERO = new ValuePromise(0);
        var EMPTYSTRING = new ValuePromise("");
        Promise.resolve = function (value) {
            if (value instanceof Promise) return value;
            if (value === null) return NULL;
            if (value === undefined) return UNDEFINED;
            if (value === true) return TRUE;
            if (value === false) return FALSE;
            if (value === 0) return ZERO;
            if (value === "") return EMPTYSTRING;
            if (typeof value === "object" || typeof value === "function") {
                try {
                    var then = value.then;
                    if (typeof then === "function") {
                        return new Promise(then.bind(value));
                    }
                } catch (ex) {
                    return new Promise(function (resolve, reject) {
                        reject(ex);
                    });
                }
            }
            return new ValuePromise(value);
        };
        Promise.all = function (arr) {
            var args = Array.prototype.slice.call(arr);
            return new Promise(function (resolve, reject) {
                if (args.length === 0) return resolve([]);
                var remaining = args.length;

                function res(i, val) {
                    try {
                        if (val && (typeof val === "object" || typeof val === "function")) {
                            var then = val.then;
                            if (typeof then === "function") {
                                then.call(val, function (val) {
                                    res(i, val);
                                }, reject);
                                return;
                            }
                        }
                        args[i] = val;
                        if (--remaining === 0) {
                            resolve(args);
                        }
                    } catch (ex) {
                        reject(ex);
                    }
                }

                for (var i = 0; i < args.length; i++) {
                    res(i, args[i]);
                }
            });
        };
        Promise.reject = function (value) {
            return new Promise(function (resolve, reject) {
                reject(value);
            });
        };
        Promise.race = function (values) {
            return new Promise(function (resolve, reject) {
                values.forEach(function (value) {
                    Promise.resolve(value).then(resolve, reject);
                });
            });
        };
        Promise.prototype["catch"] = function (onRejected) {
            return this.then(null, onRejected);
        };
    }, {
        "./core.js": 2,
        asap: 4
    }],
    4: [function (require, module, exports) {
        (function (process) {
            var head = {
                task: void 0,
                next: null
            };
            var tail = head;
            var flushing = false;
            var requestFlush = void 0;
            var isNodeJS = false;

            function flush() {
                while (head.next) {
                    head = head.next;
                    var task = head.task;
                    head.task = void 0;
                    var domain = head.domain;
                    if (domain) {
                        head.domain = void 0;
                        domain.enter();
                    }
                    try {
                        task();
                    } catch (e) {
                        if (isNodeJS) {
                            if (domain) {
                                domain.exit();
                            }
                            setTimeout(flush, 0);
                            if (domain) {
                                domain.enter();
                            }
                            throw e;
                        } else {
                            setTimeout(function () {
                                throw e;
                            }, 0);
                        }
                    }
                    if (domain) {
                        domain.exit();
                    }
                }
                flushing = false;
            }

            if (typeof process !== "undefined" && process.nextTick) {
                isNodeJS = true;
                requestFlush = function () {
                    process.nextTick(flush);
                };
            } else if (typeof setImmediate === "function") {
                if (typeof window !== "undefined") {
                    requestFlush = setImmediate.bind(window, flush);
                } else {
                    requestFlush = function () {
                        setImmediate(flush);
                    };
                }
            } else if (typeof MessageChannel !== "undefined") {
                var channel = new MessageChannel();
                channel.port1.onmessage = flush;
                requestFlush = function () {
                    channel.port2.postMessage(0);
                };
            } else {
                requestFlush = function () {
                    setTimeout(flush, 0);
                };
            }
            function asap(task) {
                tail = tail.next = {
                    task: task,
                    domain: isNodeJS && process.domain,
                    next: null
                };
                if (!flushing) {
                    flushing = true;
                    requestFlush();
                }
            }

            module.exports = asap;
        }).call(this, require("_process"));
    }, {
        _process: 1
    }],
    5: [function (require, module, exports) {
        if (typeof Promise.prototype.done !== "function") {
            Promise.prototype.done = function (onFulfilled, onRejected) {
                var self = arguments.length ? this.then.apply(this, arguments) : this;
                self.then(null, function (err) {
                    setTimeout(function () {
                        throw err;
                    }, 0);
                });
            };
        }
    }, {}],
    6: [function (require, module, exports) {
        var asap = require("asap");
        if (typeof Promise === "undefined") {
            Promise = require("./lib/core.js");
            require("./lib/es6-extensions.js");
        }
        require("./polyfill-done.js");
    }, {
        "./lib/core.js": 2,
        "./lib/es6-extensions.js": 3,
        "./polyfill-done.js": 5,
        asap: 4
    }]
}, {}, [6]);


/**
 * select menu 控件
 *
 * @author 景烁
 * @created 2014-07-31
 *
 * @requires lib.scroll
 *
 */

;
(function (win, ctrl) {

    // 辅助方法：负责窗口弹出和收起
    var SelectBox = {
        open: function (selectContainer, onOpen) {
            if (!selectContainer) return;

            var selectBox = selectContainer.firstChild;

            selectContainer.style.opacity = 1;
            selectContainer.style.visibility = 'visible';

            // fix android 展示不出 selectContainer
            setTimeout(function () {
                selectBox.style.webkitTransform = 'translateY(0)';
                onOpen && onOpen();
            }, 0);
        },

        close: function (selectContainer, onClose) {
            if (!selectContainer) return;

            var selectBox = selectContainer.firstChild;

            selectBox.style.webkitTransform = 'translateY(100%)';

            setTimeout(function () {
                selectContainer.style.opacity = 0;
                selectContainer.style.visibility = 'hidden';
                onClose && onClose();
            }, 200);
        }
    };

    // main
    var incId = 0;
    var Klass = {
        container: 'ctrlSelectmenu',
        innerBox: 'ctrlSelectmenuPicker',
        header: 'ctrlSelectmenuHeader',
        confirm: 'ctrlSelectmenuBtnConfirm',
        cancel: 'ctrlSelectmenuBtnCancel',
        col: 'ctrlSelectmenuCol',
        option: 'ctrlSelectmenuOption',
        wrapper: 'ctrlSelectmenuWrapper',
    };

    var SelectMenu = function (element, options) {

        /**
         控件规范
         */

        var that = this;

        var id = Date.now() + '-' + (++incId); // 每个控件有唯一的id
        var root = document.createDocumentFragment(); // 文档片段，用于append时提高效率

        if (arguments.length === 1 && !(arguments[0] instanceof HTMLElement)) {
            // 参数个数的兼容判断
            options = arguments[0];
            element = null;
        }
        if (!element) {
            // 如果不是从已有DOM元素创建控件，则可以新建一个元素
            element = document.createElement('div');
        }
        root.appendChild(element);

        element.setAttribute('data-ctrl-name', 'selectmenu');
        element.setAttribute('data-ctrl-id', id);
        element.className = Klass.container;

        // 全局配置
        this.options = options;

        // --- 编写控件的属性

        // 编写控件的属性
        var title = options.title || '';
        Object.defineProperty(this, 'title', {
            get: function () {
                return title;
            },

            set: function (v) {
                if (v) {
                    title = v;
                    element.querySelector('.tip').innerHTML = v;
                } else {
                    throw new Error('Non expected value');
                }
            }
        });
        var confirmText = options.confirmText || '确定';
        Object.defineProperty(this, 'confirmText', {
            get: function () {
                return confirmText;
            },

            set: function (v) {
                if (v) {
                    confirmText = v;
                    element.querySelector('.' + Klass.confirm).innerHTML = v;
                } else {
                    throw new Error('Non expected value');
                }
            }
        });
        var cancelText = options.cancelText || '取消';
        Object.defineProperty(this, 'cancelText', {
            get: function () {
                return cancelText;
            },

            set: function (v) {
                if (v) {
                    cancelText = v;
                    element.querySelector('.' + Klass.cancel).innerHTML = v;
                } else {
                    throw new Error('Non expected value');
                }
            }
        });


        // -- vm属性的获取和设置

        var viewModel;
        Object.defineProperty(this, 'viewModel', {
            get: function () {
                return viewModel;
            },

            set: function (v) {
                if (v) {
                    viewModel = v;
                    that.syncViewModel(); // 自动同步数据和视图
                } else {
                    throw new Error('Non expected value');
                }
            }
        });

        // 同步数据和视图的方法
        this.syncViewModel = function () {
            var that = this;

            // 重新渲染
            this.render();
            // todo: 待验证是否必须。 延迟，等待dom ready
            setTimeout(function () {
                that.addEvents();
            }, 100);
        };

        // --- 事件代理
        this._events = {};

        this.addEventListener = function (type, fn) {
            if (!this._events[type]) {
                this._events[type] = [];
            }

            this._events[type].push(fn);
        };

        this.removeEventListener = function (type, fn) {
            if (!this._events[type]) {
                return;
            }

            var index = this._events[type].indexOf(fn);

            if (index > -1) {
                this._events[type].splice(index, 1);
            }
        };

        // 后可带参数
        // 执行自定义事件绑定
        this.execEvent = function (type) {
            if (!this._events[type]) {
                return;
            }

            var i = 0,
                l = this._events[type].length;

            if (!l) {
                return;
            }

            for (; i < l; i++) {
                this._events[type][i].apply(this, [].slice.call(arguments, 1));
            }
        };

        // 移除控件元素
        this.remove = function () {
            if (element.parentNode) {
                element.parentNode.removeChild(element);
            }
        };

        this.element = element;
        this.root = root;

        /**
         业务代码
         */
        var myScroll = {};
        var currentOptions = {};
        var selectedValue = {};

        // -- 拼装 select dom
        this.render = function () {
            var selectData = viewModel;

            var headHtm = ['<div class="' + Klass.header + '">',
                '<a href="javascript:void(0);" class="' + Klass.cancel + '">' + cancelText + '</a>',
                '<span class="tip">' + title + '</span>',
                '<a href="javascript:void(0);" class="' + Klass.confirm + '">' + confirmText + '</a>',
                '</div>'].join('');

            var bodyHtm = '<div class="' + Klass.wrapper + '"></div>';

            this.element.innerHTML = '' +
            '<div class="' + Klass.innerBox + '">' +
            headHtm +
            bodyHtm +
            '</div>';

            for (var key in selectData) {
                this.renderColumn(key, selectData[key]);
            }

            // 刷新
            this.refresh();
        };

        // 选择数据集
        this.selectedIndex = {};
        this.renderColumn = function (key, dataList) {
            var that = this;

            if (Array.isArray(dataList)) {
                var selectDomHtm = [];

                // 默认选中是第一个
                that.selectedIndex[key] = 0;
                dataList.forEach(function (item, index) {
                    if (item.selected) {
                        that.selectedIndex[key] = index;
                    }

                    selectDomHtm.push(
                        '<div class="' + Klass.option + '" data-value="' + item.value + '">' + item.key + '</div>'
                    );
                });

                var selectHTML = selectDomHtm.join('');

                var parentNode = that.element.querySelector('.' + Klass.wrapper);
                var oldEl = parentNode.querySelector('[data-type=' + key + ']');

                if (oldEl) {
                    oldEl.firstChild.innerHTML = selectHTML;
                } else {
                    var columnHtml = '<div class="scroller">' + selectHTML + '</div>';
                    var colEl = document.createElement('div');
                    colEl.className = Klass.col;
                    colEl.setAttribute('data-type', key);
                    colEl.innerHTML = columnHtml;

                    parentNode.appendChild(colEl);
                }
            } else {
                throw ('select项数据列表必须是数组');
            }
        };

        // --- scroll 操作

        this.setScroll = function () {
            var that = this;

            var selects = [].slice.call(this.element.querySelectorAll('.' + Klass.col));
            selects.forEach(function (value) {
                var scrollElement = value.querySelector('.scroller');

                var scroll = lib.scroll({
                    scrollElement: scrollElement,
                    inertia: 'slow'
                }).init();

                scrollElement.addEventListener('click', function (e) {
                    var target = e.target;
                    if (target.className.indexOf(Klass.option) === 0) {
                        scroll.scrollToElement(target, true);
                    }
                }, false);

                scroll.addScrollendHandler(function () {
                    that.scrollEndHandler(scroll, value);
                });

                var type = value.getAttribute('data-type');

                // 初始化滚动位置
                that.scrollToNthChild(scroll, that.selectedIndex[type]);

                // 增加是否需要触发事件处理器flag, 默认 true
                scroll.handler = true;

                myScroll[type] = scroll;
            });

            // cache
            this.selects = selects;
            this.scrolls = myScroll;
        };

        // 暴露当前操作的列名
        this.currentColName = '';
        this.scrollEndHandler = function (scrollObj, container) {
            if (!scrollObj.handler) {
                // reset to true
                scrollObj.handler = true;
                return;
            }

            var scrollTop = scrollObj.getScrollTop();

            // 修正按一行一行滚动
            var scrolled = Math.round(scrollTop / this.height);
            scrollObj.handler = false;
            this.scrollToNthChild(scrollObj, scrolled, false);

            var type = container.getAttribute('data-type');
            this.currentColName = type;

            this.setSelectedStatus(type, Math.abs(scrolled));

            // 选中值
            this.execEvent('select', type);
        };
        this.setSelectedStatus = function (colName, selectedIndex) {
            var container = this.scrolls[colName].element;

            var options = container.querySelectorAll('.' + Klass.option);
            currentOptions[colName] = container.querySelector('.current');
            currentOptions[colName] && (currentOptions[colName].className = Klass.option);

            currentOptions[colName] = options[selectedIndex];
            currentOptions[colName].className = Klass.option + ' current';

            selectedValue['val-' + colName] = currentOptions[colName].getAttribute('data-value');
            selectedValue['key-' + colName] = currentOptions[colName].innerHTML;

            // public
            this.selectedValue = selectedValue;
            this.selectedIndex[colName] = selectedIndex;
        };
        // 调整选中状态
        this.resetScrollPos = function () {
            var that = this;

            this.selects && this.selects.forEach(function (value) {
                var type = value.getAttribute('data-type');
                var scroll = that.scrolls[type];
                that.scrollToNthChild(scroll, that.selectedIndex[type]);
            })
        };

        // 滚动到某个元素
        this.scrollToNthChild = function (scroll, index, smooth) {
            var el = scroll.element;

            var target = el.querySelector(':nth-child(' + (1 + index) + ')');

            var isSmooth = typeof smooth != 'undefined' ? smooth : true;
            scroll.scrollToElement(target, isSmooth);
        };

        // -- 事件监听

        var scrollHasSet = false;
        var inited = false;
        this.addEvents = function () {
            var opts = this.options;
            var that = this;

            if (!inited) {

                // 兼容tbm：同步 tbm resize 监听 resize
                var timeout;

                function resize() {
                    that.refresh();
                }

                window.addEventListener('resize', function () {
                    clearTimeout(timeout);
                    timeout = setTimeout(resize, 310);
                }, false);

                if (opts.trigger) {
                    var triggers = opts.trigger.length > 1 ? [].slice.call(opts.trigger) : [opts.trigger];
                    triggers.forEach(function (value) {
                        value.addEventListener('click', function () {
                            // cache 触发器
                            that.trigger = value;
                            that.show();
                        });
                    });
                }
                inited = true;
            }

            // 确定
            this.element.querySelector('.' + Klass.confirm).addEventListener('click', function () {

                if (opts.verifyMe) {
                    if (opts.verifyMe(that.selectedValue)) {
                        that.hide();
                    } else {
                        return;
                    }
                } else {
                    that.hide();
                }

                // confirm select
                that.execEvent('confirm', that.trigger);
            }, false);

            // 取消
            this.element.querySelector('.' + Klass.cancel).addEventListener('click', function () {
                // cancel select
                that.execEvent('cancel', that.trigger);

                that.hide();
            }, false);
        };


        // --  接口方法


        this.show = function () {
            var that = this;

            // 展示之前
            this.execEvent('beforeShow', that.trigger);

            SelectBox.open(this.element);

            // 设置scroll
            setTimeout(function () {
                if (!scrollHasSet) {
                    that.setScroll();
                    scrollHasSet = true;
                }
            }, 100);

            this.execEvent('show');
        };

        this.hide = function () {
            SelectBox.close(this.element);
            this.execEvent('hide');
        };

        // 当窗口 resize 时调用
        this.refresh = function () {
            // fix 浮出窗口跑顶上去了
            this.element.style.height = window.innerHeight + 'px';

            var that = this;
            var getHeightInterval = setInterval(function () {
                var optionClass = '.' + Klass.option;
                var el = document.querySelector(optionClass);

                if (el && el.clientHeight) {
                    that.height = el.clientHeight;
                    that.resetScrollPos();

                    clearInterval(getHeightInterval);
                    getHeightInterval = null;
                }
            }, 100);
        };

        // 触发联动
        this.linkage = function (colName, colData) {
            this.viewModel[colName] = colData;
            this.renderColumn(colName, colData);

            // refresh scroll
            var scroll = this.scrolls[colName];
            var selectedIndex = this.selectedIndex[colName];
            scroll.refresh();
            this.scrollToNthChild(scroll, selectedIndex);
        }

        // 滚动选择值
        this.scrollToIndex = function (colName, index) {
            var scroll = this.scrolls[colName];
            this.scrollToNthChild(scroll, index);
            this.setSelectedStatus(colName, index);
        };
    };

    // export
    ctrl.selectmenu = SelectMenu;

})(window, window['ctrl'] || (window['ctrl'] = {}));

!function (a) {
    "use strict";
    function b(a, b) {
        for (var c = a; c;) {
            if (c.contains(b) || c == b)return c;
            c = c.parentNode
        }
        return null
    }

    function c(a, b, c) {
        var d = i.createEvent("HTMLEvents");
        if (d.initEvent(b, !0, !0), "object" == typeof c)for (var e in c)d[e] = c[e];
        a.dispatchEvent(d)
    }

    function d(a, b, c, d, e, f, g, h) {
        var i = Math.atan2(h - f, g - e) - Math.atan2(d - b, c - a), j = Math.sqrt((Math.pow(h - f, 2) + Math.pow(g - e, 2)) / (Math.pow(d - b, 2) + Math.pow(c - a, 2))), k = [e - j * a * Math.cos(i) + j * b * Math.sin(i), f - j * b * Math.cos(i) - j * a * Math.sin(i)];
        return {
            rotate: i,
            scale: j,
            translate: k,
            matrix: [[j * Math.cos(i), -j * Math.sin(i), k[0]], [j * Math.sin(i), j * Math.cos(i), k[1]], [0, 0, 1]]
        }
    }

    function e(a) {
        0 === Object.keys(l).length && (j.addEventListener("touchmove", f, !1), j.addEventListener("touchend", g, !1), j.addEventListener("touchcancel", h, !1));
        for (var d = 0; d < a.changedTouches.length; d++) {
            var e = a.changedTouches[d], i = {};
            for (var m in e)i[m] = e[m];
            var n = {
                startTouch: i,
                startTime: Date.now(),
                status: "tapping",
                element: a.srcElement || a.target,
                pressingHandler: setTimeout(function (b) {
                    return function () {
                        "tapping" === n.status && (n.status = "pressing", c(b, "press", {touchEvent: a})), clearTimeout(n.pressingHandler), n.pressingHandler = null
                    }
                }(a.srcElement || a.target), 500)
            };
            l[e.identifier] = n
        }
        if (2 == Object.keys(l).length) {
            var o = [];
            for (var m in l)o.push(l[m].element);
            c(b(o[0], o[1]), "dualtouchstart", {touches: k.call(a.touches), touchEvent: a})
        }
    }

    function f(a) {
        for (var e = 0; e < a.changedTouches.length; e++) {
            var f = a.changedTouches[e], g = l[f.identifier];
            if (!g)return;
            g.lastTouch || (g.lastTouch = g.startTouch), g.lastTime || (g.lastTime = g.startTime), g.velocityX || (g.velocityX = 0), g.velocityY || (g.velocityY = 0), g.duration || (g.duration = 0);
            var h = Date.now() - g.lastTime, i = (f.clientX - g.lastTouch.clientX) / h, j = (f.clientY - g.lastTouch.clientY) / h, k = 70;
            h > k && (h = k), g.duration + h > k && (g.duration = k - h), g.velocityX = (g.velocityX * g.duration + i * h) / (g.duration + h), g.velocityY = (g.velocityY * g.duration + j * h) / (g.duration + h), g.duration += h, g.lastTouch = {};
            for (var m in f)g.lastTouch[m] = f[m];
            g.lastTime = Date.now();
            var n = f.clientX - g.startTouch.clientX, o = f.clientY - g.startTouch.clientY, p = Math.sqrt(Math.pow(n, 2) + Math.pow(o, 2));
            ("tapping" === g.status || "pressing" === g.status) && p > 10 && (g.status = "panning", g.isVertical = !(Math.abs(n) > Math.abs(o)), c(g.element, "panstart", {
                touch: f,
                touchEvent: a,
                isVertical: g.isVertical
            }), c(g.element, (g.isVertical ? "vertical" : "horizontal") + "panstart", {
                touch: f,
                touchEvent: a
            })), "panning" === g.status && (g.panTime = Date.now(), c(g.element, "pan", {
                displacementX: n,
                displacementY: o,
                touch: f,
                touchEvent: a,
                isVertical: g.isVertical
            }), g.isVertical ? c(g.element, "verticalpan", {
                displacementY: o,
                touch: f,
                touchEvent: a
            }) : c(g.element, "horizontalpan", {displacementX: n, touch: f, touchEvent: a}))
        }
        if (2 == Object.keys(l).length) {
            for (var q, r = [], s = [], t = [], e = 0; e < a.touches.length; e++) {
                var f = a.touches[e], g = l[f.identifier];
                r.push([g.startTouch.clientX, g.startTouch.clientY]), s.push([f.clientX, f.clientY])
            }
            for (var m in l)t.push(l[m].element);
            q = d(r[0][0], r[0][1], r[1][0], r[1][1], s[0][0], s[0][1], s[1][0], s[1][1]), c(b(t[0], t[1]), "dualtouch", {
                transform: q,
                touches: a.touches,
                touchEvent: a
            })
        }
    }

    function g(a) {
        /*if (2 == Object.keys(l).length) {
         var d = [];
         for (var e in l)d.push(l[e].element);
         c(b(d[0], d[1]), "dualtouchend", {touches: k.call(a.touches), touchEvent: a})
         }
         for (var i = 0; i < a.changedTouches.length; i++) {
         var n = a.changedTouches[i], o = n.identifier, p = l[o];
         if (p) {
         if (p.pressingHandler && (clearTimeout(p.pressingHandler), p.pressingHandler = null), "tapping" === p.status && (p.timestamp = Date.now(), c(p.element, "tap", {
         touch: n,
         touchEvent: a
         }), m && p.timestamp - m.timestamp < 300 && c(p.element, "doubletap", {
         touch: n,
         touchEvent: a
         }), m = p), "panning" === p.status) {
         var q = Date.now(), r = q - p.startTime, s = ((n.clientX - p.startTouch.clientX) / r, (n.clientY - p.startTouch.clientY) / r, n.clientX - p.startTouch.clientX), t = n.clientY - p.startTouch.clientY, u = Math.sqrt(p.velocityY * p.velocityY + p.velocityX * p.velocityX), v = u > .5 && q - p.lastTime < 100, w = {
         duration: r,
         isflick: v,
         velocityX: p.velocityX,
         velocityY: p.velocityY,
         displacementX: s,
         displacementY: t,
         touch: n,
         touchEvent: a,
         isVertical: p.isVertical
         };
         c(p.element, "panend", w), v && (c(p.element, "flick", w), p.isVertical ? c(p.element, "verticalflick", w) : c(p.element, "horizontalflick", w))
         }
         "pressing" === p.status && c(p.element, "pressend", {touch: n, touchEvent: a}), delete l[o]
         }
         }
         0 === Object.keys(l).length && (j.removeEventListener("touchmove", f, !1), j.removeEventListener("touchend", g, !1), j.removeEventListener("touchcancel", h, !1))*/
    }

    function h(a) {
        /*if (2 == Object.keys(l).length) {
         var d = [];
         for (var e in l)d.push(l[e].element);
         c(b(d[0], d[1]), "dualtouchend", {touches: k.call(a.touches), touchEvent: a})
         }
         for (var i = 0; i < a.changedTouches.length; i++) {
         var m = a.changedTouches[i], n = m.identifier, o = l[n];
         o && (o.pressingHandler && (clearTimeout(o.pressingHandler), o.pressingHandler = null), "panning" === o.status && c(o.element, "panend", {
         touch: m,
         touchEvent: a
         }), "pressing" === o.status && c(o.element, "pressend", {touch: m, touchEvent: a}), delete l[n])
         }
         0 === Object.keys(l).length && (j.removeEventListener("touchmove", f, !1), j.removeEventListener("touchend", g, !1), j.removeEventListener("touchcancel", h, !1))*/
    }

    var i = a.document, j = i.documentElement, k = Array.prototype.slice, l = {}, m = null;
    j.addEventListener("touchstart", e, !1)
}(window, window.lib || (window.lib = {}));

(function (win, ctrl) {
    var Promise = win.Promise;
    var isIEMobile = win.navigator.userAgent.match(/IEMobile\/([\d\.]+)/);
    var stylePrefix = !!isIEMobile ? 'ms' : 'webkit';

    var incId = 0;

    /**
     * 初始化加载动画实例
     * @class ctrl.loading~Loading
     * @param {HTMLElement} [element] - 初始化的元素，可省略
     */
    function Loading(element, options) {
        var that = this;
        var id = Date.now() + '-' + (++incId);
        var root = document.createDocumentFragment();

        if (arguments.length === 1 && !(arguments[0] instanceof HTMLElement)) {
            options = arguments[0];
            element = null;
        }
        if (!element) {
            element = document.createElement('div');
            root.appendChild(element);
        }
        options = options || {};

        element.setAttribute('data-ctrl-name', 'loading');
        element.setAttribute('data-ctrl-id', id);
        element.innerHTML = '<div rol="draw">' +
        '<canvas></canvas><span class="arrow"></span>' +
        '</div>' +
        '<div rol="spin">' +
        '<div class="circle"><span></span></div>' +
        '</div>' +
        '<span class="text"></span>';

        var canvas, context, radius, lineWidth, startAngle, perAngle, isInitCanvas = false;
        var canvasPixelRatio = 2;

        function initCanvas() {
            if (!isInitCanvas) {
                isInitCanvas = true;
                canvas = element.querySelector('canvas');
                context = canvas.getContext('2d');
                startAngle = 0.13373158940994154;
                perAngle = 0.06015722128359704;
            }

            var rect = canvas.getBoundingClientRect();
            if (canvas.width !== rect.width * canvasPixelRatio ||
                canvas.height !== rect.height * canvasPixelRatio) {
                canvas.width = rect.width * canvasPixelRatio;
                canvas.height = rect.height * canvasPixelRatio;
                radius = rect.width / 2;
                lineWidth = radius / 15;
            }
        }

        var drawEl = element.querySelector('[rol="draw"]');
        var spinEl = element.querySelector('[rol="spin"]');

        function draw(per) {
            if (mode !== 'draw') return;

            initCanvas();

            spinEl.style.display = 'none';
            drawEl.style.display = 'block';

            if (per > 100) {
                per = 100;
            }

            context.clearRect(0, 0, canvas.width * canvasPixelRatio, canvas.height * canvasPixelRatio);
            context.beginPath();
            context.arc(radius * canvasPixelRatio, radius * canvasPixelRatio, (radius - lineWidth) * canvasPixelRatio, -startAngle - Math.PI / 2, -startAngle - Math.PI / 2 - perAngle * per, true);
            context.lineWidth = lineWidth * canvasPixelRatio;
            context.strokeStyle = '#999';
            context.stroke();
            context.closePath();
        }

        function spin() {
            if (mode !== 'spin') return;

            spinEl.style.display = 'block';
            drawEl.style.display = 'none';
        }

        function showArrow() {
            var arrow = element.querySelector('.arrow');
            arrow.style.cssText = 'display: block';

            return Promise.resolve();
        }

        function hideArrow() {
            var arrow = element.querySelector('.arrow');
            // arrow.style.cssText = 'display: none';

            arrow.style[stylePrefix + 'Transform'] = 'scale(1)';
            arrow.style.opacity = '1';

            return new lib.animation(400, lib.cubicbezier.easeIn, function (i1, i2) {
                arrow.style[stylePrefix + 'Transform'] = 'scale(' + (1 - 0.5 * i2) + ')';
                arrow.style.opacity = (1 - i2) + '';
            }).play().then(function () {
                    arrow.style.cssText = 'display:none';
                });
        }

        /**
         * 获取/设置背景色
         * @member {String} bgcolor
         * @memberof ctrl.loading~Loading
         * @instance
         */
        Object.defineProperty(this, 'bgcolor', {
            get: function () {
                return element.style.backgroundColor;
            },
            set: function (v) {
                if (typeof v !== 'string') {
                    throw new Error('Non expected value');
                } else {
                    element.querySelector('[rol="spin"] span').style.backgroundColor = v;
                    element.style.backgroundColor = v;
                }
            }
        });

        /**
         * 获取/设置文本
         * @member {String} text
         * @memberof ctrl.loading~Loading
         * @instance
         */
        Object.defineProperty(this, 'text', {
            get: function () {
                return element.querySelector('.text').textContent;
            },
            set: function (v) {
                if (typeof v !== 'string') {
                    throw new Error('Non expected value');
                } else {
                    var divEl = element.querySelector('div');
                    var textEl = element.querySelector('.text');
                    if (v) {
                        element.style[stylePrefix + 'BoxPack'] = '';
                        divEl.style.marginLeft = '';
                        textEl.style.display = 'block';
                        textEl.textContent = v;
                    } else {
                        element.style[stylePrefix + 'BoxPack'] = 'center';
                        divEl.style.marginLeft = '0';
                        textEl.style.display = 'none';
                        textEl.textContent = '';
                    }
                }
            }
        });

        /**
         * 获取/设置模式，draw 绘制模式，可设置绘制的百分比；spin 旋转模式
         * @member {String} mode
         * @memberof ctrl.loading~Loading
         * @instance
         */
        var mode = '';
        Object.defineProperty(this, 'mode', {
            get: function () {
                return mode;
            },
            set: function (v) {
                if (!v && typeof v !== 'string' && ['draw', 'spin'].indexOf(v) < 0) {
                    throw new Error('Non expected value');
                } else {
                    mode = v;
                    if (mode === 'spin') {
                        if (arrowDirection) {
                            hideArrow().then(spin);
                        } else {
                            spin();
                        }
                    } else if (mode === 'draw') {
                        showArrow().then(function () {
                            draw(0);
                        });
                    }
                }
            }
        });

        /**
         * 获取/设置绘制的百分比，在绘制模式下设置百分比0~100
         * @member {String} per
         * @memberof ctrl.loading~Loading
         * @instance
         */
        var per = 0;
        Object.defineProperty(this, 'per', {
            get: function () {
                return per;
            },
            set: function (v) {
                if (mode !== 'draw') {
                    throw new Error('only work under "draw" mode');
                }

                if (!v && typeof v !== 'number' && v < 0 && v > 100) {
                    throw new Error('Non expected value');
                } else {
                    draw(v);
                }
            }
        });

        /**
         * 获取/设置箭头的方向
         * @member {String} arrowDirection
         * @memberof ctrl.loading~Loading
         * @instance
         */
        var arrowDirection = '';
        Object.defineProperty(this, 'arrowDirection', {
            get: function () {
                return arrowDirection;
            },
            set: function (v) {
                if (!v && typeof v !== 'string' && ['up', 'down', ''].indexOf(v) < 0) {
                    throw new Error('Non expected value');
                } else {
                    arrowDirection = v;
                    element.querySelector('.arrow').className = 'arrow ' + v;
                }
            }
        });

        /**
         * 移除控件
         * @method remove
         * @memberof ctrl.loading~Loading
         * @instance
         */
        this.remove = function () {
            if (element.parentNode) {
                element.parentNode.removeChild(element);
            }
        }


        this.element = element;
        /**
         * 当前控件的根元素
         * @member {HTMLElment} root
         * @memberof ctrl.loading~Loading
         * @instance
         */
        this.root = root;
    }

    /**
     * @namespace ctrl
     */

    /**
     * 生成加载动画控件实例
     * @method loading
     * @param {HTMLElement} element - 初始化的元素，可省略
     * @return {ctrl.loading~Loading} Loading实例
     * @memberof ctrl
     */
    ctrl.loading = function (element) {
        return new Loading(element);
    }

})(window, window['ctrl'] || (window['ctrl'] = {}));
!function (a, b) {
    function c(a) {
        return setTimeout(a, l)
    }

    function d(a) {
        clearTimeout(a)
    }

    function e() {
        var a = {}, b = new m(function (b, c) {
            a.resolve = b, a.reject = c
        });
        return a.promise = b, a
    }

    function f(a, b) {
        return ["then", "catch"].forEach(function (c) {
            b[c] = function () {
                return a[c].apply(a, arguments)
            }
        }), b
    }

    function g(b) {
        var c, d, h = !1;
        this.request = function () {
            h = !1;
            var g = arguments;
            return c = e(), f(c.promise, this), d = n(function () {
                h || c && c.resolve(b.apply(a, g))
            }), this
        }, this.cancel = function () {
            return d && (h = !0, o(d), c && c.reject("CANCEL")), this
        }, this.clone = function () {
            return new g(b)
        }
    }

    function h(a, b) {
        "function" == typeof b && (b = {0: b});
        for (var c = a / l, d = 1 / c, e = [], f = Object.keys(b).map(function (a) {
            return parseInt(a)
        }), h = 0; c > h; h++) {
            var i = f[0], j = d * h;
            if (null != i && 100 * j >= i) {
                var k = b["" + i];
                k instanceof g || (k = new g(k)), e.push(k), f.shift()
            } else e.length && e.push(e[e.length - 1].clone())
        }
        return e
    }

    function i(a) {
        var c;
        return "string" == typeof a || a instanceof Array ? b.cubicbezier ? "string" == typeof a ? b.cubicbezier[a] && (c = b.cubicbezier[a]) : a instanceof Array && 4 === a.length && (c = b.cubicbezier.apply(b.cubicbezier, a)) : console.error("require lib.cubicbezier") : "function" == typeof a && (c = a), c
    }

    function j(a, b, c) {
        var d, g = h(a, c), j = 1 / (a / l), k = 0, m = i(b);
        if (!m)throw new Error("unexcept timing function");
        var n = !1;
        this.play = function () {
            function a() {
                var c = j * (k + 1).toFixed(10), e = g[k];
                e.request(c.toFixed(10), b(c).toFixed(10)).then(function () {
                    n && (k === g.length - 1 ? (n = !1, d && d.resolve("FINISH"), d = null) : (k++, a()))
                }, function () {
                })
            }

            if (!n)return n = !0, d || (d = e(), f(d.promise, this)), a(), this
        }, this.stop = function () {
            return n ? (n = !1, g[k] && g[k].cancel(), this) : void 0
        }
    }

    var k = 60, l = 1e3 / k, m = a.Promise || b.promise && b.promise.ES6Promise, n = window.requestAnimationFrame || window.msRequestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || c, o = window.cancelAnimationFrame || window.msCancelAnimationFrame || window.webkitCancelAnimationFrame || window.mozCancelAnimationFrame || d;
    (n === c || o === d) && (n = c, o = d), b.animation = function (a, b, c) {
        return new j(a, b, c)
    }, b.animation.frame = function (a) {
        return new g(a)
    }, b.animation.requestFrame = function (a) {
        var b = new g(a);
        return b.request()
    }
}(window, window.lib || (window.lib = {}));
;
(function (win, lib, undef) {
    var doc = win.document;
    var ua = win.navigator.userAgent;
    var scrollObjs = {};
    var plugins = {};
    var dpr = win.dpr || (!!win.navigator.userAgent.match(/iPhone|iPad|iPod/) ? document.documentElement.clientWidth / win.screen.availWidth : 1);
    var inertiaCoefficient = {
        'normal': [2 * dpr, 0.0015 * dpr],
        'slow': [1.5 * dpr, 0.003 * dpr],
        'veryslow': [1.5 * dpr, 0.005 * dpr]
    }
    var timeFunction = {
        'ease': [.25, .1, .25, 1],
        'liner': [0, 0, 1, 1],
        'ease-in': [.42, 0, 1, 1],
        'ease-out': [0, 0, .58, 1],
        'ease-in-out': [.42, 0, .58, 1]
    }
    var Firefox = !!ua.match(/Firefox/i);
    var IEMobile = !!ua.match(/IEMobile/i);
    var cssPrefix = Firefox ? '-moz-' : IEMobile ? '-ms-' : '-webkit-';
    var stylePrefix = Firefox ? 'Moz' : IEMobile ? 'ms' : 'webkit';

    function debugLog() {
        if (lib.scroll.outputDebugLog) {
            console.debug.apply(console, arguments);
        }
    }

    function getBoundingClientRect(el) {
        var rect = el.getBoundingClientRect();
        if (!rect) {
            rect = {};
            rect.width = el.offsetWidth;
            rect.height = el.offsetHeight;

            rect.left = el.offsetLeft;
            rect.top = el.offsetTop;
            var parent = el.offsetParent;
            while (parent) {
                rect.left += parent.offsetLeft;
                rect.top += parent.offsetTop;
                parent = parent.offsetParent;
            }

            rect.right = rect.left + rect.width;
            rect.bottom = rect.top + rect.height;
        }
        return rect;
    }

    function getMinScrollOffset(scrollObj) {
        return 0 - scrollObj.options[scrollObj.axis + 'PaddingTop'];
    }

    function getMaxScrollOffset(scrollObj) {
        var rect = getBoundingClientRect(scrollObj.element);
        var pRect = getBoundingClientRect(scrollObj.viewport);
        var min = getMinScrollOffset(scrollObj);
        if (scrollObj.axis === 'y') {
            var max = 0 - rect.height + pRect.height;
        } else {
            var max = 0 - rect.width + pRect.width;
        }
        return Math.min(max + scrollObj.options[scrollObj.axis + 'PaddingBottom'], min);
    }

    function getBoundaryOffset(scrollObj, offset) {
        if (offset > scrollObj.minScrollOffset) {
            return offset - scrollObj.minScrollOffset;
        } else if (offset < scrollObj.maxScrollOffset) {
            return offset - scrollObj.maxScrollOffset;
        }
    }

    function touchBoundary(scrollObj, offset) {
        if (offset > scrollObj.minScrollOffset) {
            offset = scrollObj.minScrollOffset;
        } else if (offset < scrollObj.maxScrollOffset) {
            offset = scrollObj.maxScrollOffset;
        }
        return offset;
    }

    function fireEvent(scrollObj, eventName, extra) {
        debugLog(scrollObj.element.scrollId, eventName, extra);

        var event = doc.createEvent('HTMLEvents');
        event.initEvent(eventName, false, true);
        event.scrollObj = scrollObj;
        if (extra) {
            for (var key in extra) {
                event[key] = extra[key];
            }
        }
        scrollObj.element.dispatchEvent(event);
        scrollObj.viewport.dispatchEvent(event);
    }

    function getTransformOffset(scrollObj) {
        var offset = {x: 0, y: 0};
        var transform = getComputedStyle(scrollObj.element)[stylePrefix + 'Transform'];
        var matched;

        if (transform !== 'none') {
            if ((matched = transform.match(/^matrix3d\((?:[-\d.]+,\s*){12}([-\d.]+),\s*([-\d.]+)(?:,\s*[-\d.]+){2}\)/) ||
                transform.match(/^matrix\((?:[-\d.]+,\s*){4}([-\d.]+),\s*([-\d.]+)\)$/))) {
                offset.x = parseFloat(matched[1]) || 0;
                offset.y = parseFloat(matched[2]) || 0;
            }
        }

        return offset;
    }

    var CSSMatrix = IEMobile ? 'MSCSSMatrix' : 'WebKitCSSMatrix';
    var has3d = !!Firefox || CSSMatrix in win && 'm11' in new win[CSSMatrix]();

    function getTranslate(x, y) {
        x = parseFloat(x);
        y = parseFloat(y);

        if (x != 0) {
            x += 'px';
        }

        if (y != 0) {
            y += 'px';
        }

        if (has3d) {
            return 'translate3d(' + x + ', ' + y + ', 0)';
        } else {
            return 'translate(' + x + ', ' + y + ')';
        }
    }

    function setTransitionStyle(scrollObj, duration, timingFunction) {
        if (duration === '' && timingFunction === '') {
            scrollObj.element.style[stylePrefix + 'Transition'] = '';
        } else {
            scrollObj.element.style[stylePrefix + 'Transition'] = cssPrefix + 'transform ' + duration + ' ' + timingFunction + ' 0s';
        }
    }

    function setTransformStyle(scrollObj, offset) {
        var x = 0, y = 0;
        if (typeof offset === 'object') {
            x = offset.x;
            y = offset.y;
        } else {
            if (scrollObj.axis === 'y') {
                y = offset;
            } else {
                x = offset;
            }
        }
        scrollObj.element.style[stylePrefix + 'Transform'] = getTranslate(x, y);
    }

    var panning = false;
    doc.addEventListener('touchmove', function (e) {
        if (panning) {
            e.preventDefault();
            return false;
        }
        return true;
    }, false);

    /**
     * @class lib.scroll~Scroll
     * @param  {HTMLElement} [element] 需要滚动的元素，可省略
     * @param  {Object} options 设置
     * @param  {HTMLElement} [options.scrollElement] 需要滚动的元素
     * @param  {HTMLElement} [options.scrollWrap] 需要滚动元素的父元素
     * @param  {String} [options.direction='y'] 滚动的方向，默认是“y”，即垂直滚动。如需水平滚动，就设置为“x”
     * @param  {HTMLElement} [options.downgrade=false] 是否降级，默认为false，如需降级，请设置为true，并引入downgrade.js
     * @param  {Boolean} [options.noBounce=false] 边缘是否回弹效果，默认为false
     * @param  {String} [options.inertia='normal'] 惯性的类型，可取值为normal，slow，veryslow
     * @param  {Boolean} [options.isPrevent=true] 阻止默认滚动，默认为true
     * @param  {Boolean} [options.isFixScrollendClick=true] 点停滚动时，触发点击事件的问题，默认为true
     * @param  {Boolean} [options.useFrameAnimation=false] 用帧动画实现滚动，默认为false
     * @return {lib.scroll~Scroll}         Scroll类实例
     */
    function Scroll(element, options) {
        var that = this;

        options = options || {};
        options.noBounce = !!options.noBounce;
        options.padding = options.padding || {};

        if (options.isPrevent == null) {
            options.isPrevent = true;
        } else {
            options.isPrevent = !!options.isPrevent;
        }

        if (options.isFixScrollendClick == null) {
            options.isFixScrollendClick = true;
        } else {
            options.isFixScrollendClick = !!options.isFixScrollendClick;
        }

        if (options.padding) {
            options.yPaddingTop = -options.padding.top || 0;
            options.yPaddingBottom = -options.padding.bottom || 0;
            options.xPaddingTop = -options.padding.left || 0;
            options.xPaddingBottom = -options.padding.right || 0;
        } else {
            options.yPaddingTop = 0;
            options.yPaddingBottom = 0;
            options.xPaddingTop = 0;
            options.xPaddingBottom = 0;
        }

        options.direction = options.direction || 'y';
        options.inertia = options.inertia || 'normal';

        this.options = options;
        that.axis = options.direction;
        this.element = element;
        this.viewport = element.parentNode;
        this.plugins = {};

        this.element.scrollId = setTimeout(function () {
            scrollObjs[that.element.scrollId + ''] = that;
        }, 1);

        this.viewport.addEventListener('touchstart', touchstartHandler, false);
        this.viewport.addEventListener('touchend', touchendHandler, false);
        this.viewport.addEventListener('touchcancel', touchendHandler, false);
        this.viewport.addEventListener('panstart', panstartHandler, false);
        this.viewport.addEventListener('pan', panHandler, false);
        this.viewport.addEventListener('panend', panendHandler, false);

        if (options.isPrevent) {
            this.viewport.addEventListener('touchstart', function (e) {
                panning = true;
            }, false);
            that.viewport.addEventListener('touchend', function (e) {
                panning = false;
            }, false);
        }

        // if (options.isPrevent) {
        //     var d = this.axis === 'y'?'vertical':'horizontal';
        //     this.viewport.addEventListener(d + 'panstart', function(e) {
        //         panning = true;
        //     }, false);
        //     that.viewport.addEventListener('panend', function(e){
        //         panning = false;
        //     }, false);
        // }

        if (options.isFixScrollendClick) {
            var preventScrollendClick;
            var fixScrollendClickTimeoutId;

            this.viewport.addEventListener('scrolling', function () {
                preventScrollendClick = true;
                fixScrollendClickTimeoutId && clearTimeout(fixScrollendClickTimeoutId);
                fixScrollendClickTimeoutId = setTimeout(function (e) {
                    preventScrollendClick = false;
                }, 400);
            }, false);

            function preventScrollendClickHandler(e) {
                if (preventScrollendClick || isScrolling) {
                    e.preventDefault();
                    e.stopPropagation();
                    return false;
                } else {
                    return true;
                }
            }

            function fireNiceTapEventHandler(e) {
                if (!preventScrollendClick && !isScrolling) {
                    setTimeout(function () {
                        var niceTapEvent = document.createEvent('HTMLEvents');
                        niceTapEvent.initEvent('niceclick', true, true);
                        e.target.dispatchEvent(niceTapEvent);
                    }, 300);
                }
            }

            this.viewport.addEventListener('click', preventScrollendClickHandler, false);
            this.viewport.addEventListener('tap', fireNiceTapEventHandler, false);
        }

        if (options.useFrameAnimation) {
            var scrollAnimation;

            Object.defineProperty(this, 'animation', {
                get: function () {
                    return scrollAnimation;
                }
            });
        } else {
            var transitionEndHandler;
            var transitionEndTimeoutId = 0;

            function setTransitionEndHandler(h, t) {
                transitionEndHandler = null;
                clearTimeout(transitionEndTimeoutId);

                transitionEndTimeoutId = setTimeout(function () {
                    if (transitionEndHandler) {
                        transitionEndHandler = null;
                        lib.animation.requestFrame(h);
                    }
                }, (t || 400));

                transitionEndHandler = h;
            }

            element.addEventListener(Firefox ? 'transitionend' : (stylePrefix + 'TransitionEnd'), function (e) {
                if (transitionEndHandler) {
                    var handler = transitionEndHandler;

                    transitionEndHandler = null;
                    clearTimeout(transitionEndTimeoutId);

                    lib.animation.requestFrame(function () {
                        handler(e);
                    });
                }
            }, false);
        }

        var panFixRatio;
        var isScrolling;
        var isFlickScrolling;
        var cancelScrollEnd;

        Object.defineProperty(this, 'isScrolling', {
            get: function () {
                return !!isScrolling;
            }
        });

        function isEnabled(e) {
            if (!that.enabled) {
                return false;
            }

            if (typeof e.isVertical != 'undefined') {
                if (that.axis === 'y' && e.isVertical || that.axis === 'x' && !e.isVertical) {
                    // 同方向的手势，停止冒泡
                    e.stopPropagation();
                } else {
                    // 不是同方向的手势，冒泡到上层，不做任何处理
                    return false;
                }
            }

            return true;
        }

        function touchstartHandler(e) {
            if (!isEnabled(e)) {
                return;
            }

            if (isScrolling) {
                scrollEnd();
            }

            if (options.useFrameAnimation) {
                scrollAnimation && scrollAnimation.stop();
                scrollAnimation = null;
            } else {
                var transform = getTransformOffset(that);
                setTransformStyle(that, transform);
                setTransitionStyle(that, '', '');
                transitionEndHandler = null;
                clearTimeout(transitionEndTimeoutId);
            }

        }

        function touchendHandler(e) {
            if (!isEnabled(e)) {
                return;
            }

            var s0 = getTransformOffset(that)[that.axis];
            var boundaryOffset = getBoundaryOffset(that, s0);

            if (boundaryOffset) {
                // 拖动超出边缘，需要回弹
                var s1 = touchBoundary(that, s0);

                if (options.useFrameAnimation) {
                    // frame
                    var _s = s1 - s0;
                    scrollAnimation = new lib.animation(400, lib.cubicbezier.ease, function (i1, i2) {
                        var offset = (s0 + _s * i2).toFixed(2);
                        setTransformStyle(that, offset);
                        fireEvent(that, 'scrolling');
                    });
                    scrollAnimation.play().then(scrollEnd);
                } else {
                    // css
                    var offset = s1.toFixed(0);
                    setTransitionStyle(that, '0.4s', 'ease');
                    setTransformStyle(that, offset);
                    setTransitionEndHandler(scrollEnd, 400);

                    lib.animation.requestFrame(function () {
                        if (isScrolling && that.enabled) {
                            fireEvent(that, 'scrolling');
                            lib.animation.requestFrame(arguments.callee);
                        }
                    });
                }

                if (boundaryOffset > 0) {
                    fireEvent(that, that.axis === 'y' ? 'pulldownend' : 'pullrightend');
                } else if (boundaryOffset < 0) {
                    fireEvent(that, that.axis === 'y' ? 'pullupend' : 'pullleftend');
                }
            } else if (isScrolling) {
                // 未超出边缘，直接结束
                scrollEnd();
            }
        }

        var lastDisplacement;

        function panstartHandler(e) {
            if (!isEnabled(e)) {
                return;
            }

            that.transformOffset = getTransformOffset(that);
            that.minScrollOffset = getMinScrollOffset(that);
            that.maxScrollOffset = getMaxScrollOffset(that);
            panFixRatio = 2.5;
            cancelScrollEnd = true;
            isScrolling = true;
            isFlickScrolling = false;
            fireEvent(that, 'scrollstart');

            lastDisplacement = e['displacement' + that.axis.toUpperCase()];
        }


        function panHandler(e) {
            if (!isEnabled(e)) {
                return;
            }

            // 手指移动小于5像素，也忽略
            var displacement = e['displacement' + that.axis.toUpperCase()];
            if (Math.abs(displacement - lastDisplacement) < 5) {
                e.stopPropagation();
                return;
            }
            lastDisplacement = displacement;

            var offset = that.transformOffset[that.axis] + displacement;
            if (offset > that.minScrollOffset) {
                offset = that.minScrollOffset + (offset - that.minScrollOffset) / panFixRatio;
                panFixRatio *= 1.003;
            } else if (offset < that.maxScrollOffset) {
                offset = that.maxScrollOffset - (that.maxScrollOffset - offset) / panFixRatio;
                panFixRatio *= 1.003;
            }
            if (panFixRatio > 4) {
                panFixRatio = 4;
            }

            // 判断是否到了边缘
            var boundaryOffset = getBoundaryOffset(that, offset);
            if (boundaryOffset) {
                fireEvent(that, boundaryOffset > 0 ? (that.axis === 'y' ? 'pulldown' : 'pullright') : (that.axis === 'y' ? 'pullup' : 'pullleft'), {
                    boundaryOffset: Math.abs(boundaryOffset)
                });
                if (that.options.noBounce) {
                    offset = touchBoundary(that, offset);
                }
            }

            setTransformStyle(that, offset.toFixed(2));
            fireEvent(that, 'scrolling');
        }

        function panendHandler(e) {
            if (!isEnabled(e)) {
                return;
            }

            if (e.isflick) {
                flickHandler(e);
            }
        }

        function flickHandler(e) {
            cancelScrollEnd = true;

            var v0, a0, t0, s0, s, motion0;
            var v1, a1, t1, s1, motion1, sign;
            var v2, a2, t2, s2, motion2, ft;

            s0 = getTransformOffset(that)[that.axis];
            var boundaryOffset0 = getBoundaryOffset(that, s0);
            if (!boundaryOffset0) {
                //手指离开屏幕时，已经超出滚动范围，不作处理，让touchend handler处理
                //手指离开屏幕时，在滚动范围内，做一下惯性计算
                v0 = e['velocity' + that.axis.toUpperCase()];

                var maxV = 2;
                var friction = 0.0015;
                if (options.inertia && inertiaCoefficient[options.inertia]) {
                    maxV = inertiaCoefficient[options.inertia][0];
                    friction = inertiaCoefficient[options.inertia][1];
                }

                if (v0 > maxV) {
                    v0 = maxV;
                }
                if (v0 < -maxV) {
                    v0 = -maxV;
                }
                a0 = friction * ( v0 / Math.abs(v0));
                motion0 = new lib.motion({
                    v: v0,
                    a: -a0
                });
                t0 = motion0.t;
                s = s0 + motion0.s;

                var boundaryOffset1 = getBoundaryOffset(that, s);
                if (boundaryOffset1) {
                    //惯性运动足够滑出屏幕边缘
                    debugLog('惯性计算超出了边缘', boundaryOffset1);

                    v1 = v0;
                    a1 = a0;
                    if (boundaryOffset1 > 0) {
                        s1 = that.minScrollOffset;
                        sign = 1;
                    } else {
                        s1 = that.maxScrollOffset;
                        sign = -1;
                    }
                    motion1 = new lib.motion({
                        v: sign * v1,
                        a: -sign * a1,
                        s: Math.abs(s1 - s0)
                    });
                    t1 = motion1.t;
                    var timeFunction1 = motion1.generateCubicBezier();

                    v2 = v1 - a1 * t1;
                    a2 = 0.03 * (v2 / Math.abs(v2));
                    motion2 = new lib.motion({
                        v: v2,
                        a: -a2
                    });
                    t2 = motion2.t;
                    s2 = s1 + motion2.s;
                    var timeFunction2 = motion2.generateCubicBezier();

                    if (options.noBounce) {
                        // 没有边缘回弹效果，直接平顺滑到边缘
                        debugLog('没有回弹效果');

                        if (s0 !== s1) {
                            if (options.useFrameAnimation) {
                                // frame
                                var _s = s1 - s0;
                                var bezier = lib.cubicbezier(timeFunction1[0][0], timeFunction1[0][1], timeFunction1[1][0], timeFunction1[1][1]);
                                scrollAnimation = new lib.animation(t1.toFixed(0), bezier, function (i1, i2) {
                                    var offset = (s0 + _s * i2);
                                    getTransformOffset(that, offset.toFixed(2));
                                    fireEvent(that, 'scrolling', {
                                        afterFlick: true
                                    });
                                });

                                scrollAnimation.play().then(scrollEnd);
                            } else {
                                // css
                                var offset = s1.toFixed(0);
                                setTransitionStyle(that, (t1 / 1000).toFixed(2) + 's', 'cubic-bezier(' + timeFunction1 + ')');
                                setTransformStyle(that, offset);
                                setTransitionEndHandler(scrollEnd, (t1 / 1000).toFixed(2) * 1000);
                            }
                        } else {
                            scrollEnd();
                        }
                    } else if (s0 !== s2) {
                        debugLog('惯性滚动', 's=' + s2.toFixed(0), 't=' + ((t1 + t2) / 1000).toFixed(2));

                        if (options.useFrameAnimation) {
                            var _s = s2 - s0;
                            var bezier = lib.cubicbezier.easeOut;
                            scrollAnimation = new lib.animation((t1 + t2).toFixed(0), bezier, function (i1, i2) {
                                var offset = s0 + _s * i2;
                                setTransformStyle(that, offset.toFixed(2));
                                fireEvent(that, 'scrolling', {
                                    afterFlick: true
                                });
                            });

                            scrollAnimation.play().then(function () {
                                if (!that.enabled) {
                                    return;
                                }

                                var _s = s1 - s2;
                                var bezier = lib.cubicbezier.ease;
                                scrollAnimation = new lib.animation(400, bezier, function (i1, i2) {
                                    var offset = s2 + _s * i2;
                                    setTransformStyle(that, offset.toFixed(2));
                                    fireEvent(that, 'scrolling', {
                                        afterFlick: true
                                    });
                                });

                                scrollAnimation.play().then(scrollEnd);
                            });
                        } else {
                            var offset = s2.toFixed(0);
                            setTransitionStyle(that, ((t1 + t2) / 1000).toFixed(2) + 's', 'ease-out');
                            setTransformStyle(that, offset);

                            setTransitionEndHandler(function (e) {
                                if (!that.enabled) {
                                    return;
                                }

                                debugLog('惯性回弹', 's=' + s1.toFixed(0), 't=400');

                                if (s2 !== s1) {
                                    var offset = s1.toFixed(0);
                                    setTransitionStyle(that, '0.4s', 'ease');
                                    setTransformStyle(that, offset);
                                    setTransitionEndHandler(scrollEnd, 400);
                                } else {
                                    scrollEnd();
                                }
                            }, ((t1 + t2) / 1000).toFixed(2) * 1000);
                        }
                    } else {
                        scrollEnd();
                    }
                } else {
                    debugLog('惯性计算没有超出边缘');
                    var timeFunction = motion0.generateCubicBezier();

                    if (options.useFrameAnimation) {
                        // frame;
                        var _s = s - s0;
                        var bezier = lib.cubicbezier(timeFunction[0][0], timeFunction[0][1], timeFunction[1][0], timeFunction[1][1]);
                        scrollAnimation = new lib.animation(t0.toFixed(0), bezier, function (i1, i2) {
                            var offset = (s0 + _s * i2).toFixed(2);
                            setTransformStyle(that, offset);
                            fireEvent(that, 'scrolling', {
                                afterFlick: true
                            });
                        });

                        scrollAnimation.play().then(scrollEnd);
                    } else {
                        // css
                        var offset = s.toFixed(0);
                        setTransitionStyle(that, (t0 / 1000).toFixed(2) + 's', 'cubic-bezier(' + timeFunction + ')');
                        setTransformStyle(that, offset);
                        setTransitionEndHandler(scrollEnd, (t0 / 1000).toFixed(2) * 1000);
                    }
                }


                isFlickScrolling = true;
                if (!options.useFrameAnimation) {
                    lib.animation.requestFrame(function () {
                        if (isScrolling && isFlickScrolling && that.enabled) {
                            fireEvent(that, 'scrolling', {
                                afterFlick: true
                            });
                            lib.animation.requestFrame(arguments.callee);
                        }
                    });
                }
            }
        }

        function scrollEnd() {
            if (!that.enabled) {
                return;
            }

            cancelScrollEnd = false;

            setTimeout(function () {
                if (!cancelScrollEnd && isScrolling) {
                    isScrolling = false;
                    isFlickScrolling = false;

                    if (options.useFrameAnimation) {
                        scrollAnimation && scrollAnimation.stop();
                        scrollAnimation = null;
                    } else {
                        setTransitionStyle(that, '', '');
                    }
                    fireEvent(that, 'scrollend');
                }
            }, 50);
        }

        var proto = {
            /**
             * 初始化
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            init: function () {
                this.enable();
                this.refresh();
                this.scrollTo(0);
                return this;
            },
            /**
             * 启用滚动
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            enable: function () {
                this.enabled = true;
                return this;
            },
            /**
             * 停止滚动
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            disable: function () {
                var el = this.element;
                this.enabled = false;

                if (this.options.useFrameAnimation) {
                    scrollAnimation && scrollAnimation.stop();
                } else {
                    lib.animation.requestFrame(function () {
                        el.style[stylePrefix + 'Transform'] = getComputedStyle(el)[stylePrefix + 'Transform'];
                    });
                }

                return this;
            },
            /**
             * 获得滚动元素的宽度（适用于x轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getScrollWidth: function () {
                return getBoundingClientRect(this.element).width;
            },
            /**
             * 获得滚动元素的高度（适用于y轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getScrollHeight: function () {
                return getBoundingClientRect(this.element).height;
            },
            /**
             * 获得滚动元素的滚动位置（适用于x轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getScrollLeft: function () {
                return -getTransformOffset(this).x - this.options.xPaddingTop;
            },
            /**
             * 获得滚动元素的滚动位置（适用于y轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getScrollTop: function () {
                return -getTransformOffset(this).y - this.options.yPaddingTop;
            },

            getMaxScrollLeft: function () {
                return -that.maxScrollOffset - this.options.xPaddingTop;
            },
            /**
             * 获得滚动元素的最大滚动位置（适用于x轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getMaxScrollLeft: function () {
                return -that.maxScrollOffset - this.options.xPaddingTop;
            },
            /**
             * 获得滚动元素的最大滚动位置（适用于y轴滚动）
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getMaxScrollTop: function () {
                return -that.maxScrollOffset - this.options.yPaddingTop;
            },
            /**
             * 获得回弹的距离
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getBoundaryOffset: function () {
                return Math.abs(getBoundaryOffset(this, getTransformOffset(this)[this.axis]) || 0);
            },
            /**
             * 刷新滚动元素的高度
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            refresh: function () {
                var el = this.element;
                var isVertical = (this.axis === 'y');
                var type = isVertical ? 'height' : 'width';

                if (this.options[type] != null) {
                    // use options
                    el.style[type] = this.options[type] + 'px';
                } else if (!!this.options.useElementRect) {
                    el.style[type] = 'auto';
                    el.style[type] = getBoundingClientRect(el)[type] + 'px';
                } else if (el.childElementCount > 0) {
                    var range
                    var rect;
                    var firstEl = el.firstElementChild;
                    var lastEl = el.lastElementChild;

                    if (document.createRange && !this.options.ignoreOverflow) {
                        // use range
                        range = document.createRange();
                        range.selectNodeContents(el);
                        rect = getBoundingClientRect(range);
                    }

                    if (rect) {
                        el.style[type] = rect[type] + 'px';
                    } else {
                        // use child offsets
                        while (firstEl) {
                            if (getBoundingClientRect(firstEl)[type] === 0 && firstEl.nextElementSibling) {
                                firstEl = firstEl.nextElementSibling;
                            } else {
                                break;
                            }
                        }

                        while (lastEl && lastEl !== firstEl) {
                            if (getBoundingClientRect(lastEl)[type] === 0 && lastEl.previousElementSibling) {
                                lastEl = lastEl.previousElementSibling;
                            } else {
                                break;
                            }
                        }

                        el.style[type] = (getBoundingClientRect(lastEl)[isVertical ? 'bottom' : 'right'] -
                        getBoundingClientRect(firstEl)[isVertical ? 'top' : 'left']) + 'px';
                    }
                }

                this.transformOffset = getTransformOffset(this);
                this.minScrollOffset = getMinScrollOffset(this);
                this.maxScrollOffset = getMaxScrollOffset(this);
                this.scrollTo(-this.transformOffset[this.axis] - this.options[this.axis + 'PaddingTop']);
                fireEvent(this, 'contentrefresh');

                return this;
            },
            /**
             * 获得某个元素在滚动元素内的偏移距离
             * @param {HTMLElement} childEl 滚动元素中的子元素
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Object}
             */
            offset: function (childEl) {
                var elRect = getBoundingClientRect(this.element);
                var childRect = getBoundingClientRect(childEl);
                if (this.axis === 'y') {
                    var offsetRect = {
                        top: childRect.top - elRect.top - this.options.yPaddingTop,
                        left: childRect.left - elRect.left,
                        right: elRect.right - childRect.right,
                        width: childRect.width,
                        height: childRect.height
                    };

                    offsetRect.bottom = offsetRect.top + offsetRect.height;
                } else {
                    var offsetRect = {
                        top: childRect.top - elRect.top,
                        bottom: elRect.bottom - childRect.bottom,
                        left: childRect.left - elRect.left - this.options.xPaddingTop,
                        width: childRect.width,
                        height: childRect.height
                    };

                    offsetRect.right = offsetRect.left + offsetRect.width;
                }
                return offsetRect;
            },
            /**
             * 获得某个元素在滚动元素内的盒模型
             * @param {HTMLElement} childEl 滚动元素中的子元素
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Object}
             */
            getRect: function (childEl) {
                var viewRect = getBoundingClientRect(this.viewport);
                var childRect = getBoundingClientRect(childEl);
                if (this.axis === 'y') {
                    var offsetRect = {
                        top: childRect.top - viewRect.top,
                        left: childRect.left - viewRect.left,
                        right: viewRect.right - childRect.right,
                        width: childRect.width,
                        height: childRect.height
                    };

                    offsetRect.bottom = offsetRect.top + offsetRect.height;
                } else {
                    var offsetRect = {
                        top: childRect.top - viewRect.top,
                        bottom: viewRect.bottom - childRect.bottom,
                        left: childRect.left - viewRect.left,
                        width: childRect.width,
                        height: childRect.height
                    };

                    offsetRect.right = offsetRect.left + offsetRect.width;
                }
                return offsetRect;
            },
            /**
             * 判断某个元素是否在可见范围内
             * @param {HTMLElement} childEl 滚动元素中的子元素
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Boolean}
             */
            isInView: function (childEl) {
                var viewRect = this.getRect(this.viewport);
                var childRect = this.getRect(childEl);
                if (this.axis === 'y') {
                    return viewRect.top < childRect.bottom && viewRect.bottom > childRect.top;
                } else {
                    return viewRect.left < childRect.right && viewRect.right > childRect.left;
                }
            },
            /**
             * 滚动到某个位置
             * @param {Number} offset 位置
             * @param {Boolean} [isSmooth=false] 是否平滑，默认为false
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            scrollTo: function (offset, isSmooth) {
                var that = this;
                var element = this.element;

                offset = -offset - this.options[this.axis + 'PaddingTop'];
                offset = touchBoundary(this, offset);

                isScrolling = true;
                if (isSmooth === true) {
                    if (this.options.useFrameAnimation) {
                        var s0 = getTransformOffset(that)[this.axis];
                        var _s = offset - s0;
                        scrollAnimation = new lib.animation(400, lib.cubicbezier.ease, function (i1, i2) {
                            var offset = (s0 + _s * i2).toFixed(2);
                            setTransformStyle(that, offset);
                            fireEvent(that, 'scrolling');
                        });

                        scrollAnimation.play().then(scrollEnd);
                    } else {
                        setTransitionStyle(that, '0.4s', 'ease');
                        setTransformStyle(that, offset);
                        setTransitionEndHandler(scrollEnd, 400);

                        lib.animation.requestFrame(function () {
                            if (isScrolling && that.enabled) {
                                fireEvent(that, 'scrolling');
                                lib.animation.requestFrame(arguments.callee);
                            }
                        });
                    }
                } else {
                    if (!this.options.useFrameAnimation) {
                        setTransitionStyle(that, '', '');
                    }
                    setTransformStyle(that, offset);
                    scrollEnd();
                }

                return this;
            },
            /**
             * 滚动到某个元素
             * @param {HTMLElement} childEl 滚动元素中的子元素
             * @param {Boolean} [isSmooth=false] 是否平滑，默认为false
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            scrollToElement: function (childEl, isSmooth) {
                var offset = this.offset(childEl);
                offset = offset[this.axis === 'y' ? 'top' : 'left'];
                return this.scrollTo(offset, isSmooth);
            },
            /**
             * 获得可视区域的宽度
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getViewWidth: function () {
                return getBoundingClientRect(this.viewport).width;
            },
            /**
             * 获得可视区域的高度
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {Number}
             */
            getViewHeight: function () {
                return getBoundingClientRect(this.viewport).height;
            },
            /**
             * 添加下拉操作的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addPulldownHandler: function (handler) {
                var that = this;
                this.element.addEventListener('pulldownend', function (e) {
                    that.disable();
                    handler.call(that, e, function () {
                        that.scrollTo(0, true);
                        that.refresh();
                        that.enable();
                    });
                }, false);

                return this;
            },
            /**
             * 添加上拉操作的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addPullupHandler: function (handler) {
                var that = this;

                this.element.addEventListener('pullupend', function (e) {
                    that.disable();
                    handler.call(that, e, function () {
                        that.scrollTo(that.getScrollHeight(), true);
                        that.refresh();
                        that.enable();
                    });
                }, false);

                return this;
            },
            /**
             * 添加滚动开始时的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addScrollstartHandler: function (handler) {
                var that = this;
                this.element.addEventListener('scrollstart', function (e) {
                    handler.call(that, e);
                }, false);

                return this;
            },
            /**
             * 添加滚动时的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addScrollingHandler: function (handler) {
                var that = this;
                this.element.addEventListener('scrolling', function (e) {
                    handler.call(that, e);
                }, false);

                return this;
            },
            /**
             * 添加滚动结束时的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addScrollendHandler: function (handler) {
                var that = this;
                this.element.addEventListener('scrollend', function (e) {
                    handler.call(that, e);
                }, false);

                return this;
            },
            /**
             * 添加刷新时的句柄
             * @param {Function} handler 句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addContentrenfreshHandler: function (handler) {
                var that = this;
                this.element.addEventListener('contentrefresh', function (e) {
                    handler.call(that, e);
                }, false);

                return this;
            },
            /**
             * 在滚动元素上添加事件
             * @param {String} name 事件名
             * @param {Function} handler 事件句柄
             * @param {Boolean} [useCapture] 是否捕获
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            addEventListener: function (name, handler, useCapture) {
                var that = this;
                this.element.addEventListener(name, function (e) {
                    handler.call(that, e);
                }, !!useCapture);

                return this;
            },
            /**
             * 在滚动元素上移除事件
             * @param {String} name 事件名
             * @param {Function} handler 事件句柄
             * @instance
             * @memberOf lib.scroll~Scroll
             * @return {this} 当前对象
             */
            removeEventListener: function (name, handler) {
                var that = this;
                this.element.removeEventListener(name, function (e) {
                    handler.call(that, e);
                });

                return this;
            },
            /**
             * 启用插件
             * @deprecated 从`2.6.0`版本开始已废弃
             */
            enablePlugin: function (name, options) {
                var plugin = plugins[name];
                if (plugin && !this.plugins[name]) {
                    this.plugins[name] = true;
                    options = options || {};
                    plugin.call(this, name, options);
                }
                return this;
            }
        }

        for (var k in proto) {
            this[k] = proto[k];
        }
        delete proto;
    }

    /**
     * @namespace lib
     */
    /**
     * 生成Scroll对象实例
     * @param  {HTMLElement} el  需要滚动的元素
     * @param  {Object} options 设置
     * @param  {HTMLElement} [options.scrollElement] 需要滚动的元素
     * @param  {HTMLElement} [options.scrollWrap] 需要滚动元素的父元素
     * @param  {String} [options.direction='y'] 滚动的方向，默认是“y”，即垂直滚动。如需水平滚动，就设置为“x”
     * @param  {HTMLElement} [options.downgrade=false] 是否降级，默认为false，如需降级，请设置为true，并引入downgrade.js
     * @param  {Boolean} [options.noBounce=false] 边缘是否回弹效果，默认为false
     * @param  {String} [options.inertia='normal'] 惯性的类型，可取值为normal，slow，veryslow
     * @param  {Boolean} [options.isPrevent=true] 阻止默认滚动，默认为true
     * @param  {Boolean} [options.isFixScrollendClick=true] 点停滚动时，触发点击事件的问题，默认为true
     * @param  {Boolean} [options.useFrameAnimation=false] 用帧动画实现滚动，默认为false
     * @return {lib.scroll~Scroll}         Scroll类实例
     */
    lib.scroll = function (el, options) {
        if (arguments.length === 1 && !(arguments[0] instanceof HTMLElement)) {
            options = arguments[0];
            if (options.scrollElement) {
                el = options.scrollElement;
            } else if (options.scrollWrap) {
                el = options.scrollWrap.firstElementChild;
            } else {
                throw new Error('no scroll element');
            }
        }

        if (!el.parentNode) {
            throw new Error('wrong dom tree');
        }
        if (options && options.direction && ['x', 'y'].indexOf(options.direction) < 0) {
            throw new Error('wrong direction');
        }

        var scroll;
        if (options.downgrade === true && lib.scroll.downgrade) {
            scroll = lib.scroll.downgrade(el, options);
        } else {
            if (el.scrollId) {
                scroll = scrollObjs[el.scrollId];
            } else {
                scroll = new Scroll(el, options);
            }
        }
        return scroll;
    }

    /**
     * 增加插件
     * @deprecated 从`2.6.0`版本开始已废弃
     */
    lib.scroll.plugin = function (name, constructor) {
        if (constructor) {
            name = name.split(',');
            name.forEach(function (n) {
                plugins[n] = constructor;
            });
        } else {
            return plugins[name];
        }
    }

})(window, window['lib'] || (window['lib'] = {}));



