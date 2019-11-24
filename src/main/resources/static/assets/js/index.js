let hostPrefix = "/";
let historyDataColumnInfo = [
    {columnProp : 'temperHA',columnGroup : 't',columnName : 'H-A温度'},
    {columnProp : 'temperHB',columnGroup : 't',columnName : "H-B温度"},
    {columnProp : 'temperHC',columnGroup : 't',columnName : "H-C温度"},
    {columnProp : 'temperLA',columnGroup : 't',columnName : "L-A温度"},
    {columnProp : 'temperLB',columnGroup : 't',columnName : "L-B温度"},
    {columnProp : 'temperLC',columnGroup : 't',columnName : "L-C温度"},
    {columnProp : 'temperN',columnGroup : 't',columnName : "N相温度"},

    {columnProp : 'currentA',columnGroup : 'c',columnName : "A相电流"},
    {columnProp : 'currentB',columnGroup : 'c',columnName : "B相电流"},
    {columnProp : 'currentC',columnGroup : 'c',columnName : "C相电流"},

    {columnProp : 'voltageA',columnGroup : 'v',columnName : "A相电压"},
    {columnProp : 'voltageB',columnGroup : 'v',columnName : "B相电压"},
    {columnProp : 'voltageC',columnGroup : 'v',columnName : "C相电压"},

    {columnProp : 'activePowerA',columnGroup : 'f',columnName : "A相有功功率"},
    {columnProp : 'activePowerB',columnGroup : 'f',columnName : "B相有功功率"},
    {columnProp : 'activePowerC',columnGroup : 'f',columnName : "C相有功功率"},
    {columnProp : 'totalActivePower',columnGroup : 'f',columnName : "总有功功率"},
    {columnProp : 'reactivePowerA',columnGroup : 'f',columnName : "A相无功功率"},
    {columnProp : 'reactivePowerB',columnGroup : 'f',columnName : "B相无功功率"},
    {columnProp : 'reactivePowerC',columnGroup : 'f',columnName : "C相无功功率"},
    {columnProp : 'totalReactivePower',columnGroup : 'f',columnName : "总无功功率"},
    {columnProp : 'powerFactorA',columnGroup : 'f',columnName : "A相功率因数"},
    {columnProp : 'powerFactorB',columnGroup : 'f',columnName : "B相功率因数"},
    {columnProp : 'powerFactorC',columnGroup : 'f',columnName : "C相功率因数"},
    {columnProp : 'totalPowerFactor',columnGroup : 'f',columnName : "总功率因数"},

    {columnProp : 'voltageHarmonicsA',columnGroup : 'h',columnName : "A相电压谐波"},
    {columnProp : 'voltageHarmonicsB',columnGroup : 'h',columnName : "B相电压谐波"},
    {columnProp : 'voltageHarmonicsC',columnGroup : 'h',columnName : "C相电压谐波"},
    {columnProp : 'currentHarmonicsA',columnGroup : 'h',columnName : "A相电流谐波"},
    {columnProp : 'currentHarmonicsB',columnGroup : 'h',columnName : "B相电流谐波"},
    {columnProp : 'currentHarmonicsC',columnGroup : 'h',columnName : "C相电流谐波"},
];
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
});
let startTime = new Date().getTime();
let root =
    new Vue({
        el : "#root",
        data : {
            deviceDataDrawer : {
                show : false,
                device : {}
            },
            deviceMonitor : {
                form : {
                    deviceName : '',
                    deviceType : 'all',
                    order : '1',
                    deptId : null
                }
            },
            filterText : '',
            deptList : [],//部门列表
            deviceList : [],//设备列表
            deviceListLoading : true,
            warningInfo : {//告警信息
                searchForm : {},
                warningPicId : null,
                warningPicDialogShow : false
            },
            historyData : {//历史数据
                showColumn : ['t','c','v','f','h'],
                echartsInstance : null,
                list : [],
            },
            dataReport : {//数据报表
                monthReportEcahrstIns : null
            },
            drawer : false,
            drawerTitle : null,
            windowSize : windowSize,
            activeArea : "1",
            warningList : []
        },
        computed : {
            echartsLegend : function(){
                this.historyData.showColumn.indexOf("") != -1
            },
            monitorDeviceList : function(){
                let that = this;
                let deviceList = this.deviceList;
                let deviceName = that.deviceMonitor.form.deviceName;
                let deptId = that.deviceMonitor.form.deptId;
                let deviceType = that.deviceMonitor.form.deviceType;
                let filterDevice = deviceList.filter(device=>{
                    if(device.status == 0){
                        return false;
                    }
                    if(deviceName && device.name.indexOf(deviceName) == -1){
                        return false;
                    }
                    if(deptId && device.deptId != deptId){
                        return false;
                    }
                    if(deviceType != 'all' && ((deviceType == 'normal' && device.todayWarningCount > 0) || (device == 'error' && !device.todayWarningCount))){
                        return false;
                    }
                    return true;
                });
                return filterDevice.sort((l,r)=>{
                    //首先把停运的设备排到最后
                    if(l.status == 0 && r.status == 0){
                        return 0;
                    }else if(l.status == 0){
                        return 1;
                    }else if(r.status == 0){
                        return -1;
                    }
                    //再将报警次数多的设备往前排
                    let result = l.todayWarningCount > r.todayWarningCount ? -1 : (l.todayWarningCount < r.todayWarningCount ? 1 : 0);
                    return result;
                });
            }
        },
        methods : {
            getDeptList : function(callback){
                axios.get(hostPrefix + "api/dept/list",{withCredentials : true}).then(function(config){
                    if(callback){
                        callback(config.data.rows);
                    }
                }).catch(handleError);
            },
            getDeviceList : function(callback){
                axios.get(hostPrefix + "api/device/list",{params : {pageSize : 19950405},withCredentials : true})
                    .then(function(config){
                        if(callback){
                            callback(config.data.rows);
                        }
                }).catch(handleError);
            },
            /**
             * 初始化页面，加载部门数据、设备列表
             */
            initPage : function(){
                let that = this;
                that.getDeptList((deptList)=>{
                    deptList.forEach(dept=>{
                        dept.children = [];
                    });
                    that.deptList = deptList;
                    that.getDeviceList((deviceList)=>{
                        deviceList.forEach(item=>{
                            item.loadRate = loadRate(item.currentA,item.currentB,item.currentC,item.capacity);
                            item.data = [
                                {title : "A相",voltage : item.voltageA,current : item.currentA,activePower : item.activePowerA,reactivePower : item.reactivePowerA,powerFactor : item.powerFactorA,voltageHarm : item.voltageHarmA,currentHarm : item.currentHarmA,temperH : item.temperHA,temperL : item.temperHA},
                                {title : "B相",voltage : item.voltageB,current : item.currentB,activePower : item.activePowerB,reactivePower : item.reactivePowerB,powerFactor : item.powerFactorB,voltageHarm : item.voltageHarmB,currentHarm : item.currentHarmB,temperH : item.temperHB,temperL : item.temperHB},
                                {title : "C相",voltage : item.voltageC,current : item.currentC,activePower : item.activePowerC,reactivePower : item.reactivePowerC,powerFactor : item.powerFactorC,voltageHarm : item.voltageHarmC,currentHarm : item.currentHarmC,temperH : item.temperHA,temperL : item.temperHC},
                                {title : "N相",voltage : item.voltageN,current : item.currentN,activePower : item.activePowerN,reactivePower : item.reactivePowerN,powerFactor : item.powerFactorN,voltageHarm : item.voltageHarmN,currentHarm : item.currentHarmN,temperH : item.temperHN,temperL : item.temperHN}
                            ];
                            that.deptList.forEach(dept=>{
                                if(item.deptId == dept.id){
                                    dept.children.push({
                                        id : item.code,
                                        name : item.name
                                    });
                                }
                            });
                        });
                        that.deviceList = deviceList;
                        Vue.nextTick(()=>{
                            that.deviceListLoading = false;
                            that.deptList.forEach(item=>that.$refs['tree'].setChecked(item.id,true,true));
                        });
                    });
                });
            },
            showDeviceData : function(device){
                this.deviceDataDrawer.show = true;
                this.deviceDataDrawer.device = device;
            },
            deviceTreeFilter : function(value,data,node){
                if(node.level == 1){
                    return true;
                }else if(data.name.indexOf(this.filterText) != -1){
                    return true;
                }
                return false;
            },
            checkWarningInfo : function(deviceId){
                this.$message({
                    message: deviceId,
                    type : 'success'
                });
            },
            checkPositionInfo : function(deviceId){
                this.$message({
                    message: deviceId,
                    type : 'success'
                });
            },
            linkToManagement : function(){
                let a = document.createElement("a");
                a.href="management";
                a.target = "_blank";
                a.click();
            },
            changeArea : function(){
                console.log(1);
            },
            menuOpen : function(param1){
                console.log(param1);
            },
            menuClose : function(param1){
                console.log(param1);
            },
            formatNodeStr : function(node){
                console.log(node);
                return node.data.name;
            },
            updateEcharts : function(){
                if(!this.historyData.echartsInstance){
                    this.historyData.echartsInstance = echarts.init(document.getElementById('echartsInstance'));
                }
                let legendList = [];
                this.historyData.showColumn.forEach(group=>{
                    historyDataColumnInfo.forEach(columnInfo=>{
                        if(group == columnInfo.columnGroup){
                            legendList.push(columnInfo.columnName);
                        }
                    })
                });
                let seriesList = [];
                legendList.forEach(item=>{
                    let data = [];
                    for(let i = 1;i <= 31;i++){
                        let random = Math.random();
                        if(random > 0.9){
                            random *= -1;
                        }
                        data.push(random * 100);
                    }
                    seriesList.push({
                        name : item,
                        type : 'line',
                        data : data
                    });
                });
                let xAxis = [];
                for(let i = 1;i <= 31;i++){
                    xAxis.push("2019-11-" + i);
                }
                let options = {
                    title: {
                        //text: '折线图'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:legendList,
                        type : 'scroll'
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        /*feature: {
                            saveAsImage: {}
                        }*/
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: xAxis
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: seriesList
                };
                this.historyData.echartsInstance.setOption(options,{
                    notMerge : true
                });
            }
        },
        watch : {
            "deviceDataDrawer.show" : function(show){
                if(show){
                    let that = this;
                    Vue.nextTick(function(){
                        let instance = echarts.init(document.getElementById('deviceData'));
                        instance.clear();
                        axios.get(hostPrefix + "api/dept/list",{withCredentials : true}).then(function(config){
                            let legendList = [];
                            that.historyData.showColumn.forEach(group=>{
                                historyDataColumnInfo.forEach(columnInfo=>{
                                    if(group == columnInfo.columnGroup){
                                        legendList.push(columnInfo.columnName);
                                    }
                                })
                            });
                            let seriesList = [];
                            legendList.forEach(item=>{
                                let data = [];
                                for(let i = 1;i <= 31;i++){
                                    let random = Math.random();
                                    if(random > 0.9){
                                        random *= -1;
                                    }
                                    data.push(random * 100);
                                }
                                seriesList.push({
                                    name : item,
                                    type : 'line',
                                    data : data
                                });
                            });
                            let xAxis = [];
                            for(let i = 1;i <= 31;i++){
                                xAxis.push("2019-11-" + i);
                            }
                            let options = {
                                title: {
                                    //text: that.deviceDataDrawer.device.name + '实时数据'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data:legendList,
                                    type : 'scroll'
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                toolbox: {
                                    /*feature: {
                                        saveAsImage: {}
                                    }*/
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: xAxis
                                },
                                yAxis: {
                                    type: 'value'
                                },
                                series: seriesList
                            };
                            instance.setOption(options);
                        }).catch(handleError);
                    });
                }
            },
            "filterText" : function(){
                this.$refs['tree'].filter();
            },
            "historyData.list" : function(){
                this.updateEcharts();
            },
            "historyData.showColumn" : function(){
                this.updateEcharts();
            },
            "activeArea" : function(newValue){
                switch (newValue) {
                    case '3' : {
                        let that = this;
                        Vue.nextTick(function(){
                            that.historyData.echartsInstance.resize();
                        });
                    }break;
                    case '7-1' : {
                        let option = {
                            title : {
                                text : '2019年11月用电统计',
                                //x : 'center'
                            },
                            legend: {},
                            tooltip: {},
                            dataset: {
                                source: [
                                    ['product', '当月', '上月'],
                                    ['有功', 43.3, 85.8],
                                    ['无功', 83.1, 73.4],
                                    ['峰', 86.4, 65.2],
                                    ['谷', 72.4, 53.9],
                                    ['平', 72.4, 53.9]
                                ]
                            },
                            xAxis: {type: 'category'},
                            yAxis: {},
                            // Declare several bar series, each will be mapped
                            // to a column of dataset.source by default.
                            series: [
                                {type: 'bar',label : {normal : {show : true,position : 'top'}},barWidth : '35px'},
                                {type: 'bar',label : {normal : {show : true,position : 'top'}},barWidth : '35px'}
                            ]
                        };
                        Vue.nextTick(function(){
                            let ins = echarts.init(document.getElementById('monthReportCharts'));
                            ins.setOption(option,{
                                notMerge : true
                            });
                        });
                    }break;
                    case '8':{
                        Vue.nextTick(function(){
                            let ins2= echarts.init(document.getElementById('monitorStatistic'));
                            let options = {
                                title : {
                                    text: "设备运行状态",
                                    x : 'center'
                                },
                                tooltip : {
                                    trigger : 'item',
                                    formatter : "{a} <br/> {b}:{c} ({d}%)"
                                },
                                legend:{
                                    orient : 'vertical',
                                    left : 310,
                                    top : 80,
                                    data : ['异常','正常','正在运行','停运'],
                                },
                                series : [
                                    {
                                        name : '设备数量',
                                        type : 'pie',
                                        selectMode : 'single',
                                        radius : [0,'30%'],
                                        label : {
                                            normal : {position : 'inner'}
                                        },
                                        labelLine : {
                                            normal : {show : false}
                                        },
                                        data : [{ value : 344,name : '运行中'},{ value : 100,name : '停运'}]
                                    },
                                    {
                                        name : '设备数量',
                                        type : 'pie',
                                        radius : ['40%','55%'],
                                        label : {
                                            normal : {
                                                formatter: 'sdf',
                                                backgroundColor : '#eee',
                                                borderWidth : 1,
                                                borderRadius : 4,
                                                rich : {
                                                    a : {
                                                        color : '#999',
                                                        lineHeight : 22,
                                                        align : 'center',
                                                    },
                                                    hr : {
                                                        borderColor : '#aaa',
                                                    }
                                                }
                                            }
                                        },
                                        data : [
                                            {value : 200 ,name : '正常'},
                                            {value : 144 ,name : '异常'},
                                            {value : 100 ,name : '停运'},

                                        ]
                                    }
                                ]
                            };
                            ins2.setOption(options);






                            ins2 = echarts.init(document.getElementById("monitorWarningStatistic"));
                            ins2.setOption({
                                    tooltip : {
                                        trigger: 'axis',
                                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                        }
                                    },
                                title : {
                                    text : "近期设备报警次数排行",
                                    x : 'center',
                                    top : '15px',
                                    textStyle : {
                                        fontWeight : 'normal',
                                        color : '#ff1200',
                                        fontSize : 16
                                    }
                                },
                                    /*legend: {
                                        //data: ['报警次数', '报警类型']
                                    },*/
                                    grid: {
                                        left: '3%',
                                        right: '4%',
                                        bottom: '3%',
                                        containLabel: true
                                    },
                                    xAxis:  {
                                        type: 'value'
                                    },
                                    yAxis: {
                                        type: 'category',
                                        data: ['龙南线七里分47#2','玉深泵占','紫莎坨线#44变压器','南外线11#变压器','设备6']
                                    },
                                    series: [
                                        {
                                            name: '报警类型',
                                            type: 'bar',
                                            stack: '总量',
                                            label: {
                                                normal: {
                                                    show: true,
                                                    position: 'insideRight'
                                                }
                                            },
                                            data: [3, 2, 5, 9, 10],
                                            barWidth : '10px',
                                            itemStyle : {color : '#ffe011'}

                                        },
                                        {
                                            name: '报警次数',
                                            type: 'bar',
                                            stack: '总量',
                                            label: {
                                                normal: {
                                                    show: true,
                                                    position: 'insideRight'
                                                }
                                            },
                                            data: [5, 11, 15, 13, 20],
                                            barWidth : '10px',
                                            itemStyle : {color: '#ff0504'}
                                        }
                                    ]
                                }
                            );
                        });
                    }break;
                }
            }
        },
        mounted : function(){
            let that = this;
            let data = [];
            for(let i = 0;i < 10;i++){
                let obj = {};
                historyDataColumnInfo.forEach(item=>{
                    obj[item.columnProp] = Math.round(Math.random() * 220);
                });
                obj['totalPower'] = Math.random() * 1000;
                obj['order'] = i;
                obj['time'] = '2019-10-01 10:10:10';
                data.push(obj);
            }
            //this.historyData.list = data;
            Vue.nextTick(function(){
                let endTime = new Date().getTime();
                console.log("主页Vue加载时间：" + (endTime - startTime));
                that.initPage();
            });
        }
    });

function handleError(){
    alert("无法连接到服务器")
}

function loadRate(ia,ib,ic,capacity){
    let i = capacity/(1.732 * 0.4);
    return Math.round((ia + ib + ic)/3/i * 10000)/100;
}