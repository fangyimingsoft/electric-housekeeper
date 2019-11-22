let hostPrefix = "http://127.0.0.1:8081/"
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
            }
        },
        methods : {
            initDeptData : function(){
                let that = this;
                axios.get(hostPrefix + "api/dept/list").then(function(config){
                    config.data.rows.forEach(item=>{
                        item.children = [];
                    });
                    that.deptList = config.data.rows;
                    that.initDeviceList();
                }).catch(handleError);
            },
            initDeviceList : function(){
                let that = this;
                axios.get(hostPrefix + "api/device/list",{params : {currentPage : 1,pageSize : 19950405}}).then(function(config){
                    config.data.rows.forEach(item=>{
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
                    that.deviceList = config.data.rows;
                    Vue.nextTick(function(){that.deviceListLoading = false;});
                }).catch(handleError);
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
                that.initDeptData();
            });
        }
    });

function handleError(){
    alert("无法连接到服务器")
}