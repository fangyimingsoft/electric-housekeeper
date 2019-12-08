let windowSize = {
    width : document.documentElement.clientWidth,
    height : document.documentElement.clientHeight
};
$(function () {
    $(window).resize(function(){
        let windowWidth = document.documentElement.clientWidth;
        let windowHeight = document.documentElement.clientHeight;
        windowSize.width = windowWidth;
        windowSize.height = windowHeight;
    });
    let rule = { type : 'number',required : true,message : '请输入数字'};
    let root =
        new Vue({
            el : "#root",
            data : {
                CODE_CURRENT_OVER_LOAD : "current_over_load",
                CODE_OVER_TEMPERATURE : "over_temperature",
                CODE_LOW_VOLTAGE : "low_voltage",
                CODE_PHASE_UNBALANCE : "phase_unbalance",
                CODE_ELECTRIC_INCREASE : "electric_increase",
                CODE_HARMONIC_OVER_LIMIT : "harmonic_over_limit",
                windowSize : windowSize,
                activeArea : "1",
                user : {
                    addUserDrawerShow : false,
                    userList : [{
                        id : 1,
                        username : 'zhangsan',
                        name : '张三',
                        role : '管理员',
                        tel : '15827656732',
                        status :1,
                        active : true
                    },{
                        id : 2,
                        username : 'lisi',
                        name : '李四',
                        role : '用户',
                        tel : '15827656732',
                        status : 0,
                        active : true
                    },{
                        id :3,
                        username : '王五',
                        name : '王五',
                        role : '用户',
                        tel : '15827656732',
                        status :1,
                        active : false
                    },{
                        id : 4,
                        username : 'admin',
                        name : 'admin',
                        role : '管理员',
                        tel : '15827656732',
                        active : true
                    },{
                        id : 5,
                        username : 'zhangsan',
                        name : '张三',
                        role : '管理员',
                        tel : '15827656732',
                        active : false
                    }],
                    form : {},
                    userFormRule : {
                        username : [{required : true,message : '请输入用户名'}],
                        password : [{required: true,message : '请输入密码'}],
                        passwordRepeat : [{validator : function(a,b,callback){
                                if(root._data.user.form.password !== null
                                    && root._data.user.form.password !== undefined
                                    && root._data.user.form.password === root._data.user.form.passwordRepeat){
                                    callback();
                                }else{
                                    callback(new Error("两次输入的密码不匹配"));
                                }
                            }}],
                        name : [{required : true,message : "请输入姓名"}],
                        tel : [{ required : true,message : "请输入联系方式"}],
                        role : [{required : true,message : "请选择权限"}]
                    }
                },
                threshold : {
                    rules : {
                        current_over_load: [rule],over_temperature: [rule],low_voltage: [rule],phase_unbalance: [rule],electric_increase: [rule],harmonic_over_limit: [rule]
                    },
                    deviceCode : null,
                    current_over_load : 10,//电流过限
                    over_temperature : 80,//超温
                    harmonic_over_limit : 10,//谐波过限
                    electric_increase : 10,//电量突增
                    phase_unbalance : 10,//三相不平衡
                    low_voltage :200,//低电压
                    deviceOptionList: []
                },
                device : {
                    deviceName : '',
                    deviceDrawerShow : false,
                    form : {//添加和编辑的表单
                        isEdit : false,
                        code : null,
                        name : null,
                        type : null,
                        capacity : null,
                        deptId : null,
                        lng : null,//经度
                        lat : null,//纬度
                        status : null
                    },
                    deviceList : []
                },
                deptList :[]
            },
            methods : {
                initDeptData : function(){
                    let that = this;
                    axios.get("/api/dept/list").then(function(config){
                        that.deptList = config.data.rows;
                    });
                },
                initDeviceData : function(){
                    let that = this;
                    axios.get("/api/device/list",{params : {currentPage : 1,pageSize : 19950405}}).then(function(config){
                        let data = config.data.rows;
                        that.device.deviceList = data;

                    });
                },
                addDevice : function(){
                    this.device.deviceDrawerShow = true;
                    this.device.form.isEdit = false;
                    let that = this;
                    Vue.nextTick(function(){
                        that.$refs['deviceForm'].clearValidate();
                    })
                },
                doSaveDevice : function(){
                    let that = this;
                    this.$refs['deviceForm'].validate(function(success){
                        if(success){
                            let formData = new FormData();
                            for(let i in that.device.form){
                                let value = that.device.form[i];
                                if(value != undefined){
                                    formData.append(i,value);
                                }
                            }
                            axios.post("/api/device",formData).then(function(config){
                                if(config.data.status == 1){
                                    that.$message({
                                        type : 'success',
                                        message : '保存成功'
                                    });
                                    that.device.deviceDrawerShow = false;
                                    that.initDeviceData();
                                }
                            });
                        }
                    });
                },
                editDevice : function(device){
                    this.device.form.isEdit = true;
                    this.device.form.code = device.code;
                    this.device.form.name = device.name;
                    this.device.form.type = device.type,
                    this.device.form.capacity = device.capacity;
                    this.device.form.deptId = device.deptId;
                    this.device.form.lng = device.lng;
                    this.device.form.lat = device.lat;
                    this.device.form.status = device.status;
                    this.device.deviceDrawerShow = true;
                    let that = this;
                    Vue.nextTick(function () {
                        that.$refs['deviceForm'].clearValidate();
                    })
                },
                userStatusChange : function(userId,active){

                },
                addUser : function(){
                    this.user.addUserDrawerShow = true;
                },
                deviceFilter : function(name){
                    let that = this;
                    axios.get("/api/device/searchList",{params : {name : name}}).then(function(config){
                        let rows = config.data.rows;
                        if(rows && rows.length > 0){
                            that.threshold.deviceOptionList = [];
                            rows.forEach(item=>{
                                that.threshold.deviceOptionList.push({
                                    label : item.name,
                                    value : item.code
                                });
                            });
                        }
                    });
                },
                saveThreshold : function(){
                    let that = this;
                    let form = this.$refs['thresholdForm'];
                    form.validate(function(success){
                        if(success){
                            let formData = new FormData();
                            for(let i in that.threshold){
                                if(that.threshold[i]){
                                    formData.append(i,that.threshold[i]);
                                }
                            }
                            that.$confirm(that.threshold.deviceCode ? '是否更新设备阈值' : '此操作会覆盖其他的设备阈值设置, 是否继续?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                            }).then(() => {
                                axios.post("/api/threshold/update",formData).then(function(){
                                    that.$message({
                                        type: 'success',
                                        message: '更新成功!' + (that.threshold.deviceCode ? '' : '（其他设备阈值被覆盖）')
                                    });
                                });
                            });
                        }
                    });
                }
            },
            watch : {
                "activeArea" : function(newValue){
                    let that = this;
                    if(newValue == '3'){
                        axios.get("/api/threshold/device").then(function(config){
                            let data = config.data.data;
                            for(let i in data){
                                that.threshold[i] = data[i];
                            }
                        });
                    }else if(newValue == '2'){
                        that.initDeviceData();
                    }
                },
                "threshold.deviceCode" : function(newValue){
                    if(newValue){
                        let that = this;
                        axios.get("/api/threshold/device",{params : {deviceCode: newValue}}).then(function(config){
                            let data = config.data.data;
                            for(let i in data){
                                that.threshold[i] = data[i];
                            }
                        })
                    }
                }
            },
            computed : {
                showDevices : function(){
                    let name = this.device.deviceName;
                    return this.device.deviceList.filter(item=>item.name.indexOf(name) != -1);
                }
            }
        })

});
